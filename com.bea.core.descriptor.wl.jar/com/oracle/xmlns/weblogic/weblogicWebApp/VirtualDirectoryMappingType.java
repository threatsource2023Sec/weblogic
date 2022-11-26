package com.oracle.xmlns.weblogic.weblogicWebApp;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.UrlPatternType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface VirtualDirectoryMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(VirtualDirectoryMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("virtualdirectorymappingtype20adtype");

   LocalPathType getLocalPath();

   void setLocalPath(LocalPathType var1);

   LocalPathType addNewLocalPath();

   UrlPatternType[] getUrlPatternArray();

   UrlPatternType getUrlPatternArray(int var1);

   int sizeOfUrlPatternArray();

   void setUrlPatternArray(UrlPatternType[] var1);

   void setUrlPatternArray(int var1, UrlPatternType var2);

   UrlPatternType insertNewUrlPattern(int var1);

   UrlPatternType addNewUrlPattern();

   void removeUrlPattern(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static VirtualDirectoryMappingType newInstance() {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().newInstance(VirtualDirectoryMappingType.type, (XmlOptions)null);
      }

      public static VirtualDirectoryMappingType newInstance(XmlOptions options) {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().newInstance(VirtualDirectoryMappingType.type, options);
      }

      public static VirtualDirectoryMappingType parse(String xmlAsString) throws XmlException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VirtualDirectoryMappingType.type, (XmlOptions)null);
      }

      public static VirtualDirectoryMappingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VirtualDirectoryMappingType.type, options);
      }

      public static VirtualDirectoryMappingType parse(File file) throws XmlException, IOException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(file, VirtualDirectoryMappingType.type, (XmlOptions)null);
      }

      public static VirtualDirectoryMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(file, VirtualDirectoryMappingType.type, options);
      }

      public static VirtualDirectoryMappingType parse(URL u) throws XmlException, IOException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(u, VirtualDirectoryMappingType.type, (XmlOptions)null);
      }

      public static VirtualDirectoryMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(u, VirtualDirectoryMappingType.type, options);
      }

      public static VirtualDirectoryMappingType parse(InputStream is) throws XmlException, IOException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(is, VirtualDirectoryMappingType.type, (XmlOptions)null);
      }

      public static VirtualDirectoryMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(is, VirtualDirectoryMappingType.type, options);
      }

      public static VirtualDirectoryMappingType parse(Reader r) throws XmlException, IOException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(r, VirtualDirectoryMappingType.type, (XmlOptions)null);
      }

      public static VirtualDirectoryMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(r, VirtualDirectoryMappingType.type, options);
      }

      public static VirtualDirectoryMappingType parse(XMLStreamReader sr) throws XmlException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(sr, VirtualDirectoryMappingType.type, (XmlOptions)null);
      }

      public static VirtualDirectoryMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(sr, VirtualDirectoryMappingType.type, options);
      }

      public static VirtualDirectoryMappingType parse(Node node) throws XmlException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(node, VirtualDirectoryMappingType.type, (XmlOptions)null);
      }

      public static VirtualDirectoryMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(node, VirtualDirectoryMappingType.type, options);
      }

      /** @deprecated */
      public static VirtualDirectoryMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(xis, VirtualDirectoryMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static VirtualDirectoryMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (VirtualDirectoryMappingType)XmlBeans.getContextTypeLoader().parse(xis, VirtualDirectoryMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VirtualDirectoryMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VirtualDirectoryMappingType.type, options);
      }

      private Factory() {
      }
   }
}
