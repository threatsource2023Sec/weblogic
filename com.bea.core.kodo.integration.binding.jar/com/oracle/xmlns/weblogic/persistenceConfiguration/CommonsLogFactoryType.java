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

public interface CommonsLogFactoryType extends LogType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CommonsLogFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("commonslogfactorytype5f50type");

   public static final class Factory {
      public static CommonsLogFactoryType newInstance() {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().newInstance(CommonsLogFactoryType.type, (XmlOptions)null);
      }

      public static CommonsLogFactoryType newInstance(XmlOptions options) {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().newInstance(CommonsLogFactoryType.type, options);
      }

      public static CommonsLogFactoryType parse(String xmlAsString) throws XmlException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CommonsLogFactoryType.type, (XmlOptions)null);
      }

      public static CommonsLogFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CommonsLogFactoryType.type, options);
      }

      public static CommonsLogFactoryType parse(File file) throws XmlException, IOException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(file, CommonsLogFactoryType.type, (XmlOptions)null);
      }

      public static CommonsLogFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(file, CommonsLogFactoryType.type, options);
      }

      public static CommonsLogFactoryType parse(URL u) throws XmlException, IOException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(u, CommonsLogFactoryType.type, (XmlOptions)null);
      }

      public static CommonsLogFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(u, CommonsLogFactoryType.type, options);
      }

      public static CommonsLogFactoryType parse(InputStream is) throws XmlException, IOException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(is, CommonsLogFactoryType.type, (XmlOptions)null);
      }

      public static CommonsLogFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(is, CommonsLogFactoryType.type, options);
      }

      public static CommonsLogFactoryType parse(Reader r) throws XmlException, IOException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(r, CommonsLogFactoryType.type, (XmlOptions)null);
      }

      public static CommonsLogFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(r, CommonsLogFactoryType.type, options);
      }

      public static CommonsLogFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(sr, CommonsLogFactoryType.type, (XmlOptions)null);
      }

      public static CommonsLogFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(sr, CommonsLogFactoryType.type, options);
      }

      public static CommonsLogFactoryType parse(Node node) throws XmlException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(node, CommonsLogFactoryType.type, (XmlOptions)null);
      }

      public static CommonsLogFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(node, CommonsLogFactoryType.type, options);
      }

      /** @deprecated */
      public static CommonsLogFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(xis, CommonsLogFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CommonsLogFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CommonsLogFactoryType)XmlBeans.getContextTypeLoader().parse(xis, CommonsLogFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CommonsLogFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CommonsLogFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
