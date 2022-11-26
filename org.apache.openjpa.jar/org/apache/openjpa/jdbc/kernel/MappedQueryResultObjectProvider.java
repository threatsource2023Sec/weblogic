package org.apache.openjpa.jdbc.kernel;

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
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.QueryResultMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.AbstractResult;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UnsupportedException;

class MappedQueryResultObjectProvider implements ResultObjectProvider {
   private final QueryResultMapping _map;
   private final JDBCStore _store;
   private final JDBCFetchConfiguration _fetch;
   private final MappingResult _mres;

   public MappedQueryResultObjectProvider(QueryResultMapping map, JDBCStore store, JDBCFetchConfiguration fetch, Result res) {
      this._map = map;
      this._store = store;
      this._fetch = fetch == null ? store.getFetchConfiguration() : fetch;
      this._mres = new MappingResult(res);
   }

   public boolean supportsRandomAccess() {
      try {
         return this._mres.supportsRandomAccess();
      } catch (Throwable var2) {
         return false;
      }
   }

   public void open() {
   }

   public Object getResultObject() throws SQLException {
      QueryResultMapping.PCResult[] pcs = this._map.getPCResults();
      Object[] cols = this._map.getColumnResults();
      if (pcs.length == 0 && cols.length == 1) {
         return this._mres.getObject(cols[0], 1012, (Object)null);
      } else if (pcs.length == 1 && cols.length == 0) {
         return this._mres.load(pcs[0], this._store, this._fetch);
      } else {
         Object[] ret = new Object[pcs.length + cols.length];

         int i;
         for(i = 0; i < pcs.length; ++i) {
            ret[i] = this._mres.load(pcs[i], this._store, this._fetch);
         }

         for(i = 0; i < cols.length; ++i) {
            ret[pcs.length + i] = this._mres.getObject(cols[i], 1012, (Object)null);
         }

         return ret;
      }
   }

   public boolean next() throws SQLException {
      return this._mres.next();
   }

   public boolean absolute(int pos) throws SQLException {
      return this._mres.absolute(pos);
   }

   public int size() throws SQLException {
      return this._fetch.getLRSSize() != 0 && this.supportsRandomAccess() ? this._mres.size() : Integer.MAX_VALUE;
   }

   public void reset() {
      throw new UnsupportedException();
   }

   public void close() {
      this._mres.close();
   }

   public void handleCheckedException(Exception e) {
      if (e instanceof SQLException) {
         throw SQLExceptions.getStore((SQLException)e, this._store.getDBDictionary());
      } else {
         throw new StoreException(e);
      }
   }

   private static class MappingResult extends AbstractResult {
      private final Result _res;
      private final Stack _requests = new Stack();
      private QueryResultMapping.PCResult _pc = null;

      public MappingResult(Result res) {
         this._res = res;
      }

      public Object load(QueryResultMapping.PCResult pc, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
         this._pc = pc;

         Object var4;
         try {
            var4 = this.load(pc.getCandidateTypeMapping(), store, fetch);
         } finally {
            this._pc = null;
         }

         return var4;
      }

      public Object load(ClassMapping mapping, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
         return this.load(mapping, store, fetch, (Joins)null);
      }

      public Object load(ClassMapping mapping, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) throws SQLException {
         return this._pc == null ? super.load(mapping, store, fetch, joins) : ((JDBCStoreManager)store).load(mapping, (JDBCFetchConfiguration)fetch, (BitSet)this._pc.getExcludes(this._requests), this);
      }

      public Object getEager(FieldMapping key) {
         Object ret = this._res.getEager(key);
         if (this._pc != null && ret == null) {
            return this._pc.hasEager(this._requests, key) ? this : null;
         } else {
            return ret;
         }
      }

      public void putEager(FieldMapping key, Object res) {
         this._res.putEager(key, res);
      }

      public void close() {
         this._res.close();
      }

      public Joins newJoins() {
         return this._res.newJoins();
      }

      public boolean supportsRandomAccess() throws SQLException {
         return this._res.supportsRandomAccess();
      }

      public ClassMapping getBaseMapping() {
         return this._res.getBaseMapping();
      }

      public int size() throws SQLException {
         return this._res.size();
      }

      public void startDataRequest(Object mapping) {
         this._requests.push(mapping);
      }

      public void endDataRequest() {
         this._requests.pop();
      }

      public boolean wasNull() throws SQLException {
         return this._res.wasNull();
      }

      protected Object translate(Object obj, Joins joins) {
         return this._pc == null ? obj : this._pc.map(this._requests, obj, joins);
      }

      protected boolean absoluteInternal(int row) throws SQLException {
         return this._res.absolute(row);
      }

      protected boolean nextInternal() throws SQLException {
         return this._res.next();
      }

      protected boolean containsInternal(Object obj, Joins joins) throws SQLException {
         return this._res.contains(this.translate(obj, joins));
      }

      protected Array getArrayInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getArray((Column)obj, joins) : this._res.getArray(obj);
      }

      protected InputStream getAsciiStreamInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getAsciiStream((Column)obj, joins) : this._res.getAsciiStream(obj);
      }

      protected BigDecimal getBigDecimalInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getBigDecimal((Column)obj, joins) : this._res.getBigDecimal(obj);
      }

      protected Number getNumberInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getNumber((Column)obj, joins) : this._res.getNumber(obj);
      }

      protected BigInteger getBigIntegerInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getBigInteger((Column)obj, joins) : this._res.getBigInteger(obj);
      }

      protected InputStream getBinaryStreamInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getBinaryStream((Column)obj, joins) : this._res.getBinaryStream(obj);
      }

      protected Blob getBlobInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getBlob((Column)obj, joins) : this._res.getBlob(obj);
      }

      protected boolean getBooleanInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getBoolean((Column)obj, joins) : this._res.getBoolean(obj);
      }

      protected byte getByteInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getByte((Column)obj, joins) : this._res.getByte(obj);
      }

      protected byte[] getBytesInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getBytes((Column)obj, joins) : this._res.getBytes(obj);
      }

      protected Calendar getCalendarInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getCalendar((Column)obj, joins) : this._res.getCalendar(obj);
      }

      protected char getCharInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getChar((Column)obj, joins) : this._res.getChar(obj);
      }

      protected Reader getCharacterStreamInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getCharacterStream((Column)obj, joins) : this._res.getCharacterStream(obj);
      }

      protected Clob getClobInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getClob((Column)obj, joins) : this._res.getClob(obj);
      }

      protected Date getDateInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getDate((Column)obj, joins) : this._res.getDate(obj);
      }

      protected java.sql.Date getDateInternal(Object obj, Calendar cal, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getDate((Column)obj, cal, joins) : this._res.getDate(obj, cal);
      }

      protected double getDoubleInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getDouble((Column)obj, joins) : this._res.getDouble(obj);
      }

      protected float getFloatInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getFloat((Column)obj, joins) : this._res.getFloat(obj);
      }

      protected int getIntInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getInt((Column)obj, joins) : this._res.getInt(obj);
      }

      protected Locale getLocaleInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getLocale((Column)obj, joins) : this._res.getLocale(obj);
      }

      protected long getLongInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getLong((Column)obj, joins) : this._res.getLong(obj);
      }

      protected Object getObjectInternal(Object obj, int metaTypeCode, Object arg, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getObject((Column)obj, arg, joins) : this._res.getObject(obj, metaTypeCode, arg);
      }

      protected Object getSQLObjectInternal(Object obj, Map map, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getSQLObject((Column)obj, map, joins) : this._res.getSQLObject(obj, map);
      }

      protected Object getStreamInternal(JDBCStore store, Object obj, int metaTypeCode, Object arg, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getObject((Column)obj, arg, joins) : this._res.getObject(obj, metaTypeCode, arg);
      }

      protected Ref getRefInternal(Object obj, Map map, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getRef((Column)obj, map, joins) : this._res.getRef(obj, map);
      }

      protected short getShortInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getShort((Column)obj, joins) : this._res.getShort(obj);
      }

      protected String getStringInternal(Object obj, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getString((Column)obj, joins) : this._res.getString(obj);
      }

      protected Time getTimeInternal(Object obj, Calendar cal, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getTime((Column)obj, cal, joins) : this._res.getTime(obj, cal);
      }

      protected Timestamp getTimestampInternal(Object obj, Calendar cal, Joins joins) throws SQLException {
         return obj instanceof Column ? this._res.getTimestamp((Column)obj, cal, joins) : this._res.getTimestamp(obj, cal);
      }
   }
}
