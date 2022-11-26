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

public interface CustomDriverDataSourceType extends DriverDataSourceType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomDriverDataSourceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customdriverdatasourcetype9537type");

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
      public static CustomDriverDataSourceType newInstance() {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().newInstance(CustomDriverDataSourceType.type, (XmlOptions)null);
      }

      public static CustomDriverDataSourceType newInstance(XmlOptions options) {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().newInstance(CustomDriverDataSourceType.type, options);
      }

      public static CustomDriverDataSourceType parse(String xmlAsString) throws XmlException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomDriverDataSourceType.type, (XmlOptions)null);
      }

      public static CustomDriverDataSourceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomDriverDataSourceType.type, options);
      }

      public static CustomDriverDataSourceType parse(File file) throws XmlException, IOException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(file, CustomDriverDataSourceType.type, (XmlOptions)null);
      }

      public static CustomDriverDataSourceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(file, CustomDriverDataSourceType.type, options);
      }

      public static CustomDriverDataSourceType parse(URL u) throws XmlException, IOException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(u, CustomDriverDataSourceType.type, (XmlOptions)null);
      }

      public static CustomDriverDataSourceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(u, CustomDriverDataSourceType.type, options);
      }

      public static CustomDriverDataSourceType parse(InputStream is) throws XmlException, IOException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(is, CustomDriverDataSourceType.type, (XmlOptions)null);
      }

      public static CustomDriverDataSourceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(is, CustomDriverDataSourceType.type, options);
      }

      public static CustomDriverDataSourceType parse(Reader r) throws XmlException, IOException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(r, CustomDriverDataSourceType.type, (XmlOptions)null);
      }

      public static CustomDriverDataSourceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(r, CustomDriverDataSourceType.type, options);
      }

      public static CustomDriverDataSourceType parse(XMLStreamReader sr) throws XmlException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(sr, CustomDriverDataSourceType.type, (XmlOptions)null);
      }

      public static CustomDriverDataSourceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(sr, CustomDriverDataSourceType.type, options);
      }

      public static CustomDriverDataSourceType parse(Node node) throws XmlException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(node, CustomDriverDataSourceType.type, (XmlOptions)null);
      }

      public static CustomDriverDataSourceType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(node, CustomDriverDataSourceType.type, options);
      }

      /** @deprecated */
      public static CustomDriverDataSourceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xis, CustomDriverDataSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomDriverDataSourceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xis, CustomDriverDataSourceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomDriverDataSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomDriverDataSourceType.type, options);
      }

      private Factory() {
      }
   }
}
