package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
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

public interface StatefulSessionCacheType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StatefulSessionCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("statefulsessioncachetype5ac0type");

   XsdNonNegativeIntegerType getMaxBeansInCache();

   boolean isSetMaxBeansInCache();

   void setMaxBeansInCache(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewMaxBeansInCache();

   void unsetMaxBeansInCache();

   XsdNonNegativeIntegerType getIdleTimeoutSeconds();

   boolean isSetIdleTimeoutSeconds();

   void setIdleTimeoutSeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewIdleTimeoutSeconds();

   void unsetIdleTimeoutSeconds();

   XsdNonNegativeIntegerType getSessionTimeoutSeconds();

   boolean isSetSessionTimeoutSeconds();

   void setSessionTimeoutSeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewSessionTimeoutSeconds();

   void unsetSessionTimeoutSeconds();

   CacheTypeType getCacheType();

   boolean isSetCacheType();

   void setCacheType(CacheTypeType var1);

   CacheTypeType addNewCacheType();

   void unsetCacheType();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static StatefulSessionCacheType newInstance() {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().newInstance(StatefulSessionCacheType.type, (XmlOptions)null);
      }

      public static StatefulSessionCacheType newInstance(XmlOptions options) {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().newInstance(StatefulSessionCacheType.type, options);
      }

      public static StatefulSessionCacheType parse(String xmlAsString) throws XmlException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatefulSessionCacheType.type, (XmlOptions)null);
      }

      public static StatefulSessionCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatefulSessionCacheType.type, options);
      }

      public static StatefulSessionCacheType parse(File file) throws XmlException, IOException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(file, StatefulSessionCacheType.type, (XmlOptions)null);
      }

      public static StatefulSessionCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(file, StatefulSessionCacheType.type, options);
      }

      public static StatefulSessionCacheType parse(URL u) throws XmlException, IOException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(u, StatefulSessionCacheType.type, (XmlOptions)null);
      }

      public static StatefulSessionCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(u, StatefulSessionCacheType.type, options);
      }

      public static StatefulSessionCacheType parse(InputStream is) throws XmlException, IOException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(is, StatefulSessionCacheType.type, (XmlOptions)null);
      }

      public static StatefulSessionCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(is, StatefulSessionCacheType.type, options);
      }

      public static StatefulSessionCacheType parse(Reader r) throws XmlException, IOException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(r, StatefulSessionCacheType.type, (XmlOptions)null);
      }

      public static StatefulSessionCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(r, StatefulSessionCacheType.type, options);
      }

      public static StatefulSessionCacheType parse(XMLStreamReader sr) throws XmlException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(sr, StatefulSessionCacheType.type, (XmlOptions)null);
      }

      public static StatefulSessionCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(sr, StatefulSessionCacheType.type, options);
      }

      public static StatefulSessionCacheType parse(Node node) throws XmlException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(node, StatefulSessionCacheType.type, (XmlOptions)null);
      }

      public static StatefulSessionCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(node, StatefulSessionCacheType.type, options);
      }

      /** @deprecated */
      public static StatefulSessionCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(xis, StatefulSessionCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StatefulSessionCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StatefulSessionCacheType)XmlBeans.getContextTypeLoader().parse(xis, StatefulSessionCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatefulSessionCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatefulSessionCacheType.type, options);
      }

      private Factory() {
      }
   }
}
