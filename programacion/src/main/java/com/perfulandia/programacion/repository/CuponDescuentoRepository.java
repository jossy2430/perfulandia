package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param; 

import com.perfulandia.programacion.model.CuponDescuento;

@Repository
public interface CuponDescuentoRepository extends JpaRepository<CuponDescuento, String> {

    // usando JPQL correctamente
    @Query("SELECT cod FROM CuponDescuento cod WHERE cod.codigo = :codigo")
    List<CuponDescuento> buscarPorCodigo(@Param("codigo") String codigo);
}
