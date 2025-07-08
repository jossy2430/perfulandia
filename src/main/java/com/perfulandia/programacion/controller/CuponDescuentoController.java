package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.programacion.model.CuponDescuento;
import com.perfulandia.programacion.service.CuponDescuentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/cupones")
@Tag(name = "Cupones de Descuento", description = "Operaciones relacionadas con los cupones de descuento")
public class CuponDescuentoController {

    @Autowired
    private CuponDescuentoService cuponDescuentoService;

    @GetMapping
    @Operation(summary = "Listar todos los cupones", description = "Obtener una lista de todos los cupones de descuento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = CuponDescuento.class))),
        @ApiResponse(responseCode = "204", description = "No hay contenido")
    })
    public ResponseEntity<List<CuponDescuento>> listar() {
        List<CuponDescuento> descuentos = cuponDescuentoService.findAll();
        if (descuentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(descuentos, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear un cupón", description = "Crear un nuevo cupón de descuento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cupón creado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = CuponDescuento.class)))
    })
    public ResponseEntity<CuponDescuento> guardar(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Cupón a crear", required = true,
            content = @Content(schema = @Schema(implementation = CuponDescuento.class)))
        @RequestBody CuponDescuento cuponDescuento) {
        CuponDescuento nuevoCupon = cuponDescuentoService.save(cuponDescuento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCupon);
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Buscar cupón por código", description = "Obtener un cupón de descuento por su código")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = CuponDescuento.class))),
        @ApiResponse(responseCode = "404", description = "Cupón no encontrado")
    })
    public ResponseEntity<CuponDescuento> buscar(
        @Parameter(description = "Código del cupón", required = true)
        @PathVariable String codigo) {
        try {
            CuponDescuento cuponDescuento = cuponDescuentoService.findById(codigo);
            return ResponseEntity.ok(cuponDescuento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigo}")
    @Operation(summary = "Actualizar un cupón", description = "Actualizar un cupón de descuento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cupón actualizado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = CuponDescuento.class))),
        @ApiResponse(responseCode = "404", description = "Cupón no encontrado")
    })
    public ResponseEntity<CuponDescuento> actualizar(
        @Parameter(description = "Código del cupón", required = true)
        @PathVariable String codigo,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Cupón actualizado", required = true,
            content = @Content(schema = @Schema(implementation = CuponDescuento.class)))
        @RequestBody CuponDescuento cuponDescuento) {
        try {
            CuponDescuento cd = cuponDescuentoService.findById(codigo);
            cd.setCodigo(codigo);
            cd.setPorcentaje(cuponDescuento.getPorcentaje());
            cd.setFechaInicio(cuponDescuento.getFechaInicio());
            cd.setFechaExpiracion(cuponDescuento.getFechaExpiracion());

            CuponDescuento actualizado = cuponDescuentoService.save(cd);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codigo}")
    @Operation(summary = "Eliminar un cupón", description = "Eliminar un cupón de descuento por su código")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cupón eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cupón no encontrado")
    })
    public ResponseEntity<?> eliminar(
        @Parameter(description = "Código del cupón", required = true)
        @PathVariable String codigo) {
        try {
            cuponDescuentoService.delete(codigo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}