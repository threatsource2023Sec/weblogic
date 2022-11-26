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

public interface CustomPersistenceServerType extends PersistenceServerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomPersistenceServerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("custompersistenceservertypee57ftype");

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
      public static CustomPersistenceServerType newInstance() {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().newInstance(CustomPersistenceServerType.type, (XmlOptions)null);
      }

      public static CustomPersistenceServerType newInstance(XmlOptions options) {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().newInstance(CustomPersistenceServerType.type, options);
      }

      public static CustomPersistenceServerType parse(String xmlAsString) throws XmlException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomPersistenceServerType.type, (XmlOptions)null);
      }

      public static CustomPersistenceServerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomPersistenceServerType.type, options);
      }

      public static CustomPersistenceServerType parse(File file) throws XmlException, IOException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(file, CustomPersistenceServerType.type, (XmlOptions)null);
      }

      public static CustomPersistenceServerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(file, CustomPersistenceServerType.type, options);
      }

      public static CustomPersistenceServerType parse(URL u) throws XmlException, IOException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(u, CustomPersistenceServerType.type, (XmlOptions)null);
      }

      public static CustomPersistenceServerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(u, CustomPersistenceServerType.type, options);
      }

      public static CustomPersistenceServerType parse(InputStream is) throws XmlException, IOException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(is, CustomPersistenceServerType.type, (XmlOptions)null);
      }

      public static CustomPersistenceServerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(is, CustomPersistenceServerType.type, options);
      }

      public static CustomPersistenceServerType parse(Reader r) throws XmlException, IOException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(r, CustomPersistenceServerType.type, (XmlOptions)null);
      }

      public static CustomPersistenceServerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(r, CustomPersistenceServerType.type, options);
      }

      public static CustomPersistenceServerType parse(XMLStreamReader sr) throws XmlException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(sr, CustomPersistenceServerType.type, (XmlOptions)null);
      }

      public static CustomPersistenceServerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(sr, CustomPersistenceServerType.type, options);
      }

      public static CustomPersistenceServerType parse(Node node) throws XmlException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(node, CustomPersistenceServerType.type, (XmlOptions)null);
      }

      public static CustomPersistenceServerType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(node, CustomPersistenceServerType.type, options);
      }

      /** @deprecated */
      public static CustomPersistenceServerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(xis, CustomPersistenceServerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomPersistenceServerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomPersistenceServerType)XmlBeans.getContextTypeLoader().parse(xis, CustomPersistenceServerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomPersistenceServerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomPersistenceServerType.type, options);
      }

      private Factory() {
      }
   }
}
