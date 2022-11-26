package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface TableType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TableType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("tabletype214btype");

   TableNameType getTableName();

   void setTableName(TableNameType var1);

   TableNameType addNewTableName();

   DbmsColumnType[] getDbmsColumnArray();

   DbmsColumnType getDbmsColumnArray(int var1);

   int sizeOfDbmsColumnArray();

   void setDbmsColumnArray(DbmsColumnType[] var1);

   void setDbmsColumnArray(int var1, DbmsColumnType var2);

   DbmsColumnType insertNewDbmsColumn(int var1);

   DbmsColumnType addNewDbmsColumn();

   void removeDbmsColumn(int var1);

   String getEjbRelationshipRoleName();

   boolean isSetEjbRelationshipRoleName();

   void setEjbRelationshipRoleName(String var1);

   String addNewEjbRelationshipRoleName();

   void unsetEjbRelationshipRoleName();

   public static final class Factory {
      public static TableType newInstance() {
         return (TableType)XmlBeans.getContextTypeLoader().newInstance(TableType.type, (XmlOptions)null);
      }

      public static TableType newInstance(XmlOptions options) {
         return (TableType)XmlBeans.getContextTypeLoader().newInstance(TableType.type, options);
      }

      public static TableType parse(java.lang.String xmlAsString) throws XmlException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableType.type, (XmlOptions)null);
      }

      public static TableType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableType.type, options);
      }

      public static TableType parse(File file) throws XmlException, IOException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(file, TableType.type, (XmlOptions)null);
      }

      public static TableType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(file, TableType.type, options);
      }

      public static TableType parse(URL u) throws XmlException, IOException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(u, TableType.type, (XmlOptions)null);
      }

      public static TableType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(u, TableType.type, options);
      }

      public static TableType parse(InputStream is) throws XmlException, IOException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(is, TableType.type, (XmlOptions)null);
      }

      public static TableType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(is, TableType.type, options);
      }

      public static TableType parse(Reader r) throws XmlException, IOException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(r, TableType.type, (XmlOptions)null);
      }

      public static TableType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(r, TableType.type, options);
      }

      public static TableType parse(XMLStreamReader sr) throws XmlException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(sr, TableType.type, (XmlOptions)null);
      }

      public static TableType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(sr, TableType.type, options);
      }

      public static TableType parse(Node node) throws XmlException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(node, TableType.type, (XmlOptions)null);
      }

      public static TableType parse(Node node, XmlOptions options) throws XmlException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(node, TableType.type, options);
      }

      /** @deprecated */
      public static TableType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(xis, TableType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TableType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TableType)XmlBeans.getContextTypeLoader().parse(xis, TableType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableType.type, options);
      }

      private Factory() {
      }
   }
}
