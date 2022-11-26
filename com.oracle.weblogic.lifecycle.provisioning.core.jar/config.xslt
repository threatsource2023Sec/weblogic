<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <!--
      By default copy all nodes and attributes from the source to the target.
  -->
  <xsl:template match="@* | child::node()">
    <xsl:copy>
      <xsl:apply-templates select="@* | child::node()"/>
    </xsl:copy>
  </xsl:template>

  <!--
      Replace empty <partition><resource-group><target> elements with
      the values of appropriate configurable attributes.
  -->
  <xsl:template match="*[local-name() = 'partition']/*[local-name() = 'resource-group']/*[local-name() = 'target' and count(text()) = 0]">
    <xsl:copy>
      <xsl:apply-templates select="@*"/>
      <xsl:value-of select="document(concat('config:resource-group.', ../name, '.target'))"/>
      <xsl:apply-templates select="child::node()"/>
    </xsl:copy>
  </xsl:template>  
  
  <!--
      Replace empty <partition><jdbc-system-resource-override><url> elements with
      the values of appropriate configurable attributes.
  -->
  <xsl:template match="//*[local-name() = 'partition']/*[local-name() = 'jdbc-system-resource-override']/*[local-name() = 'url' and count(text()) = 0]">
    <xsl:copy>
      <xsl:apply-templates select="@*"/>
      <xsl:value-of select="document(concat('config:jdbc-data-source.', ../data-source-name, '.url'))"/>
      <xsl:apply-templates select="child::node()"/>
    </xsl:copy>
  </xsl:template>

  <!--
      Replace empty <partition><jdbc-system-resource-override><user> elements with
      the values of appropriate configurable attributes.
  -->
  <xsl:template match="//*[local-name() = 'partition']/*[local-name() = 'jdbc-system-resource-override']/*[local-name() = 'user' and count(text()) = 0]">
    <xsl:copy>
      <xsl:apply-templates select="@*"/>
      <xsl:value-of select="document(concat('config:jdbc-data-source.', ../data-source-name, '.user'))"/>
      <xsl:apply-templates select="child::node()"/>
    </xsl:copy>
  </xsl:template>

  <!--
      Replace empty <partition><jdbc-system-resource-override><password> elements with
      the values of appropriate configurable attributes.
  -->
  <xsl:template match="//*[local-name() = 'partition']/*[local-name() = 'jdbc-system-resource-override']/*[local-name() = 'password' and count(text()) = 0]">
    <xsl:copy>
      <xsl:apply-templates select="@*"/>
      <xsl:value-of select="document(concat('config:jdbc-data-source.', ../data-source-name, '.password'))"/>
      <xsl:apply-templates select="child::node()"/>
    </xsl:copy>
  </xsl:template>

</xsl:stylesheet>
