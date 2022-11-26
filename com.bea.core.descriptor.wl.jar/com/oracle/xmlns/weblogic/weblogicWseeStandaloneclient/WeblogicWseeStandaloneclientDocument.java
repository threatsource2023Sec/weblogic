package com.oracle.xmlns.weblogic.weblogicWseeStandaloneclient;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface WeblogicWseeStandaloneclientDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicWseeStandaloneclientDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicwseestandaloneclientf9c0doctype");

   WeblogicWseeStandaloneclientType getWeblogicWseeStandaloneclient();

   void setWeblogicWseeStandaloneclient(WeblogicWseeStandaloneclientType var1);

   WeblogicWseeStandaloneclientType addNewWeblogicWseeStandaloneclient();

   public static final class Factory {
      public static WeblogicWseeStandaloneclientDocument newInstance() {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicWseeStandaloneclientDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientDocument newInstance(XmlOptions options) {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicWseeStandaloneclientDocument.type, options);
      }

      public static WeblogicWseeStandaloneclientDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWseeStandaloneclientDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWseeStandaloneclientDocument.type, options);
      }

      public static WeblogicWseeStandaloneclientDocument parse(File file) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicWseeStandaloneclientDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicWseeStandaloneclientDocument.type, options);
      }

      public static WeblogicWseeStandaloneclientDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicWseeStandaloneclientDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicWseeStandaloneclientDocument.type, options);
      }

      public static WeblogicWseeStandaloneclientDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicWseeStandaloneclientDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicWseeStandaloneclientDocument.type, options);
      }

      public static WeblogicWseeStandaloneclientDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicWseeStandaloneclientDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicWseeStandaloneclientDocument.type, options);
      }

      public static WeblogicWseeStandaloneclientDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWseeStandaloneclientDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWseeStandaloneclientDocument.type, options);
      }

      public static WeblogicWseeStandaloneclientDocument parse(Node node) throws XmlException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicWseeStandaloneclientDocument.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicWseeStandaloneclientDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicWseeStandaloneclientDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWseeStandaloneclientDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicWseeStandaloneclientDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicWseeStandaloneclientDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWseeStandaloneclientDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWseeStandaloneclientDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWseeStandaloneclientDocument.type, options);
      }

      private Factory() {
      }
   }
}
