package com.example.fittestbot.service.noncommands;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.cache.records.QuestionRegistrationCacheRecord;
import com.example.fittestbot.cache.records.TestRegistrationTempDataCacheRecord;
import com.example.fittestbot.entity.AnswerEntity;
import com.example.fittestbot.entity.QuestionEntity;
import com.example.fittestbot.entity.Test;
import com.example.fittestbot.model.Answer;
import com.example.fittestbot.model.Operation;
import com.example.fittestbot.model.Question;
import com.example.fittestbot.repository.AnswerRepository;
import com.example.fittestbot.repository.QuestionRepository;
import com.example.fittestbot.repository.TestRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TestQuestionNonCommandProcessor implements NonCommandProcessor {
  private TestRepository testRepository;
  private QuestionRepository questionRepository;
  private AnswerRepository answerRepository;
  private CacheService<TestRegistrationTempDataCacheRecord, Long> testCacheService;
  private CacheService<OperationCacheRecord, Long> operationCacheService;
  private CacheService<QuestionRegistrationCacheRecord, Long> questionCacheService;

  @Override
  @Transactional
  public SendMessage process(Message message) {
    if (message.getText().equals("stop")) {
      operationCacheService.createOrUpdate(message.getChatId(), new OperationCacheRecord(Operation.NONE));
      return saveTest(message.getChatId());
    }
    String text = message.getText().split("-")[0].trim();
    Try<Integer> valueMaybe = Try.of(() -> message.getText().split("-")[1].trim())
        .map(Integer::parseInt);
    if (valueMaybe.isFailure()) {
      return new SendMessage(String.valueOf(message.getChatId()), "Невірний формат. Спробуйте ще раз");
    }
    questionCacheService.createOrUpdate(message.getChatId(),
        new QuestionRegistrationCacheRecord(new Question(text, valueMaybe.get()), null)
    );
    operationCacheService.createOrUpdate(message.getChatId(), new OperationCacheRecord(Operation.REGISTER_TEST_ANSWERS));
    return new SendMessage(String.valueOf(message.getChatId()), "Введіть варіанти відповідей через дефіз.\n" +
        "Поставте + перед правельним варіантом відповіді. Приклад: Варіант А - +Варіант Б - Варіант В");
  }

  private SendMessage saveTest(Long userId) {
    String name = testCacheService.get(userId).name();

    Map<Question, List<Answer>> questionAnswersMap = testCacheService.get(userId).chapter();
    if (questionAnswersMap.isEmpty()) {
      return new SendMessage(String.valueOf(userId), "Ви не додали жодного питання до тесту.\n" +
          "Зареєструйте тест хоча б з одним питанням /registertest");
    }
    Test test = testRepository.save(Test.builder()
        .name(name)
        .build());
    questionAnswersMap.forEach((key, value) -> {
      QuestionEntity entity = questionRepository.save(QuestionEntity.builder()
          .value(key.getValue())
          .text(key.getText())
          .test(test)
          .build());
      value.forEach(answer -> answerRepository.save(AnswerEntity.builder()
          .text(answer.getText())
          .isCorrect(answer.getIsCorrect())
          .question(entity)
          .build()));
    });
    return new SendMessage(String.valueOf(userId), "Тест успішно зареєстровано");
  }

  @Override
  public Operation getOperation() {
    return Operation.REGISTER_TEST_QUESTION;
  }
}
