package org.apache.openjpa.jdbc.kernel;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.sql.ResultSetResult;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.ResultPacker;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UnsupportedException;
import serp.util.Numbers;

class SQLProjectionResultObjectProvider implements ResultObjectProvider {
   private final JDBCStore _store;
   private final JDBCFetchConfiguration _fetch;
   private final ResultSetResult _res;
   private final ResultPacker _packer;
   private final int _cols;

   public SQLProjectionResultObjectProvider(JDBCStore store, JDBCFetchConfiguration fetch, ResultSetResult res, Class cls) throws SQLException {
      this._store = store;
      this._fetch = fetch;
      ResultSetMetaData meta = res.getResultSet().getMetaData();
      this._res = res;
      this._cols = meta.getColumnCount();
      if (cls != null) {
         String[] aliases = new String[this._cols];

         for(int i = 0; i < this._cols; ++i) {
            aliases[i] = meta.getColumnLabel(i + 1);
         }

         this._packer = new ResultPacker((Class[])null, aliases, cls);
      } else {
         this._packer = null;
      }

   }

   public boolean supportsRandomAccess() {
      try {
         return this._res.supportsRandomAccess();
      } catch (Throwable var2) {
         return false;
      }
   }

   public void open() {
   }

   public Object getResultObject() throws SQLException {
      if (this._cols == 1) {
         Object val = this._res.getObject(Numbers.valueOf(1), 1012, (Object)null);
         return this._packer == null ? val : this._packer.pack(val);
      } else {
         Object[] vals = new Object[this._cols];

         for(int i = 0; i < vals.length; ++i) {
            vals[i] = this._res.getObject(Numbers.valueOf(i + 1), 1012, (Object)null);
         }

         return this._packer == null ? vals : this._packer.pack(vals);
      }
   }

   public boolean next() throws SQLException {
      return this._res.next();
   }

   public boolean absolute(int pos) throws SQLException {
      return this._res.absolute(pos);
   }

   public int size() throws SQLException {
      return this._fetch.getLRSSize() != 0 && this.supportsRandomAccess() ? this._res.size() : Integer.MAX_VALUE;
   }

   public void reset() {
      throw new UnsupportedException();
   }

   public void close() {
      this._res.close();
   }

   public void handleCheckedException(Exception e) {
      if (e instanceof SQLException) {
         throw SQLExceptions.getStore((SQLException)e, this._store.getDBDictionary());
      } else {
         throw new StoreException(e);
      }
   }
}
