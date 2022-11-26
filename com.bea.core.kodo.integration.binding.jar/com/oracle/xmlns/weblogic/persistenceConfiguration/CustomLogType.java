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

public interface CustomLogType extends LogType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomLogType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customlogtype9992type");

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
      public static CustomLogType newInstance() {
         return (CustomLogType)XmlBeans.getContextTypeLoader().newInstance(CustomLogType.type, (XmlOptions)null);
      }

      public static CustomLogType newInstance(XmlOptions options) {
         return (CustomLogType)XmlBeans.getContextTypeLoader().newInstance(CustomLogType.type, options);
      }

      public static CustomLogType parse(String xmlAsString) throws XmlException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomLogType.type, (XmlOptions)null);
      }

      public static CustomLogType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomLogType.type, options);
      }

      public static CustomLogType parse(File file) throws XmlException, IOException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(file, CustomLogType.type, (XmlOptions)null);
      }

      public static CustomLogType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(file, CustomLogType.type, options);
      }

      public static CustomLogType parse(URL u) throws XmlException, IOException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(u, CustomLogType.type, (XmlOptions)null);
      }

      public static CustomLogType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(u, CustomLogType.type, options);
      }

      public static CustomLogType parse(InputStream is) throws XmlException, IOException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(is, CustomLogType.type, (XmlOptions)null);
      }

      public static CustomLogType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(is, CustomLogType.type, options);
      }

      public static CustomLogType parse(Reader r) throws XmlException, IOException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(r, CustomLogType.type, (XmlOptions)null);
      }

      public static CustomLogType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(r, CustomLogType.type, options);
      }

      public static CustomLogType parse(XMLStreamReader sr) throws XmlException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(sr, CustomLogType.type, (XmlOptions)null);
      }

      public static CustomLogType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(sr, CustomLogType.type, options);
      }

      public static CustomLogType parse(Node node) throws XmlException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(node, CustomLogType.type, (XmlOptions)null);
      }

      public static CustomLogType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(node, CustomLogType.type, options);
      }

      /** @deprecated */
      public static CustomLogType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(xis, CustomLogType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomLogType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomLogType)XmlBeans.getContextTypeLoader().parse(xis, CustomLogType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomLogType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomLogType.type, options);
      }

      private Factory() {
      }
   }
}
