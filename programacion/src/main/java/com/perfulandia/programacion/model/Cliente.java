package com.perfulandia.programacion.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(nullable = false, length = 25)
    private String nombres;

    @Column(nullable = false, length = 25)
    private String apellidos;

    @Column(nullable = false, length = 100)
    private String direccion;

    @Column(nullable = false, length = 100, unique = true)
    private String correo;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(columnDefinition = "INT(9)")
    private Integer telefono;

    public Cliente(String nombres, String apellidos, String direccion, String correo, String password, LocalDateTime fechaRegistro, Integer telefono) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.correo = correo;
        this.password = password;
        this.fechaRegistro = fechaRegistro;
        this.telefono = telefono;

    }
}
