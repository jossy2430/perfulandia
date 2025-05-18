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

import com.perfulandia.programacion.model.ResenaProducto;
import com.perfulandia.programacion.service.ResenaProductoService;

@RestController
@RequestMapping("/api/v1/resenasProductos")
public class ResenaProductoController {
    @Autowired
    private ResenaProductoService resenaProductoService;

    @GetMapping
    public ResponseEntity<List<ResenaProducto>> listar(){
        List<ResenaProducto> resennas = resenaProductoService.findAll();
        if (resennas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resennas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResenaProducto> guardar(@RequestBody ResenaProducto resenaProducto){
        ResenaProducto nuevaResena = resenaProductoService.save(resenaProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaResena);
    }

    @GetMapping("/{idResena}")
    public ResponseEntity<ResenaProducto> buscar(@PathVariable Long idResena){
        try {
            ResenaProducto resenaProducto = resenaProductoService.findById(idResena);
            return ResponseEntity.ok(resenaProducto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idResena}")
    public ResponseEntity<ResenaProducto> actualizar(@PathVariable Integer idResena, @RequestBody ResenaProducto resenaProducto){
        try {
            ResenaProducto rp = resenaProductoService.findById(idResena);
            rp.setIdResena(resenaProducto.getIdResena());
            rp.setCliente(resenaProducto.getCliente());
            rp.setProducto(resenaProducto.getProducto());
            rp.setCalificacion(resenaProducto.getCalificacion());
            rp.setFechaResena(resenaProducto.getFechaResena());

            resenaProductoService.save(resenaProducto);
            return ResponseEntity.ok(resenaProducto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idResena}")
    public ResponseEntity<?> eliminar(@PathVariable Long idResena){
        try {
            resenaProductoService.delete(idResena);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
