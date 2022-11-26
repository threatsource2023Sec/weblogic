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

public interface CustomBrokerFactoryType extends BrokerFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomBrokerFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("custombrokerfactorytype3e7atype");

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
      public static CustomBrokerFactoryType newInstance() {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().newInstance(CustomBrokerFactoryType.type, (XmlOptions)null);
      }

      public static CustomBrokerFactoryType newInstance(XmlOptions options) {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().newInstance(CustomBrokerFactoryType.type, options);
      }

      public static CustomBrokerFactoryType parse(String xmlAsString) throws XmlException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomBrokerFactoryType.type, (XmlOptions)null);
      }

      public static CustomBrokerFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomBrokerFactoryType.type, options);
      }

      public static CustomBrokerFactoryType parse(File file) throws XmlException, IOException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(file, CustomBrokerFactoryType.type, (XmlOptions)null);
      }

      public static CustomBrokerFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(file, CustomBrokerFactoryType.type, options);
      }

      public static CustomBrokerFactoryType parse(URL u) throws XmlException, IOException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(u, CustomBrokerFactoryType.type, (XmlOptions)null);
      }

      public static CustomBrokerFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(u, CustomBrokerFactoryType.type, options);
      }

      public static CustomBrokerFactoryType parse(InputStream is) throws XmlException, IOException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(is, CustomBrokerFactoryType.type, (XmlOptions)null);
      }

      public static CustomBrokerFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(is, CustomBrokerFactoryType.type, options);
      }

      public static CustomBrokerFactoryType parse(Reader r) throws XmlException, IOException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(r, CustomBrokerFactoryType.type, (XmlOptions)null);
      }

      public static CustomBrokerFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(r, CustomBrokerFactoryType.type, options);
      }

      public static CustomBrokerFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(sr, CustomBrokerFactoryType.type, (XmlOptions)null);
      }

      public static CustomBrokerFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(sr, CustomBrokerFactoryType.type, options);
      }

      public static CustomBrokerFactoryType parse(Node node) throws XmlException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(node, CustomBrokerFactoryType.type, (XmlOptions)null);
      }

      public static CustomBrokerFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(node, CustomBrokerFactoryType.type, options);
      }

      /** @deprecated */
      public static CustomBrokerFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xis, CustomBrokerFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomBrokerFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xis, CustomBrokerFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomBrokerFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomBrokerFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
