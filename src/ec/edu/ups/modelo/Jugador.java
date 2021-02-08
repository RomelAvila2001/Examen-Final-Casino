/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author NANCY
 */
@Entity
@Table(name = "jugador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j"),
    @NamedQuery(name = "Jugador.findById", query = "SELECT j FROM Jugador j WHERE j.id = :id"),
    @NamedQuery(name = "Jugador.findByNombre", query = "SELECT j FROM Jugador j WHERE j.nombre = :nombre"),
    @NamedQuery(name = "Jugador.findByNumeroapostado", query = "SELECT j FROM Jugador j WHERE j.numeroapostado = :numeroapostado"),
    @NamedQuery(name = "Jugador.findByDerrotas", query = "SELECT j FROM Jugador j WHERE j.derrotas = :derrotas"),
    @NamedQuery(name = "Jugador.findByNumeroruleta", query = "SELECT j FROM Jugador j WHERE j.numeroruleta = :numeroruleta"),
    @NamedQuery(name = "Jugador.findBySaldo", query = "SELECT j FROM Jugador j WHERE j.saldo = :saldo"),
    @NamedQuery(name = "Jugador.findByTipoapuesta", query = "SELECT j FROM Jugador j WHERE j.tipoapuesta = :tipoapuesta"),
    @NamedQuery(name = "Jugador.findByVictorias", query = "SELECT j FROM Jugador j WHERE j.victorias = :victorias")})
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 255)
    @Column(name = "numeroapostado")
    private String numeroapostado;
    @Column(name = "derrotas")
    private Integer derrotas;
    @Size(max = 255)
    @Column(name = "numeroruleta")
    private String numeroruleta;
    @Column(name = "saldo")
    private Integer saldo;
    @Size(max = 255)
    @Column(name = "tipoapuesta")
    private String tipoapuesta;
    @Column(name = "victorias")
    private Integer victorias;
    @OneToMany(mappedBy = "codigoJugadorFk")
    private Collection<Apostar> apostarCollection;

    public Jugador() {
    }

    public Jugador(String Nombre) {
        this.nombre = Nombre;
        this.saldo=1000;
        this.victorias=0;
        this.derrotas=0;
        this.numeroapostado="-1";
        this.numeroruleta="-1";
        this.tipoapuesta="";
        
    }
    public Jugador(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroapostado() {
        return numeroapostado;
    }

    public void setNumeroapostado(String numeroapostado) {
        this.numeroapostado = numeroapostado;
    }

    public Integer getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(Integer derrotas) {
        this.derrotas = derrotas;
    }

    public String getNumeroruleta() {
        return numeroruleta;
    }

    public void setNumeroruleta(String numeroruleta) {
        this.numeroruleta = numeroruleta;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public String getTipoapuesta() {
        return tipoapuesta;
    }

    public void setTipoapuesta(String tipoapuesta) {
        this.tipoapuesta = tipoapuesta;
    }

    public Integer getVictorias() {
        return victorias;
    }

    public void setVictorias(Integer victorias) {
        this.victorias = victorias;
    }

    @XmlTransient
    public Collection<Apostar> getApostarCollection() {
        return apostarCollection;
    }

    public void setApostarCollection(Collection<Apostar> apostarCollection) {
        this.apostarCollection = apostarCollection;
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
        if (!(object instanceof Jugador)) {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Jugador{" + "id=" + id + ", nombre=" + nombre + ", numeroapostado=" + numeroapostado + ", derrotas=" + derrotas + ", numeroruleta=" + numeroruleta + ", saldo=" + saldo + ", tipoapuesta=" + tipoapuesta + ", victorias=" + victorias + ", apostarCollection=" + apostarCollection + '}';
    }

    
    
}
