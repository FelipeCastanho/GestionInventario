/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Devolucion;
import modelo.Producto;
import modelo.Transaccion;
import modelo.TransaccionProducto;
import persistencia.DevolucionJpaController;
/**
 * Esta clase representa la logica de una devolución de un pedido realizado anteriormente.
 * @author aleji
 */

public class DevolucionLogica {
    DevolucionJpaController devolucionDAO;
    TransaccionLogica transaccionLogica;
    TransaccionProductoLogica transaccionProductoLogica;
    ProductoLogica productoLogica;
    
    /**
    * Constructor de {@code Devolucion}.
    */
    public DevolucionLogica() {
        devolucionDAO = new DevolucionJpaController();
        transaccionLogica = new TransaccionLogica();
        transaccionProductoLogica = new TransaccionProductoLogica();
        productoLogica = new ProductoLogica();
    }
    
    /**
    * Registra una {@code Devolucion} de una {@code TransaccionProducto}.
    * Primero se valida que los parametros no sean nulos o vacios,
    * Luego, dependiendo si la {@code TransaccionProducto} pertenece a una {@code Transaccion} de tipo ENTRADA o SALIDA 
    * se registra una transaccion del tipo contrario. Finalmente, se registra la devolucion
    * y se modifica el costo y cantidad de la {@code TransaccionProducto} a 0.
    * 
    * @param devolucion la {@code Devolucion} con los datos: Fecha, TransaccionProducto y Cliente. 
    */
    public void registrarDevolucion(Devolucion devolucion) throws Exception {
        if (devolucion == null) {
            throw new Exception("Devolucion vacia");  
        } else {
            if (devolucion.getIdTransaccionProducto() == null) {
            	throw new Exception("Se debe ingresar un id de transaccion producto");
            }
            if (devolucion.getNombreCliente().equals("") || devolucion.getNombreCliente() == null) {
            	throw new Exception("Se debe ingresar el nombre del cliente que hace la devolucion");
            }
            if (devolucion.getFecha() == null) {
            	throw new Exception("Se debe ingresar la fecha de la devolucion"); 
            }
        }
        
        TransaccionProducto tp = devolucion.getIdTransaccionProducto();
        
        Transaccion t = tp.getIdTransaccion();
        Transaccion tdevolucion = new Transaccion();
        tdevolucion.setNombreCliente(devolucion.getNombreCliente());
        tdevolucion.setFecha(devolucion.getFecha());
        if (t.getTipo().equals("SALIDA")) {
            tdevolucion.setTipo("ENTRADA");
            tdevolucion = transaccionLogica.registrarTransaccion(tdevolucion);
        } else if (t.getTipo().equals("ENTRADA")) {
            tdevolucion.setTipo("SALIDA");
            tdevolucion = transaccionLogica.registrarTransaccion(tdevolucion);
        }
        
        List<Producto> productos = new ArrayList<>();
        Producto p = productoLogica.buscarProducto(tp.getIdProducto().getNombre());
        p.setCantidad(tp.getCantidad());
        p.setCosto(tp.getValorUnitario());
        productos.add(p);
        
        transaccionProductoLogica.registrarTransaccionProductos(productos, tdevolucion);
        
        tp.setCantidad(0);
        tp.setValorUnitario(0);
        transaccionProductoLogica.modificarTransaccionProducto(tp);
        
        devolucionDAO.create(devolucion);
    }
    
    /**
    * Busca una {@code Devolucion}.
    * Primero se valida que los parametros no sean vacios o nulos,
    * Luego, se busca la devolucion por fecha y nombre del cliente que la realizo.
    * 
    * @param nombreCliente {@code String} para comparar
    * @param fecha {@code Date} para comparar
    * @return la lista de {@code Devolucion} filtradas con los parametros
    */
    public List<Devolucion> buscarDevolucion(String nombreCliente, Date fecha) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String f = sd.format(fecha);
        List<Devolucion> devoluciones = devolucionDAO.findDevolucionByDate(f);
        
        List<Devolucion> resultado = new ArrayList<>();
        for (int i = 0; i < devoluciones.size(); i++) {
           if (devoluciones.get(i).getNombreCliente().equals(nombreCliente)) {
               resultado.add(devoluciones.get(i));
           } 
        }
        return resultado;
    }
    
    
    /**
    * Lista toda {@code Devolucion}.
    * 
    * @return la lista de todos las {@code Devoluciones}
    */ 
    public List<Devolucion> listarDevoluciones() {
        return devolucionDAO.findDevolucionEntities();
    }
}
