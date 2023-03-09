package com.example.fittestbot.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Log {

  public static void info(String message) {
    System.out.printf("[LOG:%s] [INFO]: %s%n", LocalDateTime.now(), message);
  }

  public static void error(String message) {
    System.out.printf("[LOG:%s] [ERROR]: %s%n", LocalDateTime.now(), message);
  }
}
