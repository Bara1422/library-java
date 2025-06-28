package com.example.SimpleCrud.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "libros")
public class Libro {

    public enum Estado {
        LEIDO,
        NO_LEIDO,
        LEYENDO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;

    @Column(name = "anio_publicacion")
    private int anioPublicacion;

    private String genero;
    private Estado estado;

    @Column(length = 5000)
    private String descripcion;
    private int calificacion;

    @OneToMany(mappedBy = "libro", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Comentarios> comentarios;

}
