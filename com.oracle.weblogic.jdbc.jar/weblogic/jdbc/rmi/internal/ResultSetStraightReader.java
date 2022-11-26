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

public class ResultSetStraightReader implements ResultSetReader, Serializable {
   private static final long serialVersionUID = 2887163503487096990L;
   private java.sql.ResultSet rs;

   public ResultSetStraightReader(java.sql.ResultSet in_rs) {
      this.rs = in_rs;
   }

   public int getRmiFetchSize() throws SQLException {
      return 0;
   }

   public void setRmiFetchSize(int new_size) throws SQLException {
   }

   public boolean next() throws SQLException {
      return this.rs.next();
   }

   public boolean wasNull() throws SQLException {
      return this.rs.wasNull();
   }

   public String getString(int column_index) throws SQLException {
      return this.rs.getString(column_index);
   }

   public boolean getBoolean(int column_index) throws SQLException {
      return this.rs.getBoolean(column_index);
   }

   public byte getByte(int column_index) throws SQLException {
      return this.rs.getByte(column_index);
   }

   public short getShort(int column_index) throws SQLException {
      return this.rs.getShort(column_index);
   }

   public int getInt(int column_index) throws SQLException {
      return this.rs.getInt(column_index);
   }

   public long getLong(int column_index) throws SQLException {
      return this.rs.getLong(column_index);
   }

   public float getFloat(int column_index) throws SQLException {
      return this.rs.getFloat(column_index);
   }

   public double getDouble(int column_index) throws SQLException {
      return this.rs.getDouble(column_index);
   }

   public BigDecimal getBigDecimal(int column_index, int scale) throws SQLException {
      return this.rs.getBigDecimal(column_index, scale);
   }

   public byte[] getBytes(int column_index) throws SQLException {
      return this.rs.getBytes(column_index);
   }

   public Date getDate(int column_index) throws SQLException {
      return this.rs.getDate(column_index);
   }

   public Time getTime(int column_index) throws SQLException {
      return this.rs.getTime(column_index);
   }

   public Timestamp getTimestamp(int column_index) throws SQLException {
      return this.rs.getTimestamp(column_index);
   }

   public InputStream getAsciiStream(int column_index) throws SQLException {
      return this.rs.getAsciiStream(column_index);
   }

   public InputStream getUnicodeStream(int column_index) throws SQLException {
      return this.rs.getUnicodeStream(column_index);
   }

   public InputStream getBinaryStream(int column_index) throws SQLException {
      return this.rs.getBinaryStream(column_index);
   }

   public String getString(String column_name) throws SQLException {
      return this.rs.getString(column_name);
   }

   public boolean getBoolean(String column_name) throws SQLException {
      return this.rs.getBoolean(column_name);
   }

   public byte getByte(String column_name) throws SQLException {
      return this.rs.getByte(column_name);
   }

   public short getShort(String column_name) throws SQLException {
      return this.rs.getShort(column_name);
   }

   public int getInt(String column_name) throws SQLException {
      return this.rs.getInt(column_name);
   }

   public long getLong(String column_name) throws SQLException {
      return this.rs.getLong(column_name);
   }

   public float getFloat(String column_name) throws SQLException {
      return this.rs.getFloat(column_name);
   }

   public double getDouble(String column_name) throws SQLException {
      return this.rs.getDouble(column_name);
   }

   public BigDecimal getBigDecimal(String column_name, int scale) throws SQLException {
      return this.rs.getBigDecimal(column_name);
   }

   public byte[] getBytes(String column_name) throws SQLException {
      return this.rs.getBytes(column_name);
   }

   public Date getDate(String column_name) throws SQLException {
      return this.rs.getDate(column_name);
   }

   public Time getTime(String column_name) throws SQLException {
      return this.rs.getTime(column_name);
   }

   public Timestamp getTimestamp(String column_name) throws SQLException {
      return this.rs.getTimestamp(column_name);
   }

   public InputStream getAsciiStream(String column_name) throws SQLException {
      return this.rs.getAsciiStream(column_name);
   }

   public InputStream getUnicodeStream(String column_name) throws SQLException {
      return this.rs.getUnicodeStream(column_name);
   }

   public InputStream getBinaryStream(String column_name) throws SQLException {
      return this.rs.getBinaryStream(column_name);
   }

   public Object getObject(int column_index) throws SQLException {
      return this.rs.getObject(column_index);
   }

   public Object getObject(String column_name) throws SQLException {
      return this.rs.getObject(column_name);
   }

   public int findColumn(String column_name) throws SQLException {
      return this.rs.findColumn(column_name);
   }

   public Reader getCharacterStream(int column_index) throws SQLException {
      return this.rs.getCharacterStream(column_index);
   }

   public Reader getCharacterStream(String column_name) throws SQLException {
      return this.rs.getCharacterStream(column_name);
   }

   public BigDecimal getBigDecimal(int column_index) throws SQLException {
      return this.rs.getBigDecimal(column_index);
   }

   public BigDecimal getBigDecimal(String column_name) throws SQLException {
      return this.rs.getBigDecimal(column_name);
   }

   public boolean isBeforeFirst() throws SQLException {
      return this.rs.isBeforeFirst();
   }

   public boolean isAfterLast() throws SQLException {
      return this.rs.isAfterLast();
   }

   public boolean isFirst() throws SQLException {
      return this.rs.isFirst();
   }

   public boolean isLast() throws SQLException {
      return this.rs.isLast();
   }

   public int getRow() throws SQLException {
      return this.rs.getRow();
   }

   public Object getObject(int column_index, Map map) throws SQLException {
      return this.rs.getObject(column_index, map);
   }

   public java.sql.Ref getRef(int column_index) throws SQLException {
      return this.rs.getRef(column_index);
   }

   public java.sql.Blob getBlob(int column_index) throws SQLException {
      return this.rs.getBlob(column_index);
   }

   public java.sql.Clob getClob(int column_index) throws SQLException {
      return this.rs.getClob(column_index);
   }

   public java.sql.Array getArray(int column_index) throws SQLException {
      return this.rs.getArray(column_index);
   }

   public Object getObject(String column_index, Map map) throws SQLException {
      return this.rs.getObject(column_index, map);
   }

   public java.sql.Ref getRef(String column_name) throws SQLException {
      return this.rs.getRef(column_name);
   }

   public java.sql.Blob getBlob(String column_name) throws SQLException {
      return this.rs.getBlob(column_name);
   }

   public java.sql.Clob getClob(String column_name) throws SQLException {
      return this.rs.getClob(column_name);
   }

   public java.sql.Array getArray(String column_name) throws SQLException {
      return this.rs.getArray(column_name);
   }

   public Date getDate(int column_index, Calendar cal) throws SQLException {
      return this.rs.getDate(column_index, cal);
   }

   public Date getDate(String column_name, Calendar cal) throws SQLException {
      return this.rs.getDate(column_name, cal);
   }

   public Time getTime(int column_index, Calendar cal) throws SQLException {
      return this.rs.getTime(column_index, cal);
   }

   public Time getTime(String column_name, Calendar cal) throws SQLException {
      return this.rs.getTime(column_name, cal);
   }

   public Timestamp getTimestamp(int column_index, Calendar cal) throws SQLException {
      return this.rs.getTimestamp(column_index, cal);
   }

   public Timestamp getTimestamp(String column_name, Calendar cal) throws SQLException {
      return this.rs.getTimestamp(column_name, cal);
   }

   public URL getURL(int columnIndex) throws SQLException {
      return this.rs.getURL(columnIndex);
   }

   public URL getURL(String columnLabel) throws SQLException {
      return this.rs.getURL(columnLabel);
   }

   public Reader getNCharacterStream(int columnIndex) throws SQLException {
      return this.rs.getNCharacterStream(columnIndex);
   }

   public Reader getNCharacterStream(String columnLabel) throws SQLException {
      return this.rs.getNCharacterStream(columnLabel);
   }

   public NClob getNClob(int columnIndex) throws SQLException {
      return this.rs.getNClob(columnIndex);
   }

   public NClob getNClob(String columnLabel) throws SQLException {
      return this.rs.getNClob(columnLabel);
   }

   public String getNString(int columnIndex) throws SQLException {
      return this.rs.getNString(columnIndex);
   }

   public String getNString(String columnLabel) throws SQLException {
      return this.rs.getNString(columnLabel);
   }

   public RowId getRowId(int columnIndex) throws SQLException {
      return this.rs.getRowId(columnIndex);
   }

   public RowId getRowId(String columnLabel) throws SQLException {
      return this.rs.getRowId(columnLabel);
   }

   public java.sql.SQLXML getSQLXML(int columnIndex) throws SQLException {
      return this.rs.getSQLXML(columnIndex);
   }

   public java.sql.SQLXML getSQLXML(String columnLabel) throws SQLException {
      return this.rs.getSQLXML(columnLabel);
   }
}
