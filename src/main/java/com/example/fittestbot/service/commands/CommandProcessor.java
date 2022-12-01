package com.example.fittestbot.service.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandProcessor {
  SendMessage process(Message message);
  String getCommand();
}
