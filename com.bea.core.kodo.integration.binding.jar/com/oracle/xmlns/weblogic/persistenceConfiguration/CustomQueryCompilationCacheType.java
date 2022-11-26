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

public interface CustomQueryCompilationCacheType extends QueryCompilationCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomQueryCompilationCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customquerycompilationcachetype5a43type");

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
      public static CustomQueryCompilationCacheType newInstance() {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().newInstance(CustomQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCompilationCacheType newInstance(XmlOptions options) {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().newInstance(CustomQueryCompilationCacheType.type, options);
      }

      public static CustomQueryCompilationCacheType parse(String xmlAsString) throws XmlException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCompilationCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomQueryCompilationCacheType.type, options);
      }

      public static CustomQueryCompilationCacheType parse(File file) throws XmlException, IOException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(file, CustomQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCompilationCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(file, CustomQueryCompilationCacheType.type, options);
      }

      public static CustomQueryCompilationCacheType parse(URL u) throws XmlException, IOException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(u, CustomQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCompilationCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(u, CustomQueryCompilationCacheType.type, options);
      }

      public static CustomQueryCompilationCacheType parse(InputStream is) throws XmlException, IOException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(is, CustomQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCompilationCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(is, CustomQueryCompilationCacheType.type, options);
      }

      public static CustomQueryCompilationCacheType parse(Reader r) throws XmlException, IOException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(r, CustomQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCompilationCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(r, CustomQueryCompilationCacheType.type, options);
      }

      public static CustomQueryCompilationCacheType parse(XMLStreamReader sr) throws XmlException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(sr, CustomQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCompilationCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(sr, CustomQueryCompilationCacheType.type, options);
      }

      public static CustomQueryCompilationCacheType parse(Node node) throws XmlException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(node, CustomQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static CustomQueryCompilationCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(node, CustomQueryCompilationCacheType.type, options);
      }

      /** @deprecated */
      public static CustomQueryCompilationCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(xis, CustomQueryCompilationCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomQueryCompilationCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(xis, CustomQueryCompilationCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomQueryCompilationCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomQueryCompilationCacheType.type, options);
      }

      private Factory() {
      }
   }
}
