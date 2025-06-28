package com.example.SimpleCrud.controller;

import java.util.List;
import java.util.Map;

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

import com.example.SimpleCrud.entities.Libro;
import com.example.SimpleCrud.exception.ApiException;
import com.example.SimpleCrud.service.LibroService;

@RestController
@RequestMapping("/libros")
public class LibroController {
    @Autowired
    private LibroService libroService;

    @GetMapping
    public ResponseEntity<List<Libro>> getAllLibros() {
        List<Libro> libros = libroService.getAllLibros();
        // si la lista esta vacia retornar un arreglo vacio
        // si no retornar la lista de libros
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id) {
        Libro libro = libroService.getLibroById(id);
        if (libro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(libro);
    }

    @PostMapping
    public ResponseEntity<Libro> saveLibro(@RequestBody Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        libro.setEstado(Libro.Estado.NO_LEIDO);
        return ResponseEntity.ok(libroService.saveLibro(libro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable Long id) {
        Libro existingLibro = libroService.getLibroById(id);
        if (existingLibro == null) {
            return ResponseEntity.notFound().build();
        }
        libroService.deleteLibro(id);
        return ResponseEntity.ok().body(Map.of("message", "Libro eliminado con éxito", "value", existingLibro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLibro(@PathVariable Long id, @RequestBody Libro libro) {
        Libro existingLibro = libroService.getLibroById(id);
        if (existingLibro == null) {
            return ResponseEntity.notFound().build();
        }
        // modificar los campos que esten presentes en el json, los demas dejar igual
        if (libro.getTitulo() != null && !libro.getTitulo().isBlank()) {
            existingLibro.setTitulo(libro.getTitulo());
        }

        if (libro.getAutor() != null && !libro.getAutor().isBlank()) {
            existingLibro.setAutor(libro.getAutor());
        }

        if (libro.getAnioPublicacion() > 0) {
            existingLibro.setAnioPublicacion(libro.getAnioPublicacion());
        }

        if (libro.getGenero() != null && !libro.getGenero().isBlank()) {
            existingLibro.setGenero(libro.getGenero());
        }

        if (libro.getDescripcion() != null && !libro.getDescripcion().isBlank()) {
            existingLibro.setDescripcion(libro.getDescripcion());
        }

        if (libro.getCalificacion() >= 0 && libro.getCalificacion() <= 5) {
            existingLibro.setCalificacion(libro.getCalificacion());
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La calificación debe estar entre 0 y 5");
        }

        if (libro.getEstado() == Libro.Estado.LEIDO || libro.getEstado() == Libro.Estado.NO_LEIDO
                || libro.getEstado() == Libro.Estado.LEYENDO) {
            existingLibro.setEstado(libro.getEstado());
        } else if (libro.getEstado() != null && !libro.getEstado().toString().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El estado debe ser uno de: LEIDO, NO_LEIDO o LEYENDO");
        }

        return ResponseEntity.ok(libroService.saveLibro(existingLibro));
    }

}
