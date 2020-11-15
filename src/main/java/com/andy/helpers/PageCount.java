package com.andy.helpers;

import com.itextpdf.text.pdf.PdfReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PageCount {
    /**
     *
     * @param file
     * @return
     * @throws Exception
     */
   public static int efficientPDFPageCount(String filePath) throws IOException{
    try{ PdfReader reader = new PdfReader(filePath, new byte[0], true);
     int pages = reader.getNumberOfPages();
     reader.close();
     return pages;
    }catch(IOException e){
       // e.getCause().printStackTrace();
        System.out.println("Possible corrupted file:" + filePath);
    }return 0;
   }
   
   public static void printToXml(List<String> list) throws IOException, ParserConfigurationException{
       try{
           
           DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
           DocumentBuilder db = df.newDocumentBuilder();
           Document d = db.newDocument();
           Element r = d.createElement("files");
           d.appendChild(r);  
           for (String f : list){
                Element dir = d.createElement("file");
                dir.appendChild(d.createTextNode(f.substring(f.lastIndexOf("/")+1)));
                r.appendChild(dir);
                Attr ar = d.createAttribute("path");
                ar.setValue(f.substring(f.indexOf("/"), f.lastIndexOf("/")+1));
                dir.setAttributeNode(ar);
                Element p = d.createElement("info");
                r.appendChild(p);
                Attr attr = d.createAttribute("pages");
                attr.setValue(Integer.toString(efficientPDFPageCount(f)));
                p.setAttributeNode(attr);
          /*  Element pages = d.createElement(Integer.toString(efficientPDFPageCount(f)));
           d.appendChild(pages);*/
                    }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File("/home/master/NetBeansProjects/javaFxTest/src/main/java/main.xsl"));
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(d);
           // Source text = new StreamSource(new File("/home/master/test.xml"));
          //  transformer.transform(text, new StreamResult(new File("/home/master/output.xml")));
            StreamResult streamResult = new StreamResult(new File("/home/master/test.xml"));
            transformer.transform(domSource, streamResult);
            }
         catch (IOException ioe) {
            ioe.printStackTrace();
            } 
       catch (TransformerException tfe) {
            tfe.printStackTrace();
            }  
}    
   
    

         
}
