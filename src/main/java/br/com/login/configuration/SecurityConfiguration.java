package br.com.login.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity // this annotation disable all configuration of spring
@EnableMethodSecurity(jsr250Enabled = true) // authorize os PreAuthorized (Permission) no controller
public class SecurityConfiguration {

    public static final String[] GET_PUBLIC = {
            "/test"
    };
    public static final String[] POST_PUBLIC = {
            "authentication/auth",
            "authentication/auth/refresh"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomFilter customFilter) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                /* This csrf disable because this project it doesn't work with jsp (back and front)
                * It doesn't need use past authentication in all as requisition
                 */
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(customFilter, BasicAuthenticationFilter.class)
                // configure the return of requisitions and authentication
                .authorizeHttpRequests(auth -> auth
                        // configure what requisition need of authenticated
                        .requestMatchers(HttpMethod.GET, GET_PUBLIC).permitAll()
                        .requestMatchers(HttpMethod.POST, POST_PUBLIC).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

}
