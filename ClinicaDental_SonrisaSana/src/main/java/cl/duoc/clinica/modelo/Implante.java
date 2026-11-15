package cl.duoc.clinica.modelo;

public class Implante extends Tratamiento {
    private boolean coronaPorcelana;

    public Implante(String descripcion, int sesiones, int duracion,
                    boolean coronaPorcelana) {
        super(descripcion, sesiones, duracion);
        setCoronaPorcelana(coronaPorcelana);
    }

    public boolean isCoronaPorcelana() {
        return coronaPorcelana;
    }

    public void setCoronaPorcelana(boolean coronaPorcelana) {
        this.coronaPorcelana = coronaPorcelana;
    }

    @Override
    public double calcularCosto() {
        double costo = 350000;
        if (coronaPorcelana) {
            costo *= 1.20;
        }
        return costo;
    }

    @Override
    public String getTipo() {
        return "Implante";
    }
}
