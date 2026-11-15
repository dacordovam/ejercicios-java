package cl.duoc.clinica.modelo;

public abstract class Tratamiento {
    private String descripcion;
    private int sesiones;
    private int duracion;

    public Tratamiento(String descripcion, int sesiones, int duracion) {
        setDescripcion(descripcion);
        setSesiones(sesiones);
        setDuracion(duracion);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        this.descripcion = descripcion.trim();
    }

    public int getSesiones() {
        return sesiones;
    }

    public void setSesiones(int sesiones) {
        if (sesiones < 1 || sesiones > 12) {
            throw new IllegalArgumentException("Las sesiones deben ser un número entero entre 1 y 12.");
        }
        this.sesiones = sesiones;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor que cero.");
        }
        this.duracion = duracion;
    }

    public abstract double calcularCosto();
    public abstract String getTipo();
    public double getCosto() {
        return calcularCosto();
    }
}
