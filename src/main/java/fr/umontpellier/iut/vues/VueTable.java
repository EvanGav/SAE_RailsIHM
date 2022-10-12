package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class VueTable extends StackPane {
    private IJeu jeu;
    private VueDuJeu vueJeu;
    private VuePiocheDestinations vuePioche;
    private StackPane jeuSurTable;
    private VueInstruction instruction;
    private VueFin fin;

    public VueTable(IJeu jeu) {
        this.jeu = jeu;
        vueJeu=new VueDuJeu(jeu);
        vuePioche=new VuePiocheDestinations(jeu);
        jeuSurTable=new StackPane();
        fin=new VueFin(jeu);
        fin.toBack();
        Image background = new Image("images/miscellaneous/background_jeu.png");
        jeuSurTable.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, null, null)));
        getChildren().add(vuePioche);
        jeuSurTable.getChildren().add(vueJeu);
        getChildren().add(jeuSurTable);
        vueJeu.setDisable(true);
        vuePioche.toFront();
        instruction=new VueInstruction(jeu);
        getChildren().add(instruction);
        instruction.toBack();
        //getChildren().add(fin);
        setAlignment(Pos.CENTER);
    }

    public void creerBindings(){
        vuePioche.creerBindings();
        vueJeu.creerBindings();
        instruction.creerBindings();

        ChangeListener changementInstruction=new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Platform.runLater(() -> {
                    if(jeu.instructionProperty().get().equals("Fin de la partie.")){
                        getChildren().add(fin);
                    }
                    else{
                        instruction.toFront();
                        vueJeu.toBack();
                        //fin.toBack();
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        instruction.toBack();
                                        vueJeu.toFront();
                                    }
                                });

                            }
                        }, 1000);
                    }
                });
            }
        };
        jeu.instructionProperty().addListener(changementInstruction);

        ListChangeListener<IDestination> changementDesti=new ListChangeListener<IDestination>() {
            @Override
            public void onChanged(Change<? extends IDestination> change) {
                Platform.runLater(() -> {
                    if(jeu.destinationsInitialesProperty().isEmpty()){
                        vuePioche.toBack();
                        jeuSurTable.toFront();
                        vueJeu.setDisable(false);
                    }
                    else{
                        vueJeu.setDisable(true);
                        vuePioche.toFront();
                        //fin.toBack();
                        jeuSurTable.toBack();
                        instruction.toBack();
                    }
                });
            }
        };
        jeu.destinationsInitialesProperty().addListener(changementDesti);


    }
}
