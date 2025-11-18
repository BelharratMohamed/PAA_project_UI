package UI.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CompteurClics extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Compteur Clics");
        Pane myPane = new CompteurPane();
        Scene scene = new Scene(myPane);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
