package com.perfulandia.programacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.programacion.model.CuponDescuento;
import com.perfulandia.programacion.model.DetallePedido;
import com.perfulandia.programacion.model.Producto;
import com.perfulandia.programacion.repository.DetallePedidoRepository;
import com.perfulandia.programacion.repository.ProductoRepository;
import com.perfulandia.programacion.repository.CuponDescuentoRepository;
import com.perfulandia.programacion.service.DetallePedidoService;

@SpringBootTest
public class DetallePedidoServiceTest {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @MockitoBean
    private DetallePedidoRepository detallePedidoRepository;

    @MockitoBean
    private ProductoRepository productoRepository;

    @MockitoBean
    private CuponDescuentoRepository cuponDescuentoRepository;

    @Test
    public void testFindAll() {
        DetallePedido detallePedido = new DetallePedido(null, LocalDateTime.now(), new Producto("Producto Test", "Descripción", 1000, 10, "Categoría"), 2, 200.0, null, 200.0);

        when(detallePedidoRepository.findAll()).thenReturn(List.of(detallePedido));

        List<DetallePedido> detalles = detallePedidoService.findAll();

        assertNotNull(detalles);
        assertEquals(1, detalles.size());
        assertEquals(2, detalles.get(0).getCantidad());
    }

    @Test
    public void testFindById() {
        DetallePedido detallePedido = new DetallePedido(null, LocalDateTime.now(), new Producto("Producto Test", "Descripción", 1000, 10, "Categoría"), 2, 200.0, null, 200.0);

        when(detallePedidoRepository.findById(1L)).thenReturn(Optional.of(detallePedido));

        DetallePedido foundDetalle = detallePedidoService.findById(1L);

        assertNotNull(foundDetalle);
        assertEquals(2, foundDetalle.getCantidad());
    }

    @Test
    public void testSave() {
        // Crear un producto sin asignar manualmente el ID
        Producto producto = new Producto("Producto Test", "Descripción", 10000, 10, "Categoría");
        CuponDescuento cuponDescuento = new CuponDescuento("TEST123", 20.0, null, null);
        DetallePedido detallePedido = new DetallePedido(null, LocalDateTime.now(), producto, 2, null, cuponDescuento, null);

        // Simular el comportamiento de los repositorios
        when(productoRepository.findById(any())).thenReturn(Optional.of(producto)); // Simular búsqueda sin depender del ID
        when(cuponDescuentoRepository.buscarPorCodigo("TEST123")).thenReturn(List.of(cuponDescuento));
        when(detallePedidoRepository.save(any(DetallePedido.class))).thenReturn(detallePedido);

        // Llamar al método save del servicio
        DetallePedido savedDetalle = detallePedidoService.save(detallePedido);

        // Verificar los resultados
        assertNotNull(savedDetalle);
        assertEquals(16000.0, savedDetalle.getTotalConDescuento()); // Total con descuento aplicado
    }

    @Test
    public void testDeleteById() {
        doNothing().when(detallePedidoRepository).deleteById(1L);

        detallePedidoService.delete(1L);

        verify(detallePedidoRepository, times(1)).deleteById(1L);
    }
}