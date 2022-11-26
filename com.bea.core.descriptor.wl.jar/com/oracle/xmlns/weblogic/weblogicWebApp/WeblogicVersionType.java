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

public interface WeblogicVersionType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicVersionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicversiontypea888type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WeblogicVersionType newInstance() {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().newInstance(WeblogicVersionType.type, (XmlOptions)null);
      }

      public static WeblogicVersionType newInstance(XmlOptions options) {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().newInstance(WeblogicVersionType.type, options);
      }

      public static WeblogicVersionType parse(String xmlAsString) throws XmlException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicVersionType.type, (XmlOptions)null);
      }

      public static WeblogicVersionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicVersionType.type, options);
      }

      public static WeblogicVersionType parse(File file) throws XmlException, IOException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(file, WeblogicVersionType.type, (XmlOptions)null);
      }

      public static WeblogicVersionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(file, WeblogicVersionType.type, options);
      }

      public static WeblogicVersionType parse(URL u) throws XmlException, IOException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(u, WeblogicVersionType.type, (XmlOptions)null);
      }

      public static WeblogicVersionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(u, WeblogicVersionType.type, options);
      }

      public static WeblogicVersionType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(is, WeblogicVersionType.type, (XmlOptions)null);
      }

      public static WeblogicVersionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(is, WeblogicVersionType.type, options);
      }

      public static WeblogicVersionType parse(Reader r) throws XmlException, IOException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(r, WeblogicVersionType.type, (XmlOptions)null);
      }

      public static WeblogicVersionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(r, WeblogicVersionType.type, options);
      }

      public static WeblogicVersionType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicVersionType.type, (XmlOptions)null);
      }

      public static WeblogicVersionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicVersionType.type, options);
      }

      public static WeblogicVersionType parse(Node node) throws XmlException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(node, WeblogicVersionType.type, (XmlOptions)null);
      }

      public static WeblogicVersionType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(node, WeblogicVersionType.type, options);
      }

      /** @deprecated */
      public static WeblogicVersionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicVersionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicVersionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicVersionType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicVersionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicVersionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicVersionType.type, options);
      }

      private Factory() {
      }
   }
}
