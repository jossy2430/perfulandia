package com.perfulandia.programacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.programacion.model.CuponDescuento;
import com.perfulandia.programacion.repository.CuponDescuentoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CuponDescuentoService {
    @Autowired
    private CuponDescuentoRepository cuponDescuentoRepository;

    public List<CuponDescuento> findAll(){
        return cuponDescuentoRepository.findAll();
    }

    public CuponDescuento findById(String codigo ){
        return cuponDescuentoRepository.findById(codigo).get();
    }

    public CuponDescuento save(CuponDescuento cuponDescuento){
        return cuponDescuentoRepository.save(cuponDescuento);
    }

    public void delete(String codigo){
        cuponDescuentoRepository.deleteById(codigo);
    }
}
