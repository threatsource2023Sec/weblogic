package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface ClassTableJdbcSeqType extends SequenceType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ClassTableJdbcSeqType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("classtablejdbcseqtype8df9type");

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

   boolean getIgnoreVirtual();

   XmlBoolean xgetIgnoreVirtual();

   boolean isSetIgnoreVirtual();

   void setIgnoreVirtual(boolean var1);

   void xsetIgnoreVirtual(XmlBoolean var1);

   void unsetIgnoreVirtual();

   boolean getIgnoreUnmapped();

   XmlBoolean xgetIgnoreUnmapped();

   boolean isSetIgnoreUnmapped();

   void setIgnoreUnmapped(boolean var1);

   void xsetIgnoreUnmapped(XmlBoolean var1);

   void unsetIgnoreUnmapped();

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

   boolean getUseAliases();

   XmlBoolean xgetUseAliases();

   boolean isSetUseAliases();

   void setUseAliases(boolean var1);

   void xsetUseAliases(XmlBoolean var1);

   void unsetUseAliases();

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
      public static ClassTableJdbcSeqType newInstance() {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().newInstance(ClassTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ClassTableJdbcSeqType newInstance(XmlOptions options) {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().newInstance(ClassTableJdbcSeqType.type, options);
      }

      public static ClassTableJdbcSeqType parse(String xmlAsString) throws XmlException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClassTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ClassTableJdbcSeqType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClassTableJdbcSeqType.type, options);
      }

      public static ClassTableJdbcSeqType parse(File file) throws XmlException, IOException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(file, ClassTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ClassTableJdbcSeqType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(file, ClassTableJdbcSeqType.type, options);
      }

      public static ClassTableJdbcSeqType parse(URL u) throws XmlException, IOException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(u, ClassTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ClassTableJdbcSeqType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(u, ClassTableJdbcSeqType.type, options);
      }

      public static ClassTableJdbcSeqType parse(InputStream is) throws XmlException, IOException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(is, ClassTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ClassTableJdbcSeqType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(is, ClassTableJdbcSeqType.type, options);
      }

      public static ClassTableJdbcSeqType parse(Reader r) throws XmlException, IOException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(r, ClassTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ClassTableJdbcSeqType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(r, ClassTableJdbcSeqType.type, options);
      }

      public static ClassTableJdbcSeqType parse(XMLStreamReader sr) throws XmlException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(sr, ClassTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ClassTableJdbcSeqType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(sr, ClassTableJdbcSeqType.type, options);
      }

      public static ClassTableJdbcSeqType parse(Node node) throws XmlException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(node, ClassTableJdbcSeqType.type, (XmlOptions)null);
      }

      public static ClassTableJdbcSeqType parse(Node node, XmlOptions options) throws XmlException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(node, ClassTableJdbcSeqType.type, options);
      }

      /** @deprecated */
      public static ClassTableJdbcSeqType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xis, ClassTableJdbcSeqType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ClassTableJdbcSeqType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ClassTableJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xis, ClassTableJdbcSeqType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClassTableJdbcSeqType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClassTableJdbcSeqType.type, options);
      }

      private Factory() {
      }
   }
}
