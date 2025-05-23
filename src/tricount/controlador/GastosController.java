package tricount.controlador;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import saves.deudas.Util;
import tricount.AppTricount;
import tricount.modelo.Gasto;
import tricount.modelo.Persona;

public class GastosController implements Initializable {

    public static Gasto gastoSeleccionado;

    @FXML
    private Button btnAddGasto;

    @FXML
    private Button btnVentanaPersonas;

    @FXML
    private ListView<Gasto> lstGastos;

    @FXML
    void cargarPersonas(ActionEvent event) {
        AppTricount.cargarEscena("Personas");
    }

    @FXML
    void cargarAddGasto(ActionEvent event) {
        AppTricount.cargarEscena("AddGasto");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        lstGastos.getItems().addAll(AppTricount.gastos);

        lstGastos.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, gastoSeleccionado) -> {
                    GastosController.gastoSeleccionado = gastoSeleccionado;
                });
    }

    @FXML
    void editarGasto(ActionEvent event) {
        if (gastoSeleccionado == null) {
            AppTricount.mostrarError("No hay gasto seleccionado", "Para editar un gasto primero debes seleccionarlo.");
            return;
        }
        AppTricount.cargarEscena("Gasto");
    }

    @FXML
    void borrarGasto(ActionEvent event) {
        for (Persona p : AppTricount.personas) {
            if (p.gastos.contains(gastoSeleccionado)) { // No creo que haga falta poner el contains porque cada gasto es
                                                        // único
                p.gastos.remove(gastoSeleccionado);
                lstGastos.getItems().remove(gastoSeleccionado);
                return;
            }
        }
    }

        @FXML
    void establecerModoClaro(ActionEvent event) {
        String css = Objects.requireNonNull(getClass().getResource(AppTricount.MODO_CLARO_PATH)).toExternalForm();
        AppTricount.cambiarModo(css);
        Util.writeStringToFile("claro", AppTricount.PATH + "config.txt");

    }

    @FXML
    void establecerModoOscuro(ActionEvent event) {
        String css = Objects.requireNonNull(getClass().getResource(AppTricount.MODO_OSCURO_PATH)).toExternalForm();
        AppTricount.cambiarModo(css);
        Util.writeStringToFile("oscuro", AppTricount.PATH + "config.txt");
    }

}
