package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.Factura;

import feign.Param;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long>{
    //jpql
    @Query("SELECT id FROM Factura id WHERE id.idFactura = :idFactura")
    List<Factura> buscarPorId(@Param("idFactura") Integer idFactura);

}
