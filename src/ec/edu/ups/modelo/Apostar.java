/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NANCY
 */
@Entity
@Table(name = "apostar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apostar.findAll", query = "SELECT a FROM Apostar a"),
    @NamedQuery(name = "Apostar.findById", query = "SELECT a FROM Apostar a WHERE a.id = :id"),
    @NamedQuery(name = "Apostar.findByGanador", query = "SELECT a FROM Apostar a WHERE a.ganador = :ganador"),
    @NamedQuery(name = "Apostar.findByNumeropartida", query = "SELECT a FROM Apostar a WHERE a.numeropartida = :numeropartida"),
    @NamedQuery(name = "Apostar.findByCantidadapostada", query = "SELECT a FROM Apostar a WHERE a.cantidadapostada = :cantidadapostada"),
    @NamedQuery(name = "Apostar.findByNumeroapostado", query = "SELECT a FROM Apostar a WHERE a.numeroapostado = :numeroapostado"),
    @NamedQuery(name = "Apostar.findByNumeroganador", query = "SELECT a FROM Apostar a WHERE a.numeroganador = :numeroganador"),
    @NamedQuery(name = "Apostar.findBySaldobanca", query = "SELECT a FROM Apostar a WHERE a.saldobanca = :saldobanca"),
    @NamedQuery(name = "Apostar.findBySaldojugador", query = "SELECT a FROM Apostar a WHERE a.saldojugador = :saldojugador"),
    @NamedQuery(name = "Apostar.findByTipoapuesta", query = "SELECT a FROM Apostar a WHERE a.tipoapuesta = :tipoapuesta")})
public class Apostar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "ganador")
    private String ganador;
    @Column(name = "numeropartida")
    private Integer numeropartida;
    @Column(name = "cantidadapostada")
    private Integer cantidadapostada;
    @Size(max = 255)
    @Column(name = "numeroapostado")
    private String numeroapostado;
    @Size(max = 255)
    @Column(name = "numeroganador")
    private String numeroganador;
    @Column(name = "saldobanca")
    private Integer saldobanca;
    @Column(name = "saldojugador")
    private Integer saldojugador;
    @Size(max = 255)
    @Column(name = "tipoapuesta")
    private String tipoapuesta;
    @JoinColumn(name = "codigo_jugador_fk", referencedColumnName = "id")
    @ManyToOne
    private Jugador codigoJugadorFk;

    public Apostar() {
    }

    public Apostar(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public Integer getNumeropartida() {
        return numeropartida;
    }

    public void setNumeropartida(Integer numeropartida) {
        this.numeropartida = numeropartida;
    }

    public Integer getCantidadapostada() {
        return cantidadapostada;
    }

    public void setCantidadapostada(Integer cantidadapostada) {
        this.cantidadapostada = cantidadapostada;
    }

    public String getNumeroapostado() {
        return numeroapostado;
    }

    public void setNumeroapostado(String numeroapostado) {
        this.numeroapostado = numeroapostado;
    }

    public String getNumeroganador() {
        return numeroganador;
    }

    public void setNumeroganador(String numeroganador) {
        this.numeroganador = numeroganador;
    }

    public Integer getSaldobanca() {
        return saldobanca;
    }

    public void setSaldobanca(Integer saldobanca) {
        this.saldobanca = saldobanca;
    }

    public Integer getSaldojugador() {
        return saldojugador;
    }

    public void setSaldojugador(Integer saldojugador) {
        this.saldojugador = saldojugador;
    }

    public String getTipoapuesta() {
        return tipoapuesta;
    }

    public void setTipoapuesta(String tipoapuesta) {
        this.tipoapuesta = tipoapuesta;
    }

    public Jugador getCodigoJugadorFk() {
        return codigoJugadorFk;
    }

    public void setCodigoJugadorFk(Jugador codigoJugadorFk) {
        this.codigoJugadorFk = codigoJugadorFk;
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
        if (!(object instanceof Apostar)) {
            return false;
        }
        Apostar other = (Apostar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Apostar{" + "id=" + id + ", ganador=" + ganador + ", numeropartida=" + numeropartida + ", cantidadapostada=" + cantidadapostada + ", numeroapostado=" + numeroapostado + ", numeroganador=" + numeroganador + ", saldobanca=" + saldobanca + ", saldojugador=" + saldojugador + ", tipoapuesta=" + tipoapuesta + ", codigoJugadorFk=" + codigoJugadorFk + '}';
    }

    
    
}
