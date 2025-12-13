package DSOO_LABS.laboratorio7.util;

import javax.swing.JOptionPane;

/**
 * Utilidad mínima para mostrar mensajes en cuadros de diálogo.
 * Úsala para reemplazar System.out/err cuando quieras UI.
 */
public class Dialogo {

    private Dialogo() {}

    public static void info(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void warning(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    public static void error(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirmar(String mensaje) {
        int r = JOptionPane.showConfirmDialog(
                null,
                mensaje,
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return r == JOptionPane.YES_OPTION;
    }
}
