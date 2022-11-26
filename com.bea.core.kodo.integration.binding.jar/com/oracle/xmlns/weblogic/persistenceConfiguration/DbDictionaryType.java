package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface DbDictionaryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DbDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("dbdictionarytype18c7type");

   String getName();

   XmlString xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   void unsetName();

   public static final class Factory {
      public static DbDictionaryType newInstance() {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().newInstance(DbDictionaryType.type, (XmlOptions)null);
      }

      public static DbDictionaryType newInstance(XmlOptions options) {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().newInstance(DbDictionaryType.type, options);
      }

      public static DbDictionaryType parse(String xmlAsString) throws XmlException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DbDictionaryType.type, (XmlOptions)null);
      }

      public static DbDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DbDictionaryType.type, options);
      }

      public static DbDictionaryType parse(File file) throws XmlException, IOException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(file, DbDictionaryType.type, (XmlOptions)null);
      }

      public static DbDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(file, DbDictionaryType.type, options);
      }

      public static DbDictionaryType parse(URL u) throws XmlException, IOException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(u, DbDictionaryType.type, (XmlOptions)null);
      }

      public static DbDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(u, DbDictionaryType.type, options);
      }

      public static DbDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(is, DbDictionaryType.type, (XmlOptions)null);
      }

      public static DbDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(is, DbDictionaryType.type, options);
      }

      public static DbDictionaryType parse(Reader r) throws XmlException, IOException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(r, DbDictionaryType.type, (XmlOptions)null);
      }

      public static DbDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(r, DbDictionaryType.type, options);
      }

      public static DbDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, DbDictionaryType.type, (XmlOptions)null);
      }

      public static DbDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, DbDictionaryType.type, options);
      }

      public static DbDictionaryType parse(Node node) throws XmlException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(node, DbDictionaryType.type, (XmlOptions)null);
      }

      public static DbDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(node, DbDictionaryType.type, options);
      }

      /** @deprecated */
      public static DbDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, DbDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DbDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DbDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, DbDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DbDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DbDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
