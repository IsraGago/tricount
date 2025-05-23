package tricount.controlador;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import saves.deudas.Util;
import tricount.AppTricount;
import tricount.modelo.Gasto;
import tricount.modelo.Persona;

public class GastoController implements Initializable {

    static Persona nuevoPagador = null;
    static Persona viejoPagador = null;

    @FXML
    private ChoiceBox<Persona> lstPersona;

    @FXML
    private TextField txtAsunto;

    @FXML
    private TextField txtImporte;

    @FXML
    private Label txtResultado;

    @FXML
    private Label lblTitulo;

    @FXML
    void actualizarGasto(ActionEvent event) {
        if (viejoPagador.equals(lstPersona.getValue())) {
            // no cambió de persona

            // edito el asunto y el importe
            try {
                for (Persona p : AppTricount.personas) {
                    for (Gasto g : p.gastos) {
                        if (g.equals(GastosController.gastoSeleccionado)) {
                            g.setImporte(AppTricount.formatearImporte(txtImporte.getText()));
                            g.setAsunto(txtAsunto.getText());
                        }
                    }
                }
            } catch (Exception e) {
                AppTricount.mostrarError("Error en los campos", "Comprueba el formato del asunto y el importe");
                return;
            }
        } else {
            // cambió de persona
            for (Persona p : AppTricount.personas) {
                if (!p.equals(lstPersona.getValue()) && p.gastos.contains(GastosController.gastoSeleccionado)) {
                    // borro el gasto de la persona anterior
                    p.gastos.remove(GastosController.gastoSeleccionado);

                } else if (p.equals(lstPersona.getValue())) {
                    // añado el gasto en la persona nueva
                    p.gastos.add(new Gasto(txtAsunto.getText(), AppTricount.formatearImporte(txtImporte.getText())));
                }
            }
        }
        // recargo la lista de gastos
        AppTricount.gastos = AppTricount.getGastos();
        txtResultado.setText("Gasto editado con éxito.");
    }

    @FXML
    void cargarGastos(ActionEvent event) {
        AppTricount.cargarEscena("Gastos");
    }

    @FXML
    void cargarPersonas(ActionEvent event) {
        AppTricount.cargarEscena("Personas");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
 

        lstPersona.getItems().addAll(AppTricount.personas);
        lblTitulo.setText("Gasto: " + GastosController.gastoSeleccionado.getAsunto());
        for (Persona p : AppTricount.personas) {
            if (p.gastos.contains(GastosController.gastoSeleccionado)) {
                viejoPagador = p;
            }
        }
        if (viejoPagador != null) {
            lstPersona.setValue(viejoPagador);
        }
        txtAsunto.setText(GastosController.gastoSeleccionado.getAsunto());
        txtImporte.setText(String.valueOf(GastosController.gastoSeleccionado.getImporte()));
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
