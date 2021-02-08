/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.controlador;


import ec.edu.ups.modelo.Apostar;
import java.util.Collections;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author NANCY
 */
public class ControladorApostar extends ControladorAbstrac<Apostar>{

    @Override
    public List<Apostar> findAll() {
        Query consulta = getEm().createNamedQuery("Apostar.findAll");
        return consulta.getResultList();
    }

    @Override
    public int codigo() {
        var lista = findAll();
        Collections.sort(lista, (Apostar a1, Apostar a2) -> a1.getId().compareTo(a2.getId()));
        return lista.get(lista.size()-1).getId();
    }
    
    public int partidas(){
        var lista = findAll();
        
        if(lista.isEmpty()){
            return 0;
        }else{
            Collections.sort(lista, (Apostar a1, Apostar a2) -> a1.getId().compareTo(a2.getId()));
            return lista.get(lista.size()-1).getId();
        }
    }
}
