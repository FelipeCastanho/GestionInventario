/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.logica;

import java.util.List;
import main.java.modelo.Producto;
import main.java.modelo.Transaccion;
import main.java.modelo.TransaccionProducto;
import main.java.persistencia.TransaccionProductoJpaController;

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
    
    /**
    * Registra las {@code TransaccionProducto} de una {@code Transaccion}.
    * Primero se valida que los parametros no sean nulos,
    * Luego, dependiendo si la {@code Transaccion} es de tipo ENTRADA o SALIDA se valida si la cantidad de productos 
    * esta disponible o si el producto existe, se modifican y finalmente se registra cada {@code TransaccionProducto}.
    * 
    * @param productos {@code List<Producto>} que contiene todos los productos de la transaccion
    * @param objTransaccion {@code Transaccion} que contiene el id y tipo necesarios
    */
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
    
    /**
    * Valida la cantidad de unidades en una lista de {@code Producto}
    * Si las unidades del {@code Producto} que se va a salir de inventario es mayor a lo disponible, retorna una excepción.
    * 
    * @param productos {@code List<Producto>} que contiene todos los productos de la transaccion
    */  
    public void validarDisponibilidadProductos(List<Producto> productos) throws Exception{
        for(int i = 0; i < productos.size(); i++) {
            Producto pdcto = productoLogica.buscarProducto(productos.get(i).getNombre());
            if(productos.get(i).getCantidad() > pdcto.getCantidad()){
                throw new Exception("Solo se encuentran disponibles "+pdcto.getCantidad()+"und. del producto: "+
                        pdcto.getNombre());
            }
        }
    }
    
    /**
    * Valida si un {@code Producto} se encuentra en inventario
    * Si el {@code Producto} que entra a inventario no existe, se registra con las unidades de la transacción.
    * 
    * @param productos {@code List<Producto>} que contiene todos los productos de la transaccion
    */  
    public void validarRegistroProductos(List<Producto> productos) throws Exception{
        for(int i = 0; i < productos.size(); i++) {
            try{
                productoLogica.buscarProducto(productos.get(i).getNombre());
            }catch(Exception e){
                productoLogica.registrarProducto(productos.get(i));
            }
        }
    }
    
    /**
    * Lista {@code TransaccionProducto} por el id de la Transacción a la que pertenecen.
    * 
    * @param idTransaccion {@code int} para comparar
    * @return {@code List<TransaccionProducto>}
    */ 
    public List<TransaccionProducto> listarProductosTransaccion(int idTransaccion){
        return transaccionProductoDAO.findTransaccionProductoByIdTransaccion(idTransaccion);
    }
    
    
    /**
    * Modifica una {@code TransaccionProducto}.
    * Se valida que los parametros no sean nulos o negativos.
    * 
    * @param tp la {@code TransaccionProducto} ya modificada
    */
    public void modificarTransaccionProducto(TransaccionProducto tp) throws Exception{
        if(tp == null){
            throw new Exception("La transaccion de producto esta vacia");
        }else{
            if(tp.getIdProducto() == null) throw new Exception("Se debe ingresar un id de Producto"); 
            if(tp.getIdTransaccion() == null) throw new Exception("Se debe ingresar un id de Transaccion");
            if(tp.getValorUnitario() < 0) throw new Exception("Valor unitario de transaccion producto menor a 0");
            if(tp.getCantidad() < 0) throw new Exception("Cantidad de transaccion producto menor a 0"); 
        }
        transaccionProductoDAO.edit(tp);
    }
    
    /**
    * Busca una {@code TransaccionProducto} por su id.
    * 
    * @param id {@code int} para comparar
    * @return {@code TransaccionProducto}
    */ 
    public TransaccionProducto buscarTransaccionProducto(int id) throws Exception{
        if(id < 0) throw new Exception("El  id debe ser mayor a 0");
        return transaccionProductoDAO.findTransaccionProducto(id);
    }
}
    

