package com.example.fittestbot.repository;

import com.example.fittestbot.entity.Test;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends CrudRepository<Test, Long> {
  List<Test> findAll();

  Test save(Test test);

  void deleteById(Long id);

  Optional<Test> findByName(String name);
}
