package sample;

import javafx.scene.control.Alert;

public class NotEnoughArgumentsException extends Exception {
    public NotEnoughArgumentsException(){
        super();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Argument Error");
        alert.setContentText("Fill all fields with a value!");

        alert.showAndWait();
    }
}
