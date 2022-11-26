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

public interface CustomConnectionDecoratorType extends ConnectionDecoratorType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomConnectionDecoratorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customconnectiondecoratortypea104type");

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
      public static CustomConnectionDecoratorType newInstance() {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().newInstance(CustomConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static CustomConnectionDecoratorType newInstance(XmlOptions options) {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().newInstance(CustomConnectionDecoratorType.type, options);
      }

      public static CustomConnectionDecoratorType parse(String xmlAsString) throws XmlException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static CustomConnectionDecoratorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomConnectionDecoratorType.type, options);
      }

      public static CustomConnectionDecoratorType parse(File file) throws XmlException, IOException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(file, CustomConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static CustomConnectionDecoratorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(file, CustomConnectionDecoratorType.type, options);
      }

      public static CustomConnectionDecoratorType parse(URL u) throws XmlException, IOException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(u, CustomConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static CustomConnectionDecoratorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(u, CustomConnectionDecoratorType.type, options);
      }

      public static CustomConnectionDecoratorType parse(InputStream is) throws XmlException, IOException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(is, CustomConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static CustomConnectionDecoratorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(is, CustomConnectionDecoratorType.type, options);
      }

      public static CustomConnectionDecoratorType parse(Reader r) throws XmlException, IOException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(r, CustomConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static CustomConnectionDecoratorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(r, CustomConnectionDecoratorType.type, options);
      }

      public static CustomConnectionDecoratorType parse(XMLStreamReader sr) throws XmlException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(sr, CustomConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static CustomConnectionDecoratorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(sr, CustomConnectionDecoratorType.type, options);
      }

      public static CustomConnectionDecoratorType parse(Node node) throws XmlException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(node, CustomConnectionDecoratorType.type, (XmlOptions)null);
      }

      public static CustomConnectionDecoratorType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(node, CustomConnectionDecoratorType.type, options);
      }

      /** @deprecated */
      public static CustomConnectionDecoratorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(xis, CustomConnectionDecoratorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomConnectionDecoratorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomConnectionDecoratorType)XmlBeans.getContextTypeLoader().parse(xis, CustomConnectionDecoratorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomConnectionDecoratorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomConnectionDecoratorType.type, options);
      }

      private Factory() {
      }
   }
}
