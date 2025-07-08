package com.perfulandia.programacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.programacion.model.Proveedor;
import com.perfulandia.programacion.repository.ProveedorRepository;
import com.perfulandia.programacion.service.ProveedorService;

@SpringBootTest
public class ProveedorServiceTest {

    @Autowired
    private ProveedorService proveedorService;

    @MockitoBean
    private ProveedorRepository proveedorRepository;

    @Test
    public void testFindAll() {
        Proveedor proveedor = new Proveedor("Proveedor Test", "proveedor@test.com", 123456789);

        when(proveedorRepository.findAll()).thenReturn(List.of(proveedor));

        List<Proveedor> proveedores = proveedorService.findAll();

        assertNotNull(proveedores);
        assertEquals(1, proveedores.size());
        assertEquals("Proveedor Test", proveedores.get(0).getNombreProveedor());
    }

    @Test
    public void testFindById() {
        Proveedor proveedor = new Proveedor("Proveedor Test", "proveedor@test.com", 123456789);

        when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedor));

        Proveedor foundProveedor = proveedorService.findById(1);

        assertNotNull(foundProveedor);
        assertEquals("Proveedor Test", foundProveedor.getNombreProveedor());
    }

    @Test
    public void testSave() {
        Proveedor proveedor = new Proveedor("Proveedor Test", "proveedor@test.com", 123456789);

        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedor);

        Proveedor savedProveedor = proveedorService.save(proveedor);

        assertNotNull(savedProveedor);
        assertEquals("Proveedor Test", savedProveedor.getNombreProveedor());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(proveedorRepository).deleteById(1);

        proveedorService.delete(1);

        verify(proveedorRepository, times(1)).deleteById(1);
    }
}