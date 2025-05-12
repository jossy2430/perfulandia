package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    //jpql
    @Query("SELECT id FROM Pedido id WHERE id.idPedido = :idPedido")
    List<Pedido> buscarPorId(@Param("idPedido")Long idPedido);

}
