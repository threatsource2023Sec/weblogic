package weblogic.jdbc.rmi.internal;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.NClob;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class ResultSetCachingReader implements ResultSetReader, Serializable {
   private static final long serialVersionUID = 3906341926472915638L;
   private ResultSetStub rs;
   private transient ResultSetRowCache rowCache = null;
   private transient ResultSetMetaDataCache mdCache = null;

   public ResultSetCachingReader(ResultSetStub rs_stub) throws SQLException {
      this.rs = rs_stub;
      this.mdCache = rs_stub.getMetaDataCache();
      this.rowCache = this.rs.getNextRowCache();
      this.rowCache.setMetaDataCache(this.mdCache);
      this.rowCache.beforeFirstRow();
   }

   public synchronized boolean next() throws SQLException {
      if (this.rowCache.next()) {
         return true;
      } else if (this.rowCache.isTrueSetFinished()) {
         return false;
      } else {
         this.rowCache = this.rs.getNextRowCache();
         this.rowCache.setMetaDataCache(this.mdCache);
         return this.rowCache.getRowCount() > 0;
      }
   }

   public boolean wasNull() throws SQLException {
      return this.rowCache.wasNull();
   }

   public String getString(int column_index) throws SQLException {
      return this.rowCache.getString(column_index);
   }

   public boolean getBoolean(int column_index) throws SQLException {
      return this.rowCache.getBoolean(column_index);
   }

   public byte getByte(int column_index) throws SQLException {
      return this.rowCache.getByte(column_index);
   }

   public short getShort(int column_index) throws SQLException {
      return this.rowCache.getShort(column_index);
   }

   public int getInt(int column_index) throws SQLException {
      return this.rowCache.getInt(column_index);
   }

   public long getLong(int column_index) throws SQLException {
      return this.rowCache.getLong(column_index);
   }

   public float getFloat(int column_index) throws SQLException {
      return this.rowCache.getFloat(column_index);
   }

   public double getDouble(int column_index) throws SQLException {
      return this.rowCache.getDouble(column_index);
   }

   public BigDecimal getBigDecimal(int column_index, int scale) throws SQLException {
      return this.rowCache.getBigDecimal(column_index, scale);
   }

   public byte[] getBytes(int column_index) throws SQLException {
      return this.rowCache.getBytes(column_index);
   }

   public Date getDate(int column_index) throws SQLException {
      return this.rowCache.getDate(column_index);
   }

   public Time getTime(int column_index) throws SQLException {
      return this.rowCache.getTime(column_index);
   }

   public Timestamp getTimestamp(int column_index) throws SQLException {
      return this.rowCache.getTimestamp(column_index);
   }

   public InputStream getAsciiStream(int column_index) throws SQLException {
      String emsg = "getAsciiStream is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public InputStream getUnicodeStream(int column_index) throws SQLException {
      String emsg = "getUnicodeStream is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public InputStream getBinaryStream(int column_index) throws SQLException {
      String emsg = "getBinaryStream is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public String getString(String column_name) throws SQLException {
      return this.rowCache.getString(this.mdCache.findColumn(column_name));
   }

   public boolean getBoolean(String column_name) throws SQLException {
      return this.rowCache.getBoolean(this.mdCache.findColumn(column_name));
   }

   public byte getByte(String column_name) throws SQLException {
      return this.rowCache.getByte(this.mdCache.findColumn(column_name));
   }

   public short getShort(String column_name) throws SQLException {
      return this.rowCache.getShort(this.mdCache.findColumn(column_name));
   }

   public int getInt(String column_name) throws SQLException {
      return this.rowCache.getInt(this.mdCache.findColumn(column_name));
   }

   public long getLong(String column_name) throws SQLException {
      return this.rowCache.getLong(this.mdCache.findColumn(column_name));
   }

   public float getFloat(String column_name) throws SQLException {
      return this.rowCache.getFloat(this.mdCache.findColumn(column_name));
   }

   public double getDouble(String column_name) throws SQLException {
      return this.rowCache.getDouble(this.mdCache.findColumn(column_name));
   }

   public BigDecimal getBigDecimal(String column_name, int scale) throws SQLException {
      return this.rowCache.getBigDecimal(this.mdCache.findColumn(column_name), scale);
   }

   public byte[] getBytes(String column_name) throws SQLException {
      return this.rowCache.getBytes(this.mdCache.findColumn(column_name));
   }

   public Date getDate(String column_name) throws SQLException {
      return this.rowCache.getDate(this.mdCache.findColumn(column_name));
   }

   public Time getTime(String column_name) throws SQLException {
      return this.rowCache.getTime(this.mdCache.findColumn(column_name));
   }

   public Timestamp getTimestamp(String column_name) throws SQLException {
      return this.rowCache.getTimestamp(this.mdCache.findColumn(column_name));
   }

   public InputStream getAsciiStream(String column_name) throws SQLException {
      String emsg = "getAsciiStream is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public InputStream getUnicodeStream(String column_name) throws SQLException {
      String emsg = "getUnicodeStream is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public InputStream getBinaryStream(String column_name) throws SQLException {
      String emsg = "getBinaryStream is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public Object getObject(int column_index) throws SQLException {
      return this.rowCache.getObject(column_index);
   }

   public Object getObject(String column_name) throws SQLException {
      return this.rowCache.getObject(this.mdCache.findColumn(column_name));
   }

   public int findColumn(String column_name) throws SQLException {
      return this.mdCache.findColumn(column_name);
   }

   public Reader getCharacterStream(int column_index) throws SQLException {
      String emsg = "getCharacterStream is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public Reader getCharacterStream(String column_name) throws SQLException {
      String emsg = "getCharacterStream is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public BigDecimal getBigDecimal(int column_index) throws SQLException {
      return this.rowCache.getBigDecimal(column_index);
   }

   public BigDecimal getBigDecimal(String column_name) throws SQLException {
      return this.rowCache.getBigDecimal(this.mdCache.findColumn(column_name));
   }

   public boolean isBeforeFirst() throws SQLException {
      String emsg = "isBeforeFirst is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public boolean isAfterLast() throws SQLException {
      String emsg = "isAfterLast is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public boolean isFirst() throws SQLException {
      String emsg = "isFirst is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public boolean isLast() throws SQLException {
      String emsg = "isLast is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public int getRow() throws SQLException {
      String emsg = "getRow is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public Object getObject(int column_index, Map map) throws SQLException {
      String emsg = "getObject(int col, java.util.Map map) is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public java.sql.Ref getRef(int column_index) throws SQLException {
      String emsg = "getRef is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public java.sql.Blob getBlob(int column_index) throws SQLException {
      String emsg = "getBlob is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public java.sql.Clob getClob(int column_index) throws SQLException {
      String emsg = "getClob is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public java.sql.Array getArray(int column_index) throws SQLException {
      String emsg = "getArray is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public Object getObject(String column_index, Map map) throws SQLException {
      String emsg = "getObject(String col, java.util.Map map) is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public java.sql.Ref getRef(String column_name) throws SQLException {
      String emsg = "getRef is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public java.sql.Blob getBlob(String column_name) throws SQLException {
      String emsg = "getBlob is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public java.sql.Clob getClob(String column_name) throws SQLException {
      String emsg = "getClob is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public java.sql.Array getArray(String column_name) throws SQLException {
      String emsg = "getArray is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public Date getDate(int column_index, Calendar cal) throws SQLException {
      String emsg = " getDate(int col, java.util.Calendar cal) is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public Date getDate(String column_name, Calendar cal) throws SQLException {
      String emsg = " getDate(String col, java.util.Calendar cal) is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public Time getTime(int column_index, Calendar cal) throws SQLException {
      String emsg = " getTime(int col, java.util.Calendar cal) is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public Time getTime(String column_name, Calendar cal) throws SQLException {
      String emsg = " getTime(String col, java.util.Calendar cal) is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public Timestamp getTimestamp(int column_index, Calendar cal) throws SQLException {
      String emsg = " getTimestamp(int col, java.util.Calendar cal) is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public Timestamp getTimestamp(String column_name, Calendar cal) throws SQLException {
      String emsg = " getTimestamp(String col, java.util.Calendar cal) is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public Reader getNCharacterStream(int columnIndex) throws SQLException {
      String emsg = "getNCharacterStream is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public Reader getNCharacterStream(String columnLabel) throws SQLException {
      String emsg = "getNCharacterStream is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public NClob getNClob(int columnIndex) throws SQLException {
      String emsg = "getNClob is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public NClob getNClob(String columnLabel) throws SQLException {
      String emsg = "getNClob is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public String getNString(int columnIndex) throws SQLException {
      return this.rowCache.getNString(columnIndex);
   }

   public String getNString(String columnLabel) throws SQLException {
      return this.rowCache.getNString(this.mdCache.findColumn(columnLabel));
   }

   public RowId getRowId(int columnIndex) throws SQLException {
      String emsg = "getRowId is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public RowId getRowId(String columnLabel) throws SQLException {
      String emsg = "getRowId is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public java.sql.SQLXML getSQLXML(int columnIndex) throws SQLException {
      String emsg = "getRowId is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public java.sql.SQLXML getSQLXML(String columnLabel) throws SQLException {
      String emsg = "getRowId is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public URL getURL(int columnIndex) throws SQLException {
      String emsg = "getURL is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }

   public URL getURL(String columnLabel) throws SQLException {
      String emsg = "getURL is not supported with row caching turned on. ";
      throw new SQLException(emsg);
   }
}
