package weblogic.jdbc.rmi.internal;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;

public class ResultSetMetaDataCache implements Serializable {
   private int[] cachedTypes;
   private HashMap nameColLookup;
   private static final int INITIAL_HASH_SIZE = 89;

   ResultSetMetaDataCache() {
   }

   ResultSetMetaDataCache(java.sql.ResultSet rs) throws SQLException {
      if (rs != null) {
         java.sql.ResultSetMetaData meta_data = rs.getMetaData();
         int numCols = meta_data.getColumnCount();
         this.cachedTypes = new int[numCols];
         this.nameColLookup = new HashMap(89);

         for(int i = 0; i < numCols; ++i) {
            this.cachedTypes[i] = meta_data.getColumnType(i + 1);
            String col_name = meta_data.getColumnName(i + 1);
            this.nameColLookup.put(col_name.toLowerCase(), new Integer(i + 1));
         }

      }
   }

   int getColumnCount() {
      return this.cachedTypes.length;
   }

   int getColumnType(int index) {
      return this.cachedTypes[index - 1];
   }

   int getColumnTypeZeroBased(int index) {
      return this.cachedTypes[index];
   }

   int getColumnType(String column_name) throws SQLException {
      return this.getColumnType(this.findColumn(column_name));
   }

   public int findColumn(String column_name) throws SQLException {
      String lookup_key = column_name.toLowerCase();
      Integer col = (Integer)this.nameColLookup.get(lookup_key);
      if (col == null) {
         throw new SQLException("no such column in this ResultSet:" + column_name);
      } else {
         return col;
      }
   }
}
