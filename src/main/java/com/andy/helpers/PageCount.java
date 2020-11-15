package com.andy.helpers;

import com.itextpdf.text.pdf.PdfReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
        e.getCause().printStackTrace();
    }return 0;
   }
   
   public static void printToXml(List<String> list) throws IOException{
       try{
           DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
           DocumentBuilder db = df.newDocumentBuilder();
           Document d = db.newDocument();
           Element r = d.createElement("files");
           d.appendChild(r);  
           for (String f : list){
                Element dir = d.createElement("directory");
                dir.appendChild(d.createTextNode(f));
                r.appendChild(dir);
                Element p = d.createElement("info");
                r.appendChild(p);
                Attr attr = d.createAttribute("pages");
                attr.setValue(Integer.toString(efficientPDFPageCount(f)));
                p.setAttributeNode(attr);
          /*  Element pages = d.createElement(Integer.toString(efficientPDFPageCount(f)));
           d.appendChild(pages);*/
                    }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(d);
            StreamResult streamResult = new StreamResult(new File("/home/master/test.xml"));
            transformer.transform(domSource, streamResult);
            }
         catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            } 
       catch (TransformerException tfe) {
            tfe.printStackTrace();
            }  
}    
   
    

         
}
