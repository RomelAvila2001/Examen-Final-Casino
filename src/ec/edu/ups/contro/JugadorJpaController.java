/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.contro;

import ec.edu.ups.contro.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import ec.edu.ups.modelo.Apostar;
import ec.edu.ups.modelo.Jugador;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author NANCY
 */
public class JugadorJpaController implements Serializable {

    public JugadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jugador jugador) {
        if (jugador.getApostarCollection() == null) {
            jugador.setApostarCollection(new ArrayList<Apostar>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Apostar> attachedApostarCollection = new ArrayList<Apostar>();
            for (Apostar apostarCollectionApostarToAttach : jugador.getApostarCollection()) {
                apostarCollectionApostarToAttach = em.getReference(apostarCollectionApostarToAttach.getClass(), apostarCollectionApostarToAttach.getId());
                attachedApostarCollection.add(apostarCollectionApostarToAttach);
            }
            jugador.setApostarCollection(attachedApostarCollection);
            em.persist(jugador);
            for (Apostar apostarCollectionApostar : jugador.getApostarCollection()) {
                Jugador oldCodigoJugadorFkOfApostarCollectionApostar = apostarCollectionApostar.getCodigoJugadorFk();
                apostarCollectionApostar.setCodigoJugadorFk(jugador);
                apostarCollectionApostar = em.merge(apostarCollectionApostar);
                if (oldCodigoJugadorFkOfApostarCollectionApostar != null) {
                    oldCodigoJugadorFkOfApostarCollectionApostar.getApostarCollection().remove(apostarCollectionApostar);
                    oldCodigoJugadorFkOfApostarCollectionApostar = em.merge(oldCodigoJugadorFkOfApostarCollectionApostar);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jugador jugador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador persistentJugador = em.find(Jugador.class, jugador.getId());
            Collection<Apostar> apostarCollectionOld = persistentJugador.getApostarCollection();
            Collection<Apostar> apostarCollectionNew = jugador.getApostarCollection();
            Collection<Apostar> attachedApostarCollectionNew = new ArrayList<Apostar>();
            for (Apostar apostarCollectionNewApostarToAttach : apostarCollectionNew) {
                apostarCollectionNewApostarToAttach = em.getReference(apostarCollectionNewApostarToAttach.getClass(), apostarCollectionNewApostarToAttach.getId());
                attachedApostarCollectionNew.add(apostarCollectionNewApostarToAttach);
            }
            apostarCollectionNew = attachedApostarCollectionNew;
            jugador.setApostarCollection(apostarCollectionNew);
            jugador = em.merge(jugador);
            for (Apostar apostarCollectionOldApostar : apostarCollectionOld) {
                if (!apostarCollectionNew.contains(apostarCollectionOldApostar)) {
                    apostarCollectionOldApostar.setCodigoJugadorFk(null);
                    apostarCollectionOldApostar = em.merge(apostarCollectionOldApostar);
                }
            }
            for (Apostar apostarCollectionNewApostar : apostarCollectionNew) {
                if (!apostarCollectionOld.contains(apostarCollectionNewApostar)) {
                    Jugador oldCodigoJugadorFkOfApostarCollectionNewApostar = apostarCollectionNewApostar.getCodigoJugadorFk();
                    apostarCollectionNewApostar.setCodigoJugadorFk(jugador);
                    apostarCollectionNewApostar = em.merge(apostarCollectionNewApostar);
                    if (oldCodigoJugadorFkOfApostarCollectionNewApostar != null && !oldCodigoJugadorFkOfApostarCollectionNewApostar.equals(jugador)) {
                        oldCodigoJugadorFkOfApostarCollectionNewApostar.getApostarCollection().remove(apostarCollectionNewApostar);
                        oldCodigoJugadorFkOfApostarCollectionNewApostar = em.merge(oldCodigoJugadorFkOfApostarCollectionNewApostar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jugador.getId();
                if (findJugador(id) == null) {
                    throw new NonexistentEntityException("The jugador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador jugador;
            try {
                jugador = em.getReference(Jugador.class, id);
                jugador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jugador with id " + id + " no longer exists.", enfe);
            }
            Collection<Apostar> apostarCollection = jugador.getApostarCollection();
            for (Apostar apostarCollectionApostar : apostarCollection) {
                apostarCollectionApostar.setCodigoJugadorFk(null);
                apostarCollectionApostar = em.merge(apostarCollectionApostar);
            }
            em.remove(jugador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jugador> findJugadorEntities() {
        return findJugadorEntities(true, -1, -1);
    }

    public List<Jugador> findJugadorEntities(int maxResults, int firstResult) {
        return findJugadorEntities(false, maxResults, firstResult);
    }

    private List<Jugador> findJugadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Jugador as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Jugador findJugador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jugador.class, id);
        } finally {
            em.close();
        }
    }

    public int getJugadorCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Jugador as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
