package com.example.SimpleCrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SimpleCrud.entities.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long>{


}
