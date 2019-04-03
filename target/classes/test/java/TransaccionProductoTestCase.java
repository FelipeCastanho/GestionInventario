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

public class TransaccionProductoTestCase {

	private TransaccionProductoLogica transaccionProducto;
	private Producto producto;
	private Transaccion transaccion;
	private List<Producto> listaProductos;
	private ProductoLogica ProductoLogica;
	private TransaccionLogica transaccionLogica;

	@Before
	public void setUp() throws Exception {
		//Se crean los objetos para los test posteriores

		Calendar calendario = new GregorianCalendar();
        Date fecha = calendario.getTime();
        Transaccion tr = new Transaccion();
        tr.setNombreCliente("Maria");
        tr.setTipo("ENTRADA");
        tr.setFecha(fecha);
        transaccionLogica = new TransaccionLogica();
        Transaccion niu = transaccionLogica.registrarTransaccion(tr);
		
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
		transaccionProducto = new TransaccionProductoLogica();
	}

	@After
	public void tearDown() throws Exception {
		transaccionProducto = null;
	}

	@Test
	public void testRegistrar() {

	}

}
