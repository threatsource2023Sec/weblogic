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

public interface DataCachesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DataCachesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("datacachestype99b8type");

   DefaultDataCacheType[] getDefaultDataCacheArray();

   DefaultDataCacheType getDefaultDataCacheArray(int var1);

   boolean isNilDefaultDataCacheArray(int var1);

   int sizeOfDefaultDataCacheArray();

   void setDefaultDataCacheArray(DefaultDataCacheType[] var1);

   void setDefaultDataCacheArray(int var1, DefaultDataCacheType var2);

   void setNilDefaultDataCacheArray(int var1);

   DefaultDataCacheType insertNewDefaultDataCache(int var1);

   DefaultDataCacheType addNewDefaultDataCache();

   void removeDefaultDataCache(int var1);

   KodoConcurrentDataCacheType[] getKodoConcurrentDataCacheArray();

   KodoConcurrentDataCacheType getKodoConcurrentDataCacheArray(int var1);

   boolean isNilKodoConcurrentDataCacheArray(int var1);

   int sizeOfKodoConcurrentDataCacheArray();

   void setKodoConcurrentDataCacheArray(KodoConcurrentDataCacheType[] var1);

   void setKodoConcurrentDataCacheArray(int var1, KodoConcurrentDataCacheType var2);

   void setNilKodoConcurrentDataCacheArray(int var1);

   KodoConcurrentDataCacheType insertNewKodoConcurrentDataCache(int var1);

   KodoConcurrentDataCacheType addNewKodoConcurrentDataCache();

   void removeKodoConcurrentDataCache(int var1);

   GemFireDataCacheType[] getGemFireDataCacheArray();

   GemFireDataCacheType getGemFireDataCacheArray(int var1);

   boolean isNilGemFireDataCacheArray(int var1);

   int sizeOfGemFireDataCacheArray();

   void setGemFireDataCacheArray(GemFireDataCacheType[] var1);

   void setGemFireDataCacheArray(int var1, GemFireDataCacheType var2);

   void setNilGemFireDataCacheArray(int var1);

   GemFireDataCacheType insertNewGemFireDataCache(int var1);

   GemFireDataCacheType addNewGemFireDataCache();

   void removeGemFireDataCache(int var1);

   LruDataCacheType[] getLruDataCacheArray();

   LruDataCacheType getLruDataCacheArray(int var1);

   boolean isNilLruDataCacheArray(int var1);

   int sizeOfLruDataCacheArray();

   void setLruDataCacheArray(LruDataCacheType[] var1);

   void setLruDataCacheArray(int var1, LruDataCacheType var2);

   void setNilLruDataCacheArray(int var1);

   LruDataCacheType insertNewLruDataCache(int var1);

   LruDataCacheType addNewLruDataCache();

   void removeLruDataCache(int var1);

   TangosolDataCacheType[] getTangosolDataCacheArray();

   TangosolDataCacheType getTangosolDataCacheArray(int var1);

   boolean isNilTangosolDataCacheArray(int var1);

   int sizeOfTangosolDataCacheArray();

   void setTangosolDataCacheArray(TangosolDataCacheType[] var1);

   void setTangosolDataCacheArray(int var1, TangosolDataCacheType var2);

   void setNilTangosolDataCacheArray(int var1);

   TangosolDataCacheType insertNewTangosolDataCache(int var1);

   TangosolDataCacheType addNewTangosolDataCache();

   void removeTangosolDataCache(int var1);

   CustomDataCacheType[] getCustomDataCacheArray();

   CustomDataCacheType getCustomDataCacheArray(int var1);

   boolean isNilCustomDataCacheArray(int var1);

   int sizeOfCustomDataCacheArray();

   void setCustomDataCacheArray(CustomDataCacheType[] var1);

   void setCustomDataCacheArray(int var1, CustomDataCacheType var2);

   void setNilCustomDataCacheArray(int var1);

   CustomDataCacheType insertNewCustomDataCache(int var1);

   CustomDataCacheType addNewCustomDataCache();

   void removeCustomDataCache(int var1);

   public static final class Factory {
      public static DataCachesType newInstance() {
         return (DataCachesType)XmlBeans.getContextTypeLoader().newInstance(DataCachesType.type, (XmlOptions)null);
      }

      public static DataCachesType newInstance(XmlOptions options) {
         return (DataCachesType)XmlBeans.getContextTypeLoader().newInstance(DataCachesType.type, options);
      }

      public static DataCachesType parse(String xmlAsString) throws XmlException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DataCachesType.type, (XmlOptions)null);
      }

      public static DataCachesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DataCachesType.type, options);
      }

      public static DataCachesType parse(File file) throws XmlException, IOException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(file, DataCachesType.type, (XmlOptions)null);
      }

      public static DataCachesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(file, DataCachesType.type, options);
      }

      public static DataCachesType parse(URL u) throws XmlException, IOException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(u, DataCachesType.type, (XmlOptions)null);
      }

      public static DataCachesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(u, DataCachesType.type, options);
      }

      public static DataCachesType parse(InputStream is) throws XmlException, IOException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(is, DataCachesType.type, (XmlOptions)null);
      }

      public static DataCachesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(is, DataCachesType.type, options);
      }

      public static DataCachesType parse(Reader r) throws XmlException, IOException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(r, DataCachesType.type, (XmlOptions)null);
      }

      public static DataCachesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(r, DataCachesType.type, options);
      }

      public static DataCachesType parse(XMLStreamReader sr) throws XmlException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(sr, DataCachesType.type, (XmlOptions)null);
      }

      public static DataCachesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(sr, DataCachesType.type, options);
      }

      public static DataCachesType parse(Node node) throws XmlException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(node, DataCachesType.type, (XmlOptions)null);
      }

      public static DataCachesType parse(Node node, XmlOptions options) throws XmlException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(node, DataCachesType.type, options);
      }

      /** @deprecated */
      public static DataCachesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(xis, DataCachesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DataCachesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DataCachesType)XmlBeans.getContextTypeLoader().parse(xis, DataCachesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DataCachesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DataCachesType.type, options);
      }

      private Factory() {
      }
   }
}
