package com.example.fittestbot.service.noncommands;

import com.example.fittestbot.model.Operation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface NonCommandProcessor {
  SendMessage process(Message message);

  Operation getOperation();
}
