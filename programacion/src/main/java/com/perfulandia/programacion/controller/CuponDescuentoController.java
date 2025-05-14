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

import com.perfulandia.programacion.model.CuponDescuento;
import com.perfulandia.programacion.service.CuponDescuentoService;

@RestController
@RequestMapping("/api/v1/cupones")
public class CuponDescuentoController {
    @Autowired
    private CuponDescuentoService cuponDescuentoService;

    @GetMapping
    public ResponseEntity<List<CuponDescuento>> listar(){
        List<CuponDescuento> descuentos = cuponDescuentoService.findAll();
        if (descuentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(descuentos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CuponDescuento> guardar(@RequestBody CuponDescuento cuponDescuento){
        CuponDescuento nuevoCupon = cuponDescuentoService.save(cuponDescuento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCupon);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<CuponDescuento> buscar(@PathVariable String codigo){
        try {
            CuponDescuento cuponDescuento = cuponDescuentoService.findById(codigo);
            return ResponseEntity.ok(cuponDescuento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<CuponDescuento> actualizar(@PathVariable String codigo, @RequestBody CuponDescuento cuponDescuento){
        try {
            CuponDescuento cd = cuponDescuentoService.findById(codigo);
            cd.setCodigo(codigo);
            cd.setPorcentaje(cuponDescuento.getPorcentaje());
            cd.setFechaInicio(cuponDescuento.getFechaInicio());
            cd.setFechaExpiracion(cuponDescuento.getFechaExpiracion());

            cuponDescuentoService.save(cuponDescuento);
            return ResponseEntity.ok(cuponDescuento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> eliminar(@PathVariable String codigo){
        try {
            cuponDescuentoService.delete(codigo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
