package com.example.fittestbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Question {
  private String text;
  private Integer value;
}
