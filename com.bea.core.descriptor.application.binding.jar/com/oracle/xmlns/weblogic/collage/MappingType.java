package com.oracle.xmlns.weblogic.collage;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface MappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("mappingtype50a9type");

   String getStyle();

   XmlString xgetStyle();

   boolean isSetStyle();

   void setStyle(String var1);

   void xsetStyle(XmlString var1);

   void unsetStyle();

   String getUri();

   XmlString xgetUri();

   void setUri(String var1);

   void xsetUri(XmlString var1);

   String getPath();

   XmlString xgetPath();

   void setPath(String var1);

   void xsetPath(XmlString var1);

   PatternType[] getPatternArray();

   PatternType getPatternArray(int var1);

   int sizeOfPatternArray();

   void setPatternArray(PatternType[] var1);

   void setPatternArray(int var1, PatternType var2);

   PatternType insertNewPattern(int var1);

   PatternType addNewPattern();

   void removePattern(int var1);

   String[] getIncludeArray();

   String getIncludeArray(int var1);

   XmlString[] xgetIncludeArray();

   XmlString xgetIncludeArray(int var1);

   int sizeOfIncludeArray();

   void setIncludeArray(String[] var1);

   void setIncludeArray(int var1, String var2);

   void xsetIncludeArray(XmlString[] var1);

   void xsetIncludeArray(int var1, XmlString var2);

   void insertInclude(int var1, String var2);

   void addInclude(String var1);

   XmlString insertNewInclude(int var1);

   XmlString addNewInclude();

   void removeInclude(int var1);

   String[] getExcludeArray();

   String getExcludeArray(int var1);

   XmlString[] xgetExcludeArray();

   XmlString xgetExcludeArray(int var1);

   int sizeOfExcludeArray();

   void setExcludeArray(String[] var1);

   void setExcludeArray(int var1, String var2);

   void xsetExcludeArray(XmlString[] var1);

   void xsetExcludeArray(int var1, XmlString var2);

   void insertExclude(int var1, String var2);

   void addExclude(String var1);

   XmlString insertNewExclude(int var1);

   XmlString addNewExclude();

   void removeExclude(int var1);

   public static final class Factory {
      public static MappingType newInstance() {
         return (MappingType)XmlBeans.getContextTypeLoader().newInstance(MappingType.type, (XmlOptions)null);
      }

      public static MappingType newInstance(XmlOptions options) {
         return (MappingType)XmlBeans.getContextTypeLoader().newInstance(MappingType.type, options);
      }

      public static MappingType parse(String xmlAsString) throws XmlException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MappingType.type, (XmlOptions)null);
      }

      public static MappingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MappingType.type, options);
      }

      public static MappingType parse(File file) throws XmlException, IOException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(file, MappingType.type, (XmlOptions)null);
      }

      public static MappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(file, MappingType.type, options);
      }

      public static MappingType parse(URL u) throws XmlException, IOException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(u, MappingType.type, (XmlOptions)null);
      }

      public static MappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(u, MappingType.type, options);
      }

      public static MappingType parse(InputStream is) throws XmlException, IOException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(is, MappingType.type, (XmlOptions)null);
      }

      public static MappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(is, MappingType.type, options);
      }

      public static MappingType parse(Reader r) throws XmlException, IOException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(r, MappingType.type, (XmlOptions)null);
      }

      public static MappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(r, MappingType.type, options);
      }

      public static MappingType parse(XMLStreamReader sr) throws XmlException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(sr, MappingType.type, (XmlOptions)null);
      }

      public static MappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(sr, MappingType.type, options);
      }

      public static MappingType parse(Node node) throws XmlException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(node, MappingType.type, (XmlOptions)null);
      }

      public static MappingType parse(Node node, XmlOptions options) throws XmlException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(node, MappingType.type, options);
      }

      /** @deprecated */
      public static MappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(xis, MappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MappingType)XmlBeans.getContextTypeLoader().parse(xis, MappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MappingType.type, options);
      }

      private Factory() {
      }
   }
}
