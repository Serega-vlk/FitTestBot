package com.example.fittestbot.service.noncommands;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.cache.records.QuestionRegistrationCacheRecord;
import com.example.fittestbot.cache.records.TestRegistrationTempDataCacheRecord;
import com.example.fittestbot.model.Answer;
import com.example.fittestbot.model.Operation;
import com.example.fittestbot.model.Question;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TestAnswersNonCommandProcessor implements NonCommandProcessor {
  private CacheService<TestRegistrationTempDataCacheRecord, Long> testCacheService;
  private CacheService<OperationCacheRecord, Long> operationCacheService;
  private CacheService<QuestionRegistrationCacheRecord, Long> questionCacheService;

  @Override
  public SendMessage process(Message message) {
    List<Answer> answers = Arrays.stream(message.getText().split("-"))
        .map(String::trim)
        .map(ans -> new Answer(ans.replace("+", ""), ans.startsWith("+")))
        .toList();
    QuestionRegistrationCacheRecord questionRegistrationCacheRecord = questionCacheService.get(message.getChatId())
        .withAnswers(answers);
    questionCacheService.delete(message.getChatId());
    Map<Question, List<Answer>> testMap = testCacheService.get(message.getChatId()).chapter();
    testMap.put(questionRegistrationCacheRecord.question(), questionRegistrationCacheRecord.answers());
    testCacheService.createOrUpdate(message.getChatId(),
        testCacheService.get(message.getChatId()).withChapter(testMap)
    );
    operationCacheService.createOrUpdate(message.getChatId(), new OperationCacheRecord(Operation.REGISTER_TEST_QUESTION));
    return new SendMessage(String.valueOf(message.getChatId()), "Введіть наступне питання в тому ж форматі.\n" +
        "Для завершеня реєстрації тесту введіть stop");
  }

  @Override
  public Operation getOperation() {
    return Operation.REGISTER_TEST_ANSWERS;
  }
}
