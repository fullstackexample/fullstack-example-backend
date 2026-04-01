package com.queenanemone.fullstack_example_backend.user.repository;

import com.queenanemone.fullstack_example_backend.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  public User save(User user);

  public User findByName(String name); // User 찾기

  public List<User> findAll(); // User 리스트 찾기

  public void deleteById(Long id); // User 삭제

  public void deleteAll(); // User 일괄 삭제
}
