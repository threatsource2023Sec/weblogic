package com.bea.connector.monitoring1Dot0;

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

public interface ConnectorWorkManagerDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectorWorkManagerDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("connectorworkmanageref1ddoctype");

   ConnectorWorkManagerType getConnectorWorkManager();

   void setConnectorWorkManager(ConnectorWorkManagerType var1);

   ConnectorWorkManagerType addNewConnectorWorkManager();

   public static final class Factory {
      public static ConnectorWorkManagerDocument newInstance() {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectorWorkManagerDocument.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerDocument newInstance(XmlOptions options) {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectorWorkManagerDocument.type, options);
      }

      public static ConnectorWorkManagerDocument parse(String xmlAsString) throws XmlException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorWorkManagerDocument.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorWorkManagerDocument.type, options);
      }

      public static ConnectorWorkManagerDocument parse(File file) throws XmlException, IOException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectorWorkManagerDocument.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectorWorkManagerDocument.type, options);
      }

      public static ConnectorWorkManagerDocument parse(URL u) throws XmlException, IOException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectorWorkManagerDocument.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectorWorkManagerDocument.type, options);
      }

      public static ConnectorWorkManagerDocument parse(InputStream is) throws XmlException, IOException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectorWorkManagerDocument.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectorWorkManagerDocument.type, options);
      }

      public static ConnectorWorkManagerDocument parse(Reader r) throws XmlException, IOException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectorWorkManagerDocument.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectorWorkManagerDocument.type, options);
      }

      public static ConnectorWorkManagerDocument parse(XMLStreamReader sr) throws XmlException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectorWorkManagerDocument.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectorWorkManagerDocument.type, options);
      }

      public static ConnectorWorkManagerDocument parse(Node node) throws XmlException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectorWorkManagerDocument.type, (XmlOptions)null);
      }

      public static ConnectorWorkManagerDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectorWorkManagerDocument.type, options);
      }

      /** @deprecated */
      public static ConnectorWorkManagerDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectorWorkManagerDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectorWorkManagerDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectorWorkManagerDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectorWorkManagerDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorWorkManagerDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorWorkManagerDocument.type, options);
      }

      private Factory() {
      }
   }
}
