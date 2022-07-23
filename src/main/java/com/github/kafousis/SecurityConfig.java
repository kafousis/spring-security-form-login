package com.github.kafousis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        List<GrantedAuthority> adminAuthority = Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
        User admin = new User("admin", passwordEncoder().encode("admin"), adminAuthority);

        List<GrantedAuthority> userAuthority = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        User user = new User("user", passwordEncoder().encode("user"), userAuthority);

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // because of this SecurityFilterChain @Bean
        // the default spring security form does not show up

        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        return http.build();
    }
}
