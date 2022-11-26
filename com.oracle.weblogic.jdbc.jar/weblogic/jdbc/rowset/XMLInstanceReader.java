package weblogic.jdbc.rowset;

import java.io.IOException;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.CharacterData;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.util.TypeFilter;

public final class XMLInstanceReader implements XMLSchemaConstants {
   private static final int ROW_UNCHANGED = 1;
   private static final int ROW_INSERTED = 2;
   private static final int ROW_MODIFIED = 4;
   private static final int ROW_MODIFIED_ORIGINAL = 8;
   private static final int ROW_DELETED_ORIGINAL = 16;
   private WLCachedRowSet rowSet;
   private WLRowSetMetaData metaData;
   private XMLName rowName;
   private XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();

   public XMLInstanceReader(WLCachedRowSet rowSet) throws SQLException {
      this.rowSet = rowSet;
      this.metaData = (WLRowSetMetaData)rowSet.getMetaData();
      this.rowName = ElementFactory.createXMLName(this.metaData.getDefaultNamespace(), this.metaData.getRowName());
   }

   private BitSet readRow(XMLInputStream xis, CachedRow row) throws IOException, SQLException {
      String columnName = null;
      BitSet modCols = new BitSet();

      XMLEvent e;
      do {
         if (!xis.hasNext()) {
            return modCols;
         }

         e = xis.next();
         int i;
         if (e instanceof StartElement) {
            columnName = e.getName().getLocalName();
            if (XMLUtils.getOptionalBooleanAttribute((StartElement)e, NIL_NAME)) {
               if (columnName == null) {
                  throw new IOException("Found null column value:  with no corresponding column name.");
               }

               i = this.metaData.findColumn(columnName);
               row.setColumn(i, (Object)null);
               modCols.set(i - 1);
            }
         } else if (e instanceof CharacterData) {
            if (columnName == null) {
               throw new IOException("Found column value: " + ((CharacterData)e).getContent() + " with no corresponding column name.");
            }

            i = this.metaData.findColumn(columnName);
            Object val = TypeMapper.getJavaValue(this.metaData.getColumnType(i), ((CharacterData)e).getContent());
            row.setColumn(i, val);
            modCols.set(i - 1);
         }

         e = xis.peek();
      } while(!(e instanceof StartElement) || !this.rowName.equals(((StartElement)e).getName()));

      return modCols;
   }

   private int getRowId(StartElement se) throws IOException {
      Attribute id = se.getAttributeByName(WLDD_ROWID);
      if (id == null) {
         throw new IOException("Unable to find required id attribute on row");
      } else {
         String idValue = id.getValue();
         if (idValue == null) {
            throw new IOException("Row's id attribute was found, but the value was null");
         } else {
            try {
               return Integer.parseInt(idValue);
            } catch (NumberFormatException var5) {
               throw new IOException("Could not convert row's id value: " + idValue + " into an integer value: " + var5);
            }
         }
      }
   }

   private int getRowState(StartElement se) throws IOException {
      Attribute state = se.getAttributeByName(WLDD_ROWSTATE);
      if (state == null) {
         return 1;
      } else {
         String stateValue = state.getValue();
         if ("unchanged".equalsIgnoreCase(stateValue)) {
            return 1;
         } else if ("inserted".equalsIgnoreCase(stateValue)) {
            return 2;
         } else if ("modified".equalsIgnoreCase(stateValue)) {
            return 4;
         } else if ("modifiedoriginal".equalsIgnoreCase(stateValue)) {
            return 8;
         } else if ("deletedoriginal".equalsIgnoreCase(stateValue)) {
            return 16;
         } else {
            throw new IOException("Unrecognized row state: " + stateValue);
         }
      }
   }

   private void setRowStateFlags(CachedRow row, int rowState) throws SQLException {
      switch (rowState) {
         case 1:
            row.setInsertRow(false);
            row.setDeletedRow(false);
            row.setUpdatedRow(false);
            break;
         case 2:
            row.setInsertRow(true);
            row.setDeletedRow(false);
            row.setUpdatedRow(false);
            break;
         case 4:
            row.setInsertRow(false);
            row.setDeletedRow(false);
            row.setUpdatedRow(true);
            break;
         case 8:
            row.setInsertRow(false);
            row.setDeletedRow(false);
            row.setUpdatedRow(false);
            break;
         case 16:
            row.setInsertRow(false);
            row.setDeletedRow(true);
            row.setUpdatedRow(false);
            break;
         default:
            throw new AssertionError(rowState);
      }

   }

   private void addRow(Map rowMap, Map modMap, CachedRow row, int rowId, int rowState) throws IOException, SQLException {
      Integer rowKey = new Integer(rowId);
      CachedRow prevRow = (CachedRow)rowMap.get(rowKey);
      if (prevRow == null) {
         this.setRowStateFlags(row, rowState);
         rowMap.put(rowKey, row);
      } else {
         BitSet modCols = (BitSet)modMap.get(rowKey);
         if (rowState == 4) {
            prevRow.mergeNewValues(row, modCols);
         } else {
            if (rowState != 8) {
               throw new IOException("Found multiple rows with an id: " + rowId + ", but the row states did not indicate they were the same row");
            }

            prevRow.mergeOriginalValues(row, modCols);
         }
      }

   }

   private void readRows(XMLInputStream xis) throws IOException, SQLException {
      Map rowMap = new TreeMap();

      CachedRow row;
      int rowId;
      int rowState;
      for(Map modMap = new HashMap(); xis.hasNext(); this.addRow(rowMap, modMap, row, rowId, rowState)) {
         StartElement se = (StartElement)xis.next();
         row = new CachedRow(this.metaData);
         BitSet modCols = this.readRow(xis, row);
         rowId = this.getRowId(se);
         rowState = this.getRowState(se);
         if (rowState == 8) {
            modMap.put(new Integer(rowId), modCols);
         }
      }

      ((WLRowSetInternal)this.rowSet).getCachedRows().addAll(rowMap.values());
   }

   public void loadXML(XMLInputStream stream) throws IOException, SQLException {
      XMLInputStream xis = this.factory.newInputStream(stream, new TypeFilter(18));
      this.parseRowSet(xis);
   }

   private void parseRowSet(XMLInputStream xis) throws IOException, SQLException {
      if (!xis.hasNext()) {
         throw new IOException("XML Instance document is empty");
      } else {
         StartElement se = (StartElement)xis.next();
         String schemaLocation = XMLUtils.getOptionalStringAttribute(se, SCHEMA_LOCATION_NAME);
         if (schemaLocation != null) {
            this.metaData.setXMLSchemaLocation(schemaLocation);
         }

         xis.skip(this.rowName);
         this.readRows(xis);
      }
   }
}
