package cl.duoc.hotel.modelo;

public abstract class Reserva {
    private String huesped;
    private int noches;
    private int cantidadHuespedes;

    public Reserva(String huesped, int noches, int cantidadHuespedes) {
        setHuesped(huesped);
        setNoches(noches);
        setCantidadHuespedes(cantidadHuespedes);
    }

    public String getHuesped() {
        return huesped;
    }

    public void setHuesped(String huesped) {
        if (huesped == null || huesped.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del huésped no puede estar vacía.");
        }
        this.huesped = huesped.trim();
    }

    public int getNoches() {
        return noches;
    }

    public void setNoches(int noches) {
        if (noches < 1 || noches > 12) {
            throw new IllegalArgumentException("La cantidad de noches debe ser un número entero entre 1 y 12.");
        }
        this.noches = noches;
    }

    public int getCantidadHuespedes() {
        return cantidadHuespedes;
    }

    public void setCantidadHuespedes(int cantidadHuespedes) {
        if (cantidadHuespedes <= 0) {
            throw new IllegalArgumentException("La cantidad de huéspedes debe ser mayor que cero.");
        }
        this.cantidadHuespedes = cantidadHuespedes;
    }

    public abstract double calcularCosto();
    public abstract String getTipo();
    public double getCosto() {
        return calcularCosto();
    }
}
