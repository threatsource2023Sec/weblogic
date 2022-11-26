package org.apache.openjpa.jdbc.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Table;

public class MySQLDictionary extends DBDictionary {
   public String tableType = "innodb";
   public boolean useClobs = true;
   public boolean driverDeserializesBlobs = false;
   public boolean optimizeMultiTableDeletes = false;

   public MySQLDictionary() {
      this.platform = "MySQL";
      this.validationSQL = "SELECT NOW()";
      this.distinctCountColumnSeparator = ",";
      this.supportsDeferredConstraints = false;
      this.constraintNameMode = "mid";
      this.supportsMultipleNontransactionalResultSets = false;
      this.requiresAliasForSubselect = true;
      this.requiresTargetForDelete = true;
      this.supportsSelectStartIndex = true;
      this.supportsSelectEndIndex = true;
      this.concatenateFunction = "CONCAT({0},{1})";
      this.maxTableNameLength = 64;
      this.maxColumnNameLength = 64;
      this.maxIndexNameLength = 64;
      this.maxConstraintNameLength = 64;
      this.maxIndexesPerTable = 32;
      this.schemaCase = "preserve";
      this.supportsAutoAssign = true;
      this.lastGeneratedKeyQuery = "SELECT LAST_INSERT_ID()";
      this.autoAssignClause = "AUTO_INCREMENT";
      this.clobTypeName = "TEXT";
      this.longVarcharTypeName = "TEXT";
      this.longVarbinaryTypeName = "LONG VARBINARY";
      this.timestampTypeName = "DATETIME";
      this.fixedSizeTypeNameSet.addAll(Arrays.asList("BOOL", "LONG VARBINARY", "MEDIUMBLOB", "LONGBLOB", "TINYBLOB", "LONG VARCHAR", "MEDIUMTEXT", "LONGTEXT", "TEXT", "TINYTEXT", "DOUBLE PRECISION", "ENUM", "SET", "DATETIME"));
      this.reservedWordSet.addAll(Arrays.asList("INT1", "INT2", "INT4", "FLOAT1", "FLOAT2", "FLOAT4", "AUTO_INCREMENT", "BINARY", "BLOB", "CHANGE", "ENUM", "INFILE", "LOAD", "MEDIUMINT", "OPTION", "OUTFILE", "REPLACE", "SET", "STARTING", "TEXT", "UNSIGNED", "ZEROFILL"));
      this.searchStringEscape = "\\\\";
      this.typeModifierSet.addAll(Arrays.asList("UNSIGNED", "ZEROFILL"));
   }

   public void connectedConfiguration(Connection conn) throws SQLException {
      super.connectedConfiguration(conn);
      DatabaseMetaData metaData = conn.getMetaData();
      String productVersion = metaData.getDatabaseProductVersion();
      String driverVersion = metaData.getDriverVersion();

      try {
         int[] versions = getMajorMinorVersions(productVersion);
         int maj = versions[0];
         int min = versions[1];
         if (maj < 4 || maj == 4 && min < 1) {
            this.supportsSubselect = false;
            this.allowsAliasInBulkClause = false;
         }

         versions = getMajorMinorVersions(driverVersion);
         maj = versions[0];
         if (maj < 5) {
            this.driverDeserializesBlobs = true;
         }
      } catch (IllegalArgumentException var8) {
      }

   }

   private static int[] getMajorMinorVersions(String versionStr) throws IllegalArgumentException {
      int beginIndex = 0;
      int endIndex = 0;
      versionStr = versionStr.trim();
      char[] charArr = versionStr.toCharArray();

      int i;
      for(i = 0; i < charArr.length; ++i) {
         if (Character.isDigit(charArr[i])) {
            beginIndex = i;
            break;
         }
      }

      for(i = beginIndex + 1; i < charArr.length; ++i) {
         if (charArr[i] != '.' && !Character.isDigit(charArr[i])) {
            endIndex = i;
            break;
         }
      }

      if (endIndex < beginIndex) {
         throw new IllegalArgumentException();
      } else {
         String[] arr = versionStr.substring(beginIndex, endIndex).split("\\.");
         if (arr.length < 2) {
            throw new IllegalArgumentException();
         } else {
            int maj = Integer.parseInt(arr[0]);
            int min = Integer.parseInt(arr[1]);
            return new int[]{maj, min};
         }
      }
   }

   public String[] getCreateTableSQL(Table table) {
      String[] sql = super.getCreateTableSQL(table);
      if (!StringUtils.isEmpty(this.tableType)) {
         sql[0] = sql[0] + " ENGINE = " + this.tableType;
      }

      return sql;
   }

   public String[] getDropIndexSQL(Index index) {
      return new String[]{"DROP INDEX " + this.getFullName(index) + " ON " + this.getFullName(index.getTable(), false)};
   }

   public String[] getAddPrimaryKeySQL(PrimaryKey pk) {
      String[] sql = super.getAddPrimaryKeySQL(pk);
      Column[] cols = pk.getColumns();
      String[] ret = new String[cols.length + sql.length];

      for(int i = 0; i < cols.length; ++i) {
         ret[i] = "ALTER TABLE " + this.getFullName(cols[i].getTable(), false) + " CHANGE " + cols[i].getName() + " " + cols[i].getName() + " " + this.getTypeName(cols[i]) + " NOT NULL";
      }

      System.arraycopy(sql, 0, ret, cols.length, sql.length);
      return ret;
   }

   protected String getForeignKeyConstraintSQL(ForeignKey fk) {
      return fk.getColumns().length > 1 ? null : super.getForeignKeyConstraintSQL(fk);
   }

   public String[] getDeleteTableContentsSQL(Table[] tables) {
      if (!this.optimizeMultiTableDeletes) {
         return super.getDeleteTableContentsSQL(tables);
      } else {
         StringBuffer buf = new StringBuffer(tables.length * 8);
         buf.append("DELETE FROM ");

         for(int i = 0; i < tables.length; ++i) {
            buf.append(tables[i].getFullName());
            if (i < tables.length - 1) {
               buf.append(", ");
            }
         }

         return new String[]{buf.toString()};
      }
   }

   protected void appendSelectRange(SQLBuffer buf, long start, long end, boolean subselect) {
      buf.append(" LIMIT ").appendValue(start).append(", ");
      if (end == Long.MAX_VALUE) {
         buf.appendValue(Long.MAX_VALUE);
      } else {
         buf.appendValue(end - start);
      }

   }

   protected Column newColumn(ResultSet colMeta) throws SQLException {
      Column col = super.newColumn(colMeta);
      if (col.isNotNull() && "0".equals(col.getDefaultString())) {
         col.setDefaultString((String)null);
      }

      return col;
   }

   public Object getBlobObject(ResultSet rs, int column, JDBCStore store) throws SQLException {
      return !this.useGetBytesForBlobs && !this.useGetObjectForBlobs && this.driverDeserializesBlobs ? rs.getObject(column) : super.getBlobObject(rs, column, store);
   }

   public int getPreferredType(int type) {
      return type == 2005 && !this.useClobs ? -1 : super.getPreferredType(type);
   }
}
