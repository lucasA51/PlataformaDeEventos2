package org.example.plataformadeeventos.configuration;

import org.example.plataformadeeventos.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/users/register", "/cadastro"
                                        , "/login", "/css/**", "/js/**", "/img/**", "/fonts/**", "/static/**").permitAll()
                                .requestMatchers("/cadastroevento").hasAuthority("ADMIN")
                                .anyRequest().permitAll()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/cadastroevento", true)
                                .failureUrl("/login")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/") // Redireciona para a página inicial após o logout
                                .invalidateHttpSession(true) // Invalida a sessão
                                .deleteCookies("JSESSIONID") // Remove o cookie de sessão
                                .permitAll()
                )
                /*.logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/usuarios/login?logout=true")
                                .permitAll()
                )*/;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
