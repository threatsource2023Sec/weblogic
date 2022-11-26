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

public interface DataCacheManagerImplType extends DataCacheManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DataCacheManagerImplType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("datacachemanagerimpltypea9fetype");

   public static final class Factory {
      public static DataCacheManagerImplType newInstance() {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().newInstance(DataCacheManagerImplType.type, (XmlOptions)null);
      }

      public static DataCacheManagerImplType newInstance(XmlOptions options) {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().newInstance(DataCacheManagerImplType.type, options);
      }

      public static DataCacheManagerImplType parse(String xmlAsString) throws XmlException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DataCacheManagerImplType.type, (XmlOptions)null);
      }

      public static DataCacheManagerImplType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DataCacheManagerImplType.type, options);
      }

      public static DataCacheManagerImplType parse(File file) throws XmlException, IOException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(file, DataCacheManagerImplType.type, (XmlOptions)null);
      }

      public static DataCacheManagerImplType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(file, DataCacheManagerImplType.type, options);
      }

      public static DataCacheManagerImplType parse(URL u) throws XmlException, IOException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(u, DataCacheManagerImplType.type, (XmlOptions)null);
      }

      public static DataCacheManagerImplType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(u, DataCacheManagerImplType.type, options);
      }

      public static DataCacheManagerImplType parse(InputStream is) throws XmlException, IOException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(is, DataCacheManagerImplType.type, (XmlOptions)null);
      }

      public static DataCacheManagerImplType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(is, DataCacheManagerImplType.type, options);
      }

      public static DataCacheManagerImplType parse(Reader r) throws XmlException, IOException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(r, DataCacheManagerImplType.type, (XmlOptions)null);
      }

      public static DataCacheManagerImplType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(r, DataCacheManagerImplType.type, options);
      }

      public static DataCacheManagerImplType parse(XMLStreamReader sr) throws XmlException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(sr, DataCacheManagerImplType.type, (XmlOptions)null);
      }

      public static DataCacheManagerImplType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(sr, DataCacheManagerImplType.type, options);
      }

      public static DataCacheManagerImplType parse(Node node) throws XmlException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(node, DataCacheManagerImplType.type, (XmlOptions)null);
      }

      public static DataCacheManagerImplType parse(Node node, XmlOptions options) throws XmlException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(node, DataCacheManagerImplType.type, options);
      }

      /** @deprecated */
      public static DataCacheManagerImplType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(xis, DataCacheManagerImplType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DataCacheManagerImplType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DataCacheManagerImplType)XmlBeans.getContextTypeLoader().parse(xis, DataCacheManagerImplType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DataCacheManagerImplType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DataCacheManagerImplType.type, options);
      }

      private Factory() {
      }
   }
}
