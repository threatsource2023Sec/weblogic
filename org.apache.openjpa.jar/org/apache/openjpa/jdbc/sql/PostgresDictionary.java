package org.apache.openjpa.jdbc.sql;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Sequence;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.lib.jdbc.DelegatingConnection;
import org.apache.openjpa.lib.jdbc.DelegatingPreparedStatement;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.StoreException;
import org.postgresql.PGConnection;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

public class PostgresDictionary extends DBDictionary {
   private static final Localizer _loc = Localizer.forPackage(PostgresDictionary.class);
   public String allSequencesSQL = "SELECT NULL AS SEQUENCE_SCHEMA, relname AS SEQUENCE_NAME FROM pg_class WHERE relkind='S'";
   public String namedSequencesFromAllSchemasSQL = "SELECT NULL AS SEQUENCE_SCHEMA, relname AS SEQUENCE_NAME FROM pg_class WHERE relkind='S' AND relname = ?";
   public String allSequencesFromOneSchemaSQL = "SELECT NULL AS SEQUENCE_SCHEMA, relname AS SEQUENCE_NAME FROM pg_class, pg_namespace WHERE relkind='S' AND pg_class.relnamespace = pg_namespace.oid AND nspname = ?";
   public String namedSequenceFromOneSchemaSQL = "SELECT NULL AS SEQUENCE_SCHEMA, relname AS SEQUENCE_NAME FROM pg_class, pg_namespace WHERE relkind='S' AND pg_class.relnamespace = pg_namespace.oid AND relname = ? AND nspname = ?";
   public boolean supportsSetFetchSize = true;

   public PostgresDictionary() {
      this.platform = "PostgreSQL";
      this.validationSQL = "SELECT NOW()";
      this.datePrecision = 10000000;
      this.supportsAlterTableWithDropColumn = false;
      this.supportsDeferredConstraints = true;
      this.supportsSelectStartIndex = true;
      this.supportsSelectEndIndex = true;
      this.searchStringEscape = "\\\\";
      this.maxTableNameLength = 63;
      this.maxColumnNameLength = 63;
      this.maxIndexNameLength = 63;
      this.maxConstraintNameLength = 63;
      this.maxAutoAssignNameLength = 63;
      this.schemaCase = "lower";
      this.rangePosition = 3;
      this.requiresAliasForSubselect = true;
      this.allowsAliasInBulkClause = false;
      this.lastGeneratedKeyQuery = "SELECT CURRVAL(''{2}'')";
      this.supportsAutoAssign = true;
      this.autoAssignTypeName = "BIGSERIAL";
      this.nextSequenceQuery = "SELECT NEXTVAL(''{0}'')";
      this.useGetBytesForBlobs = true;
      this.useSetBytesForBlobs = true;
      this.useGetStringForClobs = true;
      this.useSetStringForClobs = true;
      this.bitTypeName = "BOOL";
      this.smallintTypeName = "SMALLINT";
      this.realTypeName = "FLOAT8";
      this.tinyintTypeName = "SMALLINT";
      this.binaryTypeName = "BYTEA";
      this.blobTypeName = "BYTEA";
      this.longVarbinaryTypeName = "BYTEA";
      this.varbinaryTypeName = "BYTEA";
      this.clobTypeName = "TEXT";
      this.longVarcharTypeName = "TEXT";
      this.doubleTypeName = "DOUBLE PRECISION";
      this.varcharTypeName = "VARCHAR{0}";
      this.timestampTypeName = "ABSTIME";
      this.fixedSizeTypeNameSet.addAll(Arrays.asList("BOOL", "BYTEA", "NAME", "INT8", "INT2", "INT2VECTOR", "INT4", "REGPROC", "TEXT", "OID", "TID", "XID", "CID", "OIDVECTOR", "SET", "FLOAT4", "FLOAT8", "ABSTIME", "RELTIME", "TINTERVAL", "MONEY"));
      this.supportsLockingWithDistinctClause = false;
      this.supportsLockingWithOuterJoin = false;
      this.supportsNullTableForGetImportedKeys = true;
      this.reservedWordSet.addAll(Arrays.asList("ABORT", "ACL", "AGGREGATE", "APPEND", "ARCHIVE", "ARCH_STORE", "BACKWARD", "BINARY", "CHANGE", "CLUSTER", "COPY", "DATABASE", "DELIMITER", "DELIMITERS", "DO", "EXPLAIN", "EXTEND", "FORWARD", "HEAVY", "INDEX", "INHERITS", "ISNULL", "LIGHT", "LISTEN", "LOAD", "MERGE", "NOTHING", "NOTIFY", "NOTNULL", "OID", "OIDS", "PURGE", "RECIPE", "RENAME", "REPLACE", "RETRIEVE", "RETURNS", "RULE", "SETOF", "STDIN", "STDOUT", "STORE", "VACUUM", "VERBOSE", "VERSION"));
   }

   public Date getDate(ResultSet rs, int column) throws SQLException {
      try {
         return super.getDate(rs, column);
      } catch (StringIndexOutOfBoundsException var8) {
         String dateStr = rs.getString(column);
         SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS");

         try {
            return fmt.parse(dateStr);
         } catch (ParseException var7) {
            throw new SQLException(var7.toString());
         }
      }
   }

   public byte getByte(ResultSet rs, int column) throws SQLException {
      try {
         return super.getByte(rs, column);
      } catch (SQLException var4) {
         return super.getBigDecimal(rs, column).byteValue();
      }
   }

   public short getShort(ResultSet rs, int column) throws SQLException {
      try {
         return super.getShort(rs, column);
      } catch (SQLException var4) {
         return super.getBigDecimal(rs, column).shortValue();
      }
   }

   public int getInt(ResultSet rs, int column) throws SQLException {
      try {
         return super.getInt(rs, column);
      } catch (SQLException var4) {
         return super.getBigDecimal(rs, column).intValue();
      }
   }

   public long getLong(ResultSet rs, int column) throws SQLException {
      try {
         return super.getLong(rs, column);
      } catch (SQLException var4) {
         return super.getBigDecimal(rs, column).longValue();
      }
   }

   public void setBoolean(PreparedStatement stmnt, int idx, boolean val, Column col) throws SQLException {
      stmnt.setBoolean(idx, val);
   }

   public void setNull(PreparedStatement stmnt, int idx, int colType, Column col) throws SQLException {
      if (colType == 2004) {
         colType = -2;
      }

      stmnt.setNull(idx, colType);
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

   public String[] getCreateSequenceSQL(Sequence seq) {
      String[] sql = super.getCreateSequenceSQL(seq);
      if (seq.getAllocate() > 1) {
         sql[0] = sql[0] + " CACHE " + seq.getAllocate();
      }

      return sql;
   }

   protected boolean supportsDeferredUniqueConstraints() {
      return false;
   }

   protected String getSequencesSQL(String schemaName, String sequenceName) {
      if (schemaName == null && sequenceName == null) {
         return this.allSequencesSQL;
      } else if (schemaName == null) {
         return this.namedSequencesFromAllSchemasSQL;
      } else {
         return sequenceName == null ? this.allSequencesFromOneSchemaSQL : this.namedSequenceFromOneSchemaSQL;
      }
   }

   public boolean isSystemSequence(String name, String schema, boolean targetSchema) {
      if (super.isSystemSequence(name, schema, targetSchema)) {
         return true;
      } else {
         int idx = name.indexOf(95);
         return idx != -1 && idx != name.length() - 4 && name.toUpperCase().endsWith("_SEQ");
      }
   }

   public boolean isSystemTable(String name, String schema, boolean targetSchema) {
      return super.isSystemTable(name, schema, targetSchema) || name != null && name.toLowerCase().startsWith("pg_");
   }

   public boolean isSystemIndex(String name, Table table) {
      return super.isSystemIndex(name, table) || name != null && name.toLowerCase().startsWith("pg_");
   }

   public Connection decorate(Connection conn) throws SQLException {
      return new PostgresConnection(super.decorate(conn), this);
   }

   public InputStream getLOBStream(JDBCStore store, ResultSet rs, int column) throws SQLException {
      DelegatingConnection conn = (DelegatingConnection)store.getConnection();
      conn.setAutoCommit(false);
      LargeObjectManager lom = ((PGConnection)conn.getInnermostDelegate()).getLargeObjectAPI();
      if (rs.getInt(column) != -1) {
         LargeObject lo = lom.open(rs.getInt(column));
         return lo.getInputStream();
      } else {
         return null;
      }
   }

   public void insertBlobForStreamingLoad(Row row, Column col, JDBCStore store, Object ob, Select sel) throws SQLException {
      if (row.getAction() == 1) {
         this.insertPostgresBlob(row, col, store, ob);
      } else if (row.getAction() == 0) {
         this.updatePostgresBlob(row, col, store, ob, sel);
      }

   }

   private void insertPostgresBlob(Row row, Column col, JDBCStore store, Object ob) throws SQLException {
      if (ob != null) {
         col.setType(4);
         DelegatingConnection conn = (DelegatingConnection)store.getConnection();

         try {
            conn.setAutoCommit(false);
            PGConnection pgconn = (PGConnection)conn.getInnermostDelegate();
            LargeObjectManager lom = pgconn.getLargeObjectAPI();
            int oid = lom.create();
            LargeObject lo = lom.open(oid, 131072);
            OutputStream os = lo.getOutputStream();
            this.copy((InputStream)ob, os);
            lo.close();
            row.setInt(col, oid);
         } catch (IOException var14) {
            throw new StoreException(var14);
         } finally {
            conn.close();
         }
      } else {
         row.setInt(col, -1);
      }

   }

   private void updatePostgresBlob(Row row, Column col, JDBCStore store, Object ob, Select sel) throws SQLException {
      SQLBuffer sql = sel.toSelect(true, store.getFetchConfiguration());
      ResultSet res = null;
      DelegatingConnection conn = (DelegatingConnection)store.getConnection();
      PreparedStatement stmnt = null;

      try {
         stmnt = sql.prepareStatement(conn, store.getFetchConfiguration(), 1005, 1008);
         res = stmnt.executeQuery();
         if (!res.next()) {
            throw new InternalException(_loc.get("stream-exception"));
         }

         int oid = res.getInt(1);
         PGConnection pgconn;
         LargeObjectManager lom;
         LargeObject lo;
         OutputStream os;
         if (oid != -1) {
            conn.setAutoCommit(false);
            pgconn = (PGConnection)conn.getInnermostDelegate();
            lom = pgconn.getLargeObjectAPI();
            if (ob != null) {
               lo = lom.open(oid, 131072);
               os = lo.getOutputStream();
               this.copy((InputStream)ob, os);
               lo.close();
            } else {
               lom.delete(oid);
               row.setInt(col, -1);
            }
         } else if (ob != null) {
            conn.setAutoCommit(false);
            pgconn = (PGConnection)conn.getInnermostDelegate();
            lom = pgconn.getLargeObjectAPI();
            oid = lom.create();
            lo = lom.open(oid, 131072);
            os = lo.getOutputStream();
            this.copy((InputStream)ob, os);
            lo.close();
            row.setInt(col, oid);
         }
      } catch (IOException var28) {
         throw new StoreException(var28);
      } finally {
         if (res != null) {
            try {
               res.close();
            } catch (SQLException var27) {
            }
         }

         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var26) {
            }
         }

         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var25) {
            }
         }

      }

   }

   public void updateBlob(Select sel, JDBCStore store, InputStream is) throws SQLException {
   }

   public void deleteStream(JDBCStore store, Select sel) throws SQLException {
      SQLBuffer sql = sel.toSelect(true, store.getFetchConfiguration());
      ResultSet res = null;
      DelegatingConnection conn = (DelegatingConnection)store.getConnection();
      PreparedStatement stmnt = null;

      try {
         stmnt = sql.prepareStatement(conn, store.getFetchConfiguration(), 1005, 1008);
         res = stmnt.executeQuery();
         if (!res.next()) {
            throw new InternalException(_loc.get("stream-exception"));
         }

         int oid = res.getInt(1);
         if (oid != -1) {
            conn.setAutoCommit(false);
            PGConnection pgconn = (PGConnection)conn.getInnermostDelegate();
            LargeObjectManager lom = pgconn.getLargeObjectAPI();
            lom.delete(oid);
         }
      } finally {
         if (res != null) {
            try {
               res.close();
            } catch (SQLException var21) {
            }
         }

         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var20) {
            }
         }

         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var19) {
            }
         }

      }

   }

   public String getPlaceholderValueString(Column col) {
      return col.getType() == -7 ? "false" : super.getPlaceholderValueString(col);
   }

   private static class PostgresPreparedStatement extends DelegatingPreparedStatement {
      private final PostgresDictionary _dict;

      public PostgresPreparedStatement(PreparedStatement ps, Connection conn, PostgresDictionary dict) {
         super(ps, conn);
         this._dict = dict;
      }

      protected ResultSet executeQuery(boolean wrap) throws SQLException {
         try {
            return super.executeQuery(wrap);
         } catch (SQLException var4) {
            ResultSet rs = this.getResultSet(wrap);
            if (rs == null) {
               throw var4;
            } else {
               return rs;
            }
         }
      }

      public void setFetchSize(int i) throws SQLException {
         try {
            if (this._dict.supportsSetFetchSize) {
               super.setFetchSize(i);
            }
         } catch (SQLException var3) {
            this._dict.supportsSetFetchSize = false;
            if (this._dict.log.isWarnEnabled()) {
               this._dict.log.warn(PostgresDictionary._loc.get("psql-no-set-fetch-size"), var3);
            }
         }

      }
   }

   private static class PostgresConnection extends DelegatingConnection {
      private final PostgresDictionary _dict;

      public PostgresConnection(Connection conn, PostgresDictionary dict) {
         super(conn);
         this._dict = dict;
      }

      protected PreparedStatement prepareStatement(String sql, boolean wrap) throws SQLException {
         return new PostgresPreparedStatement(super.prepareStatement(sql, false), this, this._dict);
      }

      protected PreparedStatement prepareStatement(String sql, int rsType, int rsConcur, boolean wrap) throws SQLException {
         return new PostgresPreparedStatement(super.prepareStatement(sql, rsType, rsConcur, false), this, this._dict);
      }
   }
}
