/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.controlador;


import ec.edu.ups.modelo.Jugador;
import java.util.Collections;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author NANCY
 */
public class ControladorJugador extends ControladorAbstrac<Jugador>{

    @Override
    public List<Jugador> findAll() {
        Query consulta = getEm().createNamedQuery("Jugador.findAll");
        return consulta.getResultList();
    }

    @Override
    public int codigo() {
        var lista = findAll();
        Collections.sort(lista, (Jugador j1, Jugador j2) -> j1.getId().compareTo(j2.getId()));
        
        if(!lista.isEmpty()){
            return lista.get(lista.size()-1).getId();
        }else{
            return 0;
        }
    }
    
}
