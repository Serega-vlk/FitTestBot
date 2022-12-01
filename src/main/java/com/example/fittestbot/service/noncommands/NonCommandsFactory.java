package com.example.fittestbot.service.noncommands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface NonCommandsFactory {
  SendMessage process(Message message);
}
