package org.apache.openjpa.jdbc.kernel;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.jdbc.sql.SelectExecutor;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.util.StoreException;

public abstract class SelectResultObjectProvider implements ResultObjectProvider {
   private final SelectExecutor _sel;
   private final JDBCStore _store;
   private final JDBCFetchConfiguration _fetch;
   private Result _res = null;
   private int _size = -1;
   private Boolean _ra = null;

   public SelectResultObjectProvider(SelectExecutor sel, JDBCStore store, JDBCFetchConfiguration fetch) {
      this._sel = sel;
      this._store = store;
      this._fetch = fetch;
   }

   public SelectExecutor getSelect() {
      return this._sel;
   }

   public JDBCStore getStore() {
      return this._store;
   }

   public JDBCFetchConfiguration getFetchConfiguration() {
      return this._fetch;
   }

   public Result getResult() {
      return this._res;
   }

   public boolean supportsRandomAccess() {
      if (this._ra == null) {
         boolean ra;
         if (this._res != null) {
            try {
               ra = this._res.supportsRandomAccess();
            } catch (SQLException var3) {
               throw SQLExceptions.getStore(var3, this._store.getDBDictionary());
            }
         } else {
            ra = this._sel.supportsRandomAccess(this._fetch.getReadLockLevel() > 0);
         }

         this._ra = ra ? Boolean.TRUE : Boolean.FALSE;
      }

      return this._ra;
   }

   public void open() throws SQLException {
      this._res = this._sel.execute(this._store, this._fetch);
   }

   public boolean next() throws SQLException {
      return this._res.next();
   }

   public boolean absolute(int pos) throws SQLException {
      return this._res.absolute(pos);
   }

   public int size() throws SQLException {
      if (this._size == -1) {
         if (this._res == null) {
            return Integer.MAX_VALUE;
         }

         switch (this._fetch.getLRSSize()) {
            case 0:
               this._size = Integer.MAX_VALUE;
               break;
            case 1:
               if (this.supportsRandomAccess()) {
                  this._size = this._res.size();
               } else {
                  this._size = Integer.MAX_VALUE;
               }
               break;
            default:
               this._size = this._sel.getCount(this._store);
         }
      }

      return this._size;
   }

   protected void setSize(int size) {
      if (this._size == -1) {
         this._size = size;
      }

   }

   public void reset() throws SQLException {
      this.close();
      this.open();
   }

   public void close() {
      if (this._res != null) {
         this._res.close();
         this._res = null;
      }

   }

   public void handleCheckedException(Exception e) {
      if (e instanceof SQLException) {
         throw SQLExceptions.getStore((SQLException)e, this._store.getDBDictionary());
      } else {
         throw new StoreException(e);
      }
   }
}
