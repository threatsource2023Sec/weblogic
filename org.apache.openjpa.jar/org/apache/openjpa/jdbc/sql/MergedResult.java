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
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.util.UnsupportedException;

public class MergedResult implements Result {
   private static final byte NEXT = 0;
   private static final byte CURRENT = 1;
   private static final byte DONE = 2;
   private final Result[] _res;
   private final byte[] _status;
   private final ResultComparator _comp;
   private final Object[] _order;
   private int _idx;
   private boolean _pushedBack;

   public MergedResult(Result[] res) {
      this(res, (ResultComparator)null);
   }

   public MergedResult(Result[] res, ResultComparator comp) {
      this._idx = 0;
      this._pushedBack = false;
      this._res = res;
      this._comp = comp;
      this._order = comp == null ? null : new Object[res.length];
      this._status = comp == null ? null : new byte[res.length];
   }

   public Object getEager(FieldMapping key) {
      return this._res[this._idx].getEager(key);
   }

   public void putEager(FieldMapping key, Object res) {
      this._res[this._idx].putEager(key, res);
   }

   public Joins newJoins() {
      return this._res[this._idx].newJoins();
   }

   public void close() {
      for(int i = 0; i < this._res.length; ++i) {
         this._res[i].close();
      }

   }

   public boolean isLocking() {
      return this._res[this._idx].isLocking();
   }

   public boolean supportsRandomAccess() throws SQLException {
      return false;
   }

   public boolean absolute(int row) throws SQLException {
      throw new UnsupportedException();
   }

   public boolean next() throws SQLException {
      if (this._pushedBack) {
         this._pushedBack = false;
         return true;
      } else if (this._comp == null) {
         while(!this._res[this._idx].next()) {
            if (this._idx == this._res.length - 1) {
               return false;
            }

            ++this._idx;
         }

         return true;
      } else {
         boolean hasValue = false;

         int least;
         for(least = 0; least < this._status.length; ++least) {
            switch (this._status[least]) {
               case 0:
                  if (this._res[least].next()) {
                     hasValue = true;
                     this._status[least] = 1;
                     this._order[least] = this._comp.getOrderingValue(this._res[least], least);
                  } else {
                     this._status[least] = 2;
                  }
                  break;
               case 1:
                  hasValue = true;
            }
         }

         if (!hasValue) {
            return false;
         } else {
            least = -1;
            Object orderVal = null;

            for(int i = 0; i < this._order.length; ++i) {
               if (this._status[i] == 1 && (least == -1 || this._comp.compare(this._order[i], orderVal) < 0)) {
                  least = i;
                  orderVal = this._order[i];
               }
            }

            this._idx = least;
            this._order[least] = null;
            this._status[least] = 0;
            return true;
         }
      }
   }

   public void pushBack() throws SQLException {
      this._pushedBack = true;
   }

   public int size() throws SQLException {
      int size = 0;

      for(int i = 0; i < this._res.length; ++i) {
         size += this._res[i].size();
      }

      return size;
   }

   public boolean contains(Object obj) throws SQLException {
      return this._res[this._idx].contains(obj);
   }

   public boolean containsAll(Object[] objs) throws SQLException {
      return this._res[this._idx].containsAll(objs);
   }

   public boolean contains(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].contains(col, joins);
   }

   public boolean containsAll(Column[] cols, Joins joins) throws SQLException {
      return this._res[this._idx].containsAll(cols, joins);
   }

   public ClassMapping getBaseMapping() {
      return this._res[this._idx].getBaseMapping();
   }

   public void setBaseMapping(ClassMapping mapping) {
      this._res[this._idx].setBaseMapping(mapping);
   }

   public FieldMapping getMappedByFieldMapping() {
      return this._res[this._idx].getMappedByFieldMapping();
   }

   public void setMappedByFieldMapping(FieldMapping fieldMapping) {
      this._res[this._idx].setMappedByFieldMapping(fieldMapping);
   }

   public Object getMappedByValue() {
      return this._res[this._idx].getMappedByValue();
   }

   public void setMappedByValue(Object mappedByValue) {
      this._res[this._idx].setMappedByValue(mappedByValue);
   }

   public int indexOf() {
      return this._res[this._idx].indexOf();
   }

   public Object load(ClassMapping mapping, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
      return this._res[this._idx].load(mapping, store, fetch);
   }

   public Object load(ClassMapping mapping, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) throws SQLException {
      return this._res[this._idx].load(mapping, store, fetch, joins);
   }

   public Array getArray(Object obj) throws SQLException {
      return this._res[this._idx].getArray(obj);
   }

   public InputStream getAsciiStream(Object obj) throws SQLException {
      return this._res[this._idx].getAsciiStream(obj);
   }

   public BigDecimal getBigDecimal(Object obj) throws SQLException {
      return this._res[this._idx].getBigDecimal(obj);
   }

   public BigInteger getBigInteger(Object obj) throws SQLException {
      return this._res[this._idx].getBigInteger(obj);
   }

   public InputStream getBinaryStream(Object obj) throws SQLException {
      return this._res[this._idx].getBinaryStream(obj);
   }

   public InputStream getLOBStream(JDBCStore store, Object obj) throws SQLException {
      return this._res[this._idx].getLOBStream(store, obj);
   }

   public Blob getBlob(Object obj) throws SQLException {
      return this._res[this._idx].getBlob(obj);
   }

   public boolean getBoolean(Object obj) throws SQLException {
      return this._res[this._idx].getBoolean(obj);
   }

   public byte getByte(Object obj) throws SQLException {
      return this._res[this._idx].getByte(obj);
   }

   public byte[] getBytes(Object obj) throws SQLException {
      return this._res[this._idx].getBytes(obj);
   }

   public Calendar getCalendar(Object obj) throws SQLException {
      return this._res[this._idx].getCalendar(obj);
   }

   public char getChar(Object obj) throws SQLException {
      return this._res[this._idx].getChar(obj);
   }

   public Reader getCharacterStream(Object obj) throws SQLException {
      return this._res[this._idx].getCharacterStream(obj);
   }

   public Clob getClob(Object obj) throws SQLException {
      return this._res[this._idx].getClob(obj);
   }

   public Date getDate(Object obj) throws SQLException {
      return this._res[this._idx].getDate(obj);
   }

   public java.sql.Date getDate(Object obj, Calendar cal) throws SQLException {
      return this._res[this._idx].getDate(obj, cal);
   }

   public double getDouble(Object obj) throws SQLException {
      return this._res[this._idx].getDouble(obj);
   }

   public float getFloat(Object obj) throws SQLException {
      return this._res[this._idx].getFloat(obj);
   }

   public int getInt(Object obj) throws SQLException {
      return this._res[this._idx].getInt(obj);
   }

   public Locale getLocale(Object obj) throws SQLException {
      return this._res[this._idx].getLocale(obj);
   }

   public long getLong(Object obj) throws SQLException {
      return this._res[this._idx].getLong(obj);
   }

   public Number getNumber(Object obj) throws SQLException {
      return this._res[this._idx].getNumber(obj);
   }

   public Object getObject(Object obj, int metaType, Object arg) throws SQLException {
      return this._res[this._idx].getObject(obj, metaType, arg);
   }

   public Object getSQLObject(Object obj, Map map) throws SQLException {
      return this._res[this._idx].getSQLObject(obj, map);
   }

   public Ref getRef(Object obj, Map map) throws SQLException {
      return this._res[this._idx].getRef(obj, map);
   }

   public short getShort(Object obj) throws SQLException {
      return this._res[this._idx].getShort(obj);
   }

   public String getString(Object obj) throws SQLException {
      return this._res[this._idx].getString(obj);
   }

   public Time getTime(Object obj, Calendar cal) throws SQLException {
      return this._res[this._idx].getTime(obj, cal);
   }

   public Timestamp getTimestamp(Object obj, Calendar cal) throws SQLException {
      return this._res[this._idx].getTimestamp(obj, cal);
   }

   public Array getArray(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getArray(col, joins);
   }

   public InputStream getAsciiStream(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getAsciiStream(col, joins);
   }

   public BigDecimal getBigDecimal(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getBigDecimal(col, joins);
   }

   public BigInteger getBigInteger(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getBigInteger(col, joins);
   }

   public InputStream getBinaryStream(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getBinaryStream(col, joins);
   }

   public Blob getBlob(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getBlob(col, joins);
   }

   public boolean getBoolean(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getBoolean(col, joins);
   }

   public byte getByte(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getByte(col, joins);
   }

   public byte[] getBytes(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getBytes(col, joins);
   }

   public Calendar getCalendar(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getCalendar(col, joins);
   }

   public char getChar(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getChar(col, joins);
   }

   public Reader getCharacterStream(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getCharacterStream(col, joins);
   }

   public Clob getClob(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getClob(col, joins);
   }

   public Date getDate(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getDate(col, joins);
   }

   public java.sql.Date getDate(Column col, Calendar cal, Joins joins) throws SQLException {
      return this._res[this._idx].getDate(col, cal, joins);
   }

   public double getDouble(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getDouble(col, joins);
   }

   public float getFloat(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getFloat(col, joins);
   }

   public int getInt(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getInt(col, joins);
   }

   public Locale getLocale(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getLocale(col, joins);
   }

   public long getLong(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getLong(col, joins);
   }

   public Number getNumber(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getNumber(col, joins);
   }

   public Object getObject(Column col, Object arg, Joins joins) throws SQLException {
      return this._res[this._idx].getObject(col, arg, joins);
   }

   public Object getSQLObject(Column col, Map map, Joins joins) throws SQLException {
      return this._res[this._idx].getSQLObject(col, map, joins);
   }

   public Ref getRef(Column col, Map map, Joins joins) throws SQLException {
      return this._res[this._idx].getRef(col, map, joins);
   }

   public short getShort(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getShort(col, joins);
   }

   public String getString(Column col, Joins joins) throws SQLException {
      return this._res[this._idx].getString(col, joins);
   }

   public Time getTime(Column col, Calendar cal, Joins joins) throws SQLException {
      return this._res[this._idx].getTime(col, cal, joins);
   }

   public Timestamp getTimestamp(Column col, Calendar cal, Joins joins) throws SQLException {
      return this._res[this._idx].getTimestamp(col, cal, joins);
   }

   public boolean wasNull() throws SQLException {
      return this._res[this._idx].wasNull();
   }

   public void startDataRequest(Object mapping) {
      for(int i = 0; i < this._res.length; ++i) {
         this._res[i].startDataRequest(mapping);
      }

   }

   public void endDataRequest() {
      for(int i = 0; i < this._res.length; ++i) {
         this._res[i].endDataRequest();
      }

   }

   public interface ResultComparator extends Comparator {
      Object getOrderingValue(Result var1, int var2);
   }
}
