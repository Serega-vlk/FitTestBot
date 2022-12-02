package com.example.fittestbot.service.commands;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.entity.Mark;
import com.example.fittestbot.entity.Test;
import com.example.fittestbot.entity.User;
import com.example.fittestbot.model.Operation;
import com.example.fittestbot.repository.MarkRepository;
import com.example.fittestbot.repository.TestRepository;
import com.example.fittestbot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChooseTestCommandProcessor implements CommandProcessor {
  private TestRepository repository;
  private UserRepository userRepository;
  private MarkRepository markRepository;
  private CacheService<OperationCacheRecord, Long> operationCacheService;

  @Override
  public SendMessage process(Message message) {
    Optional<User> user = userRepository.findById(message.getChatId());
    if (user.isEmpty()) {
      return new SendMessage(String.valueOf(message.getChatId()), "Вас не зареестровано.\n/register");
    }
    List<Test> passedTests = markRepository.findAllByUser(user.get()).stream()
        .map(Mark::getTest)
        .toList();
    List<InlineKeyboardButton> tests = repository.findAll().stream()
        .filter(test -> !passedTests.contains(test))
        .map(test -> InlineKeyboardButton.builder()
            .text(test.getName())
            .callbackData(String.valueOf(test.getId()))
            .build())
        .toList();
    if (tests.isEmpty()) {
      return new SendMessage(String.valueOf(message.getChatId()), "На разі не має доступних тестів");
    }
    SendMessage answer = new SendMessage(String.valueOf(message.getChatId()), "Оберіть тест");
    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    markup.setKeyboard(List.of(tests));
    answer.setReplyMarkup(markup);
    operationCacheService.createOrUpdate(message.getChatId(), new OperationCacheRecord(Operation.TEST_CHOOSING));
    return answer;
  }

  @Override
  public String getCommand() {
    return "chooseTest";
  }
}
