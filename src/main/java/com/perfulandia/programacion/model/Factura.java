package com.perfulandia.programacion.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFactura;

    @ManyToMany
    @JoinTable(
        name = "factura_producto",
        joinColumns = @JoinColumn(name = "idFactura"),
        inverseJoinColumns = @JoinColumn(name = "idProducto")
    )
    private List<Producto> productos;

    @Column(nullable = false)
    private LocalDateTime fechaEmision;

    @Column(nullable = false)
    private Integer total;

    @ManyToOne
    @JoinColumn(name = "idProveedor")
    private Proveedor proveedor;

    public Factura(List<Producto> productos, Integer total, Proveedor proveedor) {
        this.productos = productos;
        this.fechaEmision = LocalDateTime.now();
        this.total = total;
        this.proveedor = proveedor;
    }

}
