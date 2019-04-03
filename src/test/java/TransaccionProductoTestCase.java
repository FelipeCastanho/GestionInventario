package test.java;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.modelo.TransaccionProducto;

public class TransaccionProductoTestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorVacio() {
		TransaccionProducto transaccionProducto = new TransaccionProducto();
	}
	
	@Test
	public void testConstructorId() {
		int id = 1;
		TransaccionProducto transaccionProducto = new TransaccionProducto(id);
		assertEquals(id, transaccionProducto.getId(), 0);
	}
	
	@Test
	public void testConstructorParametros() {
		int id = 1;
		int cantidad = 2;
		int valorUnitario = 200;
		TransaccionProducto transaccionProducto = new TransaccionProducto();
		transaccionProducto.setId(1);
		transaccionProducto.setCantidad(2);
		transaccionProducto.setValorUnitario(200);
		TransaccionProducto transaccionProductoActual = new TransaccionProducto(id, cantidad, valorUnitario);
		assertEquals(transaccionProducto, transaccionProductoActual);
	}

}
