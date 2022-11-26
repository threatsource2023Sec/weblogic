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

public interface ProxyManagerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProxyManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("proxymanagertype419etype");

   public static final class Factory {
      public static ProxyManagerType newInstance() {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().newInstance(ProxyManagerType.type, (XmlOptions)null);
      }

      public static ProxyManagerType newInstance(XmlOptions options) {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().newInstance(ProxyManagerType.type, options);
      }

      public static ProxyManagerType parse(String xmlAsString) throws XmlException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProxyManagerType.type, (XmlOptions)null);
      }

      public static ProxyManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProxyManagerType.type, options);
      }

      public static ProxyManagerType parse(File file) throws XmlException, IOException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(file, ProxyManagerType.type, (XmlOptions)null);
      }

      public static ProxyManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(file, ProxyManagerType.type, options);
      }

      public static ProxyManagerType parse(URL u) throws XmlException, IOException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(u, ProxyManagerType.type, (XmlOptions)null);
      }

      public static ProxyManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(u, ProxyManagerType.type, options);
      }

      public static ProxyManagerType parse(InputStream is) throws XmlException, IOException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(is, ProxyManagerType.type, (XmlOptions)null);
      }

      public static ProxyManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(is, ProxyManagerType.type, options);
      }

      public static ProxyManagerType parse(Reader r) throws XmlException, IOException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(r, ProxyManagerType.type, (XmlOptions)null);
      }

      public static ProxyManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(r, ProxyManagerType.type, options);
      }

      public static ProxyManagerType parse(XMLStreamReader sr) throws XmlException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(sr, ProxyManagerType.type, (XmlOptions)null);
      }

      public static ProxyManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(sr, ProxyManagerType.type, options);
      }

      public static ProxyManagerType parse(Node node) throws XmlException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(node, ProxyManagerType.type, (XmlOptions)null);
      }

      public static ProxyManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(node, ProxyManagerType.type, options);
      }

      /** @deprecated */
      public static ProxyManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(xis, ProxyManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProxyManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProxyManagerType)XmlBeans.getContextTypeLoader().parse(xis, ProxyManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProxyManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProxyManagerType.type, options);
      }

      private Factory() {
      }
   }
}
