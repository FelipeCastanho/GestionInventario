package test.java;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.logica.ProductoLogica;
import main.java.logica.TransaccionLogica;
import main.java.logica.TransaccionProductoLogica;
import main.java.modelo.Producto;
import main.java.modelo.Transaccion;
import main.java.modelo.TransaccionProducto;
import main.java.persistencia.TransaccionJpaController;
import main.java.persistencia.TransaccionProductoJpaController;

public class TransaccionProductoTestCase {

	private TransaccionProductoLogica transaccionProductoLogica;
	private Producto producto;
	private Transaccion transaccion;
	private List<Producto> listaProductos;
	private ProductoLogica ProductoLogica;
	private TransaccionLogica transaccionLogica;
	private TransaccionProductoJpaController transaccionAux;

	@Before
	public void setUp() throws Exception {
		//Se crean los objetos para los test posteriores
		transaccionAux = new TransaccionProductoJpaController();
		Calendar calendario = new GregorianCalendar();
        Date fecha = calendario.getTime();
        Transaccion tr = new Transaccion();
        tr.setNombreCliente("Maria");
        tr.setTipo("ENTRADA");
        tr.setFecha(fecha);
        transaccionLogica = new TransaccionLogica();
        transaccion = transaccionLogica.registrarTransaccion(tr);
       
		
		producto = new Producto();
		producto.setNombre("Producto1");
		producto.setCantidad(100);
		producto.setCosto(5000);
		producto.setEstado("DISPONIBLE");
		listaProductos = new ArrayList<Producto>();
		listaProductos.add(producto);
		
		//Se crean los gestores de base de datos de los objetos anteriores y se guardan en la base de datos
		ProductoLogica = new ProductoLogica();
		ProductoLogica.registrarProducto(producto);
		
		//Se crea un objeto transaccion para las pruebas	
		transaccionProductoLogica = new TransaccionProductoLogica();
	}

	@After
	public void tearDown() throws Exception {
		transaccionProductoLogica = null;
		producto = null;
		transaccion = null;
		listaProductos = null;
		ProductoLogica = null;
		transaccionLogica = null;
	}
	
	@Test
	public void testRegistrarProductosError() {
		try {
			transaccionProductoLogica.registrarTransaccionProductos(null, transaccion);
			fail("Error al crear una transaccion producto");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Lista de productos vacio, por favor ingrese los productos");
		}
	}

	@Test
	public void testRegistrarTransaccionError() {
		try {
			transaccionProductoLogica.registrarTransaccionProductos(listaProductos, null);
			fail("Error al crear una transaccion producto");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Ingrese un id de transaccion correcto");
		}
	}
	
	@Test
	public void testValidadDisponibilidadProductos() {
		try {
			transaccionProductoLogica.validarDisponibilidadProductos(listaProductos);
		} catch (Exception e) {
			fail("Error al validar disponibilidad de productos");
		}
	}
	
	@Test
	public void testValidadDisponibilidadProductosAgotados() {
		try {
			listaProductos.get(0).setCantidad(10000);
			transaccionProductoLogica.validarDisponibilidadProductos(listaProductos);
			fail("Error al validar disponbilidad de productos cuando se encuentras agotados");
		} catch (Exception e) {			
			assertEquals(e.getMessage(), "Solo se encuentran disponibles 100und. del producto: Producto1");
		}
	}
	
	@Test 
	public void testBuscarTransaccionProducto() {
		try {
			transaccionProductoLogica.buscarTransaccionProducto(1);
		} catch (Exception e) {
			fail("Error al buscar una transaccionProducto");
		}
	}
}
