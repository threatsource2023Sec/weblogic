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

public interface QueryCachesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(QueryCachesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("querycachestype4324type");

   DefaultQueryCacheType getDefaultQueryCache();

   boolean isNilDefaultQueryCache();

   boolean isSetDefaultQueryCache();

   void setDefaultQueryCache(DefaultQueryCacheType var1);

   DefaultQueryCacheType addNewDefaultQueryCache();

   void setNilDefaultQueryCache();

   void unsetDefaultQueryCache();

   KodoConcurrentQueryCacheType getKodoConcurrentQueryCache();

   boolean isNilKodoConcurrentQueryCache();

   boolean isSetKodoConcurrentQueryCache();

   void setKodoConcurrentQueryCache(KodoConcurrentQueryCacheType var1);

   KodoConcurrentQueryCacheType addNewKodoConcurrentQueryCache();

   void setNilKodoConcurrentQueryCache();

   void unsetKodoConcurrentQueryCache();

   GemFireQueryCacheType getGemFireQueryCache();

   boolean isNilGemFireQueryCache();

   boolean isSetGemFireQueryCache();

   void setGemFireQueryCache(GemFireQueryCacheType var1);

   GemFireQueryCacheType addNewGemFireQueryCache();

   void setNilGemFireQueryCache();

   void unsetGemFireQueryCache();

   LruQueryCacheType getLruQueryCache();

   boolean isNilLruQueryCache();

   boolean isSetLruQueryCache();

   void setLruQueryCache(LruQueryCacheType var1);

   LruQueryCacheType addNewLruQueryCache();

   void setNilLruQueryCache();

   void unsetLruQueryCache();

   TangosolQueryCacheType getTangosolQueryCache();

   boolean isNilTangosolQueryCache();

   boolean isSetTangosolQueryCache();

   void setTangosolQueryCache(TangosolQueryCacheType var1);

   TangosolQueryCacheType addNewTangosolQueryCache();

   void setNilTangosolQueryCache();

   void unsetTangosolQueryCache();

   DisabledQueryCacheType getDisabledQueryCache();

   boolean isNilDisabledQueryCache();

   boolean isSetDisabledQueryCache();

   void setDisabledQueryCache(DisabledQueryCacheType var1);

   DisabledQueryCacheType addNewDisabledQueryCache();

   void setNilDisabledQueryCache();

   void unsetDisabledQueryCache();

   CustomQueryCacheType getCustomQueryCache();

   boolean isNilCustomQueryCache();

   boolean isSetCustomQueryCache();

   void setCustomQueryCache(CustomQueryCacheType var1);

   CustomQueryCacheType addNewCustomQueryCache();

   void setNilCustomQueryCache();

   void unsetCustomQueryCache();

   public static final class Factory {
      public static QueryCachesType newInstance() {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().newInstance(QueryCachesType.type, (XmlOptions)null);
      }

      public static QueryCachesType newInstance(XmlOptions options) {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().newInstance(QueryCachesType.type, options);
      }

      public static QueryCachesType parse(String xmlAsString) throws XmlException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QueryCachesType.type, (XmlOptions)null);
      }

      public static QueryCachesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QueryCachesType.type, options);
      }

      public static QueryCachesType parse(File file) throws XmlException, IOException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(file, QueryCachesType.type, (XmlOptions)null);
      }

      public static QueryCachesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(file, QueryCachesType.type, options);
      }

      public static QueryCachesType parse(URL u) throws XmlException, IOException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(u, QueryCachesType.type, (XmlOptions)null);
      }

      public static QueryCachesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(u, QueryCachesType.type, options);
      }

      public static QueryCachesType parse(InputStream is) throws XmlException, IOException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(is, QueryCachesType.type, (XmlOptions)null);
      }

      public static QueryCachesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(is, QueryCachesType.type, options);
      }

      public static QueryCachesType parse(Reader r) throws XmlException, IOException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(r, QueryCachesType.type, (XmlOptions)null);
      }

      public static QueryCachesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(r, QueryCachesType.type, options);
      }

      public static QueryCachesType parse(XMLStreamReader sr) throws XmlException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(sr, QueryCachesType.type, (XmlOptions)null);
      }

      public static QueryCachesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(sr, QueryCachesType.type, options);
      }

      public static QueryCachesType parse(Node node) throws XmlException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(node, QueryCachesType.type, (XmlOptions)null);
      }

      public static QueryCachesType parse(Node node, XmlOptions options) throws XmlException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(node, QueryCachesType.type, options);
      }

      /** @deprecated */
      public static QueryCachesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(xis, QueryCachesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static QueryCachesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (QueryCachesType)XmlBeans.getContextTypeLoader().parse(xis, QueryCachesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QueryCachesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QueryCachesType.type, options);
      }

      private Factory() {
      }
   }
}
