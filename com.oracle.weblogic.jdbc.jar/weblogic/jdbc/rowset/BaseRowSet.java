package weblogic.jdbc.rowset;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
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
import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.RowSetMetaData;
import javax.sql.RowSetReader;
import javax.sql.RowSetWriter;
import javax.sql.rowset.Joinable;
import javax.sql.rowset.RowSetWarning;
import javax.sql.rowset.serial.SerialArray;
import javax.sql.rowset.serial.SerialDatalink;
import javax.sql.rowset.serial.SerialRef;
import javax.sql.rowset.spi.SyncFactory;
import javax.sql.rowset.spi.SyncProvider;
import weblogic.jdbc.JDBCLogger;
import weblogic.utils.StackTraceUtils;

/** @deprecated */
@Deprecated
public abstract class BaseRowSet implements Serializable, Joinable {
   private static final long serialVersionUID = 496582906625949131L;
   String url;
   String userName;
   String password;
   String dataSourceName;
   boolean preferDataSource;
   transient DataSource dataSource;
   transient Connection cachedConnection;
   String command;
   transient ArrayList params = new ArrayList();
   int isolationLevel;
   int fetchDirection;
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
   transient String providerID;
   transient SyncProvider provider = new WLSyncProvider();
   transient RowSetWriter writer = new CachedRowSetJDBCWriter();
   transient RowSetReader reader = new CachedRowSetJDBCReader();
   transient boolean locked = false;
   CachedRowSetMetaData metaData;
   LifeCycle.State state;
   List rowSetListeners = new ArrayList();
   int rowIndex;
   boolean isClosed = false;
   private static final int BUFFER_SIZE = 8192;

   public abstract int size();

   public int getRow() {
      return this.rowIndex + 1;
   }

   public boolean absolute(int i) throws SQLException {
      this.checkIterator();
      int currentIndex = this.rowIndex;
      if (i < 0) {
         this.rowIndex = this.size() + i;
      } else {
         this.rowIndex = i - 1;
      }

      this.keepRowIndexValid();
      if (currentIndex != this.rowIndex) {
         this.cursorMoved();
      }

      return this.rowIndex >= 0 && this.rowIndex < this.size();
   }

   public boolean relative(int i) throws SQLException {
      this.checkIterator();
      int currentIndex = this.rowIndex;
      this.rowIndex += i;
      this.keepRowIndexValid();
      if (currentIndex != this.rowIndex) {
         this.cursorMoved();
      }

      return this.rowIndex >= 0 && this.rowIndex < this.size();
   }

   public void beforeFirst() throws SQLException {
      this.absolute(0);
   }

   public boolean first() throws SQLException {
      return this.absolute(1);
   }

   public boolean next() throws SQLException {
      return this.relative(1);
   }

   public boolean previous() throws SQLException {
      return this.relative(-1);
   }

   public boolean last() throws SQLException {
      return this.absolute(this.size());
   }

   public void afterLast() throws SQLException {
      this.absolute(this.size() + 1);
   }

   public boolean isBeforeFirst() throws SQLException {
      this.checkIterator();
      return this.rowIndex == -1;
   }

   public boolean isAfterLast() throws SQLException {
      this.checkIterator();
      return this.size() > 0 && this.rowIndex >= this.size();
   }

   public boolean isFirst() throws SQLException {
      this.checkIterator();
      return this.size() > 0 && this.rowIndex == 0;
   }

   public boolean isLast() throws SQLException {
      this.checkIterator();
      return this.size() > 0 && this.rowIndex == this.size() - 1;
   }

   public boolean rowUpdated() throws SQLException {
      return this.currentRow().isUpdatedRow();
   }

   public boolean rowInserted() throws SQLException {
      return this.currentRow().isInsertRow();
   }

   public boolean rowDeleted() throws SQLException {
      return this.currentRow().isDeletedRow();
   }

   public boolean columnUpdated(int idx) throws SQLException {
      return this.currentRow().isModified(idx);
   }

   public boolean columnUpdated(String columnName) throws SQLException {
      return this.currentRow().isModified(this.findColumn(columnName));
   }

   public ResultSetMetaData getMetaData() throws SQLException {
      return this.metaData;
   }

   public void setMetaData(RowSetMetaData md) throws SQLException {
      this.checkOp();
      if (md != null) {
         if (md instanceof CachedRowSetMetaData) {
            this.metaData = (CachedRowSetMetaData)((CachedRowSetMetaData)md).clone();
         } else {
            this.metaData = new CachedRowSetMetaData();
            DatabaseMetaData dbmd = null;

            try {
               dbmd = this.getConnection().getMetaData();
            } catch (Exception var4) {
               JDBCLogger.logStackTrace(var4);
            }

            this.metaData.initialize(md, dbmd);
         }

      }
   }

   public boolean isReadOnly() {
      return this.metaData.isReadOnly();
   }

   public void setReadOnly(boolean b) {
      this.checkOp(3);
      this.metaData.setReadOnly(b);
   }

   public void setTableName(String tabName) throws SQLException {
      this.checkOp();
      this.metaData.setWriteTableName(tabName);
   }

   public String getTableName() throws SQLException {
      return this.metaData.getWriteTableName();
   }

   public int[] getKeyColumns() throws SQLException {
      return this.metaData.getKeyColumns();
   }

   public void setKeyColumns(int[] keys) throws SQLException {
      this.checkOp();
      this.metaData.setKeyColumns(keys);
   }

   public boolean getShowDeleted() throws SQLException {
      return this.showDeleted;
   }

   public void setShowDeleted(boolean b) throws SQLException {
      this.checkOp();
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
      return null;
   }

   public Statement getStatement() throws SQLException {
      return null;
   }

   public String getDataSourceName() {
      return this.dataSourceName;
   }

   public void setDataSourceName(String s) {
      this.checkOp();
      this.dataSourceName = s;
      if (this.dataSourceName == null && this.dataSource == null) {
         this.preferDataSource = false;
      } else {
         this.preferDataSource = true;
      }

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
      this.checkOp();
      this.dataSource = dataSource;
      if (this.dataSourceName == null && dataSource == null) {
         this.preferDataSource = false;
      } else {
         this.preferDataSource = true;
      }

   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String u) {
      this.checkOp();
      this.url = u;
      this.preferDataSource = false;
   }

   boolean isPreferDataSource() {
      return this.preferDataSource;
   }

   public String getUsername() {
      return this.userName;
   }

   public void setUsername(String un) {
      this.checkOp();
      this.userName = un;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String p) {
      this.checkOp();
      this.password = p;
   }

   public Connection getConnection() throws SQLException {
      if (this.cachedConnection == null) {
         if (this.preferDataSource) {
            if (this.dataSource == null) {
               if (this.dataSourceName == null) {
                  throw new SQLException("You must call setDataSourceName and provide a valid DataSource JNDI name before attempting JDBC commands.");
               }

               this.lookupDataSource();
            }

            this.cachedConnection = this.dataSource.getConnection();
         } else {
            Properties info = new Properties();
            if (this.userName != null) {
               info.setProperty("user", this.userName);
            }

            if (this.password != null) {
               info.setProperty("password", this.password);
            }

            if (this.url != null && this.url.toLowerCase().matches(".*protocol\\s*=\\s*sdp.*")) {
               info.put("oracle.net.SDP", "true");
            }

            this.cachedConnection = DriverManager.getConnection(this.url, info);
         }
      }

      if (this.isolationLevel != -1) {
         this.cachedConnection.setTransactionIsolation(this.isolationLevel);
      }

      return this.cachedConnection;
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

   public void setTypeMap(Map m) {
      this.toDesign();
      this.typeMap = m;
   }

   public void clearParameters() {
      this.checkOp();
      this.params.clear();
   }

   public void setString(int i, String s) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 39, new Object[]{s}));
   }

   public void setBoolean(int i, boolean b) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 10, new Object[]{new Boolean(b)}));
   }

   public void setLong(int i, long l) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 23, new Object[]{new Long(l)}));
   }

   public void setInt(int i, int x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 22, new Object[]{new Integer(x)}));
   }

   public void setShort(int i, short s) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 37, new Object[]{new Short(s)}));
   }

   public void setDouble(int i, double d) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 20, new Object[]{new Double(d)}));
   }

   public void setFloat(int i, float f) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 21, new Object[]{new Float(f)}));
   }

   public void setBigDecimal(int i, BigDecimal bd) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 4, new Object[]{bd}));
   }

   public void setBinaryStream(int i, InputStream is, int x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 6, new Object[]{is, new Integer(x)}));
   }

   public void setByte(int i, byte b) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 11, new Object[]{new Byte(b)}));
   }

   public void setBytes(int i, byte[] b) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 12, new Object[]{b}));
   }

   public void setNull(int i, int x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 30, new Object[]{new Integer(x)}));
   }

   public void setNull(int i, int x, String s) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 31, new Object[]{new Integer(x), s}));
   }

   public void setObject(int i, Object o) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 32, new Object[]{o}));
   }

   public void setObject(int i, Object o, int x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 33, new Object[]{o, new Integer(x)}));
   }

   public void setObject(int i, Object o, int x, int scale) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 34, new Object[]{o, new Integer(x), new Integer(scale)}));
   }

   public void setDate(int i, Date d) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 18, new Object[]{d}));
   }

   public void setDate(int i, Date d, Calendar c) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 19, new Object[]{d, c}));
   }

   public void setTime(int i, Time t) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 40, new Object[]{t}));
   }

   public void setTime(int i, Time t, Calendar c) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 41, new Object[]{t, c}));
   }

   public void setTimestamp(int i, Timestamp t) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 42, new Object[]{t}));
   }

   public void setTimestamp(int i, Timestamp t, Calendar c) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 43, new Object[]{t, c}));
   }

   public void setAsciiStream(int i, InputStream is, int x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 3, new Object[]{is, new Integer(x)}));
   }

   public void setCharacterStream(int i, Reader r, int x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 14, new Object[]{r, new Integer(x)}));
   }

   public void setBlob(int i, Blob b) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 7, new Object[]{b}));
   }

   public void setClob(int i, Clob c) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 15, new Object[]{c}));
   }

   public void setArray(int i, Array x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 1, new Object[]{x}));
   }

   public void setRef(int i, Ref r) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, (String)null, 35, new Object[]{r}));
   }

   public int findColumn(String s) throws SQLException {
      return this.metaData.findColumn(s);
   }

   public boolean wasNull() {
      return this.wasNull;
   }

   public String getString(int i) throws SQLException {
      this.checkOp();

      try {
         Object obj = this.currentRow().getColumn(i);
         if (obj instanceof String) {
            this.wasNull = false;
            return (String)obj;
         } else if (obj == null) {
            this.wasNull = true;
            return null;
         } else {
            this.wasNull = false;
            return obj.toString().trim();
         }
      } catch (ClassCastException var3) {
         throw new SQLException("This column cannot be converted to a String");
      }
   }

   public String getString(String s) throws SQLException {
      return this.getString(this.findColumn(s));
   }

   public boolean getBoolean(int i) throws SQLException {
      this.checkOp();

      try {
         Object obj = this.currentRow().getColumn(i);
         if (obj instanceof Boolean) {
            this.wasNull = false;
            return (Boolean)obj;
         } else if (obj == null) {
            this.wasNull = true;
            return false;
         } else {
            this.wasNull = false;
            String val = obj.toString().trim().toLowerCase();
            if (val != null && !val.equals("0") && !val.equals("f") && !val.equals("false") && !val.equals("n") && !val.equals("no")) {
               if (!val.equals("1") && !val.equals("t") && !val.equals("true") && !val.equals("y") && !val.equals("yes")) {
                  throw new SQLException("This column cannot be converted to a boolean");
               } else {
                  return true;
               }
            } else {
               return false;
            }
         }
      } catch (ClassCastException var4) {
         throw new SQLException("This column cannot be converted to a boolean");
      } catch (NumberFormatException var5) {
         throw new SQLException("This column cannot be converted to a boolean");
      }
   }

   public boolean getBoolean(String s) throws SQLException {
      return this.getBoolean(this.findColumn(s));
   }

   public long getLong(int i) throws SQLException {
      this.checkOp();

      try {
         Object obj = this.currentRow().getColumn(i);
         if (obj instanceof Long) {
            this.wasNull = false;
            return (Long)obj;
         } else if (obj == null) {
            this.wasNull = true;
            return 0L;
         } else {
            this.wasNull = false;
            return Long.parseLong(obj.toString().trim());
         }
      } catch (ClassCastException var3) {
         throw new SQLException("This column cannot be converted to a long");
      } catch (NumberFormatException var4) {
         throw new SQLException("This column cannot be converted to a long");
      }
   }

   public long getLong(String s) throws SQLException {
      return this.getLong(this.findColumn(s));
   }

   public int getInt(int i) throws SQLException {
      this.checkOp();

      try {
         Object obj = this.currentRow().getColumn(i);
         if (obj instanceof Integer) {
            this.wasNull = false;
            return (Integer)obj;
         } else if (obj == null) {
            this.wasNull = true;
            return 0;
         } else {
            this.wasNull = false;
            return Integer.parseInt(obj.toString().trim());
         }
      } catch (ClassCastException var3) {
         throw new SQLException("This column cannot be converted to an int");
      } catch (NumberFormatException var4) {
         throw new SQLException("This column cannot be converted to an int");
      }
   }

   public int getInt(String s) throws SQLException {
      return this.getInt(this.findColumn(s));
   }

   public short getShort(int i) throws SQLException {
      this.checkOp();

      try {
         Object obj = this.currentRow().getColumn(i);
         if (obj instanceof Short) {
            this.wasNull = false;
            return (Short)obj;
         } else if (obj == null) {
            this.wasNull = true;
            return 0;
         } else {
            this.wasNull = false;
            return Short.parseShort(obj.toString().trim());
         }
      } catch (ClassCastException var3) {
         throw new SQLException("This column cannot be converted to a short");
      } catch (NumberFormatException var4) {
         throw new SQLException("This column cannot be converted to a short");
      }
   }

   public short getShort(String s) throws SQLException {
      return this.getShort(this.findColumn(s));
   }

   public byte getByte(int i) throws SQLException {
      this.checkOp();

      try {
         Object obj = this.currentRow().getColumn(i);
         if (obj instanceof Byte) {
            this.wasNull = false;
            return (Byte)obj;
         } else if (obj == null) {
            this.wasNull = true;
            return 0;
         } else {
            this.wasNull = false;
            return Byte.parseByte(obj.toString().trim());
         }
      } catch (ClassCastException var3) {
         throw new SQLException("This column cannot be converted to a byte");
      } catch (NumberFormatException var4) {
         throw new SQLException("This column cannot be converted to a byte");
      }
   }

   public byte getByte(String s) throws SQLException {
      return this.getByte(this.findColumn(s));
   }

   public double getDouble(int i) throws SQLException {
      this.checkOp();

      try {
         Object obj = this.currentRow().getColumn(i);
         if (obj instanceof Double) {
            this.wasNull = false;
            return (Double)obj;
         } else if (obj == null) {
            this.wasNull = true;
            return 0.0;
         } else {
            this.wasNull = false;
            return Double.parseDouble(obj.toString().trim());
         }
      } catch (ClassCastException var3) {
         throw new SQLException("This column cannot be converted to a double");
      } catch (NumberFormatException var4) {
         throw new SQLException("This column cannot be converted to a double");
      }
   }

   public double getDouble(String s) throws SQLException {
      return this.getDouble(this.findColumn(s));
   }

   public float getFloat(int i) throws SQLException {
      this.checkOp();

      try {
         Object obj = this.currentRow().getColumn(i);
         if (obj instanceof Float) {
            this.wasNull = false;
            return (Float)obj;
         } else if (obj == null) {
            this.wasNull = true;
            return 0.0F;
         } else {
            this.wasNull = false;
            return Float.parseFloat(obj.toString().trim());
         }
      } catch (ClassCastException var3) {
         throw new SQLException("This column cannot be converted to a float");
      } catch (NumberFormatException var4) {
         throw new SQLException("This column cannot be converted to a float");
      }
   }

   public float getFloat(String s) throws SQLException {
      return this.getFloat(this.findColumn(s));
   }

   public BigDecimal getBigDecimal(int i) throws SQLException {
      this.checkOp();

      try {
         Object obj = this.currentRow().getColumn(i);
         if (obj instanceof BigDecimal) {
            this.wasNull = false;
            return (BigDecimal)obj;
         } else if (obj == null) {
            this.wasNull = true;
            return null;
         } else {
            this.wasNull = false;
            return new BigDecimal(obj.toString().trim());
         }
      } catch (ClassCastException var3) {
         throw new SQLException("This column cannot be converted to a boolean");
      } catch (NumberFormatException var4) {
         throw new SQLException("This column cannot be converted to a boolean");
      }
   }

   public BigDecimal getBigDecimal(String s) throws SQLException {
      return this.getBigDecimal(this.findColumn(s));
   }

   public BigDecimal getBigDecimal(int i, int x) throws SQLException {
      BigDecimal bd = this.getBigDecimal(i);
      if (bd != null) {
         bd = bd.setScale(x);
      }

      return bd;
   }

   public BigDecimal getBigDecimal(String s, int n) throws SQLException {
      return this.getBigDecimal(this.findColumn(s), n);
   }

   public byte[] getBytes(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (obj instanceof byte[]) {
         this.wasNull = false;
         return (byte[])((byte[])obj);
      } else if (obj == null) {
         this.wasNull = true;
         return null;
      } else {
         throw new SQLException("This column cannot be converted to a byte[]");
      }
   }

   public byte[] getBytes(String s) throws SQLException {
      return this.getBytes(this.findColumn(s));
   }

   public Object getObject(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      this.wasNull = obj == null;
      return obj;
   }

   public Object getObject(String s) throws SQLException {
      return this.getObject(this.findColumn(s));
   }

   public Date getDate(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (obj instanceof Date) {
         this.wasNull = false;
         return (Date)obj;
      } else if (obj == null) {
         this.wasNull = true;
         return null;
      } else if (obj instanceof java.util.Date) {
         this.wasNull = false;
         return new Date(((java.util.Date)obj).getTime());
      } else {
         this.wasNull = false;

         try {
            java.util.Date d = DateFormat.getDateInstance().parse(obj.toString());
            return new Date(d.getTime());
         } catch (java.text.ParseException var4) {
            throw new SQLException(var4.getMessage());
         }
      }
   }

   public Date getDate(String s) throws SQLException {
      return this.getDate(this.findColumn(s));
   }

   public Time getTime(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (obj instanceof Time) {
         this.wasNull = false;
         return (Time)obj;
      } else if (obj == null) {
         this.wasNull = true;
         return null;
      } else if (obj instanceof java.util.Date) {
         this.wasNull = false;
         return new Time(((java.util.Date)obj).getTime());
      } else {
         try {
            java.util.Date d = DateFormat.getDateInstance().parse(obj.toString());
            return new Time(d.getTime());
         } catch (java.text.ParseException var4) {
            throw new SQLException(var4.getMessage());
         }
      }
   }

   public Time getTime(String s) throws SQLException {
      return this.getTime(this.findColumn(s));
   }

   public Timestamp getTimestamp(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (obj instanceof Timestamp) {
         this.wasNull = false;
         return (Timestamp)obj;
      } else if (obj == null) {
         this.wasNull = true;
         return null;
      } else if (obj instanceof java.util.Date) {
         this.wasNull = false;
         return new Timestamp(((java.util.Date)obj).getTime());
      } else {
         try {
            java.util.Date d = DateFormat.getDateInstance().parse(obj.toString());
            return new Timestamp(d.getTime());
         } catch (java.text.ParseException var4) {
            throw new SQLException(var4.getMessage());
         }
      }
   }

   public Timestamp getTimestamp(String s) throws SQLException {
      return this.getTimestamp(this.findColumn(s));
   }

   public Date getDate(int i, Calendar c) throws SQLException {
      if (c == null) {
         return this.getDate(i);
      } else {
         this.checkOp();
         Date d = this.getDate(i);
         if (d == null) {
            return null;
         } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.set(1, c.get(1));
            cal.set(2, c.get(2));
            cal.set(5, c.get(5));
            return new Date(cal.getTime().getTime());
         }
      }
   }

   public Date getDate(String s, Calendar c) throws SQLException {
      return this.getDate(this.findColumn(s), c);
   }

   public Time getTime(int i, Calendar c) throws SQLException {
      if (c == null) {
         return this.getTime(i);
      } else {
         this.checkOp();
         Time t = this.getTime(i);
         if (t == null) {
            return null;
         } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(t);
            cal.set(1, c.get(1));
            cal.set(2, c.get(2));
            cal.set(5, c.get(5));
            return new Time(cal.getTime().getTime());
         }
      }
   }

   public Time getTime(String s, Calendar c) throws SQLException {
      return this.getTime(this.findColumn(s), c);
   }

   public Timestamp getTimestamp(int i, Calendar c) throws SQLException {
      if (c == null) {
         return this.getTimestamp(i);
      } else {
         this.checkOp();
         Timestamp t = this.getTimestamp(i);
         if (t == null) {
            return null;
         } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(t);
            cal.set(1, c.get(1));
            cal.set(2, c.get(2));
            cal.set(5, c.get(5));
            return new Timestamp(cal.getTime().getTime());
         }
      }
   }

   public Timestamp getTimestamp(String s, Calendar c) throws SQLException {
      return this.getTimestamp(this.findColumn(s), c);
   }

   public InputStream getBinaryStream(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (obj instanceof byte[]) {
         this.wasNull = false;
         return new ByteArrayInputStream((byte[])((byte[])obj));
      } else if (obj == null) {
         this.wasNull = true;
         return null;
      } else {
         throw new SQLException("This column cannot be converted to a BinaryStream");
      }
   }

   public InputStream getBinaryStream(String s) throws SQLException {
      return this.getBinaryStream(this.findColumn(s));
   }

   public Reader getCharacterStream(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (obj instanceof char[]) {
         this.wasNull = false;
         return new CharArrayReader((char[])((char[])obj));
      } else if (obj == null) {
         this.wasNull = true;
         return null;
      } else {
         this.wasNull = false;
         return new StringReader(obj.toString());
      }
   }

   public Reader getCharacterStream(String s) throws SQLException {
      return this.getCharacterStream(this.findColumn(s));
   }

   public Blob getBlob(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      this.wasNull = obj == null;
      if (!this.wasNull && obj instanceof Blob) {
         return (Blob)obj;
      } else if (obj instanceof String) {
         return new RowSetBlob(((String)obj).getBytes());
      } else if (obj instanceof byte[]) {
         return new RowSetBlob((byte[])((byte[])obj));
      } else {
         this.currentRow().getColumn(i).getClass();
         throw new SQLException("This column values are " + obj.getClass().getName() + " by default. This column cannot be converted to a java.sql.Blob");
      }
   }

   public Blob getBlob(String s) throws SQLException {
      return this.getBlob(this.findColumn(s));
   }

   public Clob getClob(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (obj instanceof Clob) {
         return (Clob)obj;
      } else if (obj instanceof String) {
         return new RowSetClob(obj.toString());
      } else {
         throw new SQLException("This column values are " + obj.getClass().getName() + " by default. This column cannot be converted to a java.sql.Clob");
      }
   }

   public Object getObject(int i, Map m) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      return obj;
   }

   public Object getObject(String s, Map m) throws SQLException {
      return this.getObject(this.findColumn(s), m);
   }

   public InputStream getAsciiStream(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (obj instanceof byte[]) {
         this.wasNull = false;

         try {
            return new ByteArrayInputStream((new String((byte[])((byte[])obj))).getBytes("US-ASCII"));
         } catch (UnsupportedEncodingException var4) {
            throw new SQLException(var4.toString());
         }
      } else if (obj instanceof char[]) {
         this.wasNull = false;

         try {
            return new ByteArrayInputStream((new String((char[])((char[])obj))).getBytes("US-ASCII"));
         } catch (UnsupportedEncodingException var5) {
            throw new SQLException(var5.toString());
         }
      } else if (obj == null) {
         this.wasNull = true;
         return null;
      } else {
         throw new SQLException("This column cannot be converted to a AsciiStream");
      }
   }

   public InputStream getAsciiStream(String s) throws SQLException {
      return this.getAsciiStream(this.findColumn(s));
   }

   public InputStream getUnicodeStream(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (obj instanceof char[]) {
         this.wasNull = false;
         return new ByteArrayInputStream((new String((char[])((char[])obj))).getBytes());
      } else if (obj == null) {
         this.wasNull = true;
         return null;
      } else {
         this.wasNull = false;
         return new ByteArrayInputStream(obj.toString().getBytes());
      }
   }

   public InputStream getUnicodeStream(String s) throws SQLException {
      return this.getUnicodeStream(this.findColumn(s));
   }

   public Clob getClob(String s) throws SQLException {
      return this.getClob(this.findColumn(s));
   }

   public Array getArray(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (!(obj instanceof Array) && !(obj instanceof SerialArray)) {
         throw new SQLException("This column cannot be converted to a Array");
      } else {
         this.wasNull = false;
         return (Array)obj;
      }
   }

   public Array getArray(String s) throws SQLException {
      return this.getArray(this.findColumn(s));
   }

   public Ref getRef(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (!(obj instanceof Ref) && !(obj instanceof SerialRef)) {
         if (obj == null) {
            this.wasNull = true;
            return null;
         } else {
            throw new SQLException("This column cannot be converted to a Ref");
         }
      } else {
         this.wasNull = false;
         return (Ref)obj;
      }
   }

   public Ref getRef(String s) throws SQLException {
      return this.getRef(this.findColumn(s));
   }

   public URL getURL(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (obj instanceof URL) {
         this.wasNull = false;
         return (URL)obj;
      } else if (obj instanceof SerialDatalink) {
         this.wasNull = false;
         return ((SerialDatalink)obj).getDatalink();
      } else {
         throw new SQLException("This column cannot be converted to a URL");
      }
   }

   public URL getURL(String s) throws SQLException {
      return this.getURL(this.findColumn(s));
   }

   abstract void updateCurrent(int var1, Object var2) throws SQLException;

   public void updateNull(int i) throws SQLException {
      this.updateCurrent(i, (Object)null);
   }

   public void updateNull(String s) throws SQLException {
      this.updateNull(this.findColumn(s));
   }

   public void updateObject(int i, Object o) throws SQLException {
      this.updateCurrent(i, o);
   }

   public void updateObject(String s, Object o) throws SQLException {
      this.updateObject(this.findColumn(s), o);
   }

   public void updateObject(int i, Object o, int x) throws SQLException {
      this.metaData.setColumnType(i, x);
      this.updateCurrent(i, o);
   }

   public void updateObject(String s, Object o, int x) throws SQLException {
      this.updateObject(this.findColumn(s), o, x);
   }

   public void updateString(int i, String s) throws SQLException {
      this.updateCurrent(i, s);
   }

   public void updateString(String s, String n) throws SQLException {
      this.updateString(this.findColumn(s), n);
   }

   public void updateBoolean(int i, boolean b) throws SQLException {
      this.updateCurrent(i, new Boolean(b));
   }

   public void updateBoolean(String s, boolean b) throws SQLException {
      this.updateBoolean(this.findColumn(s), b);
   }

   public void updateLong(int i, long l) throws SQLException {
      this.updateCurrent(i, new Long(l));
   }

   public void updateLong(String s, long l) throws SQLException {
      this.updateLong(this.findColumn(s), l);
   }

   public void updateInt(int i, int x) throws SQLException {
      this.updateCurrent(i, new Integer(x));
   }

   public void updateInt(String s, int x) throws SQLException {
      this.updateInt(this.findColumn(s), x);
   }

   public void updateShort(int i, short s) throws SQLException {
      this.updateCurrent(i, new Short(s));
   }

   public void updateShort(String s, short x) throws SQLException {
      this.updateShort(this.findColumn(s), x);
   }

   public void updateByte(int i, byte b) throws SQLException {
      this.updateCurrent(i, new Byte(b));
   }

   public void updateByte(String s, byte b) throws SQLException {
      this.updateByte(this.findColumn(s), b);
   }

   public void updateDouble(int i, double d) throws SQLException {
      this.updateCurrent(i, new Double(d));
   }

   public void updateDouble(String s, double d) throws SQLException {
      this.updateDouble(this.findColumn(s), d);
   }

   public void updateFloat(int i, float f) throws SQLException {
      this.updateCurrent(i, new Float(f));
   }

   public void updateFloat(String s, float f) throws SQLException {
      this.updateFloat(this.findColumn(s), f);
   }

   public void updateBytes(int i, byte[] b) throws SQLException {
      this.updateCurrent(i, b);
   }

   public void updateBytes(String s, byte[] b) throws SQLException {
      this.updateBytes(this.findColumn(s), b);
   }

   public void updateBigDecimal(int i, BigDecimal bd) throws SQLException {
      this.updateCurrent(i, bd);
   }

   public void updateBigDecimal(String s, BigDecimal bd) throws SQLException {
      this.updateBigDecimal(this.findColumn(s), bd);
   }

   public void updateDate(int i, Date d) throws SQLException {
      this.updateCurrent(i, d);
   }

   public void updateDate(String s, Date d) throws SQLException {
      this.updateDate(this.findColumn(s), d);
   }

   public void updateTime(int i, Time t) throws SQLException {
      this.updateCurrent(i, t);
   }

   public void updateTime(String s, Time t) throws SQLException {
      this.updateTime(this.findColumn(s), t);
   }

   public void updateTimestamp(int i, Timestamp t) throws SQLException {
      this.updateCurrent(i, t);
   }

   public void updateTimestamp(String s, Timestamp t) throws SQLException {
      this.updateTimestamp(this.findColumn(s), t);
   }

   private byte[] drain(InputStream is, byte[] b) throws SQLException {
      int i = 0;

      while(i < b.length) {
         try {
            int n = is.read(b, i, b.length - i);
            if (n == -1) {
               break;
            }

            i += n;
         } catch (IOException var5) {
            JDBCLogger.logStackTrace(var5);
            throw new SQLException(var5.getMessage());
         }
      }

      return b;
   }

   private char[] drain(Reader r, char[] buf) throws SQLException {
      int i = 0;

      while(i < buf.length) {
         try {
            int n = r.read(buf, i, buf.length - i);
            if (n == -1) {
               break;
            }

            i += n;
         } catch (IOException var5) {
            JDBCLogger.logStackTrace(var5);
            throw new SQLException(var5.getMessage());
         }
      }

      return buf;
   }

   private byte[] drain(InputStream is) throws SQLException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] buf = new byte[8192];

      try {
         while(true) {
            int n = is.read(buf);
            if (n == -1) {
               byte[] var15 = baos.toByteArray();
               return var15;
            }

            baos.write(buf, 0, n);
         }
      } catch (IOException var13) {
         JDBCLogger.logStackTrace(var13);
         throw new SQLException(var13.getMessage());
      } finally {
         try {
            baos.close();
         } catch (IOException var12) {
         }

      }
   }

   private char[] drain(Reader r) throws SQLException {
      CharArrayWriter caw = new CharArrayWriter();
      char[] cbuf = new char[8192];

      try {
         while(true) {
            int n = r.read(cbuf);
            if (n == -1) {
               char[] var10 = caw.toCharArray();
               return var10;
            }

            caw.write(cbuf, 0, n);
         }
      } catch (IOException var8) {
         JDBCLogger.logStackTrace(var8);
         throw new SQLException(var8.getMessage());
      } finally {
         caw.close();
      }
   }

   private String drainContent(InputStream is) throws SQLException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] buf = new byte[8192];

      try {
         while(true) {
            int n = is.read(buf);
            if (n == -1) {
               String var15 = baos.toString();
               return var15;
            }

            baos.write(buf, 0, n);
         }
      } catch (IOException var13) {
         JDBCLogger.logStackTrace(var13);
         throw new SQLException(var13.getMessage());
      } finally {
         try {
            baos.close();
         } catch (IOException var12) {
         }

      }
   }

   public void updateAsciiStream(int i, InputStream is, int n) throws SQLException {
      if (is == null) {
         throw new SQLException("updateAsciiStream's InputStream was null");
      } else if (n <= 0) {
         throw new SQLException("updateAsciiStream's length must be > 0");
      } else {
         this.updateCurrent(i, new String(this.drain(is, new byte[n]), 0, n));
      }
   }

   public void updateAsciiStream(String s, InputStream is, int x) throws SQLException {
      this.updateAsciiStream(this.findColumn(s), is, x);
   }

   public void updateBinaryStream(int i, InputStream is, int n) throws SQLException {
      if (is == null) {
         throw new SQLException("updateBinaryStream's InputStream was null");
      } else if (n <= 0) {
         throw new SQLException("updateBinaryStream's length must be > 0");
      } else {
         this.updateCurrent(i, this.drain(is, new byte[n]));
      }
   }

   public void updateBinaryStream(String s, InputStream is, int x) throws SQLException {
      this.updateBinaryStream(this.findColumn(s), is, x);
   }

   public void updateCharacterStream(int i, Reader r, int n) throws SQLException {
      if (r == null) {
         throw new SQLException("updateCharacterStream's reader was null");
      } else if (n <= 0) {
         throw new SQLException("updateCharacterStream's length must be > 0");
      } else {
         this.updateCurrent(i, this.drain(r, new char[n]));
      }
   }

   public void updateCharacterStream(String s, Reader r, int x) throws SQLException {
      this.updateCharacterStream(this.findColumn(s), r, x);
   }

   public void updateBlob(int a, Blob b) throws SQLException {
      this.updateCurrent(a, b);
   }

   public void updateBlob(String s, Blob b) throws SQLException {
      this.updateBlob(this.findColumn(s), b);
   }

   public void updateClob(int a, Clob b) throws SQLException {
      this.updateCurrent(a, b);
   }

   public void updateClob(String s, Clob b) throws SQLException {
      this.updateClob(this.findColumn(s), b);
   }

   public void updateRef(int a, Ref b) throws SQLException {
      this.updateCurrent(a, b);
   }

   public void updateRef(String s, Ref b) throws SQLException {
      this.updateRef(this.findColumn(s), b);
   }

   public void updateArray(int a, Array b) throws SQLException {
      this.updateCurrent(a, b);
   }

   public void updateArray(String s, Array b) throws SQLException {
      this.updateArray(this.findColumn(s), b);
   }

   public void addRowSetListener(RowSetListener rsl) {
      this.rowSetListeners.add(rsl);
   }

   public void removeRowSetListener(RowSetListener rsl) {
      this.rowSetListeners.remove(rsl);
   }

   public SyncProvider getSyncProvider() throws SQLException {
      return this.provider;
   }

   public void setSyncProvider(String providerName) throws SQLException {
      if (providerName == null) {
         this.provider = new WLSyncProvider();
      } else {
         this.provider = SyncFactory.getInstance(providerName);
         if (this.provider == null) {
            throw new SQLException("Can not find SyncProvider for " + providerName + ".");
         }
      }

      this.reader = this.provider.getRowSetReader();
      this.writer = this.provider.getRowSetWriter();
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
      this.checkOp();
      this.metaData.setMatchColumn(columnIdx, true);
   }

   public void setMatchColumn(String columnName) throws SQLException {
      this.metaData.setMatchColumn(this.findColumn(columnName), true);
   }

   public void setMatchColumn(int[] columnIdxes) throws SQLException {
      this.checkOp();

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
      this.checkOp();
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

   abstract CachedRow currentRow() throws SQLException;

   public Object[] getParams() throws SQLException {
      Object[] ret = new Object[this.params.size()];

      for(int i = 0; i < this.params.size(); ++i) {
         ret[i] = ((WLParameter)this.params.get(i)).getObject();
      }

      return ret;
   }

   public boolean isComplete() {
      return this.isComplete;
   }

   public void setIsComplete(boolean b) {
      this.checkOp();
      this.isComplete = b;
   }

   ArrayList getParameters() {
      return this.params;
   }

   public void setWriter(RowSetWriter w) {
      this.writer = w;
   }

   public void setReadOnlyInternal(boolean b) {
      this.checkOp();
      this.metaData.setReadOnly(b);
   }

   void cursorMoved() {
      RowSetEvent event = new RowSetEvent((RowSet)this);
      Iterator it = this.rowSetListeners.iterator();

      while(it.hasNext()) {
         RowSetListener l = (RowSetListener)it.next();
         l.cursorMoved(event);
      }

   }

   void rowChanged() {
      RowSetEvent event = new RowSetEvent((RowSet)this);
      Iterator it = this.rowSetListeners.iterator();

      while(it.hasNext()) {
         RowSetListener l = (RowSetListener)it.next();
         l.rowChanged(event);
      }

   }

   void rowSetChanged() {
      RowSetEvent event = new RowSetEvent((RowSet)this);
      Iterator it = this.rowSetListeners.iterator();

      while(it.hasNext()) {
         RowSetListener l = (RowSetListener)it.next();
         l.rowSetChanged(event);
      }

   }

   protected void setIsClosed(boolean value) {
      this.isClosed = value;
   }

   void keepRowIndexValid() {
      if (this.rowIndex < -1) {
         this.rowIndex = -1;
      }

      if (this.rowIndex > this.size()) {
         this.rowIndex = this.size();
      }

   }

   void checkOp() {
      if (this.locked) {
         throw new RuntimeException("This operation is disabled because there are open SharedRowSet objects associated with this CachedRowSet object. Please call SharedRowSet.close() to detach it.");
      }
   }

   void checkOp(int s) {
      this.checkOp();
      this.state = this.state.checkOp(s);
   }

   void checkIterator() throws SQLException {
      this.checkOp(4);
   }

   void toConfigQuery() {
      this.checkOp(1);
   }

   void toDesign() {
      this.checkOp(0);
   }

   void isJoinable(Class c) throws SQLException {
      if (!Number.class.isAssignableFrom(c) && !Boolean.class.isAssignableFrom(c) && !java.util.Date.class.isAssignableFrom(c) && !String.class.isAssignableFrom(c)) {
         throw new SQLException(c + " is not a supported Joinable data type");
      }
   }

   void isJoinable(Class left, Class right) throws SQLException {
      if (Number.class.isAssignableFrom(left)) {
         if (!Boolean.class.isAssignableFrom(right) && !java.util.Date.class.isAssignableFrom(right)) {
            if (!Number.class.isAssignableFrom(right) && !String.class.isAssignableFrom(right)) {
               throw new SQLException(right + " is not a supported Joinable data type");
            }
         } else {
            throw new SQLException(right + " can not be joined with " + left);
         }
      } else if (Boolean.class.isAssignableFrom(left)) {
         if (!Number.class.isAssignableFrom(right) && !java.util.Date.class.isAssignableFrom(right)) {
            if (!Boolean.class.isAssignableFrom(right) && !String.class.isAssignableFrom(right)) {
               throw new SQLException(right + " is not a supported Joinable data type");
            }
         } else {
            throw new SQLException(right + " can not be compared with " + left);
         }
      } else if (java.util.Date.class.isAssignableFrom(left)) {
         if (!Number.class.isAssignableFrom(right) && !Boolean.class.isAssignableFrom(right)) {
            if (!java.util.Date.class.isAssignableFrom(right) && !String.class.isAssignableFrom(right)) {
               throw new SQLException(right + " is not a supported Joinable data type");
            }
         } else {
            throw new SQLException(right + " can not be compared with " + left);
         }
      } else if (!String.class.isAssignableFrom(left)) {
         throw new SQLException(left + " is not a supported Joinable data type");
      }
   }

   public RowId getRowId(int columnIndex) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(columnIndex);
      if (obj instanceof RowId) {
         this.wasNull = false;
         return (RowId)obj;
      } else {
         throw new SQLException("This column cannot be converted to a RowId");
      }
   }

   public RowId getRowId(String columnName) throws SQLException {
      return this.getRowId(this.findColumn(columnName));
   }

   public int getHoldability() throws SQLException {
      return 1;
   }

   public boolean isClosed() throws SQLException {
      return this.isClosed;
   }

   public NClob getNClob(int i) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(i);
      if (obj instanceof NClob) {
         this.wasNull = false;
         return (NClob)obj;
      } else if (obj instanceof String) {
         return new RowSetNClob(obj.toString());
      } else {
         throw new SQLException("This column values are " + obj.getClass().getName() + " by default. This column cannot be converted to a NClob");
      }
   }

   public NClob getNClob(String colName) throws SQLException {
      return this.getNClob(this.findColumn(colName));
   }

   public SQLXML getSQLXML(int columnIndex) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(columnIndex);
      if (obj instanceof SQLXML) {
         this.wasNull = false;
         return (SQLXML)obj;
      } else {
         throw new SQLException("This column cannot be converted to a SQLXML");
      }
   }

   public Reader getNCharacterStream(int columnIndex) throws SQLException {
      return this.getCharacterStream(columnIndex);
   }

   public Reader getNCharacterStream(String colName) throws SQLException {
      return this.getNCharacterStream(this.findColumn(colName));
   }

   public String getNString(int columnIndex) throws SQLException {
      this.checkOp();
      Object obj = this.currentRow().getColumn(columnIndex);
      if (obj instanceof String) {
         this.wasNull = false;
         return (String)obj;
      } else {
         throw new SQLException("This column cannot be converted to a String");
      }
   }

   public String getNString(String colName) throws SQLException {
      return this.getNString(this.findColumn(colName));
   }

   public SQLXML getSQLXML(String colName) throws SQLException {
      return this.getSQLXML(this.findColumn(colName));
   }

   public void updateRowId(int columnIndex, RowId x) throws SQLException {
      throw new SQLException("RowId is only valid within the transaction it is created.");
   }

   public void updateRowId(String columnName, RowId x) throws SQLException {
      throw new SQLException("RowId is only valid within the transaction it is created.");
   }

   public void updateNString(int columnIndex, String nString) throws SQLException {
      this.updateCurrent(columnIndex, nString);
   }

   public void updateNString(String columnName, String nString) throws SQLException {
      this.updateNString(this.findColumn(columnName), nString);
   }

   public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
      this.updateClob(columnIndex, (Clob)nClob);
   }

   public void updateNClob(String columnName, NClob nClob) throws SQLException {
      this.updateNClob(this.findColumn(columnName), nClob);
   }

   public void updateSQLXML(int columnIndex, SQLXML o) throws SQLException {
      this.updateCurrent(columnIndex, o);
   }

   public void updateSQLXML(String columnName, SQLXML o) throws SQLException {
      this.updateSQLXML(this.findColumn(columnName), o);
   }

   public void updateNCharacterStream(int i, Reader r, int n) throws SQLException {
      if (r == null) {
         throw new SQLException("updateCharacterStream's reader was null");
      } else if (n <= 0) {
         throw new SQLException("updateCharacterStream's length must be > 0");
      } else {
         this.updateCurrent(i, this.drain(r, new char[n]));
      }
   }

   public void updateNCharacterStream(String s, Reader r, int n) throws SQLException {
      this.updateNCharacterStream(this.findColumn(s), r, n);
   }

   public void setNClob(int i, NClob c) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, 26, new Object[]{c}));
   }

   public void setNCharacterStream(int i, Reader r, long x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, 25, new Object[]{r, x}));
   }

   public void setNString(int i, String s) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, 29, new Object[]{s}));
   }

   public void setSQLXML(int i, SQLXML sqlxml) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, 38, new Object[]{sqlxml}));
   }

   public void setRowId(int i, RowId rowid) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(i, 36, new Object[]{rowid}));
   }

   public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 38, new Object[]{xmlObject}));
   }

   public void setRowId(String parameterName, RowId x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 36, new Object[]{x}));
   }

   public void setNString(String parameterName, String value) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 29, new Object[]{value}));
   }

   public void setNClob(String parameterName, NClob value) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 26, new Object[]{value}));
   }

   public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 25, new Object[]{value, new Long(length)}));
   }

   public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
      if (x == null) {
         throw new SQLException("updateAsciiStream's InputStream was null");
      } else {
         this.updateCurrent(columnIndex, this.drainContent(x));
      }
   }

   public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
      if (length > 2147483647L) {
         throw new SQLException("updateAsciiStream's length must be <= 2147483647");
      } else {
         this.updateAsciiStream(columnIndex, x, (int)length);
      }
   }

   public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
      this.updateAsciiStream(this.findColumn(columnLabel), x);
   }

   public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
      this.updateAsciiStream(this.findColumn(columnLabel), x, length);
   }

   public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
      if (x == null) {
         throw new SQLException("updateBinaryStream's InputStream was null");
      } else {
         this.updateCurrent(columnIndex, this.drain(x));
      }
   }

   public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
      if (length > 2147483647L) {
         throw new SQLException("updateBinaryStream's length must be <= 2147483647");
      } else {
         this.updateBinaryStream(columnIndex, x, (int)length);
      }
   }

   public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
      this.updateBinaryStream(this.findColumn(columnLabel), x);
   }

   public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
      this.updateBinaryStream(this.findColumn(columnLabel), x, length);
   }

   public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
      this.updateBinaryStream(columnIndex, inputStream);
   }

   public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
      this.updateBinaryStream(columnIndex, inputStream, length);
   }

   public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
      this.updateBlob(this.findColumn(columnLabel), inputStream);
   }

   public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
      this.updateBlob(this.findColumn(columnLabel), inputStream, length);
   }

   public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
      if (x == null) {
         throw new SQLException("updateCharacterStream's reader was null");
      } else {
         this.updateCurrent(columnIndex, this.drain(x));
      }
   }

   public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
      if (length > 2147483647L) {
         throw new SQLException("updateCharacterStream's length must be <= 2147483647");
      } else {
         this.updateCharacterStream(columnIndex, x, (int)length);
      }
   }

   public void updateCharacterStream(String columnLabel, Reader x) throws SQLException {
      this.updateCharacterStream(this.findColumn(columnLabel), x);
   }

   public void updateCharacterStream(String columnLabel, Reader x, long length) throws SQLException {
      this.updateCharacterStream(this.findColumn(columnLabel), x, length);
   }

   public void updateClob(int columnIndex, Reader reader) throws SQLException {
      this.updateCharacterStream(columnIndex, reader);
   }

   public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
      this.updateCharacterStream(columnIndex, reader, length);
   }

   public void updateClob(String columnLabel, Reader x) throws SQLException {
      this.updateClob(this.findColumn(columnLabel), x);
   }

   public void updateClob(String columnLabel, Reader x, long length) throws SQLException {
      this.updateClob(this.findColumn(columnLabel), x, length);
   }

   public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
      this.updateCharacterStream(columnIndex, x);
   }

   public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
      this.updateCharacterStream(columnIndex, x, length);
   }

   public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
      this.updateNCharacterStream(this.findColumn(columnLabel), reader);
   }

   public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
      this.updateNCharacterStream(this.findColumn(columnLabel), reader, length);
   }

   public void updateNClob(int columnIndex, Reader reader) throws SQLException {
      this.updateClob(columnIndex, reader);
   }

   public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
      this.updateClob(columnIndex, reader, length);
   }

   public void updateNClob(String columnLabel, Reader reader) throws SQLException {
      this.updateNClob(this.findColumn(columnLabel), reader);
   }

   public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
      this.updateNClob(this.findColumn(columnLabel), reader, length);
   }

   public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, (String)null, 2, new Object[]{x}));
   }

   public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 2, new Object[]{x}));
   }

   public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 3, new Object[]{x, new Integer(length)}));
   }

   public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 4, new Object[]{x}));
   }

   public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, (String)null, 5, new Object[]{x}));
   }

   public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterName, 5, new Object[]{x}));
   }

   public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 6, new Object[]{x, new Integer(length)}));
   }

   public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, (String)null, 8, new Object[]{inputStream}));
   }

   public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, (String)null, 9, new Object[]{inputStream, new Long(length)}));
   }

   public void setBlob(String parameterName, Blob x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 7, new Object[]{x}));
   }

   public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 8, new Object[]{inputStream}));
   }

   public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 9, new Object[]{inputStream, new Long(length)}));
   }

   public void setBoolean(String parameterName, boolean x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 10, new Object[]{new Boolean(x)}));
   }

   public void setByte(String parameterName, byte x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 11, new Object[]{new Byte(x)}));
   }

   public void setBytes(String parameterName, byte[] x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 12, new Object[]{x}));
   }

   public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, (String)null, 13, new Object[]{reader}));
   }

   public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 13, new Object[]{reader}));
   }

   public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 14, new Object[]{reader, new Integer(length)}));
   }

   public void setClob(int parameterIndex, Reader reader) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, (String)null, 16, new Object[]{reader}));
   }

   public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, (String)null, 17, new Object[]{reader, new Long(length)}));
   }

   public void setClob(String parameterName, Clob x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 15, new Object[]{x}));
   }

   public void setClob(String parameterName, Reader reader) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 16, new Object[]{reader}));
   }

   public void setClob(String parameterName, Reader reader, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 17, new Object[]{reader, new Long(length)}));
   }

   public void setDate(String parameterName, Date x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 18, new Object[]{x}));
   }

   public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 19, new Object[]{x, cal}));
   }

   public void setDouble(String parameterName, double x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 20, new Object[]{new Double(x)}));
   }

   public void setFloat(String parameterName, float x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 21, new Object[]{new Float(x)}));
   }

   public void setInt(String parameterName, int x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 22, new Object[]{new Integer(x)}));
   }

   public void setLong(String parameterName, long x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 23, new Object[]{new Long(x)}));
   }

   public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, (String)null, 24, new Object[]{value}));
   }

   public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 24, new Object[]{value}));
   }

   public void setNClob(int parameterIndex, Reader reader) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, (String)null, 27, new Object[]{reader}));
   }

   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, (String)null, 28, new Object[]{reader, new Long(length)}));
   }

   public void setNClob(String parameterName, Reader reader) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 27, new Object[]{reader}));
   }

   public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 28, new Object[]{reader, new Long(length)}));
   }

   public void setNull(String parameterName, int sqlType) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 30, new Object[]{new Integer(sqlType)}));
   }

   public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 31, new Object[]{new Integer(sqlType), typeName}));
   }

   public void setObject(String parameterName, Object x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 32, new Object[]{x}));
   }

   public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 33, new Object[]{x, new Integer(targetSqlType)}));
   }

   public void setObject(String parameterName, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 34, new Object[]{x, new Integer(targetSqlType), new Integer(scaleOrLength)}));
   }

   public void setShort(String parameterName, short x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 37, new Object[]{new Short(x)}));
   }

   public void setString(String parameterName, String x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 39, new Object[]{x}));
   }

   public void setTime(String parameterName, Time x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 40, new Object[]{x}));
   }

   public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 41, new Object[]{x, cal}));
   }

   public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 42, new Object[]{x}));
   }

   public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(0, parameterName, 43, new Object[]{x, cal}));
   }

   public void setURL(int parameterIndex, URL x) throws SQLException {
      this.toConfigQuery();
      this.params.add(new WLParameter(parameterIndex, (String)null, 44, new Object[]{x}));
   }
}
