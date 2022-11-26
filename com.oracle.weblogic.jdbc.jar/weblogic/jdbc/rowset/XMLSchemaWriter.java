package weblogic.jdbc.rowset;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLOutputStream;

public final class XMLSchemaWriter implements XMLSchemaConstants {
   private CachedRowSetMetaData metaData;

   public XMLSchemaWriter(WLRowSetMetaData metaData) {
      this.metaData = (CachedRowSetMetaData)metaData;
   }

   private Attribute getAttr(String name, String value) {
      return ElementFactory.createAttribute(name, value);
   }

   private Attribute getAttr(XMLName name, String value) {
      return ElementFactory.createAttribute(name, value);
   }

   private Attribute getAttr(XMLName name, boolean value) {
      return ElementFactory.createAttribute(name, value ? "true" : "false");
   }

   private Attribute getAttr(XMLName name, int value) {
      return ElementFactory.createAttribute(name, "" + value);
   }

   private Attribute getDefaultNS(String name) {
      return ElementFactory.createNamespaceAttribute((String)null, name);
   }

   private void writeRowSet(XMLWriter writer) throws IOException, SQLException {
      List rowsetAttrs = new ArrayList();
      rowsetAttrs.add(this.getAttr("name", this.metaData.getRowSetName()));
      rowsetAttrs.add(this.getAttr(WLDD_DEFAULT_NAMESPACE, this.metaData.getDefaultNamespace()));
      rowsetAttrs.add(ISROWSET_ATTR);
      if (this.metaData.isReadOnly()) {
         rowsetAttrs.add(ISREADONLY_ATTR);
      } else {
         String writeTableName = this.metaData.getWriteTableName();
         if (writeTableName != null && !"".equals(writeTableName)) {
            rowsetAttrs.add(this.getAttr(WLDD_WRITE_TABLE_NAME, writeTableName));
         }

         if (this.metaData.getOptimisticPolicy() != 1) {
            rowsetAttrs.add(this.getAttr(WLDD_OPTPOLICY, this.metaData.getOptimisticPolicyAsString()));
         }

         int version = this.metaData.getVersion();
         if (version >= 2) {
            rowsetAttrs.add(this.getAttr(WLDD_METADATA_VERSION, version));
            boolean isValidMetaData = this.metaData.isValidMetaData();
            rowsetAttrs.add(this.getAttr(WLDD_VALID_METADATA, isValidMetaData));
            if (isValidMetaData) {
               DatabaseMetaDataHolder holder = this.metaData.getMetaDataHolder();
               rowsetAttrs.add(this.getAttr(WLDD_MAX_CATALOG_NAME_LENGTH, holder.getMaxCatalogNameLength()));
               rowsetAttrs.add(this.getAttr(WLDD_MAX_SCHEMA_NAME_LENGTH, holder.getMaxSchemaNameLength()));
               rowsetAttrs.add(this.getAttr(WLDD_MAX_TABLE_NAME_LENGTH, holder.getMaxTableNameLength()));
               rowsetAttrs.add(this.getAttr(WLDD_IDENTIFIER_QUOTE_STRING, holder.getIdentifierQuoteString()));
               rowsetAttrs.add(this.getAttr(WLDD_CATALOG_SEPARATOR, holder.getCatalogSeparator()));
               rowsetAttrs.add(this.getAttr(WLDD_CATALOG_AT_START, holder.isCatalogAtStart()));
               rowsetAttrs.add(this.getAttr(WLDD_SUPPORTS_SCHEMAS_IN_DML, holder.supportsSchemasInDataManipulation()));
               rowsetAttrs.add(this.getAttr(WLDD_SUPPORTS_CATALOGS_IN_DML, holder.supportsCatalogsInDataManipulation()));
               rowsetAttrs.add(this.getAttr(WLDD_STORES_UPPER_CASE_IDENTIFIERS, holder.storesUpperCaseIdentifiers()));
               rowsetAttrs.add(this.getAttr(WLDD_STORES_LOWER_CASE_IDENTIFIERS, holder.storesLowerCaseIdentifiers()));
               rowsetAttrs.add(this.getAttr(WLDD_STORES_MIXED_CASE_IDENTIFIERS, holder.storesMixedCaseIdentifiers()));
               rowsetAttrs.add(this.getAttr(WLDD_STORES_UPPER_CASE_QUOTED_IDENTIFIERS, holder.storesUpperCaseQuotedIdentifiers()));
               rowsetAttrs.add(this.getAttr(WLDD_STORES_LOWER_CASE_QUOTED_IDENTIFIERS, holder.storesLowerCaseQuotedIdentifiers()));
               rowsetAttrs.add(this.getAttr(WLDD_STORES_MIXED_CASE_QUOTED_IDENTIFIERS, holder.storesMixedCaseQuotedIdentifiers()));
            }
         }
      }

      writer.writeStartElement(ELEMENT_NAME, rowsetAttrs.iterator());
      writer.writeStartElement(COMPLEX_TYPE_NAME);
      writer.writeStartElement(CHOICE_NAME, "maxOccurs", "unbounded");
      this.writeRow(writer);
      writer.writeEndElement(CHOICE_NAME);
      writer.writeEndElement(COMPLEX_TYPE_NAME);
      writer.writeEndElement(ELEMENT_NAME);
   }

   private void writeRow(XMLWriter writer) throws IOException, SQLException {
      Map m = this.metaData.getAllRowAttributes();
      if (m.isEmpty()) {
         writer.writeStartElement(ELEMENT_NAME, "name", this.metaData.getRowName());
      } else {
         List rowAttributes = new ArrayList();
         rowAttributes.add(this.getAttr("name", this.metaData.getRowName()));
         XMLUtils.addAttributesFromPropertyMap(rowAttributes, m);
         writer.writeStartElement(ELEMENT_NAME, rowAttributes.iterator());
      }

      writer.writeStartElement(COMPLEX_TYPE_NAME);
      this.writeColumns(writer);
      writer.writeEndElement(COMPLEX_TYPE_NAME);
      writer.writeEndElement(ELEMENT_NAME);
   }

   private void writeColumns(XMLWriter writer) throws IOException, SQLException {
      writer.writeStartElement(SEQUENCE_NAME);

      for(int i = 0; i < this.metaData.getColumnCount(); ++i) {
         List attrList = new ArrayList();
         attrList.add(this.getAttr("name", this.metaData.getColumnName(i + 1)));
         int dbType = this.metaData.getColumnType(i + 1);
         XMLName n = TypeMapper.getXSDType(dbType);
         attrList.add(this.getAttr("type", n.getPrefix() + ":" + n.getLocalName()));
         attrList.add(this.getAttr(WLDD_JDBC_TYPE, TypeMapper.getJDBCTypeAsString(dbType)));
         attrList.add(this.getAttr("minOccurs", "0"));
         this.metaData.setXMLAttributes(i, attrList);
         writer.writeStartElement(ELEMENT_NAME, attrList.iterator());
         writer.writeEndElement(ELEMENT_NAME);
      }

      writer.writeEndElement(SEQUENCE_NAME);
      if (!this.metaData.isReadOnly()) {
         writer.writeStartElement(ANY_ATTRIBUTE_NAME, "namespace", "http://www.bea.com/2002/10/weblogicdata", "processContents", "skip");
         writer.writeEndElement(ANY_ATTRIBUTE_NAME);
      }

   }

   public void writeSchema(XMLOutputStream xos) throws IOException, SQLException {
      XMLWriter writer = new XMLWriter(xos);
      Attribute targetNamespace = this.getAttr("targetNamespace", this.metaData.getDefaultNamespace());
      Attribute namespace = this.getDefaultNS(this.metaData.getDefaultNamespace());
      List attrList = new ArrayList();
      attrList.add(targetNamespace);
      attrList.add(namespace);
      attrList.add(SCHEMA_NAMESPACE);
      attrList.add(WLDATA_NAMESPACE);
      attrList.add(ELEMENT_FORM_DEFAULT_ATTR);
      attrList.add(ATTRIBUTE_FORM_DEFAULT_ATTR);
      writer.writeStartElement(SCHEMA_NAME, attrList.iterator());
      this.writeRowSet(writer);
      writer.writeEndElement(SCHEMA_NAME);
      xos.flush();
   }
}
