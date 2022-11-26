package org.apache.openjpa.jdbc.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.util.InternalException;

public class RowManagerImpl implements RowManager {
   private Map _inserts = null;
   private Map _updates = null;
   private Map _deletes = null;
   private Collection _secondaryUpdates = null;
   private Collection _secondaryDeletes = null;
   private Collection _allRowUpdates = null;
   private Collection _allRowDeletes = null;
   private final List _primaryOrder;
   private boolean _auto = false;
   private Key _key = null;
   private PrimaryRow _row = null;

   public RowManagerImpl(boolean order) {
      this._primaryOrder = order ? new ArrayList() : null;
   }

   public boolean hasAutoAssignConstraints() {
      return this._auto;
   }

   public List getOrdered() {
      return this._primaryOrder == null ? Collections.emptyList() : this._primaryOrder;
   }

   public Collection getInserts() {
      return (Collection)(this._inserts == null ? Collections.emptyList() : this._inserts.values());
   }

   public Collection getUpdates() {
      return (Collection)(this._updates == null ? Collections.emptyList() : this._updates.values());
   }

   public Collection getDeletes() {
      return (Collection)(this._deletes == null ? Collections.emptyList() : this._deletes.values());
   }

   public Collection getSecondaryUpdates() {
      return (Collection)(this._secondaryUpdates == null ? Collections.emptyList() : this._secondaryUpdates);
   }

   public Collection getSecondaryDeletes() {
      return (Collection)(this._secondaryDeletes == null ? Collections.emptyList() : this._secondaryDeletes);
   }

   public Collection getAllRowUpdates() {
      return (Collection)(this._allRowUpdates == null ? Collections.emptyList() : this._allRowUpdates);
   }

   public Collection getAllRowDeletes() {
      return (Collection)(this._allRowDeletes == null ? Collections.emptyList() : this._allRowDeletes);
   }

   public Row getSecondaryRow(Table table, int action) {
      return new SecondaryRow(table, action);
   }

   public void flushSecondaryRow(Row row) throws SQLException {
      if (row.isValid()) {
         SecondaryRow srow = (SecondaryRow)row;
         if (srow.getAction() == 2) {
            if (this._secondaryDeletes == null) {
               this._secondaryDeletes = new ArrayList();
            }

            this._secondaryDeletes.add((SecondaryRow)srow.clone());
         } else {
            if (this._secondaryUpdates == null) {
               this._secondaryUpdates = new ArrayList();
            }

            this._secondaryUpdates.add((SecondaryRow)srow.clone());
         }

      }
   }

   public Row getAllRows(Table table, int action) {
      return new RowImpl(table, action);
   }

   public void flushAllRows(Row row) {
      if (row.isValid()) {
         switch (row.getAction()) {
            case 0:
               if (this._allRowUpdates == null) {
                  this._allRowUpdates = new ArrayList();
               }

               this._allRowUpdates.add(row);
               break;
            case 2:
               if (this._allRowDeletes == null) {
                  this._allRowDeletes = new ArrayList();
               }

               this._allRowDeletes.add(row);
               break;
            default:
               throw new InternalException("action = " + row.getAction());
         }

      }
   }

   public Row getRow(Table table, int action, OpenJPAStateManager sm, boolean create) {
      if (sm == null) {
         return null;
      } else if (this._key != null && this._key.table == table && this._key.sm == sm && this._row != null && this._row.getAction() == action) {
         return this._row;
      } else {
         Map map;
         if (action == 2) {
            if (this._deletes == null && create) {
               this._deletes = new LinkedHashMap();
            }

            map = this._deletes;
         } else if (action == 1) {
            if (this._inserts == null && create) {
               this._inserts = new LinkedHashMap();
            }

            map = this._inserts;
         } else {
            if (this._updates == null && create) {
               this._updates = new LinkedHashMap();
            }

            map = this._updates;
         }

         if (map == null) {
            return null;
         } else {
            this._key = new Key(table, sm);
            this._row = (PrimaryRow)map.get(this._key);
            if (this._row == null && create) {
               this._row = new PrimaryRow(table, action, sm);
               map.put(this._key, this._row);
               if (this._primaryOrder != null) {
                  this._row.setIndex(this._primaryOrder.size());
                  this._primaryOrder.add(this._row);
               }

               if (!this._auto && action == 1) {
                  this._auto = table.getAutoAssignedColumns().length > 0;
               }
            }

            if (this._row != null) {
               this._row.setFailedObject(sm.getManagedInstance());
            }

            return this._row;
         }
      }
   }

   private static class Key {
      public final Table table;
      public final OpenJPAStateManager sm;

      public Key(Table table, OpenJPAStateManager sm) {
         this.table = table;
         this.sm = sm;
      }

      public int hashCode() {
         return (this.table.hashCode() + this.sm.hashCode()) % Integer.MAX_VALUE;
      }

      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else {
            Key key = (Key)other;
            return this.table == key.table && this.sm == key.sm;
         }
      }
   }
}
