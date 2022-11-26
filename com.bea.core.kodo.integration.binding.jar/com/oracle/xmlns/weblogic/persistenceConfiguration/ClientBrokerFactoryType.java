package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface ClientBrokerFactoryType extends BrokerFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ClientBrokerFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("clientbrokerfactorytype3154type");

   public static final class Factory {
      public static ClientBrokerFactoryType newInstance() {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().newInstance(ClientBrokerFactoryType.type, (XmlOptions)null);
      }

      public static ClientBrokerFactoryType newInstance(XmlOptions options) {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().newInstance(ClientBrokerFactoryType.type, options);
      }

      public static ClientBrokerFactoryType parse(String xmlAsString) throws XmlException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClientBrokerFactoryType.type, (XmlOptions)null);
      }

      public static ClientBrokerFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClientBrokerFactoryType.type, options);
      }

      public static ClientBrokerFactoryType parse(File file) throws XmlException, IOException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(file, ClientBrokerFactoryType.type, (XmlOptions)null);
      }

      public static ClientBrokerFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(file, ClientBrokerFactoryType.type, options);
      }

      public static ClientBrokerFactoryType parse(URL u) throws XmlException, IOException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(u, ClientBrokerFactoryType.type, (XmlOptions)null);
      }

      public static ClientBrokerFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(u, ClientBrokerFactoryType.type, options);
      }

      public static ClientBrokerFactoryType parse(InputStream is) throws XmlException, IOException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(is, ClientBrokerFactoryType.type, (XmlOptions)null);
      }

      public static ClientBrokerFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(is, ClientBrokerFactoryType.type, options);
      }

      public static ClientBrokerFactoryType parse(Reader r) throws XmlException, IOException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(r, ClientBrokerFactoryType.type, (XmlOptions)null);
      }

      public static ClientBrokerFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(r, ClientBrokerFactoryType.type, options);
      }

      public static ClientBrokerFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(sr, ClientBrokerFactoryType.type, (XmlOptions)null);
      }

      public static ClientBrokerFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(sr, ClientBrokerFactoryType.type, options);
      }

      public static ClientBrokerFactoryType parse(Node node) throws XmlException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(node, ClientBrokerFactoryType.type, (XmlOptions)null);
      }

      public static ClientBrokerFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(node, ClientBrokerFactoryType.type, options);
      }

      /** @deprecated */
      public static ClientBrokerFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xis, ClientBrokerFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ClientBrokerFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ClientBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xis, ClientBrokerFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClientBrokerFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClientBrokerFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
