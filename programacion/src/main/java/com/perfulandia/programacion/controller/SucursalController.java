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

import com.perfulandia.programacion.model.Sucursal;
import com.perfulandia.programacion.service.SucursalService;

@RestController
@RequestMapping("/api/vi/sucursales")
public class SucursalController {
    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<Sucursal>> listar(){
        List<Sucursal> sucursales = sucursalService.findAll();
        if (sucursales.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sucursales, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Sucursal> guardar(@RequestBody Sucursal sucursal){
        Sucursal nuevoSucursal = sucursalService.save(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSucursal);
    }

    @GetMapping("/{idSucursal}")
    public ResponseEntity<Sucursal> buscar(@PathVariable Integer idSucursal){
        try {
            Sucursal sucursal = sucursalService.findById(idSucursal);
            return ResponseEntity.ok(sucursal);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idSucursal}")
    public ResponseEntity<Sucursal> actualizar(@PathVariable Integer idSucursal, @RequestBody Sucursal sucursal){
        try {
            Sucursal su = sucursalService.findById(idSucursal);
            su.setIdSucursal(idSucursal);
            su.setNombreSucursal(sucursal.getNombreSucursal());
            su.setDireccion(sucursal.getDireccion());

            sucursalService.save(sucursal);
            return ResponseEntity.ok(sucursal);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idSucursal}")
    public ResponseEntity<?> eliminar(@PathVariable Long idSucursal){
        try {
            sucursalService.delete(idSucursal);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
