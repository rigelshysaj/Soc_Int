package org.example.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/**").authenticated() // Configura le autorizzazioni per le tue API
                .anyRequest().permitAll() // Le altre richieste sono accessibili a tutti
                .and()
                .httpBasic(); // Utilizza l'autenticazione HTTP Basic
    }
}
