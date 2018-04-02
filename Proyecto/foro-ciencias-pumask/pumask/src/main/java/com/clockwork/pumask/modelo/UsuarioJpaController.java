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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author dima
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getRespuestaCollection() == null) {
            usuario.setRespuestaCollection(new ArrayList<Respuesta>());
        }
        if (usuario.getPreguntaCollection() == null) {
            usuario.setPreguntaCollection(new ArrayList<Pregunta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador administrador = usuario.getAdministrador();
            if (administrador != null) {
                administrador = em.getReference(administrador.getClass(), administrador.getCorreoAdmin());
                usuario.setAdministrador(administrador);
            }
            Collection<Respuesta> attachedRespuestaCollection = new ArrayList<Respuesta>();
            for (Respuesta respuestaCollectionRespuestaToAttach : usuario.getRespuestaCollection()) {
                respuestaCollectionRespuestaToAttach = em.getReference(respuestaCollectionRespuestaToAttach.getClass(), respuestaCollectionRespuestaToAttach.getIdRespuesta());
                attachedRespuestaCollection.add(respuestaCollectionRespuestaToAttach);
            }
            usuario.setRespuestaCollection(attachedRespuestaCollection);
            Collection<Pregunta> attachedPreguntaCollection = new ArrayList<Pregunta>();
            for (Pregunta preguntaCollectionPreguntaToAttach : usuario.getPreguntaCollection()) {
                preguntaCollectionPreguntaToAttach = em.getReference(preguntaCollectionPreguntaToAttach.getClass(), preguntaCollectionPreguntaToAttach.getIdPregunta());
                attachedPreguntaCollection.add(preguntaCollectionPreguntaToAttach);
            }
            usuario.setPreguntaCollection(attachedPreguntaCollection);
            em.persist(usuario);
            if (administrador != null) {
                Usuario oldUsuarioOfAdministrador = administrador.getUsuario();
                if (oldUsuarioOfAdministrador != null) {
                    oldUsuarioOfAdministrador.setAdministrador(null);
                    oldUsuarioOfAdministrador = em.merge(oldUsuarioOfAdministrador);
                }
                administrador.setUsuario(usuario);
                administrador = em.merge(administrador);
            }
            for (Respuesta respuestaCollectionRespuesta : usuario.getRespuestaCollection()) {
                Usuario oldUsuarioCorreoOfRespuestaCollectionRespuesta = respuestaCollectionRespuesta.getUsuarioCorreo();
                respuestaCollectionRespuesta.setUsuarioCorreo(usuario);
                respuestaCollectionRespuesta = em.merge(respuestaCollectionRespuesta);
                if (oldUsuarioCorreoOfRespuestaCollectionRespuesta != null) {
                    oldUsuarioCorreoOfRespuestaCollectionRespuesta.getRespuestaCollection().remove(respuestaCollectionRespuesta);
                    oldUsuarioCorreoOfRespuestaCollectionRespuesta = em.merge(oldUsuarioCorreoOfRespuestaCollectionRespuesta);
                }
            }
            for (Pregunta preguntaCollectionPregunta : usuario.getPreguntaCollection()) {
                Usuario oldCorreoUsuarioOfPreguntaCollectionPregunta = preguntaCollectionPregunta.getCorreoUsuario();
                preguntaCollectionPregunta.setCorreoUsuario(usuario);
                preguntaCollectionPregunta = em.merge(preguntaCollectionPregunta);
                if (oldCorreoUsuarioOfPreguntaCollectionPregunta != null) {
                    oldCorreoUsuarioOfPreguntaCollectionPregunta.getPreguntaCollection().remove(preguntaCollectionPregunta);
                    oldCorreoUsuarioOfPreguntaCollectionPregunta = em.merge(oldCorreoUsuarioOfPreguntaCollectionPregunta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getCorreo()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getCorreo());
            Administrador administradorOld = persistentUsuario.getAdministrador();
            Administrador administradorNew = usuario.getAdministrador();
            Collection<Respuesta> respuestaCollectionOld = persistentUsuario.getRespuestaCollection();
            Collection<Respuesta> respuestaCollectionNew = usuario.getRespuestaCollection();
            Collection<Pregunta> preguntaCollectionOld = persistentUsuario.getPreguntaCollection();
            Collection<Pregunta> preguntaCollectionNew = usuario.getPreguntaCollection();
            List<String> illegalOrphanMessages = null;
            if (administradorOld != null && !administradorOld.equals(administradorNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Administrador " + administradorOld + " since its usuario field is not nullable.");
            }
            for (Respuesta respuestaCollectionOldRespuesta : respuestaCollectionOld) {
                if (!respuestaCollectionNew.contains(respuestaCollectionOldRespuesta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Respuesta " + respuestaCollectionOldRespuesta + " since its usuarioCorreo field is not nullable.");
                }
            }
            for (Pregunta preguntaCollectionOldPregunta : preguntaCollectionOld) {
                if (!preguntaCollectionNew.contains(preguntaCollectionOldPregunta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pregunta " + preguntaCollectionOldPregunta + " since its correoUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (administradorNew != null) {
                administradorNew = em.getReference(administradorNew.getClass(), administradorNew.getCorreoAdmin());
                usuario.setAdministrador(administradorNew);
            }
            Collection<Respuesta> attachedRespuestaCollectionNew = new ArrayList<Respuesta>();
            for (Respuesta respuestaCollectionNewRespuestaToAttach : respuestaCollectionNew) {
                respuestaCollectionNewRespuestaToAttach = em.getReference(respuestaCollectionNewRespuestaToAttach.getClass(), respuestaCollectionNewRespuestaToAttach.getIdRespuesta());
                attachedRespuestaCollectionNew.add(respuestaCollectionNewRespuestaToAttach);
            }
            respuestaCollectionNew = attachedRespuestaCollectionNew;
            usuario.setRespuestaCollection(respuestaCollectionNew);
            Collection<Pregunta> attachedPreguntaCollectionNew = new ArrayList<Pregunta>();
            for (Pregunta preguntaCollectionNewPreguntaToAttach : preguntaCollectionNew) {
                preguntaCollectionNewPreguntaToAttach = em.getReference(preguntaCollectionNewPreguntaToAttach.getClass(), preguntaCollectionNewPreguntaToAttach.getIdPregunta());
                attachedPreguntaCollectionNew.add(preguntaCollectionNewPreguntaToAttach);
            }
            preguntaCollectionNew = attachedPreguntaCollectionNew;
            usuario.setPreguntaCollection(preguntaCollectionNew);
            usuario = em.merge(usuario);
            if (administradorNew != null && !administradorNew.equals(administradorOld)) {
                Usuario oldUsuarioOfAdministrador = administradorNew.getUsuario();
                if (oldUsuarioOfAdministrador != null) {
                    oldUsuarioOfAdministrador.setAdministrador(null);
                    oldUsuarioOfAdministrador = em.merge(oldUsuarioOfAdministrador);
                }
                administradorNew.setUsuario(usuario);
                administradorNew = em.merge(administradorNew);
            }
            for (Respuesta respuestaCollectionNewRespuesta : respuestaCollectionNew) {
                if (!respuestaCollectionOld.contains(respuestaCollectionNewRespuesta)) {
                    Usuario oldUsuarioCorreoOfRespuestaCollectionNewRespuesta = respuestaCollectionNewRespuesta.getUsuarioCorreo();
                    respuestaCollectionNewRespuesta.setUsuarioCorreo(usuario);
                    respuestaCollectionNewRespuesta = em.merge(respuestaCollectionNewRespuesta);
                    if (oldUsuarioCorreoOfRespuestaCollectionNewRespuesta != null && !oldUsuarioCorreoOfRespuestaCollectionNewRespuesta.equals(usuario)) {
                        oldUsuarioCorreoOfRespuestaCollectionNewRespuesta.getRespuestaCollection().remove(respuestaCollectionNewRespuesta);
                        oldUsuarioCorreoOfRespuestaCollectionNewRespuesta = em.merge(oldUsuarioCorreoOfRespuestaCollectionNewRespuesta);
                    }
                }
            }
            for (Pregunta preguntaCollectionNewPregunta : preguntaCollectionNew) {
                if (!preguntaCollectionOld.contains(preguntaCollectionNewPregunta)) {
                    Usuario oldCorreoUsuarioOfPreguntaCollectionNewPregunta = preguntaCollectionNewPregunta.getCorreoUsuario();
                    preguntaCollectionNewPregunta.setCorreoUsuario(usuario);
                    preguntaCollectionNewPregunta = em.merge(preguntaCollectionNewPregunta);
                    if (oldCorreoUsuarioOfPreguntaCollectionNewPregunta != null && !oldCorreoUsuarioOfPreguntaCollectionNewPregunta.equals(usuario)) {
                        oldCorreoUsuarioOfPreguntaCollectionNewPregunta.getPreguntaCollection().remove(preguntaCollectionNewPregunta);
                        oldCorreoUsuarioOfPreguntaCollectionNewPregunta = em.merge(oldCorreoUsuarioOfPreguntaCollectionNewPregunta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getCorreo();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getCorreo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Administrador administradorOrphanCheck = usuario.getAdministrador();
            if (administradorOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Administrador " + administradorOrphanCheck + " in its administrador field has a non-nullable usuario field.");
            }
            Collection<Respuesta> respuestaCollectionOrphanCheck = usuario.getRespuestaCollection();
            for (Respuesta respuestaCollectionOrphanCheckRespuesta : respuestaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Respuesta " + respuestaCollectionOrphanCheckRespuesta + " in its respuestaCollection field has a non-nullable usuarioCorreo field.");
            }
            Collection<Pregunta> preguntaCollectionOrphanCheck = usuario.getPreguntaCollection();
            for (Pregunta preguntaCollectionOrphanCheckPregunta : preguntaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Pregunta " + preguntaCollectionOrphanCheckPregunta + " in its preguntaCollection field has a non-nullable correoUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }


    public boolean puedeAcceder(String correo, String contrasenia) {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("tieneAcceso")
                .setParameter(1, correo)
                .setParameter(2, contrasenia);
        return (Boolean) q.getSingleResult();
    }

    public Usuario findLogin(String correo, String contrasenia) {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("findByCorreoAndPassword")
                .setParameter(1, correo)
                .setParameter(2, contrasenia);
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return (Usuario) q.getSingleResult();
    }
    
}
