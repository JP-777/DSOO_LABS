package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepo {
    private List<Cliente> listaClientes;

    public ClienteRepo() {
        this.listaClientes = new ArrayList<>();
        // Cliente del grupo
        listaClientes.add(new Cliente("C001", "76543210", "Edwar Abril", "Saire Peralta", 
            "Av. Venezuela 456, Arequipa", "989765432", "edwar.saire@gmail.com", "Activo"));
        
        // Figuras tecnológicas famosas
        listaClientes.add(new Cliente("C002", "45678901", "Elon", "Musk", 
            "Tesla Headquarters, Austin TX", "988123456", "elon.musk@tesla.com", "Activo"));
        
        listaClientes.add(new Cliente("C003", "56789012", "Satya", "Nadella", 
            "Microsoft Way, Redmond WA", "977234567", "satya.nadella@microsoft.com", "Activo"));
        
        listaClientes.add(new Cliente("C004", "67890123", "Tim", "Cook", 
            "Apple Park, Cupertino CA", "966345678", "tim.cook@apple.com", "Activo"));
        
        listaClientes.add(new Cliente("C005", "78901234", "Sundar", "Pichai", 
            "Googleplex, Mountain View CA", "955456789", "sundar.pichai@google.com", "Activo"));
        
        System.out.println("✓ Clientes inicializados: " + listaClientes.size() + " registros");
    }

    public void agregarCliente(Cliente c) {
        listaClientes.add(c);
    }

    public void listarClientes() {
        if (listaClientes.isEmpty()) {
            System.out.println("ℹ No hay clientes registrados.");
        } else {
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║                  LISTA DE CLIENTES                     ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            for (Cliente c : listaClientes) {
                System.out.println(c);
                System.out.println("───────────────────────────────────────────────────────");
            }
        }
    }

    public Cliente buscarPorId(String id) {
        for (Cliente c : listaClientes) {
            if (c.getIdCliente().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public void eliminarCliente(String id) {
        Cliente cliente = buscarPorId(id);
        if (cliente != null) {
            listaClientes.remove(cliente);
            System.out.println(" ✓ Cliente eliminado correctamente: " + cliente.getNombre() + " " + cliente.getApellido());
        } else {
            System.out.println(" X No se encontró el cliente con ID: " + id);
        }
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
}