package com.queenanemone.fullstack_example_backend.user.controller;

import com.queenanemone.fullstack_example_backend.user.dto.request.UserCreateRequest;
import com.queenanemone.fullstack_example_backend.user.dto.request.UserUpdateRequest;
import com.queenanemone.fullstack_example_backend.user.dto.response.UserCreateResponse;
import com.queenanemone.fullstack_example_backend.user.dto.response.UserFindResponse;
import com.queenanemone.fullstack_example_backend.user.dto.response.UserUpdateResponse;
import com.queenanemone.fullstack_example_backend.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // 모든 dto가 지금 그렇게 되어있지만, dto를 request와 response로 나눠서 작업한다.
  // Create와 Update 시에는 악의적인 편집을 막기 위해 개발자 측에서 request를 임의로 만들어 전달하고,
  // response도 임의로 만들어서 전달하게 만든다.

  // Read 시에는, request를 만드는 것은 개발자의 선택이지만, 데이터를 "읽는" 작업이기 때문에 response는 만들어주는 것이 좋다.

  // Delete 시에는 request와 response는 따로 필요없다.

  // 또한, Create에서의 반응 코드는 201로 설정하며, 단순히 성공이 아닌 컨텐츠 추가에 성공했다는 메시지로 보낸다.
  // Read와 Update는 200으로 보내도 무방하며, Delete는 No Content를 가리키는 204로 반환하며, 반환하는 타입도 ResponseEntity<Void>로 설정한다.

  @PostMapping(value = "/users")
  @Operation(summary = "사용자 추가", description = "사용자를 새로 추가합니다.")
  public ResponseEntity<UserCreateResponse> create(
      @RequestBody UserCreateRequest userCreateRequest) {
    return ResponseEntity.status(201).body(userService.createUser(userCreateRequest));
  }

  @GetMapping(value = "/users/{name}")
  @Operation(summary = "사용자 조회", description = "이름으로 사용자를 조회합니다.")
  @ResponseBody
  public ResponseEntity<UserFindResponse> findUser(@PathVariable String name) {
    return ResponseEntity.ok(userService.findByName(name));
  }

  @GetMapping(value = "/users")
  @Operation(summary = "사용자 전체 조회", description = "사용자 전체를 조회합니다.")
  public ResponseEntity<List<UserFindResponse>> findAll() {
    return ResponseEntity.ok(userService.findAll());
  }

  @PatchMapping(value = "/users/{id}")
  @Operation(summary = "사용자 정보 변경", description = "사용자 정보를 수정합니다. ")
  public ResponseEntity<UserUpdateResponse> updateUser(@PathVariable Long id,
      @RequestBody UserUpdateRequest userUpdateRequest) {
    return ResponseEntity.ok(userService.updateUser(id, userUpdateRequest));
  }

  @DeleteMapping(value = "/users/{id}")
  @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다. ")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(value = "/users")
  @Operation(summary = "사용자 일괄 삭제", description = "사용자를 일괄 삭제합니다. ")
  public ResponseEntity<Void> deleteAll() {
    userService.deleteAll();
    return ResponseEntity.noContent().build();
  }
}
