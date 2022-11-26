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

public interface CustomAggregateListenerType extends AggregateListenerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomAggregateListenerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customaggregatelistenertype748etype");

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
      public static CustomAggregateListenerType newInstance() {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().newInstance(CustomAggregateListenerType.type, (XmlOptions)null);
      }

      public static CustomAggregateListenerType newInstance(XmlOptions options) {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().newInstance(CustomAggregateListenerType.type, options);
      }

      public static CustomAggregateListenerType parse(String xmlAsString) throws XmlException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomAggregateListenerType.type, (XmlOptions)null);
      }

      public static CustomAggregateListenerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomAggregateListenerType.type, options);
      }

      public static CustomAggregateListenerType parse(File file) throws XmlException, IOException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(file, CustomAggregateListenerType.type, (XmlOptions)null);
      }

      public static CustomAggregateListenerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(file, CustomAggregateListenerType.type, options);
      }

      public static CustomAggregateListenerType parse(URL u) throws XmlException, IOException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(u, CustomAggregateListenerType.type, (XmlOptions)null);
      }

      public static CustomAggregateListenerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(u, CustomAggregateListenerType.type, options);
      }

      public static CustomAggregateListenerType parse(InputStream is) throws XmlException, IOException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(is, CustomAggregateListenerType.type, (XmlOptions)null);
      }

      public static CustomAggregateListenerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(is, CustomAggregateListenerType.type, options);
      }

      public static CustomAggregateListenerType parse(Reader r) throws XmlException, IOException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(r, CustomAggregateListenerType.type, (XmlOptions)null);
      }

      public static CustomAggregateListenerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(r, CustomAggregateListenerType.type, options);
      }

      public static CustomAggregateListenerType parse(XMLStreamReader sr) throws XmlException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(sr, CustomAggregateListenerType.type, (XmlOptions)null);
      }

      public static CustomAggregateListenerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(sr, CustomAggregateListenerType.type, options);
      }

      public static CustomAggregateListenerType parse(Node node) throws XmlException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(node, CustomAggregateListenerType.type, (XmlOptions)null);
      }

      public static CustomAggregateListenerType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(node, CustomAggregateListenerType.type, options);
      }

      /** @deprecated */
      public static CustomAggregateListenerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(xis, CustomAggregateListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomAggregateListenerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomAggregateListenerType)XmlBeans.getContextTypeLoader().parse(xis, CustomAggregateListenerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomAggregateListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomAggregateListenerType.type, options);
      }

      private Factory() {
      }
   }
}
