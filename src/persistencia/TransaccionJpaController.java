package persistencia;

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
import modelo.Transaccion;
import modelo.TransaccionProducto;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 * Esta clase es la persistencia que se comunica con el modelo Transaccion y su respectiva base de datos.
 * @author Felipe
 */

public class TransaccionJpaController implements Serializable {

    public TransaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public TransaccionJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TallerGestionInventarioPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Esta funcion crea una transaccion en la base de datos.
     * @param transaccion objeto transaccion a crear.
     */
    
    public Transaccion create(Transaccion transaccion) {
        if (transaccion.getTransaccionProductoList() == null) {
            transaccion.setTransaccionProductoList(new ArrayList<TransaccionProducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TransaccionProducto> attachedTransaccionProductoList = new ArrayList<TransaccionProducto>();
            for (TransaccionProducto transaccionProductoListTransaccionProductoToAttach : transaccion.getTransaccionProductoList()) {
                transaccionProductoListTransaccionProductoToAttach = 
                em.getReference(transaccionProductoListTransaccionProductoToAttach.getClass(), 
                transaccionProductoListTransaccionProductoToAttach.getId());
                attachedTransaccionProductoList.add(transaccionProductoListTransaccionProductoToAttach);
            }
            transaccion.setTransaccionProductoList(attachedTransaccionProductoList);
            em.persist(transaccion);
            for (TransaccionProducto transaccionProductoListTransaccionProducto : transaccion.getTransaccionProductoList()) {
                Transaccion oldIdTransaccionOfTransaccionProductoListTransaccionProducto = 
                transaccionProductoListTransaccionProducto.getIdTransaccion();
                transaccionProductoListTransaccionProducto.setIdTransaccion(transaccion);
                transaccionProductoListTransaccionProducto = em.merge(transaccionProductoListTransaccionProducto);
                if (oldIdTransaccionOfTransaccionProductoListTransaccionProducto != null) {
                    oldIdTransaccionOfTransaccionProductoListTransaccionProducto.getTransaccionProductoList()
                    .remove(transaccionProductoListTransaccionProducto);
                    oldIdTransaccionOfTransaccionProductoListTransaccionProducto 
                    = em.merge(oldIdTransaccionOfTransaccionProductoListTransaccionProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return transaccion;
    }
    
    /**
     * Esta funcion permite modificar una transaccion alojada en la base de datos.
     * @param transaccion objeto transaccion a modificar.
     */

    public void edit(Transaccion transaccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaccion persistentTransaccion = em.find(Transaccion.class, transaccion.getId());
            List<TransaccionProducto> transaccionProductoListOld = persistentTransaccion.getTransaccionProductoList();
            List<TransaccionProducto> transaccionProductoListNew = transaccion.getTransaccionProductoList();
            List<String> illegalOrphanMessages = null;
            for (TransaccionProducto transaccionProductoListOldTransaccionProducto : transaccionProductoListOld) {
                if (!transaccionProductoListNew.contains(transaccionProductoListOldTransaccionProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransaccionProducto " + transaccionProductoListOldTransaccionProducto 
                    + " since its idTransaccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<TransaccionProducto> attachedTransaccionProductoListNew = new ArrayList<TransaccionProducto>();
            for (TransaccionProducto transaccionProductoListNewTransaccionProductoToAttach : transaccionProductoListNew) {
                transaccionProductoListNewTransaccionProductoToAttach = 
                em.getReference(transaccionProductoListNewTransaccionProductoToAttach.getClass(), 
                transaccionProductoListNewTransaccionProductoToAttach.getId());
                attachedTransaccionProductoListNew.add(transaccionProductoListNewTransaccionProductoToAttach);
            }
            transaccionProductoListNew = attachedTransaccionProductoListNew;
            transaccion.setTransaccionProductoList(transaccionProductoListNew);
            transaccion = em.merge(transaccion);
            for (TransaccionProducto transaccionProductoListNewTransaccionProducto : transaccionProductoListNew) {
                if (!transaccionProductoListOld.contains(transaccionProductoListNewTransaccionProducto)) {
                    Transaccion oldIdTransaccionOfTransaccionProductoListNewTransaccionProducto = 
                    transaccionProductoListNewTransaccionProducto.getIdTransaccion();
                    transaccionProductoListNewTransaccionProducto.setIdTransaccion(transaccion);
                    transaccionProductoListNewTransaccionProducto = em.merge(transaccionProductoListNewTransaccionProducto);
                    if (oldIdTransaccionOfTransaccionProductoListNewTransaccionProducto != null 
                    && !oldIdTransaccionOfTransaccionProductoListNewTransaccionProducto.equals(transaccion)) {
                    oldIdTransaccionOfTransaccionProductoListNewTransaccionProducto.getTransaccionProductoList()
                    .remove(transaccionProductoListNewTransaccionProducto);
                    oldIdTransaccionOfTransaccionProductoListNewTransaccionProducto = 
                    em.merge(oldIdTransaccionOfTransaccionProductoListNewTransaccionProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transaccion.getId();
                if (findTransaccion(id) == null) {
                    throw new NonexistentEntityException("The transaccion with id " + id + " no longer exists.");
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
     * Esta funcion permite eliminar una transaccion alojada en la base de datos.
     * @param id identificador del objeto transaccion a eliminar.
     */

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaccion transaccion;
            try {
                transaccion = em.getReference(Transaccion.class, id);
                transaccion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TransaccionProducto> transaccionProductoListOrphanCheck = transaccion.getTransaccionProductoList();
            for (TransaccionProducto transaccionProductoListOrphanCheckTransaccionProducto : transaccionProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaccion (" + transaccion + ") cannot be destroyed since the TransaccionProducto " 
                + transaccionProductoListOrphanCheckTransaccionProducto 
                + " in its transaccionProductoList field has a non-nullable idTransaccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(transaccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaccion> findTransaccionEntities() {
        return findTransaccionEntities(true, -1, -1);
    }

    public List<Transaccion> findTransaccionEntities(int maxResults, int firstResult) {
        return findTransaccionEntities(false, maxResults, firstResult);
    }

    private List<Transaccion> findTransaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaccion.class));
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
     * Esta funcion permite identificar una transaccion alojada en la base de datos.
     * @param id identificador del objeto transaccion a identificar.
     */

    public Transaccion findTransaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaccion.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Esta funcion permite conocer el total de transacciones alojadas en la base de datos.
     */
    
    public int getTransaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaccion> rt = cq.from(Transaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Esta funcion permite obtener una lista de las transacciones alojadas en la base de datos
     * en una fecha en especifico.
     * @param fecha fecha del objeto transaccion.
     */
    
    public List<Transaccion> findTransaccionByDate(String fecha) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("SELECT * FROM transaccion where fecha >= '" + fecha + " 00:00:00' AND fecha <= '" + fecha + " 23:59:59';",
            Transaccion.class);  
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
