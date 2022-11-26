package org.apache.openjpa.jdbc.sql;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.kernel.JDBCStoreManager;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.util.UnsupportedException;
import serp.util.Strings;

public abstract class AbstractResult implements Result {
   private static final Joins JOINS = new NoOpJoins();
   private Map _eager = null;
   private ClassMapping _base = null;
   private int _index = 0;
   private boolean _gotEager = false;
   private boolean _wasNull = false;
   private boolean _locking = false;
   private boolean _ignoreNext = false;
   private boolean _last = false;
   private FieldMapping _mappedByFieldMapping = null;
   private Object _mappedByValue = null;

   public Object getEager(FieldMapping key) {
      Map map = this.getEagerMap(true);
      return map == null ? null : map.get(key);
   }

   public void putEager(FieldMapping key, Object res) {
      Map map = this.getEagerMap(false);
      if (map == null) {
         map = new HashMap();
         this.setEagerMap((Map)map);
      }

      ((Map)map).put(key, res);
   }

   protected Map getEagerMap(boolean client) {
      if (client) {
         this._gotEager = true;
      }

      return this._eager;
   }

   protected void setEagerMap(Map eager) {
      this._eager = eager;
   }

   public void close() {
      this.closeEagerMap(this._eager);
      this._mappedByFieldMapping = null;
      this._mappedByValue = null;
   }

   protected void closeEagerMap(Map eager) {
      if (eager != null) {
         Iterator itr = eager.values().iterator();

         while(itr.hasNext()) {
            Object res = itr.next();
            if (res != this && res instanceof Closeable) {
               try {
                  ((Closeable)res).close();
               } catch (Exception var5) {
               }
            }
         }
      }

   }

   public boolean supportsRandomAccess() throws SQLException {
      return false;
   }

   public boolean absolute(int row) throws SQLException {
      this._gotEager = false;
      return this.absoluteInternal(row);
   }

   protected boolean absoluteInternal(int row) throws SQLException {
      throw new UnsupportedException();
   }

   public boolean next() throws SQLException {
      this._gotEager = false;
      if (this._ignoreNext) {
         this._ignoreNext = false;
         return this._last;
      } else {
         this._last = this.nextInternal();
         return this._last;
      }
   }

   protected abstract boolean nextInternal() throws SQLException;

   public void pushBack() throws SQLException {
      this._ignoreNext = true;
   }

   public Joins newJoins() {
      return JOINS;
   }

   public boolean contains(Object obj) throws SQLException {
      return this.containsInternal(obj, (Joins)null);
   }

   public boolean containsAll(Object[] objs) throws SQLException {
      return this.containsAllInternal(objs, (Joins)null);
   }

   public boolean contains(Column col, Joins joins) throws SQLException {
      return this.containsInternal(col, joins);
   }

   public boolean containsAll(Column[] cols, Joins joins) throws SQLException {
      return this.containsAllInternal(cols, joins);
   }

   protected abstract boolean containsInternal(Object var1, Joins var2) throws SQLException;

   protected boolean containsAllInternal(Object[] objs, Joins joins) throws SQLException {
      for(int i = 0; i < objs.length; ++i) {
         if (!this.containsInternal(objs[i], joins)) {
            return false;
         }
      }

      return true;
   }

   public ClassMapping getBaseMapping() {
      return this._gotEager ? null : this._base;
   }

   public void setBaseMapping(ClassMapping base) {
      this._base = base;
   }

   public FieldMapping getMappedByFieldMapping() {
      return this._gotEager ? null : this._mappedByFieldMapping;
   }

   public void setMappedByFieldMapping(FieldMapping fieldMapping) {
      this._mappedByFieldMapping = fieldMapping;
   }

   public Object getMappedByValue() {
      return this._gotEager ? null : this._mappedByValue;
   }

   public void setMappedByValue(Object mappedByValue) {
      this._mappedByValue = mappedByValue;
   }

   public int indexOf() {
      return this._index;
   }

   public void setIndexOf(int idx) {
      this._index = idx;
   }

   public Object load(ClassMapping mapping, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
      return this.load(mapping, store, fetch, (Joins)null);
   }

   public Object load(ClassMapping mapping, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) throws SQLException {
      return ((JDBCStoreManager)store).load(mapping, (JDBCFetchConfiguration)fetch, (BitSet)null, this);
   }

   public Array getArray(Object obj) throws SQLException {
      return this.getArrayInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public Array getArray(Column col, Joins joins) throws SQLException {
      return this.getArrayInternal(this.translate(col, joins), joins);
   }

   protected Array getArrayInternal(Object obj, Joins joins) throws SQLException {
      return (Array)this.checkNull(this.getObjectInternal(obj, 1000, (Object)null, joins));
   }

   public InputStream getAsciiStream(Object obj) throws SQLException {
      return this.getAsciiStreamInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public InputStream getAsciiStream(Column col, Joins joins) throws SQLException {
      return this.getAsciiStreamInternal(this.translate(col, joins), joins);
   }

   protected InputStream getAsciiStreamInternal(Object obj, Joins joins) throws SQLException {
      return (InputStream)this.checkNull(this.getObjectInternal(obj, 1001, (Object)null, joins));
   }

   public BigDecimal getBigDecimal(Object obj) throws SQLException {
      return this.getBigDecimalInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public BigDecimal getBigDecimal(Column col, Joins joins) throws SQLException {
      return this.getBigDecimalInternal(this.translate(col, joins), joins);
   }

   protected BigDecimal getBigDecimalInternal(Object obj, Joins joins) throws SQLException {
      Object val = this.checkNull(this.getObjectInternal(obj, 24, (Object)null, joins));
      if (val == null) {
         return null;
      } else {
         return val instanceof BigDecimal ? (BigDecimal)val : new BigDecimal(val.toString());
      }
   }

   public BigInteger getBigInteger(Object obj) throws SQLException {
      return this.getBigIntegerInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public BigInteger getBigInteger(Column col, Joins joins) throws SQLException {
      return this.getBigIntegerInternal(this.translate(col, joins), joins);
   }

   protected BigInteger getBigIntegerInternal(Object obj, Joins joins) throws SQLException {
      Object val = this.checkNull(this.getObjectInternal(obj, 25, (Object)null, joins));
      if (val == null) {
         return null;
      } else {
         return val instanceof BigInteger ? (BigInteger)val : new BigInteger(val.toString());
      }
   }

   public InputStream getBinaryStream(Object obj) throws SQLException {
      return this.getBinaryStreamInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public InputStream getBinaryStream(Column col, Joins joins) throws SQLException {
      return this.getBinaryStreamInternal(this.translate(col, joins), joins);
   }

   public InputStream getLOBStream(JDBCStore store, Object obj) throws SQLException {
      return this.getLOBStreamInternal(store, this.translate(obj, (Joins)null), (Joins)null);
   }

   protected InputStream getBinaryStreamInternal(Object obj, Joins joins) throws SQLException {
      return (InputStream)this.checkNull(this.getObjectInternal(obj, 1002, (Object)null, joins));
   }

   protected InputStream getLOBStreamInternal(JDBCStore store, Object obj, Joins joins) throws SQLException {
      return (InputStream)this.checkNull(this.getStreamInternal(store, obj, 1002, (Object)null, joins));
   }

   public Blob getBlob(Object obj) throws SQLException {
      return this.getBlobInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public Blob getBlob(Column col, Joins joins) throws SQLException {
      return this.getBlobInternal(this.translate(col, joins), joins);
   }

   protected Blob getBlobInternal(Object obj, Joins joins) throws SQLException {
      return (Blob)this.checkNull(this.getObjectInternal(obj, 1003, (Object)null, joins));
   }

   public boolean getBoolean(Object obj) throws SQLException {
      return this.getBooleanInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public boolean getBoolean(Column col, Joins joins) throws SQLException {
      return this.getBooleanInternal(this.translate(col, joins), joins);
   }

   protected boolean getBooleanInternal(Object obj, Joins joins) throws SQLException {
      Object val = this.checkNull(this.getObjectInternal(obj, 0, (Object)null, joins));
      return val == null ? false : Boolean.valueOf(val.toString());
   }

   public byte getByte(Object obj) throws SQLException {
      return this.getByteInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public byte getByte(Column col, Joins joins) throws SQLException {
      return this.getByteInternal(this.translate(col, joins), joins);
   }

   protected byte getByteInternal(Object obj, Joins joins) throws SQLException {
      Number val = (Number)this.checkNull(this.getObjectInternal(obj, 1, (Object)null, joins));
      return val == null ? 0 : val.byteValue();
   }

   public byte[] getBytes(Object obj) throws SQLException {
      return this.getBytesInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public byte[] getBytes(Column col, Joins joins) throws SQLException {
      return this.getBytesInternal(this.translate(col, joins), joins);
   }

   protected byte[] getBytesInternal(Object obj, Joins joins) throws SQLException {
      return (byte[])((byte[])this.checkNull(this.getObjectInternal(obj, 1004, (Object)null, joins)));
   }

   public Calendar getCalendar(Object obj) throws SQLException {
      return this.getCalendarInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public Calendar getCalendar(Column col, Joins joins) throws SQLException {
      return this.getCalendarInternal(this.translate(col, joins), joins);
   }

   protected Calendar getCalendarInternal(Object obj, Joins joins) throws SQLException {
      Object val = this.checkNull(this.getObjectInternal(obj, 28, (Object)null, joins));
      if (val == null) {
         return null;
      } else if (val instanceof Calendar) {
         return (Calendar)val;
      } else {
         Calendar cal = Calendar.getInstance();
         cal.setTime(new Date(val.toString()));
         return cal;
      }
   }

   public char getChar(Object obj) throws SQLException {
      return this.getCharInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public char getChar(Column col, Joins joins) throws SQLException {
      return this.getCharInternal(this.translate(col, joins), joins);
   }

   protected char getCharInternal(Object obj, Joins joins) throws SQLException {
      Object val = this.checkNull(this.getObjectInternal(obj, 2, (Object)null, joins));
      if (val == null) {
         return '\u0000';
      } else if (val instanceof Character) {
         return (Character)val;
      } else {
         String str = val.toString();
         return str.length() == 0 ? '\u0000' : str.charAt(0);
      }
   }

   public Reader getCharacterStream(Object obj) throws SQLException {
      return this.getCharacterStreamInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public Reader getCharacterStream(Column col, Joins joins) throws SQLException {
      return this.getCharacterStreamInternal(this.translate(col, joins), joins);
   }

   protected Reader getCharacterStreamInternal(Object obj, Joins joins) throws SQLException {
      Object val = this.checkNull(this.getObjectInternal(obj, 1005, (Object)null, joins));
      if (val == null) {
         return null;
      } else {
         return (Reader)(val instanceof Reader ? (Reader)val : new StringReader(val.toString()));
      }
   }

   public Clob getClob(Object obj) throws SQLException {
      return this.getClobInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public Clob getClob(Column col, Joins joins) throws SQLException {
      return this.getClobInternal(this.translate(col, joins), joins);
   }

   protected Clob getClobInternal(Object obj, Joins joins) throws SQLException {
      return (Clob)this.checkNull(this.getObjectInternal(obj, 1006, (Object)null, joins));
   }

   public Date getDate(Object obj) throws SQLException {
      return this.getDateInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public Date getDate(Column col, Joins joins) throws SQLException {
      return this.getDateInternal(this.translate(col, joins), joins);
   }

   protected Date getDateInternal(Object obj, Joins joins) throws SQLException {
      Object val = this.checkNull(this.getObjectInternal(obj, 14, (Object)null, joins));
      if (val == null) {
         return null;
      } else {
         return val instanceof Date ? (Date)val : new Date(val.toString());
      }
   }

   public java.sql.Date getDate(Object obj, Calendar cal) throws SQLException {
      return this.getDateInternal(this.translate(obj, (Joins)null), cal, (Joins)null);
   }

   public java.sql.Date getDate(Column col, Calendar cal, Joins joins) throws SQLException {
      return this.getDateInternal(this.translate(col, joins), cal, joins);
   }

   protected java.sql.Date getDateInternal(Object obj, Calendar cal, Joins joins) throws SQLException {
      return (java.sql.Date)this.checkNull(this.getObjectInternal(obj, 1007, cal, joins));
   }

   public double getDouble(Object obj) throws SQLException {
      return this.getDoubleInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public double getDouble(Column col, Joins joins) throws SQLException {
      return this.getDoubleInternal(this.translate(col, joins), joins);
   }

   protected double getDoubleInternal(Object obj, Joins joins) throws SQLException {
      Number val = (Number)this.checkNull(this.getObjectInternal(obj, 3, (Object)null, joins));
      return val == null ? 0.0 : val.doubleValue();
   }

   public float getFloat(Object obj) throws SQLException {
      return this.getFloatInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public float getFloat(Column col, Joins joins) throws SQLException {
      return this.getFloatInternal(this.translate(col, joins), joins);
   }

   protected float getFloatInternal(Object obj, Joins joins) throws SQLException {
      Number val = (Number)this.checkNull(this.getObjectInternal(obj, 4, (Object)null, joins));
      return val == null ? 0.0F : val.floatValue();
   }

   public int getInt(Object obj) throws SQLException {
      return this.getIntInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public int getInt(Column col, Joins joins) throws SQLException {
      return this.getIntInternal(this.translate(col, joins), joins);
   }

   protected int getIntInternal(Object obj, Joins joins) throws SQLException {
      Number val = (Number)this.checkNull(this.getObjectInternal(obj, 5, (Object)null, joins));
      return val == null ? 0 : val.intValue();
   }

   public Locale getLocale(Object obj) throws SQLException {
      return this.getLocaleInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public Locale getLocale(Column col, Joins joins) throws SQLException {
      return this.getLocaleInternal(this.translate(col, joins), joins);
   }

   protected Locale getLocaleInternal(Object obj, Joins joins) throws SQLException {
      Object val = this.checkNull(this.getObjectInternal(obj, 26, (Object)null, joins));
      if (val == null) {
         return null;
      } else if (val instanceof Locale) {
         return (Locale)val;
      } else {
         String[] vals = Strings.split(val.toString(), "_", 0);
         if (vals.length < 2) {
            throw new SQLException(val.toString());
         } else {
            return vals.length == 2 ? new Locale(vals[0], vals[1]) : new Locale(vals[0], vals[1], vals[2]);
         }
      }
   }

   public long getLong(Object obj) throws SQLException {
      return this.getLongInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public long getLong(Column col, Joins joins) throws SQLException {
      return this.getLongInternal(this.translate(col, joins), joins);
   }

   protected long getLongInternal(Object obj, Joins joins) throws SQLException {
      Number val = (Number)this.checkNull(this.getObjectInternal(obj, 5, (Object)null, joins));
      return val == null ? 0L : (long)val.intValue();
   }

   public Number getNumber(Object obj) throws SQLException {
      return this.getNumberInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public Number getNumber(Column col, Joins joins) throws SQLException {
      return this.getNumberInternal(this.translate(col, joins), joins);
   }

   protected Number getNumberInternal(Object obj, Joins joins) throws SQLException {
      Object val = this.checkNull(this.getObjectInternal(obj, 10, (Object)null, joins));
      if (val == null) {
         return null;
      } else {
         return (Number)(val instanceof Number ? (Number)val : new BigDecimal(val.toString()));
      }
   }

   public Object getObject(Object obj, int metaType, Object arg) throws SQLException {
      return this.getObjectInternal(obj, metaType, arg, (Joins)null);
   }

   public Object getObject(Column col, Object arg, Joins joins) throws SQLException {
      return this.getObjectInternal(col, col.getJavaType(), arg, joins);
   }

   protected abstract Object getObjectInternal(Object var1, int var2, Object var3, Joins var4) throws SQLException;

   protected abstract Object getStreamInternal(JDBCStore var1, Object var2, int var3, Object var4, Joins var5) throws SQLException;

   public Object getSQLObject(Object obj, Map map) throws SQLException {
      return this.getSQLObjectInternal(this.translate(obj, (Joins)null), map, (Joins)null);
   }

   public Object getSQLObject(Column col, Map map, Joins joins) throws SQLException {
      return this.getSQLObjectInternal(this.translate(col, joins), map, joins);
   }

   protected Object getSQLObjectInternal(Object obj, Map map, Joins joins) throws SQLException {
      return this.checkNull(this.getObjectInternal(obj, 1008, map, joins));
   }

   public Ref getRef(Object obj, Map map) throws SQLException {
      return this.getRefInternal(this.translate(obj, (Joins)null), map, (Joins)null);
   }

   public Ref getRef(Column col, Map map, Joins joins) throws SQLException {
      return this.getRefInternal(this.translate(col, joins), map, joins);
   }

   protected Ref getRefInternal(Object obj, Map map, Joins joins) throws SQLException {
      return (Ref)this.checkNull(this.getObjectInternal(obj, 1009, map, joins));
   }

   public short getShort(Object obj) throws SQLException {
      return this.getShortInternal(this.translate(obj, (Joins)null), (Joins)null);
   }

   public short getShort(Column col, Joins joins) throws SQLException {
      return this.getShortInternal(this.translate(col, joins), joins);
   }

   protected short getShortInternal(Object obj, Joins joins) throws SQLException {
      Number val = (Number)this.checkNull(this.getObjectInternal(obj, 7, (Object)null, joins));
      return val == null ? 0 : val.shortValue();
   }

   public String getString(Object obj) throws SQLException {
      return this.getStringInternal(this.translate(obj, (Joins)null), (Joins)null, obj instanceof Column && ((Column)obj).getType() == 2005);
   }

   public String getString(Column col, Joins joins) throws SQLException {
      return this.getStringInternal(this.translate(col, joins), joins, col.getType() == 2005);
   }

   protected String getStringInternal(Object obj, Joins joins, boolean isClobString) throws SQLException {
      Object val = this.checkNull(this.getObjectInternal(obj, 9, (Object)null, joins));
      return val == null ? null : val.toString();
   }

   public Time getTime(Object obj, Calendar cal) throws SQLException {
      return this.getTimeInternal(this.translate(obj, (Joins)null), cal, (Joins)null);
   }

   public Time getTime(Column col, Calendar cal, Joins joins) throws SQLException {
      return this.getTimeInternal(this.translate(col, joins), cal, joins);
   }

   protected Time getTimeInternal(Object obj, Calendar cal, Joins joins) throws SQLException {
      return (Time)this.checkNull(this.getObjectInternal(obj, 1010, cal, joins));
   }

   public Timestamp getTimestamp(Object obj, Calendar cal) throws SQLException {
      return this.getTimestampInternal(this.translate(obj, (Joins)null), cal, (Joins)null);
   }

   public Timestamp getTimestamp(Column col, Calendar cal, Joins joins) throws SQLException {
      return this.getTimestampInternal(this.translate(col, joins), cal, joins);
   }

   protected Timestamp getTimestampInternal(Object obj, Calendar cal, Joins joins) throws SQLException {
      return (Timestamp)this.checkNull(this.getObjectInternal(obj, 1011, cal, joins));
   }

   public boolean wasNull() throws SQLException {
      return this._wasNull;
   }

   protected Object checkNull(Object val) {
      this._wasNull = val == null;
      return val;
   }

   public void setLocking(boolean locking) {
      this._locking = locking;
   }

   public boolean isLocking() {
      return this._locking;
   }

   public void startDataRequest(Object mapping) {
   }

   public void endDataRequest() {
   }

   protected Object translate(Object obj, Joins joins) throws SQLException {
      return obj;
   }

   private static class NoOpJoins implements Joins {
      private NoOpJoins() {
      }

      public boolean isEmpty() {
         return true;
      }

      public boolean isOuter() {
         return false;
      }

      public Joins crossJoin(Table localTable, Table foreignTable) {
         return this;
      }

      public Joins join(ForeignKey fk, boolean inverse, boolean toMany) {
         return this;
      }

      public Joins outerJoin(ForeignKey fk, boolean inverse, boolean toMany) {
         return this;
      }

      public Joins joinRelation(String name, ForeignKey fk, ClassMapping target, int subs, boolean inverse, boolean toMany) {
         return this;
      }

      public Joins outerJoinRelation(String name, ForeignKey fk, ClassMapping target, int subs, boolean inverse, boolean toMany) {
         return this;
      }

      public Joins setVariable(String var) {
         return this;
      }

      public Joins setSubselect(String alias) {
         return this;
      }

      public void appendTo(SQLBuffer buf) {
      }

      // $FF: synthetic method
      NoOpJoins(Object x0) {
         this();
      }
   }
}
