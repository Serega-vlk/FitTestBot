package com.example.fittestbot.service.callbackqueries;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.cache.records.TestProcessingCacheRecord;
import com.example.fittestbot.entity.Test;
import com.example.fittestbot.model.Answer;
import com.example.fittestbot.model.Operation;
import com.example.fittestbot.model.Question;
import com.example.fittestbot.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TestChooseCallbackQueryProcessor implements CallbackQueryProcessor {
  private CacheService<OperationCacheRecord, Long> operationCache;
  private CacheService<TestProcessingCacheRecord, Long> testProcessingCacheService;
  private TestRepository testRepository;

  @Override
  public SendMessage process(CallbackQuery query) {
    operationCache.createOrUpdate(query.getMessage().getChatId(), new OperationCacheRecord(Operation.TEST_PROCESSING));
    SendMessage answer = new SendMessage(String.valueOf(query.getMessage().getChatId()), "Натисніть СТАРТ щоб розпочати тест");
    InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder()
        .keyboardRow(List.of(InlineKeyboardButton.builder()
            .text("СТАРТ")
            .callbackData("start")
            .build()))
        .build();
    answer.setReplyMarkup(markup);
    Optional<Test> test = testRepository.findById(Long.parseLong(query.getData()));
    if (test.isEmpty()) {
      return new SendMessage(String.valueOf(query.getMessage().getChatId()), "Наразі тест не доступний");
    }
    Map<Question, List<Answer>> map = test.get().getQuestionEntities().stream()
        .collect(
            Collectors.toMap(questionEntity -> Question.builder()
                    .text(questionEntity.getText())
                    .value(questionEntity.getValue())
                    .build(),
                questionEntity -> questionEntity.getAnswerEntities().stream()
                    .map(answer1 -> Answer.builder()
                        .text(answer1.getText())
                        .isCorrect(answer1.getIsCorrect())
                        .build())
                    .toList()
            )
        );
    testProcessingCacheService.createOrUpdate(query.getMessage().getChatId(),
        new TestProcessingCacheRecord(test.get().getId(), test.get().getTotal(), 0, map)
    );
    return answer;
  }

  @Override
  public Operation getOperation() {
    return Operation.TEST_CHOOSING;
  }
}
