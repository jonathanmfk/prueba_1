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
import entity.Editorial;
import entity.Producto;
import entity.TipoProducto;
import entity.Productosxventa;
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
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_persistence");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ProductoJpaController() {
    }

    public void create(Producto producto) {
        if (producto.getProductosxventaCollection() == null) {
            producto.setProductosxventaCollection(new ArrayList<Productosxventa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Editorial editorialid = producto.getEditorialid();
            if (editorialid != null) {
                editorialid = em.getReference(editorialid.getClass(), editorialid.getId());
                producto.setEditorialid(editorialid);
            }
            TipoProducto tipoproductoid = producto.getTipoproductoid();
            if (tipoproductoid != null) {
                tipoproductoid = em.getReference(tipoproductoid.getClass(), tipoproductoid.getId());
                producto.setTipoproductoid(tipoproductoid);
            }
            Collection<Productosxventa> attachedProductosxventaCollection = new ArrayList<Productosxventa>();
            for (Productosxventa productosxventaCollectionProductosxventaToAttach : producto.getProductosxventaCollection()) {
                productosxventaCollectionProductosxventaToAttach = em.getReference(productosxventaCollectionProductosxventaToAttach.getClass(), productosxventaCollectionProductosxventaToAttach.getId());
                attachedProductosxventaCollection.add(productosxventaCollectionProductosxventaToAttach);
            }
            producto.setProductosxventaCollection(attachedProductosxventaCollection);
            em.persist(producto);
            if (editorialid != null) {
                editorialid.getProductoCollection().add(producto);
                editorialid = em.merge(editorialid);
            }
            if (tipoproductoid != null) {
                tipoproductoid.getProductoCollection().add(producto);
                tipoproductoid = em.merge(tipoproductoid);
            }
            for (Productosxventa productosxventaCollectionProductosxventa : producto.getProductosxventaCollection()) {
                Producto oldProductoIdOfProductosxventaCollectionProductosxventa = productosxventaCollectionProductosxventa.getProductoId();
                productosxventaCollectionProductosxventa.setProductoId(producto);
                productosxventaCollectionProductosxventa = em.merge(productosxventaCollectionProductosxventa);
                if (oldProductoIdOfProductosxventaCollectionProductosxventa != null) {
                    oldProductoIdOfProductosxventaCollectionProductosxventa.getProductosxventaCollection().remove(productosxventaCollectionProductosxventa);
                    oldProductoIdOfProductosxventaCollectionProductosxventa = em.merge(oldProductoIdOfProductosxventaCollectionProductosxventa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            Editorial editorialidOld = persistentProducto.getEditorialid();
            Editorial editorialidNew = producto.getEditorialid();
            TipoProducto tipoproductoidOld = persistentProducto.getTipoproductoid();
            TipoProducto tipoproductoidNew = producto.getTipoproductoid();
            Collection<Productosxventa> productosxventaCollectionOld = persistentProducto.getProductosxventaCollection();
            Collection<Productosxventa> productosxventaCollectionNew = producto.getProductosxventaCollection();
            if (editorialidNew != null) {
                editorialidNew = em.getReference(editorialidNew.getClass(), editorialidNew.getId());
                producto.setEditorialid(editorialidNew);
            }
            if (tipoproductoidNew != null) {
                tipoproductoidNew = em.getReference(tipoproductoidNew.getClass(), tipoproductoidNew.getId());
                producto.setTipoproductoid(tipoproductoidNew);
            }
            Collection<Productosxventa> attachedProductosxventaCollectionNew = new ArrayList<Productosxventa>();
            for (Productosxventa productosxventaCollectionNewProductosxventaToAttach : productosxventaCollectionNew) {
                productosxventaCollectionNewProductosxventaToAttach = em.getReference(productosxventaCollectionNewProductosxventaToAttach.getClass(), productosxventaCollectionNewProductosxventaToAttach.getId());
                attachedProductosxventaCollectionNew.add(productosxventaCollectionNewProductosxventaToAttach);
            }
            productosxventaCollectionNew = attachedProductosxventaCollectionNew;
            producto.setProductosxventaCollection(productosxventaCollectionNew);
            producto = em.merge(producto);
            if (editorialidOld != null && !editorialidOld.equals(editorialidNew)) {
                editorialidOld.getProductoCollection().remove(producto);
                editorialidOld = em.merge(editorialidOld);
            }
            if (editorialidNew != null && !editorialidNew.equals(editorialidOld)) {
                editorialidNew.getProductoCollection().add(producto);
                editorialidNew = em.merge(editorialidNew);
            }
            if (tipoproductoidOld != null && !tipoproductoidOld.equals(tipoproductoidNew)) {
                tipoproductoidOld.getProductoCollection().remove(producto);
                tipoproductoidOld = em.merge(tipoproductoidOld);
            }
            if (tipoproductoidNew != null && !tipoproductoidNew.equals(tipoproductoidOld)) {
                tipoproductoidNew.getProductoCollection().add(producto);
                tipoproductoidNew = em.merge(tipoproductoidNew);
            }
            for (Productosxventa productosxventaCollectionOldProductosxventa : productosxventaCollectionOld) {
                if (!productosxventaCollectionNew.contains(productosxventaCollectionOldProductosxventa)) {
                    productosxventaCollectionOldProductosxventa.setProductoId(null);
                    productosxventaCollectionOldProductosxventa = em.merge(productosxventaCollectionOldProductosxventa);
                }
            }
            for (Productosxventa productosxventaCollectionNewProductosxventa : productosxventaCollectionNew) {
                if (!productosxventaCollectionOld.contains(productosxventaCollectionNewProductosxventa)) {
                    Producto oldProductoIdOfProductosxventaCollectionNewProductosxventa = productosxventaCollectionNewProductosxventa.getProductoId();
                    productosxventaCollectionNewProductosxventa.setProductoId(producto);
                    productosxventaCollectionNewProductosxventa = em.merge(productosxventaCollectionNewProductosxventa);
                    if (oldProductoIdOfProductosxventaCollectionNewProductosxventa != null && !oldProductoIdOfProductosxventaCollectionNewProductosxventa.equals(producto)) {
                        oldProductoIdOfProductosxventaCollectionNewProductosxventa.getProductosxventaCollection().remove(productosxventaCollectionNewProductosxventa);
                        oldProductoIdOfProductosxventaCollectionNewProductosxventa = em.merge(oldProductoIdOfProductosxventaCollectionNewProductosxventa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Editorial editorialid = producto.getEditorialid();
            if (editorialid != null) {
                editorialid.getProductoCollection().remove(producto);
                editorialid = em.merge(editorialid);
            }
            TipoProducto tipoproductoid = producto.getTipoproductoid();
            if (tipoproductoid != null) {
                tipoproductoid.getProductoCollection().remove(producto);
                tipoproductoid = em.merge(tipoproductoid);
            }
            Collection<Productosxventa> productosxventaCollection = producto.getProductosxventaCollection();
            for (Productosxventa productosxventaCollectionProductosxventa : productosxventaCollection) {
                productosxventaCollectionProductosxventa.setProductoId(null);
                productosxventaCollectionProductosxventa = em.merge(productosxventaCollectionProductosxventa);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
