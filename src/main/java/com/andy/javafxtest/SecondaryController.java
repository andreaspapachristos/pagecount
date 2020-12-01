package com.andy.javafxtest;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txt;

    @FXML
    private Button secondaryButton;

    @FXML
    void initialize() {
        assert txt != null : "fx:id=\"txt\" was not injected: check your FXML file 'secondary.fxml'.";
        assert secondaryButton != null : "fx:id=\"secondaryButton\" was not injected: check your FXML file 'secondary.fxml'.";

    }
}
