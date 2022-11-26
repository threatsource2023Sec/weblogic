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

public interface DataCacheManagerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DataCacheManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("datacachemanagertype8f1btype");

   public static final class Factory {
      public static DataCacheManagerType newInstance() {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().newInstance(DataCacheManagerType.type, (XmlOptions)null);
      }

      public static DataCacheManagerType newInstance(XmlOptions options) {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().newInstance(DataCacheManagerType.type, options);
      }

      public static DataCacheManagerType parse(String xmlAsString) throws XmlException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DataCacheManagerType.type, (XmlOptions)null);
      }

      public static DataCacheManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DataCacheManagerType.type, options);
      }

      public static DataCacheManagerType parse(File file) throws XmlException, IOException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(file, DataCacheManagerType.type, (XmlOptions)null);
      }

      public static DataCacheManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(file, DataCacheManagerType.type, options);
      }

      public static DataCacheManagerType parse(URL u) throws XmlException, IOException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(u, DataCacheManagerType.type, (XmlOptions)null);
      }

      public static DataCacheManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(u, DataCacheManagerType.type, options);
      }

      public static DataCacheManagerType parse(InputStream is) throws XmlException, IOException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(is, DataCacheManagerType.type, (XmlOptions)null);
      }

      public static DataCacheManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(is, DataCacheManagerType.type, options);
      }

      public static DataCacheManagerType parse(Reader r) throws XmlException, IOException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(r, DataCacheManagerType.type, (XmlOptions)null);
      }

      public static DataCacheManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(r, DataCacheManagerType.type, options);
      }

      public static DataCacheManagerType parse(XMLStreamReader sr) throws XmlException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(sr, DataCacheManagerType.type, (XmlOptions)null);
      }

      public static DataCacheManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(sr, DataCacheManagerType.type, options);
      }

      public static DataCacheManagerType parse(Node node) throws XmlException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(node, DataCacheManagerType.type, (XmlOptions)null);
      }

      public static DataCacheManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(node, DataCacheManagerType.type, options);
      }

      /** @deprecated */
      public static DataCacheManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xis, DataCacheManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DataCacheManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xis, DataCacheManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DataCacheManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DataCacheManagerType.type, options);
      }

      private Factory() {
      }
   }
}
