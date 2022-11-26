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

public interface CustomFilterListenerType extends FilterListenerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomFilterListenerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customfilterlistenertype244dtype");

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
      public static CustomFilterListenerType newInstance() {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().newInstance(CustomFilterListenerType.type, (XmlOptions)null);
      }

      public static CustomFilterListenerType newInstance(XmlOptions options) {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().newInstance(CustomFilterListenerType.type, options);
      }

      public static CustomFilterListenerType parse(String xmlAsString) throws XmlException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomFilterListenerType.type, (XmlOptions)null);
      }

      public static CustomFilterListenerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomFilterListenerType.type, options);
      }

      public static CustomFilterListenerType parse(File file) throws XmlException, IOException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(file, CustomFilterListenerType.type, (XmlOptions)null);
      }

      public static CustomFilterListenerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(file, CustomFilterListenerType.type, options);
      }

      public static CustomFilterListenerType parse(URL u) throws XmlException, IOException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(u, CustomFilterListenerType.type, (XmlOptions)null);
      }

      public static CustomFilterListenerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(u, CustomFilterListenerType.type, options);
      }

      public static CustomFilterListenerType parse(InputStream is) throws XmlException, IOException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(is, CustomFilterListenerType.type, (XmlOptions)null);
      }

      public static CustomFilterListenerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(is, CustomFilterListenerType.type, options);
      }

      public static CustomFilterListenerType parse(Reader r) throws XmlException, IOException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(r, CustomFilterListenerType.type, (XmlOptions)null);
      }

      public static CustomFilterListenerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(r, CustomFilterListenerType.type, options);
      }

      public static CustomFilterListenerType parse(XMLStreamReader sr) throws XmlException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(sr, CustomFilterListenerType.type, (XmlOptions)null);
      }

      public static CustomFilterListenerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(sr, CustomFilterListenerType.type, options);
      }

      public static CustomFilterListenerType parse(Node node) throws XmlException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(node, CustomFilterListenerType.type, (XmlOptions)null);
      }

      public static CustomFilterListenerType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(node, CustomFilterListenerType.type, options);
      }

      /** @deprecated */
      public static CustomFilterListenerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(xis, CustomFilterListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomFilterListenerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomFilterListenerType)XmlBeans.getContextTypeLoader().parse(xis, CustomFilterListenerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomFilterListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomFilterListenerType.type, options);
      }

      private Factory() {
      }
   }
}
