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

public interface ConnectionPropertiesDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionPropertiesDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("connectionpropertiesb37edoctype");

   ConnectionDefinitionPropertiesType getConnectionProperties();

   void setConnectionProperties(ConnectionDefinitionPropertiesType var1);

   ConnectionDefinitionPropertiesType addNewConnectionProperties();

   public static final class Factory {
      public static ConnectionPropertiesDocument newInstance() {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesDocument newInstance(XmlOptions options) {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectionPropertiesDocument.type, options);
      }

      public static ConnectionPropertiesDocument parse(String xmlAsString) throws XmlException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionPropertiesDocument.type, options);
      }

      public static ConnectionPropertiesDocument parse(File file) throws XmlException, IOException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectionPropertiesDocument.type, options);
      }

      public static ConnectionPropertiesDocument parse(URL u) throws XmlException, IOException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectionPropertiesDocument.type, options);
      }

      public static ConnectionPropertiesDocument parse(InputStream is) throws XmlException, IOException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectionPropertiesDocument.type, options);
      }

      public static ConnectionPropertiesDocument parse(Reader r) throws XmlException, IOException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectionPropertiesDocument.type, options);
      }

      public static ConnectionPropertiesDocument parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectionPropertiesDocument.type, options);
      }

      public static ConnectionPropertiesDocument parse(Node node) throws XmlException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectionPropertiesDocument.type, options);
      }

      /** @deprecated */
      public static ConnectionPropertiesDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionPropertiesDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionPropertiesDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectionPropertiesDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionPropertiesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionPropertiesDocument.type, options);
      }

      private Factory() {
      }
   }
}
