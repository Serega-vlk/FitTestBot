package com.example.fittestbot.service.callbackqueries;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.entity.AnswerEntity;
import com.example.fittestbot.entity.QuestionEntity;
import com.example.fittestbot.entity.Test;
import com.example.fittestbot.model.Operation;
import com.example.fittestbot.repository.AnswerRepository;
import com.example.fittestbot.repository.MarkRepository;
import com.example.fittestbot.repository.QuestionRepository;
import com.example.fittestbot.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Service
@AllArgsConstructor
public class DeleteTestCallbackQueryProcessor implements CallbackQueryProcessor {
  private CacheService<OperationCacheRecord, Long> operationCacheService;
  private TestRepository testRepository;
  private AnswerRepository answerRepository;
  private QuestionRepository questionRepository;
  private MarkRepository markRepository;

  @Override
  @Transactional
  public SendMessage process(CallbackQuery query) {
    Test test = testRepository.findById(Long.parseLong(query.getData())).get();
    List<QuestionEntity> questionEntities = test.getQuestionEntities();
    List<AnswerEntity> answerEntities = questionEntities.stream()
        .flatMap(question -> question.getAnswerEntities().stream())
        .toList();
    answerRepository.deleteAll(answerEntities);
    questionRepository.deleteAll(questionEntities);
    markRepository.deleteByTest(test);
    testRepository.delete(test);
    operationCacheService.createOrUpdate(query.getMessage().getChatId(), new OperationCacheRecord(Operation.NONE));
    return new SendMessage(String.valueOf(query.getMessage().getChatId()), "Тест успішно видалено");
  }

  @Override
  public Operation getOperation() {
    return Operation.DELETING_TEST;
  }
}
