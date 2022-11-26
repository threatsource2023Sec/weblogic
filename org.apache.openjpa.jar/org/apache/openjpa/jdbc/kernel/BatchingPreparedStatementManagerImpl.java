package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.RowImpl;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.OptimisticException;

public class BatchingPreparedStatementManagerImpl extends PreparedStatementManagerImpl {
   private static final Localizer _loc = Localizer.forPackage(BatchingPreparedStatementManagerImpl.class);
   private String _batchedSql = null;
   private List _batchedRows = new ArrayList();
   private int _batchLimit;
   private boolean _disableBatch = false;
   private transient Log _log = null;

   public BatchingPreparedStatementManagerImpl(JDBCStore store, Connection conn, int batchLimit) {
      super(store, conn);
      this._batchLimit = batchLimit;
      this._log = store.getConfiguration().getLog("openjpa.jdbc.JDBC");
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("batch_limit", (Object)String.valueOf(this._batchLimit)));
      }

   }

   protected void flushAndUpdate(RowImpl row) throws SQLException {
      if (this.isBatchDisabled(row)) {
         this.flushBatch();
         super.flushAndUpdate(row);
      } else {
         String sql = row.getSQL(this._dict);
         if (this._batchedSql == null) {
            this._batchedSql = sql;
         } else if (!sql.equals(this._batchedSql)) {
            switch (this._batchedRows.size()) {
               case 0:
                  break;
               case 1:
                  super.flushAndUpdate((RowImpl)this._batchedRows.get(0));
                  this._batchedRows.clear();
                  break;
               default:
                  this.flushBatch();
            }

            this._batchedSql = sql;
         }

         this._batchedRows.add(row);
      }

   }

   private boolean isBatchDisabled(RowImpl row) {
      boolean rtnVal = true;
      if (this._batchLimit != 0 && !this._disableBatch) {
         row.getSQL(this._dict);
         OpenJPAStateManager sm = row.getPrimaryKey();
         ClassMapping cmd = null;
         if (sm != null) {
            cmd = (ClassMapping)sm.getMetaData();
         }

         Column[] autoAssign = null;
         if (row.getAction() == 1) {
            autoAssign = row.getTable().getAutoAssignedColumns();
         }

         this._disableBatch = this._dict.validateBatchProcess(row, autoAssign, sm, cmd);
         rtnVal = this._disableBatch;
      }

      return rtnVal;
   }

   protected void flushBatch() {
      if (this._batchedSql != null && this._batchedRows.size() > 0) {
         PreparedStatement ps = null;

         try {
            RowImpl onerow = null;
            ps = this._conn.prepareStatement(this._batchedSql);
            if (this._batchedRows.size() == 1) {
               onerow = (RowImpl)this._batchedRows.get(0);
               this.flushSingleRow(onerow, ps);
            } else {
               int count = 0;
               int batchedRowsBaseIndex = 0;
               Iterator itr = this._batchedRows.iterator();

               while(true) {
                  int[] rtn;
                  while(itr.hasNext()) {
                     onerow = (RowImpl)itr.next();
                     if (this._batchLimit == 1) {
                        this.flushSingleRow(onerow, ps);
                     } else if (count >= this._batchLimit && this._batchLimit != -1) {
                        rtn = ps.executeBatch();
                        this.checkUpdateCount(rtn, batchedRowsBaseIndex);
                        batchedRowsBaseIndex += this._batchLimit;
                        onerow.flush(ps, this._dict, this._store);
                        ps.addBatch();
                        count = 1;
                     } else {
                        onerow.flush(ps, this._dict, this._store);
                        ps.addBatch();
                        ++count;
                     }
                  }

                  rtn = ps.executeBatch();
                  this.checkUpdateCount(rtn, batchedRowsBaseIndex);
                  break;
               }
            }
         } catch (SQLException var14) {
            SQLException sqex = var14.getNextException();
            if (sqex == null) {
               sqex = var14;
            }

            throw SQLExceptions.getStore((SQLException)sqex, (Object)ps, this._dict);
         } finally {
            this._batchedSql = null;
            this._batchedRows.clear();
            if (ps != null) {
               try {
                  ps.close();
               } catch (SQLException var13) {
                  throw SQLExceptions.getStore((SQLException)var13, (Object)ps, this._dict);
               }
            }

         }
      }

   }

   private void flushSingleRow(RowImpl row, PreparedStatement ps) throws SQLException {
      row.flush(ps, this._dict, this._store);
      int count = ps.executeUpdate();
      if (count != 1) {
         Object failed = row.getFailedObject();
         if (failed != null) {
            this._exceptions.add(new OptimisticException(failed));
         } else if (row.getAction() == 1) {
            throw new SQLException(_loc.get("update-failed-no-failed-obj", String.valueOf(count), row.getSQL(this._dict)).getMessage());
         }
      }

   }

   private void checkUpdateCount(int[] count, int batchedRowsBaseIndex) throws SQLException {
      int cnt = false;
      Object failed = null;

      for(int i = 0; i < count.length; ++i) {
         int cnt = count[i];
         RowImpl row = (RowImpl)this._batchedRows.get(batchedRowsBaseIndex + i);
         switch (cnt) {
            case -3:
               failed = row.getFailedObject();
               if (failed == null && row.getAction() != 0) {
                  if (row.getAction() == 1) {
                     throw new SQLException(_loc.get("update-failed-no-failed-obj", String.valueOf(count[i]), this._batchedSql).getMessage());
                  }
               } else {
                  this._exceptions.add(new OptimisticException(failed));
               }
               break;
            case -2:
               if (this._log.isTraceEnabled()) {
                  this._log.trace(_loc.get("batch_update_info", String.valueOf(cnt), this._batchedSql).getMessage());
               }
            case -1:
            default:
               break;
            case 0:
               failed = row.getFailedObject();
               if (failed != null || row.getAction() == 1) {
                  throw new SQLException(_loc.get("update-failed-no-failed-obj", String.valueOf(count[i]), this._batchedSql).getMessage());
               }
         }
      }

   }
}
