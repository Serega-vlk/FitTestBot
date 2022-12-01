package com.example.fittestbot.service.noncommands;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.OperationCacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.model.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
@AllArgsConstructor
public class NonCommandFactoryImpl implements NonCommandsFactory {
  private CacheService<OperationCacheRecord, Long> cacheService;
  private List<NonCommandProcessor> nonCommandProcessors;

  @Override
  public SendMessage process(Message message) {
    Operation operation = cacheService.get(message.getChatId()).currentOperation();
    return nonCommandProcessors.stream()
        .filter(nonCommandProcessor -> nonCommandProcessor.getOperation() == operation)
        .findFirst()
        .map(nonCommandProcessor -> nonCommandProcessor.process(message))
        .orElseGet(() -> new SendMessage(String.valueOf(message.getChatId()), "Такої відповіді немає"));
  }
}
