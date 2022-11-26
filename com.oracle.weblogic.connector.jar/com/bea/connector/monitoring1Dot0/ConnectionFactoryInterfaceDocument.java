package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface ConnectionFactoryInterfaceDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionFactoryInterfaceDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("connectionfactoryinterface1107doctype");

   String getConnectionFactoryInterface();

   XmlString xgetConnectionFactoryInterface();

   void setConnectionFactoryInterface(String var1);

   void xsetConnectionFactoryInterface(XmlString var1);

   public static final class Factory {
      public static ConnectionFactoryInterfaceDocument newInstance() {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectionFactoryInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionFactoryInterfaceDocument newInstance(XmlOptions options) {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectionFactoryInterfaceDocument.type, options);
      }

      public static ConnectionFactoryInterfaceDocument parse(String xmlAsString) throws XmlException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionFactoryInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionFactoryInterfaceDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionFactoryInterfaceDocument.type, options);
      }

      public static ConnectionFactoryInterfaceDocument parse(File file) throws XmlException, IOException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectionFactoryInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionFactoryInterfaceDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectionFactoryInterfaceDocument.type, options);
      }

      public static ConnectionFactoryInterfaceDocument parse(URL u) throws XmlException, IOException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectionFactoryInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionFactoryInterfaceDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectionFactoryInterfaceDocument.type, options);
      }

      public static ConnectionFactoryInterfaceDocument parse(InputStream is) throws XmlException, IOException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectionFactoryInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionFactoryInterfaceDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectionFactoryInterfaceDocument.type, options);
      }

      public static ConnectionFactoryInterfaceDocument parse(Reader r) throws XmlException, IOException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectionFactoryInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionFactoryInterfaceDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectionFactoryInterfaceDocument.type, options);
      }

      public static ConnectionFactoryInterfaceDocument parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectionFactoryInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionFactoryInterfaceDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectionFactoryInterfaceDocument.type, options);
      }

      public static ConnectionFactoryInterfaceDocument parse(Node node) throws XmlException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectionFactoryInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionFactoryInterfaceDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectionFactoryInterfaceDocument.type, options);
      }

      /** @deprecated */
      public static ConnectionFactoryInterfaceDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectionFactoryInterfaceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionFactoryInterfaceDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionFactoryInterfaceDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectionFactoryInterfaceDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionFactoryInterfaceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionFactoryInterfaceDocument.type, options);
      }

      private Factory() {
      }
   }
}
