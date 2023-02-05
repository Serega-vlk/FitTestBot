package com.example.fittestbot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "marks")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "mark.entity.graph",
        attributeNodes = {
            @NamedAttributeNode("user"),
            @NamedAttributeNode("test")
        })
})
public class Mark {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull
  private Test test;

  private Integer mark;
}
