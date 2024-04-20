package co.istad.springsecuritybasic.feature.user;

import co.istad.springsecuritybasic.domain.Role;
import co.istad.springsecuritybasic.domain.User;
import co.istad.springsecuritybasic.feature.role.RoleRepository;
import co.istad.springsecuritybasic.feature.user.dto.UserRequest;
import co.istad.springsecuritybasic.feature.user.dto.UserResponse;
import co.istad.springsecuritybasic.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        //check for role
        if(userRepository.existsByEmail(userRequest.email())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email already exists");
        }
        Set<Role> roles = new HashSet<>();
        userRequest.roles().forEach(
                r -> {
                    var roleObject = roleRepository
                            .findByName(r)
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.BAD_REQUEST,
                                    "Role " + r + "not found"
                            ));
                    roles.add(roleObject);
                }
        );
        User newUser = userMapper.toUser(userRequest, roles);
        newUser.setPassword(new BCryptPasswordEncoder().encode(userRequest.password()));
        userRepository.save(newUser);
        System.out.println("TEst"+newUser);
        return userMapper.toUserResponse(newUser);

    }
}
