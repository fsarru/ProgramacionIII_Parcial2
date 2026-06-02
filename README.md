# Parcial 2 - Programación III: Backend Java y JPA ☕

## 📋 Descripción del Proyecto

Este proyecto es la resolución del Segundo Parcial de la materia Programación III. Su objetivo principal es extender un proyecto base de una Tienda Virtual (TP Unidad 8) implementando una capa de acceso a datos mediante **Java Persistence API (JPA)** e **Hibernate**, conectándose a una base de datos **H2**.

Para mantener la integridad de la arquitectura original y cumplir con la restricción estricta de no modificar las clases base (entidades, enums y `persistence.xml`), el sistema implementa:

* **Patrón Repositorio (DAO):** Una clase abstracta genérica (`BaseRepository<T>`) que centraliza el manejo del `EntityManager` y las transacciones seguras (bloques `try-catch-finally`).
* **Repositorios Específicos:** Clases dedicadas (`CategoriaRepository` y `ProductoRepository`) que heredan del repositorio base.
* **Consultas JPQL Personalizadas:** Uso de `TypedQuery` y parámetros nombrados para realizar reportes eficientes y seguros cruzando información entre tablas (Relaciones OneToMany).
* **Bajas Lógicas:** Mantenimiento del historial en la base de datos cambiando el estado de los registros (`eliminado = true`) en lugar de usar comandos `DELETE` físicos.
* **Interfaz Interactiva:** Un menú por consola robusto y a prueba de errores de tipeo para gestionar el ABM (Alta, Baja y Modificación) de Categorías y Productos.

---

## 🚀 Instrucciones para ejecutarlo

El proyecto utiliza **Gradle** como herramienta de construcción y automatización, por lo que no es necesario instalar dependencias complejas manualmente.

### Requisitos previos
* Tener instalado **Java JDK** (versión 17 o superior recomendada).
* (Opcional) Un IDE compatible con Java y Gradle como IntelliJ IDEA, Eclipse o VS Code.

## 🔗 Links útiles

### Video explicativo
https://youtu.be/NTpdFLxBgko

### Repositorio
https://github.com/fsarru/ProgramacionIII_Parcial2.git