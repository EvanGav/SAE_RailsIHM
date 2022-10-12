package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Collections;
import java.util.Objects;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends StackPane {

    private ICouleurWagon couleurWagon;
    private Label nb;
    private Button b;
    private ImageView image;

    public VueCarteWagon(ICouleurWagon couleurWagon) {
        this.couleurWagon = couleurWagon;
        b = new Button();
        image=new ImageView("images/cartesWagons/carte-wagon-"+couleurWagon.toString().toUpperCase()+".png");
        image.setFitHeight(100);
        image.setFitWidth(150);
        b.setGraphic(image);
        b.setStyle("-fx-background-color: transparent;");

        setMaxHeight(b.getHeight()+10);
        getChildren().add(b);
    }

    public void ajouterCercle(){
        Circle cercle=new Circle(15);
        StackPane compt=new StackPane();
        cercle.setFill(Paint.valueOf("white"));
        cercle.setStroke(Paint.valueOf("black"));
        compt.getChildren().add(cercle);
        nb=new Label("1");
        compt.getChildren().add(nb);
        getChildren().add(compt);
        compt.setMaxHeight(25);
        compt.setMaxWidth(25);
        compt.setAlignment(Pos.CENTER);
        setAlignment(compt,Pos.TOP_RIGHT);
    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }

    public Label getNb() {
        return nb;
    }

    public Button getButton() {
        return b;
    }

    public ImageView getImage() {
        return image;
    }

    public boolean estEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VueCarteWagon that = (VueCarteWagon) o;
        return Objects.equals(couleurWagon, that.couleurWagon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couleurWagon);
    }
}
