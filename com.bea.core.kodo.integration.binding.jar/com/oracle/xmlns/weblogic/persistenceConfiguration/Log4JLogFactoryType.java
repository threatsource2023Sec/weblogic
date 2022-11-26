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

public interface Log4JLogFactoryType extends LogType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Log4JLogFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("log4jlogfactorytype113etype");

   public static final class Factory {
      public static Log4JLogFactoryType newInstance() {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().newInstance(Log4JLogFactoryType.type, (XmlOptions)null);
      }

      public static Log4JLogFactoryType newInstance(XmlOptions options) {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().newInstance(Log4JLogFactoryType.type, options);
      }

      public static Log4JLogFactoryType parse(String xmlAsString) throws XmlException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, Log4JLogFactoryType.type, (XmlOptions)null);
      }

      public static Log4JLogFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, Log4JLogFactoryType.type, options);
      }

      public static Log4JLogFactoryType parse(File file) throws XmlException, IOException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(file, Log4JLogFactoryType.type, (XmlOptions)null);
      }

      public static Log4JLogFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(file, Log4JLogFactoryType.type, options);
      }

      public static Log4JLogFactoryType parse(URL u) throws XmlException, IOException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(u, Log4JLogFactoryType.type, (XmlOptions)null);
      }

      public static Log4JLogFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(u, Log4JLogFactoryType.type, options);
      }

      public static Log4JLogFactoryType parse(InputStream is) throws XmlException, IOException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(is, Log4JLogFactoryType.type, (XmlOptions)null);
      }

      public static Log4JLogFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(is, Log4JLogFactoryType.type, options);
      }

      public static Log4JLogFactoryType parse(Reader r) throws XmlException, IOException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(r, Log4JLogFactoryType.type, (XmlOptions)null);
      }

      public static Log4JLogFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(r, Log4JLogFactoryType.type, options);
      }

      public static Log4JLogFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(sr, Log4JLogFactoryType.type, (XmlOptions)null);
      }

      public static Log4JLogFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(sr, Log4JLogFactoryType.type, options);
      }

      public static Log4JLogFactoryType parse(Node node) throws XmlException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(node, Log4JLogFactoryType.type, (XmlOptions)null);
      }

      public static Log4JLogFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(node, Log4JLogFactoryType.type, options);
      }

      /** @deprecated */
      public static Log4JLogFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(xis, Log4JLogFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Log4JLogFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Log4JLogFactoryType)XmlBeans.getContextTypeLoader().parse(xis, Log4JLogFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Log4JLogFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Log4JLogFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
