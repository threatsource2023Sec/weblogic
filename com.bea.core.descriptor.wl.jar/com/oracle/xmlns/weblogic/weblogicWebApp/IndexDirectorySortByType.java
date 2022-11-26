package com.oracle.xmlns.weblogic.weblogicWebApp;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface IndexDirectorySortByType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IndexDirectorySortByType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("indexdirectorysortbytypec54ctype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static IndexDirectorySortByType newInstance() {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().newInstance(IndexDirectorySortByType.type, (XmlOptions)null);
      }

      public static IndexDirectorySortByType newInstance(XmlOptions options) {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().newInstance(IndexDirectorySortByType.type, options);
      }

      public static IndexDirectorySortByType parse(String xmlAsString) throws XmlException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IndexDirectorySortByType.type, (XmlOptions)null);
      }

      public static IndexDirectorySortByType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IndexDirectorySortByType.type, options);
      }

      public static IndexDirectorySortByType parse(File file) throws XmlException, IOException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(file, IndexDirectorySortByType.type, (XmlOptions)null);
      }

      public static IndexDirectorySortByType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(file, IndexDirectorySortByType.type, options);
      }

      public static IndexDirectorySortByType parse(URL u) throws XmlException, IOException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(u, IndexDirectorySortByType.type, (XmlOptions)null);
      }

      public static IndexDirectorySortByType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(u, IndexDirectorySortByType.type, options);
      }

      public static IndexDirectorySortByType parse(InputStream is) throws XmlException, IOException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(is, IndexDirectorySortByType.type, (XmlOptions)null);
      }

      public static IndexDirectorySortByType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(is, IndexDirectorySortByType.type, options);
      }

      public static IndexDirectorySortByType parse(Reader r) throws XmlException, IOException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(r, IndexDirectorySortByType.type, (XmlOptions)null);
      }

      public static IndexDirectorySortByType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(r, IndexDirectorySortByType.type, options);
      }

      public static IndexDirectorySortByType parse(XMLStreamReader sr) throws XmlException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(sr, IndexDirectorySortByType.type, (XmlOptions)null);
      }

      public static IndexDirectorySortByType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(sr, IndexDirectorySortByType.type, options);
      }

      public static IndexDirectorySortByType parse(Node node) throws XmlException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(node, IndexDirectorySortByType.type, (XmlOptions)null);
      }

      public static IndexDirectorySortByType parse(Node node, XmlOptions options) throws XmlException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(node, IndexDirectorySortByType.type, options);
      }

      /** @deprecated */
      public static IndexDirectorySortByType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(xis, IndexDirectorySortByType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IndexDirectorySortByType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IndexDirectorySortByType)XmlBeans.getContextTypeLoader().parse(xis, IndexDirectorySortByType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IndexDirectorySortByType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IndexDirectorySortByType.type, options);
      }

      private Factory() {
      }
   }
}
