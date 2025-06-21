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

import com.perfulandia.programacion.model.Empleado;
import com.perfulandia.programacion.model.Sucursal;
import com.perfulandia.programacion.repository.EmpleadoRepository;
import com.perfulandia.programacion.service.EmpleadoService;

@SpringBootTest
public class EmpleadoServiceTest {

    // incluye el servicio de empleado para ser probador
    @Autowired
    private EmpleadoService empleadoService;

    // crear un mock del repositorio de empleado para simular su comportamiento
    @MockitoBean
    private EmpleadoRepository empleadoRepository;

    @Test
    public void testFindAll(){
        //Se crea un objeto ficticio de la sucursal
        Sucursal sucursal = new Sucursal("Sucursal estacion central","estacion 123");
        //Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un Empleado.
        when(empleadoRepository.findAll()).thenReturn(List.of(new Empleado("19.564.567-k","Alan Ortega","123456","vendedor",sucursal)));

        //LLamar al método findAll() del servicio empleado.
        List<Empleado> empleados = empleadoService.findAll();

        //Verificar que la lista devuelta no sea nula y contenga exactamente un empleado.
        assertNotNull(empleados);
        assertEquals(1, empleados.size());
    }

    @Test
    public void testFindById(){
        //Se crea un objeto ficticio de la sucursal
        Sucursal sucursal = new Sucursal("Sucursal estacion central","estacion 123");
        //El id del empleado que se desea buscar
        String rut = "19.564.567-k";
        Empleado empleado = new Empleado("19.564.567-k","Alan Ortega","123456","vendedor",sucursal);

        //Define el comportamiento del mock: cuando se llame a findById() con "1", devuelve un empleado.
        when(empleadoRepository.buscarPorRut(rut)).thenReturn(Optional.of(empleado));

        //Llamar al método findById() del servicio.
        Empleado found =  empleadoService.findById(rut);

        //Verifica que el empleado devuelto no sea nulo y que su rut coincida con el rut esperado.
        assertNotNull(found);
        assertEquals(rut, found.getRut());
    }

    @Test
    public void testSave(){
        //Se crea un objeto ficticio de la sucursal
        Sucursal sucursal = new Sucursal("Sucursal estacion central","estacion 123");

        Empleado empleado = new Empleado("29.564.567-k","jorge Ortega","123456","vendedor",sucursal);

        //Define el comportamiento del mock: cuando se llame a save(), devuelve un empleado proporcionado.
        when(empleadoRepository.save(empleado)).thenReturn(empleado);

        //llamar al método save() del servicio empleado.
        Empleado saved = empleadoService.save(empleado);

        //verifica que el empleado guardado no sea nulo y que su nombre coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("jorge Ortega", saved.getNombreEmpleado());
    }

    @Test
    public void testDeleteByRut(){
        String rut = "29.564.567-k";

        // Define el comportamiento del mock: cuando se llame a deleteByRut() con "29.564.567-k", no hace nada.
        doNothing().when(empleadoRepository).deleteByRut(rut);

        // llamar al método deleteByRut() del servicio empleado.
        empleadoService.delete(rut);

        // Verifica que se haya eliminado un empleado.
        verify(empleadoRepository, times(1)).buscarPorRut(rut);
    }

}
