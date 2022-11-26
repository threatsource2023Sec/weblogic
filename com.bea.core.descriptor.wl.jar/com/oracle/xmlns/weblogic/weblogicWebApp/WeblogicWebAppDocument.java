package com.oracle.xmlns.weblogic.weblogicWebApp;

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

public interface WeblogicWebAppDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicWebAppDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicwebapp780edoctype");

   WeblogicWebAppType getWeblogicWebApp();

   void setWeblogicWebApp(WeblogicWebAppType var1);

   WeblogicWebAppType addNewWeblogicWebApp();

   public static final class Factory {
      public static WeblogicWebAppDocument newInstance() {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicWebAppDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebAppDocument newInstance(XmlOptions options) {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicWebAppDocument.type, options);
      }

      public static WeblogicWebAppDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWebAppDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebAppDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWebAppDocument.type, options);
      }

      public static WeblogicWebAppDocument parse(File file) throws XmlException, IOException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicWebAppDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebAppDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicWebAppDocument.type, options);
      }

      public static WeblogicWebAppDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicWebAppDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebAppDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicWebAppDocument.type, options);
      }

      public static WeblogicWebAppDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicWebAppDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebAppDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicWebAppDocument.type, options);
      }

      public static WeblogicWebAppDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicWebAppDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebAppDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicWebAppDocument.type, options);
      }

      public static WeblogicWebAppDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWebAppDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebAppDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWebAppDocument.type, options);
      }

      public static WeblogicWebAppDocument parse(Node node) throws XmlException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicWebAppDocument.type, (XmlOptions)null);
      }

      public static WeblogicWebAppDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicWebAppDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicWebAppDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWebAppDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicWebAppDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicWebAppDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWebAppDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWebAppDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWebAppDocument.type, options);
      }

      private Factory() {
      }
   }
}
