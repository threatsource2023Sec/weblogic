<?xml version="1.0" encoding="UTF-8" ?>
<!--

    Copyright (c) 2003, 2018 Oracle and/or its affiliates. All rights reserved.

    This program and the accompanying materials are made available under the
    terms of the Eclipse Distribution License v. 1.0, which is available at
    http://www.eclipse.org/org/documents/edl-v10.php.

    SPDX-License-Identifier: BSD-3-Clause

-->

<!--
    Document   : function.html.xsl
    Created on : Februrary 25, 2003, 7:39 PM
    Author     : mroth
    Description:
        Creates the function detail page (right frame), listing the known
        information for a given function in a tag library.
-->

<xsl:stylesheet version="1.0"
    xmlns:javaee="http://java.sun.com/xml/ns/javaee" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format">
    
    <xsl:output method="html" indent="yes"/>
    
    <xsl:param name="tlddoc-shortName">default</xsl:param>
    <xsl:param name="tlddoc-functionName">default</xsl:param>

    <!-- template rule matching source root element -->
    <xsl:template match="/">
      <xsl:apply-templates select="javaee:tlds/javaee:taglib"/>
    </xsl:template>
    
    <xsl:template match="javaee:taglib">
      <xsl:if test="javaee:short-name=$tlddoc-shortName">
        <xsl:apply-templates select="javaee:function"/>
      </xsl:if>
    </xsl:template>
    
    <xsl:template match="javaee:function">
      <xsl:if test="javaee:name=$tlddoc-functionName">
        <xsl:variable name="tldname">
          <xsl:choose>
            <xsl:when test="../javaee:display-name!=''">
              <xsl:value-of select="../javaee:display-name"/>
            </xsl:when>
            <xsl:when test="../javaee:short-name!=''">
              <xsl:value-of select="../javaee:short-name"/>
            </xsl:when>
            <xsl:otherwise>
              Unnamed TLD
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>
        <xsl:variable name="title">
          <xsl:value-of select="javaee:name"/>
          (<xsl:value-of select="/javaee:tlds/javaee:config/javaee:window-title"/>)
        </xsl:variable>
        <html>
          <head>
            <title><xsl:value-of select="$title"/></title>
            <meta name="keywords" content="$title"/>
            <link rel="stylesheet" type="text/css" href="../stylesheet.css" 
                  title="Style"/>
          </head>
          <script>
            function asd()
            {
            parent.document.title="<xsl:value-of select="normalize-space($title)"/>";
            }
          </script>
          <body bgcolor="white" onload="asd();">
            <!-- =========== START OF NAVBAR =========== -->
            <a name="navbar_top"><!-- --></a>
            <table border="0" width="100%" cellpadding="1" cellspacing="0">
            <tr>
              <td COLSPAN="3" BGCOLOR="#EEEEFF" CLASS="NavBarCell1">
                <a NAME="navbar_top_firstrow"><!-- --></a>
                <table BORDER="0" CELLPADDING="0" CELLSPACING="3">
                  <tr ALIGN="center" VALIGN="top">
                    <td BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    &#160;<a href="../overview-summary.html"><font CLASS="NavBarFont1"><b>Overview</b></font></a>&#160;</td>
                    <td BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    &#160;<a href="tld-summary.html"><font CLASS="NavBarFont1"><b>Library</b></font></a>&#160;</td>
                    <td BGCOLOR="#FFFFFF" CLASS="NavBarCell1Rev"> &#160;<font CLASS="NavBarFont1Rev">&#160;Tag&#160;</font>&#160;</td>
                    <td BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    &#160;<a HREF="../help-doc.html"><font CLASS="NavBarFont1"><b>Help</b></font></a>&#160;</td>
                  </tr>
                </table>
              </td>
              <td ALIGN="right" VALIGN="top" ROWSPAN="3"><em>
                </em>
              </td>
            </tr>
            <tr>
              <td BGCOLOR="white" CLASS="NavBarCell2"><font SIZE="-2">
                <!--&#160;PREV TAGLIB&#160;-->
                <!--&#160;NEXT TAGLIB&#160;-->
              </font></td>
              <td BGCOLOR="white" CLASS="NavBarCell2"><font SIZE="-2">
                &#160;<a HREF="../index.html" TARGET="_top"><b>FRAMES</b></a>&#160;
                &#160;<xsl:element name="a">
                  <xsl:attribute name="href"><xsl:value-of select="javaee:name"/>.fn.html</xsl:attribute>
                  <xsl:attribute name="target">_top</xsl:attribute>
                  <b>NO FRAMES</b>
                </xsl:element>&#160;
                <script>
                  <!--
                  if(window==top) {
                    document.writeln('<A HREF="alltags-noframe.html" TARGET=""><B>All Tags</B></A>');
                  }
                  //-->
                </script>
                <noscript>
                  <a HREF="../alltags-noframe.html" TARGET=""><b>All Tags</b></a>
                </noscript>
              </font></td>
            </tr>
            </table>
            <!-- =========== END OF NAVBAR =========== -->
            
            <hr/>
            <h2><font size="-1"><xsl:value-of select="$tldname"/></font><br/>
            Function <xsl:value-of select="javaee:name"/></h2>
            <code>
              <xsl:value-of select='substring-before(normalize-space(javaee:function-signature)," ")'/>
              <b>&#160;<xsl:value-of select="javaee:name"/></b>(<xsl:value-of 
              select='substring-after(normalize-space(javaee:function-signature),"(")'/>
            </code>
            <hr/>
            <xsl:value-of select="javaee:description" disable-output-escaping="yes"/><br/>
            <p/>
            <xsl:if test="javaee:example!=''">
              <b>Example:</b><br/>
              <pre>
<xsl:value-of select="javaee:example"/>              
              </pre>
              <p/>
            </xsl:if>
            <hr/>
            
            <!-- Function Information -->
            <table border="1" cellpadding="3" cellspacing="0" width="100%">
              <tr bgcolor="#CCCCFF" class="TableHeadingColor">
                <td colspan="2">
                  <font size="+2">
                    <b>Function Information</b>
                  </font>
                </td>
              </tr>
              <tr>
                <td>Function Class</td>
                <td>
                  <xsl:choose>
                    <xsl:when test="javaee:function-class!=''">
                      <xsl:value-of select="javaee:function-class"/>
                    </xsl:when>
                    <xsl:otherwise>
                      <i>None</i>
                    </xsl:otherwise>
                  </xsl:choose>
                </td>
              </tr>
              <tr>
                <td>Function Signature</td>
                <td>
                  <xsl:choose>
                    <xsl:when test="javaee:function-signature!=''">
                      <xsl:value-of select="javaee:function-signature"/>
                    </xsl:when>
                    <xsl:otherwise>
                      <i>None</i>
                    </xsl:otherwise>
                  </xsl:choose>
                </td>
              </tr>
              <tr>
                <td>Display Name</td>
                <td>
                  <xsl:choose>
                    <xsl:when test="javaee:display-name!=''">
                      <xsl:value-of select="javaee:display-name"/>
                    </xsl:when>
                    <xsl:otherwise>
                      <i>None</i>
                    </xsl:otherwise>
                  </xsl:choose>
                </td>
              </tr>
            </table>
            <br/>
            <p/>
            
            <!-- =========== START OF NAVBAR =========== -->
            <a name="navbar_bottom"><!-- --></a>
            <table border="0" width="100%" cellpadding="1" cellspacing="0">
            <tr>
              <td COLSPAN="3" BGCOLOR="#EEEEFF" CLASS="NavBarCell1">
                <a NAME="navbar_bottom_firstrow"><!-- --></a>
                <table BORDER="0" CELLPADDING="0" CELLSPACING="3">
                  <tr ALIGN="center" VALIGN="top">
                    <td BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    &#160;<a href="../overview-summary.html"><font CLASS="NavBarFont1"><b>Overview</b></font></a>&#160;</td>
                    <td BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    &#160;<a href="tld-summary.html"><font CLASS="NavBarFont1"><b>Library</b></font></a>&#160;</td>
                    <td BGCOLOR="#FFFFFF" CLASS="NavBarCell1Rev"> &#160;<font CLASS="NavBarFont1Rev">&#160;Tag&#160;</font>&#160;</td>
                    <td BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    &#160;<a HREF="../help-doc.html"><font CLASS="NavBarFont1"><b>Help</b></font></a>&#160;</td>
                  </tr>
                </table>
              </td>
              <td ALIGN="right" VALIGN="top" ROWSPAN="3"><em>
                </em>
              </td>
            </tr>
            <tr>
              <td BGCOLOR="white" CLASS="NavBarCell2"><font SIZE="-2">
                <!--&#160;PREV TAGLIB&#160;-->
                <!--&#160;NEXT TAGLIB&#160;-->
              </font></td>
              <td BGCOLOR="white" CLASS="NavBarCell2"><font SIZE="-2">
                &#160;<a HREF="../index.html" TARGET="_top"><b>FRAMES</b></a>&#160;
                &#160;<xsl:element name="a">
                  <xsl:attribute name="href"><xsl:value-of select="javaee:name"/>.fn.html</xsl:attribute>
                  <xsl:attribute name="target">_top</xsl:attribute>
                  <b>NO FRAMES</b>
                </xsl:element>&#160;
                <script>
                  <!--
                  if(window==top) {
                    document.writeln('<A HREF="alltags-noframe.html" TARGET=""><B>All Tags</B></A>');
                  }
                  //-->
                </script>
                <noscript>
                  <a HREF="../alltags-noframe.html" TARGET=""><b>All Tags</b></a>
                </noscript>
              </font></td>
            </tr>
            </table>
            <!-- =========== END OF NAVBAR =========== -->
            <hr/>            
            <small><i>
            Output Generated by 
            <a href="http://taglibrarydoc.dev.java.net/" target="_blank">Tag Library Documentation Generator</a>.
            </i></small>
          </body>
        </html>
      </xsl:if>
    </xsl:template>

</xsl:stylesheet> 
