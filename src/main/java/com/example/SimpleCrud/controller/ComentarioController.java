package com.example.SimpleCrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SimpleCrud.dto.ComentarioDTO;
import com.example.SimpleCrud.entities.Comentarios;
import com.example.SimpleCrud.entities.Libro;
import com.example.SimpleCrud.exception.ApiException;
import com.example.SimpleCrud.service.ComentarioService;
import com.example.SimpleCrud.service.LibroService;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private LibroService libroService;

    @GetMapping
    public ResponseEntity<List<Comentarios>> getAllComentarios() {
        List<Comentarios> comentarios = comentarioService.getAllComentarios();
        return ResponseEntity.ok(comentarios);
    }

    @GetMapping("/{libroId}")
    public ResponseEntity<List<Comentarios>> getComentariosByLibroId(@PathVariable Long libroId) {
        List<Comentarios> comentarios = comentarioService.getComentariosByLibroId(libroId);
        return ResponseEntity.ok(comentarios);
    }

    @PostMapping
    public ResponseEntity<?> saveComentario(@RequestBody ComentarioDTO dto) {

        Libro libro = libroService.getLibroById(dto.getId_libro());
        if (libro == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Libro no encontrado con ID: " + dto.getId_libro());
        }

        Comentarios comentario = new Comentarios();
        comentario.setTexto(dto.getTexto());

        comentario.setLibro(libro);
        Comentarios savedComentario = comentarioService.saveComentario(comentario);
        if (savedComentario == null) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el comentario");
        }

        ComentarioDTO responseDTO = new ComentarioDTO();
        responseDTO.setId(savedComentario.getId());
        responseDTO.setTexto(savedComentario.getTexto());
        responseDTO.setId_libro(savedComentario.getLibro().getId());

        return ResponseEntity.ok(responseDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Comentarios> updateComentario(@PathVariable Long id, @RequestBody Comentarios comentario) {
        Comentarios existingComentario = comentarioService.getComentarioById(id);
        if (existingComentario == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Comentario no encontrado con ID: " + id);
        }

        if (comentario.getTexto() == null || comentario.getTexto().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El texto del comentario no puede estar vacío");
        }
        if (comentario.getLibro() == null || comentario.getLibro().getId() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El libro del comentario no puede estar vacío");
        }

        existingComentario.setTexto(comentario.getTexto());

        comentarioService.saveComentario(existingComentario);

        return ResponseEntity.ok(existingComentario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        Comentarios existingComentario = comentarioService.getComentarioById(id);
        if (existingComentario == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Comentario no encontrado con ID: " + id);
        }
        comentarioService.deleteComentario(id);

        return ResponseEntity.noContent().build();
    }

}
