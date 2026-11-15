package co.com.itm.registroProductos;

import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegistroProductosApplication {

	  public static void main(String[] args) {
	        ProductoRepository repo = new ProductoRepository();
	        Scanner sc = new Scanner(System.in);
	        int opcion;

	        do {
	            System.out.println("\n=== GESTIÓN DE PRODUCTOS ===");
	            System.out.println("1. Crear producto");
	            System.out.println("2. Listar productos");
	            System.out.println("3. Buscar por ID");
	            System.out.println("4. Actualizar producto");
	            System.out.println("5. Eliminar producto");
	            System.out.println("0. Salir");
	            System.out.print("Opción: ");
	            opcion = Integer.parseInt(sc.nextLine());

	            try {
	                switch (opcion) {
	                    case 1 -> {
	                        System.out.print("Nombre: "); String n = sc.nextLine();
	                        System.out.print("Descripción: "); String d = sc.nextLine();
	                        System.out.print("Precio: "); double p = Double.parseDouble(sc.nextLine());
	                        System.out.print("Cantidad: "); int c = Integer.parseInt(sc.nextLine());
	                        System.out.println("Creado: " + repo.crear(n, d, p, c));
	                    }
	                    case 2 -> {
	                        List<Producto> lista = repo.listar();
	                        if (lista.isEmpty()) System.out.println("No hay productos.");
	                        else lista.forEach(System.out::println);
	                    }
	                    case 3 -> {
	                        System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine());
	                        repo.buscarPorId(id).ifPresentOrElse(
	                                System.out::println,
	                                () -> System.out.println("Producto no encontrado.")
	                        );
	                    }
	                    case 4 -> {
	                        System.out.print("ID a actualizar: "); int id = Integer.parseInt(sc.nextLine());
	                        System.out.print("Nuevo nombre: "); String n = sc.nextLine();
	                        System.out.print("Nueva descripción: "); String d = sc.nextLine();
	                        System.out.print("Nuevo precio: "); double p = Double.parseDouble(sc.nextLine());
	                        System.out.print("Nueva cantidad: "); int c = Integer.parseInt(sc.nextLine());
	                        System.out.println("Actualizado: " + repo.actualizar(id, n, d, p, c));
	                    }
	                    case 5 -> {
	                        System.out.print("ID a eliminar: "); int id = Integer.parseInt(sc.nextLine());
	                        System.out.println(repo.eliminar(id) ? "Eliminado." : "No existe el producto.");
	                    }
	                    case 0 -> System.out.println("¡Hasta luego!");
	                    default -> System.out.println("Opción inválida.");
	                }
	            } catch (Exception e) {
	                System.out.println("Error: " + e.getMessage());
	            }
	        } while (opcion != 0);

	        sc.close();
	    }

}
