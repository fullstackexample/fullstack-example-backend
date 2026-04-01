package com.queenanemone.fullstack_example_backend.user.dto.response;

import com.queenanemone.fullstack_example_backend.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 조회 응답 정보")
public record UserFindResponse(
    @Schema(description = "사용자 id", example = "3")
    Long id,
    @Schema(description = "사용자 이름", example = "신형석")
    String name,
    @Schema(description = "사용자 email", example = "example@example.com")
    String email
) {

  // 정적 팩토리 메서드
  // 엔티티를 DTO로 바꾸는 로직을 DTO 안에 넣어두면 서비스 코드가 훨씬 깨끗해집니다.
  public static UserFindResponse from(User user) {
    return new UserFindResponse(
        user.getId(),
        user.getName(),
        user.getEmail()
    );
  }
}
