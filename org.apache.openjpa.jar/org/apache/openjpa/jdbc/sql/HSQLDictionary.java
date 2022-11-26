package org.apache.openjpa.jdbc.sql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.ReferentialIntegrityException;

public class HSQLDictionary extends DBDictionary {
   public boolean cacheTables = false;
   private SQLBuffer _oneBuffer = (new SQLBuffer(this)).append("1");

   public HSQLDictionary() {
      this.platform = "HSQL";
      this.validationSQL = "CALL 1";
      this.closePoolSQL = "SHUTDOWN";
      this.supportsAutoAssign = true;
      this.lastGeneratedKeyQuery = "CALL IDENTITY()";
      this.autoAssignClause = "IDENTITY";
      this.autoAssignTypeName = "INTEGER";
      this.nextSequenceQuery = "SELECT NEXT VALUE FOR {0} FROM INFORMATION_SCHEMA.SYSTEM_SEQUENCES";
      this.crossJoinClause = "JOIN";
      this.requiresConditionForCrossJoin = true;
      this.stringLengthFunction = "LENGTH({0})";
      this.trimLeadingFunction = "LTRIM({0})";
      this.trimTrailingFunction = "RTRIM({0})";
      this.trimBothFunction = "LTRIM(RTRIM({0}))";
      this.useSchemaName = false;
      this.supportsSelectForUpdate = false;
      this.supportsSelectStartIndex = true;
      this.supportsSelectEndIndex = true;
      this.rangePosition = 1;
      this.supportsDeferredConstraints = false;
      this.useGetObjectForBlobs = true;
      this.blobTypeName = "VARBINARY";
      this.doubleTypeName = "NUMERIC";
      this.supportsNullTableForGetPrimaryKeys = true;
      this.supportsNullTableForGetIndexInfo = true;
      this.requiresCastForMathFunctions = true;
      this.requiresCastForComparisons = true;
      this.reservedWordSet.addAll(Arrays.asList("BEFORE", "BIGINT", "BINARY", "CACHED", "DATETIME", "LIMIT", "LONGVARBINARY", "LONGVARCHAR", "OBJECT", "OTHER", "SAVEPOINT", "TEMP", "TEXT", "TRIGGER", "TINYINT", "VARBINARY", "VARCHAR_IGNORECASE"));
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
      switch (type) {
         case 2004:
            return -3;
         case 2005:
            return 12;
         default:
            return super.getPreferredType(type);
      }
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
      buf.append("CREATE ");
      if (this.cacheTables) {
         buf.append("CACHED ");
      }

      buf.append("TABLE ").append(this.getFullName(table, false)).append(" (");
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
      return name.toUpperCase().startsWith("SYS_");
   }

   protected String getSequencesSQL(String schemaName, String sequenceName) {
      StringBuffer buf = new StringBuffer();
      buf.append("SELECT SEQUENCE_SCHEMA, SEQUENCE_NAME FROM ").append("INFORMATION_SCHEMA.SYSTEM_SEQUENCES");
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

   public SQLBuffer toOperation(String op, SQLBuffer selects, SQLBuffer from, SQLBuffer where, SQLBuffer group, SQLBuffer having, SQLBuffer order, boolean distinct, long start, long end, String forUpdateClause) {
      if ((start != 0L || end != Long.MAX_VALUE) && (order == null || order.isEmpty())) {
         order = this._oneBuffer;
      }

      return super.toOperation(op, selects, from, where, group, having, order, distinct, start, end, forUpdateClause);
   }

   public Column[] getColumns(DatabaseMetaData meta, String catalog, String schemaName, String tableName, String columnName, Connection conn) throws SQLException {
      Column[] cols = super.getColumns(meta, catalog, schemaName, tableName, columnName, conn);

      for(int i = 0; cols != null && i < cols.length; ++i) {
         if ("BOOLEAN".equalsIgnoreCase(cols[i].getTypeName())) {
            cols[i].setType(-7);
         }
      }

      return cols;
   }

   public void setDouble(PreparedStatement stmnt, int idx, double val, Column col) throws SQLException {
      if (val != 9.223372036854776E18 && val != -9.223372036854776E18) {
         super.setDouble(stmnt, idx, val, col);
      } else {
         stmnt.setLong(idx, (long)val);
      }

   }

   public void setBigDecimal(PreparedStatement stmnt, int idx, BigDecimal val, Column col) throws SQLException {
      int type = val != null && col != null ? col.getJavaType() : 24;
      switch (type) {
         case 3:
         case 19:
            this.setDouble(stmnt, idx, val.doubleValue(), col);
            break;
         case 4:
         case 20:
            this.setDouble(stmnt, idx, (double)val.floatValue(), col);
            break;
         default:
            super.setBigDecimal(stmnt, idx, val, col);
      }

   }

   protected void appendSelectRange(SQLBuffer buf, long start, long end, boolean subselect) {
      buf.append(" LIMIT ").append(String.valueOf(start)).append(" ");
      if (end == Long.MAX_VALUE) {
         buf.append(String.valueOf(0));
      } else {
         buf.append(String.valueOf(end - start));
      }

   }

   public void indexOf(SQLBuffer buf, FilterValue str, FilterValue find, FilterValue start) {
      buf.append("(LOCATE(");
      find.appendTo(buf);
      buf.append(", ");
      str.appendTo(buf);
      if (start != null) {
         buf.append(", (");
         start.appendTo(buf);
         buf.append(" + 1)");
      }

      buf.append(") - 1)");
   }

   public String getPlaceholderValueString(Column col) {
      String type = this.getTypeName(col.getType());
      int idx = type.indexOf("{0}");
      if (idx != -1) {
         String pre = type.substring(0, idx);
         if (type.length() > idx + 3) {
            type = pre + type.substring(idx + 3);
         } else {
            type = pre;
         }
      }

      return "NULL AS " + type;
   }

   public OpenJPAException newStoreException(String msg, SQLException[] causes, Object failed) {
      OpenJPAException ke = super.newStoreException(msg, causes, failed);
      if (ke instanceof ReferentialIntegrityException && causes[0].getErrorCode() == -9) {
         ((ReferentialIntegrityException)ke).setIntegrityViolation(2);
      }

      return ke;
   }
}
