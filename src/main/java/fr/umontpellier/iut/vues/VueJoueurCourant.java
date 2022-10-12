package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.*;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Jeu;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends BorderPane {
    
    private IJoueur joueur;
    private Label nbWagons;
    private Label nbGares;
    private IJeu j;
    private HBox cartes;
    private StackPane icone;
    private Button changeurAffichage;
    private boolean afficheLesCartes=true;


    
    public VueJoueurCourant(IJeu jeu) {
        j = jeu;
        joueur = j.getJoueurs().get(0);
        nbWagons = new Label(Integer.toString(j.getJoueurs().get(0).getNbWagons()));
        nbGares = new Label(Integer.toString(j.getJoueurs().get(0).getNbGares()));
        nbGares.setMaxHeight(30);
        Font f=Font.loadFont("file:src/main/resources/fonts/IMFeENsc28P.ttf",18);
        nbGares.setFont(f);
        nbGares.setStyle("-fx-font-weight: bold");
        nbWagons.setFont(f);
        nbWagons.setStyle("-fx-font-weight: bold");
        nbWagons.setMaxHeight(30);
        cartes=new HBox();
        cartes.setAlignment(Pos.CENTER);
        cartes.setSpacing(1);
        icone = new StackPane();
        cartes.setMaxHeight(300);

        changeurAffichage=new Button();
        changeurAffichage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!afficheLesCartes) {
                    afficheLesCartes=true;
                    ImageView imageBoutonChangement = new ImageView("images/miscellaneous/changerAffichage.png");
                    imageBoutonChangement.setFitWidth(30);
                    imageBoutonChangement.setFitHeight(30);
                    changeurAffichage.setGraphic(imageBoutonChangement);
                    afficherCartesWagon();
                } else {
                    ImageView imageBoutonChangement = new ImageView("images/miscellaneous/changeAffichageDesti.png");
                    imageBoutonChangement.setFitWidth(30);
                    imageBoutonChangement.setFitHeight(30);
                    changeurAffichage.setGraphic(imageBoutonChangement);
                    afficheLesCartes=false;
                    afficherDestinations();
                }
            }
        });
        ImageView imageBoutonChangement = new ImageView("images/miscellaneous/changerAffichage.png");
        imageBoutonChangement.setFitWidth(30);
        imageBoutonChangement.setFitHeight(30);
        changeurAffichage.setGraphic(imageBoutonChangement);
        changeurAffichage.setStyle("-fx-background-color: transparent;");
        changeurAffichage.setPadding(new Insets(10,0,0,0));

        ImageView imageSkip=new ImageView("images/miscellaneous/skipButtonJoueur.png");
        imageSkip.setFitHeight(50);
        imageSkip.setFitWidth(100);
        Button passer=new Button();
        passer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                jeu.passerAEteChoisi();
            }
        });
        passer.setPadding(new Insets(50,0,0,100));
        passer.setGraphic(imageSkip);
        passer.setStyle("-fx-background-color: transparent;");


        setMaxWidth(1300);
        setRight(icone);
        setLeft(passer);
        setCenter(cartes);
    }

    public HBox getCartes() {
        return cartes;
    }

    public Button getChangeurAffichage() {
        return changeurAffichage;
    }

    public void setAfficheLesCartes() {
        afficheLesCartes = true;
    }

    public void creerBindings() {
        ListChangeListener<ICouleurWagon> changement = new ListChangeListener<ICouleurWagon>() {
            @Override
            public void onChanged(Change<? extends ICouleurWagon> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for(int i=0;i<change.getAddedSize();i++){
                            ajouterCartes(change.getAddedSubList().get(i));
                        }
                    }
                    if(change.wasRemoved()){
                        for(int i=0;i<change.getRemovedSize();i++){
                            retirerCartes(change.getRemoved().get(i));
                        }
                    }
                    if(change.wasUpdated()){
                        cartes.getChildren().clear();
                        for(int i=0;i<change.getAddedSubList().size();i++){
                            ajouterCartes(change.getAddedSubList().get(i));
                        }
                    }
                }
            }
        };

        ChangeListener c = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Platform.runLater(() -> {
                    joueur = j.joueurCourantProperty().get();
                    nbWagons.setText(String.valueOf(j.joueurCourantProperty().get().getNbWagons()));
                    nbGares.setText(String.valueOf(j.joueurCourantProperty().get().getNbGares()));
                    cartes.getChildren().clear();
                    ImageView imageBoutonChangement = new ImageView("images/miscellaneous/changerAffichage.png");
                    imageBoutonChangement.setFitWidth(30);
                    imageBoutonChangement.setFitHeight(30);
                    changeurAffichage.setGraphic(imageBoutonChangement);
                    afficheLesCartes=true;
                    afficherCartesWagon();
                    icone.getChildren().clear();
                    creerIcone();
                    joueur.cartesWagonProperty().addListener(changement);
                    cartes.setPadding(new Insets(0,0,0,cartes.getChildren().size()*10));
                });
            }
        };
        j.joueurCourantProperty().addListener(c);
    }

    private void ajouterCartes(ICouleurWagon c) {
        Platform.runLater(()-> {
            VueCarteWagon carte = new VueCarteWagon(c);
            carte.ajouterCercle();
            carte.getNb().setText(String.valueOf(Collections.frequency(joueur.getCartesWagon(), c)));
            carte.getButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    j.uneCarteWagonAEteChoisie(carte.getCouleurWagon());
                }
            });
            if (joueur.getCartesWagon().contains(c) && trouverCarte(c) == -1) {
                cartes.getChildren().add(carte);
            } else {
                if (trouverCarte(c) > -1) {
                    cartes.getChildren().remove(trouverCarte(c));
                    cartes.getChildren().add(carte);
                }
            }



        carte.getButton().setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                carte.getImage().setFitHeight(130);
                carte.getImage().setFitWidth(180);
            }
        });

        carte.getButton().setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                carte.getImage().setFitHeight(100);
                carte.getImage().setFitWidth(150);
            }
        });

        carte.getNb().setText(String.valueOf(Integer.parseInt(carte.getNb().getText())));
        });
    }

    private int trouverCarte(ICouleurWagon c ){
        VueCarteWagon carte=new VueCarteWagon(c);
        for(int i=0;i<cartes.getChildren().size();i++){
            if(carte.estEquals((cartes.getChildren().get(i)))){
                return i;
            }
        }
        return -1;
    }

    private void retirerCartes(ICouleurWagon c) {
        Platform.runLater(()->{
            if(trouverCarte(c)>-1){
                VueCarteWagon carte=(VueCarteWagon) cartes.getChildren().get(trouverCarte(c));
                carte.getNb().setText(String.valueOf(Collections.frequency(joueur.getCartesWagon(),c)));
                if(carte.getNb().getText().equals(String.valueOf(0))){
                    cartes.getChildren().remove(carte);
                }
            }
        });
    }

    private void afficherDestinations(){
        cartes.getChildren().clear();
        for(IDestination d : joueur.getDestinations()){
            cartes.getChildren().add(new VueDestination(d));
        }
    }

    public void afficherCartesWagon(){
        cartes.getChildren().clear();
        for(ICouleurWagon c: joueur.cartesWagonProperty()){
            ajouterCartes(c);
        }
    }

    public void creerIcone(){
        ImageView fond=new ImageView(new Image("file:src/main/resources/images/miscellaneous/fondAvatar"+joueur.getCouleur()+".png"));
        fond.setFitHeight(170);
        fond.setFitWidth(170);
        ImageView avatar=new ImageView(new Image("file:src/main/resources/images/avatar/avatar"+joueur.getCouleur()+".png"));

        Image conteneur=new Image("file:src/main/resources/images/miscellaneous/asMap-traindestroyBG.png");

        StackPane gares=new StackPane();
        gares.setMaxWidth(60);
        gares.setMaxHeight(60);
        ImageView conteneurGare=new ImageView(conteneur);
        conteneurGare.setFitHeight(60);
        conteneurGare.setFitWidth(60);
        gares.getChildren().add(conteneurGare);
        gares.getChildren().add(nbGares);
        gares.setAlignment(nbGares,Pos.BOTTOM_CENTER);
        ImageView gareJoueur=new ImageView("file:ressources/images/images/gare-"+joueur.getCouleur()+".png");
        gareJoueur.setFitWidth(35);
        gareJoueur.setFitHeight(33);
        gares.getChildren().add(gareJoueur);
        gares.setAlignment(gareJoueur,Pos.TOP_CENTER);
        nbGares.setAlignment(Pos.TOP_CENTER);

        StackPane wagons=new StackPane();
        wagons.setMaxWidth(60);
        wagons.setMaxHeight(60);
        ImageView conteneurWagons=new ImageView(conteneur);
        conteneurWagons.setFitHeight(60);
        conteneurWagons.setFitWidth(60);
        wagons.getChildren().add(conteneurWagons);
        wagons.getChildren().add(nbWagons);
        wagons.setAlignment(nbWagons,Pos.BOTTOM_CENTER);
        ImageView wagonJoueur = new ImageView("file:ressources/images/images/image-wagon-"+joueur.getCouleur()+".png");
        wagonJoueur.setFitHeight(50);
        wagonJoueur.setFitWidth(50);
        wagons.getChildren().add(wagonJoueur);
        wagons.setAlignment(wagonJoueur,Pos.TOP_CENTER);
        nbWagons.setAlignment(Pos.TOP_CENTER);


        avatar.setFitHeight(120);
        avatar.setFitWidth(90);
        icone.setAlignment(Pos.CENTER);
        icone.getChildren().add(fond);
        icone.getChildren().add(avatar);
        icone.getChildren().add(gares);
        icone.getChildren().add(wagons);
        icone.getChildren().add(changeurAffichage);
        icone.setAlignment(gares, Pos.BOTTOM_LEFT);
        icone.setAlignment(wagons,Pos.TOP_LEFT);
        icone.setAlignment(changeurAffichage,Pos.TOP_RIGHT);
    }
}