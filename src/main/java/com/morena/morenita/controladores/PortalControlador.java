/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.morena.morenita.controladores;

import com.morena.morenita.entidades.Usuario;
import com.morena.morenita.excepciones.MisExcepciones;
import com.morena.morenita.servicio.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author More
 */
@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    UsuarioServicio usuarioServicio;
    
    @GetMapping("/")
    public String inicio(){
        return ("inicio.html");
    }
    @GetMapping("/registrar")
    public String registrar(){
        return "registroU.html";
    }
    
    @PostMapping("/registro")
    public String registrar(@RequestParam String nombre,@RequestParam String email,@RequestParam String contrasena, String contrasena2, ModelMap modelo) throws MisExcepciones{
        try {
            usuarioServicio.registrar(nombre, email, contrasena, contrasena2);
            modelo.put("exito", "Resgistrado Exitosamente");
            return "inicio.html";
        } catch (MisExcepciones ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("email", email);
            modelo.put("contrasena", contrasena);
            modelo.put("contrasena2", contrasena2);
     
            
            return "registroU.html";
        }
    }
    
    
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false)String error, ModelMap modelo){
        if (error != null){
            modelo.put("error", "Usuario o contraseña invalidos");
        }
        return "login.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicioo(HttpSession session){
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        
        
        if(logueado.getRol().toString().equals("ADMIN")){
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
        
    }
}
