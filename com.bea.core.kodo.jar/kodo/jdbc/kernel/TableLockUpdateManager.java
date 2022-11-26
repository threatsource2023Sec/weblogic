package kodo.jdbc.kernel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.PreparedStatementManager;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.RowManagerImpl;

public class TableLockUpdateManager extends AutoOrderUpdateManager {
   private static int SIZE = 0;
   private static final int INSERTS;
   private static final int UPDATES;
   private static final int DELETES;
   private static final int SECONDARYUPDATES;
   private static final int SECONDARYDELETES;
   private static final int ALLROWUPDATES;
   private static final int ALLROWDELETES;

   public boolean orderDirty() {
      return false;
   }

   protected RowManager newRowManager() {
      return new RowManagerImpl(false);
   }

   protected Collection flush(RowManager rowMgr, PreparedStatementManager psMgr, Collection exceps) {
      RowManagerImpl rmimpl = (RowManagerImpl)rowMgr;
      Map trMap = new HashMap();
      this.organizeRows(trMap, rmimpl.getInserts(), INSERTS);
      this.organizeRows(trMap, rmimpl.getUpdates(), UPDATES);
      this.organizeRows(trMap, rmimpl.getDeletes(), DELETES);
      this.organizeRows(trMap, rmimpl.getSecondaryUpdates(), SECONDARYUPDATES);
      this.organizeRows(trMap, rmimpl.getSecondaryDeletes(), SECONDARYDELETES);
      this.organizeRows(trMap, rmimpl.getAllRowUpdates(), ALLROWUPDATES);
      this.organizeRows(trMap, rmimpl.getAllRowDeletes(), ALLROWDELETES);
      Collection trs = this.sortTables(trMap.values());
      Iterator itr = trs.iterator();

      while(itr.hasNext()) {
         TableRows rows = (TableRows)itr.next();
         this.flush(rows.getRows(ALLROWDELETES), psMgr);
         this.flush(rows.getRows(SECONDARYDELETES), psMgr);
         this.flush(rows.getRows(ALLROWUPDATES), psMgr);
         this.flush(rows.getRows(DELETES), psMgr);
         this.flush(rows.getRows(INSERTS), psMgr);
         this.flush(rows.getRows(UPDATES), psMgr);
         this.flush(rows.getRows(SECONDARYUPDATES), psMgr);
      }

      psMgr.flush();
      return exceps;
   }

   protected Collection sortTables(Collection trs) {
      Collection trs = new ArrayList(trs);
      Collections.sort((List)trs, new Comparator() {
         public int compare(Object o1, Object o2) {
            TableRows tr1 = (TableRows)o1;
            TableRows tr2 = (TableRows)o2;
            return tr1.getTable().getName().compareTo(tr2.getTable().getName());
         }
      });
      return trs;
   }

   private void organizeRows(Map trMap, Collection rows, int type) {
      Row row;
      TableRows tr;
      for(Iterator i = rows.iterator(); i.hasNext(); tr.addRow(type, row)) {
         row = (Row)i.next();
         Table table = row.getTable();
         tr = (TableRows)trMap.get(table);
         if (tr == null) {
            tr = new TableRows(table);
            trMap.put(table, tr);
         }
      }

   }

   static {
      INSERTS = SIZE++;
      UPDATES = SIZE++;
      DELETES = SIZE++;
      SECONDARYUPDATES = SIZE++;
      SECONDARYDELETES = SIZE++;
      ALLROWUPDATES = SIZE++;
      ALLROWDELETES = SIZE++;
   }

   protected static class TableRows {
      private final Table _table;
      private final Collection[] _rows;

      public TableRows(Table table) {
         this._rows = new Collection[TableLockUpdateManager.SIZE];
         this._table = table;
      }

      public Table getTable() {
         return this._table;
      }

      public void addRow(int type, Row row) {
         if (this._rows[type] == null) {
            this._rows[type] = new LinkedList();
         }

         this._rows[type].add(row);
      }

      public Collection getRows(int type) {
         return (Collection)(this._rows[type] == null ? Collections.EMPTY_LIST : this._rows[type]);
      }
   }
}
