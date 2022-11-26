package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface TableJdbcSeqType extends SequenceType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TableJdbcSeqType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("tablejdbcseqtype82cetype");

   int getType();

   XmlInt xgetType();

   boolean isSetType();

   void setType(int var1);

   void xsetType(XmlInt var1);

   void unsetType();

   int getAllocate();

   XmlInt xgetAllocate();

   boolean isSetAllocate();

   void setAllocate(int var1);

   void xsetAllocate(XmlInt var1);

   void unsetAllocate();

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

   String getSequenceColumn();

   XmlString xgetSequenceColumn();

   boolean isNilSequenceColumn();

   boolean isSetSequenceColumn();

   void setSequenceColumn(String var1);

   void xsetSequenceColumn(XmlString var1);

   void setNilSequenceColumn();

   void unsetSequenceColumn();

   int getIncrement();

   XmlInt xgetIncrement();

   boolean isSetIncrement();

   void setIncrement(int var1);

   void xsetIncrement(XmlInt var1);

   void unsetIncrement();

   public static final class Factory {
      public static TableJdbcSeqType newInstance() {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().newInstance(TableJdbcSeqType.type, (XmlOptions)null);
      }

      public static TableJdbcSeqType newInstance(XmlOptions options) {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().newInstance(TableJdbcSeqType.type, options);
      }

      public static TableJdbcSeqType parse(String xmlAsString) throws XmlException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableJdbcSeqType.type, (XmlOptions)null);
      }

      public static TableJdbcSeqType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableJdbcSeqType.type, options);
      }

      public static TableJdbcSeqType parse(File file) throws XmlException, IOException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(file, TableJdbcSeqType.type, (XmlOptions)null);
      }

      public static TableJdbcSeqType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(file, TableJdbcSeqType.type, options);
      }

      public static TableJdbcSeqType parse(URL u) throws XmlException, IOException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(u, TableJdbcSeqType.type, (XmlOptions)null);
      }

      public static TableJdbcSeqType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(u, TableJdbcSeqType.type, options);
      }

      public static TableJdbcSeqType parse(InputStream is) throws XmlException, IOException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(is, TableJdbcSeqType.type, (XmlOptions)null);
      }

      public static TableJdbcSeqType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(is, TableJdbcSeqType.type, options);
      }

      public static TableJdbcSeqType parse(Reader r) throws XmlException, IOException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(r, TableJdbcSeqType.type, (XmlOptions)null);
      }

      public static TableJdbcSeqType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(r, TableJdbcSeqType.type, options);
      }

      public static TableJdbcSeqType parse(XMLStreamReader sr) throws XmlException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(sr, TableJdbcSeqType.type, (XmlOptions)null);
      }

      public static TableJdbcSeqType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(sr, TableJdbcSeqType.type, options);
      }

      public static TableJdbcSeqType parse(Node node) throws XmlException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(node, TableJdbcSeqType.type, (XmlOptions)null);
      }

      public static TableJdbcSeqType parse(Node node, XmlOptions options) throws XmlException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(node, TableJdbcSeqType.type, options);
      }

      /** @deprecated */
      public static TableJdbcSeqType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xis, TableJdbcSeqType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TableJdbcSeqType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xis, TableJdbcSeqType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableJdbcSeqType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableJdbcSeqType.type, options);
      }

      private Factory() {
      }
   }
}
