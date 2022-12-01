package com.example.fittestbot.cache;

import com.example.fittestbot.cache.records.CacheRecord;

public interface CacheService<R extends CacheRecord, ID> {
  R get(ID id);

  R createOrUpdate(ID id, R value);

  void delete(ID id);
}
