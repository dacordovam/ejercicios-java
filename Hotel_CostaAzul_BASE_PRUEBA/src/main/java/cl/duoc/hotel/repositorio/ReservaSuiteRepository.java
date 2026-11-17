package cl.duoc.hotel.repositorio;

import cl.duoc.hotel.modelo.ReservaSuite;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReservaSuiteRepository {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private List<ReservaSuite> reservas = new ArrayList<>();
    private final Path archivo = Path.of("reservas_suite.json");

    public ReservaSuiteRepository() throws IOException {
        cargar();
    }

    private void cargar() throws IOException {
        if(!Files.exists(archivo)) {
            return;
        }
        String json = Files.readString(archivo);
        ReservaSuite[] datos = gson.fromJson(json, ReservaSuite[].class);
        if(datos !=null){
            reservas.addAll(Arrays.asList(datos));
        }
    }

    public List<ReservaSuite> obtenerTodos() {
        return reservas;
    }

    public void agregar(ReservaSuite reserva) throws IOException {
        reservas.add(reserva);
        guardar();
    }

    public void eliminar(ReservaSuite reserva) throws IOException {
        reservas.remove(reserva);
        guardar();
    }

    private void guardar() throws IOException {
        String json = gson.toJson(reservas);
        Files.writeString(archivo, json);
    }
}
