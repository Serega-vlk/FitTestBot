package com.example.fittestbot.service.commands;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.model.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@AllArgsConstructor
public class CancelCommandProcessor implements CommandProcessor{
  private CacheService<OperationCacheRecord, Long> cacheService;

  @Override
  public SendMessage process(Message message) {
    Operation operation = cacheService.get(message.getChatId()).currentOperation();
    if (operation != Operation.TEST_PROCESSING){
      cacheService.createOrUpdate(message.getChatId(), new OperationCacheRecord(Operation.NONE));
      return new SendMessage(String.valueOf(message.getChatId()), "Операцію скасовано");
    }
    return new SendMessage(String.valueOf(message.getChatId()), "Вам потрібно допройти тест");
  }

  @Override
  public String getCommand() {
    return "cancel";
  }
}
