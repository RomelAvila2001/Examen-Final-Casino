/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.contro;

import ec.edu.ups.contro.exceptions.NonexistentEntityException;
import ec.edu.ups.modelo.Apostar;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import ec.edu.ups.modelo.Jugador;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author NANCY
 */
public class ApostarJpaController implements Serializable {

    public ApostarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Apostar apostar) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador codigoJugadorFk = apostar.getCodigoJugadorFk();
            if (codigoJugadorFk != null) {
                codigoJugadorFk = em.getReference(codigoJugadorFk.getClass(), codigoJugadorFk.getId());
                apostar.setCodigoJugadorFk(codigoJugadorFk);
            }
            em.persist(apostar);
            if (codigoJugadorFk != null) {
                codigoJugadorFk.getApostarCollection().add(apostar);
                codigoJugadorFk = em.merge(codigoJugadorFk);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Apostar apostar) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apostar persistentApostar = em.find(Apostar.class, apostar.getId());
            Jugador codigoJugadorFkOld = persistentApostar.getCodigoJugadorFk();
            Jugador codigoJugadorFkNew = apostar.getCodigoJugadorFk();
            if (codigoJugadorFkNew != null) {
                codigoJugadorFkNew = em.getReference(codigoJugadorFkNew.getClass(), codigoJugadorFkNew.getId());
                apostar.setCodigoJugadorFk(codigoJugadorFkNew);
            }
            apostar = em.merge(apostar);
            if (codigoJugadorFkOld != null && !codigoJugadorFkOld.equals(codigoJugadorFkNew)) {
                codigoJugadorFkOld.getApostarCollection().remove(apostar);
                codigoJugadorFkOld = em.merge(codigoJugadorFkOld);
            }
            if (codigoJugadorFkNew != null && !codigoJugadorFkNew.equals(codigoJugadorFkOld)) {
                codigoJugadorFkNew.getApostarCollection().add(apostar);
                codigoJugadorFkNew = em.merge(codigoJugadorFkNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = apostar.getId();
                if (findApostar(id) == null) {
                    throw new NonexistentEntityException("The apostar with id " + id + " no longer exists.");
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
            Apostar apostar;
            try {
                apostar = em.getReference(Apostar.class, id);
                apostar.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The apostar with id " + id + " no longer exists.", enfe);
            }
            Jugador codigoJugadorFk = apostar.getCodigoJugadorFk();
            if (codigoJugadorFk != null) {
                codigoJugadorFk.getApostarCollection().remove(apostar);
                codigoJugadorFk = em.merge(codigoJugadorFk);
            }
            em.remove(apostar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Apostar> findApostarEntities() {
        return findApostarEntities(true, -1, -1);
    }

    public List<Apostar> findApostarEntities(int maxResults, int firstResult) {
        return findApostarEntities(false, maxResults, firstResult);
    }

    private List<Apostar> findApostarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Apostar as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Apostar findApostar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Apostar.class, id);
        } finally {
            em.close();
        }
    }

    public int getApostarCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Apostar as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
