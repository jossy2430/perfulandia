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
@Table(name = "factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFactura;

    @OneToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    @Column(nullable = false)
    private LocalDateTime fechaEmision;

    @Column(length = 50)
    private String tipo;

    @Column(nullable = false)
    private Integer total;

}
