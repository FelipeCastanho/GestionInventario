package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.TransaccionProducto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-09-23T20:18:53")
@StaticMetamodel(Devolucion.class)
public class Devolucion_ { 

    public static volatile SingularAttribute<Devolucion, Date> fecha;
    public static volatile SingularAttribute<Devolucion, String> nombreCliente;
    public static volatile SingularAttribute<Devolucion, Integer> id;
    public static volatile SingularAttribute<Devolucion, TransaccionProducto> idTransaccionProducto;

}