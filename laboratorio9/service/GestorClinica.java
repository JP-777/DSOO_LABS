package DSOO_LABS.laboratorio7.service;

import DSOO_LABS.laboratorio7.model.*;
import DSOO_LABS.laboratorio7.dao.UsuarioDAO;
import DSOO_LABS.laboratorio7.dao.ClienteDAO;
import DSOO_LABS.laboratorio7.dao.EmpleadoDAO;

public class GestorClinica {
    private UsuarioDAO usuarioDAO;
    private ClienteDAO clienteDAO;
    private EmpleadoDAO empleadoDAO;

    public GestorClinica(ClienteDAO clienteDAO, EmpleadoDAO empleadoDAO) {
        this.usuarioDAO = new UsuarioDAO();
        this.clienteDAO = clienteDAO;
        this.empleadoDAO = empleadoDAO;
        
        System.out.println("✅ Sistema de usuarios inicializado con base de datos");
    }

    public Usuario login(String nombreUsuario, String contrasena) {
        System.out.println("🔐 Intentando login con BD para: " + nombreUsuario);
        
        Usuario usuario = usuarioDAO.login(nombreUsuario, contrasena);
        
        if (usuario != null) {
            System.out.println("✅ Login exitoso desde BD: " + usuario.getNombreUsuario());
            usuario.mostrarPermisos();
        } else {
            System.out.println("❌ Credenciales incorrectas o usuario inactivo");
        }
        
        return usuario;
    }

    // Método para agregar usuario (solo admin)
    public boolean agregarUsuario(Usuario usuario, int idPersona) {
        if (usuarioDAO.existeUsuario(usuario.getNombreUsuario())) {
            System.out.println("⚠ Usuario ya existe: " + usuario.getNombreUsuario());
            return false;
        }
        
        boolean resultado = usuarioDAO.registrarUsuario(usuario, idPersona);
        if (resultado) {
            System.out.println("✅ Usuario agregado a BD: " + usuario.getNombreUsuario());
        } else {
            System.out.println("❌ Error al agregar usuario");
        }
        
        return resultado;
    }

    public void mostrarUsuariosPredefinidos() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║         USUARIOS EN BASE DE DATOS                      ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("\n--- CREDENCIALES DE PRUEBA ---");
        System.out.println("Usuario: jordan.paredes   | Contraseña: admin123");
        System.out.println("Usuario: brayan.sanchez   | Contraseña: admin456");
        System.out.println("Usuario: kevin.peralta    | Contraseña: empleado123");
        System.out.println("Usuario: elkin.ramos      | Contraseña: empleado456");
        System.out.println("Usuario: fernando.solsol  | Contraseña: empleado789");
        System.out.println("Usuario: edwar.saire      | Contraseña: cliente123");
        System.out.println("Usuario: elon.musk        | Contraseña: cliente456");
        System.out.println("Usuario: satya.nadella    | Contraseña: cliente789");
        System.out.println("Usuario: tim.cook         | Contraseña: cliente012");
        System.out.println("Usuario: sundar.pichai    | Contraseña: cliente345");
        System.out.println("Usuario: mark.zuckerberg  | Contraseña: [sin usuario aún]");
        System.out.println("\n════════════════════════════════════════════════════════\n");
    }
}