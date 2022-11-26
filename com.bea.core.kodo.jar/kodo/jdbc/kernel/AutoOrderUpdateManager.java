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
import org.apache.openjpa.jdbc.kernel.AbstractUpdateManager;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.kernel.PreparedStatementManager;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.RowImpl;
import org.apache.openjpa.lib.conf.Configuration;

public abstract class AutoOrderUpdateManager extends AbstractUpdateManager {
   protected boolean batch = false;
   protected boolean maxBatch = true;
   protected SQLComparator sqlComparator = null;

   public boolean getMaximizeBatchSize() {
      return this.maxBatch;
   }

   public void setMaximizeBatchSize(boolean max) {
      this.maxBatch = max;
   }

   public void setConfiguration(Configuration c) {
      super.setConfiguration(c);
      AdvancedSQL advanced = ((KodoSQLFactory)this.conf.getSQLFactoryInstance()).getAdvancedSQL();
      this.batch = advanced.getBatchLimit() != 0 && advanced.getBatchParameterLimit() != 0;
   }

   protected PreparedStatementManager newPreparedStatementManager(JDBCStore store, Connection conn) {
      return new BatchingPreparedStatementManager(store, conn, this.batch);
   }

   protected void flush(Collection rows, PreparedStatementManager psMgr) {
      if (((Collection)rows).size() != 0) {
         if (this.batch && this.maxBatch && ((Collection)rows).size() > 2) {
            if (this.sqlComparator == null) {
               this.sqlComparator = new SQLComparator(this.dict);
            }

            rows = new ArrayList((Collection)rows);
            Collections.sort((List)rows, this.sqlComparator);
         }

         Iterator itr = ((Collection)rows).iterator();

         while(itr.hasNext()) {
            RowImpl row = (RowImpl)itr.next();
            if (row.isValid() && !row.isDependent()) {
               psMgr.flush(row);
            }
         }

      }
   }

   protected static class SQLComparator implements Comparator {
      private final DBDictionary _dict;

      public SQLComparator(DBDictionary dict) {
         this._dict = dict;
      }

      public int compare(Object o1, Object o2) {
         return ((RowImpl)o1).getSQL(this._dict).compareTo(((RowImpl)o2).getSQL(this._dict));
      }
   }
}
