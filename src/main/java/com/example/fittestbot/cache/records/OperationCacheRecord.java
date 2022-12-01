package com.example.fittestbot.cache.records;

import com.example.fittestbot.model.Operation;
import lombok.With;

@With
public record OperationCacheRecord(Operation currentOperation) implements CacheRecord {
}
