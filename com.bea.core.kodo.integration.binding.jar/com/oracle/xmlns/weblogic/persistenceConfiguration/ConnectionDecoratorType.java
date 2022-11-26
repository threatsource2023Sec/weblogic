package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface ConnectionDecoratorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionDecoratorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("connectiondecoratortype732etype");

   public static final class Factory {
      public static ConnectionDecoratorType newInstance() {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().newInstance(ConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorType newInstance(XmlOptions options) {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().newInstance(ConnectionDecoratorType.type, options);
      }

      public static ConnectionDecoratorType parse(String xmlAsString) throws XmlException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionDecoratorType.type, options);
      }

      public static ConnectionDecoratorType parse(File file) throws XmlException, IOException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(file, ConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(file, ConnectionDecoratorType.type, options);
      }

      public static ConnectionDecoratorType parse(URL u) throws XmlException, IOException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(u, ConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(u, ConnectionDecoratorType.type, options);
      }

      public static ConnectionDecoratorType parse(InputStream is) throws XmlException, IOException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(is, ConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(is, ConnectionDecoratorType.type, options);
      }

      public static ConnectionDecoratorType parse(Reader r) throws XmlException, IOException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(r, ConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(r, ConnectionDecoratorType.type, options);
      }

      public static ConnectionDecoratorType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionDecoratorType.type, options);
      }

      public static ConnectionDecoratorType parse(Node node) throws XmlException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(node, ConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(node, ConnectionDecoratorType.type, options);
      }

      /** @deprecated */
      public static ConnectionDecoratorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionDecoratorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionDecoratorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionDecoratorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionDecoratorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionDecoratorType.type, options);
      }

      private Factory() {
      }
   }
}
