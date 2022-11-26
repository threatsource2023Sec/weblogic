package org.apache.openjpa.lib.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.BatchUpdateException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.apache.openjpa.lib.util.J2DoPrivHelper;

public class LoggingConnectionDecorator implements ConnectionDecorator {
   private static final String SEP = J2DoPrivHelper.getLineSeparator();
   private static final int WARN_IGNORE = 0;
   private static final int WARN_LOG_TRACE = 1;
   private static final int WARN_LOG_INFO = 2;
   private static final int WARN_LOG_WARN = 3;
   private static final int WARN_LOG_ERROR = 4;
   private static final int WARN_THROW = 5;
   private static final int WARN_HANDLE = 6;
   private static final String[] WARNING_ACTIONS = new String[7];
   private final DataSourceLogs _logs = new DataSourceLogs();
   private SQLFormatter _formatter;
   private boolean _prettyPrint;
   private int _prettyPrintLineLength = 60;
   private int _warningAction = 0;
   private SQLWarningHandler _warningHandler;
   private boolean _trackParameters = true;

   public void setPrettyPrint(boolean prettyPrint) {
      this._prettyPrint = prettyPrint;
      if (this._formatter == null && this._prettyPrint) {
         this._formatter = new SQLFormatter();
         this._formatter.setLineLength(this._prettyPrintLineLength);
      } else if (!this._prettyPrint) {
         this._formatter = null;
      }

   }

   public boolean getPrettyPrint() {
      return this._prettyPrint;
   }

   public void setPrettyPrintLineLength(int length) {
      this._prettyPrintLineLength = length;
      if (this._formatter != null) {
         this._formatter.setLineLength(length);
      }

   }

   public int getPrettyPrintLineLength() {
      return this._prettyPrintLineLength;
   }

   public void setTrackParameters(boolean trackParameters) {
      this._trackParameters = trackParameters;
   }

   public boolean getTrackParameters() {
      return this._trackParameters;
   }

   public void setWarningAction(String warningAction) {
      int index = Arrays.asList(WARNING_ACTIONS).indexOf(warningAction);
      if (index < 0) {
         index = 0;
      }

      this._warningAction = index;
   }

   public String getWarningAction() {
      return WARNING_ACTIONS[this._warningAction];
   }

   public void setWarningHandler(SQLWarningHandler warningHandler) {
      this._warningHandler = warningHandler;
   }

   public SQLWarningHandler getWarningHandler() {
      return this._warningHandler;
   }

   public DataSourceLogs getLogs() {
      return this._logs;
   }

   public Connection decorate(Connection conn) throws SQLException {
      return new LoggingConnection(conn);
   }

   private SQLException wrap(SQLException sqle, Statement stmnt) {
      return sqle instanceof ReportingSQLException ? (ReportingSQLException)sqle : new ReportingSQLException(sqle, stmnt);
   }

   private SQLException wrap(SQLException sqle, String sql) {
      return sqle instanceof ReportingSQLException ? (ReportingSQLException)sqle : new ReportingSQLException(sqle, sql);
   }

   // $FF: synthetic method
   static SQLWarningHandler access$300(LoggingConnectionDecorator x0) {
      return x0._warningHandler;
   }

   static {
      WARNING_ACTIONS[0] = "ignore";
      WARNING_ACTIONS[1] = "trace";
      WARNING_ACTIONS[2] = "info";
      WARNING_ACTIONS[3] = "warn";
      WARNING_ACTIONS[4] = "error";
      WARNING_ACTIONS[5] = "throw";
      WARNING_ACTIONS[6] = "handle";
   }

   private class LoggingConnection extends DelegatingConnection {
      public LoggingConnection(Connection conn) throws SQLException {
         super(conn);
      }

      protected PreparedStatement prepareStatement(String sql, boolean wrap) throws SQLException {
         try {
            PreparedStatement stmnt = super.prepareStatement(sql, false);
            return new LoggingPreparedStatement(stmnt, sql);
         } catch (SQLException var4) {
            throw LoggingConnectionDecorator.this.wrap(var4, sql);
         }
      }

      protected PreparedStatement prepareStatement(String sql, int rsType, int rsConcur, boolean wrap) throws SQLException {
         try {
            PreparedStatement stmnt = super.prepareStatement(sql, rsType, rsConcur, false);
            return new LoggingPreparedStatement(stmnt, sql);
         } catch (SQLException var6) {
            throw LoggingConnectionDecorator.this.wrap(var6, sql);
         }
      }

      protected Statement createStatement(boolean wrap) throws SQLException {
         Statement stmnt = super.createStatement(false);
         return new LoggingStatement(stmnt);
      }

      protected Statement createStatement(int type, int concurrency, boolean wrap) throws SQLException {
         Statement stmnt = super.createStatement(type, concurrency, false);
         return new LoggingStatement(stmnt);
      }

      public void commit() throws SQLException {
         long start = System.currentTimeMillis();

         try {
            super.commit();
         } finally {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("commit", start, this);
            }

            this.handleSQLWarning();
         }

      }

      public void rollback() throws SQLException {
         long start = System.currentTimeMillis();

         try {
            super.rollback();
         } finally {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("rollback", start, this);
            }

            this.handleSQLWarning();
         }

      }

      public void close() throws SQLException {
         long start = System.currentTimeMillis();

         try {
            super.close();
         } finally {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("close", start, this);
            }

         }

      }

      public Savepoint setSavepoint() throws SQLException {
         long start = System.currentTimeMillis();

         Savepoint var3;
         try {
            var3 = super.setSavepoint();
         } finally {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("savepoint", start, this);
            }

            this.handleSQLWarning();
         }

         return var3;
      }

      public Savepoint setSavepoint(String name) throws SQLException {
         long start = System.currentTimeMillis();

         Savepoint var4;
         try {
            var4 = super.setSavepoint(name);
         } finally {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("savepoint: " + name, start, this);
            }

            this.handleSQLWarning();
         }

         return var4;
      }

      public void rollback(Savepoint savepoint) throws SQLException {
         long start = System.currentTimeMillis();
         boolean var12 = false;

         try {
            var12 = true;
            super.rollback(savepoint);
            var12 = false;
         } finally {
            if (var12) {
               if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
                  String name = null;

                  try {
                     name = savepoint.getSavepointName();
                  } catch (SQLException var13) {
                     name = String.valueOf(savepoint.getSavepointId());
                  }

                  LoggingConnectionDecorator.this._logs.logJDBC("rollback: " + name, start, this);
               }

               this.handleSQLWarning();
            }
         }

         if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
            String namex = null;

            try {
               namex = savepoint.getSavepointName();
            } catch (SQLException var14) {
               namex = String.valueOf(savepoint.getSavepointId());
            }

            LoggingConnectionDecorator.this._logs.logJDBC("rollback: " + namex, start, this);
         }

         this.handleSQLWarning();
      }

      public void releaseSavepoint(Savepoint savepoint) throws SQLException {
         long start = System.currentTimeMillis();
         boolean var12 = false;

         try {
            var12 = true;
            super.releaseSavepoint(savepoint);
            var12 = false;
         } finally {
            if (var12) {
               if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
                  String name = null;

                  try {
                     name = savepoint.getSavepointName();
                  } catch (SQLException var13) {
                     name = String.valueOf(savepoint.getSavepointId());
                  }

                  LoggingConnectionDecorator.this._logs.logJDBC("release: " + name, start, this);
               }

               this.handleSQLWarning();
            }
         }

         if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
            String namex = null;

            try {
               namex = savepoint.getSavepointName();
            } catch (SQLException var14) {
               namex = String.valueOf(savepoint.getSavepointId());
            }

            LoggingConnectionDecorator.this._logs.logJDBC("release: " + namex, start, this);
         }

         this.handleSQLWarning();
      }

      protected Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability, boolean wrap) throws SQLException {
         Statement stmnt = super.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability, false);
         this.handleSQLWarning();
         return new LoggingStatement(stmnt);
      }

      protected PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability, boolean wrap) throws SQLException {
         try {
            PreparedStatement stmnt = super.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability, false);
            this.handleSQLWarning();
            return new LoggingPreparedStatement(stmnt, sql);
         } catch (SQLException var7) {
            throw LoggingConnectionDecorator.this.wrap(var7, sql);
         }
      }

      protected PreparedStatement prepareStatement(String sql, int autoGeneratedKeys, boolean wrap) throws SQLException {
         try {
            PreparedStatement stmnt = super.prepareStatement(sql, autoGeneratedKeys, false);
            this.handleSQLWarning();
            return new LoggingPreparedStatement(stmnt, sql);
         } catch (SQLException var5) {
            throw LoggingConnectionDecorator.this.wrap(var5, sql);
         }
      }

      protected PreparedStatement prepareStatement(String sql, int[] columnIndexes, boolean wrap) throws SQLException {
         try {
            PreparedStatement stmnt = super.prepareStatement(sql, columnIndexes, false);
            this.handleSQLWarning();
            return new LoggingPreparedStatement(stmnt, sql);
         } catch (SQLException var5) {
            throw LoggingConnectionDecorator.this.wrap(var5, sql);
         }
      }

      protected PreparedStatement prepareStatement(String sql, String[] columnNames, boolean wrap) throws SQLException {
         try {
            PreparedStatement stmnt = super.prepareStatement(sql, columnNames, false);
            this.handleSQLWarning();
            return new LoggingPreparedStatement(stmnt, sql);
         } catch (SQLException var5) {
            throw LoggingConnectionDecorator.this.wrap(var5, sql);
         }
      }

      protected DatabaseMetaData getMetaData(boolean wrap) throws SQLException {
         return new LoggingDatabaseMetaData(super.getMetaData(false));
      }

      private void logTime(long startTime) throws SQLException {
         if (LoggingConnectionDecorator.this._logs.isSQLEnabled()) {
            LoggingConnectionDecorator.this._logs.logSQL("spent", startTime, this);
         }

      }

      private void logSQL(Statement stmnt) throws SQLException {
         if (LoggingConnectionDecorator.this._logs.isSQLEnabled()) {
            LoggingConnectionDecorator.this._logs.logSQL("executing " + stmnt, this);
         }

      }

      private void logBatchSQL(Statement stmnt) throws SQLException {
         if (LoggingConnectionDecorator.this._logs.isSQLEnabled()) {
            LoggingConnectionDecorator.this._logs.logSQL("executing batch " + stmnt, this);
         }

      }

      private void handleSQLWarning() throws SQLException {
         if (LoggingConnectionDecorator.this._warningAction != 0) {
            try {
               this.handleSQLWarning(this.getWarnings());
            } finally {
               this.clearWarnings();
            }

         }
      }

      private void handleSQLWarning(Statement stmnt) throws SQLException {
         if (LoggingConnectionDecorator.this._warningAction != 0) {
            try {
               this.handleSQLWarning(stmnt.getWarnings());
            } finally {
               stmnt.clearWarnings();
            }

         }
      }

      private void handleSQLWarning(ResultSet rs) throws SQLException {
         if (LoggingConnectionDecorator.this._warningAction != 0) {
            try {
               this.handleSQLWarning(rs.getWarnings());
            } finally {
               rs.clearWarnings();
            }

         }
      }

      private void handleSQLWarning(SQLWarning var1) throws SQLException {
         // $FF: Couldn't be decompiled
      }

      private class LoggingResultSet extends DelegatingResultSet {
         public LoggingResultSet(ResultSet rs, Statement stmnt) {
            super(rs, stmnt);
         }

         public boolean next() throws SQLException {
            boolean var1;
            try {
               var1 = super.next();
            } finally {
               LoggingConnection.this.handleSQLWarning((ResultSet)this);
            }

            return var1;
         }

         public void close() throws SQLException {
            try {
               super.close();
            } finally {
               LoggingConnection.this.handleSQLWarning((ResultSet)this);
            }

         }

         public void beforeFirst() throws SQLException {
            try {
               super.beforeFirst();
            } finally {
               LoggingConnection.this.handleSQLWarning((ResultSet)this);
            }

         }

         public void afterLast() throws SQLException {
            try {
               super.afterLast();
            } finally {
               LoggingConnection.this.handleSQLWarning((ResultSet)this);
            }

         }

         public boolean first() throws SQLException {
            boolean var1;
            try {
               var1 = super.first();
            } finally {
               LoggingConnection.this.handleSQLWarning((ResultSet)this);
            }

            return var1;
         }

         public boolean last() throws SQLException {
            boolean var1;
            try {
               var1 = super.last();
            } finally {
               LoggingConnection.this.handleSQLWarning((ResultSet)this);
            }

            return var1;
         }

         public boolean absolute(int a) throws SQLException {
            boolean var2;
            try {
               var2 = super.absolute(a);
            } finally {
               LoggingConnection.this.handleSQLWarning((ResultSet)this);
            }

            return var2;
         }

         public boolean relative(int a) throws SQLException {
            boolean var2;
            try {
               var2 = super.relative(a);
            } finally {
               LoggingConnection.this.handleSQLWarning((ResultSet)this);
            }

            return var2;
         }

         public boolean previous() throws SQLException {
            boolean var1;
            try {
               var1 = super.previous();
            } finally {
               LoggingConnection.this.handleSQLWarning((ResultSet)this);
            }

            return var1;
         }
      }

      private class LoggingPreparedStatement extends DelegatingPreparedStatement {
         private final String _sql;
         private List _params = null;
         private List _paramBatch = null;

         public LoggingPreparedStatement(PreparedStatement stmnt, String sql) throws SQLException {
            super(stmnt, LoggingConnection.this);
            this._sql = sql;
         }

         protected ResultSet wrapResult(ResultSet rs, boolean wrap) {
            return (ResultSet)(wrap && rs != null ? LoggingConnection.this.new LoggingResultSet(rs, this) : super.wrapResult(rs, wrap));
         }

         protected ResultSet executeQuery(String sql, boolean wrap) throws SQLException {
            LoggingConnection.this.logSQL(this);
            long start = System.currentTimeMillis();

            ResultSet var5;
            try {
               var5 = super.executeQuery(sql, wrap);
            } catch (SQLException var9) {
               throw LoggingConnectionDecorator.this.wrap(var9, (Statement)this);
            } finally {
               LoggingConnection.this.logTime(start);
               this.clearLogParameters(true);
               LoggingConnection.this.handleSQLWarning((Statement)this);
            }

            return var5;
         }

         public int executeUpdate(String sql) throws SQLException {
            LoggingConnection.this.logSQL(this);
            long start = System.currentTimeMillis();

            int var4;
            try {
               var4 = super.executeUpdate(sql);
            } catch (SQLException var8) {
               throw LoggingConnectionDecorator.this.wrap(var8, (Statement)this);
            } finally {
               LoggingConnection.this.logTime(start);
               this.clearLogParameters(true);
               LoggingConnection.this.handleSQLWarning((Statement)this);
            }

            return var4;
         }

         public boolean execute(String sql) throws SQLException {
            LoggingConnection.this.logSQL(this);
            long start = System.currentTimeMillis();

            boolean var4;
            try {
               var4 = super.execute(sql);
            } catch (SQLException var8) {
               throw LoggingConnectionDecorator.this.wrap(var8, (Statement)this);
            } finally {
               LoggingConnection.this.logTime(start);
               this.clearLogParameters(true);
               LoggingConnection.this.handleSQLWarning((Statement)this);
            }

            return var4;
         }

         protected ResultSet executeQuery(boolean wrap) throws SQLException {
            LoggingConnection.this.logSQL(this);
            long start = System.currentTimeMillis();

            ResultSet var4;
            try {
               var4 = super.executeQuery(wrap);
            } catch (SQLException var8) {
               throw LoggingConnectionDecorator.this.wrap(var8, (Statement)this);
            } finally {
               LoggingConnection.this.logTime(start);
               this.clearLogParameters(true);
               LoggingConnection.this.handleSQLWarning((Statement)this);
            }

            return var4;
         }

         public int executeUpdate() throws SQLException {
            LoggingConnection.this.logSQL(this);
            long start = System.currentTimeMillis();

            int var3;
            try {
               var3 = super.executeUpdate();
            } catch (SQLException var7) {
               throw LoggingConnectionDecorator.this.wrap(var7, (Statement)this);
            } finally {
               LoggingConnection.this.logTime(start);
               this.clearLogParameters(true);
               LoggingConnection.this.handleSQLWarning((Statement)this);
            }

            return var3;
         }

         public int[] executeBatch() throws SQLException {
            LoggingConnection.this.logBatchSQL(this);
            long start = System.currentTimeMillis();

            int[] var3;
            try {
               var3 = super.executeBatch();
            } catch (SQLException var10) {
               if (var10 instanceof BatchUpdateException && this._paramBatch != null && this.shouldTrackParameters()) {
                  int[] count = ((BatchUpdateException)var10).getUpdateCounts();
                  if (count != null && count.length <= this._paramBatch.size()) {
                     int index = -1;

                     for(int i = 0; i < count.length; ++i) {
                        if (count[i] == -3) {
                           index = i;
                           break;
                        }
                     }

                     if (index == -1) {
                        index = count.length + 1;
                     }

                     if (index < this._paramBatch.size()) {
                        this._params = (List)this._paramBatch.get(index);
                     }
                  }
               }

               throw LoggingConnectionDecorator.this.wrap(var10, (Statement)this);
            } finally {
               LoggingConnection.this.logTime(start);
               this.clearLogParameters(true);
               LoggingConnection.this.handleSQLWarning((Statement)this);
            }

            return var3;
         }

         public boolean execute() throws SQLException {
            LoggingConnection.this.logSQL(this);
            long start = System.currentTimeMillis();

            boolean var3;
            try {
               var3 = super.execute();
            } catch (SQLException var7) {
               throw LoggingConnectionDecorator.this.wrap(var7, (Statement)this);
            } finally {
               LoggingConnection.this.logTime(start);
               this.clearLogParameters(true);
               LoggingConnection.this.handleSQLWarning((Statement)this);
            }

            return var3;
         }

         public void cancel() throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("cancel " + this + ": " + this._sql, LoggingConnection.this);
            }

            super.cancel();
         }

         public void setNull(int i1, int i2) throws SQLException {
            this.setLogParameter(i1, "null", (Object)null);
            super.setNull(i1, i2);
         }

         public void setBoolean(int i, boolean b) throws SQLException {
            this.setLogParameter(i, b);
            super.setBoolean(i, b);
         }

         public void setByte(int i, byte b) throws SQLException {
            this.setLogParameter(i, b);
            super.setByte(i, b);
         }

         public void setShort(int i, short s) throws SQLException {
            this.setLogParameter(i, s);
            super.setShort(i, s);
         }

         public void setInt(int i1, int i2) throws SQLException {
            this.setLogParameter(i1, i2);
            super.setInt(i1, i2);
         }

         public void setLong(int i, long l) throws SQLException {
            this.setLogParameter(i, l);
            super.setLong(i, l);
         }

         public void setFloat(int i, float f) throws SQLException {
            this.setLogParameter(i, f);
            super.setFloat(i, f);
         }

         public void setDouble(int i, double d) throws SQLException {
            this.setLogParameter(i, d);
            super.setDouble(i, d);
         }

         public void setBigDecimal(int i, BigDecimal bd) throws SQLException {
            this.setLogParameter(i, "BigDecimal", bd);
            super.setBigDecimal(i, bd);
         }

         public void setString(int i, String s) throws SQLException {
            this.setLogParameter(i, "String", s);
            super.setString(i, s);
         }

         public void setBytes(int i, byte[] b) throws SQLException {
            this.setLogParameter(i, "byte[]", b);
            super.setBytes(i, b);
         }

         public void setDate(int i, Date d) throws SQLException {
            this.setLogParameter(i, "Date", d);
            super.setDate(i, d);
         }

         public void setTime(int i, Time t) throws SQLException {
            this.setLogParameter(i, "Time", t);
            super.setTime(i, t);
         }

         public void setTimestamp(int i, Timestamp t) throws SQLException {
            this.setLogParameter(i, "Timestamp", t);
            super.setTimestamp(i, t);
         }

         public void setAsciiStream(int i1, InputStream is, int i2) throws SQLException {
            this.setLogParameter(i1, "InputStream", is);
            super.setAsciiStream(i1, is, i2);
         }

         public void setUnicodeStream(int i1, InputStream is, int i2) throws SQLException {
            this.setLogParameter(i1, "InputStream", is);
            super.setUnicodeStream(i2, is, i2);
         }

         public void setBinaryStream(int i1, InputStream is, int i2) throws SQLException {
            this.setLogParameter(i1, "InputStream", is);
            super.setBinaryStream(i1, is, i2);
         }

         public void clearParameters() throws SQLException {
            this.clearLogParameters(false);
            super.clearParameters();
         }

         public void setObject(int i1, Object o, int i2, int i3) throws SQLException {
            this.setLogParameter(i1, "Object", o);
            super.setObject(i1, o, i2, i3);
         }

         public void setObject(int i1, Object o, int i2) throws SQLException {
            this.setLogParameter(i1, "Object", o);
            super.setObject(i1, o, i2);
         }

         public void setObject(int i, Object o) throws SQLException {
            this.setLogParameter(i, "Object", o);
            super.setObject(i, o);
         }

         public void addBatch() throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isSQLEnabled()) {
               LoggingConnectionDecorator.this._logs.logSQL("batching " + this, LoggingConnection.this);
            }

            long start = System.currentTimeMillis();

            try {
               super.addBatch();
               if (this.shouldTrackParameters()) {
                  if (this._paramBatch == null) {
                     this._paramBatch = new ArrayList();
                  }

                  if (this._params != null) {
                     this._paramBatch.add(new ArrayList(this._params));
                  } else {
                     this._paramBatch.add((Object)null);
                  }
               }
            } finally {
               LoggingConnection.this.logTime(start);
            }

         }

         public void setCharacterStream(int i1, Reader r, int i2) throws SQLException {
            this.setLogParameter(i1, "Reader", r);
            super.setCharacterStream(i1, r, i2);
         }

         public void setRef(int i, Ref r) throws SQLException {
            this.setLogParameter(i, "Ref", r);
            super.setRef(i, r);
         }

         public void setBlob(int i, Blob b) throws SQLException {
            this.setLogParameter(i, "Blob", b);
            super.setBlob(i, b);
         }

         public void setClob(int i, Clob c) throws SQLException {
            this.setLogParameter(i, "Clob", c);
            super.setClob(i, c);
         }

         public void setArray(int i, Array a) throws SQLException {
            this.setLogParameter(i, "Array", a);
            super.setArray(i, a);
         }

         public ResultSetMetaData getMetaData() throws SQLException {
            return super.getMetaData();
         }

         public void setDate(int i, Date d, Calendar c) throws SQLException {
            this.setLogParameter(i, "Date", d);
            super.setDate(i, d, c);
         }

         public void setTime(int i, Time t, Calendar c) throws SQLException {
            this.setLogParameter(i, "Time", t);
            super.setTime(i, t, c);
         }

         public void setTimestamp(int i, Timestamp t, Calendar c) throws SQLException {
            this.setLogParameter(i, "Timestamp", t);
            super.setTimestamp(i, t, c);
         }

         public void setNull(int i1, int i2, String s) throws SQLException {
            this.setLogParameter(i1, "null", (Object)null);
            super.setNull(i1, i2, s);
         }

         protected void appendInfo(StringBuffer buf) {
            buf.append(" ");
            if (LoggingConnectionDecorator.this._formatter != null) {
               buf.append(LoggingConnectionDecorator.SEP);
               buf.append(LoggingConnectionDecorator.this._formatter.prettyPrint(this._sql));
               buf.append(LoggingConnectionDecorator.SEP);
            } else {
               buf.append(this._sql);
            }

            StringBuffer paramBuf = null;
            if (this._params != null && !this._params.isEmpty()) {
               paramBuf = new StringBuffer();
               Iterator itr = this._params.iterator();

               while(itr.hasNext()) {
                  paramBuf.append(itr.next());
                  if (itr.hasNext()) {
                     paramBuf.append(", ");
                  }
               }
            }

            if (paramBuf != null) {
               if (!LoggingConnectionDecorator.this._prettyPrint) {
                  buf.append(" ");
               }

               buf.append("[params=").append(paramBuf.toString()).append("]");
            }

            super.appendInfo(buf);
         }

         private void clearLogParameters(boolean batch) {
            if (this._params != null) {
               this._params.clear();
            }

            if (batch && this._paramBatch != null) {
               this._paramBatch.clear();
            }

         }

         private boolean shouldTrackParameters() {
            return LoggingConnectionDecorator.this._trackParameters || LoggingConnectionDecorator.this._logs.isSQLEnabled();
         }

         private void setLogParameter(int index, boolean val) {
            if (this.shouldTrackParameters()) {
               this.setLogParameter(index, "(boolean) " + val);
            }

         }

         private void setLogParameter(int index, byte val) {
            if (this.shouldTrackParameters()) {
               this.setLogParameter(index, "(byte) " + val);
            }

         }

         private void setLogParameter(int index, double val) {
            if (this.shouldTrackParameters()) {
               this.setLogParameter(index, "(double) " + val);
            }

         }

         private void setLogParameter(int index, float val) {
            if (this.shouldTrackParameters()) {
               this.setLogParameter(index, "(float) " + val);
            }

         }

         private void setLogParameter(int index, int val) {
            if (this.shouldTrackParameters()) {
               this.setLogParameter(index, "(int) " + val);
            }

         }

         private void setLogParameter(int index, long val) {
            if (this.shouldTrackParameters()) {
               this.setLogParameter(index, "(long) " + val);
            }

         }

         private void setLogParameter(int index, short val) {
            if (this.shouldTrackParameters()) {
               this.setLogParameter(index, "(short) " + val);
            }

         }

         private void setLogParameter(int index, String type, Object val) {
            if (this.shouldTrackParameters()) {
               this.setLogParameter(index, "(" + type + ") " + val);
            }

         }

         private void setLogParameter(int index, String val) {
            if (this._params == null) {
               this._params = new ArrayList();
            }

            while(this._params.size() < index) {
               this._params.add((Object)null);
            }

            if (val.length() > 80) {
               val = val.substring(0, 77) + "...";
            }

            this._params.set(index - 1, val);
         }
      }

      private class LoggingStatement extends DelegatingStatement {
         private String _sql = null;

         public LoggingStatement(Statement stmnt) throws SQLException {
            super(stmnt, LoggingConnection.this);
         }

         public void appendInfo(StringBuffer buf) {
            if (this._sql != null) {
               buf.append(" ");
               if (LoggingConnectionDecorator.this._formatter != null) {
                  buf.append(LoggingConnectionDecorator.SEP);
                  buf.append(LoggingConnectionDecorator.this._formatter.prettyPrint(this._sql));
               } else {
                  buf.append(this._sql);
               }
            }

         }

         protected ResultSet wrapResult(ResultSet rs, boolean wrap) {
            return (ResultSet)(wrap && rs != null ? LoggingConnection.this.new LoggingResultSet(rs, this) : super.wrapResult(rs, wrap));
         }

         public void cancel() throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("cancel " + this, LoggingConnection.this);
            }

            super.cancel();
         }

         protected ResultSet executeQuery(String sql, boolean wrap) throws SQLException {
            this._sql = sql;
            LoggingConnection.this.logSQL(this);
            long start = System.currentTimeMillis();

            ResultSet var5;
            try {
               var5 = super.executeQuery(sql, wrap);
            } catch (SQLException var9) {
               throw LoggingConnectionDecorator.this.wrap(var9, (Statement)this);
            } finally {
               LoggingConnection.this.logTime(start);
               LoggingConnection.this.handleSQLWarning((Statement)this);
            }

            return var5;
         }

         public int executeUpdate(String sql) throws SQLException {
            this._sql = sql;
            LoggingConnection.this.logSQL(this);
            long start = System.currentTimeMillis();

            int var4;
            try {
               var4 = super.executeUpdate(sql);
            } catch (SQLException var8) {
               throw LoggingConnectionDecorator.this.wrap(var8, (Statement)this);
            } finally {
               LoggingConnection.this.logTime(start);
               LoggingConnection.this.handleSQLWarning((Statement)this);
            }

            return var4;
         }

         public boolean execute(String sql) throws SQLException {
            this._sql = sql;
            LoggingConnection.this.logSQL(this);
            long start = System.currentTimeMillis();

            boolean var4;
            try {
               var4 = super.execute(sql);
            } catch (SQLException var8) {
               throw LoggingConnectionDecorator.this.wrap(var8, (Statement)this);
            } finally {
               LoggingConnection.this.logTime(start);
               LoggingConnection.this.handleSQLWarning((Statement)this);
            }

            return var4;
         }
      }

      private class LoggingDatabaseMetaData extends DelegatingDatabaseMetaData {
         public LoggingDatabaseMetaData(DatabaseMetaData meta) {
            super(meta, LoggingConnection.this);
         }

         public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getBestRowIdentifier: " + catalog + ", " + schema + ", " + table, LoggingConnection.this);
            }

            return super.getBestRowIdentifier(catalog, schema, table, scope, nullable);
         }

         public ResultSet getCatalogs() throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getCatalogs", LoggingConnection.this);
            }

            return super.getCatalogs();
         }

         public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getColumnPrivileges: " + catalog + ", " + schema + ", " + table, LoggingConnection.this);
            }

            return super.getColumnPrivileges(catalog, schema, table, columnNamePattern);
         }

         public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getColumns: " + catalog + ", " + schemaPattern + ", " + tableNamePattern + ", " + columnNamePattern, LoggingConnection.this);
            }

            return super.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
         }

         public ResultSet getCrossReference(String primaryCatalog, String primarySchema, String primaryTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getCrossReference: " + primaryCatalog + ", " + primarySchema + ", " + primaryTable + ", " + foreignCatalog + ", " + foreignSchema + ", " + foreignSchema, LoggingConnection.this);
            }

            return super.getCrossReference(primaryCatalog, primarySchema, primaryTable, foreignCatalog, foreignSchema, foreignTable);
         }

         public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getExportedKeys: " + catalog + ", " + schema + ", " + table, LoggingConnection.this);
            }

            return super.getExportedKeys(catalog, schema, table);
         }

         public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getImportedKeys: " + catalog + ", " + schema + ", " + table, LoggingConnection.this);
            }

            return super.getImportedKeys(catalog, schema, table);
         }

         public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getIndexInfo: " + catalog + ", " + schema + ", " + table, LoggingConnection.this);
            }

            return super.getIndexInfo(catalog, schema, table, unique, approximate);
         }

         public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getPrimaryKeys: " + catalog + ", " + schema + ", " + table, LoggingConnection.this);
            }

            return super.getPrimaryKeys(catalog, schema, table);
         }

         public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getProcedureColumns: " + catalog + ", " + schemaPattern + ", " + procedureNamePattern + ", " + columnNamePattern, LoggingConnection.this);
            }

            return super.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern);
         }

         public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getProcedures: " + catalog + ", " + schemaPattern + ", " + procedureNamePattern, LoggingConnection.this);
            }

            return super.getProcedures(catalog, schemaPattern, procedureNamePattern);
         }

         public ResultSet getSchemas() throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getSchemas", LoggingConnection.this);
            }

            return super.getSchemas();
         }

         public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getTablePrivileges", LoggingConnection.this);
            }

            return super.getTablePrivileges(catalog, schemaPattern, tableNamePattern);
         }

         public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getTables: " + catalog + ", " + schemaPattern + ", " + tableNamePattern, LoggingConnection.this);
            }

            return super.getTables(catalog, schemaPattern, tableNamePattern, types);
         }

         public ResultSet getTableTypes() throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getTableTypes", LoggingConnection.this);
            }

            return super.getTableTypes();
         }

         public ResultSet getTypeInfo() throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getTypeInfo", LoggingConnection.this);
            }

            return super.getTypeInfo();
         }

         public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getUDTs", LoggingConnection.this);
            }

            return super.getUDTs(catalog, schemaPattern, typeNamePattern, types);
         }

         public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
            if (LoggingConnectionDecorator.this._logs.isJDBCEnabled()) {
               LoggingConnectionDecorator.this._logs.logJDBC("getVersionColumns: " + catalog + ", " + schema + ", " + table, LoggingConnection.this);
            }

            return super.getVersionColumns(catalog, schema, table);
         }
      }
   }

   public interface SQLWarningHandler {
      void handleWarning(SQLWarning var1) throws SQLException;
   }
}
