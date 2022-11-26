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

public interface DefaultDriverDataSourceType extends DriverDataSourceType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultDriverDataSourceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultdriverdatasourcetype8a55type");

   public static final class Factory {
      public static DefaultDriverDataSourceType newInstance() {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().newInstance(DefaultDriverDataSourceType.type, (XmlOptions)null);
      }

      public static DefaultDriverDataSourceType newInstance(XmlOptions options) {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().newInstance(DefaultDriverDataSourceType.type, options);
      }

      public static DefaultDriverDataSourceType parse(String xmlAsString) throws XmlException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultDriverDataSourceType.type, (XmlOptions)null);
      }

      public static DefaultDriverDataSourceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultDriverDataSourceType.type, options);
      }

      public static DefaultDriverDataSourceType parse(File file) throws XmlException, IOException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(file, DefaultDriverDataSourceType.type, (XmlOptions)null);
      }

      public static DefaultDriverDataSourceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(file, DefaultDriverDataSourceType.type, options);
      }

      public static DefaultDriverDataSourceType parse(URL u) throws XmlException, IOException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(u, DefaultDriverDataSourceType.type, (XmlOptions)null);
      }

      public static DefaultDriverDataSourceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(u, DefaultDriverDataSourceType.type, options);
      }

      public static DefaultDriverDataSourceType parse(InputStream is) throws XmlException, IOException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(is, DefaultDriverDataSourceType.type, (XmlOptions)null);
      }

      public static DefaultDriverDataSourceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(is, DefaultDriverDataSourceType.type, options);
      }

      public static DefaultDriverDataSourceType parse(Reader r) throws XmlException, IOException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(r, DefaultDriverDataSourceType.type, (XmlOptions)null);
      }

      public static DefaultDriverDataSourceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(r, DefaultDriverDataSourceType.type, options);
      }

      public static DefaultDriverDataSourceType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(sr, DefaultDriverDataSourceType.type, (XmlOptions)null);
      }

      public static DefaultDriverDataSourceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(sr, DefaultDriverDataSourceType.type, options);
      }

      public static DefaultDriverDataSourceType parse(Node node) throws XmlException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(node, DefaultDriverDataSourceType.type, (XmlOptions)null);
      }

      public static DefaultDriverDataSourceType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(node, DefaultDriverDataSourceType.type, options);
      }

      /** @deprecated */
      public static DefaultDriverDataSourceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xis, DefaultDriverDataSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultDriverDataSourceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xis, DefaultDriverDataSourceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultDriverDataSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultDriverDataSourceType.type, options);
      }

      private Factory() {
      }
   }
}
