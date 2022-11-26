package com.oracle.xmlns.weblogic.weblogicConnector;

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

public interface WeblogicConnectorDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicConnectorDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicconnector50d8doctype");

   WeblogicConnectorType getWeblogicConnector();

   void setWeblogicConnector(WeblogicConnectorType var1);

   WeblogicConnectorType addNewWeblogicConnector();

   public static final class Factory {
      public static WeblogicConnectorDocument newInstance() {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicConnectorDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorDocument newInstance(XmlOptions options) {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicConnectorDocument.type, options);
      }

      public static WeblogicConnectorDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicConnectorDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicConnectorDocument.type, options);
      }

      public static WeblogicConnectorDocument parse(File file) throws XmlException, IOException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicConnectorDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicConnectorDocument.type, options);
      }

      public static WeblogicConnectorDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicConnectorDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicConnectorDocument.type, options);
      }

      public static WeblogicConnectorDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicConnectorDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicConnectorDocument.type, options);
      }

      public static WeblogicConnectorDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicConnectorDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicConnectorDocument.type, options);
      }

      public static WeblogicConnectorDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicConnectorDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicConnectorDocument.type, options);
      }

      public static WeblogicConnectorDocument parse(Node node) throws XmlException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicConnectorDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicConnectorDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicConnectorDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicConnectorDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicConnectorDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicConnectorDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicConnectorDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicConnectorDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicConnectorDocument.type, options);
      }

      private Factory() {
      }
   }
}
