package com.perfulandia.programacion.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.programacion.model.Cliente;
import com.perfulandia.programacion.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente findById(Integer idCliente){
        return clienteRepository.findById(idCliente).orElse(null);
    }

    public Cliente save(Cliente cliente){
        if (cliente.getIdCliente() == null) {
            cliente.setFechaRegistro(LocalDateTime.now());
        }
        return clienteRepository.save(cliente);
    }

    public void delete(Integer idCliente){
        clienteRepository.deleteById(idCliente);
    }
}
