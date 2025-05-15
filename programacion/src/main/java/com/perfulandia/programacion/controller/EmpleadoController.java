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

import com.perfulandia.programacion.model.Empleado;
import com.perfulandia.programacion.service.EmpleadoService;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<Empleado>> listar(){
        List<Empleado> empleados = empleadoService.findAll();
        if (empleados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(empleados, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Empleado> guardar(@RequestBody Empleado empleado){
        Empleado  nuevEmpleado = empleadoService.save(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevEmpleado);
    }

    @GetMapping("/{idEmpleado}")
    public ResponseEntity<Empleado> buscar(@PathVariable Integer idEmpleado){
        try {
            Empleado empleado = empleadoService.findById(idEmpleado);
            return ResponseEntity.ok(empleado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idEmpleado}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Integer idEmpleado, @RequestBody Empleado empleado){
        try {
            Empleado em = empleadoService.findById(idEmpleado);
            em.setIdEmpleado(idEmpleado);
            em.setRut(empleado.getRut());
            em.setNombreEmpleado(empleado.getNombreEmpleado());
            em.setPassword(empleado.getPassword());
            em.setRol(empleado.getRol());
            em.setSucursal(empleado.getSucursal());

            empleadoService.save(empleado);
            return ResponseEntity.ok(empleado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idEmpleado}")
    public ResponseEntity<?> eliminar(@PathVariable Long idEmpleado){
        try {
            empleadoService.delete(idEmpleado);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
