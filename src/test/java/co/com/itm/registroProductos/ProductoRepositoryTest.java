package co.com.itm.registroProductos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductoRepositoryTest {

    private ProductoRepository repo;

    @BeforeEach
    void setUp() {
        // Eliminar el archivo JSON antes de cada prueba para que empiecen limpias
        File archivo = new File("productos.json");
        if (archivo.exists()) {
            archivo.delete();
        }
        repo = new ProductoRepository();
    }
    
    

    // ---------- CREATE ----------
    @Test
    void crearProductoExitoso() {
        Producto p = repo.crear("Laptop", "Portátil 14\"", 2500000, 10);
        assertEquals(1, p.getId());
        assertEquals("Laptop", p.getNombre());
        assertEquals(1, repo.total());
    }

    @Test
    void crearProductoConPrecioNegativoFalla() {
        assertThrows(IllegalArgumentException.class,
                () -> repo.crear("Mouse", "Inalámbrico", -100, 5));
    }

    // ---------- READ ----------
    @Test
    void buscarProductoExistente() {
        repo.crear("Teclado", "Mecánico", 150000, 3);
        Optional<Producto> encontrado = repo.buscarPorId(1);
        assertTrue(encontrado.isPresent());
        assertEquals("Teclado", encontrado.get().getNombre());
    }

    @Test
    void buscarProductoInexistenteRetornaVacio() {
        assertTrue(repo.buscarPorId(99).isEmpty());
    }

    // ---------- UPDATE ----------
    @Test
    void actualizarProductoExitoso() {
        repo.crear("Monitor", "24 pulgadas", 800000, 5);
        Producto actualizado = repo.actualizar(1, "Monitor 4K", "27 pulgadas", 1200000, 8);
        assertEquals("Monitor 4K", actualizado.getNombre());
        assertEquals(1200000, actualizado.getPrecio());
    }

    @Test
    void actualizarProductoInexistenteFalla() {
        assertThrows(NoSuchElementException.class,
                () -> repo.actualizar(42, "X", "Y", 10, 1));
    }

    // ---------- DELETE ----------
    @Test
    void eliminarProductoExistente() {
        repo.crear("Audífonos", "Bluetooth", 200000, 2);
        assertTrue(repo.eliminar(1));
        assertEquals(0, repo.total());
    }

    @Test
    void eliminarProductoInexistenteRetornaFalse() {
        assertFalse(repo.eliminar(99));
    }
}
