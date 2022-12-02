package com.example.fittestbot.service.callbackqueries;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryFactory {
  SendMessage process(CallbackQuery query);
}
