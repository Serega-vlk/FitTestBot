package com.example.fittestbot.cache;

import com.example.fittestbot.cache.records.TestProcessingCacheRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TestProcessingCacheService implements CacheService<TestProcessingCacheRecord, Long>{
  @Override
  @Cacheable(cacheNames = "testProcessingCache", key = "#userId")
  public TestProcessingCacheRecord get(Long userId) {
    return null;
  }

  @Override
  @CachePut(cacheNames = "testProcessingCache", key = "#userId")
  public TestProcessingCacheRecord createOrUpdate(Long userId, TestProcessingCacheRecord value) {
    return value;
  }

  @Override
  @CacheEvict(cacheNames = "testProcessingCache", key = "#userId")
  public void delete(Long userId) {

  }
}
