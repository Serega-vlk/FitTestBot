package com.example.fittestbot.cache;

import com.example.fittestbot.cache.records.TestRegistrationTempDataCacheRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TestRegistrationCacheService implements CacheService<TestRegistrationTempDataCacheRecord, Long> {
  @Override
  @Cacheable(cacheNames = "testRegistrationCache", key = "#userId")
  public TestRegistrationTempDataCacheRecord get(Long userId) {
    return null;
  }

  @Override
  @CachePut(cacheNames = "testRegistrationCache", key = "#userId")
  public TestRegistrationTempDataCacheRecord createOrUpdate(Long userId, TestRegistrationTempDataCacheRecord value) {
    return value;
  }

  @Override
  @CacheEvict(cacheNames = "testRegistrationCache", key = "#userId")
  public void delete(Long userId) {

  }
}
