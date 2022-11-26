package org.apache.openjpa.jdbc.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Index;

public abstract class AbstractSQLServerDictionary extends DBDictionary {
   public AbstractSQLServerDictionary() {
      this.reservedWordSet.addAll(Arrays.asList("FILE", "INDEX"));
      this.systemTableSet.add("DTPROPERTIES");
      this.validationSQL = "SELECT GETDATE()";
      this.rangePosition = 2;
      this.supportsDeferredConstraints = false;
      this.supportsSelectEndIndex = true;
      this.allowsAliasInBulkClause = false;
      this.supportsAutoAssign = true;
      this.autoAssignClause = "IDENTITY";
      this.lastGeneratedKeyQuery = "SELECT @@IDENTITY";
      this.trimLeadingFunction = "LTRIM({0})";
      this.trimTrailingFunction = "RTRIM({0})";
      this.trimBothFunction = "LTRIM(RTRIM({0}))";
      this.concatenateFunction = "({0}+{1})";
      this.supportsModOperator = true;
      this.currentDateFunction = "GETDATE()";
      this.currentTimeFunction = "GETDATE()";
      this.currentTimestampFunction = "GETDATE()";
      this.useGetStringForClobs = true;
      this.useSetStringForClobs = true;
      this.useGetBytesForBlobs = true;
      this.useSetBytesForBlobs = true;
      this.binaryTypeName = "BINARY";
      this.blobTypeName = "IMAGE";
      this.longVarbinaryTypeName = "IMAGE";
      this.clobTypeName = "TEXT";
      this.longVarcharTypeName = "TEXT";
      this.dateTypeName = "DATETIME";
      this.timeTypeName = "DATETIME";
      this.timestampTypeName = "DATETIME";
      this.floatTypeName = "FLOAT(16)";
      this.doubleTypeName = "FLOAT(32)";
      this.integerTypeName = "INT";
      this.fixedSizeTypeNameSet.addAll(Arrays.asList("IMAGE", "TEXT", "NTEXT", "MONEY", "SMALLMONEY", "INT", "DOUBLE PRECISION", "DATETIME", "SMALLDATETIME", "EXTENDED TYPE", "SYSNAME", "SQL_VARIANT", "INDEX"));
   }

   public Column[] getColumns(DatabaseMetaData meta, String catalog, String schemaName, String tableName, String colName, Connection conn) throws SQLException {
      Column[] cols = super.getColumns(meta, catalog, schemaName, tableName, colName, conn);

      for(int i = 0; cols != null && i < cols.length; ++i) {
         if (cols[i].getType() == -1) {
            cols[i].setType(2005);
         }
      }

      return cols;
   }

   public String getFullName(Index idx) {
      return this.getFullName(idx.getTable(), false) + "." + idx.getName();
   }

   public void setNull(PreparedStatement stmnt, int idx, int colType, Column col) throws SQLException {
      if (colType == 2005) {
         stmnt.setString(idx, (String)null);
      } else if (colType == 2004) {
         stmnt.setBytes(idx, (byte[])null);
      } else {
         super.setNull(stmnt, idx, colType, col);
      }

   }

   protected void appendSelectRange(SQLBuffer buf, long start, long end, boolean subselect) {
      buf.append(" TOP ").append(Long.toString(end));
   }

   public void substring(SQLBuffer buf, FilterValue str, FilterValue start, FilterValue end) {
      if (end != null) {
         super.substring(buf, str, start, end);
      } else {
         buf.append("SUBSTRING(");
         str.appendTo(buf);
         buf.append(", ");
         start.appendTo(buf);
         buf.append(" + 1, ");
         buf.append("LEN(");
         str.appendTo(buf);
         buf.append(")");
         buf.append(" - (");
         start.appendTo(buf);
         buf.append("))");
      }

   }

   public void indexOf(SQLBuffer buf, FilterValue str, FilterValue find, FilterValue start) {
      buf.append("(CHARINDEX(");
      find.appendTo(buf);
      buf.append(", ");
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
