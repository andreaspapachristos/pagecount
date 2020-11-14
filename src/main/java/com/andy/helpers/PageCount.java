package com.andy.helpers;

import com.itextpdf.text.pdf.PdfReader;
import java.io.IOException;

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
}