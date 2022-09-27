/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Producto;
import entity.Productosxventa;
import entity.Venta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jonat
 */
public class ProductosxventaJpaController implements Serializable {

    public ProductosxventaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_persistence");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ProductosxventaJpaController() {
    }

    public void create(Productosxventa productosxventa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto productoId = productosxventa.getProductoId();
            if (productoId != null) {
                productoId = em.getReference(productoId.getClass(), productoId.getId());
                productosxventa.setProductoId(productoId);
            }
            Venta ventaId = productosxventa.getVentaId();
            if (ventaId != null) {
                ventaId = em.getReference(ventaId.getClass(), ventaId.getId());
                productosxventa.setVentaId(ventaId);
            }
            em.persist(productosxventa);
            if (productoId != null) {
                productoId.getProductosxventaCollection().add(productosxventa);
                productoId = em.merge(productoId);
            }
            if (ventaId != null) {
                ventaId.getProductosxventaCollection().add(productosxventa);
                ventaId = em.merge(ventaId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productosxventa productosxventa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productosxventa persistentProductosxventa = em.find(Productosxventa.class, productosxventa.getId());
            Producto productoIdOld = persistentProductosxventa.getProductoId();
            Producto productoIdNew = productosxventa.getProductoId();
            Venta ventaIdOld = persistentProductosxventa.getVentaId();
            Venta ventaIdNew = productosxventa.getVentaId();
            if (productoIdNew != null) {
                productoIdNew = em.getReference(productoIdNew.getClass(), productoIdNew.getId());
                productosxventa.setProductoId(productoIdNew);
            }
            if (ventaIdNew != null) {
                ventaIdNew = em.getReference(ventaIdNew.getClass(), ventaIdNew.getId());
                productosxventa.setVentaId(ventaIdNew);
            }
            productosxventa = em.merge(productosxventa);
            if (productoIdOld != null && !productoIdOld.equals(productoIdNew)) {
                productoIdOld.getProductosxventaCollection().remove(productosxventa);
                productoIdOld = em.merge(productoIdOld);
            }
            if (productoIdNew != null && !productoIdNew.equals(productoIdOld)) {
                productoIdNew.getProductosxventaCollection().add(productosxventa);
                productoIdNew = em.merge(productoIdNew);
            }
            if (ventaIdOld != null && !ventaIdOld.equals(ventaIdNew)) {
                ventaIdOld.getProductosxventaCollection().remove(productosxventa);
                ventaIdOld = em.merge(ventaIdOld);
            }
            if (ventaIdNew != null && !ventaIdNew.equals(ventaIdOld)) {
                ventaIdNew.getProductosxventaCollection().add(productosxventa);
                ventaIdNew = em.merge(ventaIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productosxventa.getId();
                if (findProductosxventa(id) == null) {
                    throw new NonexistentEntityException("The productosxventa with id " + id + " no longer exists.");
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
            Productosxventa productosxventa;
            try {
                productosxventa = em.getReference(Productosxventa.class, id);
                productosxventa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productosxventa with id " + id + " no longer exists.", enfe);
            }
            Producto productoId = productosxventa.getProductoId();
            if (productoId != null) {
                productoId.getProductosxventaCollection().remove(productosxventa);
                productoId = em.merge(productoId);
            }
            Venta ventaId = productosxventa.getVentaId();
            if (ventaId != null) {
                ventaId.getProductosxventaCollection().remove(productosxventa);
                ventaId = em.merge(ventaId);
            }
            em.remove(productosxventa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productosxventa> findProductosxventaEntities() {
        return findProductosxventaEntities(true, -1, -1);
    }

    public List<Productosxventa> findProductosxventaEntities(int maxResults, int firstResult) {
        return findProductosxventaEntities(false, maxResults, firstResult);
    }

    private List<Productosxventa> findProductosxventaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productosxventa.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Productosxventa findProductosxventa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productosxventa.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductosxventaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productosxventa> rt = cq.from(Productosxventa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
