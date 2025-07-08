package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.programacion.model.Factura;
import com.perfulandia.programacion.service.FacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/facturas")
@Tag(name = "Facturas", description = "Operaciones relacionadas con las facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    @Operation(summary = "Listar todas las facturas", description = "Obtener una lista de todas las facturas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Factura.class))),
        @ApiResponse(responseCode = "204", description = "No hay contenido")
    })
    public ResponseEntity<List<Factura>> listar() {
        List<Factura> facturas = facturaService.findAll();
        if (facturas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear una factura", description = "Crear una nueva factura")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Factura creada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Factura.class)))
    })
    public ResponseEntity<Factura> guardar(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Factura a crear", required = true,
            content = @Content(schema = @Schema(implementation = Factura.class)))
        @RequestBody Factura factura) {
        Factura nuevaFactura = facturaService.save(factura);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFactura);
    }

    @GetMapping("/{idFactura}")
    @Operation(summary = "Buscar factura por ID", description = "Obtener una factura por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Factura.class))),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    public ResponseEntity<Factura> buscar(
        @Parameter(description = "ID de la factura", required = true)
        @PathVariable Long idFactura) {
        try {
            Factura factura = facturaService.findById(idFactura);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idFactura}")
    @Operation(summary = "Actualizar una factura", description = "Actualizar una factura existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura actualizada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Factura.class))),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    public ResponseEntity<Factura> actualizar(
        @Parameter(description = "ID de la factura", required = true)
        @PathVariable Long idFactura,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Factura actualizada", required = true,
            content = @Content(schema = @Schema(implementation = Factura.class)))
        @RequestBody Factura factura) {
        try {
            Factura fac = facturaService.findById(idFactura);
            fac.setProductos(factura.getProductos());
            fac.setFechaEmision(factura.getFechaEmision());
            fac.setTotal(factura.getTotal());
            fac.setProveedor(factura.getProveedor());

            Factura actualizada = facturaService.save(fac);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idFactura}")
    @Operation(summary = "Eliminar una factura", description = "Eliminar una factura por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Factura eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    public ResponseEntity<?> eliminar(
        @Parameter(description = "ID de la factura", required = true)
        @PathVariable Long idFactura) {
        try {
            facturaService.delete(idFactura);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}