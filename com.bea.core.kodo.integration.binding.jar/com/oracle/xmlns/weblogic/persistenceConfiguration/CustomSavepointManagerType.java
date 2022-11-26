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

public interface CustomSavepointManagerType extends SavepointManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomSavepointManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customsavepointmanagertype57a3type");

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
      public static CustomSavepointManagerType newInstance() {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().newInstance(CustomSavepointManagerType.type, (XmlOptions)null);
      }

      public static CustomSavepointManagerType newInstance(XmlOptions options) {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().newInstance(CustomSavepointManagerType.type, options);
      }

      public static CustomSavepointManagerType parse(String xmlAsString) throws XmlException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomSavepointManagerType.type, (XmlOptions)null);
      }

      public static CustomSavepointManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomSavepointManagerType.type, options);
      }

      public static CustomSavepointManagerType parse(File file) throws XmlException, IOException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(file, CustomSavepointManagerType.type, (XmlOptions)null);
      }

      public static CustomSavepointManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(file, CustomSavepointManagerType.type, options);
      }

      public static CustomSavepointManagerType parse(URL u) throws XmlException, IOException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(u, CustomSavepointManagerType.type, (XmlOptions)null);
      }

      public static CustomSavepointManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(u, CustomSavepointManagerType.type, options);
      }

      public static CustomSavepointManagerType parse(InputStream is) throws XmlException, IOException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(is, CustomSavepointManagerType.type, (XmlOptions)null);
      }

      public static CustomSavepointManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(is, CustomSavepointManagerType.type, options);
      }

      public static CustomSavepointManagerType parse(Reader r) throws XmlException, IOException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(r, CustomSavepointManagerType.type, (XmlOptions)null);
      }

      public static CustomSavepointManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(r, CustomSavepointManagerType.type, options);
      }

      public static CustomSavepointManagerType parse(XMLStreamReader sr) throws XmlException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(sr, CustomSavepointManagerType.type, (XmlOptions)null);
      }

      public static CustomSavepointManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(sr, CustomSavepointManagerType.type, options);
      }

      public static CustomSavepointManagerType parse(Node node) throws XmlException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(node, CustomSavepointManagerType.type, (XmlOptions)null);
      }

      public static CustomSavepointManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(node, CustomSavepointManagerType.type, options);
      }

      /** @deprecated */
      public static CustomSavepointManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(xis, CustomSavepointManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomSavepointManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomSavepointManagerType)XmlBeans.getContextTypeLoader().parse(xis, CustomSavepointManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomSavepointManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomSavepointManagerType.type, options);
      }

      private Factory() {
      }
   }
}
