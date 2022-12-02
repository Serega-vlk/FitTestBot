package com.example.fittestbot.service.callbackqueries;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.model.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
@AllArgsConstructor
public class TestChooseCallbackQueryProcessor implements CallbackQueryProcessor {
  CacheService<OperationCacheRecord, Long> operationCache;

  @Override
  public SendMessage process(CallbackQuery query) {
    operationCache.createOrUpdate(query.getMessage().getChatId(), new OperationCacheRecord(Operation.TEST_PROCESSING));
    SendMessage answer = new SendMessage(String.valueOf(query.getMessage().getChatId()), "Натисніть СТАРТ щоб розпочати тест");
    InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder()
        .keyboardRow(List.of(InlineKeyboardButton.builder()
            .text("СТАРТ")
            .callbackData("start")
            .build()))
        .build();
    answer.setReplyMarkup(markup);
    return answer;
  }

  @Override
  public Operation getOperation() {
    return Operation.TEST_CHOOSING;
  }
}
