package com.example.fittestbot.service.callbackqueries;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.cache.records.TestProcessingCacheRecord;
import com.example.fittestbot.entity.Mark;
import com.example.fittestbot.entity.Test;
import com.example.fittestbot.entity.User;
import com.example.fittestbot.model.Answer;
import com.example.fittestbot.model.Operation;
import com.example.fittestbot.model.Question;
import com.example.fittestbot.repository.MarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TestProcessingCallbackQueryProcessor implements CallbackQueryProcessor {
  private CacheService<TestProcessingCacheRecord, Long> processingCacheService;
  private CacheService<OperationCacheRecord, Long> operationCache;
  private MarkRepository markRepository;


  @Override
  @Transactional
  public SendMessage process(CallbackQuery query) {
    TestProcessingCacheRecord record = processingCacheService.get(query.getMessage().getChatId());
    Map<Question, List<Answer>> map = record.getQuestionsRemaining();
    if (!query.getData().equals("start")) {
      record.increaseValue(Integer.parseInt(query.getData()));
    }
    if (map.isEmpty()) {
      operationCache.createOrUpdate(query.getMessage().getChatId(), new OperationCacheRecord(Operation.NONE));
      markRepository.save(Mark.builder()
          .test(Test.builder().id(record.getTestId()).build())
          .user(User.builder().id(query.getMessage().getChatId()).build())
          .mark(record.getScore())
          .build());
      return new SendMessage(String.valueOf(query.getMessage().getChatId()),
          String.format("Тест завершено. Ваш результат: %d/%d", record.getScore(), record.getTotal()));
    }
    Map.Entry<Question, List<Answer>> entry = map.entrySet().stream()
        .findFirst()
        .get();
    Question question = entry.getKey();
    List<Answer> answers = entry.getValue();
    map.remove(question);
    record.setQuestionsRemaining(map);
    processingCacheService.createOrUpdate(query.getMessage().getChatId(), record);
    SendMessage answer = new SendMessage(String.valueOf(query.getMessage().getChatId()), question.getText());
    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    List<InlineKeyboardButton> buttons = answers.stream().map(ans -> InlineKeyboardButton.builder()
            .text(ans.getText())
            .callbackData(ans.getIsCorrect() ? String.valueOf(question.getValue()) : String.valueOf(0))
            .build())
        .toList();
    markup.setKeyboard(buttons.stream().map(List::of).toList());
    answer.setReplyMarkup(markup);
    return answer;
  }

  @Override
  public Operation getOperation() {
    return Operation.TEST_PROCESSING;
  }
}
