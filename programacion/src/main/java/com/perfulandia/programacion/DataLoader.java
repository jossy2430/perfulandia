package com.perfulandia.programacion;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.perfulandia.programacion.model.Cliente;
import com.perfulandia.programacion.model.Empleado;
import com.perfulandia.programacion.model.Producto;
import com.perfulandia.programacion.model.Sucursal;
import com.perfulandia.programacion.repository.ClienteRepository;
import com.perfulandia.programacion.repository.EmpleadoRepository;
import com.perfulandia.programacion.repository.ProductoRepository;
import com.perfulandia.programacion.repository.SucursalRepository;

import net.datafaker.Faker;

@Profile("!test")
@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();
        Random random = new Random();

        //lista de roles
        String[] roles = {"ADMIN", "VENDEDOR", "GERENTE-SUCURSAL","LOGISTICA"};

        //generar clientes
        for (int i = 0; i < 60; i++) {
            Cliente cliente = new Cliente();
            cliente.setNombres(faker.name().firstName() + " " + faker.name().firstName());
            cliente.setApellidos(faker.name().lastName() + "" + faker.name().lastName());
            cliente.setDireccion(faker.address().fullAddress());
            cliente.setCorreo(faker.internet().emailAddress());
            cliente.setPassword(faker.internet().password());
            cliente.setFechaRegistro(LocalDateTime.now());
            cliente.setTelefono(faker.number().numberBetween(100000000, 999999999));
            clienteRepository.save(cliente);
        }
        //generar sucursales
        for (int i = 0; i < 5; i++) {
            Sucursal sucursal = new Sucursal();
            sucursal.setNombreSucursal(faker.company().name());
            sucursal.setDireccion(faker.address().fullAddress());
            sucursalRepository.save(sucursal);
        }

        //generar empleados
        for (int i = 0; i < 20; i++) {
            Empleado empleado = new Empleado();
            empleado.setRut(faker.idNumber().valid());
            empleado.setNombreEmpleado(faker.name().fullName());
            empleado.setPassword(faker.internet().password());
            empleado.setRol(roles[random.nextInt(roles.length)]);
            empleado.setSucursal(sucursalRepository.findById(1).orElse(null));
            empleadoRepository.save(empleado);
        }

        //generar productos
        for (int i = 0; i < 50; i++) {
            Producto producto = new Producto();
            producto.setNombreProducto(faker.commerce().productName());
            producto.setDescripcion(faker.lorem().sentence());
            producto.setPrecio((int)faker.number().randomDouble(0, 10, 500)); //conversion a integer
            producto.setStock(faker.number().numberBetween(1,100));
            producto.setCategoria(faker.commerce().department());
            productoRepository.save(producto);

        }
    }
}
