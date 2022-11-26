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

public interface SqlQueryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SqlQueryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("sqlquerytypedd06type");

   SqlShapeNameType getSqlShapeName();

   boolean isSetSqlShapeName();

   void setSqlShapeName(SqlShapeNameType var1);

   SqlShapeNameType addNewSqlShapeName();

   void unsetSqlShapeName();

   XsdStringType getSql();

   boolean isSetSql();

   void setSql(XsdStringType var1);

   XsdStringType addNewSql();

   void unsetSql();

   DatabaseSpecificSqlType[] getDatabaseSpecificSqlArray();

   DatabaseSpecificSqlType getDatabaseSpecificSqlArray(int var1);

   int sizeOfDatabaseSpecificSqlArray();

   void setDatabaseSpecificSqlArray(DatabaseSpecificSqlType[] var1);

   void setDatabaseSpecificSqlArray(int var1, DatabaseSpecificSqlType var2);

   DatabaseSpecificSqlType insertNewDatabaseSpecificSql(int var1);

   DatabaseSpecificSqlType addNewDatabaseSpecificSql();

   void removeDatabaseSpecificSql(int var1);

   public static final class Factory {
      public static SqlQueryType newInstance() {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().newInstance(SqlQueryType.type, (XmlOptions)null);
      }

      public static SqlQueryType newInstance(XmlOptions options) {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().newInstance(SqlQueryType.type, options);
      }

      public static SqlQueryType parse(String xmlAsString) throws XmlException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SqlQueryType.type, (XmlOptions)null);
      }

      public static SqlQueryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SqlQueryType.type, options);
      }

      public static SqlQueryType parse(File file) throws XmlException, IOException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(file, SqlQueryType.type, (XmlOptions)null);
      }

      public static SqlQueryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(file, SqlQueryType.type, options);
      }

      public static SqlQueryType parse(URL u) throws XmlException, IOException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(u, SqlQueryType.type, (XmlOptions)null);
      }

      public static SqlQueryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(u, SqlQueryType.type, options);
      }

      public static SqlQueryType parse(InputStream is) throws XmlException, IOException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(is, SqlQueryType.type, (XmlOptions)null);
      }

      public static SqlQueryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(is, SqlQueryType.type, options);
      }

      public static SqlQueryType parse(Reader r) throws XmlException, IOException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(r, SqlQueryType.type, (XmlOptions)null);
      }

      public static SqlQueryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(r, SqlQueryType.type, options);
      }

      public static SqlQueryType parse(XMLStreamReader sr) throws XmlException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(sr, SqlQueryType.type, (XmlOptions)null);
      }

      public static SqlQueryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(sr, SqlQueryType.type, options);
      }

      public static SqlQueryType parse(Node node) throws XmlException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(node, SqlQueryType.type, (XmlOptions)null);
      }

      public static SqlQueryType parse(Node node, XmlOptions options) throws XmlException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(node, SqlQueryType.type, options);
      }

      /** @deprecated */
      public static SqlQueryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(xis, SqlQueryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SqlQueryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SqlQueryType)XmlBeans.getContextTypeLoader().parse(xis, SqlQueryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SqlQueryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SqlQueryType.type, options);
      }

      private Factory() {
      }
   }
}
