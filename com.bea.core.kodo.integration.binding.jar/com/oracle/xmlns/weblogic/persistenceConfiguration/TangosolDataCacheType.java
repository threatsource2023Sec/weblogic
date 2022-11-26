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

public interface TangosolDataCacheType extends DataCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TangosolDataCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("tangosoldatacachetypebec7type");

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

   String getEvictionSchedule();

   XmlString xgetEvictionSchedule();

   boolean isNilEvictionSchedule();

   boolean isSetEvictionSchedule();

   void setEvictionSchedule(String var1);

   void xsetEvictionSchedule(XmlString var1);

   void setNilEvictionSchedule();

   void unsetEvictionSchedule();

   public static final class Factory {
      public static TangosolDataCacheType newInstance() {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().newInstance(TangosolDataCacheType.type, (XmlOptions)null);
      }

      public static TangosolDataCacheType newInstance(XmlOptions options) {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().newInstance(TangosolDataCacheType.type, options);
      }

      public static TangosolDataCacheType parse(String xmlAsString) throws XmlException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TangosolDataCacheType.type, (XmlOptions)null);
      }

      public static TangosolDataCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TangosolDataCacheType.type, options);
      }

      public static TangosolDataCacheType parse(File file) throws XmlException, IOException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(file, TangosolDataCacheType.type, (XmlOptions)null);
      }

      public static TangosolDataCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(file, TangosolDataCacheType.type, options);
      }

      public static TangosolDataCacheType parse(URL u) throws XmlException, IOException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(u, TangosolDataCacheType.type, (XmlOptions)null);
      }

      public static TangosolDataCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(u, TangosolDataCacheType.type, options);
      }

      public static TangosolDataCacheType parse(InputStream is) throws XmlException, IOException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(is, TangosolDataCacheType.type, (XmlOptions)null);
      }

      public static TangosolDataCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(is, TangosolDataCacheType.type, options);
      }

      public static TangosolDataCacheType parse(Reader r) throws XmlException, IOException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(r, TangosolDataCacheType.type, (XmlOptions)null);
      }

      public static TangosolDataCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(r, TangosolDataCacheType.type, options);
      }

      public static TangosolDataCacheType parse(XMLStreamReader sr) throws XmlException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(sr, TangosolDataCacheType.type, (XmlOptions)null);
      }

      public static TangosolDataCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(sr, TangosolDataCacheType.type, options);
      }

      public static TangosolDataCacheType parse(Node node) throws XmlException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(node, TangosolDataCacheType.type, (XmlOptions)null);
      }

      public static TangosolDataCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(node, TangosolDataCacheType.type, options);
      }

      /** @deprecated */
      public static TangosolDataCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(xis, TangosolDataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TangosolDataCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TangosolDataCacheType)XmlBeans.getContextTypeLoader().parse(xis, TangosolDataCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TangosolDataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TangosolDataCacheType.type, options);
      }

      private Factory() {
      }
   }
}
