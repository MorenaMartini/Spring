/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.morena.morenita.controladores;

import com.morena.morenita.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author More
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
    @Autowired
    UsuarioServicio usuarioServicio;
    
    @GetMapping("/dashboard")
    public String inicio(){
        return ("inicio.html");
    }
    
    
    
}
