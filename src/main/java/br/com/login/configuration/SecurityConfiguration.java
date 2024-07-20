package br.com.login.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // this annotation disable all configuration of spring
//@EnableMethodSecurity(jsr250Enabled = true) // autoriza os PreAutorized (Permissões) no controller
public class SecurityConfiguration {

    public static final String[] GET_PUBLICOS = {
            "/test"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                /* This csrf disable because this project it doesn't work with jsp (back and front)
                * It doesn't need use past authentication in all as requisition
                * With it disable the default spring configuration is only get work
                 */
                .httpBasic(Customizer.withDefaults())
                /*

                // configura o retorno das requisições e a forma de autenticação
                 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, GET_PUBLICOS).permitAll()
                        .anyRequest().authenticated()
                ) // toda requisição precisa está autenticado
        ;

        return http.build();
    }

}
