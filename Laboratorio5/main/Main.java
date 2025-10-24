import service.ClinicaService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClinicaService service = new ClinicaService();
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== SISTEMA DE CITAS MÉDICAS =====");
            System.out.println("1. Registrar doctor");
            System.out.println("2. Registrar paciente");
            System.out.println("3. Registrar cita");
            System.out.println("4. Listar citas");
            System.out.println("5. Listar pacientes");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> service.registrarDoctor();
                case 2 -> service.registrarPaciente();
                case 3 -> service.registrarCita();
                case 4 -> service.listarCitas();
                case 5 -> service.listarPacientes();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
}
