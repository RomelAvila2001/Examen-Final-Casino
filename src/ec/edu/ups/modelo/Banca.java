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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NANCY
 */
@Entity
@Table(name = "banca")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Banca.findAll", query = "SELECT b FROM Banca b"),
    @NamedQuery(name = "Banca.findById", query = "SELECT b FROM Banca b WHERE b.id = :id"),
    @NamedQuery(name = "Banca.findBySaldo", query = "SELECT b FROM Banca b WHERE b.saldo = :saldo")})
public class Banca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "saldo")
    private Integer saldo;

    public Banca() {
    }

    public Banca(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public synchronized Integer getSaldo() {
        return saldo;
    }

    public synchronized void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public synchronized int aumentarSaldo(Thread thread){
        if(thread.getName().contains("N")){
            if(getSaldo()>360){
                return 360;
            }else{
                return 0;
            }
        }else if(thread.getName().contains("PI")){
            if(getSaldo()>20){
                return 20;
            }else{
                return 0;
            }
        }else{
            return 360;
        }
    }
    
    public synchronized int aumentarSaldo(Thread thread, int dineroApuesta){
        if(getSaldo()>dineroApuesta * 36){
            return dineroApuesta * 36;
        }else{
            return 0;
        }
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
        if (!(object instanceof Banca)) {
            return false;
        }
        Banca other = (Banca) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.ups.modelo.Banca[ id=" + id + " ]";
    }
    
}
