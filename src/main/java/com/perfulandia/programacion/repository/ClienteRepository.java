package com.perfulandia.programacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perfulandia.programacion.model.Cliente;

import feign.Param;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    //usando jpql
    @Query("SELECT c FROM Cliente c WHERE c.apellidos = :apellido")
    List<Cliente> buscarPorApellidos(@Param("apellidos") String apellido);
}   