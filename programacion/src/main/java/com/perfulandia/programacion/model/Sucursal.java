package com.perfulandia.programacion.model;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sucursal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSucursal;

    @Column(nullable = false, length = 50)
    private String nombreSucursal;

    @Column(nullable = false, length = 100)
    private String direccion;

    public Sucursal(String nombreSucursal, String direccion) {
        this.nombreSucursal = nombreSucursal;
        this.direccion = direccion;
    }

}
