package br.com.login.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // this annotation disable all configuration of spring
@EnableMethodSecurity(jsr250Enabled = true) // autoriza os PreAutorized (Permissões) no controller
public class SecurityConfiguration {

//    private final PasswordEncoder passwordEncoder;


    public static final String[] GET_PUBLICOS = {
            "/test"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomFilter customFilter) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                /* This csrf disable because this project it doesn't work with jsp (back and front)
                * It doesn't need use past authentication in all as requisition
                * With it disable the default spring configuration is only get work
                 */
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(customFilter, BasicAuthenticationFilter.class)
                // configure the return of requisitions and authentication
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, GET_PUBLICOS).permitAll()
                        .anyRequest().authenticated()
                ); // configure what requisition need of authenticated

        return http.build();
    }

}
