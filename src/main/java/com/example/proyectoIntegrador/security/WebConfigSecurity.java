package com.example.proyectoIntegrador.security;

import com.example.proyectoIntegrador.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean //PROVEEDOR DE AUTENTICACIÓN DAO
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(usuarioService);
        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz)-> authz
                        .requestMatchers("/index.html","/get_turnos.html","/put_turnos.html","/post_turnos.html").permitAll()
                        .requestMatchers("/js/turnos/**").permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/js/turnos/**")).hasRole("USER")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/js/turnos/**")).hasRole("USER")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/js/turnos/**")).hasRole("USER")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/js/turnos/**")).hasRole("USER")
                        .requestMatchers("/get_odontologos.html","/put_odontologos.html","/post_odontologos.html").hasRole("ADMIN")
                        .requestMatchers("/get_pacientes.html","/put_pacientes.html","/post_pacientes.html").hasRole("ADMIN")
                        .requestMatchers("/registro.html").hasRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/js/**")).hasRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/js/**")).hasRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/js/**")).hasRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/js/**")).hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(withDefaults()).logout(withDefaults());
        return http.build();
    }
    }


