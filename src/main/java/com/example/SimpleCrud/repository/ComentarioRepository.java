package com.example.SimpleCrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SimpleCrud.entities.Comentarios;

public interface ComentarioRepository extends JpaRepository<Comentarios, Long> {
    
    List<Comentarios> findByLibroId(Long libroId);
    

}
