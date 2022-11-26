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

public interface UrlMatchMapType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UrlMatchMapType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("urlmatchmaptype02f7type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static UrlMatchMapType newInstance() {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().newInstance(UrlMatchMapType.type, (XmlOptions)null);
      }

      public static UrlMatchMapType newInstance(XmlOptions options) {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().newInstance(UrlMatchMapType.type, options);
      }

      public static UrlMatchMapType parse(String xmlAsString) throws XmlException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UrlMatchMapType.type, (XmlOptions)null);
      }

      public static UrlMatchMapType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UrlMatchMapType.type, options);
      }

      public static UrlMatchMapType parse(File file) throws XmlException, IOException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(file, UrlMatchMapType.type, (XmlOptions)null);
      }

      public static UrlMatchMapType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(file, UrlMatchMapType.type, options);
      }

      public static UrlMatchMapType parse(URL u) throws XmlException, IOException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(u, UrlMatchMapType.type, (XmlOptions)null);
      }

      public static UrlMatchMapType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(u, UrlMatchMapType.type, options);
      }

      public static UrlMatchMapType parse(InputStream is) throws XmlException, IOException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(is, UrlMatchMapType.type, (XmlOptions)null);
      }

      public static UrlMatchMapType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(is, UrlMatchMapType.type, options);
      }

      public static UrlMatchMapType parse(Reader r) throws XmlException, IOException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(r, UrlMatchMapType.type, (XmlOptions)null);
      }

      public static UrlMatchMapType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(r, UrlMatchMapType.type, options);
      }

      public static UrlMatchMapType parse(XMLStreamReader sr) throws XmlException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(sr, UrlMatchMapType.type, (XmlOptions)null);
      }

      public static UrlMatchMapType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(sr, UrlMatchMapType.type, options);
      }

      public static UrlMatchMapType parse(Node node) throws XmlException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(node, UrlMatchMapType.type, (XmlOptions)null);
      }

      public static UrlMatchMapType parse(Node node, XmlOptions options) throws XmlException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(node, UrlMatchMapType.type, options);
      }

      /** @deprecated */
      public static UrlMatchMapType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(xis, UrlMatchMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UrlMatchMapType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UrlMatchMapType)XmlBeans.getContextTypeLoader().parse(xis, UrlMatchMapType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UrlMatchMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UrlMatchMapType.type, options);
      }

      private Factory() {
      }
   }
}
