package com.perfulandia.programacion.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "soporte")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Soporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSoporte;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, length = 100)
    private String asunto;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 50)
    private String estado;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    public Soporte(Cliente cliente, String asunto, String descripcion, String estado) {
        this.cliente = cliente;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCreacion = LocalDateTime.now();
    }
}