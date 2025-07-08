package com.perfulandia.programacion.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idDetalle", nullable = false)
    private DetallePedido detallePedido;

    public Pedido(Cliente cliente, DetallePedido detallePedido) {
        this.cliente = cliente;
        this.detallePedido = detallePedido;
    }

}
