package org.apache.openjpa.jdbc.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.schema.Column;
import serp.util.Numbers;

public class ResultSetResult extends AbstractResult {
   private final Connection _conn;
   private final Statement _stmnt;
   private final ResultSet _rs;
   private final DBDictionary _dict;
   private boolean _closeConn;
   private int _row;
   private int _size;
   private JDBCStore _store;

   public ResultSetResult(Connection conn, Statement stmnt, ResultSet rs, DBDictionary dict) {
      this._closeConn = true;
      this._row = -1;
      this._size = -1;
      this._store = null;
      if (stmnt == null) {
         try {
            stmnt = rs.getStatement();
         } catch (Throwable var6) {
         }
      }

      this._conn = conn;
      this._stmnt = stmnt;
      this._rs = rs;
      this._dict = dict;
   }

   public ResultSetResult(Connection conn, Statement stmnt, ResultSet rs, JDBCStore store) {
      this(conn, stmnt, rs, store.getDBDictionary());
      this.setStore(store);
   }

   public ResultSetResult(Connection conn, ResultSet rs, DBDictionary dict) {
      this._closeConn = true;
      this._row = -1;
      this._size = -1;
      this._store = null;
      this._conn = conn;
      this._stmnt = null;
      this._rs = rs;
      this._dict = dict;
   }

   public ResultSetResult(ResultSet rs, DBDictionary dict) throws SQLException {
      this._closeConn = true;
      this._row = -1;
      this._size = -1;
      this._store = null;
      this._stmnt = rs.getStatement();
      this._conn = this._stmnt.getConnection();
      this._rs = rs;
      this._dict = dict;
   }

   public ResultSetResult(ResultSet rs, JDBCStore store) throws SQLException {
      this(rs, store.getDBDictionary());
      this.setStore(store);
   }

   public Statement getStatement() {
      return this._stmnt;
   }

   public ResultSet getResultSet() {
      return this._rs;
   }

   public DBDictionary getDBDictionary() {
      return this._dict;
   }

   public JDBCStore getStore() {
      return this._store;
   }

   public void setStore(JDBCStore store) {
      this._store = store;
   }

   public boolean getCloseConnection() {
      return this._closeConn;
   }

   public void setCloseConnection(boolean closeConn) {
      this._closeConn = closeConn;
   }

   public void close() {
      super.close();

      try {
         this._rs.close();
      } catch (SQLException var4) {
      }

      if (this._stmnt != null) {
         try {
            this._stmnt.close();
         } catch (SQLException var3) {
         }
      }

      if (this._closeConn) {
         try {
            this._conn.close();
         } catch (SQLException var2) {
         }
      }

   }

   public boolean supportsRandomAccess() throws SQLException {
      return this._rs.getType() != 1003;
   }

   protected boolean absoluteInternal(int row) throws SQLException {
      if (row == ++this._row) {
         return this._rs.next();
      } else {
         this._rs.absolute(row + 1);
         if (this._rs.getRow() == 0) {
            this._row = -1;
            return false;
         } else {
            this._row = row;
            return true;
         }
      }
   }

   protected boolean nextInternal() throws SQLException {
      ++this._row;
      return this._rs.next();
   }

   public int size() throws SQLException {
      if (this._size == -1) {
         this._rs.last();
         this._size = this._rs.getRow();
         if (this._row == -1) {
            this._rs.beforeFirst();
         } else {
            this._rs.absolute(this._row + 1);
         }
      }

      return this._size;
   }

   protected boolean containsInternal(Object obj, Joins joins) throws SQLException {
      return ((Number)this.translate(obj, joins)).intValue() > 0;
   }

   protected Array getArrayInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getArray(this._rs, ((Number)obj).intValue());
   }

   protected InputStream getAsciiStreamInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getAsciiStream(this._rs, ((Number)obj).intValue());
   }

   protected BigDecimal getBigDecimalInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getBigDecimal(this._rs, ((Number)obj).intValue());
   }

   protected Number getNumberInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getNumber(this._rs, ((Number)obj).intValue());
   }

   protected BigInteger getBigIntegerInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getBigInteger(this._rs, ((Number)obj).intValue());
   }

   protected InputStream getBinaryStreamInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getBinaryStream(this._rs, ((Number)obj).intValue());
   }

   protected Blob getBlobInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getBlob(this._rs, ((Number)obj).intValue());
   }

   protected boolean getBooleanInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getBoolean(this._rs, ((Number)obj).intValue());
   }

   protected byte getByteInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getByte(this._rs, ((Number)obj).intValue());
   }

   protected byte[] getBytesInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getBytes(this._rs, ((Number)obj).intValue());
   }

   protected Calendar getCalendarInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getCalendar(this._rs, ((Number)obj).intValue());
   }

   protected char getCharInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getChar(this._rs, ((Number)obj).intValue());
   }

   protected Reader getCharacterStreamInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getCharacterStream(this._rs, ((Number)obj).intValue());
   }

   protected Clob getClobInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getClob(this._rs, ((Number)obj).intValue());
   }

   protected Date getDateInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getDate(this._rs, ((Number)obj).intValue());
   }

   protected java.sql.Date getDateInternal(Object obj, Calendar cal, Joins joins) throws SQLException {
      return this._dict.getDate(this._rs, ((Number)obj).intValue(), cal);
   }

   protected double getDoubleInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getDouble(this._rs, ((Number)obj).intValue());
   }

   protected float getFloatInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getFloat(this._rs, ((Number)obj).intValue());
   }

   protected int getIntInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getInt(this._rs, ((Number)obj).intValue());
   }

   protected Locale getLocaleInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getLocale(this._rs, ((Number)obj).intValue());
   }

   protected long getLongInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getLong(this._rs, ((Number)obj).intValue());
   }

   protected Object getStreamInternal(JDBCStore store, Object obj, int metaTypeCode, Object arg, Joins joins) throws SQLException {
      return this.getLOBStreamInternal(store, obj, joins);
   }

   protected Object getObjectInternal(Object obj, int metaTypeCode, Object arg, Joins joins) throws SQLException {
      if (metaTypeCode == -1 && obj instanceof Column) {
         metaTypeCode = ((Column)obj).getJavaType();
      }

      boolean isClob = obj instanceof Column ? ((Column)obj).getType() == 2005 : false;
      obj = this.translate(obj, joins);
      Object val = null;
      switch (metaTypeCode) {
         case 0:
         case 16:
            val = this.getBooleanInternal(obj, joins) ? Boolean.TRUE : Boolean.FALSE;
            break;
         case 1:
         case 17:
            val = new Byte(this.getByteInternal(obj, joins));
            break;
         case 2:
         case 18:
            val = new Character(this.getCharInternal(obj, joins));
            break;
         case 3:
         case 19:
            val = new Double(this.getDoubleInternal(obj, joins));
            break;
         case 4:
         case 20:
            val = new Float(this.getFloatInternal(obj, joins));
            break;
         case 5:
         case 21:
            val = Numbers.valueOf(this.getIntInternal(obj, joins));
            break;
         case 6:
         case 22:
            val = Numbers.valueOf(this.getLongInternal(obj, joins));
            break;
         case 7:
         case 23:
            val = new Short(this.getShortInternal(obj, joins));
            break;
         case 8:
            return this._dict.getBlobObject(this._rs, ((Number)obj).intValue(), this._store);
         case 9:
            return this.getStringInternal(obj, joins, isClob);
         case 10:
            return this.getNumberInternal(obj, joins);
         case 14:
            return this.getDateInternal(obj, joins);
         case 24:
            return this.getBigDecimalInternal(obj, joins);
         case 25:
            return this.getBigIntegerInternal(obj, joins);
         case 26:
            return this.getLocaleInternal(obj, joins);
         case 28:
            return this.getCalendarInternal(obj, joins);
         case 1000:
            return this.getArrayInternal(obj, joins);
         case 1001:
            return this.getAsciiStreamInternal(obj, joins);
         case 1002:
            return this.getBinaryStreamInternal(obj, joins);
         case 1003:
            return this.getBlobInternal(obj, joins);
         case 1004:
            return this.getBytesInternal(obj, joins);
         case 1005:
            return this.getCharacterStreamInternal(obj, joins);
         case 1006:
            return this.getClobInternal(obj, joins);
         case 1007:
            return this.getDateInternal(obj, (Calendar)arg, joins);
         case 1008:
            return this.getSQLObjectInternal(obj, (Map)arg, joins);
         case 1009:
            return this.getRefInternal(obj, (Map)arg, joins);
         case 1010:
            return this.getTimeInternal(obj, (Calendar)arg, joins);
         case 1011:
            return this.getTimestampInternal(obj, (Calendar)arg, joins);
         default:
            if (obj instanceof Column) {
               Column col = (Column)obj;
               if (col.getType() == 2004 || col.getType() == -3) {
                  return this._dict.getBlobObject(this._rs, col.getIndex(), this._store);
               }
            }

            return this._dict.getObject(this._rs, ((Number)obj).intValue(), (Map)null);
      }

      return this._rs.wasNull() ? null : val;
   }

   protected Object getSQLObjectInternal(Object obj, Map map, Joins joins) throws SQLException {
      return this._dict.getObject(this._rs, ((Number)obj).intValue(), map);
   }

   protected Ref getRefInternal(Object obj, Map map, Joins joins) throws SQLException {
      return this._dict.getRef(this._rs, ((Number)obj).intValue(), map);
   }

   protected short getShortInternal(Object obj, Joins joins) throws SQLException {
      return this._dict.getShort(this._rs, ((Number)obj).intValue());
   }

   protected String getStringInternal(Object obj, Joins joins, boolean isClobString) throws SQLException {
      return isClobString ? this._dict.getClobString(this._rs, ((Number)obj).intValue()) : this._dict.getString(this._rs, ((Number)obj).intValue());
   }

   protected Time getTimeInternal(Object obj, Calendar cal, Joins joins) throws SQLException {
      return this._dict.getTime(this._rs, ((Number)obj).intValue(), cal);
   }

   protected Timestamp getTimestampInternal(Object obj, Calendar cal, Joins joins) throws SQLException {
      return this._dict.getTimestamp(this._rs, ((Number)obj).intValue(), cal);
   }

   public boolean wasNull() throws SQLException {
      return this._rs.wasNull();
   }

   protected Object translate(Object obj, Joins joins) throws SQLException {
      return obj instanceof Number ? obj : Numbers.valueOf(this.findObject(obj, joins));
   }

   protected int findObject(Object obj, Joins joins) throws SQLException {
      try {
         return this.getResultSet().findColumn(obj.toString());
      } catch (SQLException var4) {
         return 0;
      }
   }

   protected InputStream getLOBStreamInternal(JDBCStore store, Object obj, Joins joins) throws SQLException {
      return this._dict.getLOBStream(store, this._rs, ((Number)obj).intValue());
   }
}
