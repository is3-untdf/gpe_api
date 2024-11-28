package untdf.is3.modelo;

public enum Exigencia {

    OBLIGATORIO('O'),
    RECOMENDADO('R');

    private final Character identificador;

    Exigencia(Character identificador) {
        this.identificador = identificador;
    }

    public Character getIdentificador() {
        return identificador;
    }

    public static Exigencia fromChar(Character identificador) {
        if (identificador == null) {
            return null;
        }

        for (Exigencia exigencia : values()) {
            if (exigencia.getIdentificador() != null && exigencia.getIdentificador() == identificador) {
                return exigencia;
            }
        }
        throw new IllegalArgumentException("No existe Exigencia para el caracter: " + identificador);
    }
}
