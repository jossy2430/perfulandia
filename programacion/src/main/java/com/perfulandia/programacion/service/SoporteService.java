package com.perfulandia.programacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.programacion.model.Soporte;
import com.perfulandia.programacion.repository.SoporteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SoporteService {
    @Autowired
    private SoporteRepository soporteRepository;

    public List<Soporte> findAll(){
        return soporteRepository.findAll();
    }

    public Soporte findById(long idSoporte){
        return soporteRepository.findById(idSoporte).get();
    }

    public Soporte save(Soporte soporte){
        return soporteRepository.save(soporte);
    }

    public  void delete(Long idSoporte){
        soporteRepository.deleteById(idSoporte);
    }

}
