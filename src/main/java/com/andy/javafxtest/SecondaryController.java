package com.andy.javafxtest;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private TextArea text;
    
    public void initialize(URL url, ResourceBundle resources) {
        text.setText("To utility είναι γραμμένο σε java \n"
                + "και έχει σαν runtime dependencies τα: \n"
                + "jre-15 και javaFx-sdk-15");

    }

}
