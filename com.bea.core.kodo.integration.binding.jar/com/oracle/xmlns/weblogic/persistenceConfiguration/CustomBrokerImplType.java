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

public interface CustomBrokerImplType extends BrokerImplType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomBrokerImplType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("custombrokerimpltype2442type");

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
      public static CustomBrokerImplType newInstance() {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().newInstance(CustomBrokerImplType.type, (XmlOptions)null);
      }

      public static CustomBrokerImplType newInstance(XmlOptions options) {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().newInstance(CustomBrokerImplType.type, options);
      }

      public static CustomBrokerImplType parse(String xmlAsString) throws XmlException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomBrokerImplType.type, (XmlOptions)null);
      }

      public static CustomBrokerImplType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomBrokerImplType.type, options);
      }

      public static CustomBrokerImplType parse(File file) throws XmlException, IOException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(file, CustomBrokerImplType.type, (XmlOptions)null);
      }

      public static CustomBrokerImplType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(file, CustomBrokerImplType.type, options);
      }

      public static CustomBrokerImplType parse(URL u) throws XmlException, IOException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(u, CustomBrokerImplType.type, (XmlOptions)null);
      }

      public static CustomBrokerImplType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(u, CustomBrokerImplType.type, options);
      }

      public static CustomBrokerImplType parse(InputStream is) throws XmlException, IOException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(is, CustomBrokerImplType.type, (XmlOptions)null);
      }

      public static CustomBrokerImplType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(is, CustomBrokerImplType.type, options);
      }

      public static CustomBrokerImplType parse(Reader r) throws XmlException, IOException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(r, CustomBrokerImplType.type, (XmlOptions)null);
      }

      public static CustomBrokerImplType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(r, CustomBrokerImplType.type, options);
      }

      public static CustomBrokerImplType parse(XMLStreamReader sr) throws XmlException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(sr, CustomBrokerImplType.type, (XmlOptions)null);
      }

      public static CustomBrokerImplType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(sr, CustomBrokerImplType.type, options);
      }

      public static CustomBrokerImplType parse(Node node) throws XmlException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(node, CustomBrokerImplType.type, (XmlOptions)null);
      }

      public static CustomBrokerImplType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(node, CustomBrokerImplType.type, options);
      }

      /** @deprecated */
      public static CustomBrokerImplType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(xis, CustomBrokerImplType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomBrokerImplType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomBrokerImplType)XmlBeans.getContextTypeLoader().parse(xis, CustomBrokerImplType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomBrokerImplType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomBrokerImplType.type, options);
      }

      private Factory() {
      }
   }
}
