package com.perfulandia.programacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.programacion.model.CuponDescuento;
import com.perfulandia.programacion.model.DetallePedido;
import com.perfulandia.programacion.model.Factura;
import com.perfulandia.programacion.model.Producto;
import com.perfulandia.programacion.model.Proveedor;
import com.perfulandia.programacion.repository.FacturaRepository;
import com.perfulandia.programacion.repository.ProductoRepository;
import com.perfulandia.programacion.service.FacturaService;

@SpringBootTest
public class FacturaServiceTest {

    @Autowired
    private FacturaService facturaService;

    @MockitoBean
    private FacturaRepository facturaRepository;

    @MockitoBean
    private ProductoRepository productoRepository;

    @Test
    public void testFindAll() {
        Proveedor proveedor = new Proveedor(null, "Proveedor Test", "proveedor@test.com", 123456789);
        Producto producto = new Producto(null, "Producto Test", "Descripción", 1000, 10, "Categoría");
        Factura factura = new Factura();

        factura.setFechaEmision(LocalDateTime.now());
        factura.setProveedor(proveedor);
        factura.setProductos(List.of(producto));
        factura.setTotal(1000);

        when(facturaRepository.findAll()).thenReturn(List.of(factura));

        List<Factura> facturas = facturaService.findAll();

        assertNotNull(facturas);
        assertEquals(1, facturas.size());
        assertEquals(1000, facturas.get(0).getTotal());
    }

    @Test
    public void testFindById() {
        Proveedor proveedor = new Proveedor(null, "Proveedor Test", "proveedor@test.com", 123456789);
        Producto producto = new Producto(null, "Producto Test", "Descripción", 1000, 10, "Categoría");
        Factura factura = new Factura();

        factura.setFechaEmision(LocalDateTime.now());
        factura.setProveedor(proveedor);
        factura.setProductos(List.of(producto));
        factura.setTotal(1000);

        when(facturaRepository.findById(1L)).thenReturn(Optional.of(factura));

        Factura foundFactura = facturaService.findById(1L);

        assertNotNull(foundFactura);
        assertEquals(1000, foundFactura.getTotal());
    }

    @Test
    public void testSave() {
        // Crear un proveedor y un producto
        Proveedor proveedor = new Proveedor("Proveedor Test", "proveedor@test.com", 123456789);
        Producto producto = new Producto("Producto Test", "Descripción", 10000, 10, "Categoría");
        producto.setIdProducto(1); // Simular que el ID fue asignado automáticamente

        // Crear una factura
        Factura factura = new Factura();
        factura.setProveedor(proveedor);
        factura.setProductos(List.of(producto));
        factura.setFechaEmision(LocalDateTime.now());

        // Simular el comportamiento de los repositorios
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto)); // Simular búsqueda de producto
        when(facturaRepository.save(any(Factura.class))).thenReturn(factura); // Simular guardado de factura

        // Llamar al método save del servicio
        Factura savedFactura = facturaService.save(factura);

        // Verificar los resultados
        assertNotNull(savedFactura);
        assertEquals(10000, savedFactura.getTotal()); // Total basado en el precio del producto
    }

    @Test
    public void testDeleteById() {
        doNothing().when(facturaRepository).deleteById(1L);

        facturaService.delete(1L);

        verify(facturaRepository, times(1)).deleteById(1L);
    }
}