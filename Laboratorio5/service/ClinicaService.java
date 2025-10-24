package service;

import model.*;
import repo.*;
import java.util.Scanner;

public class ClinicaService {
    private DoctorRepo doctorRepo;
    private PacientesRepo pacienteRepo;
    private CitaRepo citaRepo;
    private Scanner sc;

    public ClinicaService() {
        doctorRepo = new DoctorRepo();
        pacienteRepo = new PacientesRepo();
        citaRepo = new CitaRepo();
        sc = new Scanner(System.in);
    }

    public void registrarDoctor() {
        System.out.println("\n--- REGISTRAR DOCTOR ---");
        System.out.print("Código: ");
        int codigo = sc.nextInt(); sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = sc.nextLine();
        System.out.print("Horario de atención: ");
        String horario = sc.nextLine();

        Doctor d = new Doctor(codigo, nombre, especialidad, horario);
        doctorRepo.agregarDoctor(d);
    }

    public void registrarPaciente() {
        System.out.println("\n--- REGISTRAR PACIENTE ---");
        System.out.print("Código: ");
        int codigo = sc.nextInt(); sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Edad: ");
        int edad = sc.nextInt();
        System.out.print("DNI: ");
        int dni = sc.nextInt(); sc.nextLine();

        Paciente p = new Paciente(codigo, nombre, edad, dni);
        pacienteRepo.agregarPaciente(p);
    }

    public void registrarCita() {
        System.out.println("\n--- REGISTRAR CITA ---");
        System.out.print("Código de cita: ");
        int codigo = sc.nextInt();
        System.out.print("Código de doctor: ");
        int codDoc = sc.nextInt();
        System.out.print("Código de paciente: ");
        int codPac = sc.nextInt(); sc.nextLine();

        Doctor doc = doctorRepo.buscarPorCodigo(codDoc);
        Paciente pac = pacienteRepo.buscarPorCodigo(codPac);

        if (doc == null || pac == null) {
            System.out.println("Error: Doctor o Paciente no encontrado.");
            return;
        }

        System.out.print("Fecha (dd/mm/yyyy): ");
        String fecha = sc.nextLine();
        System.out.print("Hora (hh:mm): ");
        String hora = sc.nextLine();
        System.out.print("Motivo: ");
        String motivo = sc.nextLine();

        Cita cita = new Cita(codigo, doc, pac, fecha, hora, motivo);
        citaRepo.agregarCita(cita);
    }

    public void listarCitas() {
        citaRepo.listarCitas();
    }

    public void listarPacientes() {
        pacienteRepo.listarPacientes();
    }
}
