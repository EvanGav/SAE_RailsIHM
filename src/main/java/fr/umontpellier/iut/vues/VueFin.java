package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VueFin extends BorderPane {

    private IJeu jeu;
    private ImageView image;

    public VueFin(IJeu jeu) {
        this.jeu = jeu;
        image=new ImageView("images/miscellaneous/logofin"+jeu.getJoueurs().get(0).getCouleur()+".png");
        setStyle( "-fx-background-color: rgba(0, 0, 0, 0.7)");
        Button quitter=new Button();
        quitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
                System.exit(0);
            }
        });
        ImageView imageQuitter=new ImageView("images/miscellaneous/quitButtonFin.png");
        imageQuitter.setPreserveRatio(true);
        imageQuitter.setFitHeight(80);
        quitter.setGraphic(imageQuitter);
        quitter.setStyle("-fx-background-color: transparent;");
        quitter.setPadding(new Insets(0,0,0,getMaxWidth()/2+240));
        VBox conteneur=new VBox();
        image.setPreserveRatio(true);
        image.setFitWidth(850);
        conteneur.getChildren().add(image);
        conteneur.getChildren().add(quitter);
        conteneur.setPadding(new Insets(0,0,0,550));
        conteneur.setSpacing(0);
        setCenter(conteneur);
    }

    private IJoueur joueurGagnant(){
        IJoueur gagnant=jeu.getJoueurs().get(0);
        for(IJoueur j: jeu.getJoueurs()){
            if(j.getScore()>gagnant.getScore()){
                gagnant=j;
            }
        }
        return gagnant;
    }

    public void creerBindings(){
        ChangeListener fin=new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                image=new ImageView("images/miscellaneous/logofin"+joueurGagnant().getCouleur()+".png");

            }
        };
        jeu.joueurCourantProperty();
    }
}
