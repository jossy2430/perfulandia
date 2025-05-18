package com.perfulandia.programacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perfulandia.programacion.model.Pedido;
import com.perfulandia.programacion.repository.PedidoRepository;

@Service
@Transactional
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    public Pedido finById(long idPedido){
        return pedidoRepository.findById(idPedido).get();
    }

    public Pedido save(Pedido pedido){
        if (pedido.getIdPedido() == null) {
        }
        return pedidoRepository.save(pedido);
    }

    public void delete(Long idPedido){
        pedidoRepository.deleteById(idPedido);
    }
}
