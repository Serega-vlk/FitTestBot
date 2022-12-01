package com.example.fittestbot.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("telegram-bot")
@Data
public class BotProperties {
  private String name;
  private String token;
}
