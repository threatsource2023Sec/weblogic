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

public interface LruQueryCacheType extends QueryCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LruQueryCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("lruquerycachetyped76dtype");

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
      public static LruQueryCacheType newInstance() {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(LruQueryCacheType.type, (XmlOptions)null);
      }

      public static LruQueryCacheType newInstance(XmlOptions options) {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(LruQueryCacheType.type, options);
      }

      public static LruQueryCacheType parse(String xmlAsString) throws XmlException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LruQueryCacheType.type, (XmlOptions)null);
      }

      public static LruQueryCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LruQueryCacheType.type, options);
      }

      public static LruQueryCacheType parse(File file) throws XmlException, IOException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, LruQueryCacheType.type, (XmlOptions)null);
      }

      public static LruQueryCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, LruQueryCacheType.type, options);
      }

      public static LruQueryCacheType parse(URL u) throws XmlException, IOException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, LruQueryCacheType.type, (XmlOptions)null);
      }

      public static LruQueryCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, LruQueryCacheType.type, options);
      }

      public static LruQueryCacheType parse(InputStream is) throws XmlException, IOException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, LruQueryCacheType.type, (XmlOptions)null);
      }

      public static LruQueryCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, LruQueryCacheType.type, options);
      }

      public static LruQueryCacheType parse(Reader r) throws XmlException, IOException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, LruQueryCacheType.type, (XmlOptions)null);
      }

      public static LruQueryCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, LruQueryCacheType.type, options);
      }

      public static LruQueryCacheType parse(XMLStreamReader sr) throws XmlException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, LruQueryCacheType.type, (XmlOptions)null);
      }

      public static LruQueryCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, LruQueryCacheType.type, options);
      }

      public static LruQueryCacheType parse(Node node) throws XmlException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, LruQueryCacheType.type, (XmlOptions)null);
      }

      public static LruQueryCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, LruQueryCacheType.type, options);
      }

      /** @deprecated */
      public static LruQueryCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, LruQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LruQueryCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LruQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, LruQueryCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LruQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LruQueryCacheType.type, options);
      }

      private Factory() {
      }
   }
}
