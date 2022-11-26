package kodo.jdbc.kernel;

import com.solarmetric.profile.MethodEnterEvent;
import com.solarmetric.profile.MethodExitEvent;
import com.solarmetric.profile.MethodInfoImpl;
import com.solarmetric.profile.ProfilingAgent;
import com.solarmetric.profile.ProfilingAgentProvider;
import com.solarmetric.profile.ProfilingCapable;
import com.solarmetric.profile.ProfilingEnvironment;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import kodo.jdbc.sql.AdvancedSQL;
import kodo.jdbc.sql.KodoSQLFactory;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.kernel.PreparedStatementManager;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.RowImpl;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.jdbc.SQLFormatter;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.OptimisticException;

class BatchingPreparedStatementManager implements PreparedStatementManager, ProfilingCapable {
   private static final Localizer _loc = Localizer.forPackage(BatchingPreparedStatementManager.class);
   private final JDBCStore _store;
   private final Connection _conn;
   private final DBDictionary _dict;
   private final AdvancedSQL _advanced;
   private final boolean _batch;
   private PreparedStatement _stmnt = null;
   private String _sql = null;
   private int _rowAction = -1;
   private final Collection _exceptions = new LinkedList();
   private final List _failedObjects = new ArrayList();
   private int _startIdx = -1;
   private int _updateIdx = 0;
   final StoreContext _ctx;

   public BatchingPreparedStatementManager(JDBCStore store, Connection conn, boolean batch) {
      this._store = store;
      this._ctx = store.getContext();
      this._dict = store.getDBDictionary();
      this._conn = conn;
      this._advanced = ((KodoSQLFactory)store.getSQLFactory()).getAdvancedSQL();
      this._batch = batch;
   }

   public Collection getExceptions() {
      return this._exceptions;
   }

   public void flush(RowImpl row) {
      try {
         this.flushInternal(row);
      } catch (SQLException var3) {
         this.clear();
         this._exceptions.add(SQLExceptions.getStore(var3, this._dict));
      } catch (OpenJPAException var4) {
         this.clear();
         this._exceptions.add(var4);
      }

   }

   private void flushInternal(RowImpl row) throws SQLException {
      Column[] autoAssign = null;
      if (row.getAction() == 1) {
         autoAssign = row.getTable().getAutoAssignedColumns();
      }

      boolean batchable = this._batch && (autoAssign == null || autoAssign.length == 0) && (row.getFailedObject() == null || this._advanced.getSupportsUpdateCountsForBatch() || this._advanced.getSupportsTotalCountsForBatch());
      if (batchable && (this._advanced.getBatchLimit() == -1 || this._updateIdx < this._advanced.getBatchLimit()) && (this._advanced.getBatchParameterLimit() == -1 || this._updateIdx * row.getParameterCount() < this._advanced.getBatchParameterLimit()) && row.getSQL(this._dict).equals(this._sql)) {
         this._stmnt.addBatch();
      } else {
         if (this._stmnt != null) {
            this.flushInternal();
         }

         this.clear();
         this._sql = row.getSQL(this._dict);
         this._stmnt = this._conn.prepareStatement(this._sql);
         this._rowAction = row.getAction();
      }

      if (row.getFailedObject() != null && this._startIdx == -1) {
         this._startIdx = this._updateIdx;
      }

      if (this._startIdx != -1) {
         this._failedObjects.add(row.getFailedObject());
      }

      ++this._updateIdx;
      row.flush(this._stmnt, this._dict, this._store);
      if (batchable && !this.canBatch(row)) {
         batchable = false;
      }

      if (!batchable) {
         try {
            if (this._stmnt != null) {
               this.flushInternal();
            }

            this.clear();
         } catch (SQLException var8) {
            throw SQLExceptions.getStore(var8, row.getFailedObject(), this._dict);
         }

         if (autoAssign != null && autoAssign.length > 0 && row.getPrimaryKey() != null) {
            OpenJPAStateManager sm = row.getPrimaryKey();
            ClassMapping mapping = (ClassMapping)sm.getMetaData();

            for(int i = 0; i < autoAssign.length; ++i) {
               Object val = this._dict.getGeneratedKey(autoAssign[i], this._conn);
               mapping.assertJoinable(autoAssign[i]).setAutoAssignedValue(sm, this._store, autoAssign[i], val);
            }
         }
      }

   }

   private boolean canBatch(RowImpl row) {
      if (this._sql == null) {
         return false;
      } else {
         Column[] cols = row.getColumns();

         for(int i = 0; i < cols.length; ++i) {
            if (row.getSet(cols[i]) != null && !this._advanced.canBatch(cols[i])) {
               return false;
            }
         }

         return true;
      }
   }

   public void flush() {
      try {
         if (this._stmnt != null) {
            this.flushInternal();
         }
      } catch (SQLException var6) {
         this._exceptions.add(SQLExceptions.getStore(var6, this._dict));
      } catch (OpenJPAException var7) {
         this._exceptions.add(var7);
      } finally {
         this.clear();
      }

   }

   private void flushInternal() throws SQLException {
      MethodInfoImpl var5 = null;
      ProfilingAgent var6 = null;
      if (this._ctx instanceof ProfilingAgentProvider) {
         var6 = ((ProfilingAgentProvider)this._ctx).getProfilingAgent();
         if (var6 != null) {
            String var7 = "Batch Size: " + this._updateIdx + "\n" + (new SQLFormatter()).prettyPrint(this._sql);
            var5 = new MethodInfoImpl("Store.flush()", var7);
            var5.setCategory("SQL");
            var6.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._ctx, var5));
         }
      }

      try {
         try {
            if (this._updateIdx == 1) {
               try {
                  OptimisticException ole = this.checkUpdate(0, this._stmnt.executeUpdate());
                  if (ole != null) {
                     this._exceptions.add(ole);
                  }
               } catch (SQLException var21) {
                  if (this._failedObjects.isEmpty()) {
                     throw var21;
                  }

                  throw SQLExceptions.getStore(var21, this._failedObjects.get(0), this._dict);
               }
            } else {
               this._stmnt.addBatch();
               this.executeBatch();
            }
         } finally {
            try {
               this._stmnt.close();
            } catch (SQLException var20) {
            }

         }
      } finally {
         if (var6 != null) {
            var6.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._ctx, var5));
         }

      }

   }

   private void executeBatch() throws SQLException {
      try {
         int[] updates = this._stmnt.executeBatch();
         int i;
         if (!this._advanced.getSupportsUpdateCountsForBatch() && this._advanced.getSupportsTotalCountsForBatch()) {
            int expectedCount = this._updateIdx;
            if (this._startIdx != -1) {
               expectedCount -= this._startIdx;
            }

            i = this._stmnt.getUpdateCount();
            boolean success = i == expectedCount;
            updates = new int[expectedCount];
            Arrays.fill(updates, success ? 1 : 0);
         }

         for(i = 0; i < updates.length; ++i) {
            OptimisticException ole = this.checkUpdate(i, updates[i]);
            if (ole != null) {
               this._exceptions.add(ole);
            }
         }
      } catch (SQLException var13) {
         BatchUpdateException bue = null;

         for(SQLException e = var13; bue == null && e != null; e = e.getNextException()) {
            if (e instanceof BatchUpdateException) {
               bue = (BatchUpdateException)e;
            }
         }

         if (bue == null) {
            throw var13;
         }

         Collection failed = null;
         if (this._startIdx != -1) {
            int[] counts = bue.getUpdateCounts();

            for(int i = 0; counts != null && i < counts.length && i - this._startIdx < this._failedObjects.size(); ++i) {
               if (counts[i] < 0) {
                  failed = this.addFailedObject(i - this._startIdx, failed);
                  if (failed != null && this._advanced.getSupportsUpdateCountsForBatch()) {
                     break;
                  }
               }
            }

            if (failed == null && counts != null && counts.length - this._startIdx < this._failedObjects.size()) {
               failed = this.addFailedObject(counts.length - this._startIdx, failed);
            }
         }

         throw SQLExceptions.getStore(var13, failed, this._dict);
      } finally {
         try {
            this._stmnt.clearBatch();
         } catch (Exception var12) {
         }

      }

   }

   private Collection addFailedObject(int idx, Collection fails) {
      Object failed = this._failedObjects.get(idx);
      if (failed != null) {
         if (fails == null) {
            fails = new LinkedList();
         }

         ((Collection)fails).add(failed);
      }

      return (Collection)fails;
   }

   private void clear() {
      this._sql = null;
      this._stmnt = null;
      this._rowAction = -1;
      this._updateIdx = 0;
      this._startIdx = -1;
      this._failedObjects.clear();
   }

   private OptimisticException checkUpdate(int idx, int updateCount) throws SQLException {
      if (updateCount != 1) {
         Object failed = this._startIdx == -1 ? null : this._failedObjects.get(idx - this._startIdx);
         if (updateCount < 0) {
            throw new SQLException(_loc.get("batch-not-supported", String.valueOf(updateCount), this._sql).getMessage());
         }

         if (failed != null) {
            return new OptimisticException(failed);
         }

         if (this._rowAction == 1) {
            throw new SQLException(_loc.get("update-failed-no-failed-obj", String.valueOf(updateCount), this._sql).getMessage());
         }
      }

      return null;
   }
}
