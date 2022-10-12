package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class VueInstruction extends BorderPane {
    private Label instruction;
    private IJeu jeu;
    private ImageView image;

    public VueInstruction(IJeu jeu) {
        this.jeu = jeu;
        Font f=Font.loadFont("file:src/main/resources/fonts/IMFeENsc28P.ttf",50);
        instruction=new Label(jeu.instructionProperty().get());
        instruction.setFont(f);
        instruction.setStyle("-fx-text-fill: white");
        StackPane conteneur=new StackPane();
        image=new ImageView("images/miscellaneous/black-marble-support.png");
        conteneur.getChildren().add(image);
        conteneur.getChildren().add(instruction);
        setCenter(conteneur);
    }

    public void creerBindings(){
        instruction.textProperty().bind(jeu.instructionProperty());
        image.fitHeightProperty().bind(Bindings.add(20,instruction.heightProperty()));
        image.fitWidthProperty().bind(Bindings.add(100,instruction.widthProperty()));
    }
}
