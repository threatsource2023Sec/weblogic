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

public interface SqlServerDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SqlServerDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("sqlserverdictionarytype0e6btype");

   boolean getUniqueIdentifierAsVarbinary();

   XmlBoolean xgetUniqueIdentifierAsVarbinary();

   boolean isSetUniqueIdentifierAsVarbinary();

   void setUniqueIdentifierAsVarbinary(boolean var1);

   void xsetUniqueIdentifierAsVarbinary(XmlBoolean var1);

   void unsetUniqueIdentifierAsVarbinary();

   public static final class Factory {
      public static SqlServerDictionaryType newInstance() {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().newInstance(SqlServerDictionaryType.type, (XmlOptions)null);
      }

      public static SqlServerDictionaryType newInstance(XmlOptions options) {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().newInstance(SqlServerDictionaryType.type, options);
      }

      public static SqlServerDictionaryType parse(String xmlAsString) throws XmlException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SqlServerDictionaryType.type, (XmlOptions)null);
      }

      public static SqlServerDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SqlServerDictionaryType.type, options);
      }

      public static SqlServerDictionaryType parse(File file) throws XmlException, IOException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(file, SqlServerDictionaryType.type, (XmlOptions)null);
      }

      public static SqlServerDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(file, SqlServerDictionaryType.type, options);
      }

      public static SqlServerDictionaryType parse(URL u) throws XmlException, IOException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(u, SqlServerDictionaryType.type, (XmlOptions)null);
      }

      public static SqlServerDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(u, SqlServerDictionaryType.type, options);
      }

      public static SqlServerDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(is, SqlServerDictionaryType.type, (XmlOptions)null);
      }

      public static SqlServerDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(is, SqlServerDictionaryType.type, options);
      }

      public static SqlServerDictionaryType parse(Reader r) throws XmlException, IOException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(r, SqlServerDictionaryType.type, (XmlOptions)null);
      }

      public static SqlServerDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(r, SqlServerDictionaryType.type, options);
      }

      public static SqlServerDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, SqlServerDictionaryType.type, (XmlOptions)null);
      }

      public static SqlServerDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, SqlServerDictionaryType.type, options);
      }

      public static SqlServerDictionaryType parse(Node node) throws XmlException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(node, SqlServerDictionaryType.type, (XmlOptions)null);
      }

      public static SqlServerDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(node, SqlServerDictionaryType.type, options);
      }

      /** @deprecated */
      public static SqlServerDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, SqlServerDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SqlServerDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SqlServerDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, SqlServerDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SqlServerDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SqlServerDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
