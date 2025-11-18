package UI.test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class CompteurHandler implements EventHandler<ActionEvent> {
    private Label lab;
    private Compteur compteur;

    public CompteurHandler(Label lab, Compteur compteur){
        this.lab = lab;
        this.compteur = compteur;
    }

    @Override
    public void handle(ActionEvent event) {
        compteur.incrementer();
        lab.setText("-->" + compteur.getValeur());
    }
}

