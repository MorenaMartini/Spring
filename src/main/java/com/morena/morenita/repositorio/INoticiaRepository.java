/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.morena.morenita.repositorio;

import com.morena.morenita.entidades.Noticia;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author More
 */
@Repository
public interface INoticiaRepository extends JpaRepository<Noticia, Integer>{
    Optional<Noticia>findById(Integer id);// metodo, nos devuelve algo cuando se encuetre el id

    public Noticia getOne(Integer id);
    
}
