package com.perfulandia.programacion.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "descuentoCupon")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuponDescuento {
    @Id
    @Column(length = 20)
    private String codigo;

    @Column(nullable = false)
    private Double porcentaje;

    private LocalDate fechaInicio;
    
    private LocalDate fechaExpiracion;

}
