/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clockwork.pumask.modelo;

import com.clockwork.pumask.modelo.exceptions.NonexistentEntityException;
import com.clockwork.pumask.modelo.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author dima
 */
public class RespuestaJpaController implements Serializable {

    public RespuestaJpaController(final EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public final EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public final void create(final Respuesta respuesta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pregunta idPregunta = respuesta.getIdPregunta();
            if (idPregunta != null) {
                idPregunta = em.getReference(idPregunta.getClass(), idPregunta.getIdPregunta());
                respuesta.setIdPregunta(idPregunta);
            }
            Usuario usuarioCorreo = respuesta.getUsuarioCorreo();
            if (usuarioCorreo != null) {
                usuarioCorreo = em.getReference(usuarioCorreo.getClass(), usuarioCorreo.getCorreo());
                respuesta.setUsuarioCorreo(usuarioCorreo);
            }
            em.persist(respuesta);
            if (idPregunta != null) {
                idPregunta.getRespuestaCollection().add(respuesta);
                idPregunta = em.merge(idPregunta);
            }
            if (usuarioCorreo != null) {
                usuarioCorreo.getRespuestaCollection().add(respuesta);
                usuarioCorreo = em.merge(usuarioCorreo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRespuesta(respuesta.getIdRespuesta()) != null) {
                throw new PreexistingEntityException("Respuesta " + respuesta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public final void edit( Respuesta respuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Respuesta persistentRespuesta = em.find(Respuesta.class, respuesta.getIdRespuesta());
            Pregunta idPreguntaOld = persistentRespuesta.getIdPregunta();
            Pregunta idPreguntaNew = respuesta.getIdPregunta();
            Usuario usuarioCorreoOld = persistentRespuesta.getUsuarioCorreo();
            Usuario usuarioCorreoNew = respuesta.getUsuarioCorreo();
            if (idPreguntaNew != null) {
                idPreguntaNew = em.getReference(idPreguntaNew.getClass(), idPreguntaNew.getIdPregunta());
                respuesta.setIdPregunta(idPreguntaNew);
            }
            if (usuarioCorreoNew != null) {
                usuarioCorreoNew = em.getReference(usuarioCorreoNew.getClass(), usuarioCorreoNew.getCorreo());
                respuesta.setUsuarioCorreo(usuarioCorreoNew);
            }
            respuesta = em.merge(respuesta);
            if (idPreguntaOld != null && !idPreguntaOld.equals(idPreguntaNew)) {
                idPreguntaOld.getRespuestaCollection().remove(respuesta);
                idPreguntaOld = em.merge(idPreguntaOld);
            }
            if (idPreguntaNew != null && !idPreguntaNew.equals(idPreguntaOld)) {
                idPreguntaNew.getRespuestaCollection().add(respuesta);
                idPreguntaNew = em.merge(idPreguntaNew);
            }
            if (usuarioCorreoOld != null && !usuarioCorreoOld.equals(usuarioCorreoNew)) {
                usuarioCorreoOld.getRespuestaCollection().remove(respuesta);
                usuarioCorreoOld = em.merge(usuarioCorreoOld);
            }
            if (usuarioCorreoNew != null && !usuarioCorreoNew.equals(usuarioCorreoOld)) {
                usuarioCorreoNew.getRespuestaCollection().add(respuesta);
                usuarioCorreoNew = em.merge(usuarioCorreoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = respuesta.getIdRespuesta();
                if (findRespuesta(id) == null) {
                    throw new NonexistentEntityException("The respuesta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public final void destroy(final Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Respuesta respuesta;
            try {
                respuesta = em.getReference(Respuesta.class, id);
                respuesta.getIdRespuesta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuesta with id " + id + " no longer exists.", enfe);
            }
            Pregunta idPregunta = respuesta.getIdPregunta();
            if (idPregunta != null) {
                idPregunta.getRespuestaCollection().remove(respuesta);
                idPregunta = em.merge(idPregunta);
            }
            Usuario usuarioCorreo = respuesta.getUsuarioCorreo();
            if (usuarioCorreo != null) {
                usuarioCorreo.getRespuestaCollection().remove(respuesta);
                usuarioCorreo = em.merge(usuarioCorreo);
            }
            em.remove(respuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public final List<Respuesta> findRespuestaEntities() {
        return findRespuestaEntities(true, -1, -1);
    }

    public final List<Respuesta> findRespuestaEntities(final int maxResults, final int firstResult) {
        return findRespuestaEntities(false, maxResults, firstResult);
    }

    private List<Respuesta> findRespuestaEntities(final boolean all, final int maxResults, final int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Respuesta.class));
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

    public final Respuesta findRespuesta(final Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Respuesta.class, id);
        } finally {
            em.close();
        }
    }

    public final int getRespuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Respuesta> rt = cq.from(Respuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
