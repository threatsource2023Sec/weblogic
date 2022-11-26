package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface WeblogicConnectorExtensionType extends WeblogicConnectorType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicConnectorExtensionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicconnectorextensiontypec494type");

   LinkRefType getLinkRef();

   boolean isSetLinkRef();

   void setLinkRef(LinkRefType var1);

   LinkRefType addNewLinkRef();

   void unsetLinkRef();

   ProxyType getProxy();

   boolean isSetProxy();

   void setProxy(ProxyType var1);

   ProxyType addNewProxy();

   void unsetProxy();

   public static final class Factory {
      public static WeblogicConnectorExtensionType newInstance() {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().newInstance(WeblogicConnectorExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionType newInstance(XmlOptions options) {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().newInstance(WeblogicConnectorExtensionType.type, options);
      }

      public static WeblogicConnectorExtensionType parse(String xmlAsString) throws XmlException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicConnectorExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicConnectorExtensionType.type, options);
      }

      public static WeblogicConnectorExtensionType parse(File file) throws XmlException, IOException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(file, WeblogicConnectorExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(file, WeblogicConnectorExtensionType.type, options);
      }

      public static WeblogicConnectorExtensionType parse(URL u) throws XmlException, IOException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(u, WeblogicConnectorExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(u, WeblogicConnectorExtensionType.type, options);
      }

      public static WeblogicConnectorExtensionType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(is, WeblogicConnectorExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(is, WeblogicConnectorExtensionType.type, options);
      }

      public static WeblogicConnectorExtensionType parse(Reader r) throws XmlException, IOException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(r, WeblogicConnectorExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(r, WeblogicConnectorExtensionType.type, options);
      }

      public static WeblogicConnectorExtensionType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicConnectorExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicConnectorExtensionType.type, options);
      }

      public static WeblogicConnectorExtensionType parse(Node node) throws XmlException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(node, WeblogicConnectorExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorExtensionType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(node, WeblogicConnectorExtensionType.type, options);
      }

      /** @deprecated */
      public static WeblogicConnectorExtensionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicConnectorExtensionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicConnectorExtensionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicConnectorExtensionType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicConnectorExtensionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicConnectorExtensionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicConnectorExtensionType.type, options);
      }

      private Factory() {
      }
   }
}
