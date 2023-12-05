<!-- hallgatoCVVJZ4.xsl -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <!-- Az összes student elemet kiválasztjuk -->
  <xsl:template match="class">
    <html>
      <body>
        <h2>Diákok</h2>
        <table border="1">
          <tr bgcolor="#9acd32">
            <th>Id</th>
            <th>Vezetéknév</th>
            <th>Keresztnév</th>
            <th>Becenév</th>
            <th>Kor</th>
            <th>Osztondíj</th>
          </tr>
          <!-- Minden student elemre alkalmazzuk a következő template-t -->
          <xsl:apply-templates select="student"/>
        </table>
      </body>
    </html>
  </xsl:template>

  <!-- A student elemekre alkalmazott template -->
  <xsl:template match="student">
    <tr>
      <td><xsl:value-of select="@id"/></td>
      <td><xsl:value-of select="vezeteknev"/></td>
      <td><xsl:value-of select="keresztnev"/></td>
      <td><xsl:value-of select="becenev"/></td>
      <td><xsl:value-of select="kor"/></td>
      <td><xsl:value-of select="osztondij"/></td>
    </tr>
  </xsl:template>

</xsl:stylesheet>
