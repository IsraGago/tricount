package tricount;

import java.io.BufferedWriter;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
import java.io.FileWriter;
// import java.io.IOException;
// import java.io.NotSerializableException;
// import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import tricount.modelo.Gasto;
import tricount.modelo.Persona;

public class AppTricount extends Application {

    public static Stage primaryStage;
    public static List<Persona> personas = new ArrayList<>();
    public static List<Gasto> gastos = getGastos();
    public static final String PATH = "./src/saves/";

    public static List<Gasto> getGastos() {
        List<Gasto> gastos = new ArrayList<>();
        for (Persona p : personas) {
            for (Gasto g : p.gastos) {
                gastos.add(g);
            }
        }
        return gastos;
    }


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Tricount");
        cargarEscena("Personas");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void cargarEscena(String fxml) {
        try {
            // Cargar la nueva ventana
            FXMLLoader loader = new FXMLLoader(AppTricount.class.getResource("vista/" + fxml + ".fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene scene = new Scene(root);

            // Cambiar la escena
            primaryStage.setScene(scene);

        } catch (Exception e) {
            System.out.println("Error al cargar la escena: " + e.getMessage());
        }

    }

    public static void mostrarError(String titulo, String texto) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

    public static void mostrarMensaje(String titulo, String texto) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }

    public static double formatearImporte(String importeTexto) {
        double importe = 0;
        try {
            importe = Double.parseDouble(importeTexto);
        } catch (Exception e) {
            AppTricount.mostrarError("Formato invalido en el importe",
                    "El importe no tiene un formato válido. Ejempo: 59.99");
        }
        return importe;
    }

static public void guardarFichero(String path) {
        // Crear un fichero de texto para escritura
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) {
            out.write(Persona.finalizar(personas));
        } catch (Exception e) {
            System.out.println("Error de E/S");
        }
    }

    
}
