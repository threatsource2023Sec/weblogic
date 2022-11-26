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

public interface DefaultSqlFactoryType extends SqlFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultSqlFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultsqlfactorytype498dtype");

   public static final class Factory {
      public static DefaultSqlFactoryType newInstance() {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().newInstance(DefaultSqlFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSqlFactoryType newInstance(XmlOptions options) {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().newInstance(DefaultSqlFactoryType.type, options);
      }

      public static DefaultSqlFactoryType parse(String xmlAsString) throws XmlException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultSqlFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSqlFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultSqlFactoryType.type, options);
      }

      public static DefaultSqlFactoryType parse(File file) throws XmlException, IOException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(file, DefaultSqlFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSqlFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(file, DefaultSqlFactoryType.type, options);
      }

      public static DefaultSqlFactoryType parse(URL u) throws XmlException, IOException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(u, DefaultSqlFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSqlFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(u, DefaultSqlFactoryType.type, options);
      }

      public static DefaultSqlFactoryType parse(InputStream is) throws XmlException, IOException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(is, DefaultSqlFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSqlFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(is, DefaultSqlFactoryType.type, options);
      }

      public static DefaultSqlFactoryType parse(Reader r) throws XmlException, IOException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(r, DefaultSqlFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSqlFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(r, DefaultSqlFactoryType.type, options);
      }

      public static DefaultSqlFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(sr, DefaultSqlFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSqlFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(sr, DefaultSqlFactoryType.type, options);
      }

      public static DefaultSqlFactoryType parse(Node node) throws XmlException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(node, DefaultSqlFactoryType.type, (XmlOptions)null);
      }

      public static DefaultSqlFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(node, DefaultSqlFactoryType.type, options);
      }

      /** @deprecated */
      public static DefaultSqlFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(xis, DefaultSqlFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultSqlFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultSqlFactoryType)XmlBeans.getContextTypeLoader().parse(xis, DefaultSqlFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultSqlFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultSqlFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
