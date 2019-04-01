package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.TransaccionProducto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-09-24T22:43:34")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, String> estado;
    public static volatile ListAttribute<Producto, TransaccionProducto> transaccionProductoList;
    public static volatile SingularAttribute<Producto, Integer> costo;
    public static volatile SingularAttribute<Producto, Integer> id;
    public static volatile SingularAttribute<Producto, Integer> cantidad;
    public static volatile SingularAttribute<Producto, String> nombre;

}