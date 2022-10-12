package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.ArrayList;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends VBox {

    private IJeu jeu;
    private ArrayList<IJoueur> joueursAdverse;
    private IJoueur joueurCourant;

    public VueAutresJoueurs(IJeu jeu) {
        this.jeu=jeu;
        joueurCourant=jeu.getJoueurs().get(0);
        joueursAdverse=new ArrayList<>();
        //ajouterAdverse();
        setSpacing(20);
    }

    private void ajouterAdverse(){
        for(IJoueur j : jeu.getJoueurs()){
            if(j!=joueurCourant){
                joueursAdverse.add(j);
                iconeAdverse(j);
            }
        }
    }

    public void creerBindings(){
        ChangeListener c = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Platform.runLater(() -> {
                    getChildren().clear();
                    joueurCourant=jeu.joueurCourantProperty().get();
                    ajouterAdverse();
                });
            }
        };
        jeu.joueurCourantProperty().addListener(c);
    }

    private void iconeAdverse(IJoueur j){
        StackPane icone=new StackPane();
        ImageView fond=new ImageView("file:src/main/resources/images/miscellaneous/support-opponent-highlight-Horizontal-"+j.getCouleur()+".png");
        fond.setFitWidth(290);
        fond.setFitHeight(150);
        StackPane conteneurAvatar=new StackPane();
        conteneurAvatar.setMaxWidth(115);
        conteneurAvatar.setMaxHeight(100);
        ImageView avatar=new ImageView(new Image("file:src/main/resources/images/avatar/avatar"+j.getCouleur()+".png"));
        avatar.setFitHeight(90);
        avatar.setFitWidth(80);
        conteneurAvatar.getChildren().add(avatar);
        conteneurAvatar.setAlignment(Pos.CENTER);
        icone.getChildren().add(fond);
        icone.getChildren().add(conteneurAvatar);
        icone.setAlignment(conteneurAvatar, Pos.CENTER_LEFT);

        HBox gareAdverse=new HBox();
        ImageView imageGare=new ImageView("file:ressources/images/images/gare-"+j.getCouleur()+".png");
        imageGare.setFitHeight(35);
        imageGare.setFitWidth(33);
        gareAdverse.getChildren().add(imageGare);
        StackPane gares=new StackPane();
        gares.setMaxHeight(80);
        gares.setMaxWidth(40);
        ImageView conteneurCompteurGare=new ImageView("file:src/main/resources/images/miscellaneous/avatar-ring-ps4.png");
        conteneurCompteurGare.setFitWidth(30);
        conteneurCompteurGare.setFitHeight(30);
        Label nbGares=new Label(String.valueOf(j.getNbGares()));
        Font f=Font.loadFont("file:src/main/resources/fonts/IMFeENsc28P.ttf",22);
        nbGares.setFont(f);
        nbGares.setStyle("-fx-font-weight: bold");
        gares.getChildren().add(conteneurCompteurGare);
        gares.getChildren().add(nbGares);
        gares.setAlignment(Pos.CENTER);
        gareAdverse.getChildren().add(gares);

        HBox wagonsAdverse=new HBox();
        ImageView imageWagon=new ImageView("file:src/main/resources/images/miscellaneous/wagon-penche-"+j.getCouleur()+".png");
        imageWagon.setFitHeight(40);
        imageWagon.setFitWidth(40);
        wagonsAdverse.getChildren().add(imageWagon);
        ImageView conteneurCompteurWagons=new ImageView("file:src/main/resources/images/miscellaneous/avatar-ring-ps4.png");
        conteneurCompteurWagons.setFitWidth(30);
        conteneurCompteurWagons.setFitHeight(30);
        StackPane wagons=new StackPane();
        Label nbWagons=new Label(String.valueOf(j.getNbWagons()));
        nbWagons.setFont(f);
        nbWagons.setStyle("-fx-font-weight: bold");
        wagons.getChildren().add(conteneurCompteurWagons);
        wagons.getChildren().add(nbWagons);
        wagons.setAlignment(Pos.CENTER);
        wagonsAdverse.getChildren().add(wagons);

        HBox cartesAdverse=new HBox();
        ImageView imageCarte=new ImageView("file:src/main/resources/images/wagons.png");
        imageCarte.setFitHeight(30);
        imageCarte.setFitWidth(40);
        cartesAdverse.getChildren().add(imageCarte);
        ImageView conteneurCompteurcartes=new ImageView("file:src/main/resources/images/miscellaneous/avatar-ring-ps4.png");
        conteneurCompteurcartes.setFitWidth(30);
        conteneurCompteurcartes.setFitHeight(30);
        StackPane cartes=new StackPane();
        Label nbCartes=new Label(String.valueOf(j.getCartesWagon().size()));
        nbCartes.setFont(f);
        nbCartes.setStyle("-fx-font-weight: bold");
        cartes.getChildren().add(conteneurCompteurcartes);
        cartes.getChildren().add(nbCartes);
        cartes.setAlignment(Pos.CENTER);
        cartesAdverse.getChildren().add(cartes);

        HBox destinationsAdverse=new HBox();
        ImageView imageDestination=new ImageView("file:src/main/resources/images/destinations.png");
        imageDestination.setFitHeight(30);
        imageDestination.setFitWidth(40);
        destinationsAdverse.getChildren().add(imageDestination);
        ImageView conteneurCompteurDestination=new ImageView("file:src/main/resources/images/miscellaneous/avatar-ring-ps4.png");
        conteneurCompteurDestination.setFitWidth(30);
        conteneurCompteurDestination.setFitHeight(30);
        StackPane destinations=new StackPane();
        Label nbDestinations=new Label(String.valueOf(j.getDestinations().size()));
        nbDestinations.setFont(f);
        nbDestinations.setStyle("-fx-font-weight: bold");
        destinations.getChildren().add(conteneurCompteurDestination);
        destinations.getChildren().add(nbDestinations);
        destinations.setAlignment(Pos.CENTER);
        destinationsAdverse.getChildren().add(destinations);

        BorderPane conteneurMainJoueurAdverse=new BorderPane();
        conteneurMainJoueurAdverse.setMaxWidth(70);
        conteneurMainJoueurAdverse.setMaxHeight(100);

        HBox test=new HBox();
        test.setPadding(new Insets(0,0,0,100));
        test.getChildren().add(gareAdverse);
        test.getChildren().add(wagonsAdverse);
        conteneurMainJoueurAdverse.setTop(test);

        HBox test2=new HBox();
        test2.setPadding(new Insets(0,0,0,100));
        test2.getChildren().add(cartesAdverse);
        test2.getChildren().add(destinationsAdverse);
        conteneurMainJoueurAdverse.setBottom(test2);

        icone.getChildren().add(conteneurMainJoueurAdverse);
        getChildren().add(icone);
    }
}
