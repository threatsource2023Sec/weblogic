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

public interface ValueTableJdbcSeqType extends SequenceType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ValueTableJdbcSeqType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("valuetablejdbcseqtype4632type");

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

   String getPrimaryKeyValue();

   XmlString xgetPrimaryKeyValue();

   boolean isNilPrimaryKeyValue();

   boolean isSetPrimaryKeyValue();

   void setPrimaryKeyValue(String var1);

   void xsetPrimaryKeyValue(XmlString var1);

   void setNilPrimaryKeyValue();

   void unsetPrimaryKeyValue();

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
      public static ValueTableJdbcSeqType newInstance() {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().newInstance(ValueTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ValueTableJdbcSeqType newInstance(XmlOptions options) {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().newInstance(ValueTableJdbcSeqType.type, options);
      }

      public static ValueTableJdbcSeqType parse(String xmlAsString) throws XmlException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ValueTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ValueTableJdbcSeqType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ValueTableJdbcSeqType.type, options);
      }

      public static ValueTableJdbcSeqType parse(File file) throws XmlException, IOException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(file, ValueTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ValueTableJdbcSeqType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(file, ValueTableJdbcSeqType.type, options);
      }

      public static ValueTableJdbcSeqType parse(URL u) throws XmlException, IOException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(u, ValueTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ValueTableJdbcSeqType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(u, ValueTableJdbcSeqType.type, options);
      }

      public static ValueTableJdbcSeqType parse(InputStream is) throws XmlException, IOException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(is, ValueTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ValueTableJdbcSeqType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(is, ValueTableJdbcSeqType.type, options);
      }

      public static ValueTableJdbcSeqType parse(Reader r) throws XmlException, IOException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(r, ValueTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ValueTableJdbcSeqType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(r, ValueTableJdbcSeqType.type, options);
      }

      public static ValueTableJdbcSeqType parse(XMLStreamReader sr) throws XmlException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(sr, ValueTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ValueTableJdbcSeqType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(sr, ValueTableJdbcSeqType.type, options);
      }

      public static ValueTableJdbcSeqType parse(Node node) throws XmlException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(node, ValueTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ValueTableJdbcSeqType parse(Node node, XmlOptions options) throws XmlException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(node, ValueTableJdbcSeqType.type, options);
      }

      /** @deprecated */
      public static ValueTableJdbcSeqType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xis, ValueTableJdbcSeqType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ValueTableJdbcSeqType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ValueTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xis, ValueTableJdbcSeqType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ValueTableJdbcSeqType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ValueTableJdbcSeqType.type, options);
      }

      private Factory() {
      }
   }
}
