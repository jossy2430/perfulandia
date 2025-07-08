package com.perfulandia.programacion.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.programacion.model.ResenaProducto;
import com.perfulandia.programacion.repository.ResenaProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResenaProductoService {
    @Autowired
    private ResenaProductoRepository resenaProductoRepository;

    public List<ResenaProducto> findAll(){
        return resenaProductoRepository.findAll();
    }

    public ResenaProducto findById(long idResena){
        return resenaProductoRepository.findById(idResena).get();
    }

    public ResenaProducto save(ResenaProducto resenaProducto){
        if (resenaProducto.getFechaResena() == null) {
            resenaProducto.setFechaResena(LocalDateTime.now());
        }
        return resenaProductoRepository.save(resenaProducto);
    }

    public void delete(Long idResena){
        resenaProductoRepository.deleteById(idResena);
    }

}
