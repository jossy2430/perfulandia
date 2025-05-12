package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.DetallePedido;

import feign.Param;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long>{
    //utilizando  jpql
    @Query("SELECT id FROM DetallePedido id WHERE id.idDetalle = :idDetalle")
    List<DetallePedido> buscarPorId(@Param("idDetalle")Long idDetalle);
}
