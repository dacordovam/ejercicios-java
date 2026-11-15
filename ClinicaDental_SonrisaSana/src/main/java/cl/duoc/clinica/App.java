package cl.duoc.clinica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                App.class.getResource("/cl/duoc/clinica/view/main-view.fxml")
        );
        Scene scene = new Scene(loader.load());
        stage.setTitle("Clínica Dental SonrisaSana");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
