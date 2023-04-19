package com.example.fittestbot.web.dto;

import com.example.fittestbot.model.Answer;
import com.example.fittestbot.model.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
  private Integer total;
  private String name;
  private List<QuestionNode> questions;

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class QuestionNode{
    private Question question;
    private List<Answer> answers;
  }
}
