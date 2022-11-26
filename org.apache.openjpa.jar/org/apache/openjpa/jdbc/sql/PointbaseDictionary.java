package org.apache.openjpa.jdbc.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Index;

public class PointbaseDictionary extends DBDictionary {
   public PointbaseDictionary() {
      this.platform = "Pointbase Embedded";
      this.supportsDeferredConstraints = false;
      this.supportsMultipleNontransactionalResultSets = false;
      this.requiresAliasForSubselect = true;
      this.supportsLockingWithDistinctClause = false;
      this.supportsLockingWithMultipleTables = false;
      this.supportsLockingWithDistinctClause = false;
      this.bitTypeName = "TINYINT";
      this.blobTypeName = "BLOB(1M)";
      this.longVarbinaryTypeName = "BLOB(1M)";
      this.charTypeName = "CHARACTER{0}";
      this.clobTypeName = "CLOB(1M)";
      this.doubleTypeName = "DOUBLE PRECISION";
      this.floatTypeName = "FLOAT";
      this.bigintTypeName = "BIGINT";
      this.integerTypeName = "INTEGER";
      this.realTypeName = "REAL";
      this.smallintTypeName = "SMALLINT";
      this.tinyintTypeName = "TINYINT";
      this.supportsAutoAssign = true;
      this.lastGeneratedKeyQuery = "SELECT MAX({0}) FROM {1}";
      this.autoAssignTypeName = "BIGINT IDENTITY";
   }

   public int getPreferredType(int type) {
      switch (type) {
         case -1:
            return 2005;
         default:
            return super.getPreferredType(type);
      }
   }

   public Column[] getColumns(DatabaseMetaData meta, String catalog, String schemaName, String tableName, String columnName, Connection conn) throws SQLException {
      Column[] cols = super.getColumns(meta, catalog, schemaName, tableName, columnName, conn);

      for(int i = 0; cols != null && i < cols.length; ++i) {
         if (cols[i].getTypeName().toUpperCase().startsWith("CLOB")) {
            cols[i].setType(2005);
         }
      }

      return cols;
   }

   public String getFullName(Index index) {
      return this.getFullName(index.getTable(), false) + "." + index.getName();
   }

   public void substring(SQLBuffer buf, FilterValue str, FilterValue start, FilterValue end) {
      buf.append("SUBSTRING(");
      str.appendTo(buf);
      buf.append(" FROM ");
      start.appendTo(buf);
      buf.append(" + 1");
      if (end != null) {
         buf.append(" FOR ");
         end.appendTo(buf);
         buf.append(" - (");
         start.appendTo(buf);
         buf.append(")");
      }

      buf.append(")");
   }

   public void indexOf(SQLBuffer buf, FilterValue str, FilterValue find, FilterValue start) {
      buf.append("(POSITION(");
      find.appendTo(buf);
      buf.append(" IN ");
      if (start != null) {
         this.substring(buf, str, start, (FilterValue)null);
      } else {
         str.appendTo(buf);
      }

      buf.append(") - 1");
      if (start != null) {
         buf.append(" + ");
         start.appendTo(buf);
      }

      buf.append(")");
   }
}
