package com.example.fittestbot.service.noncommands;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.cache.records.RegistrationTempDataCacheRecord;
import com.example.fittestbot.entity.User;
import com.example.fittestbot.model.Operation;
import com.example.fittestbot.model.Role;
import com.example.fittestbot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@AllArgsConstructor
public class GroupNonCommandProcessor implements NonCommandProcessor {
  private final CacheService<OperationCacheRecord, Long> operationCacheService;
  private final CacheService<RegistrationTempDataCacheRecord, Long> registerCacheService;
  private final UserRepository repository;

  @Override
  public SendMessage process(Message message) {
    String group = message.getText();
    RegistrationTempDataCacheRecord record = registerCacheService.get(message.getChatId()).withGroup(group);
    registerCacheService.delete(message.getChatId());
    repository.save(User.builder()
        .id(message.getChatId())
        .name(record.name())
        .surname(record.surname())
        .role(Role.USER)
        .sgroup(record.group())
        .build());
    operationCacheService.createOrUpdate(message.getChatId(), new OperationCacheRecord(Operation.NONE));
    return new SendMessage(String.valueOf(message.getChatId()), "Вас успішно зареєстровано");
  }

  @Override
  public Operation getOperation() {
    return Operation.REGISTER_GROUP;
  }
}
