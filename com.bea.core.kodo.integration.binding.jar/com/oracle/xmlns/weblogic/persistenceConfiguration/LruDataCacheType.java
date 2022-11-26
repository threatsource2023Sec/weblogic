package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface LruDataCacheType extends DataCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LruDataCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("lrudatacachetype49ddtype");

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

   String getEvictionSchedule();

   XmlString xgetEvictionSchedule();

   boolean isNilEvictionSchedule();

   boolean isSetEvictionSchedule();

   void setEvictionSchedule(String var1);

   void xsetEvictionSchedule(XmlString var1);

   void setNilEvictionSchedule();

   void unsetEvictionSchedule();

   public static final class Factory {
      public static LruDataCacheType newInstance() {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().newInstance(LruDataCacheType.type, (XmlOptions)null);
      }

      public static LruDataCacheType newInstance(XmlOptions options) {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().newInstance(LruDataCacheType.type, options);
      }

      public static LruDataCacheType parse(String xmlAsString) throws XmlException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LruDataCacheType.type, (XmlOptions)null);
      }

      public static LruDataCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LruDataCacheType.type, options);
      }

      public static LruDataCacheType parse(File file) throws XmlException, IOException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(file, LruDataCacheType.type, (XmlOptions)null);
      }

      public static LruDataCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(file, LruDataCacheType.type, options);
      }

      public static LruDataCacheType parse(URL u) throws XmlException, IOException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(u, LruDataCacheType.type, (XmlOptions)null);
      }

      public static LruDataCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(u, LruDataCacheType.type, options);
      }

      public static LruDataCacheType parse(InputStream is) throws XmlException, IOException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(is, LruDataCacheType.type, (XmlOptions)null);
      }

      public static LruDataCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(is, LruDataCacheType.type, options);
      }

      public static LruDataCacheType parse(Reader r) throws XmlException, IOException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(r, LruDataCacheType.type, (XmlOptions)null);
      }

      public static LruDataCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(r, LruDataCacheType.type, options);
      }

      public static LruDataCacheType parse(XMLStreamReader sr) throws XmlException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(sr, LruDataCacheType.type, (XmlOptions)null);
      }

      public static LruDataCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(sr, LruDataCacheType.type, options);
      }

      public static LruDataCacheType parse(Node node) throws XmlException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(node, LruDataCacheType.type, (XmlOptions)null);
      }

      public static LruDataCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(node, LruDataCacheType.type, options);
      }

      /** @deprecated */
      public static LruDataCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(xis, LruDataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LruDataCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LruDataCacheType)XmlBeans.getContextTypeLoader().parse(xis, LruDataCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LruDataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LruDataCacheType.type, options);
      }

      private Factory() {
      }
   }
}
