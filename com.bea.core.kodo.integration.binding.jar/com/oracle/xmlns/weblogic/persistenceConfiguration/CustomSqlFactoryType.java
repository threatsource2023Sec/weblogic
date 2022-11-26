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

public interface CustomSqlFactoryType extends SqlFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomSqlFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customsqlfactorytyped8ebtype");

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
      public static CustomSqlFactoryType newInstance() {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().newInstance(CustomSqlFactoryType.type, (XmlOptions)null);
      }

      public static CustomSqlFactoryType newInstance(XmlOptions options) {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().newInstance(CustomSqlFactoryType.type, options);
      }

      public static CustomSqlFactoryType parse(String xmlAsString) throws XmlException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomSqlFactoryType.type, (XmlOptions)null);
      }

      public static CustomSqlFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomSqlFactoryType.type, options);
      }

      public static CustomSqlFactoryType parse(File file) throws XmlException, IOException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(file, CustomSqlFactoryType.type, (XmlOptions)null);
      }

      public static CustomSqlFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(file, CustomSqlFactoryType.type, options);
      }

      public static CustomSqlFactoryType parse(URL u) throws XmlException, IOException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(u, CustomSqlFactoryType.type, (XmlOptions)null);
      }

      public static CustomSqlFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(u, CustomSqlFactoryType.type, options);
      }

      public static CustomSqlFactoryType parse(InputStream is) throws XmlException, IOException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(is, CustomSqlFactoryType.type, (XmlOptions)null);
      }

      public static CustomSqlFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(is, CustomSqlFactoryType.type, options);
      }

      public static CustomSqlFactoryType parse(Reader r) throws XmlException, IOException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(r, CustomSqlFactoryType.type, (XmlOptions)null);
      }

      public static CustomSqlFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(r, CustomSqlFactoryType.type, options);
      }

      public static CustomSqlFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(sr, CustomSqlFactoryType.type, (XmlOptions)null);
      }

      public static CustomSqlFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(sr, CustomSqlFactoryType.type, options);
      }

      public static CustomSqlFactoryType parse(Node node) throws XmlException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(node, CustomSqlFactoryType.type, (XmlOptions)null);
      }

      public static CustomSqlFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(node, CustomSqlFactoryType.type, options);
      }

      /** @deprecated */
      public static CustomSqlFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(xis, CustomSqlFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomSqlFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomSqlFactoryType)XmlBeans.getContextTypeLoader().parse(xis, CustomSqlFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomSqlFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomSqlFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
