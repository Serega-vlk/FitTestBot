package com.example.fittestbot.service.commands;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.model.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandFactoryImpl implements CommandFactory {
  List<CommandProcessor> commandProcessors;
  CacheService<OperationCacheRecord, Long> operationCacheService;

  public SendMessage process(Message message) {
    Operation operation = operationCacheService.get(message.getChatId()).currentOperation();
    if (operation != Operation.NONE){
      return new SendMessage(String.valueOf(message.getChatId()), "Ця команда не доступна зараз");
    }
    String command = message.getText().replace("/", "");
    return commandProcessors.stream()
        .filter(commandProcessor -> commandProcessor.getCommand().equals(command))
        .findFirst()
        .map(commandProcessor -> commandProcessor.process(message))
        .orElseGet(() -> new SendMessage(String.valueOf(message.getChatId()), String.format("Невідома команда /%s", command)));
  }
}
