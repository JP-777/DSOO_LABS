package repo;
import model.Libro;
import model.Usuario;
import java.util.ArrayList;
import java.util.Scanner;

public class UsuarioRepo {
    private ArrayList<Usuario> usuarios;

    public UsuarioRepo() {
        this.usuarios = new ArrayList<>();
    }

    // Método con interacción para registrar usuario
    public void registrarUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== REGISTRAR NUEVO USUARIO ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("ID de usuario: ");
        String id = scanner.nextLine();
        
        Usuario usuario = new Usuario(nombre, id);
        usuarios.add(usuario);
        System.out.println("✓ Usuario registrado exitosamente.");
    }

    // Método interno para buscar usuario (usado por otros métodos)
    public Usuario buscarPorId(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equalsIgnoreCase(id)) {
                return usuario;
            }
        }
        return null;
    }

    // Método con interacción para mostrar todos los usuarios
    public void mostrarTodos() {
        if (usuarios.isEmpty()) {
            System.out.println("\nNo hay usuarios registrados.");
            return;
        }
        
        System.out.println("\n========== USUARIOS REGISTRADOS ==========");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i));
        }
        System.out.println("==========================================\n");
    }

    // Método para tomar un libro prestado (usado por el servicio)
    public boolean tomarPrestado(Usuario usuario, Libro libro) {
        if (libro.estaDisponible() && !usuario.getLibrosPrestados().contains(libro)) {
            libro.setDisponible(false);
            usuario.getLibrosPrestados().add(libro);
            return true;
        }
        return false;
    }

    // Método para devolver un libro (usado por el servicio)
    public boolean devolverLibro(Usuario usuario, Libro libro) {
        if (usuario.getLibrosPrestados().contains(libro)) {
            libro.setDisponible(true);
            usuario.getLibrosPrestados().remove(libro);
            return true;
        }
        return false;
    }
}
