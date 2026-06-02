package com.tup.programacion3;

import com.tup.programacion3.entities.Categoria;
import com.tup.programacion3.entities.Producto;
import com.tup.programacion3.repository.CategoriaRepository;
import com.tup.programacion3.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final CategoriaRepository categoriaRepository = new CategoriaRepository();
    private static final ProductoRepository productoRepository = new ProductoRepository();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n========== MENÚ PRINCIPAL - TIENDA VIRTUAL ==========");
            System.out.println("1. Gestionar Categorías (ABM)");
            System.out.println("2. Gestionar Productos (ABM)");
            System.out.println("3. Reporte: Listar productos por categoría (HU-09)");
            System.out.println("0. Salir del Sistema");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1: menuCategorias(); break;
                    case 2: menuProductos(); break;
                    case 3: reporteProductosPorCategoria(); break;
                    case 0: System.out.println("¡Gracias por utilizar el sistema!"); break;
                    default: System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
                opcion = -1;
            }
        } while (opcion != 0);
    }

    // ==========================================
    // SECCIÓN GESTIÓN DE CATEGORÍAS (ABM)
    // ==========================================
    private static void menuCategorias() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Registrar nueva Categoría (Alta)");
            System.out.println("2. Listar Categorías activas");
            System.out.println("3. Modificar una Categoría");
            System.out.println("4. Dar de baja una Categoría (Baja Lógica)");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1: altaCategoria(); break;
                    case 2: listarCategorias(); break;
                    case 3: modificacionCategoria(); break;
                    case 4: bajaCategoria(); break;
                    case 0: break;
                    default: System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número.");
                opcion = -1;
            }
        } while (opcion != 0);
    }

    private static void altaCategoria() {
        System.out.println("\n[Alta de Categoría]");
        System.out.print("Ingrese nombre de la categoría: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese descripción de la categoría: ");
        String descripcion = scanner.nextLine();

        Categoria nueva = new Categoria();
        nueva.setNombre(nombre);
        nueva.setDescripcion(descripcion);

        categoriaRepository.guardar(nueva);
        System.out.println("¡Categoría guardada con éxito!");
    }

    private static void listarCategorias() {
        System.out.println("\n[Listado de Categorías Activas]");
        List<Categoria> activas = categoriaRepository.listarActivos();
        if (activas.isEmpty()) {
            System.out.println("No hay categorías activas registradas.");
        } else {
            for (Categoria cat : activas) {
                System.out.printf("ID: %d | Nombre: %s | Descripción: %s\n", cat.getId(), cat.getNombre(), cat.getDescripcion());
            }
        }
    }

    private static void modificacionCategoria() {
        System.out.println("\n[Modificación de Categoría]");
        System.out.print("Ingrese el ID de la categoría a modificar: ");
        Long id = Long.parseLong(scanner.nextLine());

        Optional<Categoria> opt = categoriaRepository.buscarPorId(id);
        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: La categoría no existe o se encuentra dada de baja.");
            return;
        }

        Categoria cat = opt.get();
        System.out.print("Nuevo nombre (actual: " + cat.getNombre() + "): ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva descripción (actual: " + cat.getDescripcion() + "): ");
        String descripcion = scanner.nextLine();

        if (!nombre.trim().isEmpty()) cat.setNombre(nombre);
        if (!descripcion.trim().isEmpty()) cat.setDescripcion(descripcion);

        categoriaRepository.guardar(cat);
        System.out.println("¡Categoría actualizada con éxito!");
    }

    private static void bajaCategoria() {
        System.out.println("\n[Baja Lógica de Categoría]");
        System.out.print("Ingrese el ID de la categoría a dar de baja: ");
        Long id = Long.parseLong(scanner.nextLine());

        Optional<Categoria> opt = categoriaRepository.buscarPorId(id);
        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: El ID especificado no existe o ya está dado de baja.");
            return;
        }

        String nombreAfectado = opt.get().getNombre();
        categoriaRepository.eliminarLogico(id);
        System.out.println("Confirmación: La categoría '" + nombreAfectado + "' ha sido dada de baja correctamente.");
    }

    // ==========================================
    // SECCIÓN GESTIÓN DE PRODUCTOS (ABM)
    // ==========================================
    private static void menuProductos() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Registrar nuevo Producto (Alta)");
            System.out.println("2. Listar Productos activos");
            System.out.println("3. Modificar un Producto");
            System.out.println("4. Dar de baja un Producto (HU-08 - Baja Lógica)");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1: altaProducto(); break;
                    case 2: listarProductos(); break;
                    case 3: modificacionProducto(); break;
                    case 4: bajaProducto(); break;
                    case 0: break;
                    default: System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número.");
                opcion = -1;
            }
        } while (opcion != 0);
    }

    private static void altaProducto() {
        System.out.println("\n[Alta de Producto]");
        System.out.print("Ingrese nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese precio: ");
        Double precio = Double.parseDouble(scanner.nextLine());
        System.out.print("Ingrese stock disponible: ");
        int stock = Integer.parseInt(scanner.nextLine());

        System.out.println("Asigne una Categoría seleccionando de la siguiente lista:");
        listarCategorias();
        System.out.print("Ingrese el ID de la categoría elegida: ");
        Long catId = Long.parseLong(scanner.nextLine());

        Optional<Categoria> catOpt = categoriaRepository.buscarPorId(catId);
        if (catOpt.isEmpty() || catOpt.get().isEliminado()) {
            System.out.println("Error: La categoría seleccionada no es válida o está dada de baja. Operación cancelada.");
            return;
        }

        Producto nuevo = new Producto();
        nuevo.setNombre(nombre);
        nuevo.setDescripcion(descripcion);
        nuevo.setPrecio(precio);
        nuevo.setStock(stock);

        // SOLUCIÓN AL ERROR:
        // 1. Guardamos PRIMERO el producto solo, para que la BD le asigne un ID y deje de ser un objeto "transitorio".
        nuevo = productoRepository.guardar(nuevo);

        // 2. Una vez guardado, obtenemos la categoría, le inyectamos el producto usando su Set nativo y la actualizamos.
        Categoria cat = catOpt.get();
        if (cat.getProductos() != null) {
            cat.getProductos().add(nuevo);
        }
        categoriaRepository.guardar(cat);

        System.out.println("¡Producto registrado con éxito y asignado a la categoría!");
    }

    private static void listarProductos() {
        System.out.println("\n[Listado de Productos Activos]");
        List<Producto> activos = productoRepository.listarActivos();
        if (activos.isEmpty()) {
            System.out.println("No hay productos activos registrados.");
        } else {
            // CORRECCIÓN: Como Producto no tiene getCategoria(), imprimimos solo los datos propios del producto
            for (Producto prod : activos) {
                System.out.printf("ID: %d | Nombre: %s | Precio: $%.2f | Stock: %d\n",
                        prod.getId(), prod.getNombre(), prod.getPrecio(), prod.getStock());
            }
        }
    }

    private static void modificacionProducto() {
        System.out.println("\n[Modificación de Producto]");
        System.out.print("Ingrese el ID del producto a modificar: ");
        Long id = Long.parseLong(scanner.nextLine());

        Optional<Producto> opt = productoRepository.buscarPorId(id);
        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: El producto no existe o está dado de baja.");
            return;
        }

        Producto prod = opt.get();
        System.out.print("Nuevo nombre (actual: " + prod.getNombre() + "): ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevo precio (actual: " + prod.getPrecio() + "): ");
        String precioStr = scanner.nextLine();
        System.out.print("Nuevo stock (actual: " + prod.getStock() + "): ");
        String stockStr = scanner.nextLine();

        if (!nombre.trim().isEmpty()) prod.setNombre(nombre);
        if (!precioStr.trim().isEmpty()) prod.setPrecio(Double.parseDouble(precioStr));
        if (!stockStr.trim().isEmpty()) prod.setStock(Integer.parseInt(stockStr));

        productoRepository.guardar(prod);
        System.out.println("¡Producto actualizado con éxito!");
    }

    private static void bajaProducto() {
        System.out.println("\n[HU-08: Baja Lógica de Producto]");
        System.out.print("Ingrese el ID del producto a dar de baja: ");
        Long id = Long.parseLong(scanner.nextLine());

        Optional<Producto> opt = productoRepository.buscarPorId(id);
        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: El ID no existe o ya está dado de baja.");
            return;
        }

        Producto productoAfectado = opt.get();
        String nombreProducto = productoAfectado.getNombre();
        productoRepository.eliminarLogico(id);
        System.out.println("Confirmación: El producto '" + nombreProducto + "' ha sido dado de baja correctamente.");
    }

    // ==========================================
    // CUMPLIMIENTO CRITERIOS DE ACEPTACIÓN HU-09
    // ==========================================
    private static void reporteProductosPorCategoria() {
        System.out.println("\n[HU-09: Listar productos de una categoría]");

        System.out.println("Categorías Disponibles:");
        List<Categoria> categoriasActivas = categoriaRepository.listarActivos();
        if (categoriasActivas.isEmpty()) {
            System.out.println("No hay categorías activas disponibles para realizar la consulta.");
            return;
        }

        for (Categoria cat : categoriasActivas) {
            System.out.printf(" [%d] %s\n", cat.getId(), cat.getNombre());
        }

        System.out.print("Seleccione el ID de la categoría a consultar: ");
        Long categoriaId = Long.parseLong(scanner.nextLine());

        List<Producto> productosFiltrados = productoRepository.buscarPorCategoria(categoriaId);

        if (productosFiltrados.isEmpty()) {
            System.out.println("La categoría seleccionada no tiene productos activos vinculados.");
        } else {
            System.out.println("\nProductos encontrados para la categoría elegida:");
            System.out.println("------------------------------------------------------------");
            for (Producto p : productosFiltrados) {
                System.out.printf("ID: %-4d | Nombre: %-20s | Precio: $%-8.2f | Stock: %-4d\n",
                        p.getId(), p.getNombre(), p.getPrecio(), p.getStock());
            }
            System.out.println("------------------------------------------------------------");
        }
    }
}