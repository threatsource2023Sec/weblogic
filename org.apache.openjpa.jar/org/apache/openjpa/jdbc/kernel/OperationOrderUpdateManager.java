package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.PrimaryRow;
import org.apache.openjpa.jdbc.sql.RowImpl;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.RowManagerImpl;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class OperationOrderUpdateManager extends AbstractUpdateManager {
   public boolean orderDirty() {
      return true;
   }

   protected RowManager newRowManager() {
      return new RowManagerImpl(true);
   }

   protected PreparedStatementManager newPreparedStatementManager(JDBCStore store, Connection conn) {
      return new PreparedStatementManagerImpl(store, conn);
   }

   protected Collection flush(RowManager rowMgr, PreparedStatementManager psMgr, Collection exceps) {
      RowManagerImpl rmimpl = (RowManagerImpl)rowMgr;
      this.flush(rmimpl.getAllRowDeletes(), psMgr);
      this.flush(rmimpl.getSecondaryDeletes(), psMgr);
      this.flush(rmimpl.getAllRowUpdates(), psMgr);
      Collection constraintUpdates = null;
      Iterator itr = rmimpl.getDeletes().iterator();

      while(itr.hasNext()) {
         try {
            constraintUpdates = this.analyzeDeleteConstraints(rmimpl, (PrimaryRow)itr.next(), constraintUpdates);
         } catch (SQLException var9) {
            exceps = this.addException(exceps, SQLExceptions.getStore(var9, this.dict));
         }
      }

      if (constraintUpdates != null) {
         this.flush(constraintUpdates, psMgr);
         constraintUpdates.clear();
      }

      itr = rmimpl.getOrdered().iterator();

      while(itr.hasNext()) {
         try {
            constraintUpdates = this.flushPrimaryRow(rmimpl, (PrimaryRow)itr.next(), psMgr, constraintUpdates);
         } catch (SQLException var8) {
            exceps = this.addException(exceps, SQLExceptions.getStore(var8, this.dict));
         }
      }

      if (constraintUpdates != null) {
         this.flush(constraintUpdates, psMgr);
      }

      this.flush(rmimpl.getSecondaryUpdates(), psMgr);
      psMgr.flush();
      return exceps;
   }

   private Collection analyzeDeleteConstraints(RowManagerImpl rowMgr, PrimaryRow row, Collection updates) throws SQLException {
      if (!row.isValid()) {
         return (Collection)updates;
      } else {
         ForeignKey[] fks = row.getTable().getForeignKeys();

         for(int i = 0; i < fks.length; ++i) {
            OpenJPAStateManager sm = row.getForeignKeySet(fks[i]);
            if (sm == null) {
               sm = row.getForeignKeyWhere(fks[i]);
            }

            if (sm != null) {
               PrimaryRow rel = (PrimaryRow)rowMgr.getRow(fks[i].getPrimaryKeyTable(), 2, sm, false);
               if (rel != null && rel.isValid() && rel.getIndex() < row.getIndex()) {
                  RowImpl update = new PrimaryRow(row.getTable(), 0, (OpenJPAStateManager)null);
                  row.copyInto(update, true);
                  update.setForeignKey(fks[i], row.getForeignKeyIO(fks[i]), (OpenJPAStateManager)null);
                  if (updates == null) {
                     updates = new ArrayList();
                  }

                  ((Collection)updates).add(update);
               }
            }
         }

         return (Collection)updates;
      }
   }

   private Collection flushPrimaryRow(RowManagerImpl rowMgr, PrimaryRow row, PreparedStatementManager psMgr, Collection updates) throws SQLException {
      if (!row.isValid()) {
         return (Collection)updates;
      } else if (row.getAction() == 2) {
         psMgr.flush(row);
         return (Collection)updates;
      } else {
         ForeignKey[] fks = row.getTable().getForeignKeys();

         for(int i = 0; i < fks.length; ++i) {
            OpenJPAStateManager sm = row.getForeignKeySet(fks[i]);
            if (sm != null) {
               PrimaryRow rel = (PrimaryRow)rowMgr.getRow(fks[i].getPrimaryKeyTable(), 1, sm, false);
               if (rel != null && rel.isValid() && rel.getIndex() >= row.getIndex() && (rel != row || fks[i].isDeferred() || fks[i].isLogical())) {
                  PrimaryRow update = new PrimaryRow(row.getTable(), 0, (OpenJPAStateManager)null);
                  if (row.getAction() == 1) {
                     update.wherePrimaryKey(row.getPrimaryKey());
                  } else {
                     row.copyInto(update, true);
                  }

                  update.setForeignKey(fks[i], row.getForeignKeyIO(fks[i]), sm);
                  row.clearForeignKey(fks[i]);
                  if (updates == null) {
                     updates = new ArrayList();
                  }

                  ((Collection)updates).add(update);
               }
            }
         }

         if (row.isValid()) {
            psMgr.flush(row);
         }

         return (Collection)updates;
      }
   }

   protected void flush(Collection rows, PreparedStatementManager psMgr) {
      if (!rows.isEmpty()) {
         Iterator itr = rows.iterator();

         while(itr.hasNext()) {
            RowImpl row = (RowImpl)itr.next();
            if (row.isValid()) {
               psMgr.flush(row);
            }
         }

      }
   }
}
