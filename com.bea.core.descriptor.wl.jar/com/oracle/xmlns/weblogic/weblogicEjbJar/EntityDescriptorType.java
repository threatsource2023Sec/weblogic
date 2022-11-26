package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface EntityDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EntityDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("entitydescriptortype74ddtype");

   PoolType getPool();

   boolean isSetPool();

   void setPool(PoolType var1);

   PoolType addNewPool();

   void unsetPool();

   TimerDescriptorType getTimerDescriptor();

   boolean isSetTimerDescriptor();

   void setTimerDescriptor(TimerDescriptorType var1);

   TimerDescriptorType addNewTimerDescriptor();

   void unsetTimerDescriptor();

   EntityCacheType getEntityCache();

   boolean isSetEntityCache();

   void setEntityCache(EntityCacheType var1);

   EntityCacheType addNewEntityCache();

   void unsetEntityCache();

   EntityCacheRefType getEntityCacheRef();

   boolean isSetEntityCacheRef();

   void setEntityCacheRef(EntityCacheRefType var1);

   EntityCacheRefType addNewEntityCacheRef();

   void unsetEntityCacheRef();

   PersistenceType getPersistence();

   boolean isSetPersistence();

   void setPersistence(PersistenceType var1);

   PersistenceType addNewPersistence();

   void unsetPersistence();

   EntityClusteringType getEntityClustering();

   boolean isSetEntityClustering();

   void setEntityClustering(EntityClusteringType var1);

   EntityClusteringType addNewEntityClustering();

   void unsetEntityClustering();

   InvalidationTargetType getInvalidationTarget();

   boolean isSetInvalidationTarget();

   void setInvalidationTarget(InvalidationTargetType var1);

   InvalidationTargetType addNewInvalidationTarget();

   void unsetInvalidationTarget();

   TrueFalseType getEnableDynamicQueries();

   boolean isSetEnableDynamicQueries();

   void setEnableDynamicQueries(TrueFalseType var1);

   TrueFalseType addNewEnableDynamicQueries();

   void unsetEnableDynamicQueries();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EntityDescriptorType newInstance() {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().newInstance(EntityDescriptorType.type, (XmlOptions)null);
      }

      public static EntityDescriptorType newInstance(XmlOptions options) {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().newInstance(EntityDescriptorType.type, options);
      }

      public static EntityDescriptorType parse(String xmlAsString) throws XmlException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityDescriptorType.type, (XmlOptions)null);
      }

      public static EntityDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityDescriptorType.type, options);
      }

      public static EntityDescriptorType parse(File file) throws XmlException, IOException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(file, EntityDescriptorType.type, (XmlOptions)null);
      }

      public static EntityDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(file, EntityDescriptorType.type, options);
      }

      public static EntityDescriptorType parse(URL u) throws XmlException, IOException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(u, EntityDescriptorType.type, (XmlOptions)null);
      }

      public static EntityDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(u, EntityDescriptorType.type, options);
      }

      public static EntityDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(is, EntityDescriptorType.type, (XmlOptions)null);
      }

      public static EntityDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(is, EntityDescriptorType.type, options);
      }

      public static EntityDescriptorType parse(Reader r) throws XmlException, IOException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(r, EntityDescriptorType.type, (XmlOptions)null);
      }

      public static EntityDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(r, EntityDescriptorType.type, options);
      }

      public static EntityDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, EntityDescriptorType.type, (XmlOptions)null);
      }

      public static EntityDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, EntityDescriptorType.type, options);
      }

      public static EntityDescriptorType parse(Node node) throws XmlException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(node, EntityDescriptorType.type, (XmlOptions)null);
      }

      public static EntityDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(node, EntityDescriptorType.type, options);
      }

      /** @deprecated */
      public static EntityDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, EntityDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EntityDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EntityDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, EntityDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
