package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class VuePiocheDestinations extends BorderPane {
    private IJeu jeu;
    private HBox cartes;
    private Label instruction;

    public VuePiocheDestinations(IJeu jeu) {
        this.jeu = jeu;
        cartes=new HBox();
        cartes.setMaxHeight(200);
        cartes.setMaxWidth(600);
        setStyle( "-fx-background-color: rgba(0, 0, 0, 0.7)");
        Button passer=new Button("Passer");
        passer.setMaxHeight(150);
        passer.setMaxWidth(180);
        ImageView skip=new ImageView("file:src/main/resources/images/miscellaneous/skip_button.png");
        passer.setGraphic(skip);
        passer.setStyle("-fx-background-color: transparent;");
        passer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                jeu.passerAEteChoisi();
            }
        });
        passer.setPadding(new Insets(0,75,0,0));
        Font f=Font.loadFont("file:src/main/resources/fonts/IMFeENsc28P.ttf",25);
        instruction=new Label(jeu.joueurCourantProperty().getName()+", veuillez selectionner des cartes à retirer");
        instruction.setStyle("-fx-text-fill: white");
        instruction.setFont(f);
        StackPane conteneur=new StackPane();
        conteneur.getChildren().add(instruction);
        conteneur.getChildren().add(cartes);
        conteneur.getChildren().add(passer);
        conteneur.setAlignment(instruction,Pos.TOP_CENTER);
        conteneur.setAlignment(cartes,Pos.CENTER);
        conteneur.setAlignment(passer,Pos.BOTTOM_CENTER);
        conteneur.setMaxHeight(250);
        setCenter(conteneur);
    }

    public void creerBindings(){
        ListChangeListener<IDestination> changementDesti=new ListChangeListener<IDestination>() {
            @Override
            public void onChanged(Change<? extends IDestination> change) {
                Platform.runLater(() -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            for(int i=0;i<change.getAddedSubList().size();i++){
                                ajouterDestination(change.getAddedSubList().get(i));
                            }
                            if(change.getAddedSize()<4){
                                cartes.setPadding(new Insets(0,0,0,50));
                            }
                        }
                        if(change.wasRemoved()){
                            for(int i=0;i<change.getRemovedSize();i++){
                                cartes.getChildren().remove(trouverDestination(change.getRemoved().get(i)));
                            }
                        }
                    }
                });
            }
        };
        jeu.destinationsInitialesProperty().addListener(changementDesti);

        ChangeListener changementJoueur=new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Platform.runLater(() -> {
                    instruction.setText(jeu.joueurCourantProperty().get().getNom() + ", veuillez selectionner des cartes à retirer");
                });
            }
        };
        jeu.joueurCourantProperty().addListener(changementJoueur);
    }

    private void ajouterDestination(IDestination d){
        VueDestination desti=new VueDestination(d);
        desti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                jeu.uneDestinationAEteChoisie(d.getNom());
            }
        });
        cartes.getChildren().add(desti);
    }

    private int trouverDestination(IDestination d){
        VueDestination carte=new VueDestination(d);
        for(int i=0;i<cartes.getChildren().size();i++){
            if(carte.equals(cartes.getChildren().get(i))){
                return i;
            }
        }
        return -1;
    }
}
