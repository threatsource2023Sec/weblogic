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

public interface GemFireQueryCacheType extends QueryCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(GemFireQueryCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("gemfirequerycachetype4856type");

   String getGemFireCacheName();

   XmlString xgetGemFireCacheName();

   boolean isNilGemFireCacheName();

   boolean isSetGemFireCacheName();

   void setGemFireCacheName(String var1);

   void xsetGemFireCacheName(XmlString var1);

   void setNilGemFireCacheName();

   void unsetGemFireCacheName();

   public static final class Factory {
      public static GemFireQueryCacheType newInstance() {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(GemFireQueryCacheType.type, (XmlOptions)null);
      }

      public static GemFireQueryCacheType newInstance(XmlOptions options) {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(GemFireQueryCacheType.type, options);
      }

      public static GemFireQueryCacheType parse(String xmlAsString) throws XmlException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GemFireQueryCacheType.type, (XmlOptions)null);
      }

      public static GemFireQueryCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GemFireQueryCacheType.type, options);
      }

      public static GemFireQueryCacheType parse(File file) throws XmlException, IOException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, GemFireQueryCacheType.type, (XmlOptions)null);
      }

      public static GemFireQueryCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, GemFireQueryCacheType.type, options);
      }

      public static GemFireQueryCacheType parse(URL u) throws XmlException, IOException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, GemFireQueryCacheType.type, (XmlOptions)null);
      }

      public static GemFireQueryCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, GemFireQueryCacheType.type, options);
      }

      public static GemFireQueryCacheType parse(InputStream is) throws XmlException, IOException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, GemFireQueryCacheType.type, (XmlOptions)null);
      }

      public static GemFireQueryCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, GemFireQueryCacheType.type, options);
      }

      public static GemFireQueryCacheType parse(Reader r) throws XmlException, IOException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, GemFireQueryCacheType.type, (XmlOptions)null);
      }

      public static GemFireQueryCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, GemFireQueryCacheType.type, options);
      }

      public static GemFireQueryCacheType parse(XMLStreamReader sr) throws XmlException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, GemFireQueryCacheType.type, (XmlOptions)null);
      }

      public static GemFireQueryCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, GemFireQueryCacheType.type, options);
      }

      public static GemFireQueryCacheType parse(Node node) throws XmlException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, GemFireQueryCacheType.type, (XmlOptions)null);
      }

      public static GemFireQueryCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, GemFireQueryCacheType.type, options);
      }

      /** @deprecated */
      public static GemFireQueryCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, GemFireQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static GemFireQueryCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (GemFireQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, GemFireQueryCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GemFireQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GemFireQueryCacheType.type, options);
      }

      private Factory() {
      }
   }
}
