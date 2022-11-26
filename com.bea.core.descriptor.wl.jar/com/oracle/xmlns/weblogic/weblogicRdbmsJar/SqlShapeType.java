package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.XsdPositiveIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface SqlShapeType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SqlShapeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("sqlshapetypeb4dftype");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   SqlShapeNameType getSqlShapeName();

   void setSqlShapeName(SqlShapeNameType var1);

   SqlShapeNameType addNewSqlShapeName();

   TableType[] getTableArray();

   TableType getTableArray(int var1);

   int sizeOfTableArray();

   void setTableArray(TableType[] var1);

   void setTableArray(int var1, TableType var2);

   TableType insertNewTable(int var1);

   TableType addNewTable();

   void removeTable(int var1);

   XsdPositiveIntegerType getPassThroughColumns();

   boolean isSetPassThroughColumns();

   void setPassThroughColumns(XsdPositiveIntegerType var1);

   XsdPositiveIntegerType addNewPassThroughColumns();

   void unsetPassThroughColumns();

   String[] getEjbRelationNameArray();

   String getEjbRelationNameArray(int var1);

   int sizeOfEjbRelationNameArray();

   void setEjbRelationNameArray(String[] var1);

   void setEjbRelationNameArray(int var1, String var2);

   String insertNewEjbRelationName(int var1);

   String addNewEjbRelationName();

   void removeEjbRelationName(int var1);

   public static final class Factory {
      public static SqlShapeType newInstance() {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().newInstance(SqlShapeType.type, (XmlOptions)null);
      }

      public static SqlShapeType newInstance(XmlOptions options) {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().newInstance(SqlShapeType.type, options);
      }

      public static SqlShapeType parse(java.lang.String xmlAsString) throws XmlException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SqlShapeType.type, (XmlOptions)null);
      }

      public static SqlShapeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SqlShapeType.type, options);
      }

      public static SqlShapeType parse(File file) throws XmlException, IOException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(file, SqlShapeType.type, (XmlOptions)null);
      }

      public static SqlShapeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(file, SqlShapeType.type, options);
      }

      public static SqlShapeType parse(URL u) throws XmlException, IOException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(u, SqlShapeType.type, (XmlOptions)null);
      }

      public static SqlShapeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(u, SqlShapeType.type, options);
      }

      public static SqlShapeType parse(InputStream is) throws XmlException, IOException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(is, SqlShapeType.type, (XmlOptions)null);
      }

      public static SqlShapeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(is, SqlShapeType.type, options);
      }

      public static SqlShapeType parse(Reader r) throws XmlException, IOException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(r, SqlShapeType.type, (XmlOptions)null);
      }

      public static SqlShapeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(r, SqlShapeType.type, options);
      }

      public static SqlShapeType parse(XMLStreamReader sr) throws XmlException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(sr, SqlShapeType.type, (XmlOptions)null);
      }

      public static SqlShapeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(sr, SqlShapeType.type, options);
      }

      public static SqlShapeType parse(Node node) throws XmlException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(node, SqlShapeType.type, (XmlOptions)null);
      }

      public static SqlShapeType parse(Node node, XmlOptions options) throws XmlException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(node, SqlShapeType.type, options);
      }

      /** @deprecated */
      public static SqlShapeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(xis, SqlShapeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SqlShapeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SqlShapeType)XmlBeans.getContextTypeLoader().parse(xis, SqlShapeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SqlShapeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SqlShapeType.type, options);
      }

      private Factory() {
      }
   }
}
