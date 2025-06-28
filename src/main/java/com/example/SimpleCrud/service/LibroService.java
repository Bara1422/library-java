package com.example.SimpleCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SimpleCrud.entities.Libro;
import com.example.SimpleCrud.repository.LibroRepository;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public Libro saveLibro(Libro libro) {
        return libroRepository.save(libro);

    }

    public Libro getLibroById(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }

    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }
}
