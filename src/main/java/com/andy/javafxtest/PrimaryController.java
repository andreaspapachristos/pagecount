package com.andy.javafxtest;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import javafx.fxml.FXML;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javax.xml.parsers.ParserConfigurationException;

public class PrimaryController {

    @FXML
    private CheckBox ckb;

    @FXML
    private void switchToThird() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    private TextArea txt;
    @FXML
    private ProgressIndicator progress;

    @FXML
    private void txtTxt() {

        PrintStream printStream = new PrintStream(new customOutputStream(txt));
        // System.setOut(printStream);
        System.setErr(printStream);

    }

    @FXML
    private TextField tree;

    @FXML
    private void getDir() throws Exception {
        txtTxt();

        var homeDir = System.getProperty("user.home") + System.getProperty("file.separator");
        DirectoryChooser dirchooser = new DirectoryChooser();
        dirchooser.setInitialDirectory(new File(homeDir));

        var ff = dirchooser.showDialog(null);
        if (ff != null) {
            tree.setText(ff.getAbsolutePath().toString());
        }
        Task task = new Task<Void>() {

            public Void call() {

                if (ff != null) {

                    try ( Stream<Path> paths = Files.walk(Paths.get(ff.getAbsolutePath()))) {

                        List<String> pathList = paths
                                .parallel()
                                .filter(Files::isRegularFile)
                                .filter(path -> path.toString().toLowerCase.endsWith(".pdf"))
                                .filter(path -> !path.getFileName().toString().startsWith("."))
                                //.peek(System.out::println)
                                .map(p -> {
                                    if (Files.isDirectory(p)) {

                                        return p.toString() + "/";
                                    }
                                    return p.toString();
                                })
                                .collect(Collectors.toList());
                        Runnable runnable = () -> {
                            try {
                                com.andy.helpers.PageCount.printToXml(pathList, ckb.isSelected());
                            } catch (IOException ex) {
                                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ParserConfigurationException ex) {
                                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        };

                        new Thread(runnable).start();

                        progress.setVisible(true);
                    } catch (IOException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                        System.err.println("no directory choosed!!");
                    }

                }
                return null;

            }
        ;

        };
        progress.visibleProperty().bind(task.runningProperty());
        new Thread(task).start();
    }
}
