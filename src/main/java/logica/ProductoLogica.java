/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.logica;

import java.util.List;
import main.java.persistencia.ProductoJpaController;
import main.java.modelo.Producto;

/**
 * Esta clase representa la logica de un producto teniendo en cuenta funciones como: buscar, crear, modificar y eliminar.
 * @author Felipe
 */
public class ProductoLogica {
    ProductoJpaController productoDAO;
    
    public ProductoLogica() {
        productoDAO = new ProductoJpaController();
    }
    
    /**
     * Esta función registra un {@code Producto}.
     * @param producto objeto de la clase Producto. 
     */
    public void registrarProducto(Producto producto) throws Exception {
        if (producto.getNombre().equals("") || producto.getNombre() == null) {
            throw new Exception("El producto debe tener un nombre");
        }
        if (producto.getCantidad() < 0) {
            throw new Exception("Cantidad invalida");
        }
        if (producto.getCosto() < 0) {
            throw new Exception("Costo invalido");
        }
        producto.setEstado("DISPONIBLE");
        productoDAO.create(producto);
    }
    
    /**
     * Esta función actualiza los atributos de un {@code Producto}.
     * Se valida que los parametros no sean nulos o negativos.
     * 
     * @param producto {@code Producto} que se quiere actualizar.
     * @param cantidadNueva {@code int} disponible en bodega.
     * @param precioNuevo {@code int} para actualizar.
     * @param tipo {@code boolean} para actualizar.
     *  
     */
    public void actualizarProductos(Producto producto, int cantidadNueva, int precioNuevo, boolean tipo) throws Exception {
        if (cantidadNueva < 0) {
            throw new Exception("Cantidad invalida");
        }
        if (precioNuevo < 0) {
            throw new Exception("Costo invalido");
        }
        if (tipo == false && cantidadNueva > producto.getCantidad()) {
            throw new Exception("Existencias insuficientes");
        }
        if (tipo) {
            int valorPosterior = producto.getCantidad() * producto.getCosto();
            int valorActual = cantidadNueva * precioNuevo;
            producto.setCantidad(cantidadNueva + producto.getCantidad());
            producto.setCosto((valorActual + valorPosterior) / producto.getCantidad());
        } else {
            producto.setCantidad(producto.getCantidad() - cantidadNueva);
        }
        productoDAO.edit(producto);
    }
    
    /**
    * Busca un {@code Producto}.
    * Primero se valida que los parametros no sean vacios o nulos,
    * Luego, se busca el producto por nombre.
    * 
    * @param nombre {@code String} para comparar
    * @return el {@code Producto} filtrado con el parametro
    */
    public Producto buscarProducto(String nombre) throws Exception {
        if (nombre.equals("")) {
            throw new Exception("Por favor ingrese un nombre de producto");
        }
        List<Producto> productos = productoDAO.findProductoEntities();
        Producto producto = null;
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getNombre().equals(nombre) && productos.get(i).getEstado().equals("DISPONIBLE")) {
                producto = productos.get(i);
                break;
            }
        }
        if (producto == null) {
            throw new Exception("El producto no se encuentra registrado");
        }
        return producto;
    }
    
    /**
    * Busca un {@code Producto}.
    * Primero se valida que los parametros no sean vacios o nulos,
    * Luego, se busca el producto por id.
    * 
    * @param id {@code Int} para comparar
    * @return el {@code Producto} filtrado con el parametro
    */
    public Producto buscarProductoID(int id)throws Exception {
        Producto producto = productoDAO.findProducto(id);
        if (producto == null) {
            throw new Exception("El producto no se encuentra registrado");
        }
        return producto;
    }
    
    
    public void eliminarProducto(Producto producto) throws Exception {
        producto.setEstado("DE BAJA");
        productoDAO.edit(producto);
    }
    
    public List<Producto> listarProductos() {
        return productoDAO.findProductoEntities();
    }
}
