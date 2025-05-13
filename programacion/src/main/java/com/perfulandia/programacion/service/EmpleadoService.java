package com.perfulandia.programacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.programacion.model.Empleado;
import com.perfulandia.programacion.repository.EmpleadoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> findAll(){
        return empleadoRepository.findAll();
    }

    public Empleado findById(long rut){
        return empleadoRepository.findById(rut).get();
    }

    public Empleado save(Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    public void delete(Long rut){
        empleadoRepository.deleteById(rut);
    }
}
