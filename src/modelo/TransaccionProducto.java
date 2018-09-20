/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "transaccion_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransaccionProducto.findAll", query = "SELECT t FROM TransaccionProducto t")
    , @NamedQuery(name = "TransaccionProducto.findById", query = "SELECT t FROM TransaccionProducto t WHERE t.id = :id")
    , @NamedQuery(name = "TransaccionProducto.findByCantidad", query = "SELECT t FROM TransaccionProducto t WHERE t.cantidad = :cantidad")
    , @NamedQuery(name = "TransaccionProducto.findByValorUnitario", query = "SELECT t FROM TransaccionProducto t WHERE t.valorUnitario = :valorUnitario")})
public class TransaccionProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "valorUnitario")
    private int valorUnitario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTransaccionProducto")
    private List<Devolucion> devolucionList;
    @JoinColumn(name = "idProducto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Producto idProducto;
    @JoinColumn(name = "idTransaccion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transaccion idTransaccion;

    public TransaccionProducto() {
    }

    public TransaccionProducto(Integer id) {
        this.id = id;
    }

    public TransaccionProducto(Integer id, int cantidad, int valorUnitario) {
        this.id = id;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(int valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @XmlTransient
    public List<Devolucion> getDevolucionList() {
        return devolucionList;
    }

    public void setDevolucionList(List<Devolucion> devolucionList) {
        this.devolucionList = devolucionList;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Transaccion getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Transaccion idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionProducto)) {
            return false;
        }
        TransaccionProducto other = (TransaccionProducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TransaccionProducto[ id=" + id + " ]";
    }
    
}
