package com.example.fittestbot.cache.records;

import com.example.fittestbot.model.Answer;
import com.example.fittestbot.model.Question;
import lombok.With;

import java.util.List;
import java.util.Map;

@With
public record TestRegistrationTempDataCacheRecord(String name,
                                                  Map<Question, List<Answer>> chapter) implements CacheRecord {
}
