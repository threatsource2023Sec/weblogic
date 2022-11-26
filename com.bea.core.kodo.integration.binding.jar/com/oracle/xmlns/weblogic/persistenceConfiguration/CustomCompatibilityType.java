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

public interface CustomCompatibilityType extends PersistenceCompatibilityType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomCompatibilityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customcompatibilitytypec626type");

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
      public static CustomCompatibilityType newInstance() {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().newInstance(CustomCompatibilityType.type, (XmlOptions)null);
      }

      public static CustomCompatibilityType newInstance(XmlOptions options) {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().newInstance(CustomCompatibilityType.type, options);
      }

      public static CustomCompatibilityType parse(String xmlAsString) throws XmlException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomCompatibilityType.type, (XmlOptions)null);
      }

      public static CustomCompatibilityType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomCompatibilityType.type, options);
      }

      public static CustomCompatibilityType parse(File file) throws XmlException, IOException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(file, CustomCompatibilityType.type, (XmlOptions)null);
      }

      public static CustomCompatibilityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(file, CustomCompatibilityType.type, options);
      }

      public static CustomCompatibilityType parse(URL u) throws XmlException, IOException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(u, CustomCompatibilityType.type, (XmlOptions)null);
      }

      public static CustomCompatibilityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(u, CustomCompatibilityType.type, options);
      }

      public static CustomCompatibilityType parse(InputStream is) throws XmlException, IOException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(is, CustomCompatibilityType.type, (XmlOptions)null);
      }

      public static CustomCompatibilityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(is, CustomCompatibilityType.type, options);
      }

      public static CustomCompatibilityType parse(Reader r) throws XmlException, IOException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(r, CustomCompatibilityType.type, (XmlOptions)null);
      }

      public static CustomCompatibilityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(r, CustomCompatibilityType.type, options);
      }

      public static CustomCompatibilityType parse(XMLStreamReader sr) throws XmlException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(sr, CustomCompatibilityType.type, (XmlOptions)null);
      }

      public static CustomCompatibilityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(sr, CustomCompatibilityType.type, options);
      }

      public static CustomCompatibilityType parse(Node node) throws XmlException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(node, CustomCompatibilityType.type, (XmlOptions)null);
      }

      public static CustomCompatibilityType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(node, CustomCompatibilityType.type, options);
      }

      /** @deprecated */
      public static CustomCompatibilityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(xis, CustomCompatibilityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomCompatibilityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomCompatibilityType)XmlBeans.getContextTypeLoader().parse(xis, CustomCompatibilityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomCompatibilityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomCompatibilityType.type, options);
      }

      private Factory() {
      }
   }
}
