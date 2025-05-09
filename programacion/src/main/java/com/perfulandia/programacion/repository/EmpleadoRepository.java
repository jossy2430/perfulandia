package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.Empleado;

import feign.Param;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    //JPQL
    @Query("SELECT e FROM Empleado e WHERE e.rut = :rut")
    List<Empleado> buscarPorRut(@Param("rut") String rut);
    
} 
