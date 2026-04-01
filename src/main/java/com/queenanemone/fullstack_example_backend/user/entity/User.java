package com.queenanemone.fullstack_example_backend.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;

  public User(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  @Builder
  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public void update(String name, String email) {
    if (name != null) {
      this.name = name;
    }
    if (email != null) {
      this.email = email;
    }
  }
}
