package com.tup.programacion3.repository;

import com.tup.programacion3.entities.Base;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T extends Base> {

    private final Class<T> type;
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidad");
    public BaseRepository(Class<T> type) {
        this.type = type;
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // 1. guardar(T entity)
    public T guardar(T entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T mergedEntity = em.merge(entity);
            tx.commit();
            return mergedEntity;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // 2. buscarPorId(Long id)
    public Optional<T> buscarPorId(Long id) {
        EntityManager em = getEntityManager();
        try {
            T entity = em.find(type, id);
            return Optional.ofNullable(entity);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // 3. listarActivos()
    public List<T> listarActivos() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM " + type.getSimpleName() + " e WHERE e.eliminado = false";
            return em.createQuery(jpql, type).getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // 4. eliminarLogico(Long id)
    public boolean eliminarLogico(Long id) {
        Optional<T> optionalEntity = buscarPorId(id);
        if (optionalEntity.isPresent()) {
            T entity = optionalEntity.get();
            EntityManager em = getEntityManager();
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                entity.setEliminado(true);
                em.merge(entity);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                }
                e.printStackTrace();
                return false;
            } finally {
                if (em != null && em.isOpen()) {
                    em.close();
                }
            }
        }
        return false;
    }
}