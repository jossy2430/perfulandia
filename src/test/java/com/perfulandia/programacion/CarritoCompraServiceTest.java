package com.perfulandia.programacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.perfulandia.programacion.model.CarritoCompra;
import com.perfulandia.programacion.repository.CarritoCompraRepository;
import com.perfulandia.programacion.service.CarritoCompraService;

@SpringBootTest
public class CarritoCompraServiceTest {
    @Autowired
    private CarritoCompraService carritoCompraService;

    @MockitoBean
    private CarritoCompraRepository carritoCompraRepositorio;

    @Test
    public void testFindAll() {
        // Mock: cuando se llame a findAll(), devuelve una lista con un CarritoCompra.
        when(carritoCompraRepositorio.findAll()).thenReturn(List.of(new CarritoCompra()));

        // Llamar al método findAll() del servicio.
        List<CarritoCompra> carritos = carritoCompraService.findAll();

        // Verificar que la lista devuelta no sea nula y contenga exactamente un carrito.
        assertNotNull(carritos);
        assertEquals(1, carritos.size());
    }

    @Test
    public void testFindById() {
        Integer idCarrito = 1;
        CarritoCompra carrito = new CarritoCompra();
        carrito.setIdCarrito(idCarrito);

        // Mock: cuando se llame a findById() con "1", devuelve un carrito.
        when(carritoCompraRepositorio.findById(idCarrito)).thenReturn(Optional.of(carrito));

        // Llamar al método findById() del servicio.
        CarritoCompra found = carritoCompraService.findById(idCarrito);

        // Verificar que el carrito devuelto no sea nulo y que su ID coincida con el esperado.
        assertNotNull(found);
        assertEquals(idCarrito, found.getIdCarrito());
    }

    @Test
    public void testSave() {
        CarritoCompra carrito = new CarritoCompra();
        carrito.setIdCarrito(1);
        carrito.setFechaCreacion(LocalDateTime.now());

        // Mock: cuando se llame a save(), devuelve el carrito proporcionado.
        when(carritoCompraRepositorio.save(carrito)).thenReturn(carrito);

        // Llamar al método save() del servicio.
        CarritoCompra saved = carritoCompraService.save(carrito);

        // Verificar que el carrito guardado no sea nulo y que su ID coincida con el esperado.
        assertNotNull(saved);
        assertEquals(1, saved.getIdCarrito());
    }

    @Test
    public void testDeleteById() {
        Integer idCarrito = 1;

        // Mock: cuando se llame a deleteById() con "1", no hace nada.
        doNothing().when(carritoCompraRepositorio).deleteById(idCarrito);

        // Llamar al método deleteById() del servicio.
        carritoCompraService.delete(idCarrito);

        // Verificar que el método deleteById() del repositorio se haya llamado exactamente una vez.
        verify(carritoCompraRepositorio, times(1)).deleteById(idCarrito);
    }
}
