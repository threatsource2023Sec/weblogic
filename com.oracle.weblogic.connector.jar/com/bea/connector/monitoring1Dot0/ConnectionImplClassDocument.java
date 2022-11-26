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

public interface ConnectionImplClassDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionImplClassDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("connectionimplclass7ba6doctype");

   String getConnectionImplClass();

   XmlString xgetConnectionImplClass();

   void setConnectionImplClass(String var1);

   void xsetConnectionImplClass(XmlString var1);

   public static final class Factory {
      public static ConnectionImplClassDocument newInstance() {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectionImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionImplClassDocument newInstance(XmlOptions options) {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectionImplClassDocument.type, options);
      }

      public static ConnectionImplClassDocument parse(String xmlAsString) throws XmlException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionImplClassDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionImplClassDocument.type, options);
      }

      public static ConnectionImplClassDocument parse(File file) throws XmlException, IOException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectionImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionImplClassDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectionImplClassDocument.type, options);
      }

      public static ConnectionImplClassDocument parse(URL u) throws XmlException, IOException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectionImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionImplClassDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectionImplClassDocument.type, options);
      }

      public static ConnectionImplClassDocument parse(InputStream is) throws XmlException, IOException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectionImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionImplClassDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectionImplClassDocument.type, options);
      }

      public static ConnectionImplClassDocument parse(Reader r) throws XmlException, IOException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectionImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionImplClassDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectionImplClassDocument.type, options);
      }

      public static ConnectionImplClassDocument parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectionImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionImplClassDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectionImplClassDocument.type, options);
      }

      public static ConnectionImplClassDocument parse(Node node) throws XmlException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectionImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionImplClassDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectionImplClassDocument.type, options);
      }

      /** @deprecated */
      public static ConnectionImplClassDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectionImplClassDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionImplClassDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionImplClassDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectionImplClassDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionImplClassDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionImplClassDocument.type, options);
      }

      private Factory() {
      }
   }
}
