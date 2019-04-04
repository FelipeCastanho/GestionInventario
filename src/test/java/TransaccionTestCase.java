package test.java;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import main.java.logica.TransaccionLogica;
import main.java.modelo.Transaccion;

public class TransaccionTestCase {
	
	TransaccionLogica transaccionL;
	Transaccion transaccion;
	Transaccion transaccion2;
	Transaccion transaccion3;
	Date date;

	@Before
	public void setUp() throws Exception {
		
		date = new Date();	
		transaccionL = new TransaccionLogica();
		
		transaccion = new Transaccion();
		transaccion2 = new Transaccion();
		transaccion3 = new Transaccion();
		
		transaccion.setNombreCliente("Daniel Alejandro");
		transaccion.setTipo("ENTRADA");
		transaccion.setFecha(date);
		
		transaccion2.setNombreCliente("Alejandra Aguiar");
		transaccion2.setTipo("ENTRADA");
		transaccion2.setFecha(date);	
		
		transaccion3.setNombreCliente("Felipe Ledesma");
		transaccion3.setTipo("ENTRADA");
		transaccion3.setFecha(date);	
		
	}

	@After
	public void tearDown() throws Exception {
		transaccionL = null;
		transaccion = null;
		transaccion2 = null;
		transaccion3 = null;
	}

	@Test
	public void testRegistrarTransaccion() throws Exception {
		transaccionL.registrarTransaccion(transaccion);
		Transaccion transaccionEncontrada = transaccionL.buscarTransaccion("Daniel Alejandro", date).get(0);
		assertEquals(transaccion, transaccionEncontrada);
	}
	
	@Test
	public void testListarTransaccion() throws Exception {
		int transaccionesEncontradas = transaccionL.buscarTransaccion("Alejandra Aguiar", date).size();
		assertEquals(1, transaccionesEncontradas);
	}
	
	@Test
	public void testBusarTransaccion() throws Exception {
		transaccionL.registrarTransaccion(transaccion2);
		Transaccion transaccionEncontrada = transaccionL.buscarTransaccion("Alejandra Aguiar", date).get(0);
		assertEquals(transaccion2, transaccionEncontrada);
	}

	@Test
	public void testBuscarTransaccionPorID() throws Exception {
		transaccionL.registrarTransaccion(transaccion3);
		int id = transaccion3.getId();
		Transaccion transaccionEncontrada = transaccionL.buscarTransaccion(id);
		assertEquals(transaccion3, transaccionEncontrada);
	}
}