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

public interface DefaultBrokerImplType extends BrokerImplType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultBrokerImplType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultbrokerimpltype94e4type");

   public static final class Factory {
      public static DefaultBrokerImplType newInstance() {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().newInstance(DefaultBrokerImplType.type, (XmlOptions)null);
      }

      public static DefaultBrokerImplType newInstance(XmlOptions options) {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().newInstance(DefaultBrokerImplType.type, options);
      }

      public static DefaultBrokerImplType parse(String xmlAsString) throws XmlException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultBrokerImplType.type, (XmlOptions)null);
      }

      public static DefaultBrokerImplType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultBrokerImplType.type, options);
      }

      public static DefaultBrokerImplType parse(File file) throws XmlException, IOException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(file, DefaultBrokerImplType.type, (XmlOptions)null);
      }

      public static DefaultBrokerImplType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(file, DefaultBrokerImplType.type, options);
      }

      public static DefaultBrokerImplType parse(URL u) throws XmlException, IOException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(u, DefaultBrokerImplType.type, (XmlOptions)null);
      }

      public static DefaultBrokerImplType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(u, DefaultBrokerImplType.type, options);
      }

      public static DefaultBrokerImplType parse(InputStream is) throws XmlException, IOException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(is, DefaultBrokerImplType.type, (XmlOptions)null);
      }

      public static DefaultBrokerImplType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(is, DefaultBrokerImplType.type, options);
      }

      public static DefaultBrokerImplType parse(Reader r) throws XmlException, IOException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(r, DefaultBrokerImplType.type, (XmlOptions)null);
      }

      public static DefaultBrokerImplType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(r, DefaultBrokerImplType.type, options);
      }

      public static DefaultBrokerImplType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(sr, DefaultBrokerImplType.type, (XmlOptions)null);
      }

      public static DefaultBrokerImplType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(sr, DefaultBrokerImplType.type, options);
      }

      public static DefaultBrokerImplType parse(Node node) throws XmlException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(node, DefaultBrokerImplType.type, (XmlOptions)null);
      }

      public static DefaultBrokerImplType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(node, DefaultBrokerImplType.type, options);
      }

      /** @deprecated */
      public static DefaultBrokerImplType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(xis, DefaultBrokerImplType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultBrokerImplType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultBrokerImplType)XmlBeans.getContextTypeLoader().parse(xis, DefaultBrokerImplType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultBrokerImplType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultBrokerImplType.type, options);
      }

      private Factory() {
      }
   }
}
