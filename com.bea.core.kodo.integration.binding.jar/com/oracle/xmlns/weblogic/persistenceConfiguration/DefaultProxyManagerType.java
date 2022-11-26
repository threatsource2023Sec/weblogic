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

public interface DefaultProxyManagerType extends ProxyManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultProxyManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultproxymanagertype28aatype");

   public static final class Factory {
      public static DefaultProxyManagerType newInstance() {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().newInstance(DefaultProxyManagerType.type, (XmlOptions)null);
      }

      public static DefaultProxyManagerType newInstance(XmlOptions options) {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().newInstance(DefaultProxyManagerType.type, options);
      }

      public static DefaultProxyManagerType parse(String xmlAsString) throws XmlException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultProxyManagerType.type, (XmlOptions)null);
      }

      public static DefaultProxyManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultProxyManagerType.type, options);
      }

      public static DefaultProxyManagerType parse(File file) throws XmlException, IOException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(file, DefaultProxyManagerType.type, (XmlOptions)null);
      }

      public static DefaultProxyManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(file, DefaultProxyManagerType.type, options);
      }

      public static DefaultProxyManagerType parse(URL u) throws XmlException, IOException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(u, DefaultProxyManagerType.type, (XmlOptions)null);
      }

      public static DefaultProxyManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(u, DefaultProxyManagerType.type, options);
      }

      public static DefaultProxyManagerType parse(InputStream is) throws XmlException, IOException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(is, DefaultProxyManagerType.type, (XmlOptions)null);
      }

      public static DefaultProxyManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(is, DefaultProxyManagerType.type, options);
      }

      public static DefaultProxyManagerType parse(Reader r) throws XmlException, IOException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(r, DefaultProxyManagerType.type, (XmlOptions)null);
      }

      public static DefaultProxyManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(r, DefaultProxyManagerType.type, options);
      }

      public static DefaultProxyManagerType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(sr, DefaultProxyManagerType.type, (XmlOptions)null);
      }

      public static DefaultProxyManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(sr, DefaultProxyManagerType.type, options);
      }

      public static DefaultProxyManagerType parse(Node node) throws XmlException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(node, DefaultProxyManagerType.type, (XmlOptions)null);
      }

      public static DefaultProxyManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(node, DefaultProxyManagerType.type, options);
      }

      /** @deprecated */
      public static DefaultProxyManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(xis, DefaultProxyManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultProxyManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultProxyManagerType)XmlBeans.getContextTypeLoader().parse(xis, DefaultProxyManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultProxyManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultProxyManagerType.type, options);
      }

      private Factory() {
      }
   }
}
