package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlObject;
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

public interface ApplicationEntityCacheType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ApplicationEntityCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("applicationentitycachetype0090type");

   String getEntityCacheName();

   XmlString xgetEntityCacheName();

   void setEntityCacheName(String var1);

   void xsetEntityCacheName(XmlString var1);

   int getMaxBeansInCache();

   XmlInt xgetMaxBeansInCache();

   boolean isSetMaxBeansInCache();

   void setMaxBeansInCache(int var1);

   void xsetMaxBeansInCache(XmlInt var1);

   void unsetMaxBeansInCache();

   MaxCacheSizeType getMaxCacheSize();

   boolean isSetMaxCacheSize();

   void setMaxCacheSize(MaxCacheSizeType var1);

   MaxCacheSizeType addNewMaxCacheSize();

   void unsetMaxCacheSize();

   int getMaxQueriesInCache();

   XmlInt xgetMaxQueriesInCache();

   boolean isSetMaxQueriesInCache();

   void setMaxQueriesInCache(int var1);

   void xsetMaxQueriesInCache(XmlInt var1);

   void unsetMaxQueriesInCache();

   String getCachingStrategy();

   XmlString xgetCachingStrategy();

   boolean isSetCachingStrategy();

   void setCachingStrategy(String var1);

   void xsetCachingStrategy(XmlString var1);

   void unsetCachingStrategy();

   public static final class Factory {
      public static ApplicationEntityCacheType newInstance() {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().newInstance(ApplicationEntityCacheType.type, (XmlOptions)null);
      }

      public static ApplicationEntityCacheType newInstance(XmlOptions options) {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().newInstance(ApplicationEntityCacheType.type, options);
      }

      public static ApplicationEntityCacheType parse(String xmlAsString) throws XmlException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationEntityCacheType.type, (XmlOptions)null);
      }

      public static ApplicationEntityCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationEntityCacheType.type, options);
      }

      public static ApplicationEntityCacheType parse(File file) throws XmlException, IOException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(file, ApplicationEntityCacheType.type, (XmlOptions)null);
      }

      public static ApplicationEntityCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(file, ApplicationEntityCacheType.type, options);
      }

      public static ApplicationEntityCacheType parse(URL u) throws XmlException, IOException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(u, ApplicationEntityCacheType.type, (XmlOptions)null);
      }

      public static ApplicationEntityCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(u, ApplicationEntityCacheType.type, options);
      }

      public static ApplicationEntityCacheType parse(InputStream is) throws XmlException, IOException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(is, ApplicationEntityCacheType.type, (XmlOptions)null);
      }

      public static ApplicationEntityCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(is, ApplicationEntityCacheType.type, options);
      }

      public static ApplicationEntityCacheType parse(Reader r) throws XmlException, IOException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(r, ApplicationEntityCacheType.type, (XmlOptions)null);
      }

      public static ApplicationEntityCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(r, ApplicationEntityCacheType.type, options);
      }

      public static ApplicationEntityCacheType parse(XMLStreamReader sr) throws XmlException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationEntityCacheType.type, (XmlOptions)null);
      }

      public static ApplicationEntityCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationEntityCacheType.type, options);
      }

      public static ApplicationEntityCacheType parse(Node node) throws XmlException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(node, ApplicationEntityCacheType.type, (XmlOptions)null);
      }

      public static ApplicationEntityCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(node, ApplicationEntityCacheType.type, options);
      }

      /** @deprecated */
      public static ApplicationEntityCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationEntityCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ApplicationEntityCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ApplicationEntityCacheType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationEntityCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationEntityCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationEntityCacheType.type, options);
      }

      private Factory() {
      }
   }
}
