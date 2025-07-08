package com.perfulandia.programacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.programacion.model.Sucursal;
import com.perfulandia.programacion.repository.SucursalRepository;
import com.perfulandia.programacion.service.SucursalService;

@SpringBootTest
public class SucursalServiceTest {
    //inyectar el servicio de Sucursal para ser probado
    @Autowired
    private SucursalService sucursalService;

    //crear un mockito del repositorio de sucursal para simular su comportamiento
    @MockitoBean
    private SucursalRepository sucursalRepository;

    @Test
    public void testFindAll(){
        //Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con una Sucursal.
        when(sucursalRepository.findAll()).thenReturn(List.of(new Sucursal("Conce","sur 123")));

        //Llamar al método findAll() del servicio sucursal.
        List<Sucursal> sucursales = sucursalService.findAll();

        //Verificar que la lista devuelta no sea nula y contenga exactamente una sucursal.
        assertNotNull(sucursales);
        assertEquals(1, sucursales.size());
    }

    @Test
    public void testFindById(){
        //El ID de la sucursal que se desea buscar
        Integer idSucursal = 1;
        Sucursal sucursal = new Sucursal(idSucursal, "Conce", "sur 123");

        //Define el comportamiento del mock: cuando se llame a findById() con "1", devuelve una sucursal.
        when(sucursalRepository.findById(idSucursal)).thenReturn(Optional.of(sucursal));

        //Llamar al método findById() del servicio.
        Sucursal found = sucursalService.findById(idSucursal);

        //verificar que la sucursal devuelta no sea nula y que su ID coincida con el ID esperado.
        assertNotNull(found);
        assertEquals(idSucursal, found.getIdSucursal());
    }

    @Test
    public void testSave(){
        Sucursal sucursal = new Sucursal("valpo","del mar 22");

        //Define el comportamiento del mock: cuando se llame a save(), devuelve la sucursal guardada.
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);

        //Llamar al método save() del servicio.
        Sucursal saved = sucursalService.save(sucursal);

        //verificar que la sucursal guardada no sea nula y que su nombre coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("valpo", saved.getNombreSucursal());
    }

    @Test
    public void testDeleteById(){
        Integer idSucursal = 1;

        //define el comportamiento del mock: cuando se llame a deleteById() con "1", no hace nada.
        doNothing().when(sucursalRepository).deleteById(idSucursal);

        //Llamar al método deleteById() del servicio sucursal.
        sucursalService.delete(idSucursal);

        //verificar que el método deleteById() haya eliminado un sucursal.
        verify(sucursalRepository, times(1)).deleteById(idSucursal);   
    }

}
