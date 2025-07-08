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

import com.perfulandia.programacion.model.Cliente;
import com.perfulandia.programacion.model.Soporte;
import com.perfulandia.programacion.repository.SoporteRepository;
import com.perfulandia.programacion.service.SoporteService;

@SpringBootTest
public class SoporteServiceTest {

    @Autowired
    private SoporteService soporteService;

    @MockitoBean
    private SoporteRepository soporteRepository;

    @Test
    public void testFindAll() {
        Cliente cliente = new Cliente("Juan ana","gomes gomes", "Dirección", "juan.perez@gmail.com", "123456", LocalDateTime.now(), 987654321);
        Soporte soporte = new Soporte(cliente, "Asunto Test", "Descripción Test", "Abierto");

        when(soporteRepository.findAll()).thenReturn(List.of(soporte));

        List<Soporte> soportes = soporteService.findAll();

        assertNotNull(soportes);
        assertEquals(1, soportes.size());
        assertEquals("Asunto Test", soportes.get(0).getAsunto());
    }

    @Test
    public void testFindById() {
        Cliente cliente = new Cliente("Juan ana","gomes gomes", "Dirección", "juan.perez@gmail.com", "123456", LocalDateTime.now(), 987654321);
        Soporte soporte = new Soporte(cliente, "Asunto Test", "Descripción Test", "Abierto");

        when(soporteRepository.findById(1L)).thenReturn(Optional.of(soporte));

        Soporte foundSoporte = soporteService.findById(1L);

        assertNotNull(foundSoporte);
        assertEquals("Asunto Test", foundSoporte.getAsunto());
    }

    @Test
    public void testSave() {
        Cliente cliente = new Cliente("Juan ana","gomes gomes", "Dirección", "juan.perez@gmail.com", "123456", LocalDateTime.now(), 987654321);
        Soporte soporte = new Soporte(cliente, "Asunto Test", "Descripción Test", "Abierto");

        when(soporteRepository.save(soporte)).thenReturn(soporte);

        Soporte savedSoporte = soporteService.save(soporte);

        assertNotNull(savedSoporte);
        assertEquals("Asunto Test", savedSoporte.getAsunto());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(soporteRepository).deleteById(1L);

        soporteService.delete(1L);

        verify(soporteRepository, times(1)).deleteById(1L);
    }
}