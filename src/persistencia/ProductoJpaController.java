/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Transaccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Producto;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Felipe
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ProductoJpaController(){
        this.emf = Persistence.createEntityManagerFactory("TallerGestionInventarioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getTransaccionList() == null) {
            producto.setTransaccionList(new ArrayList<Transaccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Transaccion> attachedTransaccionList = new ArrayList<Transaccion>();
            for (Transaccion transaccionListTransaccionToAttach : producto.getTransaccionList()) {
                transaccionListTransaccionToAttach = em.getReference(transaccionListTransaccionToAttach.getClass(), transaccionListTransaccionToAttach.getId());
                attachedTransaccionList.add(transaccionListTransaccionToAttach);
            }
            producto.setTransaccionList(attachedTransaccionList);
            em.persist(producto);
            for (Transaccion transaccionListTransaccion : producto.getTransaccionList()) {
                Producto oldIdProductoOfTransaccionListTransaccion = transaccionListTransaccion.getIdProducto();
                transaccionListTransaccion.setIdProducto(producto);
                transaccionListTransaccion = em.merge(transaccionListTransaccion);
                if (oldIdProductoOfTransaccionListTransaccion != null) {
                    oldIdProductoOfTransaccionListTransaccion.getTransaccionList().remove(transaccionListTransaccion);
                    oldIdProductoOfTransaccionListTransaccion = em.merge(oldIdProductoOfTransaccionListTransaccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            List<Transaccion> transaccionListOld = persistentProducto.getTransaccionList();
            List<Transaccion> transaccionListNew = producto.getTransaccionList();
            List<String> illegalOrphanMessages = null;
            for (Transaccion transaccionListOldTransaccion : transaccionListOld) {
                if (!transaccionListNew.contains(transaccionListOldTransaccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transaccion " + transaccionListOldTransaccion + " since its idProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Transaccion> attachedTransaccionListNew = new ArrayList<Transaccion>();
            for (Transaccion transaccionListNewTransaccionToAttach : transaccionListNew) {
                transaccionListNewTransaccionToAttach = em.getReference(transaccionListNewTransaccionToAttach.getClass(), transaccionListNewTransaccionToAttach.getId());
                attachedTransaccionListNew.add(transaccionListNewTransaccionToAttach);
            }
            transaccionListNew = attachedTransaccionListNew;
            producto.setTransaccionList(transaccionListNew);
            producto = em.merge(producto);
            for (Transaccion transaccionListNewTransaccion : transaccionListNew) {
                if (!transaccionListOld.contains(transaccionListNewTransaccion)) {
                    Producto oldIdProductoOfTransaccionListNewTransaccion = transaccionListNewTransaccion.getIdProducto();
                    transaccionListNewTransaccion.setIdProducto(producto);
                    transaccionListNewTransaccion = em.merge(transaccionListNewTransaccion);
                    if (oldIdProductoOfTransaccionListNewTransaccion != null && !oldIdProductoOfTransaccionListNewTransaccion.equals(producto)) {
                        oldIdProductoOfTransaccionListNewTransaccion.getTransaccionList().remove(transaccionListNewTransaccion);
                        oldIdProductoOfTransaccionListNewTransaccion = em.merge(oldIdProductoOfTransaccionListNewTransaccion);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<Transaccion> transaccionListOrphanCheck = producto.getTransaccionList();
            for (Transaccion transaccionListOrphanCheckTransaccion : transaccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Transaccion " + transaccionListOrphanCheckTransaccion + " in its transaccionList field has a non-nullable idProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
