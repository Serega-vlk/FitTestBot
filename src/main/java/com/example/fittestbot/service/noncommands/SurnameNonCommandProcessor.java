package com.example.fittestbot.service.noncommands;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.cache.records.RegistrationTempDataCacheRecord;
import com.example.fittestbot.model.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@AllArgsConstructor
public class SurnameNonCommandProcessor implements NonCommandProcessor {
  private final CacheService<OperationCacheRecord, Long> operationCacheService;
  private final CacheService<RegistrationTempDataCacheRecord, Long> registerCacheService;

  @Override
  public SendMessage process(Message message) {
    String surname = message.getText();
    registerCacheService.createOrUpdate(message.getChatId(),
        registerCacheService.get(message.getChatId()).withSurname(surname)
    );
    operationCacheService.createOrUpdate(message.getChatId(), new OperationCacheRecord(Operation.REGISTER_GROUP));
    return new SendMessage(String.valueOf(message.getChatId()), "Введіть групу");
  }

  @Override
  public Operation getOperation() {
    return Operation.REGISTER_SURNAME;
  }
}
