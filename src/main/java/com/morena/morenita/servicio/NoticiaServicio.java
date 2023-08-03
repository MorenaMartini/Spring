/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.morena.morenita.servicio;


import com.morena.morenita.entidades.Noticia;
import com.morena.morenita.excepciones.MisExcepciones;
import com.morena.morenita.repositorio.INoticiaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author More
 */
@Service
public class NoticiaServicio {
    @Autowired //iniciliza la variable de abajo por el, se le conece como inyecciones de dependencias
    private INoticiaRepository NoticiaRepositorio;
    
    @Transactional // se usa cuando se hace una modificacion permanente
    public void crearNoticia(String titulo, String cuerpo) throws MisExcepciones{
        
        validar(titulo, cuerpo);
        
        Noticia no = new Noticia();
//        no.setId(id);
         no.setTitulo(titulo);
        no.setCuerpo(cuerpo);
        
        NoticiaRepositorio.save(no);
        
    }
    // lista las noticias
    public List<Noticia> listarNoticias(){
        List<Noticia> noticias = new ArrayList();
        noticias = NoticiaRepositorio.findAll(); //encuentra todas las noticias que estan en el repositorio
        
        return noticias;
        
    }
    
    public void modificarNoticia(String titulo, String cuerpo, Integer id) throws MisExcepciones{
         validar(titulo, cuerpo);
         System.out.println(titulo);
                 
        Optional<Noticia> respuesta = NoticiaRepositorio.findById(id);// es un objeto contenedor que puede 
        // contener o no un valor no nulo si esta presente devuelve true ynos retorna el valor con el metodo get
        
        if(respuesta.isPresent()  ){
            Noticia noticia = respuesta.get();
            noticia.setCuerpo(cuerpo);
            noticia.setTitulo(titulo);
           NoticiaRepositorio.save(noticia);
            
        }

    }
    
    private void validar(String titulo, String cuerpo) throws MisExcepciones{
//         if(id == null){
//           throw new MisExcepciones("El id no puede ser nulo o estar vacio");
//        }
        
        if(titulo.isEmpty() || titulo == null){
           throw new MisExcepciones("El titulo no puede ser nulo o estar vacio");
        }
        
        if(cuerpo.isEmpty() || cuerpo == null){
           throw new MisExcepciones("El cuerpo no puede ser nulo o estar vacio");
        }
        
//        if(titulo == titulo){
//            throw new MisExcepciones("Esta noticia ya esta creada, cree una nueva");
//        }
    }
    
     @Transactional
    public void borrarNoticia(Integer id) throws Exception{
        if(id==null) throw new Exception("El id no puede ser nulo");
        NoticiaRepositorio.deleteById(id);
    }
    
    public Noticia getOne(Integer id){
        return NoticiaRepositorio.getOne(id);
    }


    
}
