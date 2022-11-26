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

public interface BrokerFactoryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BrokerFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("brokerfactorytype7824type");

   public static final class Factory {
      public static BrokerFactoryType newInstance() {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().newInstance(BrokerFactoryType.type, (XmlOptions)null);
      }

      public static BrokerFactoryType newInstance(XmlOptions options) {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().newInstance(BrokerFactoryType.type, options);
      }

      public static BrokerFactoryType parse(String xmlAsString) throws XmlException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BrokerFactoryType.type, (XmlOptions)null);
      }

      public static BrokerFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BrokerFactoryType.type, options);
      }

      public static BrokerFactoryType parse(File file) throws XmlException, IOException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(file, BrokerFactoryType.type, (XmlOptions)null);
      }

      public static BrokerFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(file, BrokerFactoryType.type, options);
      }

      public static BrokerFactoryType parse(URL u) throws XmlException, IOException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(u, BrokerFactoryType.type, (XmlOptions)null);
      }

      public static BrokerFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(u, BrokerFactoryType.type, options);
      }

      public static BrokerFactoryType parse(InputStream is) throws XmlException, IOException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(is, BrokerFactoryType.type, (XmlOptions)null);
      }

      public static BrokerFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(is, BrokerFactoryType.type, options);
      }

      public static BrokerFactoryType parse(Reader r) throws XmlException, IOException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(r, BrokerFactoryType.type, (XmlOptions)null);
      }

      public static BrokerFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(r, BrokerFactoryType.type, options);
      }

      public static BrokerFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(sr, BrokerFactoryType.type, (XmlOptions)null);
      }

      public static BrokerFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(sr, BrokerFactoryType.type, options);
      }

      public static BrokerFactoryType parse(Node node) throws XmlException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(node, BrokerFactoryType.type, (XmlOptions)null);
      }

      public static BrokerFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(node, BrokerFactoryType.type, options);
      }

      /** @deprecated */
      public static BrokerFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xis, BrokerFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BrokerFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xis, BrokerFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BrokerFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BrokerFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
