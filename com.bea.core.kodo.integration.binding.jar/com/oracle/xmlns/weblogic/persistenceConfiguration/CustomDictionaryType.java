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

public interface CustomDictionaryType extends DbDictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customdictionarytype0f7atype");

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
      public static CustomDictionaryType newInstance() {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().newInstance(CustomDictionaryType.type, (XmlOptions)null);
      }

      public static CustomDictionaryType newInstance(XmlOptions options) {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().newInstance(CustomDictionaryType.type, options);
      }

      public static CustomDictionaryType parse(String xmlAsString) throws XmlException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomDictionaryType.type, (XmlOptions)null);
      }

      public static CustomDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomDictionaryType.type, options);
      }

      public static CustomDictionaryType parse(File file) throws XmlException, IOException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(file, CustomDictionaryType.type, (XmlOptions)null);
      }

      public static CustomDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(file, CustomDictionaryType.type, options);
      }

      public static CustomDictionaryType parse(URL u) throws XmlException, IOException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(u, CustomDictionaryType.type, (XmlOptions)null);
      }

      public static CustomDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(u, CustomDictionaryType.type, options);
      }

      public static CustomDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(is, CustomDictionaryType.type, (XmlOptions)null);
      }

      public static CustomDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(is, CustomDictionaryType.type, options);
      }

      public static CustomDictionaryType parse(Reader r) throws XmlException, IOException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(r, CustomDictionaryType.type, (XmlOptions)null);
      }

      public static CustomDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(r, CustomDictionaryType.type, options);
      }

      public static CustomDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, CustomDictionaryType.type, (XmlOptions)null);
      }

      public static CustomDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, CustomDictionaryType.type, options);
      }

      public static CustomDictionaryType parse(Node node) throws XmlException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(node, CustomDictionaryType.type, (XmlOptions)null);
      }

      public static CustomDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(node, CustomDictionaryType.type, options);
      }

      /** @deprecated */
      public static CustomDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, CustomDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, CustomDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
