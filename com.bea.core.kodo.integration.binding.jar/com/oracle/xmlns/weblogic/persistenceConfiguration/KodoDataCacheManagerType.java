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

public interface KodoDataCacheManagerType extends DataCacheManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KodoDataCacheManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("kododatacachemanagertypeb2aftype");

   public static final class Factory {
      public static KodoDataCacheManagerType newInstance() {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().newInstance(KodoDataCacheManagerType.type, (XmlOptions)null);
      }

      public static KodoDataCacheManagerType newInstance(XmlOptions options) {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().newInstance(KodoDataCacheManagerType.type, options);
      }

      public static KodoDataCacheManagerType parse(String xmlAsString) throws XmlException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoDataCacheManagerType.type, (XmlOptions)null);
      }

      public static KodoDataCacheManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoDataCacheManagerType.type, options);
      }

      public static KodoDataCacheManagerType parse(File file) throws XmlException, IOException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(file, KodoDataCacheManagerType.type, (XmlOptions)null);
      }

      public static KodoDataCacheManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(file, KodoDataCacheManagerType.type, options);
      }

      public static KodoDataCacheManagerType parse(URL u) throws XmlException, IOException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(u, KodoDataCacheManagerType.type, (XmlOptions)null);
      }

      public static KodoDataCacheManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(u, KodoDataCacheManagerType.type, options);
      }

      public static KodoDataCacheManagerType parse(InputStream is) throws XmlException, IOException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(is, KodoDataCacheManagerType.type, (XmlOptions)null);
      }

      public static KodoDataCacheManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(is, KodoDataCacheManagerType.type, options);
      }

      public static KodoDataCacheManagerType parse(Reader r) throws XmlException, IOException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(r, KodoDataCacheManagerType.type, (XmlOptions)null);
      }

      public static KodoDataCacheManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(r, KodoDataCacheManagerType.type, options);
      }

      public static KodoDataCacheManagerType parse(XMLStreamReader sr) throws XmlException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(sr, KodoDataCacheManagerType.type, (XmlOptions)null);
      }

      public static KodoDataCacheManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(sr, KodoDataCacheManagerType.type, options);
      }

      public static KodoDataCacheManagerType parse(Node node) throws XmlException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(node, KodoDataCacheManagerType.type, (XmlOptions)null);
      }

      public static KodoDataCacheManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(node, KodoDataCacheManagerType.type, options);
      }

      /** @deprecated */
      public static KodoDataCacheManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xis, KodoDataCacheManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KodoDataCacheManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KodoDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xis, KodoDataCacheManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoDataCacheManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoDataCacheManagerType.type, options);
      }

      private Factory() {
      }
   }
}
