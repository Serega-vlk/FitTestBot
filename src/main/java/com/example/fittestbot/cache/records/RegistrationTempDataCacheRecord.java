package com.example.fittestbot.cache.records;

import lombok.With;

@With
public record RegistrationTempDataCacheRecord(String name, String surname, String group) implements CacheRecord {
}
