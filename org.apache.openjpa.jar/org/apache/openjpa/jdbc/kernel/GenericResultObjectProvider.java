package org.apache.openjpa.jdbc.kernel;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UnsupportedException;

public class GenericResultObjectProvider implements ResultObjectProvider {
   private final ClassMapping _mapping;
   private final JDBCStore _store;
   private final JDBCFetchConfiguration _fetch;
   private final Result _res;

   public GenericResultObjectProvider(Class pcClass, JDBCStore store, JDBCFetchConfiguration fetch, Result res) {
      this(store.getConfiguration().getMappingRepositoryInstance().getMapping(pcClass, store.getContext().getClassLoader(), true), store, fetch, res);
   }

   public GenericResultObjectProvider(ClassMapping mapping, JDBCStore store, JDBCFetchConfiguration fetch, Result res) {
      this._mapping = mapping;
      this._store = store;
      if (fetch == null) {
         this._fetch = store.getFetchConfiguration();
      } else {
         this._fetch = fetch;
      }

      this._res = res;
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
      return ((JDBCStoreManager)this._store).load(this._mapping, this._fetch, StoreContext.EXCLUDE_ALL, this._res);
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
