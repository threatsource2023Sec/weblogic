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

public interface CustomQueryCacheType extends QueryCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomQueryCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customquerycachetype5879type");

   String getClassname();

   XmlString xgetClassname();

   boolean isNilClassname();

   boolean isSetClassname();

   void setClassname(String var1);

   void xsetClassname(XmlString var1);

   void setNilClassname();

   void unsetClassname();

   PropertiesType getProperties();

   boolean isNilProperties();

   boolean isSetProperties();

   void setProperties(PropertiesType var1);

   PropertiesType addNewProperties();

   void setNilProperties();

   void unsetProperties();

   public static final class Factory {
      public static CustomQueryCacheType newInstance() {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(CustomQueryCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCacheType newInstance(XmlOptions options) {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().newInstance(CustomQueryCacheType.type, options);
      }

      public static CustomQueryCacheType parse(String xmlAsString) throws XmlException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomQueryCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomQueryCacheType.type, options);
      }

      public static CustomQueryCacheType parse(File file) throws XmlException, IOException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, CustomQueryCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(file, CustomQueryCacheType.type, options);
      }

      public static CustomQueryCacheType parse(URL u) throws XmlException, IOException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, CustomQueryCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(u, CustomQueryCacheType.type, options);
      }

      public static CustomQueryCacheType parse(InputStream is) throws XmlException, IOException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, CustomQueryCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(is, CustomQueryCacheType.type, options);
      }

      public static CustomQueryCacheType parse(Reader r) throws XmlException, IOException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, CustomQueryCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(r, CustomQueryCacheType.type, options);
      }

      public static CustomQueryCacheType parse(XMLStreamReader sr) throws XmlException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, CustomQueryCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(sr, CustomQueryCacheType.type, options);
      }

      public static CustomQueryCacheType parse(Node node) throws XmlException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, CustomQueryCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(node, CustomQueryCacheType.type, options);
      }

      /** @deprecated */
      public static CustomQueryCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, CustomQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomQueryCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomQueryCacheType)XmlBeans.getContextTypeLoader().parse(xis, CustomQueryCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomQueryCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomQueryCacheType.type, options);
      }

      private Factory() {
      }
   }
}
