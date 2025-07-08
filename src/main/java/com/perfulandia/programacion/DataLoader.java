package com.perfulandia.programacion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.perfulandia.programacion.model.CarritoCompra;
import com.perfulandia.programacion.model.Cliente;
import com.perfulandia.programacion.model.CuponDescuento;
import com.perfulandia.programacion.model.DetallePedido;
import com.perfulandia.programacion.model.Empleado;
import com.perfulandia.programacion.model.Factura;
import com.perfulandia.programacion.model.Pedido;
import com.perfulandia.programacion.model.Producto;
import com.perfulandia.programacion.model.Proveedor;
import com.perfulandia.programacion.model.ResenaProducto;
import com.perfulandia.programacion.model.Soporte;
import com.perfulandia.programacion.model.Sucursal;
import com.perfulandia.programacion.repository.CarritoCompraRepository;
import com.perfulandia.programacion.repository.ClienteRepository;
import com.perfulandia.programacion.repository.CuponDescuentoRepository;
import com.perfulandia.programacion.repository.DetallePedidoRepository;
import com.perfulandia.programacion.repository.EmpleadoRepository;
import com.perfulandia.programacion.repository.FacturaRepository;
import com.perfulandia.programacion.repository.PedidoRepository;
import com.perfulandia.programacion.repository.ProductoRepository;
import com.perfulandia.programacion.repository.ProveedorRepository;
import com.perfulandia.programacion.repository.ResenaProductoRepository;
import com.perfulandia.programacion.repository.SoporteRepository;
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

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private CuponDescuentoRepository cuponDescuentoRepository;

    @Autowired
    private SoporteRepository soporteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ResenaProductoRepository resenaProductoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private CarritoCompraRepository carritoCompraRepository;

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

        //generar proveedores
        for (int i = 0; i < 10; i++) {
            Proveedor proveedor = new Proveedor();
            proveedor.setNombreProveedor(faker.company().name());
            proveedor.setCorreo(faker.internet().emailAddress());
            proveedor.setTelefonoProveedor(faker.number().numberBetween(100000000, 999999999));
            proveedorRepository.save(proveedor);
        }

        //generar facturas
        for (int i = 0; i < 30; i++) {
            Factura factura = new Factura();
            factura.setFechaEmision(LocalDateTime.now());
            factura.setTotal(faker.number().numberBetween(1000, 50000));

            // Asignar productos aleatorios a la factura
            List<Producto> productosAleatorios = productoRepository.findAll()
                .stream()
                .limit(faker.number().numberBetween(1, 5))
                .toList();
            factura.setProductos(productosAleatorios);

            // Asignar un proveedor aleatorio
            Proveedor proveedorAleatorio = proveedorRepository.findById(
                faker.number().numberBetween(1, 10) // Cambiado a Integer
            ).orElse(null);
            factura.setProveedor(proveedorAleatorio);

            facturaRepository.save(factura);
        }

       //generar detalles de pedido
        for (int i = 0; i < 50; i++) {
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setFecha(LocalDateTime.now());

            // Asignar un producto aleatorio
            Producto productoAleatorio = productoRepository.findById(
                faker.number().numberBetween(1, 50) // ID de producto entre 1 y 50
            ).orElse(null);
            detallePedido.setProducto(productoAleatorio);

            // Generar cantidad y calcular totales
            int cantidad = faker.number().numberBetween(1, 10);
            detallePedido.setCantidad(cantidad);
            detallePedido.setTotalSinDescuento((double) (cantidad * productoAleatorio.getPrecio())); // Conversión a Double

            // Asignar un cupón de descuento aleatorio (opcional)
            List<CuponDescuento> cupones = cuponDescuentoRepository.findAll();
            CuponDescuento cuponAleatorio = cupones.isEmpty() ? null : cupones.get(faker.number().numberBetween(0, cupones.size() - 1));
            detallePedido.setCuponAplicado(cuponAleatorio);

            // Calcular total con descuento si hay cupón
            if (cuponAleatorio != null) {
                double descuento = detallePedido.getTotalSinDescuento() * (cuponAleatorio.getPorcentaje() / 100);
                detallePedido.setTotalConDescuento(detallePedido.getTotalSinDescuento() - descuento);
            } else {
                detallePedido.setTotalConDescuento(detallePedido.getTotalSinDescuento());
            }

            detallePedidoRepository.save(detallePedido);
        }

        //generar pedidos
        for (int i = 0; i < 30; i++) {
            Pedido pedido = new Pedido();

            // Asignar un cliente aleatorio
            Cliente clienteAleatorio = clienteRepository.findById(
                faker.number().numberBetween(1, 60) // ID de cliente entre 1 y 60
            ).orElse(null);
            pedido.setCliente(clienteAleatorio);

            // Asignar un detalle de pedido aleatorio
            DetallePedido detallePedidoAleatorio = detallePedidoRepository.findById(
                (long) faker.number().numberBetween(1, 50) // Conversión a Long
            ).orElse(null);
            pedido.setDetallePedido(detallePedidoAleatorio);

            pedidoRepository.save(pedido);
        }

        //generar soporte
        for (int i = 0; i < 20; i++) {
            Soporte soporte = new Soporte();

            // Asignar un cliente aleatorio
            Cliente clienteAleatorio = clienteRepository.findById(
                faker.number().numberBetween(1, 60) // ID de cliente entre 1 y 60
            ).orElse(null);
            soporte.setCliente(clienteAleatorio);

            soporte.setAsunto(faker.lorem().sentence());
            soporte.setDescripcion(faker.lorem().paragraph());
            soporte.setEstado(faker.options().option("Abierto", "En Proceso", "Cerrado"));
            soporte.setFechaCreacion(LocalDateTime.now());

            soporteRepository.save(soporte);
        }

        //generar reseñas de producto
        for (int i = 0; i < 30; i++) {
            ResenaProducto resenaProducto = new ResenaProducto();

            // Asignar un cliente aleatorio
            Cliente clienteAleatorio = clienteRepository.findById(
                faker.number().numberBetween(1, 60) // ID de cliente entre 1 y 60
            ).orElse(null);
            resenaProducto.setCliente(clienteAleatorio);

            // Asignar un producto aleatorio
            Producto productoAleatorio = productoRepository.findById(
                faker.number().numberBetween(1, 50) // ID de producto entre 1 y 50
            ).orElse(null);
            resenaProducto.setProducto(productoAleatorio);

            resenaProducto.setCalificacion(faker.number().numberBetween(1, 5)); // Calificación entre 1 y 5
            resenaProducto.setFechaResena(LocalDateTime.now());

            resenaProductoRepository.save(resenaProducto);
        }

        //generar cupones de descuento
        for (int i = 0; i < 20; i++) {
            CuponDescuento cuponDescuento = new CuponDescuento();
            cuponDescuento.setCodigo(faker.code().isbn10()); // Generar un código único
            cuponDescuento.setPorcentaje(faker.number().randomDouble(2, 5, 50)); // Porcentaje entre 5% y 50%
            cuponDescuento.setFechaInicio(LocalDate.now());
            cuponDescuento.setFechaExpiracion(LocalDate.now().plusDays(faker.number().numberBetween(10, 30))); // Expira entre 10 y 30 días

            cuponDescuentoRepository.save(cuponDescuento);
        }
        // Generar carritos de compra
        for (int i = 0; i < 30; i++) {
            CarritoCompra carritoCompra = new CarritoCompra();

            // Asignar un cliente aleatorio
            Cliente clienteAleatorio = clienteRepository.findById(
                faker.number().numberBetween(1, 60) // ID de cliente entre 1 y 60
            ).orElse(null);
            carritoCompra.setCliente(clienteAleatorio);

            // Asignar productos aleatorios al carrito
            List<Producto> productosAleatorios = productoRepository.findAll()
                .stream()
                .limit(faker.number().numberBetween(1, 10)) // Seleccionar entre 1 y 10 productos
                .toList();
            carritoCompra.setProductos(productosAleatorios);

            // Calcular el total del carrito
            double totalSinDescuento = productosAleatorios.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();
            carritoCompra.setTotal(totalSinDescuento);

            // Asignar fecha de creación
            carritoCompra.setFechaCreacion(LocalDateTime.now());

            carritoCompraRepository.save(carritoCompra);
        }
    }
}
