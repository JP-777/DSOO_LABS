package DSOO_LABS.laboratorio7.service;

import DSOO_LABS.laboratorio7.model.*;
import DSOO_LABS.laboratorio7.repo.ClienteRepo;
import DSOO_LABS.laboratorio7.repo.EmpleadoRepo;

import java.util.ArrayList;
import java.util.List;

public class GestorClinica {
    private List<Usuario> listaUsuarios;
    private ClienteRepo clienteRepo;
    private EmpleadoRepo empleadoRepo;

    public GestorClinica(ClienteRepo clienteRepo, EmpleadoRepo empleadoRepo) {
        this.listaUsuarios = new ArrayList<>();
        this.clienteRepo = clienteRepo;
        this.empleadoRepo = empleadoRepo;
        cargarUsuariosPredefinidos();
    }

    private void cargarUsuariosPredefinidos() {
        // Administradores - Jordan y Brayan
        Empleado jordan = empleadoRepo.buscarPorId("E001");
        Empleado brayan = empleadoRepo.buscarPorId("E005");
        
        if (jordan != null) {
            listaUsuarios.add(new UsuarioAdministrador("jordan.paredes", "admin123", "Administración General"));
        }
        if (brayan != null) {
            listaUsuarios.add(new UsuarioAdministrador("brayan.sanchez", "admin456", "Sistemas y Tecnología"));
        }

        // Empleados - Kevin, Elkin, Fernando
        Empleado kevin = empleadoRepo.buscarPorId("E002");
        Empleado elkin = empleadoRepo.buscarPorId("E003");
        Empleado fernando = empleadoRepo.buscarPorId("E004");
        
        if (kevin != null) {
            listaUsuarios.add(new UsuarioEmpleado("kevin.peralta", "empleado123", kevin));
        }
        if (elkin != null) {
            listaUsuarios.add(new UsuarioEmpleado("elkin.ramos", "empleado456", elkin));
        }
        if (fernando != null) {
            listaUsuarios.add(new UsuarioEmpleado("fernando.solsol", "empleado789", fernando));
        }

        // Clientes - Edwar y figuras tecnológicas
        Cliente edwar = clienteRepo.buscarPorId("C001");
        Cliente elon = clienteRepo.buscarPorId("C002");
        Cliente satya = clienteRepo.buscarPorId("C003");
        Cliente tim = clienteRepo.buscarPorId("C004");
        Cliente sundar = clienteRepo.buscarPorId("C005");
        
        if (edwar != null) {
            listaUsuarios.add(new UsuarioCliente("edwar.saire", "cliente123", edwar));
        }
        if (elon != null) {
            listaUsuarios.add(new UsuarioCliente("elon.musk", "cliente456", elon));
        }
        if (satya != null) {
            listaUsuarios.add(new UsuarioCliente("satya.nadella", "cliente789", satya));
        }
        if (tim != null) {
            listaUsuarios.add(new UsuarioCliente("tim.cook", "cliente012", tim));
        }
        if (sundar != null) {
            listaUsuarios.add(new UsuarioCliente("sundar.pichai", "cliente345", sundar));
        }
        
        System.out.println("✓ Sistema de usuarios inicializado correctamente.");
    }

    public Usuario login(String nombreUsuario, String contrasena) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.validarCredenciales(nombreUsuario, contrasena)) {
                return usuario;
            }
        }
        return null;
    }

    public void agregarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
        System.out.println("Usuario agregado: " + usuario.getNombreUsuario());
    }

    public void listarUsuarios() {
        System.out.println("\n=== LISTA DE USUARIOS DEL SISTEMA ===");
        for (Usuario u : listaUsuarios) {
            System.out.println(u);
        }
        System.out.println("=====================================\n");
    }

    public Usuario buscarUsuarioPorNombre(String nombreUsuario) {
        for (Usuario u : listaUsuarios) {
            if (u.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return u;
            }
        }
        return null;
    }

    public void mostrarUsuariosPredefinidos() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║         USUARIOS PREDEFINIDOS DEL SISTEMA              ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("\n--- ADMINISTRADORES ---");
        System.out.println("Usuario: jordan.paredes   | Contraseña: admin123");
        System.out.println("Usuario: brayan.sanchez   | Contraseña: admin456");
        System.out.println("\n--- EMPLEADOS ---");
        System.out.println("Usuario: kevin.peralta    | Contraseña: empleado123");
        System.out.println("Usuario: elkin.ramos      | Contraseña: empleado456");
        System.out.println("Usuario: fernando.solsol  | Contraseña: empleado789");
        System.out.println("\n--- CLIENTES ---");
        System.out.println("Usuario: edwar.saire      | Contraseña: cliente123");
        System.out.println("Usuario: elon.musk        | Contraseña: cliente456");
        System.out.println("Usuario: satya.nadella    | Contraseña: cliente789");
        System.out.println("Usuario: tim.cook         | Contraseña: cliente012");
        System.out.println("Usuario: sundar.pichai    | Contraseña: cliente345");
        System.out.println("\n════════════════════════════════════════════════════════\n");
    }
}