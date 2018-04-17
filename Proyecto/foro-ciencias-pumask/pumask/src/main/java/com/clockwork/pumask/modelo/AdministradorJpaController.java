/**
 * Paquete que representa el modelo, en el patron de diseño
 * Vista-Controldor.
 * El modelo provee una representacion del Diseño de las Entidades
 * que se decidieron en el diseño.
 */
package com.clockwork.pumask.modelo;

import com.clockwork.pumask.modelo.exceptions.IllegalOrphanException;
import com.clockwork.pumask.modelo.exceptions.NonexistentEntityException;
import com.clockwork.pumask.modelo.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Clase generada con Ingenier&iacute;a inversa por Netbeans.
 * utiliaza la tecnologia de java pages api.
 * @author dima
 */
public class AdministradorJpaController implements Serializable {

    /**
     * Utiliza la clase EntityManagerFactory generada en EntityProvider
     * la cual en base a la informacion del archivo Percistance (archivo
     * auxiliar de Netbeans), administra la base de datos.
     * @param emf objeto provisto en EntityProvider.
     */
    public AdministradorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    /**
     * Regresa el objeto que ayuda a la administracion de la base de datos.
     * @return El objeto que ayuda a la administracion de la base de datos.
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * 
     * @param administrador
     * @throws IllegalOrphanException
     * @throws PreexistingEntityException
     * @throws Exception 
     */
    public void create(Administrador administrador) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Usuario usuarioOrphanCheck = administrador.getUsuario();
        if (usuarioOrphanCheck != null) {
            Administrador oldAdministradorOfUsuario = usuarioOrphanCheck.getAdministrador();
            if (oldAdministradorOfUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuarioOrphanCheck + " already has an item of type Administrador whose usuario column cannot be null. Please make another selection for the usuario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = administrador.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getCorreo());
                administrador.setUsuario(usuario);
            }
            em.persist(administrador);
            if (usuario != null) {
                usuario.setAdministrador(administrador);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdministrador(administrador.getCorreoAdmin()) != null) {
                throw new PreexistingEntityException("Administrador " + administrador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /**
     * Utiliza la tecnologia de Java Pages API.
     * Administra y valida la informacion de la base de datos.
     * @param administrador
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws Exception 
     */
    public void edit(Administrador administrador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador persistentAdministrador = em.find(Administrador.class, administrador.getCorreoAdmin());
            Usuario usuarioOld = persistentAdministrador.getUsuario();
            Usuario usuarioNew = administrador.getUsuario();
            List<String> illegalOrphanMessages = null;
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                Administrador oldAdministradorOfUsuario = usuarioNew.getAdministrador();
                if (oldAdministradorOfUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuarioNew + " already has an item of type Administrador whose usuario column cannot be null. Please make another selection for the usuario field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getCorreo());
                administrador.setUsuario(usuarioNew);
            }
            administrador = em.merge(administrador);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.setAdministrador(null);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.setAdministrador(administrador);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = administrador.getCorreoAdmin();
                if (findAdministrador(id) == null) {
                    throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.");
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
     * @param id
     * @throws NonexistentEntityException 
     */
    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador administrador;
            try {
                administrador = em.getReference(Administrador.class, id);
                administrador.getCorreoAdmin();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario = administrador.getUsuario();
            if (usuario != null) {
                usuario.setAdministrador(null);
                usuario = em.merge(usuario);
            }
            em.remove(administrador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /**
     * 
     * @return 
     */
    public List<Administrador> findAdministradorEntities() {
        return findAdministradorEntities(true, -1, -1);
    }

    /**
     * 
     * @param maxResults
     * @param firstResult
     * @return 
     */
    public List<Administrador> findAdministradorEntities(int maxResults, int firstResult) {
        return findAdministradorEntities(false, maxResults, firstResult);
    }

     /**
      * 
      * @param all
      * @param maxResults
      * @param firstResult
      * @return 
      */
    private List<Administrador> findAdministradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administrador.class));
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
     * 
     * @param id
     * @return 
     */
    public Administrador findAdministrador(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administrador.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * 
     * @return 
     */
    public int getAdministradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administrador> rt = cq.from(Administrador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
