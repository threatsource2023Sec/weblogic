package weblogic.jdbc.rowset;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.utils.AssertionError;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLOutputStream;

public final class XMLInstanceWriter implements XMLSchemaConstants {
   private WLRowSetInternal rowSet;
   private WLRowSetMetaData metaData;
   private static ColumnFilter all = new ColumnFilter() {
      public boolean include(CachedRow row, int i) {
         return true;
      }
   };
   private static ColumnFilter mod = new ColumnFilter() {
      public boolean include(CachedRow row, int i) throws SQLException {
         return row.isModified(i + 1);
      }
   };

   public XMLInstanceWriter(WLCachedRowSet rowSet) throws SQLException {
      this.rowSet = (WLRowSetInternal)rowSet;
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
      XMLWriter writer = new XMLWriter(xos, this.metaData.getDefaultNamespace());
      RowWriter rowWriter = XMLInstanceWriter.RowWriter.getWriter(writer, this.metaData, rowStates);
      List namespaces = new ArrayList();
      Attribute defaultNs = this.getDefaultNS(this.metaData.getDefaultNamespace());
      namespaces.add(defaultNs);
      namespaces.add(SCHEMA_INSTANCE_NAMESPACE);
      namespaces.add(WLDATA_NAMESPACE);
      List attrList = new ArrayList();
      attrList.add(this.getAttr(SCHEMA_LOCATION_NAME, this.metaData.getXMLSchemaLocation()));
      writer.writeStartElement(this.metaData.getRowSetName(), attrList.iterator(), namespaces.iterator());
      List rows = this.rowSet.getCachedRows();
      int row_id = 0;
      Iterator it = rows.iterator();

      while(it.hasNext()) {
         CachedRow row = (CachedRow)it.next();
         rowWriter.writeRow(row, row_id++);
      }

      writer.writeEndElement(this.metaData.getRowSetName());
      xos.flush();
   }

   private static class AllRowsWriter extends RowWriter {
      AllRowsWriter(XMLWriter writer, WLRowSetMetaData metaData) {
         super(writer, metaData);
      }

      void writeRow(CachedRow row, int id) throws SQLException, IOException {
         this.writeRow(row, true, id, XMLInstanceWriter.all);
         if (row.isUpdatedRow() && !row.isInsertRow()) {
            this.writeRow(row, false, id, XMLInstanceWriter.mod);
         }

      }
   }

   private static class ChangedAllWriter extends RowWriter {
      ChangedAllWriter(XMLWriter writer, WLRowSetMetaData metaData) {
         super(writer, metaData);
      }

      void writeRow(CachedRow row, int id) throws SQLException, IOException {
         if (row.isInsertRow() || row.isDeletedRow() || row.isUpdatedRow()) {
            this.writeRow(row, true, id, XMLInstanceWriter.all);
            if (row.isUpdatedRow()) {
               this.writeRow(row, false, id, XMLInstanceWriter.mod);
            }
         }

      }
   }

   private static class CurrentAllWriter extends RowWriter {
      CurrentAllWriter(XMLWriter writer, WLRowSetMetaData metaData) {
         super(writer, metaData);
      }

      void writeRow(CachedRow row, int id) throws SQLException, IOException {
         this.writeRow(row, true, id, XMLInstanceWriter.all);
      }
   }

   private static class ChangedOriginalWriter extends RowWriter {
      ChangedOriginalWriter(XMLWriter writer, WLRowSetMetaData metaData) {
         super(writer, metaData);
      }

      void writeRow(CachedRow row, int id) throws SQLException, IOException {
         if (row.isUpdatedRow()) {
            this.writeRow(row, false, id, XMLInstanceWriter.mod);
         }

      }
   }

   private static class ChangedCurrentWriter extends RowWriter {
      ChangedCurrentWriter(XMLWriter writer, WLRowSetMetaData metaData) {
         super(writer, metaData);
      }

      void writeRow(CachedRow row, int id) throws SQLException, IOException {
         if (row.isInsertRow() || row.isDeletedRow() || row.isUpdatedRow()) {
            this.writeRow(row, true, id, XMLInstanceWriter.all);
         }

      }
   }

   private static class UnchangedCurrentWriter extends RowWriter {
      UnchangedCurrentWriter(XMLWriter writer, WLRowSetMetaData metaData) {
         super(writer, metaData);
      }

      void writeRow(CachedRow row, int id) throws SQLException, IOException {
         if (!row.isInsertRow() && !row.isDeletedRow() && !row.isUpdatedRow()) {
            this.writeRow(row, true, id, XMLInstanceWriter.all);
         }
      }
   }

   private abstract static class RowWriter {
      private WLRowSetMetaData metaData;
      private XMLWriter writer;

      static RowWriter getWriter(XMLWriter writer, WLRowSetMetaData metaData, int rowStates) {
         switch (rowStates) {
            case 1:
               return new UnchangedCurrentWriter(writer, metaData);
            case 2:
               return new ChangedCurrentWriter(writer, metaData);
            case 4:
               return new ChangedOriginalWriter(writer, metaData);
            case 8:
               return new CurrentAllWriter(writer, metaData);
            case 16:
               return new ChangedAllWriter(writer, metaData);
            case 32:
               return new AllRowsWriter(writer, metaData);
            default:
               throw new AssertionError("Unexpected rowStates: " + rowStates);
         }
      }

      RowWriter(XMLWriter writer, WLRowSetMetaData metaData) {
         this.writer = writer;
         this.metaData = metaData;
      }

      abstract void writeRow(CachedRow var1, int var2) throws SQLException, IOException;

      private String getRowStateString(CachedRow row, boolean useCurrentValues) {
         if (row.isInsertRow()) {
            return "Inserted";
         } else if (row.isUpdatedRow() && useCurrentValues) {
            return "Modified";
         } else if (row.isUpdatedRow() && !useCurrentValues) {
            return "ModifiedOriginal";
         } else if (row.isDeletedRow()) {
            return "DeletedOriginal";
         } else {
            throw new AssertionError("Unexpected row: " + row);
         }
      }

      protected void writeRow(CachedRow row, boolean useCurrentValues, int id, ColumnFilter filter) throws SQLException, IOException {
         if (useCurrentValues && !row.isUpdatedRow() && !row.isInsertRow() && !row.isDeletedRow()) {
            this.writer.writeStartElement(this.metaData.getRowName(), XMLSchemaConstants.WLDD_ROWID, String.valueOf(id));
         } else {
            this.writer.writeStartElement(this.metaData.getRowName(), XMLSchemaConstants.WLDD_ROWID, String.valueOf(id), XMLSchemaConstants.WLDD_ROWSTATE, this.getRowStateString(row, useCurrentValues));
         }

         for(int i = 0; i < row.getColumnCount(); ++i) {
            if (filter.include(row, i)) {
               String colName = this.metaData.getColumnName(i + 1);
               Object value = null;
               if (useCurrentValues) {
                  if (row.isDeletedRow() && row.isUpdatedRow()) {
                     value = row.getOldColumn(i + 1);
                  } else {
                     value = row.getColumn(i + 1);
                  }
               } else {
                  value = row.getOldColumn(i + 1);
               }

               if (value == null) {
                  this.writer.writeStartElement(colName, XMLSchemaConstants.NIL_NAME, "true");
               } else {
                  this.writer.writeStartElement(colName);
                  this.writer.writeCharacterData(TypeMapper.getXMLValue(this.metaData.getColumnType(i + 1), value));
               }

               this.writer.writeEndElement(colName);
            }
         }

         this.writer.writeEndElement(this.metaData.getRowName());
      }
   }

   private interface ColumnFilter {
      boolean include(CachedRow var1, int var2) throws SQLException;
   }
}
