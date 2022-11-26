package weblogic.jdbc.rmi;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.rmi.internal.ResultSetCachingReader;
import weblogic.jdbc.rmi.internal.ResultSetReader;
import weblogic.jdbc.rmi.internal.ResultSetStraightReader;
import weblogic.jdbc.rmi.internal.ResultSetStub;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class SerialResultSet extends RMIStubWrapperImpl implements Serializable {
   private static final long serialVersionUID = -2720653455103793350L;
   private ResultSet rmi_rs = null;
   private ResultSetReader rs_reader = null;
   private transient SerialStatement parent_stmt = null;
   private boolean closed = false;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         return null;
      } else {
         try {
            if (ret instanceof Blob) {
               return SerialOracleBlob.makeSerialOracleBlob((Blob)ret);
            }

            if (ret instanceof Clob) {
               return SerialOracleClob.makeSerialOracleClob((Clob)ret);
            }

            if (ret instanceof NClob) {
               return SerialOracleNClob.makeSerialOracleNClob((NClob)ret);
            }

            if (ret instanceof Array) {
               return SerialArray.makeSerialArrayFromStub((Array)ret);
            }
         } catch (Exception var5) {
            JDBCLogger.logStackTrace(var5);
         }

         return ret;
      }
   }

   public void init(ResultSet rs, SerialStatement stmt) {
      this.parent_stmt = stmt;
      this.rmi_rs = rs;
      this.closed = false;

      try {
         if (this.rmi_rs instanceof ResultSetStub) {
            ResultSetStub int_rs = (ResultSetStub)this.rmi_rs;
            if (int_rs.isRowCaching()) {
               this.rs_reader = new ResultSetCachingReader(int_rs);
               return;
            }
         }
      } catch (Exception var4) {
      }

      this.rs_reader = new ResultSetStraightReader(rs);
   }

   public static ResultSet makeSerialResultSet(ResultSet rs, SerialStatement stmt) {
      if (rs == null) {
         return null;
      } else {
         SerialResultSet rmi_rs = (SerialResultSet)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialResultSet", rs, false);
         rmi_rs.init(rs, stmt);
         if (stmt != null) {
            stmt.addResultSet(rmi_rs);
         }

         return (ResultSet)rmi_rs;
      }
   }

   public boolean next() throws SQLException {
      boolean ret = false;
      String methodName = "next";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.next();
         this.postInvocationHandler(methodName, params, new Boolean(ret));
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public void close() throws SQLException {
      String methodName = "close";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         this.close(true);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var4) {
         this.invocationExceptionHandler(methodName, params, var4);
      }

   }

   void close(boolean notify_parent) throws SQLException {
      try {
         if (!this.closed) {
            if (notify_parent && this.parent_stmt != null) {
               this.parent_stmt.removeResultSet(this);
            }

            this.rmi_rs.close();
            this.closed = true;
         }

      } catch (Exception var4) {
         if (var4 instanceof SQLException) {
            throw (SQLException)var4;
         } else {
            Throwable t = (new SQLException(var4.toString())).initCause(var4);
            throw (SQLException)t;
         }
      }
   }

   public boolean wasNull() throws SQLException {
      boolean ret = false;
      String methodName = "wasNull";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.wasNull();
         this.postInvocationHandler(methodName, params, new Boolean(ret));
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public String getString(int columnIndex) throws SQLException {
      String ret = null;
      String methodName = "getString";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getString(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public boolean getBoolean(int columnIndex) throws SQLException {
      boolean ret = false;
      String methodName = "getBoolean";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getBoolean(columnIndex);
         this.postInvocationHandler(methodName, params, new Boolean(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public byte getByte(int columnIndex) throws SQLException {
      byte ret = 0;
      String methodName = "getbyte";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getByte(columnIndex);
         this.postInvocationHandler(methodName, params, new Byte(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public short getShort(int columnIndex) throws SQLException {
      short ret = 0;
      String methodName = "getShort";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getShort(columnIndex);
         this.postInvocationHandler(methodName, params, new Short(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public int getInt(int columnIndex) throws SQLException {
      int ret = 0;
      String methodName = "getInt";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getInt(columnIndex);
         this.postInvocationHandler(methodName, params, new Integer(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public long getLong(int columnIndex) throws SQLException {
      long ret = 0L;
      String methodName = "getLong";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getLong(columnIndex);
         this.postInvocationHandler(methodName, params, new Long(ret));
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public float getFloat(int columnIndex) throws SQLException {
      float ret = 0.0F;
      String methodName = "getFloat";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getFloat(columnIndex);
         this.postInvocationHandler(methodName, params, new Float(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public double getDouble(int columnIndex) throws SQLException {
      double ret = 0.0;
      String methodName = "getDouble";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getDouble(columnIndex);
         this.postInvocationHandler(methodName, params, new Double(ret));
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
      BigDecimal ret = null;
      String methodName = "getBigDecimal";
      Object[] params = new Object[]{new Integer(columnIndex), new Integer(scale)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getBigDecimal(columnIndex, scale);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public byte[] getBytes(int columnIndex) throws SQLException {
      byte[] ret = null;
      String methodName = "getBytes";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getBytes(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Date getDate(int columnIndex) throws SQLException {
      Date ret = null;
      String methodName = "getDate";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getDate(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Time getTime(int columnIndex) throws SQLException {
      Time ret = null;
      String methodName = "getTime";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getTime(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Timestamp getTimestamp(int columnIndex) throws SQLException {
      Timestamp ret = null;
      String methodName = "getTimestamp";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getTimestamp(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public InputStream getAsciiStream(int columnIndex) throws SQLException {
      InputStream ret = null;
      String methodName = "getAsciiStream";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getAsciiStream(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public InputStream getUnicodeStream(int columnIndex) throws SQLException {
      InputStream ret = null;
      String methodName = "getUnicodeStream";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getUnicodeStream(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public InputStream getBinaryStream(int columnIndex) throws SQLException {
      InputStream ret = null;
      String methodName = "getBinaryStream";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getBinaryStream(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public String getString(String columnName) throws SQLException {
      String ret = null;
      String methodName = "getString";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getString(columnName);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public boolean getBoolean(String columnName) throws SQLException {
      boolean ret = false;
      String methodName = "getBoolean";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getBoolean(columnName);
         this.postInvocationHandler(methodName, params, new Boolean(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public byte getByte(String columnName) throws SQLException {
      byte ret = 0;
      String methodName = "getByte";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getByte(columnName);
         this.postInvocationHandler(methodName, params, new Byte(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public short getShort(String columnName) throws SQLException {
      short ret = 0;
      String methodName = "getShort";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getShort(columnName);
         this.postInvocationHandler(methodName, params, new Short(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public int getInt(String columnName) throws SQLException {
      int ret = 0;
      String methodName = "getInt";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getInt(columnName);
         this.postInvocationHandler(methodName, params, new Integer(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public long getLong(String columnName) throws SQLException {
      long ret = 0L;
      String methodName = "getLong";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getLong(columnName);
         this.postInvocationHandler(methodName, params, new Long(ret));
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public float getFloat(String columnName) throws SQLException {
      float ret = 0.0F;
      String methodName = "getFloat";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getFloat(columnName);
         this.postInvocationHandler(methodName, params, new Float(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public double getDouble(String columnName) throws SQLException {
      double ret = 0.0;
      String methodName = "getDouble";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getDouble(columnName);
         this.postInvocationHandler(methodName, params, new Double(ret));
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
      BigDecimal ret = null;
      String methodName = "getBigDecimal";
      Object[] params = new Object[]{columnName, new Integer(scale)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getBigDecimal(columnName, scale);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public byte[] getBytes(String columnName) throws SQLException {
      byte[] ret = null;
      String methodName = "getBytes";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getBytes(columnName);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Date getDate(String columnName) throws SQLException {
      Date ret = null;
      String methodName = "getDate";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getDate(columnName);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Time getTime(String columnName) throws SQLException {
      Time ret = null;
      String methodName = "getTime";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getTime(columnName);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Timestamp getTimestamp(String columnName) throws SQLException {
      Timestamp ret = null;
      String methodName = "getTimestamp";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getTimestamp(columnName);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public InputStream getAsciiStream(String columnName) throws SQLException {
      InputStream ret = null;
      String methodName = "getAsciiStream";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getAsciiStream(columnName);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public InputStream getUnicodeStream(String columnName) throws SQLException {
      InputStream ret = null;
      String methodName = "getUnicodeStream";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getUnicodeStream(columnName);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public InputStream getBinaryStream(String columnName) throws SQLException {
      InputStream ret = null;
      String methodName = "getBinaryStream";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getBinaryStream(columnName);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public ResultSetMetaData getMetaData() throws SQLException {
      ResultSetMetaData ret = null;
      String methodName = "getMetaData";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = new SerialResultSetMetaData(this.rmi_rs.getMetaData());
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public Object getObject(int columnIndex) throws SQLException {
      Object ret = null;
      String methodName = "getObject";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getObject(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Object getObject(String columnName) throws SQLException {
      Object ret = null;
      String methodName = "getObject";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getObject(columnName);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public int findColumn(String columnName) throws SQLException {
      int ret = 0;
      String methodName = "findColumn";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.findColumn(columnName);
         this.postInvocationHandler(methodName, params, new Integer(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Reader getCharacterStream(int columnIndex) throws SQLException {
      Reader ret = null;
      String methodName = "getCharacterStream";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getCharacterStream(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Reader getCharacterStream(String columnName) throws SQLException {
      Reader ret = null;
      String methodName = "getCharacterStream";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getCharacterStream(columnName);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
      BigDecimal ret = null;
      String methodName = "getBigDecimal";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getBigDecimal(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public BigDecimal getBigDecimal(String columnName) throws SQLException {
      BigDecimal ret = null;
      String methodName = "getBigDecimal";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getBigDecimal(columnName);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public boolean isBeforeFirst() throws SQLException {
      boolean ret = false;
      String methodName = "isBeforeFirst";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.isBeforeFirst();
         this.postInvocationHandler(methodName, params, new Boolean(ret));
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public boolean isAfterLast() throws SQLException {
      boolean ret = false;
      String methodName = "isAfterLast";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.isAfterLast();
         this.postInvocationHandler(methodName, params, new Boolean(ret));
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public boolean isFirst() throws SQLException {
      boolean ret = false;
      String methodName = "isFirst";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.isFirst();
         this.postInvocationHandler(methodName, params, new Boolean(ret));
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public boolean isLast() throws SQLException {
      boolean ret = false;
      String methodName = "isLast";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.isLast();
         this.postInvocationHandler(methodName, params, new Boolean(ret));
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public int getRow() throws SQLException {
      int ret = 0;
      String methodName = "getrow";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getRow();
         this.postInvocationHandler(methodName, params, new Integer(ret));
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public Statement getStatement() throws SQLException {
      String methodName = "getStatement";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         this.postInvocationHandler(methodName, params, this.parent_stmt);
      } catch (Exception var4) {
         this.invocationExceptionHandler(methodName, params, var4);
      }

      return (Statement)this.parent_stmt;
   }

   public Object getObject(int i, Map map) throws SQLException {
      Object ret = null;
      String methodName = "getObject";
      Object[] params = new Object[]{new Integer(i), map};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getObject(i, map);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public Blob getBlob(int i) throws SQLException {
      Blob ret = null;
      String methodName = "getBlob";
      Object[] params = new Object[]{new Integer(i)};

      try {
         this.preInvocationHandler(methodName, params);
         ResultSetMetaData rmd = this.rmi_rs.getMetaData();
         ret = this.rmi_rs.getBlob(rmd.getColumnName(i));
         if (ret != null) {
            ret = SerialOracleBlob.makeSerialOracleBlob(ret);
            ((SerialConnection)((SerialConnection)this.getStatement().getConnection())).addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Clob getClob(int i) throws SQLException {
      Clob ret = null;
      String methodName = "getClob";
      Object[] params = new Object[]{new Integer(i)};

      try {
         this.preInvocationHandler(methodName, params);
         ResultSetMetaData rmd = this.rmi_rs.getMetaData();
         ret = this.rmi_rs.getClob(rmd.getColumnName(i));
         if (ret != null) {
            ret = SerialOracleClob.makeSerialOracleClob(ret);
            ((SerialConnection)((SerialConnection)this.getStatement().getConnection())).addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Array getArray(int i) throws SQLException {
      Array ret = null;
      String methodName = "getArray";
      Object[] params = new Object[]{new Integer(i)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = SerialArray.makeSerialArrayFromStub(this.rs_reader.getArray(i));
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Array getArray(String columnName) throws SQLException {
      Array ret = null;
      String methodName = "getArray";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = SerialArray.makeSerialArrayFromStub(this.rs_reader.getArray(columnName));
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Object getObject(String columnName, Map map) throws SQLException {
      Object ret = null;
      String methodName = "getObject";
      Object[] params = new Object[]{columnName, map};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getObject(columnName, map);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public Ref getRef(String columnName) throws SQLException {
      Ref ret = null;
      String methodName = "getRef";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getRef(columnName);
         if (ret != null) {
            ret = SerialRef.makeSerialRefFromStub(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Blob getBlob(String columnName) throws SQLException {
      Blob ret = null;
      String methodName = "getBlob";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_rs.getBlob(columnName);
         if (ret != null) {
            ret = SerialOracleBlob.makeSerialOracleBlob(ret);
            ((SerialConnection)((SerialConnection)this.getStatement().getConnection())).addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Clob getClob(String columnName) throws SQLException {
      Clob ret = null;
      String methodName = "getClob";
      Object[] params = new Object[]{columnName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_rs.getClob(columnName);
         if (ret != null) {
            ret = SerialOracleClob.makeSerialOracleClob(ret);
            ((SerialConnection)((SerialConnection)this.getStatement().getConnection())).addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Date getDate(int columnIndex, Calendar cal) throws SQLException {
      Date ret = null;
      String methodName = "getDate";
      Object[] params = new Object[]{new Integer(columnIndex), cal};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getDate(columnIndex, cal);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public Date getDate(String columnName, Calendar cal) throws SQLException {
      Date ret = null;
      String methodName = "getDate";
      Object[] params = new Object[]{columnName, cal};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getDate(columnName, cal);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public Time getTime(int columnIndex, Calendar cal) throws SQLException {
      Time ret = null;
      String methodName = "getTime";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getTime(columnIndex, cal);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public Time getTime(String columnName, Calendar cal) throws SQLException {
      Time ret = null;
      String methodName = "getTime";
      Object[] params = new Object[]{columnName, cal};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getTime(columnName, cal);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
      Timestamp ret = null;
      String methodName = "getTimestamp";
      Object[] params = new Object[]{new Integer(columnIndex), cal};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getTimestamp(columnIndex, cal);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException {
      Timestamp ret = null;
      String methodName = "getTimestamp";
      Object[] params = new Object[]{columnName, cal};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getTimestamp(columnName, cal);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public Reader getNCharacterStream(int columnIndex) throws SQLException {
      Reader ret = null;
      String methodName = "getNCharacterStream";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getNCharacterStream(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Reader getNCharacterStream(String columnLabel) throws SQLException {
      Reader ret = null;
      String methodName = "getNCharacterStream";
      Object[] params = new Object[]{columnLabel};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getNCharacterStream(columnLabel);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public NClob getNClob(int columnIndex) throws SQLException {
      NClob ret = null;
      String methodName = "getNClob";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getNClob(columnIndex);
         if (ret != null) {
            ret = SerialOracleNClob.makeSerialOracleNClob(ret);
            ((SerialConnection)((SerialConnection)this.getStatement().getConnection())).addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public NClob getNClob(String columnLabel) throws SQLException {
      NClob ret = null;
      String methodName = "getNClob";
      Object[] params = new Object[]{columnLabel};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getNClob(columnLabel);
         if (ret != null) {
            ret = SerialOracleNClob.makeSerialOracleNClob(ret);
            ((SerialConnection)((SerialConnection)this.getStatement().getConnection())).addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public String getNString(int columnIndex) throws SQLException {
      String ret = null;
      String methodName = "getNString";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getNString(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public String getNString(String columnLabel) throws SQLException {
      String ret = null;
      String methodName = "getNString";
      Object[] params = new Object[]{columnLabel};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getNString(columnLabel);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Ref getRef(int columnIndex) throws SQLException {
      Ref ret = null;
      String methodName = "getRef";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getRef(columnIndex);
         if (ret != null) {
            ret = SerialRef.makeSerialRefFromStub(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public SQLXML getSQLXML(int columnIndex) throws SQLException {
      SQLXML ret = null;
      String methodName = "getSQLXML";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getSQLXML(columnIndex);
         if (ret != null) {
            ret = SerialSQLXML.makeSerialSQLXMLFromStub(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public SQLXML getSQLXML(String columnLabel) throws SQLException {
      SQLXML ret = null;
      String methodName = "getSQLXML";
      Object[] params = new Object[]{columnLabel};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getSQLXML(columnLabel);
         if (ret != null) {
            ret = SerialSQLXML.makeSerialSQLXMLFromStub(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public URL getURL(int columnIndex) throws SQLException {
      URL ret = null;
      String methodName = "getURL";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getURL(columnIndex);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public URL getURL(String columnLabel) throws SQLException {
      URL ret = null;
      String methodName = "getURL";
      Object[] params = new Object[]{columnLabel};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rs_reader.getURL(columnLabel);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public boolean isClosed() throws SQLException {
      boolean ret = this.closed;
      String methodName = "isClosed";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         if (!this.closed) {
            ret = this.rmi_rs.isClosed();
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }
}
