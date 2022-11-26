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

public interface CustomMetaDataRepositoryType extends MetaDataRepositoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomMetaDataRepositoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("custommetadatarepositorytype3c6btype");

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
      public static CustomMetaDataRepositoryType newInstance() {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().newInstance(CustomMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataRepositoryType newInstance(XmlOptions options) {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().newInstance(CustomMetaDataRepositoryType.type, options);
      }

      public static CustomMetaDataRepositoryType parse(String xmlAsString) throws XmlException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataRepositoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomMetaDataRepositoryType.type, options);
      }

      public static CustomMetaDataRepositoryType parse(File file) throws XmlException, IOException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(file, CustomMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataRepositoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(file, CustomMetaDataRepositoryType.type, options);
      }

      public static CustomMetaDataRepositoryType parse(URL u) throws XmlException, IOException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(u, CustomMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataRepositoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(u, CustomMetaDataRepositoryType.type, options);
      }

      public static CustomMetaDataRepositoryType parse(InputStream is) throws XmlException, IOException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(is, CustomMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataRepositoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(is, CustomMetaDataRepositoryType.type, options);
      }

      public static CustomMetaDataRepositoryType parse(Reader r) throws XmlException, IOException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(r, CustomMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataRepositoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(r, CustomMetaDataRepositoryType.type, options);
      }

      public static CustomMetaDataRepositoryType parse(XMLStreamReader sr) throws XmlException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(sr, CustomMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataRepositoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(sr, CustomMetaDataRepositoryType.type, options);
      }

      public static CustomMetaDataRepositoryType parse(Node node) throws XmlException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(node, CustomMetaDataRepositoryType.type, (XmlOptions)null);
      }

      public static CustomMetaDataRepositoryType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(node, CustomMetaDataRepositoryType.type, options);
      }

      /** @deprecated */
      public static CustomMetaDataRepositoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(xis, CustomMetaDataRepositoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomMetaDataRepositoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomMetaDataRepositoryType)XmlBeans.getContextTypeLoader().parse(xis, CustomMetaDataRepositoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomMetaDataRepositoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomMetaDataRepositoryType.type, options);
      }

      private Factory() {
      }
   }
}
