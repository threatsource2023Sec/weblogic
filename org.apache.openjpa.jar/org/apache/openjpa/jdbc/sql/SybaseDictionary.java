package org.apache.openjpa.jdbc.sql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.lib.jdbc.DelegatingConnection;
import org.apache.openjpa.lib.util.Localizer;

public class SybaseDictionary extends AbstractSQLServerDictionary {
   private static Localizer _loc = Localizer.forPackage(SybaseDictionary.class);
   public boolean createIdentityColumn = true;
   public String identityColumnName = "UNQ_INDEX";

   public SybaseDictionary() {
      this.platform = "Sybase";
      this.schemaCase = "preserve";
      this.forUpdateClause = "FOR UPDATE AT ISOLATION SERIALIZABLE";
      this.supportsLockingWithDistinctClause = false;
      this.supportsNullTableForGetColumns = false;
      this.requiresAliasForSubselect = true;
      this.requiresAutoCommitForMetaData = true;
      this.maxTableNameLength = 30;
      this.maxColumnNameLength = 30;
      this.maxIndexNameLength = 30;
      this.maxConstraintNameLength = 30;
      this.bigintTypeName = "NUMERIC(38)";
      this.bitTypeName = "TINYINT";
      this.crossJoinClause = "JOIN";
      this.requiresConditionForCrossJoin = true;
      this.systemTableSet.addAll(Arrays.asList("IJDBC_FUNCTION_ESCAPES", "JDBC_FUNCTION_ESCAPES", "SPT_IJDBC_CONVERSION", "SPT_IJDBC_MDA", "SPT_IJDBC_TABLE_TYPES", "SPT_JDBC_CONVERSION", "SPT_JDBC_TABLE_TYPES", "SPT_JTEXT", "SPT_LIMIT_TYPES", "SPT_MDA", "SPT_MONITOR", "SPT_VALUES", "SYBLICENSESLOG"));
      this.reservedWordSet.addAll(Arrays.asList("ARITH_OVERFLOW", "BREAK", "BROWSE", "BULK", "CHAR_CONVERT", "CHECKPOINT", "CLUSTERED", "COMPUTE", "CONFIRM", "CONTROLROW", "DATABASE", "DBCC", "DETERMINISTIC", "DISK DISTINCT", "DUMMY", "DUMP", "ENDTRAN", "ERRLVL", "ERRORDATA", "ERROREXIT", "EXCLUSIVE", "EXIT", "EXP_ROW_SIZE", "FILLFACTOR", "FUNC", "FUNCTION", "HOLDLOCK", "IDENTITY_GAP", "IDENTITY_INSERT", "IDENTITY_START", "IF", "INDEX", "INOUT", "INSTALL", "INTERSECT", "JAR", "KILL", "LINENO", "LOAD", "LOCK", "MAX_ROWS_PER_PAGE", "MIRROR", "MIRROREXIT", "MODIFY", "NEW", "NOHOLDLOCK", "NONCLUSTERED", "NUMERIC_TRUNCATION", "OFF", "OFFSETS", "ONCE", "ONLINE", "OUT", "OVER", "PARTITION", "PERM", "PERMANENT", "PLAN", "PRINT", "PROC", "PROCESSEXIT", "PROXY_TABLE", "QUIESCE", "RAISERROR", "READ", "READPAST", "READTEXT", "RECONFIGURE", "REFERENCES REMOVE", "REORG", "REPLACE", "REPLICATION", "RESERVEPAGEGAP", "RETURN", "RETURNS", "ROLE", "ROWCOUNT", "RULE", "SAVE", "SETUSER", "SHARED", "SHUTDOWN", "SOME", "STATISTICS", "STRINGSIZE", "STRIPE", "SYB_IDENTITY", "SYB_RESTREE", "SYB_TERMINATE", "TEMP", "TEXTSIZE", "TRAN", "TRIGGER", "TRUNCATE", "TSEQUAL", "UNPARTITION", "USE", "USER_OPTION", "WAITFOR", "WHILE", "WRITETEXT"));
   }

   public int getJDBCType(int metaTypeCode, boolean lob) {
      switch (metaTypeCode) {
         case 1:
         case 17:
            return this.getPreferredType(5);
         default:
            return super.getJDBCType(metaTypeCode, lob);
      }
   }

   public void setBigInteger(PreparedStatement stmnt, int idx, BigInteger val, Column col) throws SQLException {
      this.setObject(stmnt, idx, new BigDecimal(val), -5, col);
   }

   public String[] getAddForeignKeySQL(ForeignKey fk) {
      return new String[0];
   }

   public String[] getCreateTableSQL(Table table) {
      if (!this.createIdentityColumn) {
         return super.getCreateTableSQL(table);
      } else {
         StringBuffer buf = new StringBuffer();
         buf.append("CREATE TABLE ").append(this.getFullName(table, false)).append(" (");
         Column[] cols = table.getColumns();
         boolean hasIdentity = false;

         for(int i = 0; i < cols.length; ++i) {
            if (cols[i].isAutoAssigned()) {
               hasIdentity = true;
            }

            buf.append(i == 0 ? "" : ", ");
            buf.append(this.getDeclareColumnSQL(cols[i], false));
         }

         if (!hasIdentity) {
            buf.append(", ").append(this.identityColumnName).append(" NUMERIC IDENTITY UNIQUE");
         }

         PrimaryKey pk = table.getPrimaryKey();
         if (pk != null) {
            buf.append(", ").append(this.getPrimaryKeyConstraintSQL(pk));
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
   }

   protected String getDeclareColumnSQL(Column col, boolean alter) {
      StringBuffer buf = new StringBuffer();
      buf.append(col).append(" ");
      buf.append(this.getTypeName(col));
      if (!alter) {
         if (col.getDefaultString() != null && !col.isAutoAssigned()) {
            buf.append(" DEFAULT ").append(col.getDefaultString());
         }

         if (col.isAutoAssigned()) {
            buf.append(" IDENTITY");
         }
      }

      if (col.isNotNull()) {
         buf.append(" NOT NULL");
      } else if (!col.isPrimaryKey()) {
         buf.append(" NULL");
      }

      return buf.toString();
   }

   public String[] getDropColumnSQL(Column column) {
      return new String[]{"ALTER TABLE " + this.getFullName(column.getTable(), false) + " DROP " + column};
   }

   public void refSchemaComponents(Table table) {
      Column[] cols = table.getColumns();

      for(int i = 0; i < cols.length; ++i) {
         if (this.identityColumnName.equalsIgnoreCase(cols[i].getName())) {
            cols[i].ref();
         }
      }

   }

   public void endConfiguration() {
      super.endConfiguration();
      String url = this.conf.getConnectionURL();
      if (!StringUtils.isEmpty(url) && url.toLowerCase().indexOf("jdbc:sybase:tds") != -1 && url.toLowerCase().indexOf("be_as_jdbc_compliant_as_possible=") == -1) {
         this.log.warn(_loc.get("sybase-compliance", (Object)url));
      }

   }

   public Connection decorate(Connection conn) throws SQLException {
      return new SybaseConnection(super.decorate(conn));
   }

   private static class SybaseConnection extends DelegatingConnection {
      private String _catalog = null;

      public SybaseConnection(Connection conn) {
         super(conn);
      }

      public String getCatalog() throws SQLException {
         if (this._catalog == null) {
            this._catalog = super.getCatalog();
         }

         return this._catalog;
      }

      public void setAutoCommit(boolean autocommit) throws SQLException {
         try {
            super.setAutoCommit(autocommit);
         } catch (SQLException var3) {
            if (autocommit) {
               super.commit();
            } else {
               super.rollback();
            }

            super.setAutoCommit(autocommit);
         }

      }
   }
}
