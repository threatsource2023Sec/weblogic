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

public interface CustomOrphanedKeyActionType extends LogOrphanedKeyActionType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomOrphanedKeyActionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customorphanedkeyactiontypeba4ctype");

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
      public static CustomOrphanedKeyActionType newInstance() {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().newInstance(CustomOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static CustomOrphanedKeyActionType newInstance(XmlOptions options) {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().newInstance(CustomOrphanedKeyActionType.type, options);
      }

      public static CustomOrphanedKeyActionType parse(String xmlAsString) throws XmlException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static CustomOrphanedKeyActionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomOrphanedKeyActionType.type, options);
      }

      public static CustomOrphanedKeyActionType parse(File file) throws XmlException, IOException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(file, CustomOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static CustomOrphanedKeyActionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(file, CustomOrphanedKeyActionType.type, options);
      }

      public static CustomOrphanedKeyActionType parse(URL u) throws XmlException, IOException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(u, CustomOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static CustomOrphanedKeyActionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(u, CustomOrphanedKeyActionType.type, options);
      }

      public static CustomOrphanedKeyActionType parse(InputStream is) throws XmlException, IOException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(is, CustomOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static CustomOrphanedKeyActionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(is, CustomOrphanedKeyActionType.type, options);
      }

      public static CustomOrphanedKeyActionType parse(Reader r) throws XmlException, IOException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(r, CustomOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static CustomOrphanedKeyActionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(r, CustomOrphanedKeyActionType.type, options);
      }

      public static CustomOrphanedKeyActionType parse(XMLStreamReader sr) throws XmlException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(sr, CustomOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static CustomOrphanedKeyActionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(sr, CustomOrphanedKeyActionType.type, options);
      }

      public static CustomOrphanedKeyActionType parse(Node node) throws XmlException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(node, CustomOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static CustomOrphanedKeyActionType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(node, CustomOrphanedKeyActionType.type, options);
      }

      /** @deprecated */
      public static CustomOrphanedKeyActionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xis, CustomOrphanedKeyActionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomOrphanedKeyActionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xis, CustomOrphanedKeyActionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomOrphanedKeyActionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomOrphanedKeyActionType.type, options);
      }

      private Factory() {
      }
   }
}
