package org.apache.openjpa.jdbc.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Table;

public class EmpressDictionary extends DBDictionary {
   public boolean allowConcurrentRead = false;

   public EmpressDictionary() {
      this.platform = "Empress";
      this.validationSQL = "SELECT DISTINCT today FROM sys_tables";
      this.joinSyntax = 1;
      this.toUpperCaseFunction = "TOUPPER({0})";
      this.toLowerCaseFunction = "TOLOWER({0})";
      this.supportsDeferredConstraints = false;
      this.requiresAliasForSubselect = true;
      this.maxTableNameLength = 28;
      this.maxColumnNameLength = 28;
      this.maxIndexNameLength = 28;
      this.maxConstraintNameLength = 28;
      this.schemaCase = "preserve";
      this.useGetBytesForBlobs = true;
      this.useSetBytesForBlobs = true;
      this.useGetStringForClobs = true;
      this.useSetStringForClobs = true;
      this.clobTypeName = "TEXT";
      this.blobTypeName = "BULK";
      this.realTypeName = "FLOAT(8)";
      this.bigintTypeName = "DECIMAL(38)";
      this.timestampTypeName = "DATE";
      this.varcharTypeName = "CHARACTER";
      this.tinyintTypeName = "DOUBLE PRECISION";
      this.doubleTypeName = "SMALLINT";
      this.bitTypeName = "SMALLINT";
      this.fixedSizeTypeNameSet.addAll(Arrays.asList("TEXT", "BULK", "LONGFLOAT", "INTEGER64", "SHORTINTEGER", "LONGINTEGER"));
   }

   public boolean isSystemIndex(String name, Table table) {
      return name.toUpperCase().startsWith("SYS_");
   }

   public SQLBuffer toSelect(SQLBuffer selects, JDBCFetchConfiguration fetch, SQLBuffer from, SQLBuffer where, SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, boolean forUpdate, long startIdx, long endIdx) {
      if (!this.allowConcurrentRead) {
         return super.toSelect(selects, fetch, from, where, group, having, order, distinct, forUpdate, startIdx, endIdx);
      } else {
         SQLBuffer buf = new SQLBuffer(this);
         buf.append("SELECT BYPASS ");
         if (distinct) {
            buf.append("DISTINCT ");
         }

         buf.append(selects).append(" FROM ").append(from);
         if (where != null && !where.isEmpty()) {
            buf.append(" WHERE ").append(where);
         }

         if (group != null && !group.isEmpty()) {
            buf.append(" GROUP BY ").append(group);
         }

         if (having != null && !having.isEmpty()) {
            buf.append(" HAVING ").append(having);
         }

         if (order != null && !order.isEmpty()) {
            buf.append(" ORDER BY ").append(order);
         }

         return buf;
      }
   }

   public String[] getDropColumnSQL(Column column) {
      return new String[]{"ALTER TABLE " + this.getFullName(column.getTable(), false) + " DELETE " + column};
   }

   public void setFloat(PreparedStatement stmnt, int idx, float val, Column col) throws SQLException {
      if (val == Float.POSITIVE_INFINITY) {
         val = Float.MAX_VALUE;
         this.storageWarning(new Float(Float.POSITIVE_INFINITY), new Float(val));
      } else if (val == Float.NEGATIVE_INFINITY) {
         val = 1.0F;
         this.storageWarning(new Float(Float.NEGATIVE_INFINITY), new Float(val));
      }

      super.setFloat(stmnt, idx, val, col);
   }

   public void setDouble(PreparedStatement stmnt, int idx, double val, Column col) throws SQLException {
      if (val == Double.POSITIVE_INFINITY) {
         val = Double.MAX_VALUE;
         this.storageWarning(new Double(Double.POSITIVE_INFINITY), new Double(val));
      } else if (val == Double.NEGATIVE_INFINITY) {
         val = 1.0;
         this.storageWarning(new Double(Double.NEGATIVE_INFINITY), new Double(val));
      }

      super.setDouble(stmnt, idx, val, col);
   }
}
