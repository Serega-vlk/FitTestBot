package com.example.fittestbot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "answer")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @NotNull
  private String text;

  @NotNull
  private Boolean isCorrect;

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private QuestionEntity question;
}
