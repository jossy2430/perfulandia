package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.programacion.model.Proveedor;
import com.perfulandia.programacion.service.ProveedorService;

@RestController
@RequestMapping("/api/v1/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<Proveedor>> listar(){
        List<Proveedor> proveedores = proveedorService.findAll();
        if (proveedores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Proveedor> guardar(@RequestBody Proveedor proveedor){
        Proveedor nuevoProveedor = proveedorService.save(proveedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProveedor);
    }

    @GetMapping("/{idProveedor}")
    public ResponseEntity<Proveedor> buscar(@PathVariable Integer idProveedor){
        try {
            Proveedor proveedor = proveedorService.findById(idProveedor);
            return ResponseEntity.ok(proveedor);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idProveedor}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Integer idProveedor, @RequestBody Proveedor proveedor){
        try {
            Proveedor prov = proveedorService.findById(idProveedor);
            prov.setIdProveedor(idProveedor);
            prov.setNombreProveedor(proveedor.getNombreProveedor());
            prov.setCorreo(proveedor.getCorreo());
            prov.setTelefonoProveedor(proveedor.getTelefonoProveedor());

            proveedorService.save(proveedor);
            return ResponseEntity.ok(proveedor);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idProveedor}")
    public ResponseEntity<?> eliminar(@PathVariable Long idProveedor){
        try {
            proveedorService.delete(idProveedor);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
