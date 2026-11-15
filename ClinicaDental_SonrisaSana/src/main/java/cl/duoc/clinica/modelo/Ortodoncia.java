package cl.duoc.clinica.modelo;

public class Ortodoncia extends Tratamiento implements Financiable {
    private String tipoBrackets;
    private boolean planDentalPrevio;
    private boolean financiamientoActivo;

    public Ortodoncia(String descripcion, int sesiones, int duracion,
                      String tipoBrackets, boolean planDentalPrevio,
                      boolean financiamientoActivo) {
        super(descripcion, sesiones, duracion);
        setTipoBrackets(tipoBrackets);
        setPlanDentalPrevio(planDentalPrevio);
        setFinanciamientoActivo(financiamientoActivo);
    }

    public String getTipoBrackets() {
        return tipoBrackets;
    }

    public void setTipoBrackets(String tipoBrackets) {
        if (tipoBrackets == null || tipoBrackets.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de brackets no puede estar vacío.");
        }
        this.tipoBrackets = tipoBrackets.trim();
    }

    public boolean isPlanDentalPrevio() {
        return planDentalPrevio;
    }

    public void setPlanDentalPrevio(boolean planDentalPrevio) {
        this.planDentalPrevio = planDentalPrevio;
    }

    @Override
    public boolean tieneFinanciamientoActivo() {
        return financiamientoActivo;
    }

    @Override
    public void setFinanciamientoActivo(boolean financiamientoActivo) {
        this.financiamientoActivo = financiamientoActivo;
    }

    @Override
    public double calcularCosto() {
        double costo = 85000;
        if (!planDentalPrevio) {
            costo *= 1.15;
        }
        return costo;
    }

    @Override
    public String getTipo() {
        return "Ortodoncia";
    }
}
