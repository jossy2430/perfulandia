package com.perfulandia.programacion.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Double totalSinDescuento;

    @ManyToOne
    @JoinColumn(name = "codigoCupon", referencedColumnName = "codigo")
    private CuponDescuento cuponAplicado;

    @Column(length = 20)
    private Double totalConDescuento;

    public DetallePedido(LocalDateTime fecha, Producto producto, Integer cantidad, Double totalSinDescuento) {
        this.fecha = fecha;
        this.producto = producto;
        this.cantidad = cantidad;
        this.totalSinDescuento = totalSinDescuento;
        this.totalConDescuento = totalSinDescuento; // Inicialmente sin descuento
    }

}
