<?xml version="1.0" encoding="UTF-8"?>
 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:xs="http://www.w3.org/2001/XMLSchema" version="1.0">

    <xsl:output indent="yes" version="1.0" method="xml" encoding="UTF-8"/>

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="* | */@*">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="xs:annotation/xs:documentation">
        <xs:documentation>
            <xsl:choose>
                <xsl:when test="starts-with(.,'&lt;p>')">
                    <xsl:choose>
                    <xsl:when test="contains(.,'&lt;/p&gt;')">                       
                        <xsl:value-of select="substring-before(substring-after(.,'&lt;p>'),'&lt;/p&gt;')"/>
                    </xsl:when>
                    <xsl:otherwise>
                         <xsl:copy-of select="text()"/>
                    </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:copy-of select="text()"/>
                </xsl:otherwise>
            </xsl:choose>
        </xs:documentation>
    </xsl:template>

    
</xsl:stylesheet>
