package tricount.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tricount.AppTricount;
import tricount.modelo.Persona;

public class PersonasController implements Initializable {
    @FXML
    private Button bntAdd;

    @FXML
    private Button bntEliminar;

    @FXML
    private Button bntVentanaGasto;

    @FXML
    private ListView<Persona> lstPersonas;

    @FXML
    private TextField txtNombre;

    @FXML
    void borrarUsuario(ActionEvent event) {
        if (txtNombre.getText() == null || txtNombre.getText().equals("")) {
            AppTricount.mostrarError("No se ha podido borrar a la persona", "No ha seleccionado a ninguna persona.");
            return;
        }
        Persona p = new Persona(txtNombre.getText());

        if (lstPersonas.getItems().contains(p)) {
            lstPersonas.getItems().remove(p);
            AppTricount.personas.remove(p);
            txtNombre.setText("");
        } else {
            AppTricount.mostrarError("No se ha podido borrar a la persona",
                    "La persona " + txtNombre.getText() + " no está en la lista.");
        }
    }

    @FXML
    void insertarUsuario(ActionEvent event) {
        Persona p = new Persona(txtNombre.getText());
        if (!lstPersonas.getItems().contains(p)) {
            AppTricount.personas.add(p);
            lstPersonas.getItems().add(p);
            txtNombre.setText("");
        } else {
            AppTricount.mostrarError("Inserción inválida", "La persona con el nombre " + txtNombre.getText()
                    + " ya está añadida. No pueden haber dos personas con el mismo nombre");
        }

    }

    @FXML
    void ventanaGastos(ActionEvent event) {
        AppTricount.cargarEscena("Gastos");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstPersonas.getItems().addAll(AppTricount.personas);

        lstPersonas.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, personaSeleccionada) -> {
                    // System.out.println(personaSeleccionada.getNombre());
                    if (personaSeleccionada != null) {
                        txtNombre.setText(personaSeleccionada.getNombre());
                    }
                });
    }

    @FXML
    void cargarFinalizar(ActionEvent event) {
        // System.out.println(Persona.finalizar(AppTricount.personas));
        if (AppTricount.personas.size() <= 1) {
            AppTricount.mostrarError("Error", "No puedes finalizar la actividad si solo hay una persona anotada.");
            return;
        } else if (Persona.getDineroGastadoTotal(AppTricount.personas) <= 0) {
            AppTricount.mostrarError("Error", "No puedes finalizar la actividad si no hay gastos.");
            return;
        }
        AppTricount.mostrarMensaje("Deudas", Persona.finalizar(AppTricount.personas));
    }

}
