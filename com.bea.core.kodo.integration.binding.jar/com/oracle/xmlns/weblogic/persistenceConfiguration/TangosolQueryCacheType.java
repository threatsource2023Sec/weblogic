package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface TangosolQueryCacheType extends QueryCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TangosolQueryCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("tangosolquerycachetypeffc3type");

   boolean getClearOnClose();

   XmlBoolean xgetClearOnClose();

   boolean isSetClearOnClose();

   void setClearOnClose(boolean var1);

   void xsetClearOnClose(XmlBoolean var1);

   void unsetClearOnClose();

   String getTangosolCacheType();

   XmlString xgetTangosolCacheType();

   boolean isNilTangosolCacheType();

   boolean isSetTangosolCacheType();

   void setTangosolCacheType(String var1);

   void xsetTangosolCacheType(XmlString var1);

   void setNilTangosolCacheType();

   void unsetTangosolCacheType();

   String getTangosolCacheName();

   XmlString xgetTangosolCacheName();

   boolean isNilTangosolCacheName();

   boolean isSetTangosolCacheName();

   void setTangosolCacheName(String var1);

   void xsetTangosolCacheName(XmlString var1);

   void setNilTangosolCacheName();

   void unsetTangosolCacheName();

   public static final class Factory {
      public static TangosolQueryCacheType newInstance() {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(TangosolQueryCacheType.type, (XmlOptions)null);
      }

      public static TangosolQueryCacheType newInstance(XmlOptions options) {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(TangosolQueryCacheType.type, options);
      }

      public static TangosolQueryCacheType parse(String xmlAsString) throws XmlException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TangosolQueryCacheType.type, (XmlOptions)null);
      }

      public static TangosolQueryCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TangosolQueryCacheType.type, options);
      }

      public static TangosolQueryCacheType parse(File file) throws XmlException, IOException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, TangosolQueryCacheType.type, (XmlOptions)null);
      }

      public static TangosolQueryCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, TangosolQueryCacheType.type, options);
      }

      public static TangosolQueryCacheType parse(URL u) throws XmlException, IOException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, TangosolQueryCacheType.type, (XmlOptions)null);
      }

      public static TangosolQueryCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, TangosolQueryCacheType.type, options);
      }

      public static TangosolQueryCacheType parse(InputStream is) throws XmlException, IOException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, TangosolQueryCacheType.type, (XmlOptions)null);
      }

      public static TangosolQueryCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, TangosolQueryCacheType.type, options);
      }

      public static TangosolQueryCacheType parse(Reader r) throws XmlException, IOException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, TangosolQueryCacheType.type, (XmlOptions)null);
      }

      public static TangosolQueryCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, TangosolQueryCacheType.type, options);
      }

      public static TangosolQueryCacheType parse(XMLStreamReader sr) throws XmlException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, TangosolQueryCacheType.type, (XmlOptions)null);
      }

      public static TangosolQueryCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, TangosolQueryCacheType.type, options);
      }

      public static TangosolQueryCacheType parse(Node node) throws XmlException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, TangosolQueryCacheType.type, (XmlOptions)null);
      }

      public static TangosolQueryCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, TangosolQueryCacheType.type, options);
      }

      /** @deprecated */
      public static TangosolQueryCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, TangosolQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TangosolQueryCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TangosolQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, TangosolQueryCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TangosolQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TangosolQueryCacheType.type, options);
      }

      private Factory() {
      }
   }
}
