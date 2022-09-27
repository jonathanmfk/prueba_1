/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jonat
 */
@Entity
@Table(name = "productosxventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productosxventa.findAll", query = "SELECT p FROM Productosxventa p"),
    @NamedQuery(name = "Productosxventa.findById", query = "SELECT p FROM Productosxventa p WHERE p.id = :id"),
    @NamedQuery(name = "Productosxventa.findByCantidad", query = "SELECT p FROM Productosxventa p WHERE p.cantidad = :cantidad")})
public class Productosxventa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    @ManyToOne
    private Producto productoId;
    @JoinColumn(name = "venta_id", referencedColumnName = "id")
    @ManyToOne
    private Venta ventaId;

    public Productosxventa() {
    }

    public Productosxventa(Integer id) {
        this.id = id;
    }

    public Productosxventa(Integer id, Integer cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProductoId() {
        return productoId;
    }

    public void setProductoId(Producto productoId) {
        this.productoId = productoId;
    }

    public Venta getVentaId() {
        return ventaId;
    }

    public void setVentaId(Venta ventaId) {
        this.ventaId = ventaId;
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
        if (!(object instanceof Productosxventa)) {
            return false;
        }
        Productosxventa other = (Productosxventa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Productosxventa[ id=" + id + " ]";
    }
    
}
