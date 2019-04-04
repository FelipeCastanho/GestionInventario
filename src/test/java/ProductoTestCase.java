package test.java;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.logica.ProductoLogica;
import main.java.modelo.Producto;

public class ProductoTestCase {
	ProductoLogica productoLogica;
	Producto producto1;
	Producto producto2;
	Producto producto3;
	Producto productoActualizado;

	@Before
	public void setUp() throws Exception {
	    productoLogica = new ProductoLogica();
	    
	    producto3 = new Producto();
	    producto3.setNombre("Vidrio Protector");
	    producto3.setCantidad(15);
	    producto3.setCosto(5000);
	    
	    producto2 = new Producto();
	    producto2.setNombre("MemoriaUSB 8GB");
	    producto2.setCantidad(10);
	    producto2.setCosto(22000);
	    
	    producto1 = new Producto();
	    producto1.setNombre("Cable USB");
	    producto1.setCantidad(7);
	    producto1.setCosto(7000);
	}

	@After
	public void tearDown() throws Exception {
		productoLogica = null;
		producto1 = null;
		producto2 = null;
		producto3 = null;
		productoActualizado = null;
	}
	
	@Test
	public void testRegistrarProducto() throws Exception {
		productoLogica.registrarProducto(producto1);
		Producto productoEncontrado = productoLogica.buscarProducto("Cable USB");
		assertEquals(producto1, productoEncontrado);
	}
	
	@Test
	public void testBuscarProducto() throws Exception {
		productoLogica.registrarProducto(producto2);
		Producto productoEncontrado = productoLogica.buscarProducto("MemoriaUSB 8GB");
		assertEquals(producto2, productoEncontrado);
	}
	
	@Test
	public void testBuscarProductoID() throws Exception {
		productoLogica.registrarProducto(producto3);
		Producto productoEncontrado = productoLogica.buscarProducto("Vidrio Protector");
		int id = productoEncontrado.getId();
		Producto productoEncontradoPorId = productoLogica.buscarProductoID(id);
		assertEquals(productoEncontrado, productoEncontradoPorId);
	}
}
