package com.example.fittestbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class FitTestBotApplication {

  public static void main(String[] args) {
    SpringApplication.run(FitTestBotApplication.class, args);
  }

}
