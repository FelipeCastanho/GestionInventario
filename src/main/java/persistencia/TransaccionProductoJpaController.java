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
import main.java.modelo.Devolucion;
import main.java.modelo.Producto;
import main.java.modelo.Transaccion;
import main.java.modelo.TransaccionProducto;
import main.java.persistencia.exceptions.IllegalOrphanException;
import main.java.persistencia.exceptions.NonexistentEntityException;

/**
 * Esta clase es la persistencia que se comunica con el modelo TransaccionProducto y su respectiva base de datos.
 * @author Felipe
 */

public class TransaccionProductoJpaController implements Serializable {

    public TransaccionProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public TransaccionProductoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("GestionInventarioPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    /**
     * Esta funcion crea un objeto relacion entre producto y transaccion en la base de datos.
     * @param transaccionProducto objeto transaccionProducto a crear.
     */

    public void create(TransaccionProducto transaccionProducto) {
        if (transaccionProducto.getDevolucionList() == null) {
            transaccionProducto.setDevolucionList(new ArrayList<Devolucion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = transaccionProducto.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getId());
                transaccionProducto.setIdProducto(idProducto);
            }
            Transaccion idTransaccion = transaccionProducto.getIdTransaccion();
            if (idTransaccion != null) {
                idTransaccion = em.getReference(idTransaccion.getClass(), idTransaccion.getId());
                transaccionProducto.setIdTransaccion(idTransaccion);
            }
            List<Devolucion> attachedDevolucionList = new ArrayList<Devolucion>();
            for (Devolucion devolucionListDevolucionToAttach : transaccionProducto.getDevolucionList()) {
                devolucionListDevolucionToAttach = em.getReference(devolucionListDevolucionToAttach.getClass(), 
                devolucionListDevolucionToAttach.getId());
                attachedDevolucionList.add(devolucionListDevolucionToAttach);
            }
            transaccionProducto.setDevolucionList(attachedDevolucionList);
            em.persist(transaccionProducto);
            if (idProducto != null) {
                idProducto.getTransaccionProductoList().add(transaccionProducto);
                idProducto = em.merge(idProducto);
            }
            if (idTransaccion != null) {
                idTransaccion.getTransaccionProductoList().add(transaccionProducto);
                idTransaccion = em.merge(idTransaccion);
            }
            for (Devolucion devolucionListDevolucion : transaccionProducto.getDevolucionList()) {
                TransaccionProducto oldIdTransaccionProductoOfDevolucionListDevolucion = devolucionListDevolucion.getIdTransaccionProducto();
                devolucionListDevolucion.setIdTransaccionProducto(transaccionProducto);
                devolucionListDevolucion = em.merge(devolucionListDevolucion);
                if (oldIdTransaccionProductoOfDevolucionListDevolucion != null) {
                    oldIdTransaccionProductoOfDevolucionListDevolucion.getDevolucionList().remove(devolucionListDevolucion);
                    oldIdTransaccionProductoOfDevolucionListDevolucion = em.merge(oldIdTransaccionProductoOfDevolucionListDevolucion);
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
     * Esta funcion permite modificar un objeto relacion entre producto y 
     * transaccion alojado en la base de datos.
     * @param transaccionProducto objeto transaccionProducto a modificar.
     */

    public void edit(TransaccionProducto transaccionProducto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransaccionProducto persistentTransaccionProducto = em.find(TransaccionProducto.class, transaccionProducto.getId());
            //idProducto
            Producto idProductoNew = transaccionProducto.getIdProducto();
            //idTransaccionNew
            //idTransaccionOld
            List<Devolucion> devolucionListOld = persistentTransaccionProducto.getDevolucionList();
            List<Devolucion> devolucionListNew = transaccionProducto.getDevolucionList();
            List<String> illegalOrphanMessages = null;
            for (Devolucion devolucionListOldDevolucion : devolucionListOld) {
                if (!devolucionListNew.contains(devolucionListOldDevolucion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Devolucion " + devolucionListOldDevolucion 
                    + " since its idTransaccionProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getId());
                transaccionProducto.setIdProducto(idProductoNew);
            }
            
            Transaccion idTransaccionNew = transaccionProducto.getIdTransaccion();
            
            if (idTransaccionNew != null) {
                idTransaccionNew = em.getReference(idTransaccionNew.getClass(), idTransaccionNew.getId());
                transaccionProducto.setIdTransaccion(idTransaccionNew);
            }
            List<Devolucion> attachedDevolucionListNew = new ArrayList<Devolucion>();
            for (Devolucion devolucionListNewDevolucionToAttach : devolucionListNew) {
                devolucionListNewDevolucionToAttach = 
                em.getReference(devolucionListNewDevolucionToAttach.getClass(), devolucionListNewDevolucionToAttach.getId());
                attachedDevolucionListNew.add(devolucionListNewDevolucionToAttach);
            }
            devolucionListNew = attachedDevolucionListNew;
            transaccionProducto.setDevolucionList(devolucionListNew);
            transaccionProducto = em.merge(transaccionProducto);
            
            Producto idProductoOld = persistentTransaccionProducto.getIdProducto();
            
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getTransaccionProductoList().remove(transaccionProducto);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getTransaccionProductoList().add(transaccionProducto);
                idProductoNew = em.merge(idProductoNew);
            }
            
            Transaccion idTransaccionOld = persistentTransaccionProducto.getIdTransaccion();
            
            if (idTransaccionOld != null && !idTransaccionOld.equals(idTransaccionNew)) {
                idTransaccionOld.getTransaccionProductoList().remove(transaccionProducto);
                idTransaccionOld = em.merge(idTransaccionOld);
            }
            if (idTransaccionNew != null && !idTransaccionNew.equals(idTransaccionOld)) {
                idTransaccionNew.getTransaccionProductoList().add(transaccionProducto);
                idTransaccionNew = em.merge(idTransaccionNew);
            }
            for (Devolucion devolucionListNewDevolucion : devolucionListNew) {
                if (!devolucionListOld.contains(devolucionListNewDevolucion)) {
                TransaccionProducto oldIdTransaccionProductoOfDevolucionListNewDevolucion 
                = devolucionListNewDevolucion.getIdTransaccionProducto();
                devolucionListNewDevolucion.setIdTransaccionProducto(transaccionProducto);
                devolucionListNewDevolucion = em.merge(devolucionListNewDevolucion);
                if (oldIdTransaccionProductoOfDevolucionListNewDevolucion != null 
                && !oldIdTransaccionProductoOfDevolucionListNewDevolucion.equals(transaccionProducto)) {
                oldIdTransaccionProductoOfDevolucionListNewDevolucion.getDevolucionList().remove(devolucionListNewDevolucion);
                oldIdTransaccionProductoOfDevolucionListNewDevolucion = em.merge(oldIdTransaccionProductoOfDevolucionListNewDevolucion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transaccionProducto.getId();
                if (findTransaccionProducto(id) == null) {
                    throw new NonexistentEntityException("The transaccionProducto with id " + id + " no longer exists.");
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
     * Esta funcion permite eliminar un objeto relacion entre producto y 
     * transaccion alojado en la base de datos.
     * @param id identificador del objeto transaccionProducto a eliminar.
     */


    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransaccionProducto transaccionProducto;
            try {
                transaccionProducto = em.getReference(TransaccionProducto.class, id);
                transaccionProducto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaccionProducto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Devolucion> devolucionListOrphanCheck = transaccionProducto.getDevolucionList();
            for (Devolucion devolucionListOrphanCheckDevolucion : devolucionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TransaccionProducto (" + transaccionProducto + ") cannot be destroyed since the Devolucion " 
                + devolucionListOrphanCheckDevolucion + " in its devolucionList field has a non-nullable idTransaccionProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Producto idProducto = transaccionProducto.getIdProducto();
            if (idProducto != null) {
                idProducto.getTransaccionProductoList().remove(transaccionProducto);
                idProducto = em.merge(idProducto);
            }
            Transaccion idTransaccion = transaccionProducto.getIdTransaccion();
            if (idTransaccion != null) {
                idTransaccion.getTransaccionProductoList().remove(transaccionProducto);
                idTransaccion = em.merge(idTransaccion);
            }
            em.remove(transaccionProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TransaccionProducto> findTransaccionProductoEntities() {
        return findTransaccionProductoEntities(true, -1, -1);
    }

    public List<TransaccionProducto> findTransaccionProductoEntities(int maxResults, int firstResult) {
        return findTransaccionProductoEntities(false, maxResults, firstResult);
    }

    private List<TransaccionProducto> findTransaccionProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransaccionProducto.class));
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
     * Esta funcion permite identificar un objeto relacion entre producto y 
     * transaccion alojado en la base de datos.
     * @param id identificador del objeto transaccionProducto a buscar.
     */

    public TransaccionProducto findTransaccionProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransaccionProducto.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * Esta funcion permite conocer el total de objetos en la relacion
     * producto y transaccion  alojados en la base de datos.
     */

    public int getTransaccionProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransaccionProducto> rt = cq.from(TransaccionProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Esta funcion permite obtener una lista de los 
     * objetos dados de la relacion producto transaccion alojados en la base de datos.
     * @param id id del objeto transaccion.
     */
    
    public List<TransaccionProducto> findTransaccionProductoByIdTransaccion(int id) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("SELECT * FROM transaccion_producto where idTransaccion = '" + id + "';", TransaccionProducto.class);  
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
