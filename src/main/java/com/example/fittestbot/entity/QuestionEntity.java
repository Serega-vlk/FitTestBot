package com.example.fittestbot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "question")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @NotNull
  private String text;

  @NotNull
  private Integer value;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
  private List<AnswerEntity> answerEntities;

  @ManyToOne
  @NotNull
  private Test test;
}
