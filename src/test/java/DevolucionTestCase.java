package test.java;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.logica.DevolucionLogica;
import main.java.logica.ProductoLogica;
import main.java.logica.TransaccionLogica;
import main.java.logica.TransaccionProductoLogica;
import main.java.modelo.Devolucion;
import main.java.modelo.Producto;
import main.java.modelo.Transaccion;
import main.java.modelo.TransaccionProducto;


public class DevolucionTestCase {
	Devolucion devolucionEsperada;
	DevolucionLogica devolucionLogica;
	Transaccion transaccion1;
	TransaccionLogica transaccionLogica;
	TransaccionProductoLogica transaccionProductoLogica;
	Producto producto1;
	Producto producto2;
	ProductoLogica productoLogica;
	Date fecha;
	
	
	@Before
	public void setUp() throws Exception {
		productoLogica = new ProductoLogica();
		devolucionLogica = new DevolucionLogica();
		transaccionLogica = new TransaccionLogica();
		transaccionProductoLogica = new TransaccionProductoLogica();
		Calendar calendar = new GregorianCalendar();
        Date fecha = calendar.getTime();
	    
	    producto2 = new Producto();
	    producto2.setNombre("MemoriaUSB 4GB");
	    producto2.setCantidad(10);
	    producto2.setCosto(12000);
	    
	    producto1 = new Producto();
	    producto1.setNombre("Cargador");
	    producto1.setCantidad(7);
	    producto1.setCosto(7000);
	    
	    transaccion1 = new Transaccion();
	    transaccion1.setNombreCliente("JHON");
	    transaccion1.setTipo("ENTRADA");
	    transaccion1.setFecha(fecha);

	    devolucionEsperada = new Devolucion();
	    devolucionEsperada.setFecha(fecha);
	    devolucionEsperada.setNombreCliente("JHON");
	    
	  
	}

	@After
	public void tearDown() throws Exception {
		productoLogica = null;
		producto1 = null;
		producto2 = null;
		
		devolucionEsperada = null;
		devolucionLogica = null;
		transaccion1 = null;
		transaccionLogica = null;
		transaccionProductoLogica = null;
	}
	
	@Test
	public void testRegistrarDevolucion() throws Exception {
		productoLogica.registrarProducto(producto1);
		productoLogica.registrarProducto(producto2);
		transaccionLogica.registrarTransaccion(transaccion1);
		List<Transaccion> transacciones = transaccionLogica.listarTransacciones();
		List<Producto> listaProductos = productoLogica.listarProductos();
		transaccionProductoLogica.registrarTransaccionProductos(listaProductos, transacciones.get(0));
		
		List<TransaccionProducto> listaTransaccionesProducto = transaccionProductoLogica.listarProductosTransaccion(transacciones.get(0).getId());
		
		devolucionEsperada.setIdTransaccionProducto(listaTransaccionesProducto.get(0));
		devolucionLogica.registrarDevolucion(devolucionEsperada);
		List<Devolucion> listadevolucionObtenida = devolucionLogica.listarDevoluciones();
		assertEquals(devolucionEsperada,listadevolucionObtenida.get(0));
	}
	
	@Test
	public void testBuscarDevolucion() {
		List<Transaccion> transacciones = transaccionLogica.listarTransacciones();
		List<TransaccionProducto> listaTransaccionesProducto = transaccionProductoLogica.listarProductosTransaccion(transacciones.get(0).getId());
		devolucionEsperada.setIdTransaccionProducto(listaTransaccionesProducto.get(0));
		List<Devolucion> listaDevolucionObtenida = devolucionLogica.listarDevoluciones();
		devolucionEsperada.setId(listaDevolucionObtenida.get(0).getId());
		List<Devolucion> listaDevolucionEsperada = new ArrayList<Devolucion>();
		listaDevolucionEsperada.add(devolucionEsperada);
		
		assertEquals(listaDevolucionEsperada, listaDevolucionObtenida);
		
	}
}
