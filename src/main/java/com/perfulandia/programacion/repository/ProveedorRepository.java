package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>{
    //jpql
    @Query("SELECT p FROM Proveedor p WHERE p.nombreProveedor = :nombreProveedor")
List<Proveedor> buscarPorNombre(@Param("nombreProveedor") String nombreProveedor);

}
