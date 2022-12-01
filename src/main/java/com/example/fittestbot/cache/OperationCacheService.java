package com.example.fittestbot.cache;

import com.example.fittestbot.cache.records.OperationCacheRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class OperationCacheService implements CacheService<OperationCacheRecord, Long> {
  @Override
  @Cacheable(cacheNames = "operationCache", key = "#userId")
  public OperationCacheRecord get(Long userId) {
    return null;
  }

  @Override
  @CachePut(cacheNames = "operationCache", key = "#userId")
  public OperationCacheRecord createOrUpdate(Long userId, OperationCacheRecord record) {
    return record;
  }

  @Override
  @CacheEvict(cacheNames = "operationCache", key = "#userId")
  public void delete(Long userId) {
    //Deleting...
  }
}
