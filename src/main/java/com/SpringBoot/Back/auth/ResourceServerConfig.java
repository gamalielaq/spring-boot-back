package com.SpringBoot.Back.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/clientes", "/api/clientes/page/**", "/uploads/img/**").permitAll() // solo esta ruta no necesita token
        .antMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAnyRole("USER", "ADMIN") // Any: para agregar mas de un rol
        .antMatchers(HttpMethod.POST, "/api/clientes/upload").hasAnyRole("USER", "ADMIN")
        .antMatchers(HttpMethod.POST, "/api/clientes").hasRole("ADMIN")
        .antMatchers("/api/clientes/**").hasRole("ADMIN") // No indicamos el metodo -> aplica para cualquier otro metodo
        .anyRequest().authenticated(); //Cualquie peticion requiere autenticacion 
    }
    
}
