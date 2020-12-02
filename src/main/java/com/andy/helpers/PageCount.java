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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PageCount {

    private static final String OS = System.getProperty("os.name");

    /**
     *
     * @param filePath
     * @return
     * @throws java.io.IOException
     */
    public static int efficientPDFPageCount(String filePath) throws IOException {
        try {
            PdfReader reader = new PdfReader(filePath, new byte[0], true);
            int pages = reader.getNumberOfPages();
            reader.close();
            return pages;
        } catch (IOException e) {
            // e.getCause().printStackTrace();
            System.err.println("Possible corrupted file:" + filePath);
        }
        return 0;
    }

    public static void printToXml(List<String> list, Boolean b) throws IOException, ParserConfigurationException {
        try {

            DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = df.newDocumentBuilder();
            Document d = db.newDocument();
            Element r = d.createElement("files");
            d.appendChild(r);
            for (String f : list) {
                Element dir = d.createElement("file");
                r.appendChild(dir);
                Element pdf = d.createElement("name");
                pdf.appendChild(d.createTextNode(f.substring(f.lastIndexOf(System.getProperty("file.separator")) + 1)));
                dir.appendChild(pdf);
                Element name = d.createElement("path");
                if (OS.matches("^Win.*")) {
                    name.appendChild(d.createTextNode("file:\\" + f.substring(0, f.lastIndexOf(System.getProperty("file.separator")) + 1)));
                } else {
                    name.appendChild(d.createTextNode(f.substring(f.indexOf(System.getProperty("file.separator")), f.lastIndexOf(System.getProperty("file.separator")) + 1)));
                }

                dir.appendChild(name);
                Element p = d.createElement("pages");
                p.appendChild(d.createTextNode(Integer.toString(efficientPDFPageCount(f))));
                dir.appendChild(p);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File("src/main/java/main.xsl"));
            Transformer transformer = (b ? transformerFactory.newTransformer() : transformerFactory.newTransformer(xslt));

            DOMSource domSource = new DOMSource(d);
            StreamResult streamResult = new StreamResult(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "test" + (b ? ".xml" : ".html")));
            transformer.transform(domSource, streamResult);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

}
