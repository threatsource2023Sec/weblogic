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

public interface BrokerImplType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BrokerImplType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("brokerimpltype5ad8type");

   public static final class Factory {
      public static BrokerImplType newInstance() {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().newInstance(BrokerImplType.type, (XmlOptions)null);
      }

      public static BrokerImplType newInstance(XmlOptions options) {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().newInstance(BrokerImplType.type, options);
      }

      public static BrokerImplType parse(String xmlAsString) throws XmlException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BrokerImplType.type, (XmlOptions)null);
      }

      public static BrokerImplType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BrokerImplType.type, options);
      }

      public static BrokerImplType parse(File file) throws XmlException, IOException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(file, BrokerImplType.type, (XmlOptions)null);
      }

      public static BrokerImplType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(file, BrokerImplType.type, options);
      }

      public static BrokerImplType parse(URL u) throws XmlException, IOException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(u, BrokerImplType.type, (XmlOptions)null);
      }

      public static BrokerImplType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(u, BrokerImplType.type, options);
      }

      public static BrokerImplType parse(InputStream is) throws XmlException, IOException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(is, BrokerImplType.type, (XmlOptions)null);
      }

      public static BrokerImplType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(is, BrokerImplType.type, options);
      }

      public static BrokerImplType parse(Reader r) throws XmlException, IOException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(r, BrokerImplType.type, (XmlOptions)null);
      }

      public static BrokerImplType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(r, BrokerImplType.type, options);
      }

      public static BrokerImplType parse(XMLStreamReader sr) throws XmlException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(sr, BrokerImplType.type, (XmlOptions)null);
      }

      public static BrokerImplType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(sr, BrokerImplType.type, options);
      }

      public static BrokerImplType parse(Node node) throws XmlException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(node, BrokerImplType.type, (XmlOptions)null);
      }

      public static BrokerImplType parse(Node node, XmlOptions options) throws XmlException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(node, BrokerImplType.type, options);
      }

      /** @deprecated */
      public static BrokerImplType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(xis, BrokerImplType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BrokerImplType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BrokerImplType)XmlBeans.getContextTypeLoader().parse(xis, BrokerImplType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BrokerImplType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BrokerImplType.type, options);
      }

      private Factory() {
      }
   }
}
