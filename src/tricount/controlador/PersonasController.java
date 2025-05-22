package tricount.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import tricount.AppTricount;
import tricount.Util;
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

    @FXML
    void guardarDeudasTexto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar deudas como...");
        fileChooser.setInitialDirectory(new File(AppTricount.PATH));
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showSaveDialog(AppTricount.primaryStage);
        if (selectedFile != null) {
            AppTricount.guardarDeudasFichero(selectedFile.toString());

        }
    }

    @FXML
    void abrirActividad(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar JSON...");
        fileChooser.setInitialDirectory(new File(AppTricount.PATH));
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("JSON Files", "*.json"));

        File selectedFile = fileChooser.showOpenDialog(AppTricount.primaryStage);
        if (selectedFile != null) {
            Persona[] t = Util.importarArrayJson(selectedFile.toString(), Persona[].class);
            AppTricount.personas.clear();
            AppTricount.personas.addAll(List.of(t));
            lstPersonas.getItems().clear();
            lstPersonas.getItems().addAll(AppTricount.personas);
        }
    }

    @FXML
    void guardarActividad(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar actividad como...");
        fileChooser.setInitialDirectory(new File(AppTricount.PATH));
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showSaveDialog(AppTricount.primaryStage);
        if (selectedFile != null) {
            Persona[] t = AppTricount.personas.toArray(new Persona[0]);
            Util.exportararArrayJson(selectedFile.toString(), t);
        }
    }

    @FXML
    void establecerModoClaro(ActionEvent event) {
        
        //TODO CAMBIAR ENTRE MODO CLARO Y OSCURO

        Scene scene = AppTricount.primaryStage.getScene();
        // scene.getStylesheets().clear();
        // scene.getStylesheets().add(getClass().getResource("/tricount/vista/claro.css").toExternalForm());

        Path path = Paths.get("src/tricount/vista/claro.css");
        File file = path.toFile();

        if (file.exists()) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(file.toURI().toString());
            System.out.println(file.toURI().toString());
        } else {
            System.out.println("No se encontró el archivo: " + file.getAbsolutePath());
        }


        System.out.println("modo claro");

    }

    @FXML
    void establecerModoOscuro(ActionEvent event) {
        Scene scene = AppTricount.primaryStage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("/tricount/vista/style.css").toExternalForm());
        System.out.println("modo oscuro");
    }

}
