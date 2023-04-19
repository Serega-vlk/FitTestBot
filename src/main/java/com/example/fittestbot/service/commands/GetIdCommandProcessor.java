package com.example.fittestbot.service.commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class GetIdCommandProcessor implements CommandProcessor{
  @Override
  public SendMessage process(Message message) {
    return new SendMessage(String.valueOf(message.getChatId()), String.valueOf(message.getChatId()));
  }

  @Override
  public String getCommand() {
    return "id";
  }
}
