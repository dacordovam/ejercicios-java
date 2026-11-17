package cl.duoc.hotel.modelo;

public class ReservaSuite extends Reserva {
    private boolean vistaMar;

    public ReservaSuite(String huesped, int noches, int cantidadHuespedes,
                    boolean vistaMar) {
        super(huesped, noches, cantidadHuespedes);
        setVistaMar(vistaMar);
    }

    public boolean isVistaMar() {
        return vistaMar;
    }

    public void setVistaMar(boolean vistaMar) {
        this.vistaMar = vistaMar;
    }

    @Override
    public double calcularCosto() {
        double costo = 80000.0 * getNoches();
        if (vistaMar) {
            costo *= 1.20;
        }
        return costo;
    }

    @Override
    public String getTipo() {
        return "Suite";
    }
}
