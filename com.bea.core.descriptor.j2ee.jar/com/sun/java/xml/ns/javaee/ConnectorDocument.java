package com.sun.java.xml.ns.javaee;

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

public interface ConnectorDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectorDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("connector1e1edoctype");

   ConnectorType getConnector();

   void setConnector(ConnectorType var1);

   ConnectorType addNewConnector();

   public static final class Factory {
      public static ConnectorDocument newInstance() {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument newInstance(XmlOptions options) {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(java.lang.String xmlAsString) throws XmlException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(File file) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(URL u) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(InputStream is) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(Reader r) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(XMLStreamReader sr) throws XmlException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(Node node) throws XmlException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectorDocument.type, options);
      }

      /** @deprecated */
      public static ConnectorDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectorDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectorDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectorDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorDocument.type, options);
      }

      private Factory() {
      }
   }
}
