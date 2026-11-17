package cl.duoc.hotel.controlador;

import cl.duoc.hotel.modelo.ReservaSuite;
import cl.duoc.hotel.modelo.ReservaEstandar;
import cl.duoc.hotel.modelo.Reserva;
import cl.duoc.hotel.repositorio.ReservaSuiteRepository;
import cl.duoc.hotel.repositorio.ReservaEstandarRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class ReservaController {
    @FXML
    private ComboBox<String> tipoComboBox;

    @FXML
    private TextField nombreHuespedTextField;
    @FXML
    private TextField numeroNochesTextField;
    @FXML
    private TextField numeroPersonasTextField;
    @FXML
    private TextField tipoCamaTextField;

    @FXML
    private CheckBox tarifaSocioCheckBox;
    @FXML
    private CheckBox financiamientoCheckBox;
    @FXML
    private CheckBox vistaMarCheckBox;

    @FXML
    private TableView<Reserva> reservasTableView;

    @FXML
    private Label mensajeLabel;

    private ReservaEstandarRepository repositorioEstandar;
    private ReservaSuiteRepository repositorioSuite;
    private ObservableList<Reserva> reservas = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        tipoComboBox.getItems().addAll("Estándar", "Suite");
        cambiarTipo();
        reservasTableView.setItems(reservas);
        try {
            repositorioEstandar = new ReservaEstandarRepository();
            reservas.addAll(repositorioEstandar.obtenerTodos());
        } catch (IOException e) {
            mostrarMensaje("No se pudieron cargar las reservas estándar.", false);
        }
        try {
            repositorioSuite = new ReservaSuiteRepository();
            reservas.addAll(repositorioSuite.obtenerTodos());
        } catch (IOException e) {
            mostrarMensaje("No se pudieron cargar las reservas suite.", false);
        }

    }

    @FXML
    public void cambiarTipo() {
        String tipo = tipoComboBox.getValue();

        boolean esEstandar = "Estándar".equals(tipo);
        tipoCamaTextField.setDisable(!esEstandar);
        tarifaSocioCheckBox.setDisable(!esEstandar);
        financiamientoCheckBox.setDisable(!esEstandar);

        boolean esSuite = "Suite".equals(tipo);
        vistaMarCheckBox.setDisable(!esSuite);
    }

    @FXML
    public void registrar() {
        String tipo = tipoComboBox.getValue();
        if (tipo == null) {
            mostrarMensaje("Selecciona un tipo de reserva.", false);
            return;
        }
        String nombreHuesped = nombreHuespedTextField.getText();
        if (nombreHuesped.isBlank()) {
            mostrarMensaje("Ingresa el nombre del huésped.", false);
            return;
        }
        String numeroNochesStr = numeroNochesTextField.getText();
        int numeroNoches;
        try {
            numeroNoches = Integer.parseInt(numeroNochesStr);
        } catch (NumberFormatException e) {
            mostrarMensaje("Ingresa un número entero de noches.", false);
            return;
        }
        if (numeroNoches <= 0 || numeroNoches > 12) {
            mostrarMensaje("Las noches deben estar entre 1 y 12.", false);
            return;
        }
        String numeroHuespedes = numeroPersonasTextField.getText();
        int cantidadPersonas;
        try {
            cantidadPersonas = Integer.parseInt(numeroHuespedes);
        } catch (NumberFormatException e) {
            mostrarMensaje("Ingresa un número entero de personas.", false);
            return;
        }
        if (cantidadPersonas <= 0) {
            mostrarMensaje("La cantidad de personas debe ser mayor que cero.", false);
            return;
        }
        if ("Estándar".equals(tipo)) {
            String tipoCama = tipoCamaTextField.getText();
            if (tipoCama.isBlank()) {
                mostrarMensaje("Ingresa el tipo de cama.", false);
                return;
            }
            boolean tarifaSocio = tarifaSocioCheckBox.isSelected();
            boolean financiamientoActivo = financiamientoCheckBox.isSelected();

            ReservaEstandar reservaEstandar = new ReservaEstandar(nombreHuesped, numeroNoches, cantidadPersonas, tipoCama, tarifaSocio, financiamientoActivo);
            try {
                repositorioEstandar.agregar(reservaEstandar);
            } catch (IOException e) {
                mostrarMensaje("No se pudo guardar la reserva estándar.", false);
                return;
            }
            actualizarTabla();
            mostrarMensaje("Reserva registrada correctamente.", true);

            limpiarFormulario();

        } else {
            boolean vistaMar = vistaMarCheckBox.isSelected();
            ReservaSuite reservaSuite = new ReservaSuite(nombreHuesped, numeroNoches, cantidadPersonas, vistaMar);
            try {
                repositorioSuite.agregar(reservaSuite);
            } catch (IOException e) {
                mostrarMensaje("No se pudo guardar la reserva suite.", false);
                return;
            }
            actualizarTabla();
            mostrarMensaje("Reserva registrada correctamente.", true);

            limpiarFormulario();
        }
    }

    @FXML
    public void eliminar() {
        Reserva seleccionada = reservasTableView.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("Selecciona una reserva para eliminar.", false);
            return;
        }
        try {
            if (seleccionada instanceof ReservaEstandar reservaEstandar) {
                repositorioEstandar.eliminar(reservaEstandar);
            } else if (seleccionada instanceof ReservaSuite reservaSuite) {
                repositorioSuite.eliminar(reservaSuite);
            }
        } catch (IOException e){
                mostrarMensaje("No se pudo eliminar la reserva.", false);
                return;
            }
        mostrarMensaje("Reserva eliminada correctamente.", true);
        actualizarTabla();
    }

    private void actualizarTabla() {
        reservas.clear();
        reservas.addAll(repositorioEstandar.obtenerTodos());
        reservas.addAll(repositorioSuite.obtenerTodos());
    }

    private void limpiarFormulario() {
        nombreHuespedTextField.clear();
        numeroNochesTextField.clear();
        numeroPersonasTextField.clear();
        tipoCamaTextField.clear();
        tarifaSocioCheckBox.setSelected(false);
        financiamientoCheckBox.setSelected(false);
        vistaMarCheckBox.setSelected(false);
    }

    private void mostrarMensaje(String mensaje, boolean correcto) {
        mensajeLabel.setText(mensaje);
        if (correcto) {
            mensajeLabel.setStyle("-fx-text-fill: green");
        } else mensajeLabel.setStyle("-fx-text-fill: red");
    }
}
