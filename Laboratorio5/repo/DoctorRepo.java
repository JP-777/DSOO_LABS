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
}
