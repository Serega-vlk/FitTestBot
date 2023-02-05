package com.example.fittestbot.repository;

import com.example.fittestbot.entity.Test;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends CrudRepository<Test, Long> {
  @EntityGraph(value = "test.entity.graph")
  List<Test> findAll();

  @EntityGraph(value = "test.entity.graph")
  Optional<Test> findById(Long id);
}
