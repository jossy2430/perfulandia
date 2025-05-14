package com.perfulandia.programacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.programacion.model.Soporte;
import com.perfulandia.programacion.service.SoporteService;

@RestController
@RequestMapping("/api/v1/soportes")
public class SoporteController {
    @Autowired
    private SoporteService soporteService;

    @GetMapping
    public ResponseEntity<List<Soporte>> listar(){
        List<Soporte> soportes = soporteService.findAll();
        if (soportes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(soportes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Soporte> guardar(@RequestBody Soporte soporte){
        Soporte nuevoSoporte = soporteService.save(soporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSoporte);
    }

    @GetMapping("/{idSoporte}")
    public ResponseEntity<Soporte> buscar(@PathVariable Integer idSoporte){
        try {
            Soporte soporte = soporteService.findById(idSoporte);
            return ResponseEntity.ok(soporte);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idSoporte}")
    public ResponseEntity<Soporte> actualizar(@PathVariable Long idSoporte, @RequestBody Soporte soporte){
        try {
            Soporte so = soporteService.findById(idSoporte);
            so.setIdSoporte(idSoporte);
            so.setCliente(soporte.getCliente());
            so.setAsunto(soporte.getAsunto());
            so.setDescripcion(soporte.getDescripcion());
            so.setEstado(soporte.getEstado());
            so.setFechaCreacion(soporte.getFechaCreacion());

            soporteService.save(soporte);
            return ResponseEntity.ok(soporte);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
