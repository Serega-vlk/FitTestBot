package com.example.fittestbot.cache.records;

import com.example.fittestbot.model.Answer;
import com.example.fittestbot.model.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;
import java.util.Map;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestProcessingCacheRecord implements CacheRecord {
  private Long testId;
  private Integer score;
  private Map<Question, List<Answer>> questionsRemaining;

  public void increaseValue(Integer integer){
    score += integer;
  }
}
