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

import com.perfulandia.programacion.model.Factura;
import com.perfulandia.programacion.service.FacturaService;

@RestController
@RequestMapping("/api/v1/facturas")
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<Factura>> listar(){
        List<Factura> facturas = facturaService.findAll();
        if (facturas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Factura> guardar(@RequestBody Factura factura){
        // Ahora la factura debe recibir una lista de productos en el JSON
        Factura nuevaFactura = facturaService.save(factura);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFactura);
    }

    @GetMapping("/{idFactura}")
    public ResponseEntity<Factura> buscar(@PathVariable Integer idFactura){
        try {
            Factura factura = facturaService.findById(idFactura);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idFactura}")
    public ResponseEntity<Factura> actualizar(@PathVariable Integer idFactura, @RequestBody Factura factura){
        try {
            Factura fac = facturaService.findById(idFactura);
            fac.setIdFactura(idFactura);
            fac.setProductos(factura.getProductos());
            fac.setFechaEmision(factura.getFechaEmision());
            fac.setTotal(factura.getTotal());
            fac.setProveedor(factura.getProveedor());

            facturaService.save(factura);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idFactura}")
    public ResponseEntity<?> eliminar(@PathVariable Long idFactura){
        try {
            facturaService.delete(idFactura);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
