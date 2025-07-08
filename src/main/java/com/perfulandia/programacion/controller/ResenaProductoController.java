package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.programacion.model.ResenaProducto;
import com.perfulandia.programacion.service.ResenaProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/resenasProductos")
@Tag(name = "Reseñas de Productos", description = "Operaciones relacionadas con las reseñas de productos")
public class ResenaProductoController {

    @Autowired
    private ResenaProductoService resenaProductoService;

    @GetMapping
    @Operation(summary = "Listar todas las reseñas", description = "Obtener una lista de todas las reseñas de productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = ResenaProducto.class))),
        @ApiResponse(responseCode = "204", description = "No hay contenido")
    })
    public ResponseEntity<List<ResenaProducto>> listar() {
        List<ResenaProducto> resennas = resenaProductoService.findAll();
        if (resennas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resennas, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear una reseña", description = "Crear una nueva reseña de producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = ResenaProducto.class)))
    })
    public ResponseEntity<ResenaProducto> guardar(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Reseña a crear", required = true,
            content = @Content(schema = @Schema(implementation = ResenaProducto.class)))
        @RequestBody ResenaProducto resenaProducto) {
        ResenaProducto nuevaResena = resenaProductoService.save(resenaProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaResena);
    }

    @GetMapping("/{idResena}")
    @Operation(summary = "Buscar reseña por ID", description = "Obtener una reseña de producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = ResenaProducto.class))),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<ResenaProducto> buscar(
        @Parameter(description = "ID de la reseña", required = true)
        @PathVariable Long idResena) {
        try {
            ResenaProducto resenaProducto = resenaProductoService.findById(idResena);
            return ResponseEntity.ok(resenaProducto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idResena}")
    @Operation(summary = "Actualizar una reseña", description = "Actualizar una reseña de producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña actualizada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = ResenaProducto.class))),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<ResenaProducto> actualizar(
        @Parameter(description = "ID de la reseña", required = true)
        @PathVariable Long idResena,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Reseña actualizada", required = true,
            content = @Content(schema = @Schema(implementation = ResenaProducto.class)))
        @RequestBody ResenaProducto resenaProducto) {
        try {
            ResenaProducto rp = resenaProductoService.findById(idResena);
            rp.setCliente(resenaProducto.getCliente());
            rp.setProducto(resenaProducto.getProducto());
            rp.setCalificacion(resenaProducto.getCalificacion());
            rp.setFechaResena(resenaProducto.getFechaResena());

            ResenaProducto actualizada = resenaProductoService.save(rp);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idResena}")
    @Operation(summary = "Eliminar una reseña", description = "Eliminar una reseña de producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reseña eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<?> eliminar(
        @Parameter(description = "ID de la reseña", required = true)
        @PathVariable Long idResena) {
        try {
            resenaProductoService.delete(idResena);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}