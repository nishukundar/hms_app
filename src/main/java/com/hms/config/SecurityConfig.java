package com.hms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        //cross-site request forgery  //cors-->who can access my api like Angular team, postman-->cross origin resource sharing

       //h(cd)2
        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class); //we use this to make oue custom filter(jwtFilter) run first later all builtin filters can run

        //haap (to make all the url open no security apply not authenticate
        http.authorizeHttpRequests().anyRequest().permitAll();


        return http.build();   //build() will build http object with all above configuration

     }

     @Bean
     public PasswordEncoder passwordEncoder(){
         return new BCryptPasswordEncoder();
     }
}

