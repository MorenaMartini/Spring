/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.morena.morenita;

import com.morena.morenita.excepciones.MisExcepciones;
import com.morena.morenita.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author More
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

    @Autowired
    public UsuarioServicio UsuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(UsuarioServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/*").hasRole( "ADMIN")
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll()
                .and().formLogin()//pertenece al formulario de login
                .loginPage("/login")//Nuestra pagina de login
                .loginProcessingUrl("/logincheck")//url con la que spring va a autenticar un usuario
                .usernameParameter("email")//configuracion de credenciales
                .passwordParameter("contrasena")
                .defaultSuccessUrl("/inicio")//si por default se genera un login correcto, le vamos a dar una url donde se va a dirigir
     .permitAll()
                .and().logout()//cuando un usuario ingrese a determinada url
                .logoutUrl("/logout")//se cierre la sesion
                .logoutSuccessUrl("/")// en caso que sea exitoso se redirige al index
                .permitAll();

    }

}
