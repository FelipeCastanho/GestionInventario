package main.java.persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import main.java.modelo.Devolucion;
import main.java.modelo.TransaccionProducto;
import main.java.persistencia.exceptions.NonexistentEntityException;

/**
 * Esta clase es la persistencia que se comunica con el modelo Devolucion y su respectiva base de datos.
 * @author Felipe
 */

public class DevolucionJpaController implements Serializable {

	public DevolucionJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}

    private EntityManagerFactory emf = null;
    
    public DevolucionJpaController() {
        this.emf = Persistence.createEntityManagerFactory("GestionInventarioPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Esta funcion crea una devolucion en la base de datos a traves de una transaccion.
     * @param devolucion objeto devolucion a crear.
     */

    public void create(Devolucion devolucion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransaccionProducto idTransaccionProducto = devolucion.getIdTransaccionProducto();
            if (idTransaccionProducto != null) {
                idTransaccionProducto = em.getReference(idTransaccionProducto.getClass(), idTransaccionProducto.getId());
                devolucion.setIdTransaccionProducto(idTransaccionProducto);
            }
            em.persist(devolucion);
            if (idTransaccionProducto != null) {
                idTransaccionProducto.getDevolucionList().add(devolucion);
                idTransaccionProducto = em.merge(idTransaccionProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
   
    
    /**
     * Esta funcion permite modificar una devolucion alojada en la base de datos.
     * @param devolucion objeto devolucion a modificar.
     */

    public void edit(Devolucion devolucion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Devolucion persistentDevolucion = em.find(Devolucion.class, devolucion.getId());
            TransaccionProducto idTransaccionProductoOld = persistentDevolucion.getIdTransaccionProducto();
            TransaccionProducto idTransaccionProductoNew = devolucion.getIdTransaccionProducto();
            if (idTransaccionProductoNew != null) {
                idTransaccionProductoNew = em.getReference(idTransaccionProductoNew.getClass(), idTransaccionProductoNew.getId());
                devolucion.setIdTransaccionProducto(idTransaccionProductoNew);
            }
            devolucion = em.merge(devolucion);
            if (idTransaccionProductoOld != null && !idTransaccionProductoOld.equals(idTransaccionProductoNew)) {
                idTransaccionProductoOld.getDevolucionList().remove(devolucion);
                idTransaccionProductoOld = em.merge(idTransaccionProductoOld);
            }
            if (idTransaccionProductoNew != null && !idTransaccionProductoNew.equals(idTransaccionProductoOld)) {
                idTransaccionProductoNew.getDevolucionList().add(devolucion);
                idTransaccionProductoNew = em.merge(idTransaccionProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = devolucion.getId();
                if (findDevolucion(id) == null) {
                    throw new NonexistentEntityException("The devolucion with id " + id + " no longer exists.");
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
     * Esta funcion permite eliminar una devolucion alojada en la base de datos.
     * @param id identificador del objeto devolucion a eliminar.
     */

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Devolucion devolucion;
            try {
                devolucion = em.getReference(Devolucion.class, id);
                devolucion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The devolucion with id " + id + " no longer exists.", enfe);
            }
            TransaccionProducto idTransaccionProducto = devolucion.getIdTransaccionProducto();
            if (idTransaccionProducto != null) {
                idTransaccionProducto.getDevolucionList().remove(devolucion);
                idTransaccionProducto = em.merge(idTransaccionProducto);
            }
            em.remove(devolucion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Devolucion> findDevolucionEntities() {
        return findDevolucionEntities(true, -1, -1);
    }

    public List<Devolucion> findDevolucionEntities(int maxResults, int firstResult) {
        return findDevolucionEntities(false, maxResults, firstResult);
    }

    private List<Devolucion> findDevolucionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Devolucion.class));
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
     * Esta funcion permite identificar una devolucion alojada en la base de datos.
     * @param id objeto devolucion.
     */

    public Devolucion findDevolucion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Devolucion.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * Esta funcion permite conocer el total de devoluciones alojadas en la base de datos.
     */

    public int getDevolucionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Devolucion> rt = cq.from(Devolucion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Esta funcion permite obtener una lista de las devoluciones alojadas en la base de datos
     * en una fecha en especifico.
     * @param fecha fecha del objeto devolucion.
     */
    
    public List<Devolucion> findDevolucionByDate(String fecha) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("SELECT * FROM devolucion where fecha >= '" + fecha + " 00:00:00' AND fecha <= '" + fecha + " 23:59:59';", 
            Devolucion.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
