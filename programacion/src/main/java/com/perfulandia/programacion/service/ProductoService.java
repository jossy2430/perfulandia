package com.perfulandia.programacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perfulandia.programacion.model.Producto;
import com.perfulandia.programacion.repository.ProductoRepository;

@Service
@Transactional
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto findById(Integer idProducto){
        return productoRepository.findById(idProducto).get();
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public void delete(Integer idProducto){
        productoRepository.deleteById(idProducto);
    }
}
