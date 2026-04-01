package com.queenanemone.fullstack_example_backend.user.dto.response;

public record UserUpdateResponse(
    Long id,        // 생성된 ID를 알려줘야 프론트가 좋아합니다.
    String name,
    String email
) {

}