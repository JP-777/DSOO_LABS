package DSOO_LABS.laboratorio7.repo;

import DSOO_LABS.laboratorio7.model.Cliente;
import DSOO_LABS.laboratorio7.model.Cuenta;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepo {
    private List<Cliente> listaClientes;

    public ClienteRepo() {
        this.listaClientes = new ArrayList<>();

        listaClientes.add(new Cliente("C001", "12345678", "Ana", "López", "Av. Grau 123", "987654321", "ana@mail.com", "Activo"));
        listaClientes.add(new Cliente("C002", "87654321", "Luis", "Pérez", "Calle Lima 456", "999888777", "luis@mail.com", "Activo"));
    }

    public void agregarCliente(Cliente c) {
        listaClientes.add(c);
    }

    public Cliente buscarPorId(String idCliente) {
        for (Cliente c : listaClientes) {
            if (c.getIdCliente().equalsIgnoreCase(idCliente)) return c;
        }
        return null;
    }

    public void listarClientes() {
        System.out.println("=== LISTA DE CLIENTES ===");
        for (Cliente c : listaClientes) {
            c.mostrarDatos();
            System.out.println("----------------------------");
        }
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
}
