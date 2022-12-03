package com.example.fittestbot.service.commands;

import com.example.fittestbot.cache.CacheService;
import com.example.fittestbot.cache.records.OperationCacheRecord;
import com.example.fittestbot.entity.Test;
import com.example.fittestbot.entity.User;
import com.example.fittestbot.model.Operation;
import com.example.fittestbot.model.Role;
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
public class DeleteTestCommandProcessor implements CommandProcessor {
  private CacheService<OperationCacheRecord, Long> operationCacheService;
  private UserRepository userRepository;
  private TestRepository testRepository;

  @Override
  public SendMessage process(Message message) {
    Optional<User> user = userRepository.findById(message.getChatId());
    if (user.isEmpty()) {
      return new SendMessage(String.valueOf(message.getChatId()), "Вас не зареестровано.\n/register");
    }
    if (user.get().getRole() != Role.ADMIN) {
      return new SendMessage(String.valueOf(message.getChatId()), "У вас немає прав для видалення тесту");
    }
    List<Test> tests = testRepository.findAll();
    if (tests.isEmpty()){
      return new SendMessage(String.valueOf(message.getChatId()), "На разі тестів немає");
    }
    SendMessage answer = new SendMessage(String.valueOf(message.getChatId()), "Який тест ви хочете видалити?");
    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    markup.setKeyboard(tests.stream().map(test -> List.of(InlineKeyboardButton.builder()
            .text(test.getName())
            .callbackData(String.valueOf(test.getId()))
            .build())
        ).toList()
    );
    answer.setReplyMarkup(markup);
    operationCacheService.createOrUpdate(message.getChatId(), new OperationCacheRecord(Operation.DELETING_TEST));
    return answer;
  }

  @Override
  public String getCommand() {
    return "deletetest";
  }
}
