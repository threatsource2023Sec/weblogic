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

public interface CustomMetaDataFactoryType extends MetaDataFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomMetaDataFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("custommetadatafactorytype8587type");

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
      public static CustomMetaDataFactoryType newInstance() {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().newInstance(CustomMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataFactoryType newInstance(XmlOptions options) {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().newInstance(CustomMetaDataFactoryType.type, options);
      }

      public static CustomMetaDataFactoryType parse(String xmlAsString) throws XmlException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomMetaDataFactoryType.type, options);
      }

      public static CustomMetaDataFactoryType parse(File file) throws XmlException, IOException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(file, CustomMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(file, CustomMetaDataFactoryType.type, options);
      }

      public static CustomMetaDataFactoryType parse(URL u) throws XmlException, IOException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(u, CustomMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(u, CustomMetaDataFactoryType.type, options);
      }

      public static CustomMetaDataFactoryType parse(InputStream is) throws XmlException, IOException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(is, CustomMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(is, CustomMetaDataFactoryType.type, options);
      }

      public static CustomMetaDataFactoryType parse(Reader r) throws XmlException, IOException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(r, CustomMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(r, CustomMetaDataFactoryType.type, options);
      }

      public static CustomMetaDataFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(sr, CustomMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(sr, CustomMetaDataFactoryType.type, options);
      }

      public static CustomMetaDataFactoryType parse(Node node) throws XmlException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(node, CustomMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(node, CustomMetaDataFactoryType.type, options);
      }

      /** @deprecated */
      public static CustomMetaDataFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xis, CustomMetaDataFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomMetaDataFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xis, CustomMetaDataFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomMetaDataFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomMetaDataFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
