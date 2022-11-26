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

public interface KodoConcurrentDataCacheType extends DataCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KodoConcurrentDataCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("kodoconcurrentdatacachetypee983type");

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
      public static KodoConcurrentDataCacheType newInstance() {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().newInstance(KodoConcurrentDataCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentDataCacheType newInstance(XmlOptions options) {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().newInstance(KodoConcurrentDataCacheType.type, options);
      }

      public static KodoConcurrentDataCacheType parse(String xmlAsString) throws XmlException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoConcurrentDataCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentDataCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoConcurrentDataCacheType.type, options);
      }

      public static KodoConcurrentDataCacheType parse(File file) throws XmlException, IOException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(file, KodoConcurrentDataCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentDataCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(file, KodoConcurrentDataCacheType.type, options);
      }

      public static KodoConcurrentDataCacheType parse(URL u) throws XmlException, IOException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(u, KodoConcurrentDataCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentDataCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(u, KodoConcurrentDataCacheType.type, options);
      }

      public static KodoConcurrentDataCacheType parse(InputStream is) throws XmlException, IOException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(is, KodoConcurrentDataCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentDataCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(is, KodoConcurrentDataCacheType.type, options);
      }

      public static KodoConcurrentDataCacheType parse(Reader r) throws XmlException, IOException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(r, KodoConcurrentDataCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentDataCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(r, KodoConcurrentDataCacheType.type, options);
      }

      public static KodoConcurrentDataCacheType parse(XMLStreamReader sr) throws XmlException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(sr, KodoConcurrentDataCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentDataCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(sr, KodoConcurrentDataCacheType.type, options);
      }

      public static KodoConcurrentDataCacheType parse(Node node) throws XmlException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(node, KodoConcurrentDataCacheType.type, (XmlOptions)null);
      }

      public static KodoConcurrentDataCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(node, KodoConcurrentDataCacheType.type, options);
      }

      /** @deprecated */
      public static KodoConcurrentDataCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(xis, KodoConcurrentDataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KodoConcurrentDataCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KodoConcurrentDataCacheType)XmlBeans.getContextTypeLoader().parse(xis, KodoConcurrentDataCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoConcurrentDataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoConcurrentDataCacheType.type, options);
      }

      private Factory() {
      }
   }
}
