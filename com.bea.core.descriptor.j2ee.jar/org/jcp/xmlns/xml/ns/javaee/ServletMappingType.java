package org.jcp.xmlns.xml.ns.javaee;

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

public interface ServletMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ServletMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("servletmappingtype5cb7type");

   ServletNameType getServletName();

   void setServletName(ServletNameType var1);

   ServletNameType addNewServletName();

   UrlPatternType[] getUrlPatternArray();

   UrlPatternType getUrlPatternArray(int var1);

   int sizeOfUrlPatternArray();

   void setUrlPatternArray(UrlPatternType[] var1);

   void setUrlPatternArray(int var1, UrlPatternType var2);

   UrlPatternType insertNewUrlPattern(int var1);

   UrlPatternType addNewUrlPattern();

   void removeUrlPattern(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ServletMappingType newInstance() {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().newInstance(ServletMappingType.type, (XmlOptions)null);
      }

      public static ServletMappingType newInstance(XmlOptions options) {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().newInstance(ServletMappingType.type, options);
      }

      public static ServletMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServletMappingType.type, (XmlOptions)null);
      }

      public static ServletMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServletMappingType.type, options);
      }

      public static ServletMappingType parse(File file) throws XmlException, IOException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(file, ServletMappingType.type, (XmlOptions)null);
      }

      public static ServletMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(file, ServletMappingType.type, options);
      }

      public static ServletMappingType parse(URL u) throws XmlException, IOException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(u, ServletMappingType.type, (XmlOptions)null);
      }

      public static ServletMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(u, ServletMappingType.type, options);
      }

      public static ServletMappingType parse(InputStream is) throws XmlException, IOException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(is, ServletMappingType.type, (XmlOptions)null);
      }

      public static ServletMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(is, ServletMappingType.type, options);
      }

      public static ServletMappingType parse(Reader r) throws XmlException, IOException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(r, ServletMappingType.type, (XmlOptions)null);
      }

      public static ServletMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(r, ServletMappingType.type, options);
      }

      public static ServletMappingType parse(XMLStreamReader sr) throws XmlException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(sr, ServletMappingType.type, (XmlOptions)null);
      }

      public static ServletMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(sr, ServletMappingType.type, options);
      }

      public static ServletMappingType parse(Node node) throws XmlException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(node, ServletMappingType.type, (XmlOptions)null);
      }

      public static ServletMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(node, ServletMappingType.type, options);
      }

      /** @deprecated */
      public static ServletMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(xis, ServletMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ServletMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ServletMappingType)XmlBeans.getContextTypeLoader().parse(xis, ServletMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServletMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServletMappingType.type, options);
      }

      private Factory() {
      }
   }
}
