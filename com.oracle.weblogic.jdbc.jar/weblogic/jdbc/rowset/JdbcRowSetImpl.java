package weblogic.jdbc.rowset;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetWarning;
import weblogic.utils.StackTraceUtils;

/** @deprecated */
@Deprecated
public class JdbcRowSetImpl implements JdbcRowSet {
   String url;
   String userName;
   String password;
   String dataSourceName;
   boolean preferDataSource;
   transient DataSource dataSource;
   transient Connection cachedConnection;
   String command;
   transient ArrayList params = new ArrayList();
   int isolationLevel = -1;
   int fetchDirection = 1000;
   int fetchSize;
   int concurrency = 1008;
   int resultSetType = 1004;
   Map typeMap;
   int queryTimeout;
   int maxRows;
   int maxFieldSize;
   boolean escapeProcessing;
   boolean showDeleted;
   boolean isComplete;
   boolean wasNull;
   private Connection conn = null;
   private CallableStatement cstmt = null;
   private ResultSet rs = null;
   CachedRowSetMetaData metaData = null;
   LifeCycle.State state;
   List rowSetListeners;

   public JdbcRowSetImpl() {
      this.state = LifeCycle.DESIGNING;
      this.rowSetListeners = new ArrayList();
   }

   public JdbcRowSetImpl(Connection con) {
      this.state = LifeCycle.DESIGNING;
      this.rowSetListeners = new ArrayList();
      throw new RuntimeException("Method not implemented");
   }

   public JdbcRowSetImpl(ResultSet res) {
      this.state = LifeCycle.DESIGNING;
      this.rowSetListeners = new ArrayList();
      throw new RuntimeException("Method not implemented");
   }

   public JdbcRowSetImpl(String url, String user, String password) {
      this.state = LifeCycle.DESIGNING;
      this.rowSetListeners = new ArrayList();
      throw new RuntimeException("Method not implemented");
   }

   void checkOp(int s) {
      this.state = this.state.checkOp(s);
   }

   public void addRowSetListener(RowSetListener rsl) {
      this.rowSetListeners.add(rsl);
   }

   public void removeRowSetListener(RowSetListener rsl) {
      this.rowSetListeners.remove(rsl);
   }

   void cursorMoved() {
      RowSetEvent event = new RowSetEvent(this);
      Iterator it = this.rowSetListeners.iterator();

      while(it.hasNext()) {
         RowSetListener l = (RowSetListener)it.next();
         l.cursorMoved(event);
      }

   }

   void rowChanged() {
      RowSetEvent event = new RowSetEvent(this);
      Iterator it = this.rowSetListeners.iterator();

      while(it.hasNext()) {
         RowSetListener l = (RowSetListener)it.next();
         l.rowChanged(event);
      }

   }

   void rowSetChanged() {
      RowSetEvent event = new RowSetEvent(this);
      Iterator it = this.rowSetListeners.iterator();

      while(it.hasNext()) {
         RowSetListener l = (RowSetListener)it.next();
         l.rowSetChanged(event);
      }

   }

   public ResultSetMetaData getMetaData() throws SQLException {
      return this.metaData;
   }

   public boolean isReadOnly() {
      return this.metaData.isReadOnly();
   }

   public void setReadOnly(boolean b) {
      this.metaData.setReadOnly(b);
   }

   public String getTableName() throws SQLException {
      return this.metaData.getWriteTableName();
   }

   public void setTableName(String tabName) throws SQLException {
      this.metaData.setWriteTableName(tabName);
   }

   public int[] getKeyColumns() throws SQLException {
      return this.metaData.getKeyColumns();
   }

   public void setKeyColumns(int[] keys) throws SQLException {
      this.metaData.setKeyColumns(keys);
   }

   public boolean getShowDeleted() throws SQLException {
      return this.showDeleted;
   }

   public void setShowDeleted(boolean b) throws SQLException {
      this.showDeleted = b;
   }

   public SQLWarning getWarnings() throws SQLException {
      return null;
   }

   public void clearWarnings() throws SQLException {
   }

   public RowSetWarning getRowSetWarnings() {
      return null;
   }

   public String getCursorName() throws SQLException {
      return this.rs.getCursorName();
   }

   public Statement getStatement() throws SQLException {
      return this.rs.getStatement();
   }

   public String getDataSourceName() {
      return this.dataSourceName;
   }

   public void setDataSourceName(String s) {
      this.dataSourceName = s;
      this.preferDataSource = true;
   }

   private DataSource lookupDataSource() throws SQLException {
      Context ctx = null;

      try {
         Hashtable p = new Hashtable();
         if (this.userName != null) {
            p.put("java.naming.security.principal", this.userName);
         }

         if (this.password != null) {
            p.put("java.naming.security.credentials", this.password);
         }

         ctx = new InitialContext(p);
      } catch (NamingException var5) {
         throw new SQLException("Unable to access JNDI with username: " + this.userName + ". Please ensure that your username and password are correct.", StackTraceUtils.throwable2StackTrace(var5));
      }

      try {
         this.dataSource = (DataSource)ctx.lookup(this.dataSourceName);
         return this.dataSource;
      } catch (ClassCastException var3) {
         throw new SQLException("The name: " + this.dataSourceName + " was found in the JNDI tree, but this is not a javax.sql.DataSource.");
      } catch (NamingException var4) {
         throw new SQLException("Unable to locate a DataSource with the name: " + this.dataSourceName + ".  Please check your configuration and make sure that this DataSource has been deployed.", StackTraceUtils.throwable2StackTrace(var4));
      }
   }

   public DataSource getDataSource() throws SQLException {
      if (this.dataSource != null) {
         return this.dataSource;
      } else {
         return this.dataSourceName != null ? this.lookupDataSource() : null;
      }
   }

   public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
      this.preferDataSource = true;
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String u) {
      this.url = u;
      this.preferDataSource = false;
   }

   public String getUsername() {
      return this.userName;
   }

   public void setUsername(String un) {
      this.userName = un;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String p) {
      this.password = p;
   }

   public Connection getConnection() throws SQLException {
      if (this.conn == null) {
         if (this.preferDataSource) {
            if (this.dataSource == null) {
               if (this.dataSourceName == null) {
                  throw new SQLException("You must call setDataSourceName and provide a valid DataSource JNDI name before attempting JDBC commands.");
               }

               this.lookupDataSource();
            }

            this.conn = this.dataSource.getConnection();
         } else {
            Properties info = new Properties();
            info.setProperty("user", this.userName);
            info.setProperty("password", this.password);
            if (this.url != null && this.url.toLowerCase().matches(".*protocol\\s*=\\s*sdp.*")) {
               info.put("oracle.net.SDP", "true");
            }

            this.conn = DriverManager.getConnection(this.url, info);
         }
      }

      if (this.isolationLevel != -1) {
         this.conn.setTransactionIsolation(this.isolationLevel);
      }

      return this.conn;
   }

   void toDesign() {
      this.checkOp(0);
   }

   public int getTransactionIsolation() {
      return this.isolationLevel;
   }

   public void setTransactionIsolation(int i) throws SQLException {
      this.toDesign();
      switch (i) {
         case 0:
         case 1:
         case 2:
         case 4:
         case 8:
            this.isolationLevel = i;
         case -1:
            return;
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            throw new SQLException("Invalid value for setTransactionIsolation: " + i);
      }
   }

   public String getCommand() {
      return this.command;
   }

   public void setCommand(String s) {
      this.toDesign();
      this.command = s;
      this.clearParameters();
   }

   public int getQueryTimeout() {
      return this.queryTimeout;
   }

   public void setQueryTimeout(int t) {
      this.toDesign();
      this.queryTimeout = t;
   }

   public int getMaxRows() {
      return this.maxRows;
   }

   public void setMaxRows(int n) {
      this.toDesign();
      this.maxRows = n;
   }

   public int getMaxFieldSize() {
      return this.maxFieldSize;
   }

   public void setMaxFieldSize(int x) {
      this.toDesign();
      this.maxFieldSize = x;
   }

   public boolean getEscapeProcessing() {
      return this.escapeProcessing;
   }

   public void setEscapeProcessing(boolean b) {
      this.toDesign();
      this.escapeProcessing = b;
   }

   public int getConcurrency() {
      return this.concurrency;
   }

   public void setConcurrency(int c) {
      this.toDesign();
      this.concurrency = c;
   }

   public int getType() {
      return this.resultSetType;
   }

   public void setType(int t) {
      this.toDesign();
      this.resultSetType = t;
   }

   public int getFetchDirection() {
      return this.fetchDirection;
   }

   public void setFetchDirection(int i) throws SQLException {
      this.toDesign();
      switch (i) {
         case 1001:
            if (this.getType() == 1003) {
               throw new SQLException("Cannot set FETCH_REVERSE on a TYPE_FORWARD_ONLY RowSet.");
            }
         case 1000:
         case 1002:
            this.fetchDirection = i;
            return;
         default:
            throw new SQLException("Unknown value for setFetchDirection:" + i);
      }
   }

   public int getFetchSize() {
      return this.fetchSize;
   }

   public void setFetchSize(int i) {
      this.toDesign();
      this.fetchSize = i;
   }

   public Map getTypeMap() {
      return this.typeMap;
   }

   public void setTypeMap(Map map) {
      this.toDesign();
      this.typeMap = map;
   }

   public boolean isComplete() {
      return this.isComplete;
   }

   public void setIsComplete(boolean b) {
      this.isComplete = b;
   }

   ArrayList getParameters() {
      return this.params;
   }

   public void clearParameters() {
      this.params.clear();
   }

   public Object[] getParams() throws SQLException {
      Object[] ret = new Object[this.params.size()];

      for(int i = 0; i < this.params.size(); ++i) {
         ret[i] = ((WLParameter)this.params.get(i)).getObject();
      }

      return ret;
   }

   public int getRow() throws SQLException {
      return this.rs.getRow();
   }

   public void moveToInsertRow() throws SQLException {
      this.checkOp(5);
      this.rs.moveToInsertRow();
   }

   public void moveToCurrentRow() throws SQLException {
      this.checkOp(6);
      this.rs.moveToCurrentRow();
   }

   public boolean absolute(int i) throws SQLException {
      this.checkOp(4);
      return this.rs.absolute(i);
   }

   public void beforeFirst() throws SQLException {
      this.checkOp(4);
      this.rs.beforeFirst();
   }

   public boolean first() throws SQLException {
      this.checkOp(4);
      return this.rs.first();
   }

   public boolean last() throws SQLException {
      this.checkOp(4);
      return this.rs.last();
   }

   public void afterLast() throws SQLException {
      this.checkOp(4);
      this.rs.afterLast();
   }

   public boolean relative(int i) throws SQLException {
      this.checkOp(4);
      return this.rs.relative(i);
   }

   public boolean next() throws SQLException {
      this.checkOp(4);
      return this.rs.next();
   }

   public boolean previous() throws SQLException {
      this.checkOp(4);
      return this.rs.previous();
   }

   public boolean isBeforeFirst() throws SQLException {
      this.checkOp(4);
      return this.rs.isBeforeFirst();
   }

   public boolean isAfterLast() throws SQLException {
      this.checkOp(4);
      return this.rs.isAfterLast();
   }

   public boolean isFirst() throws SQLException {
      this.checkOp(4);
      return this.rs.isFirst();
   }

   public boolean isLast() throws SQLException {
      this.checkOp(4);
      return this.rs.isLast();
   }

   public boolean rowUpdated() throws SQLException {
      return this.rs.rowUpdated();
   }

   public boolean rowInserted() throws SQLException {
      return this.rs.rowInserted();
   }

   public boolean rowDeleted() throws SQLException {
      return this.rs.rowDeleted();
   }

   void toConfigQuery() {
      this.checkOp(1);
   }

   public void setArray(int i, Array x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, 1, new Object[]{x}));
   }

   public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 2, new Object[]{x}));
   }

   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 3, new Object[]{x, new Integer(length)}));
   }

   public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 2, new Object[]{x}));
   }

   public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 3, new Object[]{x, new Integer(length)}));
   }

   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 4, new Object[]{x}));
   }

   public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 4, new Object[]{x}));
   }

   public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 5, new Object[]{x}));
   }

   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 6, new Object[]{x, new Integer(length)}));
   }

   public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 5, new Object[]{x}));
   }

   public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 6, new Object[]{x, new Integer(length)}));
   }

   public void setBlob(int i, Blob x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 7, new Object[]{x}));
   }

   public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 8, new Object[]{inputStream}));
   }

   public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 9, new Object[]{inputStream, new Long(length)}));
   }

   public void setBlob(String parameterName, Blob x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 7, new Object[]{x}));
   }

   public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 8, new Object[]{inputStream}));
   }

   public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 9, new Object[]{inputStream, new Long(length)}));
   }

   public void setBoolean(int parameterIndex, boolean x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 10, new Object[]{new Boolean(x)}));
   }

   public void setBoolean(String parameterName, boolean x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 10, new Object[]{new Boolean(x)}));
   }

   public void setByte(int parameterIndex, byte x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 11, new Object[]{new Byte(x)}));
   }

   public void setByte(String parameterName, byte x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 11, new Object[]{new Byte(x)}));
   }

   public void setBytes(int parameterIndex, byte[] x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 12, new Object[]{x}));
   }

   public void setBytes(String parameterName, byte[] x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 12, new Object[]{x}));
   }

   public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 13, new Object[]{reader}));
   }

   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 14, new Object[]{reader, new Integer(length)}));
   }

   public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 13, new Object[]{reader}));
   }

   public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 14, new Object[]{reader, new Integer(length)}));
   }

   public void setClob(int i, Clob x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 15, new Object[]{x}));
   }

   public void setClob(int parameterIndex, Reader reader) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 16, new Object[]{reader}));
   }

   public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 17, new Object[]{reader, new Long(length)}));
   }

   public void setClob(String parameterName, Clob x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 15, new Object[]{x}));
   }

   public void setClob(String parameterName, Reader reader) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 16, new Object[]{reader}));
   }

   public void setClob(String parameterName, Reader reader, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 17, new Object[]{reader, new Long(length)}));
   }

   public void setDate(int parameterIndex, Date x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 18, new Object[]{x}));
   }

   public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 19, new Object[]{x, cal}));
   }

   public void setDate(String parameterName, Date x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 18, new Object[]{x}));
   }

   public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 19, new Object[]{x, cal}));
   }

   public void setDouble(int parameterIndex, double x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 20, new Object[]{new Double(x)}));
   }

   public void setDouble(String parameterName, double x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 20, new Object[]{new Double(x)}));
   }

   public void setFloat(int parameterIndex, float x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 21, new Object[]{new Float(x)}));
   }

   public void setFloat(String parameterName, float x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 21, new Object[]{new Float(x)}));
   }

   public void setInt(int parameterIndex, int x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 22, new Object[]{new Integer(x)}));
   }

   public void setInt(String parameterName, int x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 22, new Object[]{new Integer(x)}));
   }

   public void setLong(int parameterIndex, long x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 23, new Object[]{new Long(x)}));
   }

   public void setLong(String parameterName, long x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 23, new Object[]{new Long(x)}));
   }

   public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 24, new Object[]{value}));
   }

   public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 25, new Object[]{value, new Long(length)}));
   }

   public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 24, new Object[]{value}));
   }

   public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 25, new Object[]{value, new Long(length)}));
   }

   public void setNClob(int parameterIndex, NClob value) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 26, new Object[]{value}));
   }

   public void setNClob(int parameterIndex, Reader reader) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 27, new Object[]{reader}));
   }

   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 28, new Object[]{reader, new Long(length)}));
   }

   public void setNClob(String parameterName, NClob value) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 26, new Object[]{value}));
   }

   public void setNClob(String parameterName, Reader reader) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 27, new Object[]{reader}));
   }

   public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 28, new Object[]{reader, new Long(length)}));
   }

   public void setNString(int parameterIndex, String value) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 29, new Object[]{value}));
   }

   public void setNString(String parameterName, String value) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 29, new Object[]{value}));
   }

   public void setNull(int parameterIndex, int sqlType) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 30, new Object[]{new Integer(sqlType)}));
   }

   public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 31, new Object[]{new Integer(sqlType), typeName}));
   }

   public void setNull(String parameterName, int sqlType) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 30, new Object[]{new Integer(sqlType)}));
   }

   public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 31, new Object[]{new Integer(sqlType), typeName}));
   }

   public void setObject(int parameterIndex, Object x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 32, new Object[]{x}));
   }

   public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 33, new Object[]{x, new Integer(targetSqlType)}));
   }

   public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 34, new Object[]{x, new Integer(targetSqlType), new Integer(scaleOrLength)}));
   }

   public void setObject(String parameterName, Object x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 32, new Object[]{x}));
   }

   public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 33, new Object[]{x, new Integer(targetSqlType)}));
   }

   public void setObject(String parameterName, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 34, new Object[]{x, new Integer(targetSqlType), new Integer(scaleOrLength)}));
   }

   public void setRef(int i, Ref x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, 35, new Object[]{x}));
   }

   public void setRowId(int parameterIndex, RowId x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 36, new Object[]{x}));
   }

   public void setRowId(String parameterName, RowId x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 36, new Object[]{x}));
   }

   public void setShort(int parameterIndex, short x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 37, new Object[]{new Short(x)}));
   }

   public void setShort(String parameterName, short x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 37, new Object[]{new Short(x)}));
   }

   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 38, new Object[]{xmlObject}));
   }

   public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 38, new Object[]{xmlObject}));
   }

   public void setString(int parameterIndex, String x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 39, new Object[]{x}));
   }

   public void setString(String parameterName, String x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 39, new Object[]{x}));
   }

   public void setTime(int parameterIndex, Time x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 40, new Object[]{x}));
   }

   public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 41, new Object[]{x, cal}));
   }

   public void setTime(String parameterName, Time x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 40, new Object[]{x}));
   }

   public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 41, new Object[]{x, cal}));
   }

   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 42, new Object[]{x}));
   }

   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, 43, new Object[]{x, cal}));
   }

   public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 42, new Object[]{x}));
   }

   public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 43, new Object[]{x, cal}));
   }

   public void setURL(int parameterIndex, URL x) throws SQLException {
      this.params.add(new WLParameter(parameterIndex, 44, new Object[]{x}));
   }

   public Array getArray(int columnIndex) throws SQLException {
      return this.rs.getArray(columnIndex);
   }

   public Array getArray(String columnLabel) throws SQLException {
      return this.rs.getArray(columnLabel);
   }

   public InputStream getAsciiStream(int columnIndex) throws SQLException {
      return this.rs.getAsciiStream(columnIndex);
   }

   public InputStream getAsciiStream(String columnLabel) throws SQLException {
      return this.rs.getAsciiStream(columnLabel);
   }

   public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
      return this.rs.getBigDecimal(columnIndex);
   }

   /** @deprecated */
   @Deprecated
   public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
      return this.rs.getBigDecimal(columnIndex, scale);
   }

   public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
      return this.rs.getBigDecimal(columnLabel);
   }

   /** @deprecated */
   @Deprecated
   public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
      return this.rs.getBigDecimal(columnLabel, scale);
   }

   public InputStream getBinaryStream(int columnIndex) throws SQLException {
      return this.rs.getBinaryStream(columnIndex);
   }

   public InputStream getBinaryStream(String columnLabel) throws SQLException {
      return this.rs.getBinaryStream(columnLabel);
   }

   public Blob getBlob(int columnIndex) throws SQLException {
      try {
         return this.rs.getBlob(columnIndex);
      } catch (SQLException var4) {
         byte[] blobBytes = this.rs.getBytes(columnIndex);
         return new RowSetBlob(blobBytes);
      }
   }

   public Blob getBlob(String columnLabel) throws SQLException {
      try {
         return this.rs.getBlob(columnLabel);
      } catch (SQLException var4) {
         byte[] blobBytes = this.rs.getBytes(columnLabel);
         return new RowSetBlob(blobBytes);
      }
   }

   public boolean getBoolean(int columnIndex) throws SQLException {
      return this.rs.getBoolean(columnIndex);
   }

   public boolean getBoolean(String columnLabel) throws SQLException {
      return this.rs.getBoolean(columnLabel);
   }

   public byte getByte(int columnIndex) throws SQLException {
      return this.rs.getByte(columnIndex);
   }

   public byte getByte(String columnLabel) throws SQLException {
      return this.rs.getByte(columnLabel);
   }

   public byte[] getBytes(int columnIndex) throws SQLException {
      return this.rs.getBytes(columnIndex);
   }

   public byte[] getBytes(String columnLabel) throws SQLException {
      return this.rs.getBytes(columnLabel);
   }

   public Reader getCharacterStream(int columnIndex) throws SQLException {
      return this.rs.getCharacterStream(columnIndex);
   }

   public Reader getCharacterStream(String columnLabel) throws SQLException {
      return this.rs.getCharacterStream(columnLabel);
   }

   public Clob getClob(int columnIndex) throws SQLException {
      try {
         return this.rs.getClob(columnIndex);
      } catch (SQLException var4) {
         String clobString = this.rs.getString(columnIndex);
         return new RowSetClob(clobString);
      }
   }

   public Clob getClob(String columnLabel) throws SQLException {
      try {
         return this.rs.getClob(columnLabel);
      } catch (SQLException var4) {
         String clobString = this.rs.getString(columnLabel);
         return new RowSetClob(clobString);
      }
   }

   public Date getDate(int columnIndex) throws SQLException {
      return this.rs.getDate(columnIndex);
   }

   public Date getDate(int columnIndex, Calendar cal) throws SQLException {
      return this.rs.getDate(columnIndex, cal);
   }

   public Date getDate(String columnLabel) throws SQLException {
      return this.rs.getDate(columnLabel);
   }

   public Date getDate(String columnLabel, Calendar cal) throws SQLException {
      return this.rs.getDate(columnLabel, cal);
   }

   public double getDouble(int columnIndex) throws SQLException {
      return this.rs.getDouble(columnIndex);
   }

   public double getDouble(String columnLabel) throws SQLException {
      return this.rs.getDouble(columnLabel);
   }

   public float getFloat(int columnIndex) throws SQLException {
      return this.rs.getFloat(columnIndex);
   }

   public float getFloat(String columnLabel) throws SQLException {
      return this.rs.getFloat(columnLabel);
   }

   public int getInt(int columnIndex) throws SQLException {
      return this.rs.getInt(columnIndex);
   }

   public int getInt(String columnLabel) throws SQLException {
      return this.rs.getInt(columnLabel);
   }

   public long getLong(int columnIndex) throws SQLException {
      return this.rs.getLong(columnIndex);
   }

   public long getLong(String columnLabel) throws SQLException {
      return this.rs.getLong(columnLabel);
   }

   public Reader getNCharacterStream(int columnIndex) throws SQLException {
      return this.rs.getNCharacterStream(columnIndex);
   }

   public Reader getNCharacterStream(String columnLabel) throws SQLException {
      return this.rs.getNCharacterStream(columnLabel);
   }

   public NClob getNClob(int columnIndex) throws SQLException {
      try {
         return this.rs.getNClob(columnIndex);
      } catch (SQLException var4) {
         String clobString = this.rs.getString(columnIndex);
         return new RowSetNClob(clobString);
      }
   }

   public NClob getNClob(String columnLabel) throws SQLException {
      try {
         return this.rs.getNClob(columnLabel);
      } catch (SQLException var4) {
         String clobString = this.rs.getString(columnLabel);
         return new RowSetNClob(clobString);
      }
   }

   public String getNString(int columnIndex) throws SQLException {
      return this.rs.getNString(columnIndex);
   }

   public String getNString(String columnLabel) throws SQLException {
      return this.rs.getNString(columnLabel);
   }

   public Object getObject(int columnIndex) throws SQLException {
      return this.rs.getObject(columnIndex);
   }

   public Object getObject(int columnIndex, Map map) throws SQLException {
      return this.rs.getObject(columnIndex, map);
   }

   public Object getObject(String columnLabel) throws SQLException {
      return this.rs.getObject(columnLabel);
   }

   public Object getObject(String columnLabel, Map map) throws SQLException {
      return this.rs.getObject(columnLabel, map);
   }

   public Ref getRef(int columnIndex) throws SQLException {
      return this.rs.getRef(columnIndex);
   }

   public Ref getRef(String columnLabel) throws SQLException {
      return this.rs.getRef(columnLabel);
   }

   public RowId getRowId(int columnIndex) throws SQLException {
      return this.rs.getRowId(columnIndex);
   }

   public RowId getRowId(String columnLabel) throws SQLException {
      return this.rs.getRowId(columnLabel);
   }

   public short getShort(int columnIndex) throws SQLException {
      return this.rs.getShort(columnIndex);
   }

   public short getShort(String columnLabel) throws SQLException {
      return this.rs.getShort(columnLabel);
   }

   public SQLXML getSQLXML(int columnIndex) throws SQLException {
      return this.rs.getSQLXML(columnIndex);
   }

   public SQLXML getSQLXML(String columnLabel) throws SQLException {
      return this.rs.getSQLXML(columnLabel);
   }

   public String getString(int columnIndex) throws SQLException {
      return this.rs.getString(columnIndex);
   }

   public String getString(String columnLabel) throws SQLException {
      return this.rs.getString(columnLabel);
   }

   public Time getTime(int columnIndex) throws SQLException {
      return this.rs.getTime(columnIndex);
   }

   public Time getTime(int columnIndex, Calendar cal) throws SQLException {
      return this.rs.getTime(columnIndex, cal);
   }

   public Time getTime(String columnLabel) throws SQLException {
      return this.rs.getTime(columnLabel);
   }

   public Time getTime(String columnLabel, Calendar cal) throws SQLException {
      return this.rs.getTime(columnLabel, cal);
   }

   public Timestamp getTimestamp(int columnIndex) throws SQLException {
      return this.rs.getTimestamp(columnIndex);
   }

   public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
      return this.rs.getTimestamp(columnIndex, cal);
   }

   public Timestamp getTimestamp(String columnLabel) throws SQLException {
      return this.rs.getTimestamp(columnLabel);
   }

   public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
      return this.rs.getTimestamp(columnLabel, cal);
   }

   /** @deprecated */
   @Deprecated
   public InputStream getUnicodeStream(int columnIndex) throws SQLException {
      return this.rs.getUnicodeStream(columnIndex);
   }

   /** @deprecated */
   @Deprecated
   public InputStream getUnicodeStream(String columnLabel) throws SQLException {
      return this.rs.getUnicodeStream(columnLabel);
   }

   public URL getURL(int columnIndex) throws SQLException {
      return this.rs.getURL(columnIndex);
   }

   public URL getURL(String columnLabel) throws SQLException {
      return this.rs.getURL(columnLabel);
   }

   public boolean wasNull() throws SQLException {
      return this.rs.wasNull();
   }

   public void updateArray(int columnIndex, Array x) throws SQLException {
      this.checkOp(7);
      this.rs.updateArray(columnIndex, x);
   }

   public void updateArray(String columnLabel, Array x) throws SQLException {
      this.checkOp(7);
      this.rs.updateArray(columnLabel, x);
   }

   public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
      this.checkOp(7);
      this.rs.updateAsciiStream(columnIndex, x);
   }

   public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
      this.checkOp(7);
      this.rs.updateAsciiStream(columnIndex, x, length);
   }

   public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateAsciiStream(columnIndex, x, length);
   }

   public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
      this.checkOp(7);
      this.rs.updateAsciiStream(columnLabel, x);
   }

   public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
      this.checkOp(7);
      this.rs.updateAsciiStream(columnLabel, x, length);
   }

   public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateAsciiStream(columnLabel, x, length);
   }

   public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
      this.checkOp(7);
      this.rs.updateBigDecimal(columnIndex, x);
   }

   public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
      this.checkOp(7);
      this.rs.updateBigDecimal(columnLabel, x);
   }

   public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
      this.checkOp(7);
      this.rs.updateBinaryStream(columnIndex, x);
   }

   public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
      this.checkOp(7);
      this.rs.updateBinaryStream(columnIndex, x, length);
   }

   public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateBinaryStream(columnIndex, x, length);
   }

   public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
      this.checkOp(7);
      this.rs.updateBinaryStream(columnLabel, x);
   }

   public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
      this.checkOp(7);
      this.rs.updateBinaryStream(columnLabel, x, length);
   }

   public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateBinaryStream(columnLabel, x, length);
   }

   public void updateBlob(int columnIndex, Blob x) throws SQLException {
      this.checkOp(7);
      this.rs.updateBlob(columnIndex, x);
   }

   public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
      this.checkOp(7);
      this.rs.updateBlob(columnIndex, inputStream);
   }

   public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateBlob(columnIndex, inputStream, length);
   }

   public void updateBlob(String columnLabel, Blob x) throws SQLException {
      this.checkOp(7);
      this.rs.updateBlob(columnLabel, x);
   }

   public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
      this.checkOp(7);
      this.rs.updateBlob(columnLabel, inputStream);
   }

   public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateBlob(columnLabel, inputStream, length);
   }

   public void updateBoolean(int columnIndex, boolean x) throws SQLException {
      this.checkOp(7);
      this.rs.updateBoolean(columnIndex, x);
   }

   public void updateBoolean(String columnLabel, boolean x) throws SQLException {
      this.checkOp(7);
      this.rs.updateBoolean(columnLabel, x);
   }

   public void updateByte(int columnIndex, byte x) throws SQLException {
      this.checkOp(7);
      this.rs.updateByte(columnIndex, x);
   }

   public void updateByte(String columnLabel, byte x) throws SQLException {
      this.checkOp(7);
      this.rs.updateByte(columnLabel, x);
   }

   public void updateBytes(int columnIndex, byte[] x) throws SQLException {
      this.checkOp(7);
      this.rs.updateBytes(columnIndex, x);
   }

   public void updateBytes(String columnLabel, byte[] x) throws SQLException {
      this.checkOp(7);
      this.rs.updateBytes(columnLabel, x);
   }

   public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
      this.checkOp(7);
      this.rs.updateCharacterStream(columnIndex, x);
   }

   public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
      this.checkOp(7);
      this.rs.updateCharacterStream(columnIndex, x, length);
   }

   public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateCharacterStream(columnIndex, x, length);
   }

   public void updateCharacterStream(String columnLabel, Reader x) throws SQLException {
      this.checkOp(7);
      this.rs.updateCharacterStream(columnLabel, x);
   }

   public void updateCharacterStream(String columnLabel, Reader x, int length) throws SQLException {
      this.checkOp(7);
      this.rs.updateCharacterStream(columnLabel, x, length);
   }

   public void updateCharacterStream(String columnLabel, Reader x, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateCharacterStream(columnLabel, x, length);
   }

   public void updateClob(int columnIndex, Clob x) throws SQLException {
      this.checkOp(7);
      this.rs.updateClob(columnIndex, x);
   }

   public void updateClob(int columnIndex, Reader reader) throws SQLException {
      this.checkOp(7);
      this.rs.updateClob(columnIndex, reader);
   }

   public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateClob(columnIndex, reader, length);
   }

   public void updateClob(String columnLabel, Clob x) throws SQLException {
      this.checkOp(7);
      this.rs.updateClob(columnLabel, x);
   }

   public void updateClob(String columnLabel, Reader reader) throws SQLException {
      this.checkOp(7);
      this.rs.updateClob(columnLabel, reader);
   }

   public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateClob(columnLabel, reader, length);
   }

   public void updateDate(int columnIndex, Date x) throws SQLException {
      this.checkOp(7);
      this.rs.updateDate(columnIndex, x);
   }

   public void updateDate(String columnLabel, Date x) throws SQLException {
      this.checkOp(7);
      this.rs.updateDate(columnLabel, x);
   }

   public void updateDouble(int columnIndex, double x) throws SQLException {
      this.checkOp(7);
      this.rs.updateDouble(columnIndex, x);
   }

   public void updateDouble(String columnLabel, double x) throws SQLException {
      this.checkOp(7);
      this.rs.updateDouble(columnLabel, x);
   }

   public void updateFloat(int columnIndex, float x) throws SQLException {
      this.checkOp(7);
      this.rs.updateFloat(columnIndex, x);
   }

   public void updateFloat(String columnLabel, float x) throws SQLException {
      this.checkOp(7);
      this.rs.updateFloat(columnLabel, x);
   }

   public void updateInt(int columnIndex, int x) throws SQLException {
      this.checkOp(7);
      this.rs.updateInt(columnIndex, x);
   }

   public void updateInt(String columnLabel, int x) throws SQLException {
      this.checkOp(7);
      this.rs.updateInt(columnLabel, x);
   }

   public void updateLong(int columnIndex, long x) throws SQLException {
      this.checkOp(7);
      this.rs.updateLong(columnIndex, x);
   }

   public void updateLong(String columnLabel, long x) throws SQLException {
      this.checkOp(7);
      this.rs.updateLong(columnLabel, x);
   }

   public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
      this.checkOp(7);
      this.rs.updateNCharacterStream(columnIndex, x);
   }

   public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateNCharacterStream(columnIndex, x, length);
   }

   public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
      this.checkOp(7);
      this.rs.updateNCharacterStream(columnLabel, reader);
   }

   public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateNCharacterStream(columnLabel, reader, length);
   }

   public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
      this.checkOp(7);
      this.rs.updateNClob(columnIndex, nClob);
   }

   public void updateNClob(int columnIndex, Reader reader) throws SQLException {
      this.checkOp(7);
      this.rs.updateNClob(columnIndex, reader);
   }

   public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateNClob(columnIndex, reader, length);
   }

   public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
      this.checkOp(7);
      this.rs.updateNClob(columnLabel, nClob);
   }

   public void updateNClob(String columnLabel, Reader reader) throws SQLException {
      this.checkOp(7);
      this.rs.updateNClob(columnLabel, reader);
   }

   public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
      this.checkOp(7);
      this.rs.updateNClob(columnLabel, reader, length);
   }

   public void updateNString(int columnIndex, String nString) throws SQLException {
      this.checkOp(7);
      this.rs.updateNString(columnIndex, nString);
   }

   public void updateNString(String columnLabel, String nString) throws SQLException {
      this.checkOp(7);
      this.rs.updateNString(columnLabel, nString);
   }

   public void updateNull(int columnIndex) throws SQLException {
      this.checkOp(7);
      this.rs.updateNull(columnIndex);
   }

   public void updateNull(String columnLabel) throws SQLException {
      this.checkOp(7);
      this.rs.updateNull(columnLabel);
   }

   public void updateObject(int columnIndex, Object x) throws SQLException {
      this.checkOp(7);
      this.rs.updateObject(columnIndex, x);
   }

   public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
      this.checkOp(7);
      this.rs.updateObject(columnIndex, x, scaleOrLength);
   }

   public void updateObject(String columnLabel, Object x) throws SQLException {
      this.checkOp(7);
      this.rs.updateObject(columnLabel, x);
   }

   public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
      this.checkOp(7);
      this.rs.updateObject(columnLabel, x, scaleOrLength);
   }

   public void updateRef(int columnIndex, Ref x) throws SQLException {
      this.checkOp(7);
      this.rs.updateRef(columnIndex, x);
   }

   public void updateRef(String columnLabel, Ref x) throws SQLException {
      this.checkOp(7);
      this.rs.updateRef(columnLabel, x);
   }

   public void updateRowId(int columnIndex, RowId x) throws SQLException {
      this.checkOp(7);
      this.rs.updateRowId(columnIndex, x);
   }

   public void updateRowId(String columnLabel, RowId x) throws SQLException {
      this.checkOp(7);
      this.rs.updateRowId(columnLabel, x);
   }

   public void updateShort(int columnIndex, short x) throws SQLException {
      this.checkOp(7);
      this.rs.updateShort(columnIndex, x);
   }

   public void updateShort(String columnLabel, short x) throws SQLException {
      this.checkOp(7);
      this.rs.updateShort(columnLabel, x);
   }

   public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
      this.checkOp(7);
      this.rs.updateSQLXML(columnIndex, xmlObject);
   }

   public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
      this.checkOp(7);
      this.rs.updateSQLXML(columnLabel, xmlObject);
   }

   public void updateString(int columnIndex, String x) throws SQLException {
      this.checkOp(7);
      this.rs.updateString(columnIndex, x);
   }

   public void updateString(String columnLabel, String x) throws SQLException {
      this.checkOp(7);
      this.rs.updateString(columnLabel, x);
   }

   public void updateTime(int columnIndex, Time x) throws SQLException {
      this.checkOp(7);
      this.rs.updateTime(columnIndex, x);
   }

   public void updateTime(String columnLabel, Time x) throws SQLException {
      this.checkOp(7);
      this.rs.updateTime(columnLabel, x);
   }

   public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
      this.checkOp(7);
      this.rs.updateTimestamp(columnIndex, x);
   }

   public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
      this.checkOp(7);
      this.rs.updateTimestamp(columnLabel, x);
   }

   public int[] getMatchColumnIndexes() throws SQLException {
      return this.metaData.getMatchColumns();
   }

   public String[] getMatchColumnNames() throws SQLException {
      int[] matchColumns = this.metaData.getMatchColumns();
      String[] matchColumnNames = new String[matchColumns.length];

      for(int i = 0; i < matchColumns.length; ++i) {
         matchColumnNames[i] = this.metaData.getColumnName(matchColumns[i]);
      }

      return matchColumnNames;
   }

   public void setMatchColumn(int columnIdx) throws SQLException {
      this.metaData.setMatchColumn(columnIdx, true);
   }

   public void setMatchColumn(String columnName) throws SQLException {
      this.metaData.setMatchColumn(this.findColumn(columnName), true);
   }

   public void setMatchColumn(int[] columnIdxes) throws SQLException {
      for(int i = 0; i < columnIdxes.length; ++i) {
         this.setMatchColumn(columnIdxes[i]);
      }

   }

   public void setMatchColumn(String[] columnNames) throws SQLException {
      for(int i = 0; i < columnNames.length; ++i) {
         this.setMatchColumn(columnNames[i]);
      }

   }

   public void unsetMatchColumn(int columnIdx) throws SQLException {
      this.metaData.setMatchColumn(columnIdx, false);
   }

   public void unsetMatchColumn(String columnName) throws SQLException {
      this.metaData.setMatchColumn(this.findColumn(columnName), false);
   }

   public void unsetMatchColumn(int[] columnIdxes) throws SQLException {
      for(int i = 0; i < columnIdxes.length; ++i) {
         this.unsetMatchColumn(columnIdxes[i]);
      }

   }

   public void unsetMatchColumn(String[] columnNames) throws SQLException {
      for(int i = 0; i < columnNames.length; ++i) {
         this.unsetMatchColumn(columnNames[i]);
      }

   }

   public void execute() throws SQLException {
      this.checkOp(2);
      this.cstmt = this.getConnection().prepareCall(this.command, this.resultSetType, this.concurrency);
      Iterator it = this.getParameters().iterator();

      while(it.hasNext()) {
         WLParameter p = (WLParameter)it.next();
         p.setParam(this.cstmt);
      }

      this.rs = this.cstmt.executeQuery();
      this.metaData = new CachedRowSetMetaData();
      this.metaData.initialize(this.rs.getMetaData(), this.rs.getStatement().getConnection().getMetaData());
   }

   public void insertRow() throws SQLException {
      this.checkOp(8);
      this.rs.insertRow();
      this.rowChanged();
   }

   public void deleteRow() throws SQLException {
      this.checkOp(4);
      this.rs.deleteRow();
      this.rowChanged();
   }

   public void updateRow() throws SQLException {
      this.checkOp(8);
      this.rs.updateRow();
      this.rowChanged();
   }

   public void refreshRow() throws SQLException {
      this.rs.updateRow();
   }

   public void cancelRowUpdates() throws SQLException {
      this.checkOp(8);
      this.rs.cancelRowUpdates();
   }

   public void commit() throws SQLException {
      this.conn.commit();
   }

   public boolean getAutoCommit() throws SQLException {
      return this.conn.getAutoCommit();
   }

   public void setAutoCommit(boolean autoCommit) throws SQLException {
      this.conn.setAutoCommit(autoCommit);
   }

   public void rollback() throws SQLException {
      this.conn.rollback();
   }

   public void rollback(Savepoint s) throws SQLException {
      this.conn.rollback(s);
   }

   public void close() throws SQLException {
      try {
         if (this.rs != null) {
            this.rs.close();
         }
      } catch (Throwable var4) {
      }

      try {
         if (this.cstmt != null) {
            this.cstmt.close();
         }
      } catch (Throwable var3) {
      }

      try {
         if (this.conn != null) {
            this.conn.close();
         }
      } catch (Throwable var2) {
      }

   }

   public int findColumn(String s) throws SQLException {
      return this.metaData.findColumn(s);
   }

   public int getHoldability() throws SQLException {
      return this.rs.getHoldability();
   }

   public boolean isClosed() throws SQLException {
      return this.rs == null ? false : this.rs.isClosed();
   }

   public Object unwrap(Class iface) throws SQLException {
      if (iface.isInstance(this)) {
         return iface.cast(this);
      } else {
         throw new SQLException(this + " is not an instance of " + iface);
      }
   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      return iface.isInstance(this);
   }

   public Object getObject(int columnIndex, Class type) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }

   public Object getObject(String columnLabel, Class type) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }
}
