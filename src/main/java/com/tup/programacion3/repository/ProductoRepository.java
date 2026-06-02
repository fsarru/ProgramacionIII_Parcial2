package com.tup.programacion3.repository;

import com.tup.programacion3.entities.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        EntityManager em = getEntityManager();
        try {
            // COMENTARIO OBLIGATORIO HU-09:
            // Como la relación es unidireccional, partimos desde Categoria (c)
            // haciendo un JOIN con su colección de productos (p).
            // Filtramos por el id de la categoría (:categoriaId) y verificamos
            // que el producto no esté dado de baja (eliminado = false).
            String jpql = "SELECT p FROM Categoria c JOIN c.productos p WHERE c.id = :categoriaId AND p.eliminado = false";

            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            query.setParameter("categoriaId", categoriaId);

            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}