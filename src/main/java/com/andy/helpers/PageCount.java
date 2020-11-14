package com.andy.helpers;

import com.itextpdf.text.pdf.PdfReader;
import java.io.IOException;

public class PageCount {

    /**
     * @param file
     * @return
     * @throws Exception
     */

        public static int process (String filePath) throws IOException {
            PdfReader reader = new PdfReader(filePath, new byte[0], true);
            int pages = reader.getNumberOfPages();
            reader.close();
            return pages;
        }
    }
