package com.perfulandia.programacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.Empleado;

import feign.Param;
import jakarta.transaction.Transactional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    //JPQL
    @Query("SELECT e FROM Empleado e WHERE e.rut = :rut")
    Optional<Empleado> buscarPorRut(@Param("rut") String rut);

    // JPQL para eliminar por rut
    @Modifying
    @Transactional
    @Query("DELETE FROM Empleado e WHERE e.rut = :rut")
    void deleteByRut(@Param("rut") String rut);
    
} 
