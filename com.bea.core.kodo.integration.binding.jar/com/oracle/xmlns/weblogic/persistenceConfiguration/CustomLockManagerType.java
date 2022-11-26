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

public interface CustomLockManagerType extends LockManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomLockManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customlockmanagertype5925type");

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
      public static CustomLockManagerType newInstance() {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().newInstance(CustomLockManagerType.type, (XmlOptions)null);
      }

      public static CustomLockManagerType newInstance(XmlOptions options) {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().newInstance(CustomLockManagerType.type, options);
      }

      public static CustomLockManagerType parse(String xmlAsString) throws XmlException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomLockManagerType.type, (XmlOptions)null);
      }

      public static CustomLockManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomLockManagerType.type, options);
      }

      public static CustomLockManagerType parse(File file) throws XmlException, IOException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(file, CustomLockManagerType.type, (XmlOptions)null);
      }

      public static CustomLockManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(file, CustomLockManagerType.type, options);
      }

      public static CustomLockManagerType parse(URL u) throws XmlException, IOException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(u, CustomLockManagerType.type, (XmlOptions)null);
      }

      public static CustomLockManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(u, CustomLockManagerType.type, options);
      }

      public static CustomLockManagerType parse(InputStream is) throws XmlException, IOException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(is, CustomLockManagerType.type, (XmlOptions)null);
      }

      public static CustomLockManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(is, CustomLockManagerType.type, options);
      }

      public static CustomLockManagerType parse(Reader r) throws XmlException, IOException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(r, CustomLockManagerType.type, (XmlOptions)null);
      }

      public static CustomLockManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(r, CustomLockManagerType.type, options);
      }

      public static CustomLockManagerType parse(XMLStreamReader sr) throws XmlException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(sr, CustomLockManagerType.type, (XmlOptions)null);
      }

      public static CustomLockManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(sr, CustomLockManagerType.type, options);
      }

      public static CustomLockManagerType parse(Node node) throws XmlException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(node, CustomLockManagerType.type, (XmlOptions)null);
      }

      public static CustomLockManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(node, CustomLockManagerType.type, options);
      }

      /** @deprecated */
      public static CustomLockManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(xis, CustomLockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomLockManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomLockManagerType)XmlBeans.getContextTypeLoader().parse(xis, CustomLockManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomLockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomLockManagerType.type, options);
      }

      private Factory() {
      }
   }
}
