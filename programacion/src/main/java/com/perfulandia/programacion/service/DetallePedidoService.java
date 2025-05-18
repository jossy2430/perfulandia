package com.perfulandia.programacion.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.programacion.model.CuponDescuento;
import com.perfulandia.programacion.model.DetallePedido;
import com.perfulandia.programacion.model.Producto;
import com.perfulandia.programacion.repository.CuponDescuentoRepository;
import com.perfulandia.programacion.repository.DetallePedidoRepository;
import com.perfulandia.programacion.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetallePedidoService {
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CuponDescuentoRepository cuponDescuentoRepository;

    public List<DetallePedido> findAll(){
        return detallePedidoRepository.findAll();
    }

    public DetallePedido findById(long idDetalle){
        return detallePedidoRepository.findById(idDetalle).orElse(null);
    }

    public DetallePedido save(DetallePedido detallePedido){
        if (detallePedido.getIdDetalle() == null) {
            detallePedido.setFecha(LocalDateTime.now());
        }

        //vamos a obtener el producto para calcular el pediro
        Producto producto = productoRepository.findById(detallePedido.getProducto().getIdProducto()).orElse(null);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado con ID: "+ detallePedido.getProducto().getIdProducto());
        }

        //disminuir el stok
        int nuevaCantidadStock = producto.getStock() - detallePedido.getCantidad();
        if (nuevaCantidadStock <0) {
            throw new RuntimeException("Sin stock para el producto con el ID :"+ producto.getIdProducto());
        }
        producto.setStock(nuevaCantidadStock);
        productoRepository.save(producto);

        //calcular total sin descuento
        double totalSinDescuento = producto.getPrecio() * detallePedido.getCantidad();
        detallePedido.setTotalSinDescuento(totalSinDescuento);

        //Aplicar descuento si hay cupón
        CuponDescuento cupon = detallePedido.getCuponAplicado();
        if (cupon != null && cupon.getCodigo() != null) {
            List<CuponDescuento> cuponesEncontrados = cuponDescuentoRepository.buscarPorCodigo(cupon.getCodigo());
            CuponDescuento cuponAplicado = cuponesEncontrados.isEmpty() ? null : cuponesEncontrados.get(0);
            if ( cuponAplicado != null && cuponAplicado.getPorcentaje() != null) {
                double descuento = totalSinDescuento * (cuponAplicado.getPorcentaje()/100.0);
                detallePedido.setTotalConDescuento(totalSinDescuento - descuento);
            }else{
                detallePedido.setTotalConDescuento(totalSinDescuento);//no e aplica descuento
            }
        } else{
            detallePedido.setTotalConDescuento(totalSinDescuento);// no hay cupón
        }
        return detallePedidoRepository.save(detallePedido);
    }

    public void delete(long idDetalle){
        detallePedidoRepository.deleteById(idDetalle);
    }

}
