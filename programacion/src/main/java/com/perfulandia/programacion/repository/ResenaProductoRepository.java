package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.ResenaProducto;

import feign.Param;

@Repository
public interface ResenaProductoRepository extends JpaRepository<ResenaProducto, Long>{
    //jpql
    @Query("SELECT id FROM ResenaProducto id WHERE id.idResena = :idResena")
    List<ResenaProducto> buscarPorId(@Param("idResena")Long idResena);
}
