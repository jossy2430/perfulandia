package com.perfulandia.programacion.service;

import java.time.LocalDateTime;
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
        if (soporte.getIdSoporte() == null) {
            soporte.setFechaCreacion(LocalDateTime.now());
        }
        return soporteRepository.save(soporte);
    }

    public  void delete(Long idSoporte){
        soporteRepository.deleteById(idSoporte);
    }

}
