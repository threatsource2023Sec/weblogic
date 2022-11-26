package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface JspPropertyGroupType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JspPropertyGroupType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("jsppropertygrouptype2c7atype");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   DisplayNameType[] getDisplayNameArray();

   DisplayNameType getDisplayNameArray(int var1);

   int sizeOfDisplayNameArray();

   void setDisplayNameArray(DisplayNameType[] var1);

   void setDisplayNameArray(int var1, DisplayNameType var2);

   DisplayNameType insertNewDisplayName(int var1);

   DisplayNameType addNewDisplayName();

   void removeDisplayName(int var1);

   IconType[] getIconArray();

   IconType getIconArray(int var1);

   int sizeOfIconArray();

   void setIconArray(IconType[] var1);

   void setIconArray(int var1, IconType var2);

   IconType insertNewIcon(int var1);

   IconType addNewIcon();

   void removeIcon(int var1);

   UrlPatternType[] getUrlPatternArray();

   UrlPatternType getUrlPatternArray(int var1);

   int sizeOfUrlPatternArray();

   void setUrlPatternArray(UrlPatternType[] var1);

   void setUrlPatternArray(int var1, UrlPatternType var2);

   UrlPatternType insertNewUrlPattern(int var1);

   UrlPatternType addNewUrlPattern();

   void removeUrlPattern(int var1);

   TrueFalseType getElIgnored();

   boolean isSetElIgnored();

   void setElIgnored(TrueFalseType var1);

   TrueFalseType addNewElIgnored();

   void unsetElIgnored();

   String getPageEncoding();

   boolean isSetPageEncoding();

   void setPageEncoding(String var1);

   String addNewPageEncoding();

   void unsetPageEncoding();

   TrueFalseType getScriptingInvalid();

   boolean isSetScriptingInvalid();

   void setScriptingInvalid(TrueFalseType var1);

   TrueFalseType addNewScriptingInvalid();

   void unsetScriptingInvalid();

   TrueFalseType getIsXml();

   boolean isSetIsXml();

   void setIsXml(TrueFalseType var1);

   TrueFalseType addNewIsXml();

   void unsetIsXml();

   PathType[] getIncludePreludeArray();

   PathType getIncludePreludeArray(int var1);

   int sizeOfIncludePreludeArray();

   void setIncludePreludeArray(PathType[] var1);

   void setIncludePreludeArray(int var1, PathType var2);

   PathType insertNewIncludePrelude(int var1);

   PathType addNewIncludePrelude();

   void removeIncludePrelude(int var1);

   PathType[] getIncludeCodaArray();

   PathType getIncludeCodaArray(int var1);

   int sizeOfIncludeCodaArray();

   void setIncludeCodaArray(PathType[] var1);

   void setIncludeCodaArray(int var1, PathType var2);

   PathType insertNewIncludeCoda(int var1);

   PathType addNewIncludeCoda();

   void removeIncludeCoda(int var1);

   TrueFalseType getDeferredSyntaxAllowedAsLiteral();

   boolean isSetDeferredSyntaxAllowedAsLiteral();

   void setDeferredSyntaxAllowedAsLiteral(TrueFalseType var1);

   TrueFalseType addNewDeferredSyntaxAllowedAsLiteral();

   void unsetDeferredSyntaxAllowedAsLiteral();

   TrueFalseType getTrimDirectiveWhitespaces();

   boolean isSetTrimDirectiveWhitespaces();

   void setTrimDirectiveWhitespaces(TrueFalseType var1);

   TrueFalseType addNewTrimDirectiveWhitespaces();

   void unsetTrimDirectiveWhitespaces();

   String getDefaultContentType();

   boolean isSetDefaultContentType();

   void setDefaultContentType(String var1);

   String addNewDefaultContentType();

   void unsetDefaultContentType();

   String getBuffer();

   boolean isSetBuffer();

   void setBuffer(String var1);

   String addNewBuffer();

   void unsetBuffer();

   TrueFalseType getErrorOnUndeclaredNamespace();

   boolean isSetErrorOnUndeclaredNamespace();

   void setErrorOnUndeclaredNamespace(TrueFalseType var1);

   TrueFalseType addNewErrorOnUndeclaredNamespace();

   void unsetErrorOnUndeclaredNamespace();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static JspPropertyGroupType newInstance() {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().newInstance(JspPropertyGroupType.type, (XmlOptions)null);
      }

      public static JspPropertyGroupType newInstance(XmlOptions options) {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().newInstance(JspPropertyGroupType.type, options);
      }

      public static JspPropertyGroupType parse(java.lang.String xmlAsString) throws XmlException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JspPropertyGroupType.type, (XmlOptions)null);
      }

      public static JspPropertyGroupType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JspPropertyGroupType.type, options);
      }

      public static JspPropertyGroupType parse(File file) throws XmlException, IOException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(file, JspPropertyGroupType.type, (XmlOptions)null);
      }

      public static JspPropertyGroupType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(file, JspPropertyGroupType.type, options);
      }

      public static JspPropertyGroupType parse(URL u) throws XmlException, IOException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(u, JspPropertyGroupType.type, (XmlOptions)null);
      }

      public static JspPropertyGroupType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(u, JspPropertyGroupType.type, options);
      }

      public static JspPropertyGroupType parse(InputStream is) throws XmlException, IOException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(is, JspPropertyGroupType.type, (XmlOptions)null);
      }

      public static JspPropertyGroupType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(is, JspPropertyGroupType.type, options);
      }

      public static JspPropertyGroupType parse(Reader r) throws XmlException, IOException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(r, JspPropertyGroupType.type, (XmlOptions)null);
      }

      public static JspPropertyGroupType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(r, JspPropertyGroupType.type, options);
      }

      public static JspPropertyGroupType parse(XMLStreamReader sr) throws XmlException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(sr, JspPropertyGroupType.type, (XmlOptions)null);
      }

      public static JspPropertyGroupType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(sr, JspPropertyGroupType.type, options);
      }

      public static JspPropertyGroupType parse(Node node) throws XmlException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(node, JspPropertyGroupType.type, (XmlOptions)null);
      }

      public static JspPropertyGroupType parse(Node node, XmlOptions options) throws XmlException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(node, JspPropertyGroupType.type, options);
      }

      /** @deprecated */
      public static JspPropertyGroupType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(xis, JspPropertyGroupType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JspPropertyGroupType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JspPropertyGroupType)XmlBeans.getContextTypeLoader().parse(xis, JspPropertyGroupType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JspPropertyGroupType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JspPropertyGroupType.type, options);
      }

      private Factory() {
      }
   }
}
