package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.programacion.model.Sucursal;
import com.perfulandia.programacion.service.SucursalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/sucursales")
@Tag(name = "Sucursales", description = "Operaciones relacionadas con las sucursales")
public class SucursalController {
    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    @Operation(summary = "Obtener todas las sucursales", description = "Obtener una lista de todas las sucursales")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Sucursal.class)))
    })
    public List<Sucursal> getAllSucursales(){
        return sucursalService.findAll();
    }
    
    @PostMapping
    @Operation(summary = "Crear una sucursal", description = "Crear una sucursal nueva")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Sucursal.class)))
    })
    public Sucursal createSucursal(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Sucursal a crear", required = true,
            content = @Content(schema = @Schema(implementation = Sucursal.class)))
        @RequestBody Sucursal sucursal){
        return sucursalService.save(sucursal);
    }
    
    @GetMapping("/{idSucursal}")
    @Operation(summary = "Obtener sucursal por ID", description = "Obtener una sucursal por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public Sucursal getSucursalById(
        @Parameter(description = "ID de la sucursal", required = true)
        @PathVariable Integer idSucursal){
        return sucursalService.findById(idSucursal);
    }
    
    @PutMapping("/{idSucursal}")
    @Operation(summary = "Actualizar sucursal", description = "Actualizar una sucursal existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public Sucursal updateSucursal(
        @Parameter(description = "ID de la sucursal", required = true)
        @PathVariable Integer idSucursal,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Sucursal actualizada", required = true,
            content = @Content(schema = @Schema(implementation = Sucursal.class)))
        @RequestBody Sucursal sucursal){
        sucursal.setIdSucursal(idSucursal);
        return sucursalService.save(sucursal);
    }
    
    @DeleteMapping("/{idSucursal}")
    @Operation(summary = "Eliminar una sucursal", description = "Eliminar una sucursal por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public void deleteSucursal(
        @Parameter(description = "ID de la sucursal", required = true)
        @PathVariable Integer idSucursal){
        sucursalService.delete(idSucursal);
    }

}
