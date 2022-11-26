package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import com.sun.java.xml.ns.javaee.XsdPositiveIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface EntityCacheRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EntityCacheRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("entitycachereftype6c14type");

   EntityCacheNameType getEntityCacheName();

   void setEntityCacheName(EntityCacheNameType var1);

   EntityCacheNameType addNewEntityCacheName();

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

   XsdPositiveIntegerType getEstimatedBeanSize();

   boolean isSetEstimatedBeanSize();

   void setEstimatedBeanSize(XsdPositiveIntegerType var1);

   XsdPositiveIntegerType addNewEstimatedBeanSize();

   void unsetEstimatedBeanSize();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EntityCacheRefType newInstance() {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().newInstance(EntityCacheRefType.type, (XmlOptions)null);
      }

      public static EntityCacheRefType newInstance(XmlOptions options) {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().newInstance(EntityCacheRefType.type, options);
      }

      public static EntityCacheRefType parse(String xmlAsString) throws XmlException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityCacheRefType.type, (XmlOptions)null);
      }

      public static EntityCacheRefType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityCacheRefType.type, options);
      }

      public static EntityCacheRefType parse(File file) throws XmlException, IOException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(file, EntityCacheRefType.type, (XmlOptions)null);
      }

      public static EntityCacheRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(file, EntityCacheRefType.type, options);
      }

      public static EntityCacheRefType parse(URL u) throws XmlException, IOException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(u, EntityCacheRefType.type, (XmlOptions)null);
      }

      public static EntityCacheRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(u, EntityCacheRefType.type, options);
      }

      public static EntityCacheRefType parse(InputStream is) throws XmlException, IOException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(is, EntityCacheRefType.type, (XmlOptions)null);
      }

      public static EntityCacheRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(is, EntityCacheRefType.type, options);
      }

      public static EntityCacheRefType parse(Reader r) throws XmlException, IOException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(r, EntityCacheRefType.type, (XmlOptions)null);
      }

      public static EntityCacheRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(r, EntityCacheRefType.type, options);
      }

      public static EntityCacheRefType parse(XMLStreamReader sr) throws XmlException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(sr, EntityCacheRefType.type, (XmlOptions)null);
      }

      public static EntityCacheRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(sr, EntityCacheRefType.type, options);
      }

      public static EntityCacheRefType parse(Node node) throws XmlException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(node, EntityCacheRefType.type, (XmlOptions)null);
      }

      public static EntityCacheRefType parse(Node node, XmlOptions options) throws XmlException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(node, EntityCacheRefType.type, options);
      }

      /** @deprecated */
      public static EntityCacheRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(xis, EntityCacheRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EntityCacheRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EntityCacheRefType)XmlBeans.getContextTypeLoader().parse(xis, EntityCacheRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityCacheRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityCacheRefType.type, options);
      }

      private Factory() {
      }
   }
}
