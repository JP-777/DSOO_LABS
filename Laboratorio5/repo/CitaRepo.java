package repo;

import model.Cita;
import model.Doctor;
import model.Paciente;
import java.util.ArrayList;
import java.util.List;

public class CitaRepo {
    private List<Cita> listaCitas;

    public CitaRepo() {
        this.listaCitas = new ArrayList<>();
    }

    public boolean agregarCita(Cita cita) {
        if (buscarPorCodigo(cita.getCodigo()) != null) {
            System.out.println("Error: ya existe una cita con el código " + cita.getCodigo());
            return false;
        }

        // Validar si el doctor ya tiene una cita a esa hora
        for (Cita c : listaCitas) {
            if (c.getDoctor().getCodigo() == cita.getDoctor().getCodigo() &&
                c.getFecha().equals(cita.getFecha()) &&
                c.getHora().equals(cita.getHora())) {
                System.out.println("Error: el doctor ya tiene una cita en esa fecha y hora.");
                return false;
            }
        }

        listaCitas.add(cita);
        System.out.println("Cita registrada correctamente.");
        return true;
    }

    public Cita buscarPorCodigo(int codigo) {
        for (Cita c : listaCitas) {
            if (c.getCodigo() == codigo) {
                return c;
            }
        }
        return null;
    }

    public void listarCitas() {
        if (listaCitas.isEmpty()) {
            System.out.println("No hay citas registradas.");
        } else {
            System.out.println("Lista de citas registradas:");
            for (Cita c : listaCitas) {
                System.out.println(c);
            }
        }
    }

    public boolean eliminarCita(int codigo) {
        Cita c = buscarPorCodigo(codigo);
        if (c != null) {
            listaCitas.remove(c);
            System.out.println("Cita eliminada correctamente.");
            return true;
        }
        System.out.println("No se encontró una cita con el código " + codigo);
        return false;
    }
}