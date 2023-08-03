/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.morena.morenita.repositorio;

import com.morena.morenita.entidades.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author More
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
  
// public Optional<Usuario> findByEmail(String email);//tipo de archivo que puede guardar nulls
  //nos retorna un usuario segun su email 
    
    public Usuario findByEmail(String email);
      

}
 
