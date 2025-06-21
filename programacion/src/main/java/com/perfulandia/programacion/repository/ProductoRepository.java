package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.Producto;

import feign.Param;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>  {
    //JPL
    @Query("SELECT p FROM Producto p WHERE p.nombreProducto = :nombre_producto")
    List<Producto> buscarNombreProducto(@Param("nombre_producto") String nombre_producto);
}