package com.example.fittestbot.service.commands;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.cache.records.TestRegistrationTempDataCacheRecord;
import com.example.fittestbot.entity.User;
import com.example.fittestbot.model.Operation;
import com.example.fittestbot.model.Role;
import com.example.fittestbot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TestRegisterCommandProcessor implements CommandProcessor {
  private CacheService<OperationCacheRecord, Long> operationCacheService;
  private CacheService<TestRegistrationTempDataCacheRecord, Long> testCacheService;
  private UserRepository repository;

  @Override
  public String getCommand() {
    return "registertest";
  }

  @Override
  public SendMessage process(Message message) {
    Optional<User> user = repository.findById(message.getChatId());
    if (user.isEmpty()) {
      return new SendMessage(String.valueOf(message.getChatId()), "Вас не зареестровано.\n/register");
    } else if (user.get().getRole() != Role.ADMIN) {
      return new SendMessage(String.valueOf(message.getChatId()), "У вас немає прав для створення тесту");
    }
    operationCacheService.createOrUpdate(message.getChatId(), new OperationCacheRecord(Operation.REGISTER_TEST_NAME));
    testCacheService.createOrUpdate(message.getChatId(), new TestRegistrationTempDataCacheRecord(null, new HashMap<>()));
    return new SendMessage(String.valueOf(message.getChatId()), "Введіть назву тесту");
  }
}
