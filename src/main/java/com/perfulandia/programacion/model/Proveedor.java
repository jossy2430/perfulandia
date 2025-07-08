package com.perfulandia.programacion.model;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProveedor;

    @Column(nullable = false, length = 100)
    private String nombreProveedor;

    @Column(length = 100)
    private String correo;

    @Column(columnDefinition = "INT(9)")
    private Integer telefonoProveedor;

    public Proveedor(String nombreProveedor, String correo, Integer telefonoProveedor) {
        this.nombreProveedor = nombreProveedor;
        this.correo = correo;
        this.telefonoProveedor = telefonoProveedor;
    }

}
