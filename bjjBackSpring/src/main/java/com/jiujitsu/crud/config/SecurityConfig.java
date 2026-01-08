package com.jiujitsu.crud.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,
                                    JwtAuthenticationConverter jwtAuthConverter) throws Exception {

        return http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .cors(cors -> {})
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt ->
                    jwt.jwtAuthenticationConverter(jwtAuthConverter)
                )
            )
            .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Object realmAccessObj = jwt.getClaim("realm_access");
            if (!(realmAccessObj instanceof Map)) {
                return Collections.<GrantedAuthority>emptyList();
            }

            Map<?, ?> realmAccess = (Map<?, ?>) realmAccessObj;

            Object rolesObj = realmAccess.get("roles");
            if (!(rolesObj instanceof List)) {
                return Collections.<GrantedAuthority>emptyList();
            }

            List<?> roles = (List<?>) rolesObj;

            return roles.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(r -> "ROLE_" + r) // importante para hasRole(...)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        });

        return converter;
    }
}
