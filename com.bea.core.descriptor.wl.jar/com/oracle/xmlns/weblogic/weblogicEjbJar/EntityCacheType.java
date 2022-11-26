package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface EntityCacheType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EntityCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("entitycachetype37cetype");

   XsdNonNegativeIntegerType getMaxBeansInCache();

   boolean isSetMaxBeansInCache();

   void setMaxBeansInCache(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewMaxBeansInCache();

   void unsetMaxBeansInCache();

   XsdIntegerType getMaxQueriesInCache();

   boolean isSetMaxQueriesInCache();

   void setMaxQueriesInCache(XsdIntegerType var1);

   XsdIntegerType addNewMaxQueriesInCache();

   void unsetMaxQueriesInCache();

   XsdNonNegativeIntegerType getIdleTimeoutSeconds();

   boolean isSetIdleTimeoutSeconds();

   void setIdleTimeoutSeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewIdleTimeoutSeconds();

   void unsetIdleTimeoutSeconds();

   XsdNonNegativeIntegerType getReadTimeoutSeconds();

   boolean isSetReadTimeoutSeconds();

   void setReadTimeoutSeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewReadTimeoutSeconds();

   void unsetReadTimeoutSeconds();

   ConcurrencyStrategyType getConcurrencyStrategy();

   boolean isSetConcurrencyStrategy();

   void setConcurrencyStrategy(ConcurrencyStrategyType var1);

   ConcurrencyStrategyType addNewConcurrencyStrategy();

   void unsetConcurrencyStrategy();

   TrueFalseType getCacheBetweenTransactions();

   boolean isSetCacheBetweenTransactions();

   void setCacheBetweenTransactions(TrueFalseType var1);

   TrueFalseType addNewCacheBetweenTransactions();

   void unsetCacheBetweenTransactions();

   TrueFalseType getDisableReadyInstances();

   boolean isSetDisableReadyInstances();

   void setDisableReadyInstances(TrueFalseType var1);

   TrueFalseType addNewDisableReadyInstances();

   void unsetDisableReadyInstances();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EntityCacheType newInstance() {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().newInstance(EntityCacheType.type, (XmlOptions)null);
      }

      public static EntityCacheType newInstance(XmlOptions options) {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().newInstance(EntityCacheType.type, options);
      }

      public static EntityCacheType parse(String xmlAsString) throws XmlException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityCacheType.type, (XmlOptions)null);
      }

      public static EntityCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityCacheType.type, options);
      }

      public static EntityCacheType parse(File file) throws XmlException, IOException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(file, EntityCacheType.type, (XmlOptions)null);
      }

      public static EntityCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(file, EntityCacheType.type, options);
      }

      public static EntityCacheType parse(URL u) throws XmlException, IOException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(u, EntityCacheType.type, (XmlOptions)null);
      }

      public static EntityCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(u, EntityCacheType.type, options);
      }

      public static EntityCacheType parse(InputStream is) throws XmlException, IOException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(is, EntityCacheType.type, (XmlOptions)null);
      }

      public static EntityCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(is, EntityCacheType.type, options);
      }

      public static EntityCacheType parse(Reader r) throws XmlException, IOException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(r, EntityCacheType.type, (XmlOptions)null);
      }

      public static EntityCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(r, EntityCacheType.type, options);
      }

      public static EntityCacheType parse(XMLStreamReader sr) throws XmlException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(sr, EntityCacheType.type, (XmlOptions)null);
      }

      public static EntityCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(sr, EntityCacheType.type, options);
      }

      public static EntityCacheType parse(Node node) throws XmlException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(node, EntityCacheType.type, (XmlOptions)null);
      }

      public static EntityCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(node, EntityCacheType.type, options);
      }

      /** @deprecated */
      public static EntityCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(xis, EntityCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EntityCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EntityCacheType)XmlBeans.getContextTypeLoader().parse(xis, EntityCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityCacheType.type, options);
      }

      private Factory() {
      }
   }
}
