package weblogic.jdbc.rowset;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sql.rowset.spi.SyncProvider;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLOutputStream;

public final class WebRowSetWriter implements XMLSchemaConstants {
   private CachedRowSetImpl rowSet;
   private WLRowSetMetaData metaData;

   public WebRowSetWriter(CachedRowSetImpl rowSet) throws SQLException {
      this.rowSet = rowSet;
      this.metaData = (WLRowSetMetaData)rowSet.getMetaData();
   }

   private Attribute getAttr(String name, String value) {
      return ElementFactory.createAttribute(name, value);
   }

   private Attribute getAttr(XMLName name, String value) {
      return ElementFactory.createAttribute(name, value);
   }

   private Attribute getDefaultNS(String name) {
      return ElementFactory.createNamespaceAttribute((String)null, name);
   }

   public void writeXML(XMLOutputStream xos, int rowStates) throws SQLException, IOException {
      XMLWriter writer = new XMLWriter(xos, "http://java.sun.com/xml/ns/jdbc");
      List namespaces = new ArrayList();
      Attribute defaultNs = this.getDefaultNS("http://java.sun.com/xml/ns/jdbc");
      namespaces.add(defaultNs);
      namespaces.add(SCHEMA_INSTANCE_NAMESPACE);
      List attrList = new ArrayList();
      attrList.add(this.getAttr(SCHEMA_LOCATION_NAME, "http://java.sun.com/xml/ns/jdbc/webrowset.xsd"));
      writer.writeStartElement("webRowSet", attrList.iterator(), namespaces.iterator());
      this.writeProperties(writer);
      this.writeMetaData(writer);
      this.writeData(writer);
      writer.writeEndElement("webRowSet");
      xos.flush();
   }

   private void writeProperties(XMLWriter writer) throws SQLException, IOException {
      String tmp = null;
      writer.writeStartElement("properties");
      writer.writeSimpleElements("command", this.rowSet.getCommand());
      writer.writeSimpleElements("concurrency", this.rowSet.getConcurrency());
      writer.writeSimpleElements("datasource", this.rowSet.getDataSourceName());
      writer.writeSimpleElements("escape-processing", this.rowSet.getEscapeProcessing());
      writer.writeSimpleElements("fetch-direction", this.rowSet.getFetchDirection());
      writer.writeSimpleElements("fetch-size", this.rowSet.getFetchSize());
      writer.writeSimpleElements("isolation-level", this.rowSet.getTransactionIsolation());
      writer.writeStartElement("key-columns");
      int[] keyColumns = this.rowSet.getKeyColumns();
      if (keyColumns != null) {
         for(int i = 0; i < keyColumns.length; ++i) {
            writer.writeSimpleElements("column", this.metaData.getColumnName(keyColumns[i]));
         }
      }

      writer.writeEndElement("key-columns");
      writer.writeStartElement("map");
      Map typeMap = this.rowSet.getTypeMap();
      if (typeMap != null && !typeMap.isEmpty()) {
         Iterator it = typeMap.keySet().iterator();

         while(it.hasNext()) {
            String s = (String)it.next();
            writer.writeSimpleElements("type", s);
            writer.writeSimpleElements("class", ((Class)typeMap.get(s)).getName());
         }
      }

      writer.writeEndElement("map");
      writer.writeSimpleElements("max-field-size", this.rowSet.getMaxFieldSize());
      writer.writeSimpleElements("max-rows", this.rowSet.getMaxRows());
      writer.writeSimpleElements("query-timeout", this.rowSet.getQueryTimeout());
      writer.writeSimpleElements("read-only", this.rowSet.isReadOnly());
      writer.writeSimpleElements("rowset-type", this.rowSet.getType());
      writer.writeSimpleElements("show-deleted", this.rowSet.getShowDeleted());
      writer.writeSimpleElements("table-name", this.rowSet.getTableName());
      writer.writeSimpleElements("url", this.rowSet.getUrl());
      writer.writeStartElement("sync-provider");
      SyncProvider sp = this.rowSet.getSyncProvider();
      writer.writeSimpleElements("sync-provider-name", sp.getProviderID());
      writer.writeSimpleElements("sync-provider-vendor", "unknown");
      writer.writeSimpleElements("sync-provider-version", "unknown");
      writer.writeSimpleElements("sync-provider-grade", sp.getProviderGrade());
      writer.writeSimpleElements("data-source-lock", sp.getDataSourceLock());
      writer.writeEndElement("sync-provider");
      writer.writeEndElement("properties");
   }

   private void writeMetaData(XMLWriter writer) throws SQLException, IOException {
      writer.writeStartElement("metadata");
      writer.writeSimpleElements("column-count", this.metaData.getColumnCount());

      for(int i = 1; i <= this.metaData.getColumnCount(); ++i) {
         writer.writeStartElement("column-definition");
         writer.writeSimpleElements("column-index", i);
         writer.writeSimpleElements("auto-increment", this.metaData.isAutoIncrement(i));
         writer.writeSimpleElements("column-class-name", this.metaData.isDefinitelyWritable(i));
         writer.writeSimpleElements("definitely-writable", this.metaData.isDefinitelyWritable(i));
         writer.writeSimpleElements("case-sensitive", this.metaData.isCaseSensitive(i));
         writer.writeSimpleElements("currency", this.metaData.isCurrency(i));
         writer.writeSimpleElements("nullable", this.metaData.isNullable(i));
         writer.writeSimpleElements("signed", this.metaData.isSigned(i));
         writer.writeSimpleElements("searchable", this.metaData.isSearchable(i));
         writer.writeSimpleElements("column-display-size", this.metaData.getColumnDisplaySize(i));
         writer.writeSimpleElements("column-label", this.metaData.getColumnLabel(i));
         writer.writeSimpleElements("column-name", this.metaData.getColumnName(i));
         writer.writeSimpleElements("column-class-name", this.metaData.getColumnClassName(i));
         writer.writeSimpleElements("schema-name", this.metaData.getSchemaName(i));
         writer.writeSimpleElements("column-precision", this.metaData.getPrecision(i));
         writer.writeSimpleElements("column-scale", this.metaData.getScale(i));
         writer.writeSimpleElements("table-name", this.metaData.getTableName(i));
         writer.writeSimpleElements("catalog-name", this.metaData.getCatalogName(i));
         writer.writeSimpleElements("column-type", this.metaData.getColumnType(i));
         writer.writeSimpleElements("column-type-name", this.metaData.getColumnTypeName(i));
         writer.writeEndElement("column-definition");
      }

      writer.writeEndElement("metadata");
   }

   private void writeData(XMLWriter writer) throws SQLException, IOException {
      writer.writeStartElement("data");
      Iterator it = this.rowSet.getCachedRows().iterator();

      while(it.hasNext()) {
         this.writeRow((CachedRow)it.next(), writer);
      }

      writer.writeEndElement("data");
   }

   private void writeRow(CachedRow row, XMLWriter writer) throws SQLException, IOException {
      if (row.isInsertRow()) {
         writer.writeStartElement("insertRow");
         this.writeColumns(row, writer);
         writer.writeEndElement("insertRow");
      } else if (row.isDeletedRow()) {
         writer.writeStartElement("deleteRow");
         this.writeColumns(row, writer);
         writer.writeEndElement("deleteRow");
      } else if (row.isUpdatedRow()) {
         writer.writeStartElement("modifyRow");
         this.writeColumns(row, writer);
         writer.writeEndElement("modifyRow");
      } else {
         writer.writeStartElement("currentRow");
         this.writeColumns(row, writer);
         writer.writeEndElement("currentRow");
      }

   }

   private void writeColumns(CachedRow row, XMLWriter writer) throws SQLException, IOException {
      for(int i = 0; i < row.getColumnCount(); ++i) {
         if (row.isUpdatedRow() && row.isModified(i + 1)) {
            writer.writeStartElement("columnValue");
            writer.writeCharacterData(TypeMapper.getXMLValue(this.metaData.getColumnType(i + 1), row.getOldColumn(i + 1)));
            writer.writeEndElement("columnValue");
            writer.writeStartElement("updateValue");
            writer.writeCharacterData(TypeMapper.getXMLValue(this.metaData.getColumnType(i + 1), row.getColumn(i + 1)));
            writer.writeEndElement("updateValue");
         } else {
            writer.writeStartElement("columnValue");
            writer.writeCharacterData(TypeMapper.getXMLValue(this.metaData.getColumnType(i + 1), row.getColumn(i + 1)));
            writer.writeEndElement("columnValue");
         }
      }

   }
}
