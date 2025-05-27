package tricount.controlador;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tricount.modelo.Util;
import tricount.AppTricount;
import tricount.modelo.Gasto;
import tricount.modelo.Persona;

public class AddGastoController implements Initializable {

    @FXML
    private ChoiceBox<Persona> cbPersona;

    @FXML
    private TextField txtAsunto;

    @FXML
    private TextField txtImporte;
    
    @FXML
    private Label txtResultado;

    @FXML
    void addGasto(ActionEvent event) {
        String mensaje = "";
        if (txtAsunto.getText().isEmpty()) {
            AppTricount.mostrarError("Debe proporcionar un asunto",
                    "Debe proporcionar un asunto para el gasto.");
            return;
        }
        if (!AppTricount.personas.contains(cbPersona.getValue())) {
            AppTricount.mostrarError("Debe seleccionar una persona",
                    "Debe seleccionar una persona para añadir un gasto.");
            return;
        }
        String importeTexto = txtImporte.getText();
        importeTexto = importeTexto.replace(',', '.');

        Gasto g = new Gasto(txtAsunto.getText(), AppTricount.formatearImporte(importeTexto));

        if (!AppTricount.gastos.contains(g)) {
            for (Persona p : AppTricount.personas) {
                if (p.equals(cbPersona.getValue())) {
                    p.añadirGasto(g);
                }
            }
            AppTricount.gastos = AppTricount.getGastos();
            mensaje = "El gasto ("+g.getAsunto()+") ha sido añadido exitosamente";
        }
        txtResultado.setText(mensaje);
        
    }

    @FXML
    void cargarGastos(ActionEvent event) {
        AppTricount.cargarEscena("Gastos");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbPersona.getItems().addAll(AppTricount.personas);
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
