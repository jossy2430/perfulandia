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

import com.perfulandia.programacion.model.Cliente;
import com.perfulandia.programacion.model.Producto;
import com.perfulandia.programacion.model.ResenaProducto;
import com.perfulandia.programacion.repository.ResenaProductoRepository;
import com.perfulandia.programacion.service.ResenaProductoService;

@SpringBootTest
public class ResenaProductoServiceTest {

    @Autowired
    private ResenaProductoService resenaProductoService;

    @MockitoBean
    private ResenaProductoRepository resenaProductoRepository;

    @Test
    public void testFindAll() {
        Cliente cliente = new Cliente("alonzo Antonio","JJ Gonzalez","Calle luis 123, Pudahuel","alonzo.b@gmail.com","123456",LocalDateTime.now(),912345678);
        Producto producto = new Producto("Producto Test", "Descripción", 10000, 10, "Categoría");
        ResenaProducto resenaProducto = new ResenaProducto(cliente, producto, 5);

        when(resenaProductoRepository.findAll()).thenReturn(List.of(resenaProducto));

        List<ResenaProducto> resenas = resenaProductoService.findAll();

        assertNotNull(resenas);
        assertEquals(1, resenas.size());
        assertEquals(5, resenas.get(0).getCalificacion());
    }

    @Test
    public void testFindById() {
        Cliente cliente = new Cliente("alonzo Antonio","JJ Gonzalez","Calle luis 123, Pudahuel","alonzo.b@gmail.com","123456",LocalDateTime.now(),912345678);
        Producto producto = new Producto("Producto Test", "Descripción", 10000, 10, "Categoría");
        ResenaProducto resenaProducto = new ResenaProducto(cliente, producto, 5);

        when(resenaProductoRepository.findById(1L)).thenReturn(Optional.of(resenaProducto));

        ResenaProducto foundResena = resenaProductoService.findById(1L);

        assertNotNull(foundResena);
        assertEquals(5, foundResena.getCalificacion());
    }

    @Test
    public void testSave() {
        Cliente cliente = new Cliente("alonzo Antonio","JJ Gonzalez","Calle luis 123, Pudahuel","alonzo.b@gmail.com","123456",LocalDateTime.now(),912345678);
        Producto producto = new Producto("Producto Test", "Descripción", 10000, 10, "Categoría");
        ResenaProducto resenaProducto = new ResenaProducto(cliente, producto, 5);

        when(resenaProductoRepository.save(any(ResenaProducto.class))).thenReturn(resenaProducto);

        ResenaProducto savedResena = resenaProductoService.save(resenaProducto);

        assertNotNull(savedResena);
        assertEquals(5, savedResena.getCalificacion());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(resenaProductoRepository).deleteById(1L);

        resenaProductoService.delete(1L);

        verify(resenaProductoRepository, times(1)).deleteById(1L);
    }
}