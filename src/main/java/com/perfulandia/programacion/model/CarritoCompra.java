package com.perfulandia.programacion.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrito_compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCarrito;

    @ManyToMany
    @JoinTable(
        name = "carrito_producto",
        joinColumns = @JoinColumn(name = "idCarrito"),
        inverseJoinColumns = @JoinColumn(name = "idProducto")
    )
    private List<Producto> productos;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private Double total;

    public CarritoCompra(List<Producto> productos, Cliente cliente) {
        this.productos = productos;
        this.cliente = cliente;
        this.fechaCreacion = LocalDateTime.now();
        this.total = 0.0;
    }
}