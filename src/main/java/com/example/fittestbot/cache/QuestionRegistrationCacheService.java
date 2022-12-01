package com.example.fittestbot.cache;

import com.example.fittestbot.cache.records.QuestionRegistrationCacheRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class QuestionRegistrationCacheService implements CacheService<QuestionRegistrationCacheRecord, Long>{
  @Override
  @Cacheable(cacheNames = "questionRegistrationCache", key = "#userId")
  public QuestionRegistrationCacheRecord get(Long userId) {
    return null;
  }

  @Override
  @CachePut(cacheNames = "questionRegistrationCache", key = "#userId")
  public QuestionRegistrationCacheRecord createOrUpdate(Long userId, QuestionRegistrationCacheRecord value) {
    return value;
  }

  @Override
  @CacheEvict(cacheNames = "questionRegistrationCache", key = "#userId")
  public void delete(Long userId) {

  }
}
