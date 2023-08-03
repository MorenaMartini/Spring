/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.morena.morenita.servicio;

import com.morena.morenita.entidades.Usuario;
import com.morena.morenita.enumeraciones.Rol;
import com.morena.morenita.excepciones.MisExcepciones;
import com.morena.morenita.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author More
 */
@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrar(String nombre, String email, String contrasena, String contrasena2) throws MisExcepciones {
        validar(nombre, email, contrasena, contrasena2);
        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContrasena( new BCryptPasswordEncoder().encode(contrasena));
        usuario.setRol(Rol.USER);

        usuarioRepositorio.save(usuario);

    }

    public void validar(String nombre, String email, String contrasena, String contrasena2) throws MisExcepciones {
        if (nombre.isEmpty() || nombre == null) {
            throw new MisExcepciones("El nombre no puede estar vacio o ser nulo");
        }

        if (email.isEmpty() || null == email) {
            throw new MisExcepciones("El email no puede estar vacio o ser nulo");
        }

        if (contrasena.isEmpty() || contrasena == null || contrasena.length() <= 5) {
            throw new MisExcepciones("La contraseña debe tener al menos 5 digitos");

        }

        if (!contrasena.equals(contrasena2)) {
            throw new MisExcepciones("Las contraseñas tiene que ser iguales ");

        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();//lista de permisos, guarda objectos de la clase granted

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());// ROLE_USER
            permisos.add(p);
             ServletRequestAttributes attr  = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();// esta llamada lo que hace es recuperar los atributos del request oseade la solicitud http
             HttpSession session = attr.getRequest().getSession(true);
             session.setAttribute("usuariosession", usuario);
          
             
            

            return new  User(usuario.getEmail(), usuario.getContrasena(), permisos);

        }else{
            return null;
        }

    }

}
