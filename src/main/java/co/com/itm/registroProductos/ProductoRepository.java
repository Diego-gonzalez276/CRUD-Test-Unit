package co.com.itm.registroProductos;


import org.springframework.stereotype.Repository;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

@Repository
public class ProductoRepository {
    private final Map<Integer, Producto> productos = new HashMap<>();
    private int nextId = 1;
    private final String ARCHIVO_JSON = "productos.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProductoRepository() {
        cargarDesdeArchivo();
    }

    // CREATE
    public Producto crear(String nombre, String descripcion, double precio, int cantidad) {
        Producto p = new Producto(nextId++, nombre, descripcion, precio, cantidad);
        productos.put(p.getId(), p);
        guardarEnArchivo();
        return p;
    }

    // READ (todos)
    public List<Producto> listar() {
        return new ArrayList<>(productos.values());
    }

    // READ (por id)
    public Optional<Producto> buscarPorId(int id) {
        return Optional.ofNullable(productos.get(id));
    }

    // UPDATE
    public Producto actualizar(int id, String nombre, String descripcion, double precio, int cantidad) {
        Producto p = productos.get(id);
        if (p == null) {
            throw new NoSuchElementException("Producto con id " + id + " no existe");
        }
        p.setNombre(nombre);
        p.setDescripcion(descripcion);
        p.setPrecio(precio);
        p.setCantidad(cantidad);
        guardarEnArchivo();
        return p;
    }

    // DELETE
    public boolean eliminar(int id) {
        boolean eliminado = productos.remove(id) != null;
        if (eliminado) {
            guardarEnArchivo();
        }
        return eliminado;
    }

    public int total() { return productos.size(); }

    // --- MÉTODOS PRIVADOS PARA JSON ---

    private void guardarEnArchivo() {
        objectMapper.writerWithDefaultPrettyPrinter()
		        .writeValue(new File(ARCHIVO_JSON), productos.values());
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO_JSON);
        if (archivo.exists()) {
            List<Producto> lista = objectMapper.readValue(archivo, new TypeReference<List<Producto>>() {});
			for (Producto p : lista) {
			    productos.put(p.getId(), p);
			    // Actualizar el nextId para que no se repitan IDs
			    if (p.getId() >= nextId) {
			        nextId = p.getId() + 1;
			    }
			}
        }
    }
}