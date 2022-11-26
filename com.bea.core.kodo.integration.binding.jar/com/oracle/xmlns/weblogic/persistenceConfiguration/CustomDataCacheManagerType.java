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

public interface CustomDataCacheManagerType extends DataCacheManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomDataCacheManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customdatacachemanagertype3a71type");

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
      public static CustomDataCacheManagerType newInstance() {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().newInstance(CustomDataCacheManagerType.type, (XmlOptions)null);
      }

      public static CustomDataCacheManagerType newInstance(XmlOptions options) {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().newInstance(CustomDataCacheManagerType.type, options);
      }

      public static CustomDataCacheManagerType parse(String xmlAsString) throws XmlException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomDataCacheManagerType.type, (XmlOptions)null);
      }

      public static CustomDataCacheManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomDataCacheManagerType.type, options);
      }

      public static CustomDataCacheManagerType parse(File file) throws XmlException, IOException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(file, CustomDataCacheManagerType.type, (XmlOptions)null);
      }

      public static CustomDataCacheManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(file, CustomDataCacheManagerType.type, options);
      }

      public static CustomDataCacheManagerType parse(URL u) throws XmlException, IOException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(u, CustomDataCacheManagerType.type, (XmlOptions)null);
      }

      public static CustomDataCacheManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(u, CustomDataCacheManagerType.type, options);
      }

      public static CustomDataCacheManagerType parse(InputStream is) throws XmlException, IOException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(is, CustomDataCacheManagerType.type, (XmlOptions)null);
      }

      public static CustomDataCacheManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(is, CustomDataCacheManagerType.type, options);
      }

      public static CustomDataCacheManagerType parse(Reader r) throws XmlException, IOException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(r, CustomDataCacheManagerType.type, (XmlOptions)null);
      }

      public static CustomDataCacheManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(r, CustomDataCacheManagerType.type, options);
      }

      public static CustomDataCacheManagerType parse(XMLStreamReader sr) throws XmlException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(sr, CustomDataCacheManagerType.type, (XmlOptions)null);
      }

      public static CustomDataCacheManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(sr, CustomDataCacheManagerType.type, options);
      }

      public static CustomDataCacheManagerType parse(Node node) throws XmlException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(node, CustomDataCacheManagerType.type, (XmlOptions)null);
      }

      public static CustomDataCacheManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(node, CustomDataCacheManagerType.type, options);
      }

      /** @deprecated */
      public static CustomDataCacheManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xis, CustomDataCacheManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomDataCacheManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomDataCacheManagerType)XmlBeans.getContextTypeLoader().parse(xis, CustomDataCacheManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomDataCacheManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomDataCacheManagerType.type, options);
      }

      private Factory() {
      }
   }
}
