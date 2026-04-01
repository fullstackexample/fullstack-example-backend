package com.queenanemone.fullstack_example_backend.user.service;

import com.queenanemone.fullstack_example_backend.user.dto.request.UserCreateRequest;
import com.queenanemone.fullstack_example_backend.user.dto.request.UserUpdateRequest;
import com.queenanemone.fullstack_example_backend.user.dto.response.UserCreateResponse;
import com.queenanemone.fullstack_example_backend.user.dto.response.UserFindResponse;
import com.queenanemone.fullstack_example_backend.user.dto.response.UserUpdateResponse;
import com.queenanemone.fullstack_example_backend.user.entity.User;
import com.queenanemone.fullstack_example_backend.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
    User user = User.builder()
        .name(userCreateRequest.name())
        .email(userCreateRequest.email())
        .build();

    User savedUser = userRepository.save(user);
    return new UserCreateResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
  }

  public UserFindResponse findByName(String name) {
    User user = userRepository.findByName(name);
    return UserFindResponse.from(user);
  }

  public List<UserFindResponse> findAll() {
    // Stream : 생성, 가공, 결과의 세 과정을 거치며 데이터를 가공
    // User가 들어간 리스트를 findAll을 통해 가져온 후, stream을 만나면
    // 이 객체를 하나씩 다 컨베이어벨트에 올린다.
    // 이 엔티티를 DTO로 포장한 후, 리스트로 묶는다.
    return userRepository.findAll().stream().map(UserFindResponse::from).toList();
  }

  @Transactional
  public UserUpdateResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
    User newUser = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    newUser.update(userUpdateRequest.name(), userUpdateRequest.email());

    return new UserUpdateResponse(id, newUser.getName(), newUser.getEmail());
  }

  @Transactional
  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }

  @Transactional
  public void deleteAll() {
    userRepository.deleteAll();
  }
}
