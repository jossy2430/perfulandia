package com.perfulandia.programacion.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.programacion.model.CarritoCompra;
import com.perfulandia.programacion.model.Producto;
import com.perfulandia.programacion.repository.CarritoCompraRepository;
import com.perfulandia.programacion.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CarritoCompraService {
    @Autowired
    private CarritoCompraRepository carritoCompraRepositorio;

    @Autowired
    private ProductoRepository productoRepository;

    public List<CarritoCompra> findAll() {
        return carritoCompraRepositorio.findAll();
    }

    public CarritoCompra findById(Integer idCarrito) {
        return carritoCompraRepositorio.findById(idCarrito)
            .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado con ID: " + idCarrito));
    }

    public CarritoCompra save(CarritoCompra carritoCompra) {
        if (carritoCompra.getIdCarrito() == null) {
            carritoCompra.setFechaCreacion(LocalDateTime.now());
        }
        calcularTotal(carritoCompra, 0.0); 
        return carritoCompraRepositorio.save(carritoCompra);
    }

    public void delete(Integer idCarrito) {
        carritoCompraRepositorio.deleteById(idCarrito);
    }

    public void agregarProducto(CarritoCompra carritoCompra, Integer idProducto, Integer cantidad, Double descuentoPorcentaje) {
        Producto producto = productoRepository.findById(idProducto)
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + idProducto));
        
        for (int i = 0; i < cantidad; i++) {
            carritoCompra.getProductos().add(producto);
        }
        calcularTotal(carritoCompra, descuentoPorcentaje);
        carritoCompraRepositorio.save(carritoCompra);
    }

    private void calcularTotal(CarritoCompra carritoCompra, Double descuentoPorcentaje) {
        if (carritoCompra.getProductos() == null || carritoCompra.getProductos().isEmpty()) {
            carritoCompra.setTotal(0.0); // Si no hay productos, el total es 0
            return;
        }

        double totalSinDescuento = carritoCompra.getProductos()
            .stream()
            .mapToDouble(Producto::getPrecio)
            .sum(); // Calcular el total usando streams

        double descuento = 0;
        if (descuentoPorcentaje != null && descuentoPorcentaje >= 0) {
            descuento = totalSinDescuento * (descuentoPorcentaje / 100.0);
        }

        carritoCompra.setTotal(totalSinDescuento - descuento); // Aplicar descuento
    }
}