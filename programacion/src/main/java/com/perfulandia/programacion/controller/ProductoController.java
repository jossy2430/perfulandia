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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Obtiene una lista de todos los productos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Producto.class)))
    })
    public List<Producto> getAllProductos() {
        return productoService.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear un producto", description = "Crea un nuevo producto en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Producto.class)))
    })
    public Producto createProducto(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Producto a crear", required = true,
            content = @Content(schema = @Schema(implementation = Producto.class)))
        @RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @GetMapping("/{idProducto}")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public Producto getProductoById(
        @Parameter(description = "ID del producto", required = true)
        @PathVariable Integer idProducto) {
        return productoService.findById(idProducto);
    }

    @PutMapping("/{idProducto}")
    @Operation(summary = "Actualizar un producto", description = "Actualiza la información de un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public Producto updateProducto(
        @Parameter(description = "ID del producto a actualizar", required = true)
        @PathVariable Integer idProducto,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados del producto", required = true,
            content = @Content(schema = @Schema(implementation = Producto.class)))
        @RequestBody Producto producto) {
        producto.setIdProducto(idProducto);
        return productoService.save(producto);    
    }

    @DeleteMapping("/{idProducto}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public void deleteProducto(
        @Parameter(description = "ID del producto a eliminar", required = true)
        @PathVariable Integer idProducto) {
        productoService.delete(idProducto);
    }

    @GetMapping("/verificar-inventario/{idProducto}/{cantidad}")
    @Operation(summary = "Verificar disponibilidad de inventario", description = "Verifica si hay suficiente stock de un producto para una cantidad solicitada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hay suficiente inventario",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "No hay suficiente inventario")
    })
    public ResponseEntity<String> verificarInventario(
        @Parameter(description = "ID del producto", required = true)
        @PathVariable Integer idProducto, 
        @Parameter(description = "Cantidad solicitada", required = true)
        @PathVariable int cantidad) {
        
        boolean hayInventario = productoService.verificarInventario(idProducto, cantidad);
        if (hayInventario) {
            return ResponseEntity.ok("Hay suficiente inventario.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay suficiente inventario.");
        }
    }
}
