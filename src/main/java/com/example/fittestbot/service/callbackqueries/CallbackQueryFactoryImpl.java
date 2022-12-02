package com.example.fittestbot.service.callbackqueries;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.model.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Service
@AllArgsConstructor
public class CallbackQueryFactoryImpl implements CallbackQueryFactory{
  private CacheService<OperationCacheRecord,Long> operationCache;
  private List<CallbackQueryProcessor> processors;

  @Override
  public SendMessage process(CallbackQuery query) {
    Operation operation = operationCache.get(query.getMessage().getChatId()).currentOperation();
    return processors.stream()
        .filter(nonCommandProcessor -> nonCommandProcessor.getOperation() == operation)
        .findFirst()
        .map(nonCommandProcessor -> nonCommandProcessor.process(query))
        .orElseGet(() -> new SendMessage(String.valueOf(query.getMessage().getChatId()), "Такої відповіді немає"));
  }
}
