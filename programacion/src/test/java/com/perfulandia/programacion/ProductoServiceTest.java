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

import com.perfulandia.programacion.model.Producto;
import com.perfulandia.programacion.repository.ProductoRepository;
import com.perfulandia.programacion.service.ProductoService;

@SpringBootTest
public class ProductoServiceTest {
    //inyectar el servicio de Producto para ser probado
    @Autowired
    private ProductoService productoService;

    //crear un mockito del repositorio de producto para simular su comportamiento
    @MockitoBean
    private ProductoRepository productoRepository;

    @Test
    public void testFindAll(){
        //define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un Producto.
        when(productoRepository.findAll()).thenReturn(List.of(new Producto("teslta","aroma maderado",20000,50,"hombre")));

        //llamar al método findAll() del servicio producto.
        List<Producto> productos = productoService.findAll();

        //verificar que la lista devuelta no sea nula y contenga exactamente un producto.
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindById(){
        //el id del empleado que se desea buscar
        Integer idProducto = 1;
        Producto producto = new Producto(idProducto,"karol","aroma dulce",25000,50,"mujer");

        //define el comportamiento del mock: cuando se llame a findById() con "1", devuelve un producto.
        when(productoRepository.findById(idProducto)).thenReturn(Optional.of(producto));

        //Llamar al método findById() del servicio.
        Producto found = productoService.findById(idProducto);

        //verificar que el producto devuelto no sea nulo y que su ID coincida con el ID esperado.
        assertNotNull(found);
        assertEquals(idProducto, found.getIdProducto());
    }

    @Test
    public void testSave(){
        Producto producto = new Producto("aqua the gird","aroma berrys",50000,30,"mixto");

        //define el comportamiento del mock: cuando se llame a save(), devuelve el producto guardado.
        when(productoRepository.save(producto)).thenReturn(producto);

        //llamar al método save() del servicio producto.
        Producto saved = productoService.save(producto);

        //verificar que el producto guardado no sea nulo y que su nombre coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("aqua the gird", saved.getNombreProducto());
    }

    @Test
    public void testDeleteById(){
        Integer idProducto = 1;

        //define el comportamiento del mock: cuando se llame a deleteById() con "1", no hace nada.
        doNothing().when(productoRepository).deleteById(idProducto);

        //llamar al método deleteById() del servicio producto.
        productoService.delete(idProducto);

        //verificar que no se lanza ninguna excepción al eliminar el producto.
        verify(productoRepository, times(1)).deleteById(idProducto);
    }
}
