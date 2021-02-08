/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.controlador;

import ec.edu.ups.utils.JPAUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author NANCY
 */
public abstract class ControladorAbstrac <E> {
    private List <E> listaGenerica;
    private Class<E> clase;
    private EntityManager em;

    public abstract List<E> findAll();
    public abstract int codigo();
    
    public ControladorAbstrac() {
        listaGenerica= new ArrayList();
        //java.lang.reflect.Type t= getClass().getGenericSuperclass();
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clase= (Class) pt.getActualTypeArguments()[0];
        em=JPAUtils.getEntityManager();
    }
    public ControladorAbstrac(EntityManager em) {
        listaGenerica= new ArrayList(); 
        //java.lang.reflect.Type t= getClass().getGenericSuperclass();
        Type t =getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clase= (Class) pt.getActualTypeArguments()[0];
        this.em=em;
    }
    
    public boolean crear(E objeto){
        em.getTransaction().begin();
        em.persist(objeto);
        em.getTransaction().commit();
        listaGenerica.add(objeto);
        return true;
    }
    
    public boolean eliminar(E objeto){
        em.getTransaction().begin();
        em.remove(em.merge(objeto));
        em.getTransaction().commit();
        listaGenerica.remove(objeto);
        return true;
    }
    public boolean actualizar(E objeto){
        em.getTransaction().begin();
        em.merge(objeto);
        em.getTransaction().commit();
        return true;
    }
    
    public E buscar (Object id){
        return(E) em.find(clase, id);
    }
    
    public List<E> buscarTodo(){
        return em.createQuery("Select t from " + clase.getSimpleName() + " t").getResultList();
    }

    public List<E> getListaGenerica() {
        return listaGenerica;
    }

    public void setListaGenerica(List<E> listaGenerica) {
        this.listaGenerica = listaGenerica;
    }

    public Class<E> getClase() {
        return clase;
    }

    public void setClase(Class<E> clase) {
        this.clase = clase;
    }

    public EntityManager getEm() {
        return em;
    }
    
    public void setEm(EntityManager em) {
        this.em = em;
    }

}
