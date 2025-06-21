package com.perfulandia.programacion.model;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpleado;

    @Column(nullable = false, length = 12, unique = true)
    private String rut;
    
    @Column(nullable = false, length = 70)
    private String nombreEmpleado;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String rol;

    @ManyToOne
    @JoinColumn(name = "idSucursal", nullable = false)
    private Sucursal sucursal;

    public Empleado(String rut, String nombreEmpleado, String password, String rol, Sucursal sucursal) {
        this.rut = rut;
        this.nombreEmpleado = nombreEmpleado;
        this.password = password;
        this.rol = rol;
        this.sucursal = sucursal;
    }
}
