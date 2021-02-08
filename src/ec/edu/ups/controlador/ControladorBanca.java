/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.controlador;

import ec.edu.ups.modelo.Banca;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author NANCY
 */
public class ControladorBanca extends ControladorAbstrac<Banca>{

    @Override
    public List<Banca> findAll() {
        Query consulta = getEm().createNamedQuery("Banca.findAll");
        return consulta.getResultList();
    }

    @Override
    public int codigo() {
        return 1;
    }
    
}
