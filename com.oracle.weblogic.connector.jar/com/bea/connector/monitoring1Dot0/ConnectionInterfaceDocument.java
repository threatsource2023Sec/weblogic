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

public interface ConnectionInterfaceDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionInterfaceDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("connectioninterfacefee4doctype");

   String getConnectionInterface();

   XmlString xgetConnectionInterface();

   void setConnectionInterface(String var1);

   void xsetConnectionInterface(XmlString var1);

   public static final class Factory {
      public static ConnectionInterfaceDocument newInstance() {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectionInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInterfaceDocument newInstance(XmlOptions options) {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectionInterfaceDocument.type, options);
      }

      public static ConnectionInterfaceDocument parse(String xmlAsString) throws XmlException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInterfaceDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionInterfaceDocument.type, options);
      }

      public static ConnectionInterfaceDocument parse(File file) throws XmlException, IOException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectionInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInterfaceDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectionInterfaceDocument.type, options);
      }

      public static ConnectionInterfaceDocument parse(URL u) throws XmlException, IOException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectionInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInterfaceDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectionInterfaceDocument.type, options);
      }

      public static ConnectionInterfaceDocument parse(InputStream is) throws XmlException, IOException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectionInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInterfaceDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectionInterfaceDocument.type, options);
      }

      public static ConnectionInterfaceDocument parse(Reader r) throws XmlException, IOException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectionInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInterfaceDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectionInterfaceDocument.type, options);
      }

      public static ConnectionInterfaceDocument parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectionInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInterfaceDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectionInterfaceDocument.type, options);
      }

      public static ConnectionInterfaceDocument parse(Node node) throws XmlException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectionInterfaceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInterfaceDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectionInterfaceDocument.type, options);
      }

      /** @deprecated */
      public static ConnectionInterfaceDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectionInterfaceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionInterfaceDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionInterfaceDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectionInterfaceDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionInterfaceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionInterfaceDocument.type, options);
      }

      private Factory() {
      }
   }
}
