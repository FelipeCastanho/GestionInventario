package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.TransaccionProducto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-09-23T20:18:53")
@StaticMetamodel(Transaccion.class)
public class Transaccion_ { 

    public static volatile SingularAttribute<Transaccion, Date> fecha;
    public static volatile SingularAttribute<Transaccion, String> tipo;
    public static volatile SingularAttribute<Transaccion, String> nombreCliente;
    public static volatile ListAttribute<Transaccion, TransaccionProducto> transaccionProductoList;
    public static volatile SingularAttribute<Transaccion, Integer> id;

}