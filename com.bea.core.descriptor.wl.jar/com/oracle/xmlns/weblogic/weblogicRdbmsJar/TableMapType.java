package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.XsdNonNegativeIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface TableMapType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TableMapType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("tablemaptype37datype");

   TableNameType getTableName();

   void setTableName(TableNameType var1);

   TableNameType addNewTableName();

   FieldMapType[] getFieldMapArray();

   FieldMapType getFieldMapArray(int var1);

   int sizeOfFieldMapArray();

   void setFieldMapArray(FieldMapType[] var1);

   void setFieldMapArray(int var1, FieldMapType var2);

   FieldMapType insertNewFieldMap(int var1);

   FieldMapType addNewFieldMap();

   void removeFieldMap(int var1);

   VerifyRowsType getVerifyRows();

   boolean isSetVerifyRows();

   void setVerifyRows(VerifyRowsType var1);

   VerifyRowsType addNewVerifyRows();

   void unsetVerifyRows();

   VerifyColumnsType getVerifyColumns();

   boolean isSetVerifyColumns();

   void setVerifyColumns(VerifyColumnsType var1);

   VerifyColumnsType addNewVerifyColumns();

   void unsetVerifyColumns();

   OptimisticColumnType getOptimisticColumn();

   boolean isSetOptimisticColumn();

   void setOptimisticColumn(OptimisticColumnType var1);

   OptimisticColumnType addNewOptimisticColumn();

   void unsetOptimisticColumn();

   TrueFalseType getTriggerUpdatesOptimisticColumn();

   boolean isSetTriggerUpdatesOptimisticColumn();

   void setTriggerUpdatesOptimisticColumn(TrueFalseType var1);

   TrueFalseType addNewTriggerUpdatesOptimisticColumn();

   void unsetTriggerUpdatesOptimisticColumn();

   XsdNonNegativeIntegerType getVersionColumnInitialValue();

   boolean isSetVersionColumnInitialValue();

   void setVersionColumnInitialValue(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewVersionColumnInitialValue();

   void unsetVersionColumnInitialValue();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TableMapType newInstance() {
         return (TableMapType)XmlBeans.getContextTypeLoader().newInstance(TableMapType.type, (XmlOptions)null);
      }

      public static TableMapType newInstance(XmlOptions options) {
         return (TableMapType)XmlBeans.getContextTypeLoader().newInstance(TableMapType.type, options);
      }

      public static TableMapType parse(String xmlAsString) throws XmlException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableMapType.type, (XmlOptions)null);
      }

      public static TableMapType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableMapType.type, options);
      }

      public static TableMapType parse(File file) throws XmlException, IOException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(file, TableMapType.type, (XmlOptions)null);
      }

      public static TableMapType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(file, TableMapType.type, options);
      }

      public static TableMapType parse(URL u) throws XmlException, IOException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(u, TableMapType.type, (XmlOptions)null);
      }

      public static TableMapType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(u, TableMapType.type, options);
      }

      public static TableMapType parse(InputStream is) throws XmlException, IOException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(is, TableMapType.type, (XmlOptions)null);
      }

      public static TableMapType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(is, TableMapType.type, options);
      }

      public static TableMapType parse(Reader r) throws XmlException, IOException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(r, TableMapType.type, (XmlOptions)null);
      }

      public static TableMapType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(r, TableMapType.type, options);
      }

      public static TableMapType parse(XMLStreamReader sr) throws XmlException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(sr, TableMapType.type, (XmlOptions)null);
      }

      public static TableMapType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(sr, TableMapType.type, options);
      }

      public static TableMapType parse(Node node) throws XmlException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(node, TableMapType.type, (XmlOptions)null);
      }

      public static TableMapType parse(Node node, XmlOptions options) throws XmlException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(node, TableMapType.type, options);
      }

      /** @deprecated */
      public static TableMapType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(xis, TableMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TableMapType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TableMapType)XmlBeans.getContextTypeLoader().parse(xis, TableMapType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableMapType.type, options);
      }

      private Factory() {
      }
   }
}
