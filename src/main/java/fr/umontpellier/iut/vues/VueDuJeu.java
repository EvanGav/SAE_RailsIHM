package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJeu;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends BorderPane {

    private IJeu jeu;
    private VuePlateau plateau;
    private VueJoueurCourant jCourant;
    private VueAutresJoueurs jAutre;
    private Button piocheCartesWagons;
    private Button piocheDestinations;
    private VBox piocheVisible;
    private Label instruction;

    private Slider slider;
    private Slider slider2;
    private StackPane stackPanedesti;
    private StackPane stackPanecarte;
    private Rectangle2D rectangle2Ddesti;
    private Rectangle rectangledesti;
    private Rectangle2D rectangle2Dcarte;
    private Rectangle rectanglecarte;
    private ImageView gaugevidedesti =new ImageView("images/miscellaneous/gaugedestivide.png");
    private ImageView gaugepleindesti =new ImageView("images/miscellaneous/gaugedestirempli.png");

    private ImageView gaugeremplicarte = new ImageView("images/miscellaneous/gaugecarterempli.png");
    private ImageView gaugevidecarte = new ImageView("images/miscellaneous/gaugecartevide.png");




    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        plateau = new VuePlateau(jeu);
        jCourant = new VueJoueurCourant(jeu);
        jAutre=new VueAutresJoueurs(jeu);
        instruction=new Label("test");

        stackPanedesti = new StackPane();
        stackPanecarte = new StackPane();
        slider= new Slider();
        slider2 = new Slider();
        slider2.setMin(0);
        slider2.setMax(110);
        slider2.setValue(110-4*jeu.getJoueurs().size());



        slider.setMin(0);
        slider.setMax(46);
        slider.setValue(46-3*jeu.getJoueurs().size());
        getChildren().add(stackPanedesti);
        getChildren().add(stackPanecarte);
        stackPanedesti = new StackPane();
        stackPanecarte= new StackPane();

        initGaugeCarte();
        initGaugeDesti();
        setupcropcarte();
        setupcropdesti();

        ImageView imageDestination=new ImageView("file:src/main/resources/images/destinations.png");
        imageDestination.setFitHeight(100);
        imageDestination.setFitWidth(150);
        piocheDestinations=new Button();
        piocheDestinations.setGraphic(imageDestination);
        piocheDestinations.setStyle("-fx-background-color: transparent;");
        piocheDestinations.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                jeu.uneDestinationAEtePiochee();
                slider.setValue(slider.getValue()-3);
                setupcropdesti();
            }
        });
        HBox gaugePiocheDesti=new HBox();
        gaugePiocheDesti.getChildren().add(piocheDestinations);
        gaugePiocheDesti.getChildren().add(stackPanedesti);
        ImageView imageCarte=new ImageView("file:src/main/resources/images/wagons.png");
        imageCarte.setFitHeight(100);
        imageCarte.setFitWidth(150);
        piocheCartesWagons=new Button();
        piocheCartesWagons.setGraphic(imageCarte);
        piocheCartesWagons.setStyle("-fx-background-color: transparent;");
        piocheCartesWagons.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                jeu.uneCarteWagonAEtePiochee();
                slider2.setValue(slider2.getValue()-1);
                setupcropcarte();
                ImageView imageBoutonChangement = new ImageView("images/miscellaneous/changerAffichage.png");
                imageBoutonChangement.setFitWidth(30);
                imageBoutonChangement.setFitHeight(30);
                jCourant.getChangeurAffichage().setGraphic(imageBoutonChangement);
                jCourant.setAfficheLesCartes();
                jCourant.afficherCartesWagon();
            }
        });
        HBox gaugePioche=new HBox();
        gaugePioche.getChildren().add(piocheCartesWagons);
        gaugePioche.getChildren().add(stackPanecarte);
        piocheVisible=new VBox();
        VBox pioches=new VBox();
        pioches.getChildren().add(gaugePiocheDesti);
        pioches.getChildren().add(piocheVisible);
        pioches.getChildren().add(gaugePioche);
        pioches.setPadding(new Insets(30,0,0,0));
        setCenter(plateau);

        BorderPane pane = new BorderPane();
        pane.setCenter(jCourant);
        setBottom(pane);
        setLeft(jAutre);
        setRight(pioches);
    }

    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {
        jCourant.creerBindings();
        jAutre.creerBindings();
        ListChangeListener<ICouleurWagon> changementPiocheVisible=new ListChangeListener<ICouleurWagon>() {
            @Override
            public void onChanged(Change<? extends ICouleurWagon> change) {
                Platform.runLater(() -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            for(int i=0;i<change.getAddedSubList().size();i++){
                                VueCarteWagon carte=new VueCarteWagon(change.getAddedSubList().get(i));
                                carte.getButton().setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        jeu.uneCarteWagonAEteChoisie(carte.getCouleurWagon());
                                        slider2.setValue(slider2.getValue()-1);
                                        setupcropcarte();
                                        ImageView imageBoutonChangement = new ImageView("images/miscellaneous/changerAffichage.png");
                                        imageBoutonChangement.setFitWidth(30);
                                        imageBoutonChangement.setFitHeight(30);
                                        jCourant.getChangeurAffichage().setGraphic(imageBoutonChangement);
                                        jCourant.setAfficheLesCartes();
                                        jCourant.afficherCartesWagon();
                                    }
                                });
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
                                if(piocheVisible.getChildren().size()<5){
                                    piocheVisible.getChildren().add(carte);
                                }
                            }
                        }
                        if(change.wasRemoved()){
                            for(int i=0;i<change.getRemovedSize();i++){
                                if(trouverCarte(change.getRemoved().get(i))!=-1){
                                    piocheVisible.getChildren().remove(trouverCarte(change.getRemoved().get(i)));
                                }
                            }
                        }

                    }
                });
            }
        };
        jeu.cartesWagonVisiblesProperty().addListener(changementPiocheVisible);


    }

    private int trouverCarte(ICouleurWagon c ){
        VueCarteWagon carte=new VueCarteWagon(c);
        for(int i=0;i<piocheVisible.getChildren().size();i++){
            if(carte.estEquals(piocheVisible.getChildren().get(i))){
                return i;
            }
        }
        return -1;
    }

    public double calculerpourcentagetauxdesti(){return 100-((100*slider.getValue())/46);}
    public double calculerpourcentagetauxcarte(){return 100-((100*slider2.getValue())/110);}


    public void setupcropdesti(){
        stackPanedesti.getChildren().remove(rectangledesti);
        rectangledesti.setHeight(calculerpourcentagetauxdesti());
        rectangle2Ddesti = new Rectangle2D(rectangledesti.getX(), rectangledesti.getY(), rectangledesti.getWidth(), rectangledesti.getHeight());
        gaugevidedesti.setViewport(rectangle2Ddesti);
    }
    public void setupcropcarte(){
        stackPanecarte.getChildren().remove(rectanglecarte);
        rectanglecarte.setHeight(calculerpourcentagetauxcarte());
        rectangle2Dcarte = new Rectangle2D(rectanglecarte.getX(),rectanglecarte.getY(),rectanglecarte.getWidth(),rectanglecarte.getHeight());
        gaugevidecarte.setViewport(rectangle2Dcarte);
    }


    public void initGaugeDesti(){
        rectangledesti = new Rectangle(0,0,40,1);
        rectangle2Ddesti = new Rectangle2D(rectangledesti.getX(), rectangledesti.getY(), rectangledesti.getWidth(), rectangledesti.getHeight());
        gaugevidedesti.setViewport(rectangle2Ddesti);
        stackPanedesti.setAlignment(gaugevidedesti, Pos.TOP_CENTER);
        stackPanedesti.getChildren().add(gaugepleindesti);
        stackPanedesti.getChildren().add(gaugevidedesti);
    }

    public void initGaugeCarte(){
        rectanglecarte= new Rectangle(0,0,40,1);
        rectangle2Dcarte = new Rectangle2D(rectanglecarte.getX(),rectanglecarte.getY(),rectanglecarte.getWidth(),rectanglecarte.getHeight());
        gaugevidecarte.setViewport(rectangle2Dcarte);
        stackPanecarte.setAlignment(gaugevidecarte,Pos.TOP_CENTER);
        stackPanecarte.getChildren().add(gaugeremplicarte);
        stackPanecarte.getChildren().add(gaugevidecarte);
    }

    public Slider getSliderDesti() {
        return slider;
    }

    public Slider getSliderCarte() {
        return slider2;
    }

    public VueJoueurCourant getjCourant() {
        return jCourant;
    }
}

