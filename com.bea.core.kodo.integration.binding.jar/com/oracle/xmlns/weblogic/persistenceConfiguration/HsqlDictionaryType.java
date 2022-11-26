package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface HsqlDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(HsqlDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("hsqldictionarytype786ftype");

   boolean getCacheTables();

   XmlBoolean xgetCacheTables();

   boolean isSetCacheTables();

   void setCacheTables(boolean var1);

   void xsetCacheTables(XmlBoolean var1);

   void unsetCacheTables();

   public static final class Factory {
      public static HsqlDictionaryType newInstance() {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().newInstance(HsqlDictionaryType.type, (XmlOptions)null);
      }

      public static HsqlDictionaryType newInstance(XmlOptions options) {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().newInstance(HsqlDictionaryType.type, options);
      }

      public static HsqlDictionaryType parse(String xmlAsString) throws XmlException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HsqlDictionaryType.type, (XmlOptions)null);
      }

      public static HsqlDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HsqlDictionaryType.type, options);
      }

      public static HsqlDictionaryType parse(File file) throws XmlException, IOException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(file, HsqlDictionaryType.type, (XmlOptions)null);
      }

      public static HsqlDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(file, HsqlDictionaryType.type, options);
      }

      public static HsqlDictionaryType parse(URL u) throws XmlException, IOException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(u, HsqlDictionaryType.type, (XmlOptions)null);
      }

      public static HsqlDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(u, HsqlDictionaryType.type, options);
      }

      public static HsqlDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(is, HsqlDictionaryType.type, (XmlOptions)null);
      }

      public static HsqlDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(is, HsqlDictionaryType.type, options);
      }

      public static HsqlDictionaryType parse(Reader r) throws XmlException, IOException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(r, HsqlDictionaryType.type, (XmlOptions)null);
      }

      public static HsqlDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(r, HsqlDictionaryType.type, options);
      }

      public static HsqlDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, HsqlDictionaryType.type, (XmlOptions)null);
      }

      public static HsqlDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, HsqlDictionaryType.type, options);
      }

      public static HsqlDictionaryType parse(Node node) throws XmlException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(node, HsqlDictionaryType.type, (XmlOptions)null);
      }

      public static HsqlDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(node, HsqlDictionaryType.type, options);
      }

      /** @deprecated */
      public static HsqlDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, HsqlDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static HsqlDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (HsqlDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, HsqlDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HsqlDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HsqlDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
