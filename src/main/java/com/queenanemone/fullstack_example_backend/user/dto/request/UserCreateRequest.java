package com.queenanemone.fullstack_example_backend.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 생성 요청")
public record UserCreateRequest(
    @Schema(description = "사용자 이름", example = "신형석")
    String name,
    @Schema(description = "사용자 이메일", example = "example@example.com")
    String email
) {

}
