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

public interface DefaultQueryCacheType extends QueryCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultQueryCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultquerycachetypec91btype");

   public static final class Factory {
      public static DefaultQueryCacheType newInstance() {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(DefaultQueryCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCacheType newInstance(XmlOptions options) {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(DefaultQueryCacheType.type, options);
      }

      public static DefaultQueryCacheType parse(String xmlAsString) throws XmlException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultQueryCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultQueryCacheType.type, options);
      }

      public static DefaultQueryCacheType parse(File file) throws XmlException, IOException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, DefaultQueryCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, DefaultQueryCacheType.type, options);
      }

      public static DefaultQueryCacheType parse(URL u) throws XmlException, IOException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, DefaultQueryCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, DefaultQueryCacheType.type, options);
      }

      public static DefaultQueryCacheType parse(InputStream is) throws XmlException, IOException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, DefaultQueryCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, DefaultQueryCacheType.type, options);
      }

      public static DefaultQueryCacheType parse(Reader r) throws XmlException, IOException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, DefaultQueryCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, DefaultQueryCacheType.type, options);
      }

      public static DefaultQueryCacheType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, DefaultQueryCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, DefaultQueryCacheType.type, options);
      }

      public static DefaultQueryCacheType parse(Node node) throws XmlException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, DefaultQueryCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, DefaultQueryCacheType.type, options);
      }

      /** @deprecated */
      public static DefaultQueryCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, DefaultQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultQueryCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, DefaultQueryCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultQueryCacheType.type, options);
      }

      private Factory() {
      }
   }
}
