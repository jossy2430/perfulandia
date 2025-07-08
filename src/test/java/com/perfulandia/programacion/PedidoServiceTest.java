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
import com.perfulandia.programacion.model.DetallePedido;
import com.perfulandia.programacion.model.Pedido;
import com.perfulandia.programacion.repository.PedidoRepository;
import com.perfulandia.programacion.service.PedidoService;

@SpringBootTest
public class PedidoServiceTest {

    @Autowired
    private PedidoService pedidoService;

    @MockitoBean
    private PedidoRepository pedidoRepository;

    @Test
    public void testFindAll() {
        Cliente cliente = new Cliente("Juan ana","gomes gomes", "Dirección", "juan.perez@gmail.com", "123456", LocalDateTime.now(), 987654321);
        DetallePedido detallePedido = new DetallePedido(null, null, null, 2, 200.0, null, 200.0);
        Pedido pedido = new Pedido(cliente, detallePedido);

        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

        List<Pedido> pedidos = pedidoService.findAll();

        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        assertEquals(cliente.getNombres(), pedidos.get(0).getCliente().getNombres());
    }

    @Test
    public void testFindById() {
        Cliente cliente = new Cliente("Juan ana","gomes gomes", "Dirección", "juan.perez@gmail.com", "123456", LocalDateTime.now(), 987654321);
        DetallePedido detallePedido = new DetallePedido(null, null, null, 2, 200.0, null, 200.0);
        Pedido pedido = new Pedido(cliente, detallePedido);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido foundPedido = pedidoService.finById(1L);

        assertNotNull(foundPedido);
        assertEquals(cliente.getNombres(), foundPedido.getCliente().getNombres());
    }

    @Test
    public void testSave() {
        Cliente cliente = new Cliente("Juan ana","gomes gomes", "Dirección", "juan.perez@gmail.com", "123456", LocalDateTime.now(), 987654321);
        DetallePedido detallePedido = new DetallePedido(null, null, null, 2, 200.0, null, 200.0);
        Pedido pedido = new Pedido(cliente, detallePedido);

        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido savedPedido = pedidoService.save(pedido);

        assertNotNull(savedPedido);
        assertEquals(cliente.getNombres(), savedPedido.getCliente().getNombres());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(pedidoRepository).deleteById(1L);

        pedidoService.delete(1L);

        verify(pedidoRepository, times(1)).deleteById(1L);
    }
}