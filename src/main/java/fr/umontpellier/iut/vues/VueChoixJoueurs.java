package fr.umontpellier.iut.vues;


import fr.umontpellier.iut.RailsIHM;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;




/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 *
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends Stage {

    private ObservableList<String> nomsJoueurs;

    private ObservableList<String>TEMPnomsJoueurs;

    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }


    private StackPane stackpane;
    private StackPane stackpanelabel;
    private StackPane numberOfPlayersChoice;
    private ImageView logo = new ImageView(new Image("images/miscellaneous/titletickettoride.png"));
    private ImageView logo2 = new ImageView(new Image("images/miscellaneous/titletickettoride.png"));
    private ImageView bgimage = new ImageView(new Image("images/miscellaneous/dailychallenge-ticket.png"));
    private ImageView numberimage = new ImageView(new Image("images/miscellaneous/buttonintroGreenIddle.png"));

    private VBox vbox;
    private VBox vbox2;
    private Button quitter;
    private Button retour;
    private Button retour2;
    private Button règles;
    private Button règles2;
    private Button règles3;
    private Button PlayButton;
    private File pdfregles;
    private int nbjoueurs;
    private Button ConfirmButton;

    private HBox buttonsNumber;
    private VBox buttonsvbox;
    private Button up;
    private Button down;


    private Label label;
    private Label number;

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    public VueChoixJoueurs() {
        pdfregles = new File("src/main/resources/LesAventuriersduRail-Règles.pdf");
        logo.setPreserveRatio(true);
        logo2.setPreserveRatio(true);
        logo.setFitHeight(450);
        logo2.setFitHeight(450);
        quitter = new Button("");
        quitter.setGraphic(new ImageView(new Image("images/miscellaneous/buttonquitOver.png")));
        quitter.setStyle("-fx-background-color: transparent;-fx-scale-y: 1;-fx-scale-x: 1;-fx-padding: 20");

        ConfirmButton = new Button("");
        ConfirmButton.setGraphic(new ImageView(new Image("images/miscellaneous/button-confirm.png")));
        ConfirmButton.setStyle("-fx-background-color: transparent;-fx-scale-y: 0.5;-fx-scale-x: 0.5;-fx-padding: -20");

        règles = new Button("");
        règles.setGraphic(new ImageView(new Image("images/miscellaneous/see-rules.png")));
        règles.setStyle("-fx-background-color: transparent;-fx-scale-x: 1.0;-fx-scale-y: 1.0;-fx-padding: 10");

        PlayButton = new Button("");
        PlayButton.setGraphic(new ImageView(new Image("images/miscellaneous/playButton.png")));
        PlayButton.setStyle("-fx-background-color: transparent;-fx-scale-x: 1.5;-fx-scale-y: 1.5;");

        retour = new Button("");
        retour.setGraphic(new ImageView(new Image("images/miscellaneous/buttonquitOver.png")));
        retour.setStyle("-fx-background-color: transparent;-fx-scale-y: 1;-fx-scale-x: 1;-fx-padding: 20");

        retour2 = new Button("");
        retour2.setGraphic(new ImageView(new Image("images/miscellaneous/buttonquitOver.png")));
        retour2.setStyle("-fx-background-color: transparent;-fx-scale-y: 1;-fx-scale-x: 1;-fx-padding: 20");

        règles2 = new Button("");
        règles2.setGraphic(new ImageView(new Image("images/miscellaneous/see-rules.png")));
        règles2.setStyle("-fx-background-color: transparent;-fx-scale-x: 1.0;-fx-scale-y: 1.0;-fx-padding: 10");

        règles3 = new Button("");
        règles3.setGraphic(new ImageView(new Image("images/miscellaneous/see-rules.png")));
        règles3.setStyle("-fx-background-color: transparent;-fx-scale-x: 1.0;-fx-scale-y: 1.0;-fx-padding: 10");

        up = new Button("");
        up.setGraphic(new ImageView(new Image("images/miscellaneous/buttondeploy-up.png")));
        up.setStyle("-fx-background-color: transparent;-fx-scale-x: 1.0;-fx-scale-y: 1.0;-fx-padding: 10");

        down = new Button("");
        down.setGraphic(new ImageView(new Image("images/miscellaneous/buttondeploy-down.png")));
        down.setStyle("-fx-background-color: transparent;-fx-scale-x: 1.0;-fx-scale-y: 1.0;-fx-padding: 10");

        Stage stage = this;
        stackpane = new StackPane();


        vbox = new VBox();


        vbox.getChildren().add(logo);
        vbox.getChildren().add(PlayButton);
        stackpane.getChildren().add(vbox);
        stackpane.getChildren().add(quitter);
        stackpane.getChildren().add(règles);

        Scene scene = new Scene(stackpane);


        stackpane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight() - 70);


        stackpane.setStyle("-fx-background-image: url('images/miscellaneous/williamTurner.png');-fx-background-size: cover;");


        vbox.setAlignment(Pos.CENTER);
        StackPane.setAlignment(quitter, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(règles, Pos.TOP_RIGHT);


        //2nd scene
        StackPane stackpane2 = new StackPane();

        Scene scene2 = new Scene(stackpane2);

        stackpanelabel = new StackPane();
        stackpanelabel.getChildren().add(bgimage);
        bgimage.setPreserveRatio(true);
        bgimage.setFitHeight(200);

        Font f=Font.loadFont("file:src/main/resources/fonts/IMFeENsc28P.ttf",25);
        label = new Label("Choississez le nombre de joueurs");
        label.setFont(f);
        stackpanelabel.getChildren().add(label);

        numberOfPlayersChoice = new StackPane();
        numberOfPlayersChoice.getChildren().add(numberimage);
        numberimage.setPreserveRatio(true);
        numberimage.setFitHeight(140);
        nbjoueurs = 4;
        Font f2=Font.loadFont("file:src/main/resources/fonts/IMFeENsc28P.ttf",60);
        number = new Label(nbjoueurs + "");
        number.setFont(f2);

        numberOfPlayersChoice.getChildren().add(number);
        number.setAlignment(Pos.CENTER);

        buttonsvbox = new VBox();
        buttonsNumber = new HBox();

        buttonsvbox.getChildren().add(up);
        up.setOnAction(eventup -> {
            nbjoueurs++;
            number.setText(nbjoueurs + "");
        });

        buttonsvbox.getChildren().add(down);
        down.setOnAction(eventdown -> {
            nbjoueurs--;
            number.setText(nbjoueurs + "");
        });
        buttonsNumber.getChildren().add(numberOfPlayersChoice);
        buttonsNumber.getChildren().add(buttonsvbox);

        buttonsNumber.setAlignment(Pos.CENTER);


        vbox2 = new VBox();

        vbox2.getChildren().add(stackpanelabel);
        vbox2.getChildren().add(buttonsNumber);
        vbox2.getChildren().add(ConfirmButton);
        // buttonsvbox.setAlignment(Pos.CENTER);

        stackpane2.getChildren().add(vbox2);
        stackpane2.getChildren().add(retour);
        stackpane2.getChildren().add(règles2);


        vbox2.setAlignment(Pos.CENTER);
        StackPane.setAlignment(retour, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(règles2, Pos.TOP_RIGHT);


        stackpane2.setStyle("-fx-background-image: url('images/miscellaneous/williamTurner.png');-fx-background-size: cover;");
        stackpane2.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight() - 70);


        //scene 3
        StackPane stackpane3 = new StackPane();
        Scene scene3 = new Scene(stackpane3);
        stackpane3.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight() - 70);
        stackpane3.setStyle("-fx-background-image: url('images/miscellaneous/williamTurner.png');-fx-background-size: cover;");
        VBox vbox3 = new VBox();
        stackpane3.getChildren().add(vbox3);


        PlayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(scene2);
            }
        });


        EventHandler clickOnRegles = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File f=new File("src/main/resources/images/miscellaneous/regles.pdf");
                RailsIHM.hostServices().showDocument(f.toURI().toString());
            }
        };
        règles.setOnAction(clickOnRegles);
        règles2.setOnAction(clickOnRegles);
        règles3.setOnAction(clickOnRegles);


        EventHandler action = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }

        };
        quitter.setOnAction(action);

        EventHandler retourner = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(scene);
            }

        };
        retour.setOnAction(retourner);

        EventHandler retourneravant = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(scene2);
            }
        };
        retour2.setOnAction(retourneravant);

        EventHandler selectJoueur = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(scene3);
                créerperso(nbjoueurs, vbox3,0);
            }
        };
        ConfirmButton.setOnAction(selectJoueur);


        createBindings();

        nomsJoueurs = FXCollections.observableArrayList();
        TEMPnomsJoueurs = FXCollections.observableArrayList();

        stage.setScene(scene);
        stage.setTitle("Rails");
        stage.show();
    }

    public void createBindings() {
        number.textProperty().addListener((observable, oldValue, newValue) -> {
            up.setDisable(newValue.equals("5"));
            down.setDisable(newValue.equals("2"));
        });


    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {
        nomsJoueurs.addListener(quandLesNomsDesJoueursSontDefinis);
    }






    /**
     * Définit l'action à exécuter lorsque le nombre de participants change
     */
  /*  protected void setChangementDuNombreDeJoueursListener(ChangeListener<Integer> quandLeNombreDeJoueursChange) {



    }*/

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs(); i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            } else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        return TEMPnomsJoueurs.size();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     *
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        return TEMPnomsJoueurs.get(playerNumber-1);
    }

    public void créerperso(int i, VBox conteneur,int nbstart) {

        HBox hbox = new HBox();
        conteneur.getChildren().clear();
        Font f=Font.loadFont("file:src/main/resources/fonts/IMFeENsc28P.ttf",45);
        Label label = new Label("joueur n°" + (nbstart+1));
        label.setFont(f);
        label.setStyle("-fx-font-weight: bold");
        TextField textField = new TextField("");
        textField.setStyle("-fx-background-color: #f5f5f5;");
        textField.setMaxWidth(150);
        hbox.getChildren().add(textField);
        conteneur.getChildren().add(label);
        conteneur.getChildren().add(hbox);
        hbox.setAlignment(Pos.CENTER);
        conteneur.setAlignment(Pos.CENTER);
        if (!(nbstart+1 == nbjoueurs)) {
            Button button = new Button("");
            button.setGraphic(new ImageView(new Image("images/miscellaneous/plus.png")));
            button.setStyle("-fx-background-color: transparent;-fx-scale-x: 1.0;-fx-scale-y: 1.0;-fx-padding: 10");
            button.setOnAction(actionvevent -> {
                créerperso(i+1, conteneur,nbstart+1);
                TEMPnomsJoueurs.add(textField.getText());
            });
            textField.setOnAction(actionEvent -> {
                créerperso(i+1, conteneur,nbstart+1);
                TEMPnomsJoueurs.add(textField.getText());
            });
            hbox.getChildren().add(button);
        }
        else{
            Button button = new Button("");
            button.setGraphic(new ImageView(new Image("images/miscellaneous/button-validate.png")));
            button.setStyle("-fx-background-color: transparent;-fx-scale-x: 0.5;-fx-scale-y: 0.5;-fx-padding: 5");
            button.setOnAction(actionvevent -> {
                TEMPnomsJoueurs.add(textField.getText());
                setListeDesNomsDeJoueurs();
            });
            textField.setOnAction(actionEvent -> {
                TEMPnomsJoueurs.add(textField.getText());
                setListeDesNomsDeJoueurs();
            });
            hbox.getChildren().add(button);
        }
    }
}
