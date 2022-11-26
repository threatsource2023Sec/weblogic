package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.XsdStringType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface DatabaseSpecificSqlType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DatabaseSpecificSqlType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("databasespecificsqltype0c42type");

   DatabaseTypeType getDatabaseType();

   void setDatabaseType(DatabaseTypeType var1);

   DatabaseTypeType addNewDatabaseType();

   XsdStringType getSql();

   void setSql(XsdStringType var1);

   XsdStringType addNewSql();

   public static final class Factory {
      public static DatabaseSpecificSqlType newInstance() {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().newInstance(DatabaseSpecificSqlType.type, (XmlOptions)null);
      }

      public static DatabaseSpecificSqlType newInstance(XmlOptions options) {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().newInstance(DatabaseSpecificSqlType.type, options);
      }

      public static DatabaseSpecificSqlType parse(String xmlAsString) throws XmlException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DatabaseSpecificSqlType.type, (XmlOptions)null);
      }

      public static DatabaseSpecificSqlType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DatabaseSpecificSqlType.type, options);
      }

      public static DatabaseSpecificSqlType parse(File file) throws XmlException, IOException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(file, DatabaseSpecificSqlType.type, (XmlOptions)null);
      }

      public static DatabaseSpecificSqlType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(file, DatabaseSpecificSqlType.type, options);
      }

      public static DatabaseSpecificSqlType parse(URL u) throws XmlException, IOException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(u, DatabaseSpecificSqlType.type, (XmlOptions)null);
      }

      public static DatabaseSpecificSqlType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(u, DatabaseSpecificSqlType.type, options);
      }

      public static DatabaseSpecificSqlType parse(InputStream is) throws XmlException, IOException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(is, DatabaseSpecificSqlType.type, (XmlOptions)null);
      }

      public static DatabaseSpecificSqlType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(is, DatabaseSpecificSqlType.type, options);
      }

      public static DatabaseSpecificSqlType parse(Reader r) throws XmlException, IOException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(r, DatabaseSpecificSqlType.type, (XmlOptions)null);
      }

      public static DatabaseSpecificSqlType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(r, DatabaseSpecificSqlType.type, options);
      }

      public static DatabaseSpecificSqlType parse(XMLStreamReader sr) throws XmlException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(sr, DatabaseSpecificSqlType.type, (XmlOptions)null);
      }

      public static DatabaseSpecificSqlType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(sr, DatabaseSpecificSqlType.type, options);
      }

      public static DatabaseSpecificSqlType parse(Node node) throws XmlException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(node, DatabaseSpecificSqlType.type, (XmlOptions)null);
      }

      public static DatabaseSpecificSqlType parse(Node node, XmlOptions options) throws XmlException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(node, DatabaseSpecificSqlType.type, options);
      }

      /** @deprecated */
      public static DatabaseSpecificSqlType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(xis, DatabaseSpecificSqlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DatabaseSpecificSqlType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DatabaseSpecificSqlType)XmlBeans.getContextTypeLoader().parse(xis, DatabaseSpecificSqlType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DatabaseSpecificSqlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DatabaseSpecificSqlType.type, options);
      }

      private Factory() {
      }
   }
}
