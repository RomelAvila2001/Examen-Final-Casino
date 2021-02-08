/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.contro;

import ec.edu.ups.contro.exceptions.NonexistentEntityException;
import ec.edu.ups.modelo.Banca;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author NANCY
 */
public class BancaJpaController implements Serializable {

    public BancaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Banca banca) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(banca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Banca banca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            banca = em.merge(banca);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = banca.getId();
                if (findBanca(id) == null) {
                    throw new NonexistentEntityException("The banca with id " + id + " no longer exists.");
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
            Banca banca;
            try {
                banca = em.getReference(Banca.class, id);
                banca.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The banca with id " + id + " no longer exists.", enfe);
            }
            em.remove(banca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Banca> findBancaEntities() {
        return findBancaEntities(true, -1, -1);
    }

    public List<Banca> findBancaEntities(int maxResults, int firstResult) {
        return findBancaEntities(false, maxResults, firstResult);
    }

    private List<Banca> findBancaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Banca as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Banca findBanca(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Banca.class, id);
        } finally {
            em.close();
        }
    }

    public int getBancaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Banca as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
