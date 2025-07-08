package com.perfulandia.programacion.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.programacion.model.DetallePedido;
import com.perfulandia.programacion.service.DetallePedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/detallePedido")
@Tag(name = "Detalle Pedido", description = "Operaciones relacionadas con los detalles de pedidos")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    @Operation(summary = "Listar todos los detalles de pedidos", description = "Obtener una lista de todos los detalles de pedidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = DetallePedido.class))),
        @ApiResponse(responseCode = "204", description = "No hay contenido")
    })
    public ResponseEntity<List<DetallePedido>> listar() {
        List<DetallePedido> detallesPedidos = detallePedidoService.findAll();
        if (detallesPedidos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detallesPedidos, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear un detalle de pedido", description = "Crear un nuevo detalle de pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Detalle de pedido creado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = DetallePedido.class)))
    })
    public ResponseEntity<DetallePedido> guardar(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Detalle de pedido a crear", required = true,
            content = @Content(schema = @Schema(implementation = DetallePedido.class)))
        @RequestBody DetallePedido detallePedido) {
        DetallePedido nuevoDetallePedido = detallePedidoService.save(detallePedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDetallePedido);
    }

    @GetMapping("/{idDetalle}")
    @Operation(summary = "Buscar detalle de pedido por ID", description = "Obtener un detalle de pedido por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = DetallePedido.class))),
        @ApiResponse(responseCode = "404", description = "Detalle de pedido no encontrado")
    })
    public ResponseEntity<DetallePedido> buscar(
        @Parameter(description = "ID del detalle de pedido", required = true)
        @PathVariable Long idDetalle) {
        try {
            DetallePedido detalleP = detallePedidoService.findById(idDetalle);
            return ResponseEntity.ok(detalleP);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idDetalle}")
    @Operation(summary = "Actualizar un detalle de pedido", description = "Actualizar un detalle de pedido existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle de pedido actualizado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = DetallePedido.class))),
        @ApiResponse(responseCode = "404", description = "Detalle de pedido no encontrado")
    })
    public ResponseEntity<DetallePedido> actualizar(
        @Parameter(description = "ID del detalle de pedido", required = true)
        @PathVariable Long idDetalle,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Detalle de pedido actualizado", required = true,
            content = @Content(schema = @Schema(implementation = DetallePedido.class)))
        @RequestBody DetallePedido detallePedido) {
        try {
            DetallePedido dp = detallePedidoService.findById(idDetalle);
            dp.setIdDetalle(idDetalle);
            dp.setCantidad(detallePedido.getCantidad());
            dp.setFecha(detallePedido.getFecha());
            dp.setProducto(detallePedido.getProducto());
            dp.setTotalSinDescuento(detallePedido.getTotalSinDescuento());
            dp.setCuponAplicado(detallePedido.getCuponAplicado());
            dp.setTotalConDescuento(detallePedido.getTotalConDescuento());

            DetallePedido actualizado = detallePedidoService.save(dp);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idDetalle}")
    @Operation(summary = "Eliminar un detalle de pedido", description = "Eliminar un detalle de pedido por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Detalle de pedido eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Detalle de pedido no encontrado")
    })
    public ResponseEntity<?> eliminar(
        @Parameter(description = "ID del detalle de pedido", required = true)
        @PathVariable Long idDetalle) {
        try {
            detallePedidoService.delete(idDetalle);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}