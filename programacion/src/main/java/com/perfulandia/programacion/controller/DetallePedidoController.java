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

import com.perfulandia.programacion.model.DetallePedido;
import com.perfulandia.programacion.service.DetallePedidoService;

@RestController
@RequestMapping("/api/v1/detallePedido")
public class DetallePedidoController {
    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<List<DetallePedido>> listar(){
        List<DetallePedido> detallesPedidos = detallePedidoService.findAll();
        if (detallesPedidos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detallesPedidos, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<DetallePedido> guardar(@RequestBody DetallePedido detallePedido){
        DetallePedido nuevoDetallePedido = detallePedidoService.save(detallePedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDetallePedido);
    }

    @GetMapping("/{idDetalle}")
    public ResponseEntity<DetallePedido> buscar(@PathVariable Integer idDetalle){
        try {
            DetallePedido detalleP = detallePedidoService.findById(idDetalle);
            return ResponseEntity.ok(detalleP);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idDetalle}")
    public ResponseEntity<DetallePedido> actualizar(@PathVariable Long idDetalle, @RequestBody DetallePedido detallePedido){
        try {
            DetallePedido dp = detallePedidoService.findById(idDetalle);
            dp.setIdDetalle(idDetalle);
            dp.setPedido(detallePedido.getPedido());
            dp.setCantidad(detallePedido.getCantidad());
            dp.setPrecioUnitario(detallePedido.getPrecioUnitario());

            detallePedidoService.save(detallePedido);
            return ResponseEntity.ok(detallePedido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idDetalle}")
    public ResponseEntity<?> eliminar(@PathVariable Long idDetalle){
        try {
            detallePedidoService.delete(idDetalle);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
