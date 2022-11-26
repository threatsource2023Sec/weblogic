package kodo.jdbc.kernel;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kodo.jdbc.sql.AdvancedSQL;
import kodo.jdbc.sql.KodoSQLFactory;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.kernel.OperationOrderUpdateManager;
import org.apache.openjpa.jdbc.kernel.PreparedStatementManager;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.RowImpl;
import org.apache.openjpa.lib.conf.Configuration;

public class BatchingOperationOrderUpdateManager extends OperationOrderUpdateManager {
   private boolean _batch = false;
   private boolean _maxBatch = true;
   private SQLComparator _sqlComparator = null;

   public boolean getMaximizeBatchSize() {
      return this._maxBatch;
   }

   public void setMaximizeBatchSize(boolean max) {
      this._maxBatch = max;
   }

   public void setConfiguration(Configuration c) {
      super.setConfiguration(c);
      AdvancedSQL advanced = ((KodoSQLFactory)this.conf.getSQLFactoryInstance()).getAdvancedSQL();
      this._batch = advanced.getBatchLimit() != 0 && advanced.getBatchParameterLimit() != 0;
   }

   protected PreparedStatementManager newPreparedStatementManager(JDBCStore store, Connection conn) {
      return new BatchingPreparedStatementManager(store, conn, this._batch);
   }

   protected void flush(Collection rows, PreparedStatementManager psMgr) {
      if (((Collection)rows).size() != 0) {
         if (this._batch && this._maxBatch && ((Collection)rows).size() > 2) {
            if (this._sqlComparator == null) {
               this._sqlComparator = new SQLComparator(this.dict);
            }

            rows = new ArrayList((Collection)rows);
            Collections.sort((List)rows, this._sqlComparator);
         }

         Iterator itr = ((Collection)rows).iterator();

         while(itr.hasNext()) {
            RowImpl row = (RowImpl)itr.next();
            if (row.isValid()) {
               psMgr.flush(row);
            }
         }

      }
   }

   private static class SQLComparator implements Comparator {
      private final DBDictionary _dict;

      public SQLComparator(DBDictionary dict) {
         this._dict = dict;
      }

      public int compare(Object o1, Object o2) {
         return ((RowImpl)o1).getSQL(this._dict).compareTo(((RowImpl)o2).getSQL(this._dict));
      }
   }
}
