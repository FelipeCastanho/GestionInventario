/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Esta clase representa una operación de devolución de un pedido realizado anteriormente.
 * @author Felipe.
 */
@Entity
@Table(name = "devolucion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Devolucion.findAll", query = "SELECT d FROM Devolucion d"),
    @NamedQuery(name = "Devolucion.findById", query = "SELECT d FROM Devolucion d WHERE d.id = :id"),
    @NamedQuery(name = "Devolucion.findByNombreCliente", query = "SELECT d FROM Devolucion d WHERE d.nombreCliente = :nombreCliente"),
    @NamedQuery(name = "Devolucion.findByFecha", query = "SELECT d FROM Devolucion d WHERE d.fecha = :fecha")})
public class Devolucion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombreCliente")
    private String nombreCliente;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "id_transaccion_producto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TransaccionProducto idTransaccionProducto;

    public Devolucion() {
    }

    public Devolucion(Integer id) {
        this.id = id;
    }
    
    /**
     * Esta función crea un objeto devolución.
     * @param id identificador de la devolución.
     * @param nombreCliente nombre completo del cliente a quien se le asigna la devolución.
     * @param fecha en que se hace una devolución.
     */
    public Devolucion(Integer id, String nombreCliente, Date fecha) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public TransaccionProducto getIdTransaccionProducto() {
        return idTransaccionProducto;
    }

    public void setIdTransaccionProducto(TransaccionProducto idTransaccionProducto) {
        this.idTransaccionProducto = idTransaccionProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Devolucion)) {
            return false;
        }
        Devolucion other = (Devolucion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Devolucion[ id=" + id + " ]";
    }
    
}
