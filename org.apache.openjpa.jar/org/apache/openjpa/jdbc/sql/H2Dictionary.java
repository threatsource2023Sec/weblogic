package org.apache.openjpa.jdbc.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;

public class H2Dictionary extends DBDictionary {
   public H2Dictionary() {
      this.platform = "H2";
      this.validationSQL = "CALL 1";
      this.closePoolSQL = "SHUTDOWN";
      this.supportsAutoAssign = true;
      this.lastGeneratedKeyQuery = "CALL IDENTITY()";
      this.autoAssignClause = "IDENTITY";
      this.autoAssignTypeName = "INTEGER";
      this.nextSequenceQuery = "CALL NEXT VALUE FOR {0}";
      this.crossJoinClause = "JOIN";
      this.requiresConditionForCrossJoin = true;
      this.stringLengthFunction = "LENGTH({0})";
      this.trimLeadingFunction = "LTRIM({0})";
      this.trimTrailingFunction = "RTRIM({0})";
      this.trimBothFunction = "TRIM({0})";
      this.useSchemaName = true;
      this.supportsSelectForUpdate = true;
      this.supportsSelectStartIndex = true;
      this.supportsSelectEndIndex = true;
      this.rangePosition = 3;
      this.supportsDeferredConstraints = false;
      this.blobTypeName = "BLOB";
      this.doubleTypeName = "DOUBLE";
      this.supportsNullTableForGetPrimaryKeys = true;
      this.supportsNullTableForGetIndexInfo = true;
      this.requiresCastForMathFunctions = false;
      this.requiresCastForComparisons = false;
      this.reservedWordSet.addAll(Arrays.asList("CURRENT_TIMESTAMP", "CURRENT_TIME", "CURRENT_DATE", "CROSS", "DISTINCT", "EXCEPT", "EXISTS", "FROM", "FOR", "FALSE", "FULL", "GROUP", "HAVING", "INNER", "INTERSECT", "IS", "JOIN", "LIKE", "MINUS", "NATURAL", "NOT", "NULL", "ON", "ORDER", "PRIMARY", "ROWNUM", "SELECT", "SYSDATE", "SYSTIME", "SYSTIMESTAMP", "TODAY", "TRUE", "UNION", "WHERE"));
   }

   public int getJDBCType(int metaTypeCode, boolean lob) {
      int type = super.getJDBCType(metaTypeCode, lob);
      switch (type) {
         case -5:
            if (metaTypeCode == 25) {
               return 2;
            }
         default:
            return type;
      }
   }

   public int getPreferredType(int type) {
      return super.getPreferredType(type);
   }

   public String[] getAddPrimaryKeySQL(PrimaryKey pk) {
      return new String[0];
   }

   public String[] getDropPrimaryKeySQL(PrimaryKey pk) {
      return new String[0];
   }

   public String[] getAddColumnSQL(Column column) {
      return new String[]{"ALTER TABLE " + this.getFullName(column.getTable(), false) + " ADD COLUMN " + this.getDeclareColumnSQL(column, true)};
   }

   public String[] getCreateTableSQL(Table table) {
      StringBuffer buf = new StringBuffer();
      buf.append("CREATE TABLE ").append(this.getFullName(table, false)).append(" (");
      Column[] cols = table.getColumns();

      for(int i = 0; i < cols.length; ++i) {
         if (i > 0) {
            buf.append(", ");
         }

         buf.append(this.getDeclareColumnSQL(cols[i], false));
      }

      PrimaryKey pk = table.getPrimaryKey();
      if (pk != null) {
         String pkStr = this.getPrimaryKeyConstraintSQL(pk);
         if (!StringUtils.isEmpty(pkStr)) {
            buf.append(", ").append(pkStr);
         }
      }

      Unique[] unqs = table.getUniques();

      for(int i = 0; i < unqs.length; ++i) {
         String unqStr = this.getUniqueConstraintSQL(unqs[i]);
         if (unqStr != null) {
            buf.append(", ").append(unqStr);
         }
      }

      buf.append(")");
      return new String[]{buf.toString()};
   }

   protected String getPrimaryKeyConstraintSQL(PrimaryKey pk) {
      Column[] cols = pk.getColumns();
      return cols.length == 1 && cols[0].isAutoAssigned() ? null : super.getPrimaryKeyConstraintSQL(pk);
   }

   public boolean isSystemIndex(String name, Table table) {
      return name.toUpperCase(Locale.ENGLISH).startsWith("SYSTEM_");
   }

   protected String getSequencesSQL(String schemaName, String sequenceName) {
      StringBuffer buf = new StringBuffer();
      buf.append("SELECT SEQUENCE_SCHEMA, SEQUENCE_NAME FROM ").append("INFORMATION_SCHEMA.SEQUENCES");
      if (schemaName != null || sequenceName != null) {
         buf.append(" WHERE ");
      }

      if (schemaName != null) {
         buf.append("SEQUENCE_SCHEMA = ?");
         if (sequenceName != null) {
            buf.append(" AND ");
         }
      }

      if (sequenceName != null) {
         buf.append("SEQUENCE_NAME = ?");
      }

      return buf.toString();
   }

   public Column[] getColumns(DatabaseMetaData meta, String catalog, String schemaName, String tableName, String columnName, Connection conn) throws SQLException {
      Column[] cols = super.getColumns(meta, catalog, schemaName, tableName, columnName, conn);
      return cols;
   }

   protected void appendSelectRange(SQLBuffer buf, long start, long end, boolean subselect) {
      if (end != Long.MAX_VALUE) {
         buf.append(" LIMIT ").appendValue(end - start);
      }

      if (start != 0L) {
         buf.append(" OFFSET ").appendValue(start);
      }

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
