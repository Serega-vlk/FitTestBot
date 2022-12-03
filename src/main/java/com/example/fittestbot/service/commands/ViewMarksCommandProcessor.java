package com.example.fittestbot.service.commands;

import com.example.fittestbot.entity.Mark;
import com.example.fittestbot.entity.User;
import com.example.fittestbot.repository.MarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
@AllArgsConstructor
public class ViewMarksCommandProcessor implements CommandProcessor{
  private MarkRepository repository;

  @Override
  public SendMessage process(Message message) {
    List<Mark> marks = repository.findAllByUser(User.builder().id(message.getChatId()).build());
    if (marks.isEmpty()){
      return new SendMessage(String.valueOf(message.getChatId()), "Ви не пройшли жодного тесту");
    }
    StringBuilder builder = new StringBuilder();
    marks.forEach(mark -> builder.append(String.format("%s - %d/%d%n", mark.getTest().getName(),
        mark.getMark(), mark.getTest().getTotal())));
    return new SendMessage(String.valueOf(message.getChatId()), builder.toString());
  }

  @Override
  public String getCommand() {
    return "viewmarks";
  }
}
