package cl.duoc.clinica.controlador;

import cl.duoc.clinica.modelo.Implante;
import cl.duoc.clinica.modelo.Ortodoncia;
import cl.duoc.clinica.modelo.Tratamiento;
import cl.duoc.clinica.repositorio.ImplanteRepository;
import cl.duoc.clinica.repositorio.OrtodonciaRepository;
import com.google.gson.JsonParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class TratamientoController {

    @FXML
    private ComboBox<String> tipoComboBox;
    @FXML
    private TextField bracketsTextField;
    @FXML
    private CheckBox planDentalCheckBox;
    @FXML
    private CheckBox financiamientoCheckBox;
    @FXML
    private CheckBox coronaCheckBox;
    @FXML
    private TextField descripcionTextField;
    @FXML
    private TextField sesionesTextField;
    @FXML
    private TextField duracionTextField;
    @FXML
    private Label mensajeLabel;
    @FXML
    private TableView<Tratamiento> tratamientosTableView;
    private final ObservableList<Tratamiento> tratamientos =
            FXCollections.observableArrayList();

    private OrtodonciaRepository ortodonciaRepository;
    private ImplanteRepository implanteRepository;




    @FXML
    public void initialize() {
        tipoComboBox.getItems().addAll("Implante", "Ortodoncia");
        cambiarTipo();
        tratamientosTableView.setItems(tratamientos);

        try{
            ortodonciaRepository = new OrtodonciaRepository();
            implanteRepository = new ImplanteRepository();

            tratamientos.addAll(ortodonciaRepository.obtenerTodos());
            tratamientos.addAll(implanteRepository.obtenerTodos());
        }catch (IOException | JsonParseException e){
            mostrarMensaje("Error al cargar los tratamientos: " + e.getMessage(), false);
        }

    }

    @FXML
    public void cambiarTipo() {
        String tipoSeleccionado = tipoComboBox.getValue();
        boolean esOrtodoncia = "Ortodoncia".equals(tipoSeleccionado);
        bracketsTextField.setDisable(!esOrtodoncia);
        planDentalCheckBox.setDisable(!esOrtodoncia);
        financiamientoCheckBox.setDisable(!esOrtodoncia);
        boolean esImplante = "Implante".equals(tipoSeleccionado);
        coronaCheckBox.setDisable(!esImplante);
    }

    @FXML
    public void registrar() {
        String tipo = tipoComboBox.getValue();
        String descripcion = descripcionTextField.getText().trim();
        if(tipo == null){
            mostrarMensaje("Debe selecionar un tipo de tratamiento", false);
            return;
        }
        if(descripcion.isEmpty()){
            mostrarMensaje("La descripción no puede estar vacia", false);
            return;
        }

        int sesiones;

        try {
            sesiones = Integer.parseInt(sesionesTextField.getText().trim());
        } catch (NumberFormatException e) {
            mostrarMensaje("Las sesiones deben estar entre 1 y 12.", false);
            return;
        }
        if(sesiones < 1 || sesiones > 12){
            mostrarMensaje("Las sesiones deben estar entre 1 y 12.", false);
            return;
        }

        int duracion;
        try{
            duracion = Integer.parseInt(duracionTextField.getText().trim());
        }catch (NumberFormatException e){
            mostrarMensaje("La duración debe ser un número entero de minutos.", false);
            return;
        }
        if(duracion <= 0){
            mostrarMensaje("La duracion Debe ser un numero mayor a 0", false);
            return;
        }
        String brackets = bracketsTextField.getText().trim();
        if("Ortodoncia".equals(tipo) && brackets.isEmpty()){
            mostrarMensaje("El tipo de brakets no puede estar vacio", false);
            return;
        }
        if("Ortodoncia".equals(tipo)) {
            boolean planPrevio = planDentalCheckBox.isSelected();
            boolean financiamiento = financiamientoCheckBox.isSelected();
            Ortodoncia ortodoncia = new Ortodoncia(
                    descripcion,
                    sesiones,
                    duracion,
                    brackets,
                    planPrevio,
                    financiamiento
            );
            try{
                ortodonciaRepository.agregar(ortodoncia);
                tratamientos.add(ortodoncia);
                mostrarMensaje("Tratamiento registrado correctamente", true);
            } catch (IOException e) {
                mostrarMensaje("Error al registrar el tratamiento: " + e.getMessage(), false);
                return;
            }


        }else if ("Implante".equals(tipo)) {
            boolean coronaPorcelana = coronaCheckBox.isSelected();

            Implante implante = new Implante(
                    descripcion,
                    sesiones,
                    duracion,
                    coronaPorcelana
            );
            try{
                implanteRepository.agregar(implante);
                tratamientos.add(implante);
                mostrarMensaje("Tratamiento registrado correctamente", true);
            } catch (IOException e) {
                mostrarMensaje("Error al registrar el tratamiento: " + e.getMessage(), false);
                return;
            }
        }
        limpiarFormulario();
    }

    @FXML
    public void eliminar() {
        Tratamiento seleccionado =
                tratamientosTableView.getSelectionModel().getSelectedItem();

        if(seleccionado == null){
            mostrarMensaje("Debe seleccionar un tratamiento para eliminar", false);
            return;
        }
        try {
            if(seleccionado instanceof Ortodoncia ortodoncia) {
                ortodonciaRepository.eliminar(ortodoncia);
            }else if (seleccionado instanceof Implante implante) {
                implanteRepository.eliminar(implante);
            }
            tratamientos.remove(seleccionado);
            mostrarMensaje("Tratamiento eliminado correctamente", true);
        } catch (IOException e) {
            mostrarMensaje("Error al eliminar el tratamiento: " + e.getMessage(), false);
        }
    }


    private void limpiarFormulario() {
        descripcionTextField.clear();
        sesionesTextField.clear();
        duracionTextField.clear();
        bracketsTextField.clear();
        planDentalCheckBox.setSelected(false);
        financiamientoCheckBox.setSelected(false);
        coronaCheckBox.setSelected(false);

        tipoComboBox.getSelectionModel().clearSelection();
        cambiarTipo();

    }

    private void mostrarMensaje(String mensaje, boolean correcto) {
        mensajeLabel.setText(mensaje);

    }
}
