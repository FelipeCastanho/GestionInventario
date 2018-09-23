/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tallergestioninventario;

import java.util.logging.Level;
import java.util.logging.Logger;
import logica.ProductoLogica;
import modelo.Producto;

/**
 *
 * @author Felipe
 */
public class TallerGestionInventario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        // TODO code application logic here
        ProductoLogica p = new ProductoLogica();
        try {
            /*Producto p1 = new Producto();
            p1.setNombre("a");
            p1.setCantidad(10);
            p1.setCosto(0);
            p.registrarProducto(p1);
            
            System.out.println(p.buscarProducto("c"));
            p.eliminarProducto(p.buscarProducto("a"));
            p.ActualizarProductos(p.buscarProducto("b"), 10, 5000, true);*/ //Esto es para agregar prodcutos al inventario
            //p.ActualizarProductos(p.buscarProducto("b"), 20, 0, false); //Esto es para sacar productos del inventario
            p.ActualizarProductos(p.buscarProducto("a"), 10, 5000, true);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }    
}
