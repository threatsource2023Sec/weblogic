package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
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

public interface RelationshipRoleMapType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RelationshipRoleMapType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("relationshiprolemaptype9ef7type");

   ForeignKeyTableType getForeignKeyTable();

   boolean isSetForeignKeyTable();

   void setForeignKeyTable(ForeignKeyTableType var1);

   ForeignKeyTableType addNewForeignKeyTable();

   void unsetForeignKeyTable();

   PrimaryKeyTableType getPrimaryKeyTable();

   boolean isSetPrimaryKeyTable();

   void setPrimaryKeyTable(PrimaryKeyTableType var1);

   PrimaryKeyTableType addNewPrimaryKeyTable();

   void unsetPrimaryKeyTable();

   ColumnMapType[] getColumnMapArray();

   ColumnMapType getColumnMapArray(int var1);

   int sizeOfColumnMapArray();

   void setColumnMapArray(ColumnMapType[] var1);

   void setColumnMapArray(int var1, ColumnMapType var2);

   ColumnMapType insertNewColumnMap(int var1);

   ColumnMapType addNewColumnMap();

   void removeColumnMap(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RelationshipRoleMapType newInstance() {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().newInstance(RelationshipRoleMapType.type, (XmlOptions)null);
      }

      public static RelationshipRoleMapType newInstance(XmlOptions options) {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().newInstance(RelationshipRoleMapType.type, options);
      }

      public static RelationshipRoleMapType parse(String xmlAsString) throws XmlException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RelationshipRoleMapType.type, (XmlOptions)null);
      }

      public static RelationshipRoleMapType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RelationshipRoleMapType.type, options);
      }

      public static RelationshipRoleMapType parse(File file) throws XmlException, IOException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(file, RelationshipRoleMapType.type, (XmlOptions)null);
      }

      public static RelationshipRoleMapType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(file, RelationshipRoleMapType.type, options);
      }

      public static RelationshipRoleMapType parse(URL u) throws XmlException, IOException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(u, RelationshipRoleMapType.type, (XmlOptions)null);
      }

      public static RelationshipRoleMapType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(u, RelationshipRoleMapType.type, options);
      }

      public static RelationshipRoleMapType parse(InputStream is) throws XmlException, IOException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(is, RelationshipRoleMapType.type, (XmlOptions)null);
      }

      public static RelationshipRoleMapType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(is, RelationshipRoleMapType.type, options);
      }

      public static RelationshipRoleMapType parse(Reader r) throws XmlException, IOException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(r, RelationshipRoleMapType.type, (XmlOptions)null);
      }

      public static RelationshipRoleMapType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(r, RelationshipRoleMapType.type, options);
      }

      public static RelationshipRoleMapType parse(XMLStreamReader sr) throws XmlException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(sr, RelationshipRoleMapType.type, (XmlOptions)null);
      }

      public static RelationshipRoleMapType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(sr, RelationshipRoleMapType.type, options);
      }

      public static RelationshipRoleMapType parse(Node node) throws XmlException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(node, RelationshipRoleMapType.type, (XmlOptions)null);
      }

      public static RelationshipRoleMapType parse(Node node, XmlOptions options) throws XmlException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(node, RelationshipRoleMapType.type, options);
      }

      /** @deprecated */
      public static RelationshipRoleMapType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(xis, RelationshipRoleMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RelationshipRoleMapType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RelationshipRoleMapType)XmlBeans.getContextTypeLoader().parse(xis, RelationshipRoleMapType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RelationshipRoleMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RelationshipRoleMapType.type, options);
      }

      private Factory() {
      }
   }
}
