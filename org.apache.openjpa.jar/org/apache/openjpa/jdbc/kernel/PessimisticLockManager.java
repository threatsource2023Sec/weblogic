package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.kernel.VersionLockManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.LockException;

public class PessimisticLockManager extends VersionLockManager implements JDBCLockManager {
   public static final int LOCK_DATASTORE_ONLY = 1;
   private static final Localizer _loc = Localizer.forPackage(PessimisticLockManager.class);
   private JDBCStore _store;

   public PessimisticLockManager() {
      this.setVersionCheckOnReadLock(false);
      this.setVersionUpdateOnWriteLock(false);
   }

   public void setContext(StoreContext ctx) {
      super.setContext(ctx);
      this._store = (JDBCStore)ctx.getStoreManager().getInnermostDelegate();
   }

   public boolean selectForUpdate(Select sel, int lockLevel) {
      if (lockLevel == 0) {
         return false;
      } else {
         DBDictionary dict = this._store.getDBDictionary();
         if (dict.simulateLocking) {
            return false;
         } else {
            dict.assertSupport(dict.supportsSelectForUpdate, "SupportsSelectForUpdate");
            if (!sel.supportsLocking()) {
               if (this.log.isInfoEnabled()) {
                  this.log.info(_loc.get("cant-lock-on-load", (Object)sel.toSelect(false, (JDBCFetchConfiguration)null).getSQL()));
               }

               return false;
            } else {
               this.ensureStoreManagerTransaction();
               return true;
            }
         }
      }
   }

   public void loadedForUpdate(OpenJPAStateManager sm) {
      if (this.getLockLevel(sm) == 0) {
         this.setLockLevel(sm, 1);
      }

   }

   protected void lockInternal(OpenJPAStateManager sm, int level, int timeout, Object sdata) {
      if (this.getLockLevel(sm) == 0) {
         ConnectionInfo info = (ConnectionInfo)sdata;
         if (info == null || info.result == null || !info.result.isLocking()) {
            this.lockRow(sm, timeout);
         }
      }

      super.lockInternal(sm, level, timeout, sdata);
   }

   private void lockRow(OpenJPAStateManager sm, int timeout) {
      DBDictionary dict = this._store.getDBDictionary();
      if (!dict.simulateLocking) {
         dict.assertSupport(dict.supportsSelectForUpdate, "SupportsSelectForUpdate");
         Object id = sm.getObjectId();

         ClassMapping mapping;
         for(mapping = (ClassMapping)sm.getMetaData(); mapping.getJoinablePCSuperclassMapping() != null; mapping = mapping.getJoinablePCSuperclassMapping()) {
         }

         Select select = this._store.getSQLFactory().newSelect();
         select.select(mapping.getPrimaryKeyColumns());
         select.wherePrimaryKey(id, mapping, this._store);
         SQLBuffer sql = select.toSelect(true, this._store.getFetchConfiguration());
         this.ensureStoreManagerTransaction();
         Connection conn = this._store.getConnection();
         PreparedStatement stmnt = null;
         ResultSet rs = null;

         try {
            stmnt = this.prepareStatement(conn, sql);
            this.setTimeout(stmnt, timeout);
            rs = this.executeQuery(conn, stmnt, sql);
            this.checkLock(rs, sm);
         } catch (SQLException var25) {
            throw SQLExceptions.getStore(var25, dict);
         } finally {
            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var24) {
               }
            }

            if (rs != null) {
               try {
                  rs.close();
               } catch (SQLException var23) {
               }
            }

            try {
               conn.close();
            } catch (SQLException var22) {
            }

         }

      }
   }

   private void ensureStoreManagerTransaction() {
      if (!this._store.getContext().isStoreActive()) {
         this._store.getContext().beginStore();
         if (this.log.isInfoEnabled()) {
            this.log.info(_loc.get("start-trans-for-lock"));
         }
      }

   }

   public JDBCStore getStore() {
      return this._store;
   }

   protected PreparedStatement prepareStatement(Connection conn, SQLBuffer sql) throws SQLException {
      return sql.prepareStatement(conn);
   }

   protected void setTimeout(PreparedStatement stmnt, int timeout) throws SQLException {
      DBDictionary dict = this._store.getDBDictionary();
      if (timeout >= 0 && dict.supportsQueryTimeout) {
         if (timeout < 1000) {
            timeout = 1000;
            if (this.log.isWarnEnabled()) {
               this.log.warn(_loc.get("millis-query-timeout"));
            }
         }

         stmnt.setQueryTimeout(timeout / 1000);
      }

   }

   protected ResultSet executeQuery(Connection conn, PreparedStatement stmnt, SQLBuffer sql) throws SQLException {
      return stmnt.executeQuery();
   }

   protected void checkLock(ResultSet rs, OpenJPAStateManager sm) throws SQLException {
      if (!rs.next()) {
         throw new LockException(sm.getManagedInstance());
      }
   }
}
