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

import com.perfulandia.programacion.model.Empleado;
import com.perfulandia.programacion.service.EmpleadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/empleados")
@Tag(name = "Empleados", description = "Operaciones relacionadas con los empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    @Operation(summary = "Obtener todos los empleados", description = "Obtener una lista de todos los empleados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Empleado.class)))
    })
    public List<Empleado> getAllEmpleados(){
        return empleadoService.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear un empleado", description = "Crear un empleado nuevo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Empleado.class)))
    })
    public Empleado createEmpleado(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Empleado a crear", required = true,
            content = @Content(schema = @Schema(implementation = Empleado.class)))
        @RequestBody Empleado empleado){
        return empleadoService.save(empleado);
    }
    
    @GetMapping("/{rut}")
    @Operation(summary = "Obtener empleado por RUT", description = "Obtener un empleado por su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Empleado.class))),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public Empleado getEmpleadoByRut(
        @Parameter(description = "RUT del empleado", required = true)
        @PathVariable String rut){
        return empleadoService.findById(rut);
    }

    @PutMapping("/{rut}")
    @Operation(summary = "Actualizar empleado", description = "Actualizar un empleado existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Empleado.class))),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public Empleado updateEmpleado(
        @Parameter(description = "RUT del empleado", required = true)
        @PathVariable String rut,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Empleado actualizado", required = true,
            content = @Content(schema = @Schema(implementation = Empleado.class)))
        @RequestBody Empleado empleado){
        empleado.setRut(rut);
        return empleadoService.save(empleado);
    }

    @DeleteMapping("/{rut}")
    @Operation(summary = "Eliminar un empleado", description = "Eliminar un empleado por su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public void deleteEmpleado(
        @Parameter(description = "RUT del empleado", required = true)
        @PathVariable String rut){
        empleadoService.delete(rut);
    }
}
