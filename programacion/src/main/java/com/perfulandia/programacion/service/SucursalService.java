package com.perfulandia.programacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.programacion.model.Sucursal;
import com.perfulandia.programacion.repository.SucursalRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SucursalService {
    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> findAll(){
        return sucursalRepository.findAll();
    }

    public Sucursal findById(Integer idSucursal){
        return sucursalRepository.findById(idSucursal).get();
    }

    public Sucursal save(Sucursal sucursal){
        return sucursalRepository.save(sucursal);
    }

    public void delete(Integer idSucursal){
        sucursalRepository.deleteById(idSucursal);
    }

}
