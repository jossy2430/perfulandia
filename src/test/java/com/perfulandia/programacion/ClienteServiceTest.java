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
import com.perfulandia.programacion.repository.ClienteRepository;
import com.perfulandia.programacion.service.ClienteService;

@SpringBootTest
public class ClienteServiceTest {
    //Inyectar el servicio de Cliente para ser probado
    @Autowired
    private ClienteService clienteService;

    //Crear un mockito del repositorio de cliente para simular su comportamiento
    @MockitoBean
    private ClienteRepository clienteRepository;

    @Test
    public void testFindAll(){
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un Cliente.
        when(clienteRepository.findAll()).thenReturn(List.of(new Cliente("alonzo Antonio","JJ Gonzalez","Calle luis 123, Pudahuel","alonzo.b@gmail.com","123456",LocalDateTime.now(),912345678)));

        //Llamar al método findAll() del servicio cliente.
        List<Cliente> clientes = clienteService.findAll();

        //Verificarl que la lista devuelva no sea nula y contenga exactamente un cliente.
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
    }

    @Test
    public void testFindById(){
        //El ID es del cliente que se desea buscar
        Integer idCliente = 3;
        Cliente cliente = new Cliente(idCliente,"Eleni Yon","YundtNolan","Apt. 708 8429 Katina Spurs, D'Amorechester, AR 46441","beatris.tillman@yahoo.com","8ib3r7p17b14kw",LocalDateTime.now(),854274588);

        // Define el comportamiento del mock: cuando se llame a findById() con "3", devuelve un cliente.
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));

        //Llammar al método findById() del servicio.
        Cliente found = clienteService.findById(idCliente);

        // Verifica que el cliente devuelta no sea nula y que su código coincida con el código esperado.
        assertNotNull(found);
        assertEquals(idCliente, found.getIdCliente());
    }

    @Test
    public void testSave(){
        Cliente cliente = new Cliente(3,"Eleni Yon","YundtNolan","Apt. 708 8429 Katina Spurs, D'Amorechester, AR 46441","beatris.tillman@yahoo.com","8ib3r7p17b14kw",LocalDateTime.now(),854274588);

        //Define el comportamiento del mock: cuando se llame a save(), devuelve un cliente proporcionado.
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        //llamar al método save() del servicio cliente.
        Cliente saved = clienteService.save(cliente);

        // Verifica que el cliente guardada no sea nula y que su nombre coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("Eleni Yon", saved.getNombres());
    }

    @Test
    public void testDeleteById(){
        Integer idCliente = 4;

        // Define el comportamiento del mock: cuando se llame a deleteById() con "4", no hace nada.
        doNothing().when(clienteRepository).deleteById(idCliente);

        // llamar al método deleteById() del servicio cliente.
        clienteService.delete(idCliente);

        // Verifica que el método deleteById() del repositorio se haya llamado exactamente una vez con el código proporcionado.
        verify(clienteRepository, times(1)).deleteById(idCliente);
    }

}
