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

public interface CustomMappingDefaultsType extends MappingDefaultsType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomMappingDefaultsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("custommappingdefaultstype4e7ftype");

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
      public static CustomMappingDefaultsType newInstance() {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().newInstance(CustomMappingDefaultsType.type, (XmlOptions)null);
      }

      public static CustomMappingDefaultsType newInstance(XmlOptions options) {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().newInstance(CustomMappingDefaultsType.type, options);
      }

      public static CustomMappingDefaultsType parse(String xmlAsString) throws XmlException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomMappingDefaultsType.type, (XmlOptions)null);
      }

      public static CustomMappingDefaultsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomMappingDefaultsType.type, options);
      }

      public static CustomMappingDefaultsType parse(File file) throws XmlException, IOException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(file, CustomMappingDefaultsType.type, (XmlOptions)null);
      }

      public static CustomMappingDefaultsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(file, CustomMappingDefaultsType.type, options);
      }

      public static CustomMappingDefaultsType parse(URL u) throws XmlException, IOException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(u, CustomMappingDefaultsType.type, (XmlOptions)null);
      }

      public static CustomMappingDefaultsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(u, CustomMappingDefaultsType.type, options);
      }

      public static CustomMappingDefaultsType parse(InputStream is) throws XmlException, IOException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(is, CustomMappingDefaultsType.type, (XmlOptions)null);
      }

      public static CustomMappingDefaultsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(is, CustomMappingDefaultsType.type, options);
      }

      public static CustomMappingDefaultsType parse(Reader r) throws XmlException, IOException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(r, CustomMappingDefaultsType.type, (XmlOptions)null);
      }

      public static CustomMappingDefaultsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(r, CustomMappingDefaultsType.type, options);
      }

      public static CustomMappingDefaultsType parse(XMLStreamReader sr) throws XmlException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(sr, CustomMappingDefaultsType.type, (XmlOptions)null);
      }

      public static CustomMappingDefaultsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(sr, CustomMappingDefaultsType.type, options);
      }

      public static CustomMappingDefaultsType parse(Node node) throws XmlException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(node, CustomMappingDefaultsType.type, (XmlOptions)null);
      }

      public static CustomMappingDefaultsType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(node, CustomMappingDefaultsType.type, options);
      }

      /** @deprecated */
      public static CustomMappingDefaultsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xis, CustomMappingDefaultsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomMappingDefaultsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xis, CustomMappingDefaultsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomMappingDefaultsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomMappingDefaultsType.type, options);
      }

      private Factory() {
      }
   }
}
