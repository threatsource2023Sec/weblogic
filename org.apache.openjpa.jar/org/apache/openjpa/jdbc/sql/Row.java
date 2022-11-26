package org.apache.openjpa.jdbc.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.openjpa.jdbc.meta.RelationId;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface Row {
   int ACTION_UNKNOWN = -1;
   int ACTION_UPDATE = 0;
   int ACTION_INSERT = 1;
   int ACTION_DELETE = 2;

   Table getTable();

   int getAction();

   Object getFailedObject();

   void setFailedObject(Object var1);

   boolean isValid();

   void setValid(boolean var1);

   OpenJPAStateManager getPrimaryKey();

   void setPrimaryKey(OpenJPAStateManager var1) throws SQLException;

   void setPrimaryKey(ColumnIO var1, OpenJPAStateManager var2) throws SQLException;

   void wherePrimaryKey(OpenJPAStateManager var1) throws SQLException;

   void setForeignKey(ForeignKey var1, OpenJPAStateManager var2) throws SQLException;

   void setForeignKey(ForeignKey var1, ColumnIO var2, OpenJPAStateManager var3) throws SQLException;

   void whereForeignKey(ForeignKey var1, OpenJPAStateManager var2) throws SQLException;

   void setArray(Column var1, Array var2) throws SQLException;

   void setAsciiStream(Column var1, InputStream var2, int var3) throws SQLException;

   void setBigDecimal(Column var1, BigDecimal var2) throws SQLException;

   void setBigInteger(Column var1, BigInteger var2) throws SQLException;

   void setBinaryStream(Column var1, InputStream var2, int var3) throws SQLException;

   void setBlob(Column var1, Blob var2) throws SQLException;

   void setBoolean(Column var1, boolean var2) throws SQLException;

   void setByte(Column var1, byte var2) throws SQLException;

   void setBytes(Column var1, byte[] var2) throws SQLException;

   void setCalendar(Column var1, Calendar var2) throws SQLException;

   void setChar(Column var1, char var2) throws SQLException;

   void setCharacterStream(Column var1, Reader var2, int var3) throws SQLException;

   void setClob(Column var1, Clob var2) throws SQLException;

   void setDate(Column var1, Date var2) throws SQLException;

   void setDate(Column var1, java.sql.Date var2, Calendar var3) throws SQLException;

   void setDouble(Column var1, double var2) throws SQLException;

   void setFloat(Column var1, float var2) throws SQLException;

   void setInt(Column var1, int var2) throws SQLException;

   void setLong(Column var1, long var2) throws SQLException;

   void setLocale(Column var1, Locale var2) throws SQLException;

   void setNull(Column var1) throws SQLException;

   void setNull(Column var1, boolean var2) throws SQLException;

   void setNumber(Column var1, Number var2) throws SQLException;

   void setObject(Column var1, Object var2) throws SQLException;

   void setRaw(Column var1, String var2) throws SQLException;

   void setRelationId(Column var1, OpenJPAStateManager var2, RelationId var3) throws SQLException;

   void setShort(Column var1, short var2) throws SQLException;

   void setString(Column var1, String var2) throws SQLException;

   void setTime(Column var1, Time var2, Calendar var3) throws SQLException;

   void setTimestamp(Column var1, Timestamp var2, Calendar var3) throws SQLException;

   void whereArray(Column var1, Array var2) throws SQLException;

   void whereAsciiStream(Column var1, InputStream var2, int var3) throws SQLException;

   void whereBigDecimal(Column var1, BigDecimal var2) throws SQLException;

   void whereBigInteger(Column var1, BigInteger var2) throws SQLException;

   void whereBinaryStream(Column var1, InputStream var2, int var3) throws SQLException;

   void whereBlob(Column var1, Blob var2) throws SQLException;

   void whereBoolean(Column var1, boolean var2) throws SQLException;

   void whereByte(Column var1, byte var2) throws SQLException;

   void whereBytes(Column var1, byte[] var2) throws SQLException;

   void whereCalendar(Column var1, Calendar var2) throws SQLException;

   void whereChar(Column var1, char var2) throws SQLException;

   void whereCharacterStream(Column var1, Reader var2, int var3) throws SQLException;

   void whereClob(Column var1, Clob var2) throws SQLException;

   void whereDate(Column var1, Date var2) throws SQLException;

   void whereDate(Column var1, java.sql.Date var2, Calendar var3) throws SQLException;

   void whereDouble(Column var1, double var2) throws SQLException;

   void whereFloat(Column var1, float var2) throws SQLException;

   void whereInt(Column var1, int var2) throws SQLException;

   void whereLong(Column var1, long var2) throws SQLException;

   void whereLocale(Column var1, Locale var2) throws SQLException;

   void whereNull(Column var1) throws SQLException;

   void whereNumber(Column var1, Number var2) throws SQLException;

   void whereObject(Column var1, Object var2) throws SQLException;

   void whereRaw(Column var1, String var2) throws SQLException;

   void whereShort(Column var1, short var2) throws SQLException;

   void whereString(Column var1, String var2) throws SQLException;

   void whereTime(Column var1, Time var2, Calendar var3) throws SQLException;

   void whereTimestamp(Column var1, Timestamp var2, Calendar var3) throws SQLException;
}
