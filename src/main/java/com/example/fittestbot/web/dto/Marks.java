package com.example.fittestbot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Marks {
  private String testName;
  private Integer total;
  private List<Mark> marks;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Mark {
    String username;
    String group;
    Integer mark;
  }
}
