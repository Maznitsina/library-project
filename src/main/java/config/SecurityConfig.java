package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.itgirl.libraryproject.dto.UsersDto;
import ru.itgirl.libraryproject.model.Users;
import ru.itgirl.libraryproject.repository.UsersRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UsersRepository usersRepository;


      @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/book").hasRole("USER")
                                .requestMatchers("/book/v2").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .httpBasic();

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return login -> {
            UsersDto user = usersRepository.findByUsername(login);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            return User.builder()
                    .username(user.getUsername())
                    .password(passwordEncoder().encode(user.getPassword()))
                    .roles(user.getRoles())
                    .build();
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}