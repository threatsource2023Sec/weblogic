package org.apache.openjpa.jdbc.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.lib.util.Closeable;

public interface Result extends Closeable {
   Object getEager(FieldMapping var1);

   void putEager(FieldMapping var1, Object var2);

   Joins newJoins();

   void close();

   boolean isLocking();

   boolean supportsRandomAccess() throws SQLException;

   boolean absolute(int var1) throws SQLException;

   boolean next() throws SQLException;

   void pushBack() throws SQLException;

   int size() throws SQLException;

   boolean contains(Object var1) throws SQLException;

   boolean containsAll(Object[] var1) throws SQLException;

   boolean contains(Column var1, Joins var2) throws SQLException;

   boolean containsAll(Column[] var1, Joins var2) throws SQLException;

   ClassMapping getBaseMapping();

   void setBaseMapping(ClassMapping var1);

   FieldMapping getMappedByFieldMapping();

   void setMappedByFieldMapping(FieldMapping var1);

   Object getMappedByValue();

   void setMappedByValue(Object var1);

   int indexOf();

   Object load(ClassMapping var1, JDBCStore var2, JDBCFetchConfiguration var3) throws SQLException;

   Object load(ClassMapping var1, JDBCStore var2, JDBCFetchConfiguration var3, Joins var4) throws SQLException;

   Array getArray(Object var1) throws SQLException;

   InputStream getAsciiStream(Object var1) throws SQLException;

   BigDecimal getBigDecimal(Object var1) throws SQLException;

   BigInteger getBigInteger(Object var1) throws SQLException;

   InputStream getBinaryStream(Object var1) throws SQLException;

   InputStream getLOBStream(JDBCStore var1, Object var2) throws SQLException;

   Blob getBlob(Object var1) throws SQLException;

   boolean getBoolean(Object var1) throws SQLException;

   byte getByte(Object var1) throws SQLException;

   byte[] getBytes(Object var1) throws SQLException;

   Calendar getCalendar(Object var1) throws SQLException;

   char getChar(Object var1) throws SQLException;

   Reader getCharacterStream(Object var1) throws SQLException;

   Clob getClob(Object var1) throws SQLException;

   Date getDate(Object var1) throws SQLException;

   java.sql.Date getDate(Object var1, Calendar var2) throws SQLException;

   double getDouble(Object var1) throws SQLException;

   float getFloat(Object var1) throws SQLException;

   int getInt(Object var1) throws SQLException;

   Locale getLocale(Object var1) throws SQLException;

   long getLong(Object var1) throws SQLException;

   Number getNumber(Object var1) throws SQLException;

   Object getObject(Object var1, int var2, Object var3) throws SQLException;

   Object getSQLObject(Object var1, Map var2) throws SQLException;

   Ref getRef(Object var1, Map var2) throws SQLException;

   short getShort(Object var1) throws SQLException;

   String getString(Object var1) throws SQLException;

   Time getTime(Object var1, Calendar var2) throws SQLException;

   Timestamp getTimestamp(Object var1, Calendar var2) throws SQLException;

   Array getArray(Column var1, Joins var2) throws SQLException;

   InputStream getAsciiStream(Column var1, Joins var2) throws SQLException;

   BigDecimal getBigDecimal(Column var1, Joins var2) throws SQLException;

   BigInteger getBigInteger(Column var1, Joins var2) throws SQLException;

   InputStream getBinaryStream(Column var1, Joins var2) throws SQLException;

   Blob getBlob(Column var1, Joins var2) throws SQLException;

   boolean getBoolean(Column var1, Joins var2) throws SQLException;

   byte getByte(Column var1, Joins var2) throws SQLException;

   byte[] getBytes(Column var1, Joins var2) throws SQLException;

   Calendar getCalendar(Column var1, Joins var2) throws SQLException;

   char getChar(Column var1, Joins var2) throws SQLException;

   Reader getCharacterStream(Column var1, Joins var2) throws SQLException;

   Clob getClob(Column var1, Joins var2) throws SQLException;

   Date getDate(Column var1, Joins var2) throws SQLException;

   java.sql.Date getDate(Column var1, Calendar var2, Joins var3) throws SQLException;

   double getDouble(Column var1, Joins var2) throws SQLException;

   float getFloat(Column var1, Joins var2) throws SQLException;

   int getInt(Column var1, Joins var2) throws SQLException;

   Locale getLocale(Column var1, Joins var2) throws SQLException;

   long getLong(Column var1, Joins var2) throws SQLException;

   Number getNumber(Column var1, Joins var2) throws SQLException;

   Object getObject(Column var1, Object var2, Joins var3) throws SQLException;

   Object getSQLObject(Column var1, Map var2, Joins var3) throws SQLException;

   Ref getRef(Column var1, Map var2, Joins var3) throws SQLException;

   short getShort(Column var1, Joins var2) throws SQLException;

   String getString(Column var1, Joins var2) throws SQLException;

   Time getTime(Column var1, Calendar var2, Joins var3) throws SQLException;

   Timestamp getTimestamp(Column var1, Calendar var2, Joins var3) throws SQLException;

   boolean wasNull() throws SQLException;

   void startDataRequest(Object var1);

   void endDataRequest();
}
