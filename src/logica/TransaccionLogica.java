/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import modelo.Transaccion;
import persistencia.TransaccionJpaController;
import java.util.List;

/**
 *
 * @author aleji
 */
public class TransaccionLogica {
    TransaccionJpaController transaccionDAO;
    
    public TransaccionLogica(){
        transaccionDAO = new TransaccionJpaController();
    }
    
    //Retorna el Id de la transaccion registrada
    public Transaccion registrarTransaccion(Transaccion transaccion) throws Exception{
        if (transaccion  == null) throw new Exception("Transaccion vacia");     
                
        if(transaccion.getNombreCliente().equals("") || transaccion.getNombreCliente() == null){
            throw new Exception("Se debe ingresar el nombre del cliente");
        }
        if(transaccion.getTipo().equals("") || transaccion.getTipo() == null){
            throw new Exception("La transaccion debe tener un tipo");
        }
        if(transaccion.getFecha() == null){
            throw new Exception("Se debe ingresar una fecha");
        }

        return transaccionDAO.create(transaccion);  
    }
    
    //Retorna la lista de transacciones que cumplen con el nombre y la fecha
    public List<Transaccion> buscarTransaccion(String nombreCliente, Date fecha){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String f = sd.format(fecha);
        List<Transaccion> transacciones = transaccionDAO.findTransaccionByDate(f);
        List<Transaccion> resultado = new ArrayList<>();
        for(int i = 0; i < transacciones.size(); i++) {
           if(transacciones.get(i).getNombreCliente().equals(nombreCliente)){
               resultado.add(transacciones.get(i));
           } 
        }
        return resultado;
    }
    
}
