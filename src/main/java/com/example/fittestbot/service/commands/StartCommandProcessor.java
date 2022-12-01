package com.example.fittestbot.service.commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class StartCommandProcessor implements CommandProcessor {
  @Override
  public SendMessage process(Message message) {
    String ans = """
        Вітаємо в боті для тестування студентів!!!
        Для прохождення тестування потрібно зареєструватися.
        /register""";
    return new SendMessage(String.valueOf(message.getChatId()), ans);
  }

  @Override
  public String getCommand() {
    return "start";
  }
}
