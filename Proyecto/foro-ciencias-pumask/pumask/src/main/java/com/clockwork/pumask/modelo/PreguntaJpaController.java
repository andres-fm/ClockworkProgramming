 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import java.util.Collection;
import java.util.List;
import java.util.Hashtable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author dima
 */
public class PreguntaJpaController implements Serializable {

    public PreguntaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pregunta pregunta) throws PreexistingEntityException, Exception {
        if (pregunta.getRespuestaCollection() == null) {
            pregunta.setRespuestaCollection(new ArrayList<Respuesta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario correoUsuario = pregunta.getCorreoUsuario();
            if (correoUsuario != null) {
                correoUsuario = em.getReference(correoUsuario.getClass(), correoUsuario.getCorreo());
                pregunta.setCorreoUsuario(correoUsuario);
            }
            Collection<Respuesta> attachedRespuestaCollection = new ArrayList<Respuesta>();
            for (Respuesta respuestaCollectionRespuestaToAttach : pregunta.getRespuestaCollection()) {
                respuestaCollectionRespuestaToAttach = em.getReference(respuestaCollectionRespuestaToAttach.getClass(), respuestaCollectionRespuestaToAttach.getIdRespuesta());
                attachedRespuestaCollection.add(respuestaCollectionRespuestaToAttach);
            }
            pregunta.setRespuestaCollection(attachedRespuestaCollection);
            em.persist(pregunta);
            if (correoUsuario != null) {
                correoUsuario.getPreguntaCollection().add(pregunta);
                correoUsuario = em.merge(correoUsuario);
            }
            for (Respuesta respuestaCollectionRespuesta : pregunta.getRespuestaCollection()) {
                Pregunta oldIdPreguntaOfRespuestaCollectionRespuesta = respuestaCollectionRespuesta.getIdPregunta();
                respuestaCollectionRespuesta.setIdPregunta(pregunta);
                respuestaCollectionRespuesta = em.merge(respuestaCollectionRespuesta);
                if (oldIdPreguntaOfRespuestaCollectionRespuesta != null) {
                    oldIdPreguntaOfRespuestaCollectionRespuesta.getRespuestaCollection().remove(respuestaCollectionRespuesta);
                    oldIdPreguntaOfRespuestaCollectionRespuesta = em.merge(oldIdPreguntaOfRespuestaCollectionRespuesta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPregunta(pregunta.getIdPregunta()) != null) {
                throw new PreexistingEntityException("Pregunta " + pregunta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pregunta pregunta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pregunta persistentPregunta = em.find(Pregunta.class, pregunta.getIdPregunta());
            Usuario correoUsuarioOld = persistentPregunta.getCorreoUsuario();
            Usuario correoUsuarioNew = pregunta.getCorreoUsuario();
            Collection<Respuesta> respuestaCollectionOld = persistentPregunta.getRespuestaCollection();
            Collection<Respuesta> respuestaCollectionNew = pregunta.getRespuestaCollection();
            List<String> illegalOrphanMessages = null;
            for (Respuesta respuestaCollectionOldRespuesta : respuestaCollectionOld) {
                if (!respuestaCollectionNew.contains(respuestaCollectionOldRespuesta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Respuesta " + respuestaCollectionOldRespuesta + " since its idPregunta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (correoUsuarioNew != null) {
                correoUsuarioNew = em.getReference(correoUsuarioNew.getClass(), correoUsuarioNew.getCorreo());
                pregunta.setCorreoUsuario(correoUsuarioNew);
            }
            Collection<Respuesta> attachedRespuestaCollectionNew = new ArrayList<Respuesta>();
            for (Respuesta respuestaCollectionNewRespuestaToAttach : respuestaCollectionNew) {
                respuestaCollectionNewRespuestaToAttach = em.getReference(respuestaCollectionNewRespuestaToAttach.getClass(), respuestaCollectionNewRespuestaToAttach.getIdRespuesta());
                attachedRespuestaCollectionNew.add(respuestaCollectionNewRespuestaToAttach);
            }
            respuestaCollectionNew = attachedRespuestaCollectionNew;
            pregunta.setRespuestaCollection(respuestaCollectionNew);
            pregunta = em.merge(pregunta);
            if (correoUsuarioOld != null && !correoUsuarioOld.equals(correoUsuarioNew)) {
                correoUsuarioOld.getPreguntaCollection().remove(pregunta);
                correoUsuarioOld = em.merge(correoUsuarioOld);
            }
            if (correoUsuarioNew != null && !correoUsuarioNew.equals(correoUsuarioOld)) {
                correoUsuarioNew.getPreguntaCollection().add(pregunta);
                correoUsuarioNew = em.merge(correoUsuarioNew);
            }
            for (Respuesta respuestaCollectionNewRespuesta : respuestaCollectionNew) {
                if (!respuestaCollectionOld.contains(respuestaCollectionNewRespuesta)) {
                    Pregunta oldIdPreguntaOfRespuestaCollectionNewRespuesta = respuestaCollectionNewRespuesta.getIdPregunta();
                    respuestaCollectionNewRespuesta.setIdPregunta(pregunta);
                    respuestaCollectionNewRespuesta = em.merge(respuestaCollectionNewRespuesta);
                    if (oldIdPreguntaOfRespuestaCollectionNewRespuesta != null && !oldIdPreguntaOfRespuestaCollectionNewRespuesta.equals(pregunta)) {
                        oldIdPreguntaOfRespuestaCollectionNewRespuesta.getRespuestaCollection().remove(respuestaCollectionNewRespuesta);
                        oldIdPreguntaOfRespuestaCollectionNewRespuesta = em.merge(oldIdPreguntaOfRespuestaCollectionNewRespuesta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pregunta.getIdPregunta();
                if (findPregunta(id) == null) {
                    throw new NonexistentEntityException("The pregunta with id " + id + " no longer exists.");
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
            Pregunta pregunta;
            try {
                pregunta = em.getReference(Pregunta.class, id);
                pregunta.getIdPregunta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pregunta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Respuesta> respuestaCollectionOrphanCheck = pregunta.getRespuestaCollection();
            for (Respuesta respuestaCollectionOrphanCheckRespuesta : respuestaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pregunta (" + pregunta + ") cannot be destroyed since the Respuesta " + respuestaCollectionOrphanCheckRespuesta + " in its respuestaCollection field has a non-nullable idPregunta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario correoUsuario = pregunta.getCorreoUsuario();
            if (correoUsuario != null) {
                correoUsuario.getPreguntaCollection().remove(pregunta);
                correoUsuario = em.merge(correoUsuario);
            }
            em.remove(pregunta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pregunta> findPreguntaEntities() {
        return findPreguntaEntities(true, -1, -1);
    }

    public List<Pregunta> findPreguntaEntities(int maxResults, int firstResult) {
        return findPreguntaEntities(false, maxResults, firstResult);
    }

    private List<Pregunta> findPreguntaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pregunta.class));
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

    public Pregunta findPregunta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pregunta.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreguntaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pregunta> rt = cq.from(Pregunta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Pregunta> obtenPreguntasCarrera(String carrera) {
    	EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Pregunta.findByCarrera")
                .setParameter("carrera", "actuaria");
        return (List<Pregunta>) q.getResultList();
    }

    public List<Pregunta> obtenPreguntasCC(String carrera, String categoria) {
    	EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("encontrarPorCategoriaCarrera")
                .setParameter(1, categoria)
                .setParameter(2, carrera);
        return (List<Pregunta>) q.getResultList();
    }

    public List<Pregunta> obtenPreguntasUsuario(String correo) {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Pregunta.findByUser")
            .setParameter("correo", correo);
        return (List<Pregunta>)q.getResultList();
    }

    public List<Pregunta> obtenPreguntasPorPalabrasClave(String query) {
        EntityManager em = getEntityManager();
        String[] keyWords = query.split("\\s+");
        Hashtable<Pregunta, Pregunta> results = new Hashtable();
        for(String keyWord : keyWords) {
            Query q = em.createQuery("SELECT p FROM Pregunta p WHERE p.contenido LIKE :keyword")
                    .setParameter("keyword", keyWord);
            List<Pregunta> tempResults = q.getResultList();
            for(Pregunta p : tempResults) {
                results.put(p, p);
            }
        }
        ArrayList<Pregunta> listaFinal = new ArrayList();
        for(Pregunta p : results.values()) {
            listaFinal.add(p);
        }
        return listaFinal;
    }
    
    
}
