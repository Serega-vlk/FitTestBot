package com.example.fittestbot.bot;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.config.properties.BotProperties;
import com.example.fittestbot.model.Operation;
import com.example.fittestbot.service.callbackqueries.CallbackQueryFactory;
import com.example.fittestbot.service.commands.CommandFactory;
import com.example.fittestbot.service.noncommands.NonCommandsFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class Bot extends TelegramLongPollingBot {
  private final String BOT_NAME;
  private final String BOT_TOKEN;
  private final CommandFactory commandFactory;
  private final NonCommandsFactory nonCommandsFactory;
  private final CallbackQueryFactory callbackQueryFactory;
  private final CacheService<OperationCacheRecord, Long> cacheService;

  private Bot(TelegramBotsApi botsApi,
              BotProperties properties,
              CommandFactory commandFactory,
              NonCommandsFactory nonCommandsFactory,
              CallbackQueryFactory callbackQueryFactory,
              CacheService<OperationCacheRecord, Long> cacheService) throws TelegramApiException {
    this.BOT_NAME = properties.getName();
    this.BOT_TOKEN = properties.getToken();
    this.commandFactory = commandFactory;
    this.nonCommandsFactory = nonCommandsFactory;
    this.callbackQueryFactory = callbackQueryFactory;
    this.cacheService = cacheService;
    botsApi.registerBot(this);
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (!update.hasCallbackQuery() && !update.hasMessage()){
      return;
    }
    setDefaultOperationIfEmpty(update);
    if (update.hasCallbackQuery()) {
      send(callbackQueryFactory.process(update.getCallbackQuery()));
    } else if (update.getMessage().getText().startsWith("/")) {
      send(commandFactory.process(update.getMessage()));
    } else {
      send(nonCommandsFactory.process(update.getMessage()));
    }
  }

  private void setDefaultOperationIfEmpty(Update update) {
    if (update.getMessage() == null) {
      if (cacheService.get(update.getCallbackQuery().getMessage().getChatId()) == null) {
        cacheService.createOrUpdate(update.getCallbackQuery().getMessage().getChatId(),
            new OperationCacheRecord(Operation.NONE));
      }
    } else {
      if (cacheService.get(update.getMessage().getChatId()) == null) {
        cacheService.createOrUpdate(update.getMessage().getChatId(),
            new OperationCacheRecord(Operation.NONE));
      }
    }
  }

  @Override
  public String getBotToken() {
    return BOT_TOKEN;
  }

  @Override
  public String getBotUsername() {
    return BOT_NAME;
  }


  public void send(SendMessage sendMessage) {
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error("Cannot send message to {}", sendMessage.getChatId());
    }
  }
}