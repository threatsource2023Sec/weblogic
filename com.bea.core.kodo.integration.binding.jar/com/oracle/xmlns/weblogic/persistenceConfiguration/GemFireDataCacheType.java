package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface GemFireDataCacheType extends DataCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(GemFireDataCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("gemfiredatacachetype97d4type");

   String getGemFireCacheName();

   XmlString xgetGemFireCacheName();

   boolean isNilGemFireCacheName();

   boolean isSetGemFireCacheName();

   void setGemFireCacheName(String var1);

   void xsetGemFireCacheName(XmlString var1);

   void setNilGemFireCacheName();

   void unsetGemFireCacheName();

   String getEvictionSchedule();

   XmlString xgetEvictionSchedule();

   boolean isNilEvictionSchedule();

   boolean isSetEvictionSchedule();

   void setEvictionSchedule(String var1);

   void xsetEvictionSchedule(XmlString var1);

   void setNilEvictionSchedule();

   void unsetEvictionSchedule();

   public static final class Factory {
      public static GemFireDataCacheType newInstance() {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().newInstance(GemFireDataCacheType.type, (XmlOptions)null);
      }

      public static GemFireDataCacheType newInstance(XmlOptions options) {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().newInstance(GemFireDataCacheType.type, options);
      }

      public static GemFireDataCacheType parse(String xmlAsString) throws XmlException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GemFireDataCacheType.type, (XmlOptions)null);
      }

      public static GemFireDataCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GemFireDataCacheType.type, options);
      }

      public static GemFireDataCacheType parse(File file) throws XmlException, IOException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(file, GemFireDataCacheType.type, (XmlOptions)null);
      }

      public static GemFireDataCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(file, GemFireDataCacheType.type, options);
      }

      public static GemFireDataCacheType parse(URL u) throws XmlException, IOException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(u, GemFireDataCacheType.type, (XmlOptions)null);
      }

      public static GemFireDataCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(u, GemFireDataCacheType.type, options);
      }

      public static GemFireDataCacheType parse(InputStream is) throws XmlException, IOException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(is, GemFireDataCacheType.type, (XmlOptions)null);
      }

      public static GemFireDataCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(is, GemFireDataCacheType.type, options);
      }

      public static GemFireDataCacheType parse(Reader r) throws XmlException, IOException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(r, GemFireDataCacheType.type, (XmlOptions)null);
      }

      public static GemFireDataCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(r, GemFireDataCacheType.type, options);
      }

      public static GemFireDataCacheType parse(XMLStreamReader sr) throws XmlException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(sr, GemFireDataCacheType.type, (XmlOptions)null);
      }

      public static GemFireDataCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(sr, GemFireDataCacheType.type, options);
      }

      public static GemFireDataCacheType parse(Node node) throws XmlException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(node, GemFireDataCacheType.type, (XmlOptions)null);
      }

      public static GemFireDataCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(node, GemFireDataCacheType.type, options);
      }

      /** @deprecated */
      public static GemFireDataCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(xis, GemFireDataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static GemFireDataCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (GemFireDataCacheType)XmlBeans.getContextTypeLoader().parse(xis, GemFireDataCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GemFireDataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GemFireDataCacheType.type, options);
      }

      private Factory() {
      }
   }
}
