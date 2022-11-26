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

public interface CustomUpdateManagerType extends UpdateManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomUpdateManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customupdatemanagertypee887type");

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
      public static CustomUpdateManagerType newInstance() {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(CustomUpdateManagerType.type, (XmlOptions)null);
      }

      public static CustomUpdateManagerType newInstance(XmlOptions options) {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(CustomUpdateManagerType.type, options);
      }

      public static CustomUpdateManagerType parse(String xmlAsString) throws XmlException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomUpdateManagerType.type, (XmlOptions)null);
      }

      public static CustomUpdateManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomUpdateManagerType.type, options);
      }

      public static CustomUpdateManagerType parse(File file) throws XmlException, IOException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, CustomUpdateManagerType.type, (XmlOptions)null);
      }

      public static CustomUpdateManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, CustomUpdateManagerType.type, options);
      }

      public static CustomUpdateManagerType parse(URL u) throws XmlException, IOException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, CustomUpdateManagerType.type, (XmlOptions)null);
      }

      public static CustomUpdateManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, CustomUpdateManagerType.type, options);
      }

      public static CustomUpdateManagerType parse(InputStream is) throws XmlException, IOException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, CustomUpdateManagerType.type, (XmlOptions)null);
      }

      public static CustomUpdateManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, CustomUpdateManagerType.type, options);
      }

      public static CustomUpdateManagerType parse(Reader r) throws XmlException, IOException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, CustomUpdateManagerType.type, (XmlOptions)null);
      }

      public static CustomUpdateManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, CustomUpdateManagerType.type, options);
      }

      public static CustomUpdateManagerType parse(XMLStreamReader sr) throws XmlException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, CustomUpdateManagerType.type, (XmlOptions)null);
      }

      public static CustomUpdateManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, CustomUpdateManagerType.type, options);
      }

      public static CustomUpdateManagerType parse(Node node) throws XmlException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, CustomUpdateManagerType.type, (XmlOptions)null);
      }

      public static CustomUpdateManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, CustomUpdateManagerType.type, options);
      }

      /** @deprecated */
      public static CustomUpdateManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, CustomUpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomUpdateManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, CustomUpdateManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomUpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomUpdateManagerType.type, options);
      }

      private Factory() {
      }
   }
}
