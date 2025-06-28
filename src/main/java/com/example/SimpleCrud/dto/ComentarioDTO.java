package com.example.SimpleCrud.dto;

import lombok.Data;

@Data
public class ComentarioDTO {
    public Long id;
    public String texto;
    public Long id_libro;
}
