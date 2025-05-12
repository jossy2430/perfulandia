package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.Soporte;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Long>{
    //jpql
    @Query("SELECT id FROM Soporte id WHERE id.idSoporte = :idSoporte")
    List<Soporte> buscarPorId(@Param("idSoporte")Long idSoporte);

}
