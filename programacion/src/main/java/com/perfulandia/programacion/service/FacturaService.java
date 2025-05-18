package com.perfulandia.programacion.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perfulandia.programacion.model.Factura;
import com.perfulandia.programacion.model.Producto;
import com.perfulandia.programacion.repository.FacturaRepository;
import com.perfulandia.programacion.repository.ProductoRepository;

@Service
@Transactional
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Factura> findAll(){
        return facturaRepository.findAll();
    }

    public Factura findById(long idFactura){
        return facturaRepository.findById(idFactura).get();
    }

    public Factura save(Factura factura){
        if (factura.getIdFactura() == null) {
            factura.setFechaEmision(LocalDateTime.now());
        }

        //obtener el producto asociado a la factura
        Producto producto = productoRepository.findById(factura.getProducto().getIdProducto()).orElse(null);
        if (producto != null) {
            //si la factura representa la venta de un producto con cantidad 1 
            factura.setTotal((int) producto.getPrecio());
        } else{
            factura.setTotal(0);
        }
        return facturaRepository.save(factura);
    }

    public void delete(Long idFactura){
        facturaRepository.deleteById(idFactura);
    }

}
