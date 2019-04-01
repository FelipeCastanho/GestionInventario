/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tallergestioninventario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.DevolucionLogica;
import logica.ProductoLogica;
import logica.TransaccionLogica;
import logica.TransaccionProductoLogica;
import modelo.Devolucion;
import modelo.Producto;
import modelo.Transaccion;
import modelo.TransaccionProducto;

/**
 *
 * @author Felipe
 */
public class TallerGestionInventario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        try {
            /*Calendar hola = new GregorianCalendar();
            Date fecha = hola.getTime();
            Transaccion tr = new Transaccion();
            tr.setNombreCliente("Maria");
            tr.setTipo("ENTRADA");
            tr.setFecha(fecha);
            TransaccionLogica trLogica = new TransaccionLogica();*/
            //Transaccion niu = trLogica.registrarTransaccion(tr);
            //trLogica.buscarTransaccion("Brayan", fecha);



            // TODO code application logic here
            ProductoLogica p = new ProductoLogica();
        
            Producto p1 = new Producto();
            p1.setNombre("Cargador celular");
            p1.setCantidad(5);
            p1.setCosto(10000);
            p.registrarProducto(p1);
            /*Producto p2 = new Producto();
            p2.setNombre("Forro Celular");
            p2.setCantidad(10);
            p2.setCosto(12000);
            
            
            List<Producto> productos = new ArrayList<>();
            productos.add(p1);
            productos.add(p2);
            
            TransaccionProductoLogica trp = new TransaccionProductoLogica();
            //trp.registrarTransaccionProductos(productos, niu);
            
            List<Transaccion> transacciones = trLogica.listarTransacciones();
            List<TransaccionProducto> transaccionesProducto = trp.listarProductosTransaccion(transacciones.get(0).getId());
            
            System.out.print(transaccionesProducto.get(0).getId());
            Devolucion devolucion = new Devolucion();
            devolucion.setFecha(fecha);
            devolucion.setIdTransaccionProducto(transaccionesProducto.get(0));
            devolucion.setNombreCliente("Maria");
            DevolucionLogica devLogica = new DevolucionLogica();
            devLogica.registrarDevolucion(devolucion);*/
            
            
            //System.out.println(p.buscarProducto("c"));
            //p.eliminarProducto(p.buscarProducto("a"));
            //p.ActualizarProductos(p.buscarProducto("b"), 10, 5000, true);*/ //Esto es para agregar prodcutos al inventario
            //p.ActualizarProductos(p.buscarProducto("b"), 20, 0, false); //Esto es para sacar productos del inventario
            //p.ActualizarProductos(p.buscarProducto("a"), 10, 5000, true);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }    
}
