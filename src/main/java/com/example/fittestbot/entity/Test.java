package com.example.fittestbot.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "test")
@Builder
@Setter
@Getter
@With
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
    name = "test.entity.graph",
    attributeNodes = @NamedAttributeNode(value = "questionEntities", subgraph = "question.entity.graph"),
    subgraphs = @NamedSubgraph(name = "question.entity.graph",
        attributeNodes = @NamedAttributeNode(value = "answerEntities"))
)
public class Test {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @NotNull
  private Integer total;

  @NotNull
  private String name;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "test")
  private Set<QuestionEntity> questionEntities = new HashSet<>();
}
