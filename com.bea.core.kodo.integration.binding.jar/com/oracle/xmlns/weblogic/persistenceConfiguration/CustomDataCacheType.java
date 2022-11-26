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

public interface CustomDataCacheType extends DataCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomDataCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customdatacachetype7751type");

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
      public static CustomDataCacheType newInstance() {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().newInstance(CustomDataCacheType.type, (XmlOptions)null);
      }

      public static CustomDataCacheType newInstance(XmlOptions options) {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().newInstance(CustomDataCacheType.type, options);
      }

      public static CustomDataCacheType parse(String xmlAsString) throws XmlException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomDataCacheType.type, (XmlOptions)null);
      }

      public static CustomDataCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomDataCacheType.type, options);
      }

      public static CustomDataCacheType parse(File file) throws XmlException, IOException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(file, CustomDataCacheType.type, (XmlOptions)null);
      }

      public static CustomDataCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(file, CustomDataCacheType.type, options);
      }

      public static CustomDataCacheType parse(URL u) throws XmlException, IOException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(u, CustomDataCacheType.type, (XmlOptions)null);
      }

      public static CustomDataCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(u, CustomDataCacheType.type, options);
      }

      public static CustomDataCacheType parse(InputStream is) throws XmlException, IOException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(is, CustomDataCacheType.type, (XmlOptions)null);
      }

      public static CustomDataCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(is, CustomDataCacheType.type, options);
      }

      public static CustomDataCacheType parse(Reader r) throws XmlException, IOException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(r, CustomDataCacheType.type, (XmlOptions)null);
      }

      public static CustomDataCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(r, CustomDataCacheType.type, options);
      }

      public static CustomDataCacheType parse(XMLStreamReader sr) throws XmlException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(sr, CustomDataCacheType.type, (XmlOptions)null);
      }

      public static CustomDataCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(sr, CustomDataCacheType.type, options);
      }

      public static CustomDataCacheType parse(Node node) throws XmlException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(node, CustomDataCacheType.type, (XmlOptions)null);
      }

      public static CustomDataCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(node, CustomDataCacheType.type, options);
      }

      /** @deprecated */
      public static CustomDataCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(xis, CustomDataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomDataCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomDataCacheType)XmlBeans.getContextTypeLoader().parse(xis, CustomDataCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomDataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomDataCacheType.type, options);
      }

      private Factory() {
      }
   }
}
