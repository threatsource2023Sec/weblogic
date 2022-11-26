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

public interface JdbcBrokerFactoryType extends BrokerFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JdbcBrokerFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("jdbcbrokerfactorytype02c4type");

   public static final class Factory {
      public static JdbcBrokerFactoryType newInstance() {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().newInstance(JdbcBrokerFactoryType.type, (XmlOptions)null);
      }

      public static JdbcBrokerFactoryType newInstance(XmlOptions options) {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().newInstance(JdbcBrokerFactoryType.type, options);
      }

      public static JdbcBrokerFactoryType parse(String xmlAsString) throws XmlException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdbcBrokerFactoryType.type, (XmlOptions)null);
      }

      public static JdbcBrokerFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdbcBrokerFactoryType.type, options);
      }

      public static JdbcBrokerFactoryType parse(File file) throws XmlException, IOException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(file, JdbcBrokerFactoryType.type, (XmlOptions)null);
      }

      public static JdbcBrokerFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(file, JdbcBrokerFactoryType.type, options);
      }

      public static JdbcBrokerFactoryType parse(URL u) throws XmlException, IOException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(u, JdbcBrokerFactoryType.type, (XmlOptions)null);
      }

      public static JdbcBrokerFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(u, JdbcBrokerFactoryType.type, options);
      }

      public static JdbcBrokerFactoryType parse(InputStream is) throws XmlException, IOException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(is, JdbcBrokerFactoryType.type, (XmlOptions)null);
      }

      public static JdbcBrokerFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(is, JdbcBrokerFactoryType.type, options);
      }

      public static JdbcBrokerFactoryType parse(Reader r) throws XmlException, IOException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(r, JdbcBrokerFactoryType.type, (XmlOptions)null);
      }

      public static JdbcBrokerFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(r, JdbcBrokerFactoryType.type, options);
      }

      public static JdbcBrokerFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(sr, JdbcBrokerFactoryType.type, (XmlOptions)null);
      }

      public static JdbcBrokerFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(sr, JdbcBrokerFactoryType.type, options);
      }

      public static JdbcBrokerFactoryType parse(Node node) throws XmlException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(node, JdbcBrokerFactoryType.type, (XmlOptions)null);
      }

      public static JdbcBrokerFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(node, JdbcBrokerFactoryType.type, options);
      }

      /** @deprecated */
      public static JdbcBrokerFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xis, JdbcBrokerFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JdbcBrokerFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JdbcBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xis, JdbcBrokerFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdbcBrokerFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdbcBrokerFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
