package com.example.fittestbot.entity;

import jakarta.persistence.CascadeType;
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
import lombok.NoArgsConstructor;

@Entity(name = "marks")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mark {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @NotNull
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  @NotNull
  private Test test;

  private Integer mark;
}
