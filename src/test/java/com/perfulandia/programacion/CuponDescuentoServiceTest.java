package com.perfulandia.programacion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.programacion.model.CuponDescuento;
import com.perfulandia.programacion.repository.CuponDescuentoRepository;
import com.perfulandia.programacion.service.CuponDescuentoService;

@SpringBootTest
public class CuponDescuentoServiceTest {

    @Autowired
    private CuponDescuentoService cuponDescuentoService;

    @MockitoBean
    private CuponDescuentoRepository cuponDescuentoRepository;

    @Test
    public void testFindAll() {
        CuponDescuento cuponDescuento = new CuponDescuento("TEST123", 20.0, LocalDate.now(), LocalDate.now().plusDays(10));

        when(cuponDescuentoRepository.findAll()).thenReturn(List.of(cuponDescuento));

        List<CuponDescuento> cupones = cuponDescuentoService.findAll();

        assertNotNull(cupones);
        assertEquals(1, cupones.size());
        assertEquals("TEST123", cupones.get(0).getCodigo());
    }

    @Test
    public void testFindById() {
        CuponDescuento cuponDescuento = new CuponDescuento("TEST123", 20.0, LocalDate.now(), LocalDate.now().plusDays(10));

        when(cuponDescuentoRepository.findById("TEST123")).thenReturn(Optional.of(cuponDescuento));

        CuponDescuento foundCupon = cuponDescuentoService.findById("TEST123");

        assertNotNull(foundCupon);
        assertEquals("TEST123", foundCupon.getCodigo());
    }

    @Test
    public void testSave() {
        CuponDescuento cuponDescuento = new CuponDescuento("TEST123", 20.0, LocalDate.now(), LocalDate.now().plusDays(10));

        when(cuponDescuentoRepository.save(cuponDescuento)).thenReturn(cuponDescuento);

        CuponDescuento savedCupon = cuponDescuentoService.save(cuponDescuento);

        assertNotNull(savedCupon);
        assertEquals("TEST123", savedCupon.getCodigo());
    }

    @Test
    public void testDelete() {
        doNothing().when(cuponDescuentoRepository).deleteById("TEST123");

        cuponDescuentoService.delete("TEST123");

        verify(cuponDescuentoRepository, times(1)).deleteById("TEST123");
    }
}