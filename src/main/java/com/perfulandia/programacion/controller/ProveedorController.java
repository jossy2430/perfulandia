package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.programacion.model.Proveedor;
import com.perfulandia.programacion.service.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/proveedores")
@Tag(name = "Proveedores", description = "Operaciones relacionadas con los proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    @Operation(summary = "Obtener todos los proveedores", description = "Obtener una lista de todos los proveedores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Proveedor.class)))
    })
    public List<Proveedor> getAllProveedores() {
        return proveedorService.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear un proveedor", description = "Crear un proveedor nuevo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Proveedor.class)))
    })
    public Proveedor createProveedor(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Proveedor a crear", required = true,
            content = @Content(schema = @Schema(implementation = Proveedor.class)))
        @RequestBody Proveedor proveedor) {
        return proveedorService.save(proveedor);
    }

    @GetMapping("/{idProveedor}")
    @Operation(summary = "Obtener proveedor por ID", description = "Obtener un proveedor por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Proveedor.class))),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public Proveedor getProveedorById(
        @Parameter(description = "ID del proveedor", required = true)
        @PathVariable Integer idProveedor) {
        return proveedorService.findById(idProveedor);
    }

    @PutMapping("/{idProveedor}")
    @Operation(summary = "Actualizar proveedor", description = "Actualizar un proveedor existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor actualizado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Proveedor.class))),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public Proveedor updateProveedor(
        @Parameter(description = "ID del proveedor", required = true)
        @PathVariable Integer idProveedor,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Proveedor actualizado", required = true,
            content = @Content(schema = @Schema(implementation = Proveedor.class)))
        @RequestBody Proveedor proveedor) {
        proveedor.setIdProveedor(idProveedor);
        return proveedorService.save(proveedor);
    }

    @DeleteMapping("/{idProveedor}")
    @Operation(summary = "Eliminar un proveedor", description = "Eliminar un proveedor por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public void deleteProveedor(
        @Parameter(description = "ID del proveedor", required = true)
        @PathVariable Integer idProveedor) {
        proveedorService.delete(idProveedor);
    }
}