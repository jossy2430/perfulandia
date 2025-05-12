package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.Producto;

import feign.Param;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>  {
    //JPL
    @Query("SELECT nom FROM Producto nom WHERE nom.nombre_producto = :nombre_producto")
    List<Producto> buscarNombreProducto(@Param("nombre_producto")String nombre_producto);
}