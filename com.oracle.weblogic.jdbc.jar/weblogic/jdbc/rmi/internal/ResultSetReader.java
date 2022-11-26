package weblogic.jdbc.rmi.internal;

import java.io.InputStream;
import java.io.Reader;
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

public interface ResultSetReader {
   boolean next() throws SQLException;

   boolean wasNull() throws SQLException;

   String getString(int var1) throws SQLException;

   boolean getBoolean(int var1) throws SQLException;

   byte getByte(int var1) throws SQLException;

   short getShort(int var1) throws SQLException;

   int getInt(int var1) throws SQLException;

   long getLong(int var1) throws SQLException;

   float getFloat(int var1) throws SQLException;

   double getDouble(int var1) throws SQLException;

   BigDecimal getBigDecimal(int var1, int var2) throws SQLException;

   byte[] getBytes(int var1) throws SQLException;

   Date getDate(int var1) throws SQLException;

   Time getTime(int var1) throws SQLException;

   Timestamp getTimestamp(int var1) throws SQLException;

   InputStream getAsciiStream(int var1) throws SQLException;

   InputStream getUnicodeStream(int var1) throws SQLException;

   InputStream getBinaryStream(int var1) throws SQLException;

   String getString(String var1) throws SQLException;

   boolean getBoolean(String var1) throws SQLException;

   byte getByte(String var1) throws SQLException;

   short getShort(String var1) throws SQLException;

   int getInt(String var1) throws SQLException;

   long getLong(String var1) throws SQLException;

   float getFloat(String var1) throws SQLException;

   double getDouble(String var1) throws SQLException;

   BigDecimal getBigDecimal(String var1, int var2) throws SQLException;

   byte[] getBytes(String var1) throws SQLException;

   Date getDate(String var1) throws SQLException;

   Time getTime(String var1) throws SQLException;

   Timestamp getTimestamp(String var1) throws SQLException;

   InputStream getAsciiStream(String var1) throws SQLException;

   InputStream getUnicodeStream(String var1) throws SQLException;

   InputStream getBinaryStream(String var1) throws SQLException;

   Object getObject(int var1) throws SQLException;

   Object getObject(String var1) throws SQLException;

   int findColumn(String var1) throws SQLException;

   Reader getCharacterStream(int var1) throws SQLException;

   Reader getCharacterStream(String var1) throws SQLException;

   BigDecimal getBigDecimal(int var1) throws SQLException;

   BigDecimal getBigDecimal(String var1) throws SQLException;

   boolean isBeforeFirst() throws SQLException;

   boolean isAfterLast() throws SQLException;

   boolean isFirst() throws SQLException;

   boolean isLast() throws SQLException;

   int getRow() throws SQLException;

   Object getObject(int var1, Map var2) throws SQLException;

   java.sql.Ref getRef(int var1) throws SQLException;

   java.sql.Blob getBlob(int var1) throws SQLException;

   java.sql.Clob getClob(int var1) throws SQLException;

   java.sql.Array getArray(int var1) throws SQLException;

   Object getObject(String var1, Map var2) throws SQLException;

   java.sql.Ref getRef(String var1) throws SQLException;

   java.sql.Blob getBlob(String var1) throws SQLException;

   java.sql.Clob getClob(String var1) throws SQLException;

   java.sql.Array getArray(String var1) throws SQLException;

   Date getDate(int var1, Calendar var2) throws SQLException;

   Date getDate(String var1, Calendar var2) throws SQLException;

   Time getTime(int var1, Calendar var2) throws SQLException;

   Time getTime(String var1, Calendar var2) throws SQLException;

   Timestamp getTimestamp(int var1, Calendar var2) throws SQLException;

   Timestamp getTimestamp(String var1, Calendar var2) throws SQLException;

   Reader getNCharacterStream(int var1) throws SQLException;

   Reader getNCharacterStream(String var1) throws SQLException;

   NClob getNClob(int var1) throws SQLException;

   NClob getNClob(String var1) throws SQLException;

   String getNString(int var1) throws SQLException;

   String getNString(String var1) throws SQLException;

   RowId getRowId(int var1) throws SQLException;

   RowId getRowId(String var1) throws SQLException;

   java.sql.SQLXML getSQLXML(int var1) throws SQLException;

   java.sql.SQLXML getSQLXML(String var1) throws SQLException;

   URL getURL(int var1) throws SQLException;

   URL getURL(String var1) throws SQLException;
}
