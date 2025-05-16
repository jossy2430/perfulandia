package com.perfulandia.programacion.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.programacion.model.DetallePedido;
import com.perfulandia.programacion.repository.DetallePedidoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetallePedidoService {
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<DetallePedido> findAll(){
        return detallePedidoRepository.findAll();
    }

    public DetallePedido findById(long idDetalle){
        return detallePedidoRepository.findById(idDetalle).get();
    }

    public DetallePedido save(DetallePedido detallePedido){
        if (detallePedido.getIdDetalle() == null) {
            detallePedido.setFecha(LocalDateTime.now());
        }
        return detallePedidoRepository.save(detallePedido);
    }

    public void delete(long idDetalle){
        detallePedidoRepository.deleteById(idDetalle);
    }

}
