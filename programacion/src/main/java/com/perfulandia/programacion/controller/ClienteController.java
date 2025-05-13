package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.programacion.model.Cliente;
import com.perfulandia.programacion.service.ClienteService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar(){
        List<Cliente> clientes = clienteService.findAll();
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cliente> guardar(@RequestBody Cliente cliente){
        Cliente nuevoCliente = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> buscar(@PathVariable Integer idCliente){
        try {
            Cliente cliente = clienteService.findById(idCliente);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer idCliente, @RequestBody Cliente cliente){
        try {
            Cliente cli = clienteService.findById(idCliente);
            cli.setIdCliente(idCliente);
            cli.setNombres(cliente.getNombres());
            cli.setApellidos(cliente.getApellidos());
            cli.setDireccion(cliente.getDireccion()); 
            cli.setCorreo(cliente.getCorreo());
            cli.setPassword(cliente.getPassword());
            cli.setFechaRegistro(null);
            cli.setTelefono(cliente.getTelefono());
            
            clienteService.save(cliente);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<?> eliminar(@PathVariable Long idCliente){
        try {
            clienteService.delete(idCliente);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
