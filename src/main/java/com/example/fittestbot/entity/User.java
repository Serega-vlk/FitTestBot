package com.example.fittestbot.entity;

import com.example.fittestbot.model.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  Long id;
  @NotNull
  String name;
  @NotNull
  String surname;
  @NotNull
  @Enumerated(EnumType.STRING)
  Role role;
  @NotNull
  String sgroup;
}
