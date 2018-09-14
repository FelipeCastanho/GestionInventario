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
import modelo.Producto;
import modelo.Devolucion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Transaccion;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Felipe
 */
public class TransaccionJpaController implements Serializable {

    public TransaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public TransaccionJpaController(){
        this.emf = Persistence.createEntityManagerFactory("TallerGestionInventarioPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaccion transaccion) {
        if (transaccion.getDevolucionList() == null) {
            transaccion.setDevolucionList(new ArrayList<Devolucion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = transaccion.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getId());
                transaccion.setIdProducto(idProducto);
            }
            List<Devolucion> attachedDevolucionList = new ArrayList<Devolucion>();
            for (Devolucion devolucionListDevolucionToAttach : transaccion.getDevolucionList()) {
                devolucionListDevolucionToAttach = em.getReference(devolucionListDevolucionToAttach.getClass(), devolucionListDevolucionToAttach.getId());
                attachedDevolucionList.add(devolucionListDevolucionToAttach);
            }
            transaccion.setDevolucionList(attachedDevolucionList);
            em.persist(transaccion);
            if (idProducto != null) {
                idProducto.getTransaccionList().add(transaccion);
                idProducto = em.merge(idProducto);
            }
            for (Devolucion devolucionListDevolucion : transaccion.getDevolucionList()) {
                Transaccion oldIdTransaccionOfDevolucionListDevolucion = devolucionListDevolucion.getIdTransaccion();
                devolucionListDevolucion.setIdTransaccion(transaccion);
                devolucionListDevolucion = em.merge(devolucionListDevolucion);
                if (oldIdTransaccionOfDevolucionListDevolucion != null) {
                    oldIdTransaccionOfDevolucionListDevolucion.getDevolucionList().remove(devolucionListDevolucion);
                    oldIdTransaccionOfDevolucionListDevolucion = em.merge(oldIdTransaccionOfDevolucionListDevolucion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaccion transaccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaccion persistentTransaccion = em.find(Transaccion.class, transaccion.getId());
            Producto idProductoOld = persistentTransaccion.getIdProducto();
            Producto idProductoNew = transaccion.getIdProducto();
            List<Devolucion> devolucionListOld = persistentTransaccion.getDevolucionList();
            List<Devolucion> devolucionListNew = transaccion.getDevolucionList();
            List<String> illegalOrphanMessages = null;
            for (Devolucion devolucionListOldDevolucion : devolucionListOld) {
                if (!devolucionListNew.contains(devolucionListOldDevolucion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Devolucion " + devolucionListOldDevolucion + " since its idTransaccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getId());
                transaccion.setIdProducto(idProductoNew);
            }
            List<Devolucion> attachedDevolucionListNew = new ArrayList<Devolucion>();
            for (Devolucion devolucionListNewDevolucionToAttach : devolucionListNew) {
                devolucionListNewDevolucionToAttach = em.getReference(devolucionListNewDevolucionToAttach.getClass(), devolucionListNewDevolucionToAttach.getId());
                attachedDevolucionListNew.add(devolucionListNewDevolucionToAttach);
            }
            devolucionListNew = attachedDevolucionListNew;
            transaccion.setDevolucionList(devolucionListNew);
            transaccion = em.merge(transaccion);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getTransaccionList().remove(transaccion);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getTransaccionList().add(transaccion);
                idProductoNew = em.merge(idProductoNew);
            }
            for (Devolucion devolucionListNewDevolucion : devolucionListNew) {
                if (!devolucionListOld.contains(devolucionListNewDevolucion)) {
                    Transaccion oldIdTransaccionOfDevolucionListNewDevolucion = devolucionListNewDevolucion.getIdTransaccion();
                    devolucionListNewDevolucion.setIdTransaccion(transaccion);
                    devolucionListNewDevolucion = em.merge(devolucionListNewDevolucion);
                    if (oldIdTransaccionOfDevolucionListNewDevolucion != null && !oldIdTransaccionOfDevolucionListNewDevolucion.equals(transaccion)) {
                        oldIdTransaccionOfDevolucionListNewDevolucion.getDevolucionList().remove(devolucionListNewDevolucion);
                        oldIdTransaccionOfDevolucionListNewDevolucion = em.merge(oldIdTransaccionOfDevolucionListNewDevolucion);
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
            List<Devolucion> devolucionListOrphanCheck = transaccion.getDevolucionList();
            for (Devolucion devolucionListOrphanCheckDevolucion : devolucionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaccion (" + transaccion + ") cannot be destroyed since the Devolucion " + devolucionListOrphanCheckDevolucion + " in its devolucionList field has a non-nullable idTransaccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Producto idProducto = transaccion.getIdProducto();
            if (idProducto != null) {
                idProducto.getTransaccionList().remove(transaccion);
                idProducto = em.merge(idProducto);
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

    public Transaccion findTransaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaccion.class, id);
        } finally {
            em.close();
        }
    }

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
    
}
