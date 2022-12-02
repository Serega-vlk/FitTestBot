package com.example.fittestbot.service.noncommands;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.cache.records.TestRegistrationTempDataCacheRecord;
import com.example.fittestbot.model.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@AllArgsConstructor
public class TestNameNonCommandProcessor implements NonCommandProcessor {
  private CacheService<TestRegistrationTempDataCacheRecord, Long> testCacheService;
  private CacheService<OperationCacheRecord, Long> operationCacheService;

  @Override
  public SendMessage process(Message message) {
    testCacheService.createOrUpdate(message.getChatId(),
        testCacheService.get(message.getChatId()).withName(message.getText())
    );
    operationCacheService.createOrUpdate(message.getChatId(), new OperationCacheRecord(Operation.REGISTER_TEST_QUESTION));
    return new SendMessage(String.valueOf(message.getChatId()), "Введіть питання та кількість балів, " +
        "скільки важить це питання через дефіз. Приклад: Хто був першим призидентом України? - 5");
  }

  @Override
  public Operation getOperation() {
    return Operation.REGISTER_TEST_NAME;
  }
}
