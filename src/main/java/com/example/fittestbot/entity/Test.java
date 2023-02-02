package com.example.fittestbot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "test")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Test {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @NotNull
  private Integer total;

  @NotNull
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "test")
  private Set<QuestionEntity> questionEntities = new HashSet<>();
}
