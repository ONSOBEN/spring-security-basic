package co.istad.springsecuritybasic.feature.user.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record UserResponse(
        String id,
        String email,
        Set<String> roles
) {
}
