package com.example.fittestbot.service.callbackqueries;

import com.example.fittestbot.model.Operation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryProcessor {
  SendMessage process(CallbackQuery query);

  Operation getOperation();
}
