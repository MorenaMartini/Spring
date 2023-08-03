/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.morena.morenita.controladores;

import com.morena.morenita.entidades.Noticia;
import com.morena.morenita.excepciones.MisExcepciones;
import com.morena.morenita.servicio.NoticiaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author More
 */
@Controller
@RequestMapping("/noticias")//configurar cual es la url que va a escuchar el controlador 
public class NoticiaControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;

    @GetMapping("/")
    public String inicio() {
        return "inicio.html";

    }

    @GetMapping("/registro")// mapea la url (con la barra / )
    public String registro(ModelMap modelo) {
        return "index.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) String titulo, @RequestParam(required = false) String cuerpo, ModelMap modelo) {//Model map sirve para que insertemos en este modelo toda la informacion que vamos a insertar por pantalla //indica que esto es un parametro requerido y que va a llegar cuando se ejecute el formulario
        try {
            noticiaServicio.crearNoticia(titulo, cuerpo);
            modelo.put("exito", "La noticia fue cargada exitosamente");//trabaja con un formato de llave y valor
        } catch (MisExcepciones ex) {

            modelo.put("error", ex.getMessage());
            return "index.html";
        }

        return "inicio.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "tablas.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, ModelMap modelo) {//esta variable string id es una variable de pad y viaja en la url modificar
        modelo.addAttribute("noticia", noticiaServicio.getOne(id));
        return "noticia_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, String titulo, String cuerpo, ModelMap modelo) {
        try {
            noticiaServicio.modificarNoticia(titulo, cuerpo, id);
//            modelo.put("exito", "salio wien");
            return "redirect:../lista";
        } catch (MisExcepciones ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:../";
        }
    }

    @GetMapping("/borrar/{id}")
    public String borrarNoticia(@PathVariable Integer id, ModelMap modelo){
        try {
            noticiaServicio.borrarNoticia(id);
            modelo.put("exito", "Noticia eliminada");
            return "redirect:../../";
        } catch (Exception ex) {
            modelo.put("error",ex.getMessage());
            return "index.html";
        }
    }
}
