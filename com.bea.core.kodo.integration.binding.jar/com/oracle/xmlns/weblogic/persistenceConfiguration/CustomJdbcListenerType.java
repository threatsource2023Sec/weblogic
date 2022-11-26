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

public interface CustomJdbcListenerType extends JdbcListenerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomJdbcListenerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customjdbclistenertypee990type");

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
      public static CustomJdbcListenerType newInstance() {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().newInstance(CustomJdbcListenerType.type, (XmlOptions)null);
      }

      public static CustomJdbcListenerType newInstance(XmlOptions options) {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().newInstance(CustomJdbcListenerType.type, options);
      }

      public static CustomJdbcListenerType parse(String xmlAsString) throws XmlException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomJdbcListenerType.type, (XmlOptions)null);
      }

      public static CustomJdbcListenerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomJdbcListenerType.type, options);
      }

      public static CustomJdbcListenerType parse(File file) throws XmlException, IOException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(file, CustomJdbcListenerType.type, (XmlOptions)null);
      }

      public static CustomJdbcListenerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(file, CustomJdbcListenerType.type, options);
      }

      public static CustomJdbcListenerType parse(URL u) throws XmlException, IOException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(u, CustomJdbcListenerType.type, (XmlOptions)null);
      }

      public static CustomJdbcListenerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(u, CustomJdbcListenerType.type, options);
      }

      public static CustomJdbcListenerType parse(InputStream is) throws XmlException, IOException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(is, CustomJdbcListenerType.type, (XmlOptions)null);
      }

      public static CustomJdbcListenerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(is, CustomJdbcListenerType.type, options);
      }

      public static CustomJdbcListenerType parse(Reader r) throws XmlException, IOException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(r, CustomJdbcListenerType.type, (XmlOptions)null);
      }

      public static CustomJdbcListenerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(r, CustomJdbcListenerType.type, options);
      }

      public static CustomJdbcListenerType parse(XMLStreamReader sr) throws XmlException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(sr, CustomJdbcListenerType.type, (XmlOptions)null);
      }

      public static CustomJdbcListenerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(sr, CustomJdbcListenerType.type, options);
      }

      public static CustomJdbcListenerType parse(Node node) throws XmlException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(node, CustomJdbcListenerType.type, (XmlOptions)null);
      }

      public static CustomJdbcListenerType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(node, CustomJdbcListenerType.type, options);
      }

      /** @deprecated */
      public static CustomJdbcListenerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(xis, CustomJdbcListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomJdbcListenerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomJdbcListenerType)XmlBeans.getContextTypeLoader().parse(xis, CustomJdbcListenerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomJdbcListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomJdbcListenerType.type, options);
      }

      private Factory() {
      }
   }
}
