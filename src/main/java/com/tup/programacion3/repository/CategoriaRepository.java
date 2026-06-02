package com.tup.programacion3.repository;

import com.tup.programacion3.entities.Categoria;
import com.tup.programacion3.entities.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Optional;

public class CategoriaRepository extends BaseRepository<Categoria> {

    public CategoriaRepository() {
        super(Categoria.class);
    }


    public boolean agregarProductoACategoria(Long categoriaId, Producto nuevoProducto) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // 1. Buscamos la categoría en la base de datos
            Categoria categoria = em.find(Categoria.class, categoriaId);

            if (categoria != null && !categoria.isEliminado()) {
                // 2. Persistimos el producto manualmente primero para que deje de ser "Transient"
                em.persist(nuevoProducto);

                // 3. Lo agregamos a la colección de la categoría
                if (categoria.getProductos() != null) {
                    categoria.getProductos().add(nuevoProducto);
                }

                // 4. Actualizamos la categoría
                em.merge(categoria);
                tx.commit();
                return true;
            }
            return false;
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
}