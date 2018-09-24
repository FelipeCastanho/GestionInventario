package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Devolucion;
import modelo.Producto;
import modelo.Transaccion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-09-23T20:18:53")
@StaticMetamodel(TransaccionProducto.class)
public class TransaccionProducto_ { 

    public static volatile SingularAttribute<TransaccionProducto, Transaccion> idTransaccion;
    public static volatile ListAttribute<TransaccionProducto, Devolucion> devolucionList;
    public static volatile SingularAttribute<TransaccionProducto, Integer> id;
    public static volatile SingularAttribute<TransaccionProducto, Integer> cantidad;
    public static volatile SingularAttribute<TransaccionProducto, Producto> idProducto;
    public static volatile SingularAttribute<TransaccionProducto, Integer> valorUnitario;

}