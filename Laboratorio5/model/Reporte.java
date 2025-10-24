package model;

import java.util.List;

public class Reporte {
    private List<Cita> listaCitas;

    public Reporte(List<Cita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    // Mostrar todas las citas programadas
    public void mostrarTodasLasCitas() {
        System.out.println("\n=== TODAS LAS CITAS PROGRAMADAS ===");
        if (listaCitas == null || listaCitas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }

        for (Cita c : listaCitas) {
            System.out.println(c);
        }
    }

    // Listar citas por doctor
    public void listarCitasPorDoctor(int codigoDoctor) {
        System.out.println("\n=== CITAS DEL DOCTOR (Código: " + codigoDoctor + ") ===");
        if (listaCitas == null || listaCitas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }

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

    // Listar citas por paciente
    public void listarCitasPorPaciente(int codigoPaciente) {
        System.out.println("\n=== CITAS DEL PACIENTE (Código: " + codigoPaciente + ") ===");
        if (listaCitas == null || listaCitas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }

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

    // Mostrar totales de citas
    // (Se cuenta el total y se puede extender más adelante para estados si se agregan)
    public void mostrarTotales() {
        System.out.println("\n=== ESTADÍSTICAS DE CITAS ===");
        if (listaCitas == null || listaCitas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }

        int total = listaCitas.size();
        System.out.println("Total de citas registradas: " + total);
    }
}
