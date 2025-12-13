package DSOO_LABS.laboratorio7.util;

import java.util.regex.Pattern;

public class Validador {
    
    // Validación de DNI (8 dígitos)
    public static boolean validarDNI(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return false;
        }
        return dni.matches("\\d{8}");
    }

    // Validación de teléfono (9 dígitos)
    public static boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        return telefono.matches("\\d{9}");
    }

    // Validación de correo electrónico
    public static boolean validarCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(regex, correo);
    }

    // Validación de nombre/apellido (solo letras y espacios)
    public static boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") && nombre.trim().length() >= 2;
    }

    // Validación de monto (debe ser positivo)
    public static boolean validarMonto(double monto) {
        return monto > 0;
    }

    // Validación de saldo suficiente
    public static boolean validarSaldoSuficiente(double saldoActual, double montoRetiro) {
        return saldoActual >= montoRetiro;
    }

    // Validación de ID (no vacío)
    public static boolean validarId(String id) {
        return id != null && !id.trim().isEmpty();
    }

    // Validación de número de cuenta
    public static boolean validarNumeroCuenta(String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            return false;
        }
        return numero.matches("\\d{4,}");
    }

    // Validación de dirección
    public static boolean validarDireccion(String direccion) {
        return direccion != null && direccion.trim().length() >= 5;
    }

    // Validación de contraseña (mínimo 6 caracteres)
    public static boolean validarContrasena(String contrasena) {
        return contrasena != null && contrasena.length() >= 6;
    }

    // Método para mostrar mensaje de error de validación
    public static void mostrarErrorValidacion(String campo, String razon) {
        Dialogo.error("Error de validación en '" + campo + "':\n" + razon);
    }

    // Validación completa de cliente
    public static boolean validarDatosCliente(String dni, String nombre, String apellido, 
                                               String direccion, String telefono, String correo) {
        boolean valido = true;

        if (!validarDNI(dni)) {
            mostrarErrorValidacion("DNI", "Debe tener exactamente 8 dígitos");
            valido = false;
        }
        if (!validarNombre(nombre)) {
            mostrarErrorValidacion("Nombre", "Debe contener solo letras y tener al menos 2 caracteres");
            valido = false;
        }
        if (!validarNombre(apellido)) {
            mostrarErrorValidacion("Apellido", "Debe contener solo letras y tener al menos 2 caracteres");
            valido = false;
        }
        if (!validarDireccion(direccion)) {
            mostrarErrorValidacion("Dirección", "Debe tener al menos 5 caracteres");
            valido = false;
        }
        if (!validarTelefono(telefono)) {
            mostrarErrorValidacion("Teléfono", "Debe tener exactamente 9 dígitos");
            valido = false;
        }
        if (!validarCorreo(correo)) {
            mostrarErrorValidacion("Correo", "Formato inválido (ejemplo: usuario@dominio.com)");
            valido = false;
        }

        return valido;
    }

    // Validación completa de empleado
    public static boolean validarDatosEmpleado(String dni, String nombre, String apellido, 
                                                String direccion, String telefono, String cargo) {
        boolean valido = true;

        if (!validarDNI(dni)) {
            mostrarErrorValidacion("DNI", "Debe tener exactamente 8 dígitos");
            valido = false;
        }
        if (!validarNombre(nombre)) {
            mostrarErrorValidacion("Nombre", "Debe contener solo letras y tener al menos 2 caracteres");
            valido = false;
        }
        if (!validarNombre(apellido)) {
            mostrarErrorValidacion("Apellido", "Debe contener solo letras y tener al menos 2 caracteres");
            valido = false;
        }
        if (!validarDireccion(direccion)) {
            mostrarErrorValidacion("Dirección", "Debe tener al menos 5 caracteres");
            valido = false;
        }
        if (!validarTelefono(telefono)) {
            mostrarErrorValidacion("Teléfono", "Debe tener exactamente 9 dígitos");
            valido = false;
        }
        if (cargo == null || cargo.trim().isEmpty()) {
            mostrarErrorValidacion("Cargo", "No puede estar vacío");
            valido = false;
        }

        return valido;
    }
}