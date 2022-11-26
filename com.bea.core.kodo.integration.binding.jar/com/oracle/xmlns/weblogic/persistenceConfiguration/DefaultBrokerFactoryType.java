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

public interface DefaultBrokerFactoryType extends BrokerFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultBrokerFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultbrokerfactorytype7298type");

   public static final class Factory {
      public static DefaultBrokerFactoryType newInstance() {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().newInstance(DefaultBrokerFactoryType.type, (XmlOptions)null);
      }

      public static DefaultBrokerFactoryType newInstance(XmlOptions options) {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().newInstance(DefaultBrokerFactoryType.type, options);
      }

      public static DefaultBrokerFactoryType parse(String xmlAsString) throws XmlException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultBrokerFactoryType.type, (XmlOptions)null);
      }

      public static DefaultBrokerFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultBrokerFactoryType.type, options);
      }

      public static DefaultBrokerFactoryType parse(File file) throws XmlException, IOException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(file, DefaultBrokerFactoryType.type, (XmlOptions)null);
      }

      public static DefaultBrokerFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(file, DefaultBrokerFactoryType.type, options);
      }

      public static DefaultBrokerFactoryType parse(URL u) throws XmlException, IOException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(u, DefaultBrokerFactoryType.type, (XmlOptions)null);
      }

      public static DefaultBrokerFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(u, DefaultBrokerFactoryType.type, options);
      }

      public static DefaultBrokerFactoryType parse(InputStream is) throws XmlException, IOException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(is, DefaultBrokerFactoryType.type, (XmlOptions)null);
      }

      public static DefaultBrokerFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(is, DefaultBrokerFactoryType.type, options);
      }

      public static DefaultBrokerFactoryType parse(Reader r) throws XmlException, IOException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(r, DefaultBrokerFactoryType.type, (XmlOptions)null);
      }

      public static DefaultBrokerFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(r, DefaultBrokerFactoryType.type, options);
      }

      public static DefaultBrokerFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(sr, DefaultBrokerFactoryType.type, (XmlOptions)null);
      }

      public static DefaultBrokerFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(sr, DefaultBrokerFactoryType.type, options);
      }

      public static DefaultBrokerFactoryType parse(Node node) throws XmlException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(node, DefaultBrokerFactoryType.type, (XmlOptions)null);
      }

      public static DefaultBrokerFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(node, DefaultBrokerFactoryType.type, options);
      }

      /** @deprecated */
      public static DefaultBrokerFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xis, DefaultBrokerFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultBrokerFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xis, DefaultBrokerFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultBrokerFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultBrokerFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
