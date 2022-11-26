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

public interface CacheMapType extends QueryCompilationCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CacheMapType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("cachemaptype387btype");

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
      public static CacheMapType newInstance() {
         return (CacheMapType)XmlBeans.getContextTypeLoader().newInstance(CacheMapType.type, (XmlOptions)null);
      }

      public static CacheMapType newInstance(XmlOptions options) {
         return (CacheMapType)XmlBeans.getContextTypeLoader().newInstance(CacheMapType.type, options);
      }

      public static CacheMapType parse(String xmlAsString) throws XmlException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CacheMapType.type, (XmlOptions)null);
      }

      public static CacheMapType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CacheMapType.type, options);
      }

      public static CacheMapType parse(File file) throws XmlException, IOException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(file, CacheMapType.type, (XmlOptions)null);
      }

      public static CacheMapType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(file, CacheMapType.type, options);
      }

      public static CacheMapType parse(URL u) throws XmlException, IOException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(u, CacheMapType.type, (XmlOptions)null);
      }

      public static CacheMapType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(u, CacheMapType.type, options);
      }

      public static CacheMapType parse(InputStream is) throws XmlException, IOException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(is, CacheMapType.type, (XmlOptions)null);
      }

      public static CacheMapType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(is, CacheMapType.type, options);
      }

      public static CacheMapType parse(Reader r) throws XmlException, IOException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(r, CacheMapType.type, (XmlOptions)null);
      }

      public static CacheMapType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(r, CacheMapType.type, options);
      }

      public static CacheMapType parse(XMLStreamReader sr) throws XmlException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(sr, CacheMapType.type, (XmlOptions)null);
      }

      public static CacheMapType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(sr, CacheMapType.type, options);
      }

      public static CacheMapType parse(Node node) throws XmlException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(node, CacheMapType.type, (XmlOptions)null);
      }

      public static CacheMapType parse(Node node, XmlOptions options) throws XmlException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(node, CacheMapType.type, options);
      }

      /** @deprecated */
      public static CacheMapType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(xis, CacheMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CacheMapType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CacheMapType)XmlBeans.getContextTypeLoader().parse(xis, CacheMapType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CacheMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CacheMapType.type, options);
      }

      private Factory() {
      }
   }
}
