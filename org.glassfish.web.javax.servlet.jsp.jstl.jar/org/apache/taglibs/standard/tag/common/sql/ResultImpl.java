package org.apache.taglibs.standard.tag.common.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.jsp.jstl.sql.Result;

public class ResultImpl implements Result {
   private List rowMap = new ArrayList();
   private List rowByIndex = new ArrayList();
   private String[] columnNames;
   private boolean isLimited;

   public ResultImpl(ResultSet rs, int startRow, int maxRows) throws SQLException {
      ResultSetMetaData rsmd = rs.getMetaData();
      int noOfColumns = rsmd.getColumnCount();
      this.columnNames = new String[noOfColumns];

      int processedRows;
      for(processedRows = 1; processedRows <= noOfColumns; ++processedRows) {
         this.columnNames[processedRows - 1] = rsmd.getColumnName(processedRows);
      }

      for(processedRows = 0; processedRows < startRow; ++processedRows) {
         rs.next();
      }

      for(processedRows = 0; rs.next(); ++processedRows) {
         if (maxRows != -1 && processedRows == maxRows) {
            this.isLimited = true;
            break;
         }

         Object[] columns = new Object[noOfColumns];
         SortedMap columnMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);

         for(int i = 1; i <= noOfColumns; ++i) {
            Object value = rs.getObject(i);
            if (rs.wasNull()) {
               value = null;
            }

            columns[i - 1] = value;
            columnMap.put(this.columnNames[i - 1], value);
         }

         this.rowMap.add(columnMap);
         this.rowByIndex.add(columns);
      }

   }

   public SortedMap[] getRows() {
      return this.rowMap == null ? null : (SortedMap[])((SortedMap[])this.rowMap.toArray(new SortedMap[0]));
   }

   public Object[][] getRowsByIndex() {
      return this.rowByIndex == null ? (Object[][])null : (Object[][])((Object[][])this.rowByIndex.toArray(new Object[0][0]));
   }

   public String[] getColumnNames() {
      return this.columnNames;
   }

   public int getRowCount() {
      return this.rowMap == null ? -1 : this.rowMap.size();
   }

   public boolean isLimitedByMaxRows() {
      return this.isLimited;
   }
}
