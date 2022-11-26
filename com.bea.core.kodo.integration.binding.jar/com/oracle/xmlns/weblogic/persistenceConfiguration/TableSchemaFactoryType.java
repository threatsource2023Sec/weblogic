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

public interface TableSchemaFactoryType extends SchemaFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TableSchemaFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("tableschemafactorytype885dtype");

   String getSchemaColumn();

   XmlString xgetSchemaColumn();

   boolean isNilSchemaColumn();

   boolean isSetSchemaColumn();

   void setSchemaColumn(String var1);

   void xsetSchemaColumn(XmlString var1);

   void setNilSchemaColumn();

   void unsetSchemaColumn();

   String getTableName();

   XmlString xgetTableName();

   boolean isNilTableName();

   boolean isSetTableName();

   void setTableName(String var1);

   void xsetTableName(XmlString var1);

   void setNilTableName();

   void unsetTableName();

   String getTable();

   XmlString xgetTable();

   boolean isNilTable();

   boolean isSetTable();

   void setTable(String var1);

   void xsetTable(XmlString var1);

   void setNilTable();

   void unsetTable();

   String getPrimaryKeyColumn();

   XmlString xgetPrimaryKeyColumn();

   boolean isNilPrimaryKeyColumn();

   boolean isSetPrimaryKeyColumn();

   void setPrimaryKeyColumn(String var1);

   void xsetPrimaryKeyColumn(XmlString var1);

   void setNilPrimaryKeyColumn();

   void unsetPrimaryKeyColumn();

   public static final class Factory {
      public static TableSchemaFactoryType newInstance() {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(TableSchemaFactoryType.type, (XmlOptions)null);
      }

      public static TableSchemaFactoryType newInstance(XmlOptions options) {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(TableSchemaFactoryType.type, options);
      }

      public static TableSchemaFactoryType parse(String xmlAsString) throws XmlException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableSchemaFactoryType.type, (XmlOptions)null);
      }

      public static TableSchemaFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableSchemaFactoryType.type, options);
      }

      public static TableSchemaFactoryType parse(File file) throws XmlException, IOException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, TableSchemaFactoryType.type, (XmlOptions)null);
      }

      public static TableSchemaFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, TableSchemaFactoryType.type, options);
      }

      public static TableSchemaFactoryType parse(URL u) throws XmlException, IOException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, TableSchemaFactoryType.type, (XmlOptions)null);
      }

      public static TableSchemaFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, TableSchemaFactoryType.type, options);
      }

      public static TableSchemaFactoryType parse(InputStream is) throws XmlException, IOException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, TableSchemaFactoryType.type, (XmlOptions)null);
      }

      public static TableSchemaFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, TableSchemaFactoryType.type, options);
      }

      public static TableSchemaFactoryType parse(Reader r) throws XmlException, IOException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, TableSchemaFactoryType.type, (XmlOptions)null);
      }

      public static TableSchemaFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, TableSchemaFactoryType.type, options);
      }

      public static TableSchemaFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, TableSchemaFactoryType.type, (XmlOptions)null);
      }

      public static TableSchemaFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, TableSchemaFactoryType.type, options);
      }

      public static TableSchemaFactoryType parse(Node node) throws XmlException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, TableSchemaFactoryType.type, (XmlOptions)null);
      }

      public static TableSchemaFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, TableSchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static TableSchemaFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, TableSchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TableSchemaFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TableSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, TableSchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableSchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableSchemaFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
