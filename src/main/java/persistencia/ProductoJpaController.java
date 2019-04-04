package main.java.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import main.java.modelo.Producto;
import main.java.modelo.TransaccionProducto;
import main.java.persistencia.exceptions.IllegalOrphanException;
import main.java.persistencia.exceptions.NonexistentEntityException;

/**
 * Esta clase es la persistencia que se comunica con el modelo Producto y su respectiva base de datos.
 * @author Felipe
 */

public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public ProductoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("GestionInventarioPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    /**
     * Esta funcion crea un producto en la base de datos.
     * @param producto objeto producto a crear.
     */

    public void create(Producto producto) {
        if (producto.getTransaccionProductoList() == null) {
            producto.setTransaccionProductoList(new ArrayList<TransaccionProducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TransaccionProducto> attachedTransaccionProductoList = new ArrayList<TransaccionProducto>();
            for (TransaccionProducto transaccionProductoListTransaccionProductoToAttach : producto.getTransaccionProductoList()) {
                transaccionProductoListTransaccionProductoToAttach = em.getReference(transaccionProductoListTransaccionProductoToAttach.getClass(), 
                transaccionProductoListTransaccionProductoToAttach.getId());
                attachedTransaccionProductoList.add(transaccionProductoListTransaccionProductoToAttach);
            }
            producto.setTransaccionProductoList(attachedTransaccionProductoList);
            em.persist(producto);
            for (TransaccionProducto transaccionProductoListTransaccionProducto : producto.getTransaccionProductoList()) {
                Producto oldIdProductoOfTransaccionProductoListTransaccionProducto = transaccionProductoListTransaccionProducto.getIdProducto();
                transaccionProductoListTransaccionProducto.setIdProducto(producto);
                transaccionProductoListTransaccionProducto = em.merge(transaccionProductoListTransaccionProducto);
                if (oldIdProductoOfTransaccionProductoListTransaccionProducto != null) {
                    oldIdProductoOfTransaccionProductoListTransaccionProducto.getTransaccionProductoList()
                    .remove(transaccionProductoListTransaccionProducto);
                    oldIdProductoOfTransaccionProductoListTransaccionProducto = em.merge(oldIdProductoOfTransaccionProductoListTransaccionProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /**
     * Esta funcion permite modificar un producto alojado en la base de datos.
     * @param producto objeto producto a modificar.
     */

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            List<TransaccionProducto> transaccionProductoListOld = persistentProducto.getTransaccionProductoList();
            List<TransaccionProducto> transaccionProductoListNew = producto.getTransaccionProductoList();
            List<String> illegalOrphanMessages = null;
            for (TransaccionProducto transaccionProductoListOldTransaccionProducto : transaccionProductoListOld) {
                if (!transaccionProductoListNew.contains(transaccionProductoListOldTransaccionProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransaccionProducto " + transaccionProductoListOldTransaccionProducto 
                    + " since its idProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<TransaccionProducto> attachedTransaccionProductoListNew = new ArrayList<TransaccionProducto>();
            for (TransaccionProducto transaccionProductoListNewTransaccionProductoToAttach : transaccionProductoListNew) {
                transaccionProductoListNewTransaccionProductoToAttach = em.getReference(transaccionProductoListNewTransaccionProductoToAttach
                .getClass(), transaccionProductoListNewTransaccionProductoToAttach.getId());
                attachedTransaccionProductoListNew.add(transaccionProductoListNewTransaccionProductoToAttach);
            }
            transaccionProductoListNew = attachedTransaccionProductoListNew;
            producto.setTransaccionProductoList(transaccionProductoListNew);
            producto = em.merge(producto);
            for (TransaccionProducto transaccionProductoListNewTransaccionProducto : transaccionProductoListNew) {
                if (!transaccionProductoListOld.contains(transaccionProductoListNewTransaccionProducto)) {
                    Producto oldIdProductoOfTransaccionProductoListNewTransaccionProducto = transaccionProductoListNewTransaccionProducto
                    .getIdProducto();
                    transaccionProductoListNewTransaccionProducto.setIdProducto(producto);
                    transaccionProductoListNewTransaccionProducto = em.merge(transaccionProductoListNewTransaccionProducto);
                    if (oldIdProductoOfTransaccionProductoListNewTransaccionProducto != null 
                    && !oldIdProductoOfTransaccionProductoListNewTransaccionProducto.equals(producto)) {
                    oldIdProductoOfTransaccionProductoListNewTransaccionProducto.getTransaccionProductoList()
                    .remove(transaccionProductoListNewTransaccionProducto);
                    oldIdProductoOfTransaccionProductoListNewTransaccionProducto = 
                    em.merge(oldIdProductoOfTransaccionProductoListNewTransaccionProducto);
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
    
    /**
     * Esta funcion permite eliminar un producto alojado en la base de datos.
     * @param id identificador del objeto producto a eliminar.
     */

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
            List<TransaccionProducto> transaccionProductoListOrphanCheck = producto.getTransaccionProductoList();
            for (TransaccionProducto transaccionProductoListOrphanCheckTransaccionProducto : transaccionProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the TransaccionProducto " 
                + transaccionProductoListOrphanCheckTransaccionProducto 
                + " in its transaccionProductoList field has a non-nullable idProducto field.");
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
    
    /**
     * Esta funcion permite identificar un producto alojado en la base de datos.
     * @param id identificador del objeto producto a buscar.
     */

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * Esta funcion permite conocer el total de productos  alojados en la base de datos.
     */

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
