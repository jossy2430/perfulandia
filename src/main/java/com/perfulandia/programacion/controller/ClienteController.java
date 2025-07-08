package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.programacion.model.Cliente;
import com.perfulandia.programacion.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description = "Obtener una lista de todos los clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Cliente.class)))
    })
    public List<Cliente> getAllClientes(){
        return clienteService.findAll();
    }
    

    @PostMapping
    @Operation(summary = "Crear un cliente", description ="Crear un cliente nuevo" )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamenta",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Cliente.class)))
    })
    public Cliente createCliente(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Cliente creado", required = true,
            content = @Content(schema = @Schema(implementation = Cliente.class)))
        @RequestBody Cliente cliente){
        return clienteService.save(cliente);
        }
        
    

    @GetMapping("/{idCliente}")
    @Operation(summary = "Obtener cliente por ID", description = "Ontener un cliente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "404",description = "Cliente no encontrado")
    })
    public Cliente getClienteById(
        @Parameter(description = "ID del cliente", required = true)
        @PathVariable Integer idCliente){
            return clienteService.findById(idCliente);
        }
    

    @PutMapping("/{idCliente}")
    @Operation(summary = "Actualizar cliente", description = "Actualizar un cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public Cliente updateCliente(
        @Parameter(description = "ID del cliente", required = true)
        @PathVariable Integer idCliente,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Cliente actualizado", required = true,
            content = @Content(schema = @Schema(implementation = Cliente.class)))
            @RequestBody Cliente cliente){
                cliente.setIdCliente(idCliente);
                return clienteService.save(cliente);
            }
    
    
    @DeleteMapping("/{idCliente}")
    @Operation(summary = "Eliminar un cliente", description = "Eliminar un cliente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public void deleteCliente(
        @Parameter(description = "ID del cliente", required = true)
        @PathVariable Integer idCliente){
        clienteService.delete(idCliente);
    }
}
