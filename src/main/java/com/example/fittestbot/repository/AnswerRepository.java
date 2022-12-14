package com.example.fittestbot.repository;

import com.example.fittestbot.entity.AnswerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<AnswerEntity, Long> {
}
