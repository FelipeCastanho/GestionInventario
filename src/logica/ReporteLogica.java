/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import modelo.Devolucion;
import modelo.Producto;
import modelo.Transaccion;
import modelo.TransaccionProducto;
import persistencia.TransaccionJpaController;

/**
 * Esta clase representa la logica de un Reporte teniendo en cuenta funciones como: buscar, crear, modificar y eliminar.
 * @author Felipe
 */
public class ReporteLogica {
    TransaccionLogica transaccionLogica;
    ProductoLogica productoLogica;
    DevolucionLogica devolucionesLogica;

    /**
    * Constructor de {@code Reporte}.
    */
    public ReporteLogica() {
        transaccionLogica = new TransaccionLogica();
        productoLogica = new ProductoLogica();
        devolucionesLogica = new DevolucionLogica();
    }
    
    
    /**
    * Crea un {@code Reporte} basico de {@code Transaccion}.
	* Cada fila tiene 4 columnas
    * La primera columna contiene el id del producto, la segunda la cantidad de entradas/salidas realizadas, la tercera es el costo actual 
    * del producto Y por el ultimo la cuarta columna contiene el totalizado de ventas (cantidad*costo del producto en el momento de la transaccion)
    * 
    * @param tipo {@code String} con el que se har� el reporte
    * @return un arreglo {@code Int} de tantas filas como productos existan en el inventario
    */
    public int[][] reporteBasicoTransacciones(String tipo) { //"ENTRADA"
        //Se consultan todos las entradas en un mes
        Calendar date = new GregorianCalendar();
        Date fecha = date.getTime();
        List<Producto> productosList =  productoLogica.listarProductos(); //Se consulta la lista d productos
        int cantidadProductosExistentes = productosList.get(productosList.size() - 1).getId();
        int[][] resultado = new int[cantidadProductosExistentes][4];
        for (int i = 0; i < cantidadProductosExistentes; i++) { 
        //Se llena la lista para posteriormente facilitar la suma de cantidades entrantes por cada prodcuto
            resultado[i][0] = i + 1;
            resultado[i][1] = 0;
            resultado[i][2] = productosList.get(i).getCosto();
            resultado[i][3] = 0;
        }
        int mes = fecha.getMonth();
        List<Transaccion> transacciones = transaccionLogica.listarTransacciones();
        for (int i = 0; i < transacciones.size(); i++) {
            if (transacciones.get(i).getFecha().getMonth() == mes && transacciones.get(i).getTipo().equals(tipo)) {
                List<TransaccionProducto> productos = transacciones.get(i).getTransaccionProductoList();
                for (int j = 0; j < productos.size(); j++) {
                    resultado[productos.get(j).getIdProducto().getId() - 1][1] +=  productos.get(j).getCantidad();
                    resultado[productos.get(j).getIdProducto().getId() - 1][3] += 
                            productos.get(j).getCantidad() * productos.get(j).getValorUnitario();
                }             
            }
        }
        //Se ordena la lista de producto mas vendido a producto menos vendido
        for (int i = 0; i < cantidadProductosExistentes; i++) {
            for (int j = i + 1; j < cantidadProductosExistentes; j++) {
                if (resultado[i][1] < resultado[j][1]) {
                    int[] aux = resultado[i];
                    resultado[i] = resultado[j];
                    resultado[j] = aux;
                }
            }
        }
        
        System.out.println(resultado.length);
        
        return resultado;
    }
    
    /**
    * Crea un {@code Reporte} basico de {@code Producto}.
	* Las primeras dos posiciones del reporte contiene el producto mas caro 
    * Las ultimas dos posiciones contienen el producto con mayor existencia y el producto con menor existencia con su respectiva cantidad
    * 
    * @return un arreglo {@code Int} con los productos mas representativos
    */
    public int[][] reporteBasicoProductos() { 
        int[][] resultado = new int[4][2];
        for (int i = 0; i < resultado.length; i++) {
            resultado[i][0] = 0;
        }
        resultado[0][1] = Integer.MIN_VALUE;
        resultado[1][1] = Integer.MAX_VALUE;
        resultado[2][1] = Integer.MIN_VALUE;
        resultado[3][1] = Integer.MAX_VALUE;
        
        List<Producto> productosList =  productoLogica.listarProductos(); //Se consulta la lista de productos
        for (int i = 0; i < productosList.size(); i++) {
            if (productosList.get(i).getCosto() > resultado[0][1]) {
                resultado[0][1] = productosList.get(i).getCosto();
                resultado[0][0] = productosList.get(i).getId();
            }
            if (productosList.get(i).getCosto() < resultado[1][1]) {
                resultado[1][1] = productosList.get(i).getCosto();
                resultado[1][0] = productosList.get(i).getId();
            }
            if (productosList.get(i).getCantidad() > resultado[2][1]) {
                resultado[2][1] = productosList.get(i).getCosto();
                resultado[2][0] = productosList.get(i).getId();
            }
            if (productosList.get(i).getCantidad() < resultado[3][1]) {
                resultado[2][1] = productosList.get(i).getCosto();
                resultado[2][0] = productosList.get(i).getId();
            }
        }
        return resultado;
    }
    
    public int cantidadDevoluciones() {
        List<Devolucion> devoluciones = devolucionesLogica.listarDevoluciones();
        return devoluciones.size();
    }
}
