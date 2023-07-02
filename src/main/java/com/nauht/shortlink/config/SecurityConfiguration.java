package com.nauht.shortlink.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.nauht.shortlink.user.Permission.*;
import static com.nauht.shortlink.user.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/api/v1/auth/**",
                            "/v2/api-docs",
                            "/v3/api-docs",
                            "/v3/api-docs/**",
                            "/swagger-resources",
                            "/swagger-resources/**",
                            "/configuration/ui",
                            "/configuration/security",
                            "/swagger-ui/**",
                            "/webjars/**",
                            "/swagger-ui.html",
                            "/api/v1/statistic/click"
                    )
                    .permitAll()
                    .requestMatchers("/api/v1/statistic/click").permitAll()
                    .requestMatchers("/api/v1/statistic/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                    .requestMatchers(GET, "/api/v1/statistic/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                    .requestMatchers(POST, "/api/v1/statistic/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                    .requestMatchers(PUT, "/api/v1/statistic/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                    .requestMatchers(DELETE, "/api/v1/statistic/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                    .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                    .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                    .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                    .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                    .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

//                    .requestMatchers("/api/v1/user/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                    .requestMatchers("/api/v1/user/activate").hasRole(ADMIN.name())
                    .requestMatchers("/api/v1/user/deactivate").hasRole(ADMIN.name())
                    .requestMatchers(POST, "/api/v1/user/activate").hasAuthority(ADMIN_UPDATE.name())
                    .requestMatchers(POST, "/api/v1/user/deactivate").hasAuthority(ADMIN_UPDATE.name())

                    .requestMatchers("/api/v1/linkmap/getAll").hasRole(ADMIN.name())
                    .requestMatchers(GET, "/api/v1/linkmap/getAll").hasAuthority(ADMIN_READ.name())
                    .requestMatchers("/api/v1/linkmap/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                    .requestMatchers(GET, "/api/v1/linkmap/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                    .requestMatchers(POST, "/api/v1/linkmap/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                    .requestMatchers(PUT, "/api/v1/linkmap/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                    .requestMatchers(DELETE, "/api/v1/linkmap/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                    .anyRequest().authenticated()
            )

            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .logout((logout) ->
                    logout.logoutUrl("/api/v1/auth/logout")
                            .addLogoutHandler(logoutHandler)
                            .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))

    ;

    return http.build();
  }
}
