package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.rails.Destination;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.Locale;
import java.util.Objects;

/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueDestination extends Button {

    private IDestination destination;

    public VueDestination(IDestination destination) {
        this.destination = destination;
        ImageView image = new ImageView("images/missions/eu"+getDestination(destination)+".png");
        image.setFitHeight(100);
        image.setFitWidth(150);
        setGraphic(image);
        setStyle("-fx-background-color: transparent;");
    }

    private String getDestination(IDestination d){
        int i=0;
        String c="";
        while(d.getNom().charAt(i)!='('){
            if(d.getNom().charAt(i)!=' ' && d.getNom().charAt(i)!='-'){
                c=c+d.getNom().charAt(i);
            }
            i++;
        }
        return c.toLowerCase();
    }



    public IDestination getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VueDestination that = (VueDestination) o;
        return Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination);
    }
}
