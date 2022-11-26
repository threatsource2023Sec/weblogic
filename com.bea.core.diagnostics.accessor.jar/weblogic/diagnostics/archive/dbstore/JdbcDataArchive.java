package weblogic.diagnostics.archive.dbstore;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.accessor.DiagnosticDataAccessException;
import weblogic.diagnostics.accessor.runtime.DataRetirementTaskRuntimeMBean;
import weblogic.diagnostics.archive.DataRetirementTaskImpl;
import weblogic.diagnostics.archive.EditableDataArchive;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.query.QueryException;
import weblogic.management.ManagementException;

public abstract class JdbcDataArchive extends EditableDataArchive {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private static final long INTERVAL_CHUNK_SIZE = 300000L;
   private String jndiName;
   private String schemaName;
   private String tableName;
   private String url;
   private String userName;
   private String password;
   private DataSource dataSource;
   private long insertionCount;
   private long insertionTime;
   private long deletionCount;
   private long deletionTime;
   protected String insert_sql;
   protected String select_sql;

   abstract DataRecord getDataRecord(ResultSet var1) throws SQLException;

   protected abstract void insertDataRecord(PreparedStatement var1, Object var2) throws SQLException;

   public JdbcDataArchive(String name, String jndiName, String schemaName, String tableName, ColumnInfo[] columns) throws NamingException, ManagementException, SQLException {
      this(name, jndiName, schemaName, tableName, columns, (String)null, (String)null, (String)null);
   }

   public JdbcDataArchive(String name, String jndiName, String configuredSchema, String tableName, ColumnInfo[] columns, String url, String userName, String password) throws NamingException, ManagementException, SQLException {
      super(name, columns, false);
      this.jndiName = jndiName;
      this.tableName = tableName;
      this.url = url;
      this.userName = userName;
      this.password = password;
      this.dataSource = this.getDataSource();
      this.initSchemaName(configuredSchema);
      this.select_sql = this.getSelectSQL(tableName, columns);
      this.insert_sql = this.getInsertSQL(tableName, columns);
      this.testColumns(columns);
      this.registerRuntimeMBean();
   }

   private void testColumns(ColumnInfo[] columns) throws SQLException {
      Connection conn = null;
      ResultSet rs = null;

      try {
         conn = this.dataSource.getConnection();
         DatabaseMetaData metaData = conn.getMetaData();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("schema name is " + this.schemaName);
         }

         rs = metaData.getTables((String)null, this.schemaName, this.tableName, (String[])null);
         switch (this.getMatchCount(rs)) {
            case 0:
               throw new SQLException("Required table " + this.tableName + " not found");
            case 1:
               rs.close();
               rs = null;
               int size = columns != null ? columns.length : 0;
               int i = 0;

               while(i < size) {
                  ColumnInfo col = columns[i];
                  String colName = col.getColumnName();
                  rs = metaData.getColumns((String)null, this.schemaName, this.tableName, colName);
                  switch (this.getMatchCount(rs)) {
                     case 0:
                        throw new SQLException("Column " + colName + " in table " + this.tableName + " not found");
                     case 1:
                        rs.close();
                        rs = null;
                        ++i;
                        break;
                     default:
                        throw new SQLException("Duplicate columns " + colName + " in table " + this.tableName + " found");
                  }
               }
               break;
            default:
               throw new SQLException("Duplicate tables " + this.tableName + " found");
         }
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (Exception var17) {
            }
         }

         if (conn != null) {
            try {
               conn.close();
            } catch (Exception var16) {
            }
         }

      }

   }

   private int getMatchCount(ResultSet rs) throws SQLException {
      int count;
      for(count = 0; rs.next(); ++count) {
      }

      return count;
   }

   private DataSource getDataSource() throws NamingException {
      Context ctx = null;
      DataSource ds = null;

      DataSource var4;
      try {
         Hashtable ht = new Hashtable();
         ht.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
         if (this.url != null) {
            ht.put("java.naming.provider.url", this.url);
         }

         if (this.userName != null) {
            ht.put("java.naming.security.principal", this.userName);
         }

         if (this.password != null) {
            ht.put("java.naming.security.credentials", this.password);
         }

         ctx = new InitialContext(ht);
         ds = (DataSource)ctx.lookup(this.jndiName);
         var4 = ds;
      } finally {
         if (ctx != null) {
            ctx.close();
         }

      }

      return var4;
   }

   Connection getConnection() throws SQLException {
      Connection conn = null;

      try {
         conn = this.dataSource.getConnection();
      } catch (SQLException var5) {
         try {
            this.dataSource = this.getDataSource();
            conn = this.dataSource.getConnection();
         } catch (NamingException var4) {
            throw var5;
         }
      }

      return conn;
   }

   protected String getSelectSQL(String tableName, ColumnInfo[] columns) {
      String qualifiedTableName = this.getQualifiedTableName(tableName);
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Using qualified table name " + qualifiedTableName + " for SELECT statement");
      }

      StringBuffer buf = new StringBuffer();
      buf.append("SELECT ");

      for(int i = 0; i < columns.length; ++i) {
         if (i > 0) {
            buf.append(", ");
         }

         buf.append(columns[i].getColumnName());
      }

      buf.append(" FROM ");
      buf.append(qualifiedTableName);
      buf.append(" ");
      return new String(buf);
   }

   protected String getInsertSQL(String tableName, ColumnInfo[] columns) {
      StringBuffer buf = new StringBuffer();
      buf.append("INSERT INTO ");
      buf.append(this.getQualifiedTableName(tableName));
      buf.append(" (");
      int startIndex = 1;

      int i;
      for(i = startIndex; i < columns.length; ++i) {
         if (i > startIndex) {
            buf.append(", ");
         }

         buf.append(columns[i].getColumnName());
      }

      buf.append(") VALUES (");

      for(i = startIndex; i < columns.length; ++i) {
         if (i > startIndex) {
            buf.append(", ");
         }

         buf.append("?");
      }

      buf.append(") ");
      return new String(buf);
   }

   protected Iterator getResults(String sql) throws DiagnosticDataAccessException {
      try {
         return new JdbcRecordIterator(this, sql);
      } catch (SQLException var3) {
         throw new DiagnosticDataAccessException("Error executing query: " + sql, var3);
      }
   }

   private void insertDataRecords(Collection data) throws DiagnosticDataAccessException {
      Connection conn = null;
      PreparedStatement pStmt = null;
      SQLException exception = null;
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("insertDataRecords: " + this.insert_sql);
      }

      long t0 = this.elapsedTimer.timestamp();
      int count = 0;
      DataRecord dataRecord = null;

      try {
         conn = this.getConnection();
         pStmt = conn.prepareStatement(this.insert_sql);

         for(Iterator dataIterator = data.iterator(); dataIterator.hasNext(); ++count) {
            dataRecord = (DataRecord)dataIterator.next();
            this.insertDataRecord(pStmt, dataRecord);
         }
      } catch (SQLException var25) {
         exception = var25;
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("insertDataRecords: encountered exception on dataRecord=" + dataRecord);
         }
      } finally {
         if (pStmt != null) {
            try {
               pStmt.close();
            } catch (SQLException var24) {
               if (exception == null) {
                  exception = var24;
               }
            }
         }

         if (conn != null) {
            try {
               if (!conn.getAutoCommit()) {
                  conn.commit();
               }

               conn.close();
            } catch (SQLException var23) {
               if (exception == null) {
                  exception = var23;
               }
            }
         }

      }

      long delta = this.elapsedTimer.timestamp() - t0;
      synchronized(this) {
         this.insertionCount += (long)count;
         this.insertionTime += delta;
      }

      if (exception != null) {
         throw new DiagnosticDataAccessException("Error executing query: " + this.insert_sql, exception);
      }
   }

   public void writeData(Collection data) throws IOException {
      try {
         this.insertDataRecords(data);
      } catch (DiagnosticDataAccessException var4) {
         IOException ioe = new IOException(var4.getMessage());
         ioe.initCause(var4);
         throw ioe;
      }
   }

   private Iterator getDataRecords(String select_sql, String[] queryClauses) throws QueryException, DiagnosticDataAccessException {
      StringBuffer buf = new StringBuffer();
      boolean hasWhere = false;
      buf.append(select_sql);

      for(int i = 0; i < queryClauses.length; ++i) {
         String clause = queryClauses[i];
         if (clause != null && clause.trim().length() > 0) {
            buf.append(hasWhere ? "AND" : "WHERE").append(" (").append(clause).append(") ");
            hasWhere = true;
         }
      }

      buf.append(" ORDER BY RECORDID");
      return this.getResults(buf.toString());
   }

   public Iterator getDataRecords(String query) throws QueryException, DiagnosticDataAccessException {
      String[] clauses = new String[]{query};
      return this.getDataRecords(this.select_sql, clauses);
   }

   public Iterator getDataRecords(long startTime, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      String filter = "TIMESTAMP >= " + startTime + " AND TIMESTAMP < " + endTime;
      String[] clauses = new String[]{filter, query};
      return this.getDataRecords(this.select_sql, clauses);
   }

   public Iterator getDataRecords(long startRecordId, long endrecordId, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      String filter = "RECORDID >= " + startRecordId + " AND RECORDID < " + endrecordId + " AND TIMESTAMP < " + endTime;
      String[] clauses = new String[]{filter, query};
      return this.getDataRecords(this.select_sql, clauses);
   }

   public int deleteDataRecords(long startTime, long endTime, String queryString, DataRetirementTaskRuntimeMBean task) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      long t0 = this.elapsedTimer.timestamp();
      int count = 0;
      long increment = endTime - startTime;
      DataRetirementTaskImpl taskImpl = null;
      if (task instanceof DataRetirementTaskImpl) {
         long earliest = this.getEarliestAvailableTimestamp();
         if (earliest < 0L) {
            return count;
         }

         if (startTime < earliest) {
            startTime = earliest;
         }

         increment = 300000L;
         taskImpl = (DataRetirementTaskImpl)task;
      }

      if (queryString != null && queryString.trim().length() > 0) {
         queryString = queryString.trim();
      } else {
         queryString = null;
      }

      Connection conn = null;
      Statement stmt = null;

      long _start;
      try {
         conn = this.getConnection();
         stmt = conn.createStatement();

         for(_start = startTime; _start < endTime && (task == null || task.isRunning()); _start += increment) {
            long _end = _start + increment;
            if (_end > endTime) {
               _end = endTime;
            }

            String criterion = " (TIMESTAMP >= " + _start + " AND TIMESTAMP < " + _end + " ) ";
            if (queryString != null) {
               criterion = criterion + " AND ( " + queryString + ")";
            }

            String sql = "DELETE FROM " + this.getQualifiedTableName(this.tableName) + " WHERE " + criterion;
            if (DEBUG.isDebugEnabled()) {
               this.debugSql(sql);
            }

            count += stmt.executeUpdate(sql);
         }
      } catch (SQLException var34) {
         throw new DiagnosticDataAccessException(var34);
      } finally {
         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException var32) {
            }
         }

         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var31) {
            }
         }

      }

      if (taskImpl != null) {
         taskImpl.incrementRetiredRecordsCount((long)count);
      }

      _start = this.elapsedTimer.timestamp() - t0;
      synchronized(this) {
         this.deletionCount += (long)count;
         this.deletionTime += _start;
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Deleted " + count + " records");
      }

      return count;
   }

   public long getInsertionCount() {
      return this.insertionCount;
   }

   public long getInsertionTime() {
      return this.insertionTime;
   }

   public long getDeletionCount() {
      return this.deletionCount;
   }

   public long getDeletionTime() {
      return this.deletionTime;
   }

   public void close() throws DiagnosticDataAccessException, ManagementException {
      if (this.archiveRuntime != null) {
         this.unregisterRuntimeMBean();
      }

      this.isClosed = true;
   }

   public long getLatestKnownRecordID() throws DiagnosticDataAccessException, UnsupportedOperationException {
      long lastId = 0L;

      try {
         lastId = this.getLongValue("MAX(RECORDID)");
      } catch (SQLException var4) {
         throw new DiagnosticDataAccessException(var4);
      }

      if (lastId < 0L) {
         lastId = 0L;
      }

      return lastId;
   }

   public long getEarliestAvailableTimestamp() {
      long retVal = -1L;

      try {
         retVal = this.getLongValue("MIN(TIMESTAMP)");
      } catch (SQLException var4) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Exception: " + var4);
         }
      }

      if (retVal <= 0L) {
         retVal = -1L;
      }

      return retVal;
   }

   public long getLatestAvailableTimestamp() {
      return System.currentTimeMillis();
   }

   private long getLongValue(String attribute) throws SQLException {
      String sql = "SELECT " + attribute + " from " + this.getQualifiedTableName(this.tableName);
      if (DEBUG.isDebugEnabled()) {
         this.debugSql(sql);
      }

      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;
      long retVal = 0L;

      try {
         conn = this.getConnection();
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         if (rs.next()) {
            retVal = rs.getLong(1);
         }
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException var20) {
            }
         }

         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException var19) {
            }
         }

         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var18) {
            }
         }

      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Returned: " + retVal);
      }

      return retVal;
   }

   private String getQualifiedTableName(String tableName) {
      StringBuffer buf = new StringBuffer();
      if (this.schemaName != null) {
         buf.append(this.schemaName);
         buf.append(".");
      }

      buf.append(tableName);
      return buf.toString();
   }

   private void debugSql(String sql) {
      DEBUG.debug("Executing: " + sql);
   }

   private void initSchemaName(String configuredSchema) {
      try {
         DatabaseMetaData metaData = this.getConnection().getMetaData();
         if (configuredSchema != null && !configuredSchema.trim().isEmpty()) {
            this.schemaName = configuredSchema.trim();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Database identifier case handling: [storesLowerCaseIdentifiers=" + metaData.storesLowerCaseIdentifiers() + ", storesLowerCaseQuotedIdentifiers=" + metaData.storesLowerCaseQuotedIdentifiers() + ", storesMixedCaseIdentifiers=" + metaData.storesMixedCaseIdentifiers() + ", storesMixedCaseQuotedIdentifiers=" + metaData.storesMixedCaseQuotedIdentifiers() + ", storesUpperCaseIdentifiers=" + metaData.storesUpperCaseIdentifiers() + ", storesUpperCaseQuotedIdentifiers=" + metaData.storesUpperCaseQuotedIdentifiers() + "]");
            }

            if (metaData.storesUpperCaseIdentifiers()) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Converting schema to upper case");
               }

               this.schemaName = this.schemaName.toUpperCase().trim();
            }
         }
      } catch (SQLException var3) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Caught exception obtaining user for connection", var3);
         }
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Schema for connection: " + this.schemaName);
      }

   }
}
