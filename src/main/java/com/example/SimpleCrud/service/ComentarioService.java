package com.example.SimpleCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SimpleCrud.entities.Comentarios;
import com.example.SimpleCrud.repository.ComentarioRepository;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    public Comentarios saveComentario(Comentarios comentario) {
        return comentarioRepository.save(comentario);
    }

    public Comentarios getComentarioById(Long id) {
        return comentarioRepository.findById(id).orElse(null);
    }
    public void deleteComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
    public List<Comentarios> getAllComentarios() {
        return comentarioRepository.findAll();
    }
    public List<Comentarios> getComentariosByLibroId(Long libroId) {
        return comentarioRepository.findByLibroId(libroId);
    }

}
