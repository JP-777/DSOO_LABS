package repo;
import model.Paciente;
import java.util.ArrayList;
import java.util.List;

public class PacientesRepo {
    private List<Paciente> listaPacientes;

    public PacientesRepo() {
        this.listaPacientes = new ArrayList<>();
    }

    public boolean agregarPaciente(Paciente p) {
        if (p.getEdad() <= 0) {
            System.out.println(" Error: la edad debe ser mayor que 0.");
            return false;
        }

        if (buscarPorCodigo(p.getCodigo()) != null) {
            System.out.println(" Error: ya existe un paciente con el código " + p.getCodigo());
            return false;
        }

        if (buscarPorDni(p.getDni()) != null) {
            System.out.println(" Error: ya existe un paciente con el documento " + p.getDni());
            return false;
        }

        listaPacientes.add(p);
        System.out.println(" Paciente agregado correctamente.");
        return true;
    }

    public Paciente buscarPorCodigo(int codigo) {
        for (Paciente p : listaPacientes) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

    public Paciente buscarPorDni(int dni) {
        for (Paciente p : listaPacientes) {
            if (p.getDni() == dni) {
                return p;
            }
        }
        return null;
    }

    public void listarPacientes() {
        if (listaPacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            System.out.println("📋 Lista de pacientes:");
            for (Paciente p : listaPacientes) {
                System.out.println(p);
            }
        }
    }
}