package repo;
import java.util.ArrayList;
import model.Doctor;
public class DoctorRepo {
    private ArrayList<Doctor> listaDoctores;
    public DoctorRepo() {
        this.listaDoctores = new ArrayList<>();
    }
    public boolean agregarDoctor(Doctor doctor) {
        if (buscarPorCodigo(doctor.getCodigo()) != null) {
            System.out.println("Error: ya existe un doctor con el código " + doctor.getCodigo());
            return false;
        }
        listaDoctores.add(doctor);
        System.out.println("Doctor agregado correctamente.");
        return true;
    }
    public Doctor buscarPorCodigo(int codigo) {
        for (Doctor doc : listaDoctores) {
            if (doc.getCodigo() == codigo) {
                return doc;
            }
        }
        return null;
    }
    public void mostrarDoctorPorCodigo(int codigo) {
        Doctor doc = buscarPorCodigo(codigo);
        if (doc != null) {
            System.out.println("Doctor encontrado: " + doc.getNombre() +
                ", Especialidad: " + doc.getEspecialidad() +
                ", Horario: " + doc.getHorarioAtencion());
        } else {
            System.out.println("No se encontró un doctor con el código " + codigo);
        }
    }
}

