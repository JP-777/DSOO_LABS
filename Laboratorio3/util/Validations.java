package util;

public final class Validations {
    private Validations() {}

    public static void noVacio(String contenido, String campo) {
        if (contenido == null || contenido.trim().isEmpty())
            throw new IllegalArgumentException("El campo '" + campo + "' no puede ser vacío");
    }
}
