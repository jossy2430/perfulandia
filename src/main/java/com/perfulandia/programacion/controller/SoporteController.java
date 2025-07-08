package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.programacion.model.Soporte;
import com.perfulandia.programacion.service.SoporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/soportes")
@Tag(name = "Soportes", description = "Operaciones relacionadas con los soportes")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;

    @GetMapping
    @Operation(summary = "Listar todos los soportes", description = "Obtener una lista de todos los soportes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Soporte.class))),
        @ApiResponse(responseCode = "204", description = "No hay contenido")
    })
    public ResponseEntity<List<Soporte>> listar() {
        List<Soporte> soportes = soporteService.findAll();
        if (soportes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(soportes, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear un soporte", description = "Crear un nuevo soporte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Soporte creado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Soporte.class)))
    })
    public ResponseEntity<Soporte> guardar(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Soporte a crear", required = true,
            content = @Content(schema = @Schema(implementation = Soporte.class)))
        @RequestBody Soporte soporte) {
        Soporte nuevoSoporte = soporteService.save(soporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSoporte);
    }

    @GetMapping("/{idSoporte}")
    @Operation(summary = "Buscar soporte por ID", description = "Obtener un soporte por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Soporte.class))),
        @ApiResponse(responseCode = "404", description = "Soporte no encontrado")
    })
    public ResponseEntity<Soporte> buscar(
        @Parameter(description = "ID del soporte", required = true)
        @PathVariable Long idSoporte) {
        try {
            Soporte soporte = soporteService.findById(idSoporte);
            return ResponseEntity.ok(soporte);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idSoporte}")
    @Operation(summary = "Actualizar un soporte", description = "Actualizar un soporte existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Soporte actualizado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Soporte.class))),
        @ApiResponse(responseCode = "404", description = "Soporte no encontrado")
    })
    public ResponseEntity<Soporte> actualizar(
        @Parameter(description = "ID del soporte", required = true)
        @PathVariable Long idSoporte,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Soporte actualizado", required = true,
            content = @Content(schema = @Schema(implementation = Soporte.class)))
        @RequestBody Soporte soporte) {
        try {
            Soporte so = soporteService.findById(idSoporte);
            so.setIdSoporte(idSoporte);
            so.setCliente(soporte.getCliente());
            so.setAsunto(soporte.getAsunto());
            so.setDescripcion(soporte.getDescripcion());
            so.setEstado(soporte.getEstado());
            so.setFechaCreacion(soporte.getFechaCreacion());

            Soporte actualizado = soporteService.save(so);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idSoporte}")
    @Operation(summary = "Eliminar un soporte", description = "Eliminar un soporte por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Soporte eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Soporte no encontrado")
    })
    public ResponseEntity<?> eliminar(
        @Parameter(description = "ID del soporte", required = true)
        @PathVariable Long idSoporte) {
        try {
            soporteService.delete(idSoporte);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}