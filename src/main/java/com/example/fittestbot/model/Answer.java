package com.example.fittestbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Answer {
  private String text;
  private Boolean isCorrect;
}
