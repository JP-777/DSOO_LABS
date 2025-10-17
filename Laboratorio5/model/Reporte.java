package model;

import java.util.List;

public class Reporte {
    private List<Cita> listaCitas;

    public Reporte(List<Cita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    // mostrar todas las citas programadas
    public void mostrarTodasLasCitas() {
        System.out.println("\n=== TODAS LAS CITAS PROGRAMADAS ===");
        if (listaCitas == null || listaCitas.isEmpty()) {
            System.out.println("No hay citas registradas.");
        } else {
            for (Cita c : listaCitas) {
                System.out.println(c);
            }
        }
    }

    // listar citas por doctor
    public void listarCitasPorDoctor(int codigoDoctor) {
        System.out.println("\n=== CITAS DEL DOCTOR " + codigoDoctor + " ===");
        boolean encontrado = false;
        for (Cita c : listaCitas) {
            if (c.getDoctor().getCodigo() == codigoDoctor) {
                System.out.println(c);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay citas registradas para este doctor.");
        }
    }

    // listar citas por paciente
    public void listarCitasPorPaciente(int codigoPaciente) {
        System.out.println("\n=== CITAS DEL PACIENTE " + codigoPaciente + " ===");
        boolean encontrado = false;
        for (Cita c : listaCitas) {
            if (c.getPaciente().getCodigo() == codigoPaciente) {
                System.out.println(c);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay citas registradas para este paciente.");
        }
    }

    // mostrar totales de atendidas y canceladas
    public void mostrarTotales() {
        int atendidas = 0;
        int canceladas = 0;
        for (Cita c : listaCitas) {
            if (c.getEstado().equalsIgnoreCase("atendida")) {
                atendidas++;
            } else if (c.getEstado().equalsIgnoreCase("cancelada")) {
                canceladas++;
            }
        }
        System.out.println("\n=== ESTADÍSTICAS DE CITAS ===");
        System.out.println("Citas atendidas: " + atendidas);
        System.out.println("Citas canceladas: " + canceladas);
    }
}
