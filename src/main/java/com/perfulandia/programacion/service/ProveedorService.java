package com.perfulandia.programacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.programacion.model.Proveedor;
import com.perfulandia.programacion.repository.ProveedorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> findAll(){
        return proveedorRepository.findAll();
    }

    public Proveedor findById(Integer idProveedor){
        return proveedorRepository.findById(idProveedor).get();
    } 

    public Proveedor save(Proveedor proveedor){
        return proveedorRepository.save(proveedor);
    }

    public void delete(Integer idProveedor){
        proveedorRepository.deleteById(idProveedor);
    }

}
