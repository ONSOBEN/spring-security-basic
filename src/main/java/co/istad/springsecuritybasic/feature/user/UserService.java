package co.istad.springsecuritybasic.feature.user;

import co.istad.springsecuritybasic.feature.user.dto.UserRequest;
import co.istad.springsecuritybasic.feature.user.dto.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
}
