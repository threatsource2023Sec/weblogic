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

public interface ConnectionfactoryImplClassDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionfactoryImplClassDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("connectionfactoryimplclass08a2doctype");

   String getConnectionfactoryImplClass();

   XmlString xgetConnectionfactoryImplClass();

   void setConnectionfactoryImplClass(String var1);

   void xsetConnectionfactoryImplClass(XmlString var1);

   public static final class Factory {
      public static ConnectionfactoryImplClassDocument newInstance() {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectionfactoryImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionfactoryImplClassDocument newInstance(XmlOptions options) {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectionfactoryImplClassDocument.type, options);
      }

      public static ConnectionfactoryImplClassDocument parse(String xmlAsString) throws XmlException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionfactoryImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionfactoryImplClassDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionfactoryImplClassDocument.type, options);
      }

      public static ConnectionfactoryImplClassDocument parse(File file) throws XmlException, IOException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectionfactoryImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionfactoryImplClassDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectionfactoryImplClassDocument.type, options);
      }

      public static ConnectionfactoryImplClassDocument parse(URL u) throws XmlException, IOException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectionfactoryImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionfactoryImplClassDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectionfactoryImplClassDocument.type, options);
      }

      public static ConnectionfactoryImplClassDocument parse(InputStream is) throws XmlException, IOException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectionfactoryImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionfactoryImplClassDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectionfactoryImplClassDocument.type, options);
      }

      public static ConnectionfactoryImplClassDocument parse(Reader r) throws XmlException, IOException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectionfactoryImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionfactoryImplClassDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectionfactoryImplClassDocument.type, options);
      }

      public static ConnectionfactoryImplClassDocument parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectionfactoryImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionfactoryImplClassDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectionfactoryImplClassDocument.type, options);
      }

      public static ConnectionfactoryImplClassDocument parse(Node node) throws XmlException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectionfactoryImplClassDocument.type, (XmlOptions)null);
      }

      public static ConnectionfactoryImplClassDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectionfactoryImplClassDocument.type, options);
      }

      /** @deprecated */
      public static ConnectionfactoryImplClassDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectionfactoryImplClassDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionfactoryImplClassDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionfactoryImplClassDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectionfactoryImplClassDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionfactoryImplClassDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionfactoryImplClassDocument.type, options);
      }

      private Factory() {
      }
   }
}
