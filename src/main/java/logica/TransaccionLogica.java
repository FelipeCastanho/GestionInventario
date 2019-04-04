/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
<<<<<<< HEAD:src/logica/TransaccionLogica.java

package logica;
=======
package main.java.logica;
>>>>>>> feature/testsTransaccionProducto:src/main/java/logica/TransaccionLogica.java

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
<<<<<<< HEAD:src/logica/TransaccionLogica.java
import java.util.List;
import modelo.Transaccion;
import persistencia.TransaccionJpaController;

=======
import main.java.modelo.Transaccion;
import main.java.persistencia.TransaccionJpaController;
import java.util.List;
>>>>>>> feature/testsTransaccionProducto:src/main/java/logica/TransaccionLogica.java

/**
 * Esta clase representa la logica de una Transaccion teniendo en cuenta funciones como: buscar, crear, modificar y eliminar.
 * @author aleji
 */
public class TransaccionLogica {
    TransaccionJpaController transaccionDAO;
    
    public TransaccionLogica() {
        transaccionDAO = new TransaccionJpaController();
    }
    
    /**
    * Registra una {@code Transaccion}.
    * Primero se valida que los atributos que la componen no esten vacios,
    * Luego, se pasa como parametro a la funcion del JpaController que finalmente la crea.
    * 
    * @param transaccion {@code Transaccion} que se crear�
<<<<<<< HEAD:src/logica/TransaccionLogica.java
    * @return el objeto {@code Transaccion} que se crea
=======
    * @return el objeto {@code Transaccion} que se cre�
>>>>>>> feature/testsTransaccionProducto:src/main/java/logica/TransaccionLogica.java
    */
    public Transaccion registrarTransaccion(Transaccion transaccion) throws Exception {
        if (transaccion  == null) {
        	throw new Exception("Transaccion vacia");
        }
        if (transaccion.getNombreCliente().equals("") || transaccion.getNombreCliente() == null) {
            throw new Exception("Se debe ingresar el nombre del cliente");
        }
        if (transaccion.getTipo().equals("") || transaccion.getTipo() == null) {
            throw new Exception("La transaccion debe tener un tipo");
        }
        if (transaccion.getFecha() == null) {
            throw new Exception("Se debe ingresar una fecha");
        }

        return transaccionDAO.create(transaccion);  
    }
    
    /**
    * Busca una {@code Transaccion}.
    * Primero se valida que los parametros no sean vacios o nulos,
    * Luego, se busca la transaccion por fecha y nombre del cliente que realiz�.
    * 
    * @param nombreCliente {@code String} para comparar
    * @param fecha {@code Date} para comparar
    * @return la lista de {@code Transaccion} filtradas con los parametros
    */
<<<<<<< HEAD:src/logica/TransaccionLogica.java
    public List<Transaccion> buscarTransaccion(String nombreCliente, Date fecha) throws Exception {
        if (nombreCliente.equals("")) { 
        	throw new Exception("Ingrese el nombre de cliente a buscar"); 
        }
        if (fecha == null) {
        	throw new Exception("Ingrese la fecha en la que desea buscar la transaccion");
        }
=======
    public List<Transaccion> buscarTransaccion(String nombreCliente, Date fecha) throws Exception{
        if(nombreCliente.equals("")) throw new Exception("Ingrese el nombre de cliente a buscar");
        if(fecha == null) throw new Exception("Ingrese la fecha en la que desea buscar la transacci�n");
>>>>>>> feature/testsTransaccionProducto:src/main/java/logica/TransaccionLogica.java
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String f = sd.format(fecha);
        List<Transaccion> transacciones = transaccionDAO.findTransaccionByDate(f);
        List<Transaccion> resultado = new ArrayList<>();
        for (int i = 0; i < transacciones.size(); i++) {
           if (transacciones.get(i).getNombreCliente().equals(nombreCliente)) {
               resultado.add(transacciones.get(i));
           } 
        }
        if (resultado.isEmpty()) {
            throw new Exception("No se encontraron transacciones");
        }
        return resultado;
    }
    
    /**
    * Busca una {@code Transaccion} por su id.
    * 
    * @param id {@code int} para comparar
    * @return {@code Transaccion}
    */ 
    public Transaccion buscarTransaccion(int id) throws Exception {
        if (id < 0) {
        	throw new Exception("El  id  de transaccion debe ser mayor a 0");
        }
        return transaccionDAO.findTransaccion(id);
    }
    
    /**
    * Lista los objetos {@code Transaccion}.
    * 
    * @return la lista de todos las {@code Transacciones}
    */ 
    public List<Transaccion> listarTransacciones() {
        return transaccionDAO.findTransaccionEntities();
    }
}
