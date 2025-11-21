package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Cliente;

import java.util.ArrayList;
import java.util.List;


public class ClienteRepo {
    private List<Cliente> listaClientes;

    public ClienteRepo() {
        this.listaClientes = new ArrayList<>();
        listaClientes.add(new Cliente("C001", "74859632", "Ana", "López", "Av. Grau 123", "987654321", "ana@mail.com", "Activo"));
        listaClientes.add(new Cliente("C002", "73524168", "Luis", "Pérez", "Calle Lima 456", "912345678", "luis@mail.com", "Activo"));
    }

    public void agregarCliente(Cliente c) {
        listaClientes.add(c);
    }

    public void listarClientes() {
        if (listaClientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("Lista de clientes:");
            for (Cliente c : listaClientes) {
                System.out.println(c);
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
            System.out.println("Cliente eliminado correctamente.");
        } else {
            System.out.println("No se encontró el cliente con ID: " + id);
        }
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
}

