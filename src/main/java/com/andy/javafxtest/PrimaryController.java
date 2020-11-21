package com.andy.javafxtest;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.control.CheckBox;
import javafx.stage.DirectoryChooser;

public class PrimaryController {
    @FXML
    private CheckBox ckb;
            
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void getDir() throws Exception {
        //private int i = 1;
        //System.out.println(this.ckb.isSelected());
        var homeDir = System.getProperty("user.home") + System.getProperty("file.separator");
        DirectoryChooser dirchooser = new DirectoryChooser();
        dirchooser.setInitialDirectory(new File(homeDir));
        var ff = dirchooser.showDialog(null);

        if (ff != null) {
            try (Stream<Path> paths = Files.walk(Paths.get(ff.getAbsolutePath()))) {

                List<String> pathList = paths
                        .parallel()
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".pdf"))
                        //.peek(System.out::println)
                        .map(p -> {
                            if (Files.isDirectory(p)) {
                                return p.toString() + "/";
                            }
                            return p.toString();
                        })
                        .collect(Collectors.toList());
                com.andy.helpers.PageCount.printToXml(pathList, ckb.isSelected());
                /*  for (String f:pathList){
                    System.out.printf("%s"+")"+"%s" +"%d"+"\n",i++, f, PageCount.efficientPDFPageCount(f));
                  // System.out.println(PageCount.efficientPDFPageCount(f));
                }*///paths.parallel().forEach(p-> System.out.println("Thread : " + Thread.currentThread().getName() + ", value: " + p));

            }
        }

    }
}
