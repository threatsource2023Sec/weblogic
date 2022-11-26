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

public interface CustomSchemaFactoryType extends SchemaFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomSchemaFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customschemafactorytype2fb2type");

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
      public static CustomSchemaFactoryType newInstance() {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(CustomSchemaFactoryType.type, (XmlOptions)null);
      }

      public static CustomSchemaFactoryType newInstance(XmlOptions options) {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(CustomSchemaFactoryType.type, options);
      }

      public static CustomSchemaFactoryType parse(String xmlAsString) throws XmlException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomSchemaFactoryType.type, (XmlOptions)null);
      }

      public static CustomSchemaFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomSchemaFactoryType.type, options);
      }

      public static CustomSchemaFactoryType parse(File file) throws XmlException, IOException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, CustomSchemaFactoryType.type, (XmlOptions)null);
      }

      public static CustomSchemaFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, CustomSchemaFactoryType.type, options);
      }

      public static CustomSchemaFactoryType parse(URL u) throws XmlException, IOException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, CustomSchemaFactoryType.type, (XmlOptions)null);
      }

      public static CustomSchemaFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, CustomSchemaFactoryType.type, options);
      }

      public static CustomSchemaFactoryType parse(InputStream is) throws XmlException, IOException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, CustomSchemaFactoryType.type, (XmlOptions)null);
      }

      public static CustomSchemaFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, CustomSchemaFactoryType.type, options);
      }

      public static CustomSchemaFactoryType parse(Reader r) throws XmlException, IOException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, CustomSchemaFactoryType.type, (XmlOptions)null);
      }

      public static CustomSchemaFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, CustomSchemaFactoryType.type, options);
      }

      public static CustomSchemaFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, CustomSchemaFactoryType.type, (XmlOptions)null);
      }

      public static CustomSchemaFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, CustomSchemaFactoryType.type, options);
      }

      public static CustomSchemaFactoryType parse(Node node) throws XmlException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, CustomSchemaFactoryType.type, (XmlOptions)null);
      }

      public static CustomSchemaFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, CustomSchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static CustomSchemaFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, CustomSchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomSchemaFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, CustomSchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomSchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomSchemaFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
