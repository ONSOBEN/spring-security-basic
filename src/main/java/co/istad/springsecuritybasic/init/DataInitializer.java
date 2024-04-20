package co.istad.springsecuritybasic.init;

import co.istad.springsecuritybasic.domain.Authority;
import co.istad.springsecuritybasic.domain.Role;
import co.istad.springsecuritybasic.feature.authority.AuthorityRepository;
import co.istad.springsecuritybasic.feature.role.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @PostConstruct
    void initData(){
        authorityInit();
        roleInit();

    }

    private void authorityInit() {
        if(authorityRepository.count() == 0){
            List<String> authorities = List.of("READ", "WRITE", "DELETE");
            authorities.forEach(authority -> {
                Authority newAuthority = new Authority();
                newAuthority.setName(authority);
                authorityRepository.save(newAuthority);
            });
        }
    }

    private void roleInit() {

        if(roleRepository.count() == 0){


            Role userRole = new Role();
            userRole.setName("USER");
            userRole.setAuthorities(Set.of(authorityRepository.findByName("READ").get()));
            roleRepository.save(userRole);
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setAuthorities(new HashSet<>(authorityRepository.findAll()));
            roleRepository.save(adminRole);



        }
    }
}
