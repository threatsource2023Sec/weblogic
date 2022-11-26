<?xml version="1.0" encoding="UTF-8" ?>
<!--

    Copyright (c) 2003, 2018 Oracle and/or its affiliates. All rights reserved.

    This program and the accompanying materials are made available under the
    terms of the Eclipse Distribution License v. 1.0, which is available at
    http://www.eclipse.org/org/documents/edl-v10.php.

    SPDX-License-Identifier: BSD-3-Clause

-->

<!--

  Translates a JSP 1.1 TLD into a JSP 1.2 TLD, using the following 
  conversion rules:

  1. Change the document type definition for the TLD to:
     <!DOCTYPE taglib
         PUBLIC "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN"
	 "http://java.sun.com/dtd/web-jsptaglibrary_1_2.dtd">
  2. Change the <tlibversion> element to <tlib-version>
  3. Change the optional <jspversion> element to <jsp-version>.  If no
     element exists, supply one that's set to 1.2
  4. Change the <shortname> element to <short-name>
  5. Preserve the original optional <uri> tag
  6. Change the optional <info> tag to <description>
  7. Preserve each tag/name
  8. Change each tag/tagclass to tag/tag-class
  9. Change each optional tag/teiclass to tag/tei-class
  10. Change each optional tag/bodycontent to tag/body-content
  11. Change each optional tag/info to tag/description
  12. Preserve each tag/attribute element and its contents.

  Side-effect: Strips the id attributes

  Author: Mark Roth

-->

<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"
      doctype-system="http://java.sun.com/dtd/web-jsptaglibrary_1_2.dtd"
      doctype-public="-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN"/>

  <xsl:template match="/taglib">
    <taglib>
      <xsl:apply-templates select="tlibversion"/>
      <jsp-version>1.2</jsp-version>
      <xsl:apply-templates select="shortname"/>
      <xsl:apply-templates select="uri"/>
      <xsl:apply-templates select="info"/>
      <xsl:apply-templates select="tag"/>
    </taglib>
  </xsl:template>

  <!-- Rename to tlib-version -->
  <xsl:template match="tlibversion">
    <tlib-version><xsl:apply-templates/></tlib-version>
  </xsl:template>

  <!-- Rename to jsp-version -->
  <xsl:template match="jspversion">
    <jsp-version><xsl:apply-templates/></jsp-version>
  </xsl:template>

  <!-- Rename to short-name -->
  <xsl:template match="shortname">
    <short-name><xsl:apply-templates/></short-name>
  </xsl:template>

  <!-- Preserve uri -->
  <xsl:template match="uri">
    <uri><xsl:apply-templates/></uri>
  </xsl:template>

  <!-- Rename to description -->
  <xsl:template match="info">
    <description><xsl:apply-templates/></description>
  </xsl:template>

  <xsl:template match="tag">
    <tag>
      <xsl:apply-templates select="name"/>
      <xsl:apply-templates select="tagclass"/>
      <xsl:apply-templates select="teiclass"/>
      <xsl:apply-templates select="bodycontent"/>
      <xsl:apply-templates select="info"/>
      <xsl:apply-templates select="attribute"/>
    </tag>
  </xsl:template>

  <!-- Preserve name -->
  <xsl:template match="name">
    <name><xsl:apply-templates/></name>
  </xsl:template>

  <!-- Rename to tag-class -->
  <xsl:template match="tagclass">
    <tag-class><xsl:apply-templates/></tag-class>
  </xsl:template>

  <!-- Rename to tei-class -->
  <xsl:template match="teiclass">
    <tei-class><xsl:apply-templates/></tei-class>
  </xsl:template>

  <!-- Rename to body-content -->
  <xsl:template match="bodycontent">
    <body-content><xsl:apply-templates/></body-content>
  </xsl:template>

  <!-- Rename to description -->
  <xsl:template match="info">
    <description><xsl:apply-templates/></description>
  </xsl:template>

  <!-- Preserve attribute -->
  <xsl:template match="attribute">
    <attribute> 
      <xsl:apply-templates/>
    </attribute>
  </xsl:template>

  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>

</xsl:stylesheet>
