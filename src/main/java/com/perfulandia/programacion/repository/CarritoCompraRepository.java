package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Importación correcta
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.CarritoCompra;

@Repository
public interface CarritoCompraRepository extends JpaRepository<CarritoCompra, Integer> {
    // Usando JPQL
    @Query("SELECT c FROM CarritoCompra c WHERE c.cliente.idCliente = :idCliente")
    List<CarritoCompra> buscarPorCliente(@Param("idCliente") Integer idCliente);
}