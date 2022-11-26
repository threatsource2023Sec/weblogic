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

public interface DefaultDataCacheManagerType extends DataCacheManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultDataCacheManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultdatacachemanagertype2f8ftype");

   public static final class Factory {
      public static DefaultDataCacheManagerType newInstance() {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().newInstance(DefaultDataCacheManagerType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheManagerType newInstance(XmlOptions options) {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().newInstance(DefaultDataCacheManagerType.type, options);
      }

      public static DefaultDataCacheManagerType parse(String xmlAsString) throws XmlException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultDataCacheManagerType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultDataCacheManagerType.type, options);
      }

      public static DefaultDataCacheManagerType parse(File file) throws XmlException, IOException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(file, DefaultDataCacheManagerType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(file, DefaultDataCacheManagerType.type, options);
      }

      public static DefaultDataCacheManagerType parse(URL u) throws XmlException, IOException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(u, DefaultDataCacheManagerType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(u, DefaultDataCacheManagerType.type, options);
      }

      public static DefaultDataCacheManagerType parse(InputStream is) throws XmlException, IOException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(is, DefaultDataCacheManagerType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(is, DefaultDataCacheManagerType.type, options);
      }

      public static DefaultDataCacheManagerType parse(Reader r) throws XmlException, IOException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(r, DefaultDataCacheManagerType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(r, DefaultDataCacheManagerType.type, options);
      }

      public static DefaultDataCacheManagerType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(sr, DefaultDataCacheManagerType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(sr, DefaultDataCacheManagerType.type, options);
      }

      public static DefaultDataCacheManagerType parse(Node node) throws XmlException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(node, DefaultDataCacheManagerType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(node, DefaultDataCacheManagerType.type, options);
      }

      /** @deprecated */
      public static DefaultDataCacheManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xis, DefaultDataCacheManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultDataCacheManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xis, DefaultDataCacheManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultDataCacheManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultDataCacheManagerType.type, options);
      }

      private Factory() {
      }
   }
}
