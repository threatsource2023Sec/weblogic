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

public interface DisabledQueryCacheType extends QueryCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DisabledQueryCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("disabledquerycachetypeeacetype");

   public static final class Factory {
      public static DisabledQueryCacheType newInstance() {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(DisabledQueryCacheType.type, (XmlOptions)null);
      }

      public static DisabledQueryCacheType newInstance(XmlOptions options) {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(DisabledQueryCacheType.type, options);
      }

      public static DisabledQueryCacheType parse(String xmlAsString) throws XmlException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DisabledQueryCacheType.type, (XmlOptions)null);
      }

      public static DisabledQueryCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DisabledQueryCacheType.type, options);
      }

      public static DisabledQueryCacheType parse(File file) throws XmlException, IOException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, DisabledQueryCacheType.type, (XmlOptions)null);
      }

      public static DisabledQueryCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, DisabledQueryCacheType.type, options);
      }

      public static DisabledQueryCacheType parse(URL u) throws XmlException, IOException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, DisabledQueryCacheType.type, (XmlOptions)null);
      }

      public static DisabledQueryCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, DisabledQueryCacheType.type, options);
      }

      public static DisabledQueryCacheType parse(InputStream is) throws XmlException, IOException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, DisabledQueryCacheType.type, (XmlOptions)null);
      }

      public static DisabledQueryCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, DisabledQueryCacheType.type, options);
      }

      public static DisabledQueryCacheType parse(Reader r) throws XmlException, IOException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, DisabledQueryCacheType.type, (XmlOptions)null);
      }

      public static DisabledQueryCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, DisabledQueryCacheType.type, options);
      }

      public static DisabledQueryCacheType parse(XMLStreamReader sr) throws XmlException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, DisabledQueryCacheType.type, (XmlOptions)null);
      }

      public static DisabledQueryCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, DisabledQueryCacheType.type, options);
      }

      public static DisabledQueryCacheType parse(Node node) throws XmlException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, DisabledQueryCacheType.type, (XmlOptions)null);
      }

      public static DisabledQueryCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, DisabledQueryCacheType.type, options);
      }

      /** @deprecated */
      public static DisabledQueryCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, DisabledQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DisabledQueryCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DisabledQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, DisabledQueryCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DisabledQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DisabledQueryCacheType.type, options);
      }

      private Factory() {
      }
   }
}
