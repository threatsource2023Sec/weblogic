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

public interface EntityMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EntityMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("entitymappingtype517ftype");

   String getEntityMappingName();

   XmlString xgetEntityMappingName();

   void setEntityMappingName(String var1);

   void xsetEntityMappingName(XmlString var1);

   String getPublicId();

   XmlString xgetPublicId();

   boolean isSetPublicId();

   void setPublicId(String var1);

   void xsetPublicId(XmlString var1);

   void unsetPublicId();

   String getSystemId();

   XmlString xgetSystemId();

   boolean isSetSystemId();

   void setSystemId(String var1);

   void xsetSystemId(XmlString var1);

   void unsetSystemId();

   String getEntityUri();

   XmlString xgetEntityUri();

   boolean isSetEntityUri();

   void setEntityUri(String var1);

   void xsetEntityUri(XmlString var1);

   void unsetEntityUri();

   String getWhenToCache();

   XmlString xgetWhenToCache();

   boolean isSetWhenToCache();

   void setWhenToCache(String var1);

   void xsetWhenToCache(XmlString var1);

   void unsetWhenToCache();

   int getCacheTimeoutInterval();

   XmlInt xgetCacheTimeoutInterval();

   boolean isSetCacheTimeoutInterval();

   void setCacheTimeoutInterval(int var1);

   void xsetCacheTimeoutInterval(XmlInt var1);

   void unsetCacheTimeoutInterval();

   public static final class Factory {
      public static EntityMappingType newInstance() {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().newInstance(EntityMappingType.type, (XmlOptions)null);
      }

      public static EntityMappingType newInstance(XmlOptions options) {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().newInstance(EntityMappingType.type, options);
      }

      public static EntityMappingType parse(String xmlAsString) throws XmlException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityMappingType.type, (XmlOptions)null);
      }

      public static EntityMappingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityMappingType.type, options);
      }

      public static EntityMappingType parse(File file) throws XmlException, IOException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(file, EntityMappingType.type, (XmlOptions)null);
      }

      public static EntityMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(file, EntityMappingType.type, options);
      }

      public static EntityMappingType parse(URL u) throws XmlException, IOException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(u, EntityMappingType.type, (XmlOptions)null);
      }

      public static EntityMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(u, EntityMappingType.type, options);
      }

      public static EntityMappingType parse(InputStream is) throws XmlException, IOException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(is, EntityMappingType.type, (XmlOptions)null);
      }

      public static EntityMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(is, EntityMappingType.type, options);
      }

      public static EntityMappingType parse(Reader r) throws XmlException, IOException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(r, EntityMappingType.type, (XmlOptions)null);
      }

      public static EntityMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(r, EntityMappingType.type, options);
      }

      public static EntityMappingType parse(XMLStreamReader sr) throws XmlException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(sr, EntityMappingType.type, (XmlOptions)null);
      }

      public static EntityMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(sr, EntityMappingType.type, options);
      }

      public static EntityMappingType parse(Node node) throws XmlException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(node, EntityMappingType.type, (XmlOptions)null);
      }

      public static EntityMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(node, EntityMappingType.type, options);
      }

      /** @deprecated */
      public static EntityMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(xis, EntityMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EntityMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EntityMappingType)XmlBeans.getContextTypeLoader().parse(xis, EntityMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityMappingType.type, options);
      }

      private Factory() {
      }
   }
}
