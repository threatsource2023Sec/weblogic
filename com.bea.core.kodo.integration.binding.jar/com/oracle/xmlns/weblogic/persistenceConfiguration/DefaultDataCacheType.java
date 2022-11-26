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

public interface DefaultDataCacheType extends DataCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultDataCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultdatacachetype6a6ftype");

   public static final class Factory {
      public static DefaultDataCacheType newInstance() {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().newInstance(DefaultDataCacheType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheType newInstance(XmlOptions options) {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().newInstance(DefaultDataCacheType.type, options);
      }

      public static DefaultDataCacheType parse(String xmlAsString) throws XmlException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultDataCacheType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultDataCacheType.type, options);
      }

      public static DefaultDataCacheType parse(File file) throws XmlException, IOException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(file, DefaultDataCacheType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(file, DefaultDataCacheType.type, options);
      }

      public static DefaultDataCacheType parse(URL u) throws XmlException, IOException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(u, DefaultDataCacheType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(u, DefaultDataCacheType.type, options);
      }

      public static DefaultDataCacheType parse(InputStream is) throws XmlException, IOException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(is, DefaultDataCacheType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(is, DefaultDataCacheType.type, options);
      }

      public static DefaultDataCacheType parse(Reader r) throws XmlException, IOException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(r, DefaultDataCacheType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(r, DefaultDataCacheType.type, options);
      }

      public static DefaultDataCacheType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(sr, DefaultDataCacheType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(sr, DefaultDataCacheType.type, options);
      }

      public static DefaultDataCacheType parse(Node node) throws XmlException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(node, DefaultDataCacheType.type, (XmlOptions)null);
      }

      public static DefaultDataCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(node, DefaultDataCacheType.type, options);
      }

      /** @deprecated */
      public static DefaultDataCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(xis, DefaultDataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultDataCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultDataCacheType)XmlBeans.getContextTypeLoader().parse(xis, DefaultDataCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultDataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultDataCacheType.type, options);
      }

      private Factory() {
      }
   }
}
