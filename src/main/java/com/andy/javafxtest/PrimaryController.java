package com.andy.javafxtest;

import com.andy.helpers.PageCount;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.stage.DirectoryChooser;

public class PrimaryController {
    
    

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    private void getDir() throws IOException, Exception{
        
        
        var homeDir = System.getProperty("user.home")+ System.getProperty("file.separator") + "Downloads" ;
       //Δευερη λύση, προσπαθεί αναδρομικά να κάνει το ίδιο πράγμα, αλλά υπάρχει πρόβλημα με τους υποκαταλόγους//
     // System.setProperty("org.apache.pdfbox.baseParser.pushBackSize", "999999999");
     // System.out.println(System.getProperty("org.apache.pdfbox.baseParser.pushBackSize"));
       DirectoryChooser dirchooser = new DirectoryChooser();
       dirchooser.setInitialDirectory(new File (homeDir));
       var ff = dirchooser.showDialog(null);
       int i = 1;
      if ( ff != null){
          try(Stream<Path> paths = Files.walk(Paths.get(ff.getAbsolutePath()))){
                        List<String> pathList = paths
                                                .filter(Files::isRegularFile)
                                                .filter(path -> path.toString().endsWith(".pdf"))
                                                //.peek(System.out::println)
                                                .map(p ->{
                                                if(Files.isDirectory(p)){
                                                    
                                                   
                                                    return p.toString()+ "/";
                                                }
                                                return p.toString();
                                                 })
                                                .collect(Collectors.toList());
              
                for (String f:pathList){
                    System.out.printf("%-4d"+ "%-120s" +"%6d"+"\n",i++, f, PageCount.efficientPDFPageCount(f));
                  // System.out.println(PageCount.efficientPDFPageCount(f));
                }
               
            }    
        }
    }  
}
        
    

