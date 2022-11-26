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

public interface WeblogicConnectorExtensionDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicConnectorExtensionDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicconnectorextension72cadoctype");

   WeblogicConnectorExtensionType getWeblogicConnectorExtension();

   void setWeblogicConnectorExtension(WeblogicConnectorExtensionType var1);

   WeblogicConnectorExtensionType addNewWeblogicConnectorExtension();

   public static final class Factory {
      public static WeblogicConnectorExtensionDocument newInstance() {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicConnectorExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionDocument newInstance(XmlOptions options) {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicConnectorExtensionDocument.type, options);
      }

      public static WeblogicConnectorExtensionDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicConnectorExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicConnectorExtensionDocument.type, options);
      }

      public static WeblogicConnectorExtensionDocument parse(File file) throws XmlException, IOException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicConnectorExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicConnectorExtensionDocument.type, options);
      }

      public static WeblogicConnectorExtensionDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicConnectorExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicConnectorExtensionDocument.type, options);
      }

      public static WeblogicConnectorExtensionDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicConnectorExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicConnectorExtensionDocument.type, options);
      }

      public static WeblogicConnectorExtensionDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicConnectorExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicConnectorExtensionDocument.type, options);
      }

      public static WeblogicConnectorExtensionDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicConnectorExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicConnectorExtensionDocument.type, options);
      }

      public static WeblogicConnectorExtensionDocument parse(Node node) throws XmlException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicConnectorExtensionDocument.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicConnectorExtensionDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicConnectorExtensionDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicConnectorExtensionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicConnectorExtensionDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicConnectorExtensionDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicConnectorExtensionDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicConnectorExtensionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicConnectorExtensionDocument.type, options);
      }

      private Factory() {
      }
   }
}
