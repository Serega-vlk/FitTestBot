package com.example.fittestbot.cache.records;

import com.example.fittestbot.model.Answer;
import com.example.fittestbot.model.Question;
import lombok.With;

import java.util.List;

@With
public record QuestionRegistrationCacheRecord(Question question, List<Answer> answers) implements CacheRecord {
}
