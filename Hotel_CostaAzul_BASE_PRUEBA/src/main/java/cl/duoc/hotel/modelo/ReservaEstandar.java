package cl.duoc.hotel.modelo;

public class ReservaEstandar extends Reserva implements Financiable {
    private String tipoCama;
    private boolean tarifaSocio;
    private boolean financiamientoActivo;

    public ReservaEstandar(String huesped, int noches, int cantidadHuespedes,
                      String tipoCama, boolean tarifaSocio,
                      boolean financiamientoActivo) {
        super(huesped, noches, cantidadHuespedes);
        setTipoCama(tipoCama);
        setTarifaSocio(tarifaSocio);
        setFinanciamientoActivo(financiamientoActivo);
    }

    public String getTipoCama() {
        return tipoCama;
    }

    public void setTipoCama(String tipoCama) {
        if (tipoCama == null || tipoCama.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de cama no puede estar vacío.");
        }
        this.tipoCama = tipoCama.trim();
    }

    public boolean isTarifaSocio() {
        return tarifaSocio;
    }

    public void setTarifaSocio(boolean tarifaSocio) {
        this.tarifaSocio = tarifaSocio;
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
        double costo = 40000.0 * getNoches();
        if (!tarifaSocio) {
            costo *= 1.15;
        }
        return costo;
    }

    @Override
    public String getTipo() {
        return "Estándar";
    }
}
