package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.programacion.model.Producto;
import com.perfulandia.programacion.service.ProductoService;

@RestController
@RequestMapping("api/v1/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        Producto nuevProducto = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevProducto);
    }

    @GetMapping("/{idProducto}")
    public ResponseEntity<Producto> buscar(@PathVariable Integer idProducto){
        try {
            Producto producto = productoService.findById(idProducto);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{idProducto}")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer idProducto, @RequestBody Producto producto){
        try {
            Producto pro = productoService.findById(idProducto);
            pro.setIdProducto(idProducto);
            pro.setNombreProducto(producto.getNombreProducto());
            pro.setDescripcion(producto.getDescripcion());
            pro.setPrecio(producto.getPrecio());
            pro.setStock(producto.getStock());
            pro.setCategoria(producto.getCategoria());

            productoService.save(producto);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idProducto}")
    public ResponseEntity<?> eliminar(@PathVariable Integer idProducto){
        try {
            productoService.delete(idProducto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/verificar-inventario/{idProducto}/{cantidad}")
    public ResponseEntity<String> verificarInventario(@PathVariable Integer idProducto, @PathVariable int cantidad) {
        boolean hayInventario = productoService.verificarInventario(idProducto, cantidad);
        if (hayInventario) {
            return ResponseEntity.ok("Hay suficiente inventario.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay suficiente inventario.");
        }
    }
}
