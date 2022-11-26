package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface KodoConcurrentQueryCacheType extends QueryCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KodoConcurrentQueryCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("kodoconcurrentquerycachetype2c87type");

   int getCacheSize();

   XmlInt xgetCacheSize();

   boolean isSetCacheSize();

   void setCacheSize(int var1);

   void xsetCacheSize(XmlInt var1);

   void unsetCacheSize();

   int getSoftReferenceSize();

   XmlInt xgetSoftReferenceSize();

   boolean isSetSoftReferenceSize();

   void setSoftReferenceSize(int var1);

   void xsetSoftReferenceSize(XmlInt var1);

   void unsetSoftReferenceSize();

   public static final class Factory {
      public static KodoConcurrentQueryCacheType newInstance() {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(KodoConcurrentQueryCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentQueryCacheType newInstance(XmlOptions options) {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(KodoConcurrentQueryCacheType.type, options);
      }

      public static KodoConcurrentQueryCacheType parse(String xmlAsString) throws XmlException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoConcurrentQueryCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentQueryCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoConcurrentQueryCacheType.type, options);
      }

      public static KodoConcurrentQueryCacheType parse(File file) throws XmlException, IOException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, KodoConcurrentQueryCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentQueryCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, KodoConcurrentQueryCacheType.type, options);
      }

      public static KodoConcurrentQueryCacheType parse(URL u) throws XmlException, IOException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, KodoConcurrentQueryCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentQueryCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, KodoConcurrentQueryCacheType.type, options);
      }

      public static KodoConcurrentQueryCacheType parse(InputStream is) throws XmlException, IOException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, KodoConcurrentQueryCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentQueryCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, KodoConcurrentQueryCacheType.type, options);
      }

      public static KodoConcurrentQueryCacheType parse(Reader r) throws XmlException, IOException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, KodoConcurrentQueryCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentQueryCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, KodoConcurrentQueryCacheType.type, options);
      }

      public static KodoConcurrentQueryCacheType parse(XMLStreamReader sr) throws XmlException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, KodoConcurrentQueryCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentQueryCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, KodoConcurrentQueryCacheType.type, options);
      }

      public static KodoConcurrentQueryCacheType parse(Node node) throws XmlException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, KodoConcurrentQueryCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentQueryCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, KodoConcurrentQueryCacheType.type, options);
      }

      /** @deprecated */
      public static KodoConcurrentQueryCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, KodoConcurrentQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KodoConcurrentQueryCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KodoConcurrentQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, KodoConcurrentQueryCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoConcurrentQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoConcurrentQueryCacheType.type, options);
      }

      private Factory() {
      }
   }
}
