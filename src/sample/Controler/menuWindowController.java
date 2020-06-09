package sample.Controler;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class menuWindowController {

    @FXML
    private GridPane rootPane;

    @FXML
    private Button button1;

    public void onCloseRequest(){
        Platform.exit();
    }


    @FXML
    public void onStartRequest() throws Exception {
        Stage stage;

        stage = (Stage) button1.getScene().getWindow();

        Scene scene = new Scene(new BorderPane(), 500, 500);
        stage.setScene(scene);
        stage.show();
    }
}
