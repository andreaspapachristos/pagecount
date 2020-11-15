<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : main.xsl
    Created on : November 15, 2020, 9:29 AM
    Author     : master
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            
            <body>
                <table border="1">
                    <tr bgcolor="#9acd32">
                      <th style="text-align:left">File</th>
                      <th style="text-align:left">Pages</th>
                    </tr>
                    <xsl:for-each select="files/directory">
                    <tr>
                      <td><xsl:value-of select="files/directory"/></td>
                      <td><xsl:value-of select="files/info/@pages"/></td>
                    </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
