package com.andy.javafxtest;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
}