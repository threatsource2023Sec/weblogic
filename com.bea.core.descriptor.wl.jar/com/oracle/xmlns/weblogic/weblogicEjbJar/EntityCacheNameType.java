package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface EntityCacheNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EntityCacheNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("entitycachenametype6424type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EntityCacheNameType newInstance() {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().newInstance(EntityCacheNameType.type, (XmlOptions)null);
      }

      public static EntityCacheNameType newInstance(XmlOptions options) {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().newInstance(EntityCacheNameType.type, options);
      }

      public static EntityCacheNameType parse(String xmlAsString) throws XmlException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityCacheNameType.type, (XmlOptions)null);
      }

      public static EntityCacheNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityCacheNameType.type, options);
      }

      public static EntityCacheNameType parse(File file) throws XmlException, IOException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(file, EntityCacheNameType.type, (XmlOptions)null);
      }

      public static EntityCacheNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(file, EntityCacheNameType.type, options);
      }

      public static EntityCacheNameType parse(URL u) throws XmlException, IOException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(u, EntityCacheNameType.type, (XmlOptions)null);
      }

      public static EntityCacheNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(u, EntityCacheNameType.type, options);
      }

      public static EntityCacheNameType parse(InputStream is) throws XmlException, IOException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(is, EntityCacheNameType.type, (XmlOptions)null);
      }

      public static EntityCacheNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(is, EntityCacheNameType.type, options);
      }

      public static EntityCacheNameType parse(Reader r) throws XmlException, IOException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(r, EntityCacheNameType.type, (XmlOptions)null);
      }

      public static EntityCacheNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(r, EntityCacheNameType.type, options);
      }

      public static EntityCacheNameType parse(XMLStreamReader sr) throws XmlException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(sr, EntityCacheNameType.type, (XmlOptions)null);
      }

      public static EntityCacheNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(sr, EntityCacheNameType.type, options);
      }

      public static EntityCacheNameType parse(Node node) throws XmlException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(node, EntityCacheNameType.type, (XmlOptions)null);
      }

      public static EntityCacheNameType parse(Node node, XmlOptions options) throws XmlException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(node, EntityCacheNameType.type, options);
      }

      /** @deprecated */
      public static EntityCacheNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(xis, EntityCacheNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EntityCacheNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EntityCacheNameType)XmlBeans.getContextTypeLoader().parse(xis, EntityCacheNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityCacheNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityCacheNameType.type, options);
      }

      private Factory() {
      }
   }
}
