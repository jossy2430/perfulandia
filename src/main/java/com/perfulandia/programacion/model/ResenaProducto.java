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
@Data
@Table(name = "resena_producto")
@AllArgsConstructor
@NoArgsConstructor
public class ResenaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResena;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer calificacion;

    @Column(nullable = false)
    private LocalDateTime fechaResena;

    public ResenaProducto(Cliente cliente, Producto producto, Integer calificacion) {
        this.cliente = cliente;
        this.producto = producto;
        this.calificacion = calificacion;
        this.fechaResena = LocalDateTime.now();
    }

}
