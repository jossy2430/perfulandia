package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.programacion.model.Pedido;
import com.perfulandia.programacion.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con los pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Listar todos los pedidos", description = "Obtener una lista de todos los pedidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "204", description = "No hay contenido")
    })
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = pedidoService.findAll();
        if (pedidos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear un pedido", description = "Crear un nuevo pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Pedido.class)))
    })
    public ResponseEntity<Pedido> guardar(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Pedido a crear", required = true,
            content = @Content(schema = @Schema(implementation = Pedido.class)))
        @RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @GetMapping("/{idPedido}")
    @Operation(summary = "Buscar pedido por ID", description = "Obtener un pedido por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<Pedido> buscar(
        @Parameter(description = "ID del pedido", required = true)
        @PathVariable Long idPedido) {
        try {
            Pedido pedido = pedidoService.finById(idPedido);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idPedido}")
    @Operation(summary = "Actualizar un pedido", description = "Actualizar un pedido existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<Pedido> actualizar(
        @Parameter(description = "ID del pedido", required = true)
        @PathVariable Long idPedido,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Pedido actualizado", required = true,
            content = @Content(schema = @Schema(implementation = Pedido.class)))
        @RequestBody Pedido pedido) {
        try {
            Pedido pe = pedidoService.finById(idPedido);
            pe.setIdPedido(idPedido);
            pe.setCliente(pedido.getCliente());
            pe.setDetallePedido(pedido.getDetallePedido());

            Pedido actualizado = pedidoService.save(pe);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idPedido}")
    @Operation(summary = "Eliminar un pedido", description = "Eliminar un pedido por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<?> eliminar(
        @Parameter(description = "ID del pedido", required = true)
        @PathVariable Long idPedido) {
        try {
            pedidoService.delete(idPedido);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}