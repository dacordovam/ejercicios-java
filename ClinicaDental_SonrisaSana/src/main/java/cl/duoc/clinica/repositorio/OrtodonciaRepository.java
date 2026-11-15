package cl.duoc.clinica.repositorio;

import cl.duoc.clinica.modelo.Ortodoncia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrtodonciaRepository {
    private final Path archivo = Path.of("ortodoncias.json");
    private final List<Ortodoncia> tratamientos = new ArrayList<>();

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public OrtodonciaRepository() throws IOException {
        cargar();
    }

    private void cargar() throws IOException {
        if(!Files.exists(archivo)){
            return;
        }
        String json = Files.readString(archivo);
        Ortodoncia[]  datos = gson.fromJson(json, Ortodoncia[].class);

        if (datos != null){
            tratamientos.addAll(Arrays.asList(datos));
        }
    }

    public List<Ortodoncia> obtenerTodos() {
        return new ArrayList<>(tratamientos);

    }

    public void agregar(Ortodoncia tratamiento) throws IOException {
        tratamientos.add(tratamiento);
        try{
            guardar();
        }catch(IOException e){
            tratamientos.remove(tratamientos.size() - 1);
            throw e;
        }

    }

    public void eliminar(Ortodoncia tratamiento) throws IOException {
        int posicion = tratamientos.indexOf(tratamiento);

        if(posicion == -1){
            return;
        }
        tratamientos.remove(posicion);
        try{
            guardar();
        }catch(IOException e){
            tratamientos.add(posicion, tratamiento);
            throw e;
        }

    }

    private void guardar() throws IOException {
        String json = gson.toJson(tratamientos);
        Files.writeString(archivo, json);

    }
}
