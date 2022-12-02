package com.example.fittestbot.service.commands;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.cache.records.RegistrationTempDataCacheRecord;
import com.example.fittestbot.entity.User;
import com.example.fittestbot.model.Operation;
import com.example.fittestbot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RegisterCommandProcessor implements CommandProcessor {
  private CacheService<OperationCacheRecord, Long> operationCacheService;
  private CacheService<RegistrationTempDataCacheRecord, Long> registerCacheService;
  private UserRepository repository;

  @Override
  public SendMessage process(Message message) {
    Optional<User> userMaybe = repository.findById(message.getChatId());
    if (userMaybe.isPresent()) {
      return new SendMessage(String.valueOf(message.getChatId()), "Вас вже зареєстрованно");
    }
    operationCacheService.createOrUpdate(message.getChatId(), new OperationCacheRecord(Operation.REGISTER_NAME));
    registerCacheService.createOrUpdate(message.getChatId(), new RegistrationTempDataCacheRecord(null, null, null));
    return new SendMessage(String.valueOf(message.getChatId()), "Введіть Ім'я");
  }

  @Override
  public String getCommand() {
    return "register";
  }
}
