package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.programacion.model.CarritoCompra;
import com.perfulandia.programacion.service.CarritoCompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/carritos")
@Tag(name = "Carritos de Compra", description = "Operaciones relacionadas con los carritos de compra")
public class CarritoCompraController {

    @Autowired
    private CarritoCompraService carritoCompraService;

    @GetMapping
    @Operation(summary = "Obtener todos los carritos", description = "Obtener una lista de todos los carritos de compra")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = CarritoCompra.class)))
    })
    public List<CarritoCompra> getAllCarritos() {
        return carritoCompraService.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear un carrito", description = "Crear un nuevo carrito de compra")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Carrito creado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = CarritoCompra.class)))
    })
    public CarritoCompra createCarrito(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Carrito a crear", required = true,
            content = @Content(schema = @Schema(implementation = CarritoCompra.class)))
        @RequestBody CarritoCompra carritoCompra) {
        return carritoCompraService.save(carritoCompra);
    }

    @GetMapping("/{idCarrito}")
    @Operation(summary = "Obtener carrito por ID", description = "Obtener un carrito de compra por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = CarritoCompra.class))),
        @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    public CarritoCompra getCarritoById(
        @Parameter(description = "ID del carrito", required = true)
        @PathVariable Integer idCarrito) {
        return carritoCompraService.findById(idCarrito);
    }

    @PutMapping("/{idCarrito}")
    @Operation(summary = "Actualizar carrito", description = "Actualizar un carrito existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito actualizado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = CarritoCompra.class))),
        @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    public CarritoCompra updateCarrito(
        @Parameter(description = "ID del carrito", required = true)
        @PathVariable Integer idCarrito,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Carrito actualizado", required = true,
            content = @Content(schema = @Schema(implementation = CarritoCompra.class)))
        @RequestBody CarritoCompra carritoCompra) {
        carritoCompra.setIdCarrito(idCarrito);
        return carritoCompraService.save(carritoCompra);
    }

    @DeleteMapping("/{idCarrito}")
    @Operation(summary = "Eliminar un carrito", description = "Eliminar un carrito por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    public void deleteCarrito(
        @Parameter(description = "ID del carrito", required = true)
        @PathVariable Integer idCarrito) {
        carritoCompraService.delete(idCarrito);
    }

    @PostMapping("/{idCarrito}/productos/{idProducto}")
    @Operation(summary = "Agregar producto al carrito", description = "Agregar un producto al carrito de compra")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto agregado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Carrito o producto no encontrado")
    })
    public CarritoCompra agregarProducto(
        @Parameter(description = "ID del carrito", required = true)
        @PathVariable Integer idCarrito,
        @Parameter(description = "ID del producto", required = true)
        @PathVariable Integer idProducto,
        @Parameter(description = "Cantidad del producto", required = true)
        @RequestParam Integer cantidad,
        @Parameter(description = "Porcentaje de descuento", required = false)
        @RequestParam(required = false) Double descuentoPorcentaje) {
        CarritoCompra carritoCompra = carritoCompraService.findById(idCarrito);
        carritoCompraService.agregarProducto(carritoCompra, idProducto, cantidad, descuentoPorcentaje);
        return carritoCompra;
    }
}