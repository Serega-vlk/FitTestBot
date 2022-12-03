package com.example.fittestbot.repository;

import com.example.fittestbot.entity.Mark;
import com.example.fittestbot.entity.Test;
import com.example.fittestbot.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends CrudRepository<Mark, Long> {
  List<Mark> findAllByUser(User user);
  void deleteByTest(Test test);
}
