/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;
import java.util.List;
import modelo.Producto;
import persistencia.ProductoJpaController;

/**
 *
 * @author Felipe
 */
public class ProductoLogica {
    ProductoJpaController productoDAO;
    
    public ProductoLogica(){
        productoDAO = new ProductoJpaController();
    }
    
    public void registrarProducto(Producto producto) throws Exception{
        //Validar el id del equipo
        if(producto.getNombre() == "" || producto.getNombre() == null){
            throw new Exception("El producto debe tener un nombre");
        }
        if(producto.getCantidad() < 0){
            throw new Exception("Cantidad invalida");
        }
        if(producto.getCosto()< 0){
            throw new Exception("Costo invalido");
        }
        producto.setEstado("DISPONIBLE");
        productoDAO.create(producto);
    }
    
    public void ActualizarProductos(Producto producto, int cantidadNueva, int precioNuevo, boolean tipo) throws Exception{
        //Validar el id del equipo
        if(cantidadNueva < 0){
            throw new Exception("Cantidad invalida");
        }
        if(precioNuevo < 0){
            throw new Exception("Costo invalido");
        }
        if(tipo == false && cantidadNueva > producto.getCantidad()){
            throw new Exception("Existencias insuficientes");
        }
        if(tipo){
            int valorPosterior = producto.getCantidad() * producto.getCosto();
            int valorActual = cantidadNueva * precioNuevo;
            producto.setCantidad(cantidadNueva+producto.getCantidad());
            producto.setCosto((valorActual+valorPosterior)/producto.getCantidad());
        }else{
            producto.setCantidad(producto.getCantidad() - cantidadNueva);
        }
        productoDAO.edit(producto);
    }
    
    public Producto buscarProducto(String nombre) throws Exception{
        if(nombre == "" || nombre == null){
            throw new Exception("Por favor ingrese un nombre de producto");
        }
        List<Producto> productos = productoDAO.findProductoEntities();
        Producto producto = null;
        for(int i = 0; i < productos.size(); i++){
            if(productos.get(i).getNombre().equals(nombre) && productos.get(i).getEstado().equals("DISPONIBLE")){
                producto = productos.get(i);
                break;
            }
        }
        if(producto == null){
            throw new Exception("El producto no se encuentra registrado");
        }
        return producto;
    }
    
    public void eliminarProducto(Producto producto) throws Exception{
        producto.setEstado("DE BAJA");
        productoDAO.edit(producto);
    }
}
