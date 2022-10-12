package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IRoute;
import fr.umontpellier.iut.IVille;
import fr.umontpellier.iut.rails.Jeu;
import fr.umontpellier.iut.rails.Route;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 *
 * On y définit le listener à exécuter lorsque qu'un élément du plateau a été choisi par l'utilisateur
 * ainsi que les bindings qui mettront ?à jour le plateau après la prise d'une route ou d'une ville par un joueur
 */
public class VuePlateau extends Pane {

    private IJeu jeu;

    public VuePlateau(IJeu jeu) {
        try {
            this.jeu=jeu;
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/plateau.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IRoute getRouteParGroup(String nom) {
        Jeu j=(Jeu) jeu;
        IRoute r=j.getRoutes().get(0);
        for (IRoute route : j.getRoutes()) {
            if (route.getNom().equals(nom)) {
                r=route;
            }
        }
        return r;
    }

    public IVille getVilleParGroup(String nom){
        Jeu j=(Jeu) jeu;
        IVille v=j.getVilles().get(0);
        for(IVille ville:j.getVilles()){
            if(ville.getNom().equals(nom)){
                v=ville;
            }
        }
        return v;
    }

    private boolean groupeDeRoute(Group g){
        if(g.getId().contains("-")){
            return true;
        }
        return false;
    }

    @FXML
    public void creerBindingsRoute(Group groupe){
        if(groupeDeRoute(groupe)){
            IRoute route=getRouteParGroup(groupe.getId());
            ChangeListener changementProprio=new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    Platform.runLater(() -> {
                        if (route.proprietaireProperty().get() != null) {
                            for (Node n : groupe.getChildren()) {
                                ImageView wagon = new ImageView("file:ressources/images/images/image-wagon-" + route.proprietaireProperty().get().getCouleur() + ".png");
                                wagon.setFitWidth(60);
                                wagon.setFitHeight(50);
                                wagon.setRotate(n.getRotate());
                                wagon.setLayoutX(n.getLayoutX() - 33);
                                wagon.setLayoutY(n.getLayoutY() - 23);
                                getChildren().add(wagon);
                            }
                        }
                    });
                }
            };
            route.proprietaireProperty().addListener(changementProprio);
        }
    }

    public void creerBindingsVille(Node cercle){
        IVille ville=getVilleParGroup(cercle.getId());
        ChangeListener changementProprio=new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Platform.runLater(() -> {
                    if (ville.proprietaireProperty().get() != null) {
                            ImageView gare = new ImageView("file:ressources/images/images/gare-" + ville.proprietaireProperty().get().getCouleur() + ".png");
                            gare.setFitWidth(45);
                            gare.setFitHeight(35);
                            gare.setLayoutX(cercle.getLayoutX() - 30);
                            gare.setLayoutY(cercle.getLayoutY() - 23);
                            getChildren().add(gare);
                    }
                });
            }
        };
        ville.proprietaireProperty().addListener(changementProprio);
    }

    @FXML
    private Group getGroupRoute(Node n) {
        for (Node node : getChildren()) {
            if(n instanceof Rectangle){
                if (node instanceof Group g && node.getId().equals("routes")) {
                    for (Node routes : g.getChildren()){
                        Group gRoute=(Group) routes;
                        for(Node rect : gRoute.getChildren()){
                            if(rect==n){
                                return gRoute;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    @FXML
    public void choixRouteOuVille(MouseEvent event) {
        if(event.getTarget() instanceof Rectangle rect){
            Group route=getGroupRoute(rect);
            jeu.uneVilleOuUneRouteAEteChoisie(route.getId());
            creerBindingsRoute(route);
        }
        else if(event.getTarget() instanceof Circle cercle){
            jeu.uneVilleOuUneRouteAEteChoisie(cercle.getId());
            creerBindingsVille(cercle);
        }
    }
}
