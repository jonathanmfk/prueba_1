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
import entity.Cliente;
import entity.Productosxventa;
import entity.Venta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jonat
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_persistence");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public VentaJpaController() {
    }

    public void create(Venta venta) {
        if (venta.getProductosxventaCollection() == null) {
            venta.setProductosxventaCollection(new ArrayList<Productosxventa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente clienteId = venta.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getId());
                venta.setClienteId(clienteId);
            }
            Collection<Productosxventa> attachedProductosxventaCollection = new ArrayList<Productosxventa>();
            for (Productosxventa productosxventaCollectionProductosxventaToAttach : venta.getProductosxventaCollection()) {
                productosxventaCollectionProductosxventaToAttach = em.getReference(productosxventaCollectionProductosxventaToAttach.getClass(), productosxventaCollectionProductosxventaToAttach.getId());
                attachedProductosxventaCollection.add(productosxventaCollectionProductosxventaToAttach);
            }
            venta.setProductosxventaCollection(attachedProductosxventaCollection);
            em.persist(venta);
            if (clienteId != null) {
                clienteId.getVentaCollection().add(venta);
                clienteId = em.merge(clienteId);
            }
            for (Productosxventa productosxventaCollectionProductosxventa : venta.getProductosxventaCollection()) {
                Venta oldVentaIdOfProductosxventaCollectionProductosxventa = productosxventaCollectionProductosxventa.getVentaId();
                productosxventaCollectionProductosxventa.setVentaId(venta);
                productosxventaCollectionProductosxventa = em.merge(productosxventaCollectionProductosxventa);
                if (oldVentaIdOfProductosxventaCollectionProductosxventa != null) {
                    oldVentaIdOfProductosxventaCollectionProductosxventa.getProductosxventaCollection().remove(productosxventaCollectionProductosxventa);
                    oldVentaIdOfProductosxventaCollectionProductosxventa = em.merge(oldVentaIdOfProductosxventaCollectionProductosxventa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta venta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta persistentVenta = em.find(Venta.class, venta.getId());
            Cliente clienteIdOld = persistentVenta.getClienteId();
            Cliente clienteIdNew = venta.getClienteId();
            Collection<Productosxventa> productosxventaCollectionOld = persistentVenta.getProductosxventaCollection();
            Collection<Productosxventa> productosxventaCollectionNew = venta.getProductosxventaCollection();
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getId());
                venta.setClienteId(clienteIdNew);
            }
            Collection<Productosxventa> attachedProductosxventaCollectionNew = new ArrayList<Productosxventa>();
            for (Productosxventa productosxventaCollectionNewProductosxventaToAttach : productosxventaCollectionNew) {
                productosxventaCollectionNewProductosxventaToAttach = em.getReference(productosxventaCollectionNewProductosxventaToAttach.getClass(), productosxventaCollectionNewProductosxventaToAttach.getId());
                attachedProductosxventaCollectionNew.add(productosxventaCollectionNewProductosxventaToAttach);
            }
            productosxventaCollectionNew = attachedProductosxventaCollectionNew;
            venta.setProductosxventaCollection(productosxventaCollectionNew);
            venta = em.merge(venta);
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getVentaCollection().remove(venta);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getVentaCollection().add(venta);
                clienteIdNew = em.merge(clienteIdNew);
            }
            for (Productosxventa productosxventaCollectionOldProductosxventa : productosxventaCollectionOld) {
                if (!productosxventaCollectionNew.contains(productosxventaCollectionOldProductosxventa)) {
                    productosxventaCollectionOldProductosxventa.setVentaId(null);
                    productosxventaCollectionOldProductosxventa = em.merge(productosxventaCollectionOldProductosxventa);
                }
            }
            for (Productosxventa productosxventaCollectionNewProductosxventa : productosxventaCollectionNew) {
                if (!productosxventaCollectionOld.contains(productosxventaCollectionNewProductosxventa)) {
                    Venta oldVentaIdOfProductosxventaCollectionNewProductosxventa = productosxventaCollectionNewProductosxventa.getVentaId();
                    productosxventaCollectionNewProductosxventa.setVentaId(venta);
                    productosxventaCollectionNewProductosxventa = em.merge(productosxventaCollectionNewProductosxventa);
                    if (oldVentaIdOfProductosxventaCollectionNewProductosxventa != null && !oldVentaIdOfProductosxventaCollectionNewProductosxventa.equals(venta)) {
                        oldVentaIdOfProductosxventaCollectionNewProductosxventa.getProductosxventaCollection().remove(productosxventaCollectionNewProductosxventa);
                        oldVentaIdOfProductosxventaCollectionNewProductosxventa = em.merge(oldVentaIdOfProductosxventaCollectionNewProductosxventa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venta.getId();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
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
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            Cliente clienteId = venta.getClienteId();
            if (clienteId != null) {
                clienteId.getVentaCollection().remove(venta);
                clienteId = em.merge(clienteId);
            }
            Collection<Productosxventa> productosxventaCollection = venta.getProductosxventaCollection();
            for (Productosxventa productosxventaCollectionProductosxventa : productosxventaCollection) {
                productosxventaCollectionProductosxventa.setVentaId(null);
                productosxventaCollectionProductosxventa = em.merge(productosxventaCollectionProductosxventa);
            }
            em.remove(venta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
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

    public Venta findVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
