package co.istad.springsecuritybasic.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(
                (authz) -> authz.requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("api/v1/article/**").hasAnyRole("AUTHOR", "ADMIN", "USER")
                        .anyRequest().authenticated()
        )       .csrf(AbstractHttpConfigurer::disable)
                .formLogin((AbstractHttpConfigurer::disable)).httpBasic(Customizer.withDefaults()).build();
    }


    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1= User.builder().username("user").password(passwordEncoder().encode("123456")).roles("USER").build();
        UserDetails user2= User.builder().username("admin").password(passwordEncoder().encode("123456")).roles("ADMIN").build();
        UserDetails user3= User.builder().username("author").password(passwordEncoder().encode("123456")).roles("Author").build();

        return new InMemoryUserDetailsManager(user1, user2, user3);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
