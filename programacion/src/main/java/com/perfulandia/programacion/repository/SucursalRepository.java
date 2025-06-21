package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.Sucursal;

import feign.Param;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
    //JPL
    @Query("SELECT s FROM Sucursal s WHERE s.nombreSucursal= :nombreSucursal")
    List<Sucursal> buscarPorNombre(@Param("nombreSucursal") String nombreSucursal);
    
}