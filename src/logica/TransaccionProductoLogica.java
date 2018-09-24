/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Producto;
import modelo.Transaccion;
import modelo.TransaccionProducto;
import persistencia.TransaccionProductoJpaController;

/**
 *
 * @author aleji
 */
public class TransaccionProductoLogica {
    TransaccionProductoJpaController transaccionProductoDAO;
    ProductoLogica productoLogica;
    
    public TransaccionProductoLogica(){
        transaccionProductoDAO = new TransaccionProductoJpaController();
        productoLogica = new ProductoLogica();
    }
    
    public void registrarTransaccionProductos(List<Producto> productos, Transaccion objTransaccion) throws Exception{
        if (productos  == null) throw new Exception("Lista de productos vacio, por favor ingrese los productos");
        if (objTransaccion == null) throw new Exception("Ingrese un id de transaccion correcto");
        
        boolean tipo = false;
        if(objTransaccion.getTipo().equals("ENTRADA")){
            validarRegistroProductos(productos);
            tipo =  true;
        }else if(objTransaccion.getTipo().equals("SALIDA")){
            validarDisponibilidadProductos(productos);
        }
        
        for(int i = 0; i < productos.size(); i++){
           Producto pdcto = productoLogica.buscarProducto(productos.get(i).getNombre());
           TransaccionProducto trn = new TransaccionProducto();
           trn.setIdProducto(pdcto);
           trn.setIdTransaccion(objTransaccion);
           trn.setCantidad(productos.get(i).getCantidad());
           trn.setValorUnitario(productos.get(i).getCosto());
           productoLogica.actualizarProductos(pdcto, productos.get(i).getCantidad(), productos.get(i).getCosto(), tipo);
           transaccionProductoDAO.create(trn);
        }
    }
    
    public void validarDisponibilidadProductos(List<Producto> productos) throws Exception{
        for(int i = 0; i < productos.size(); i++) {
            Producto pdcto = productoLogica.buscarProducto(productos.get(i).getNombre());
            if(productos.get(i).getCantidad() > pdcto.getCantidad()){
                throw new Exception("Solo se encuentran disponibles "+pdcto.getCantidad()+"und. del producto: "+
                        pdcto.getNombre());
            }
        }
    }
    
    public void validarRegistroProductos(List<Producto> productos) throws Exception{
        for(int i = 0; i < productos.size(); i++) {
            try{
                productoLogica.buscarProducto(productos.get(i).getNombre());
            }catch(Exception e){
                productoLogica.registrarProducto(productos.get(i));
            }
        }
    }
}
    

