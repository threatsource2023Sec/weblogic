package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.Embeddable;
import org.apache.openjpa.jdbc.meta.Joinable;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;

public class HandlerFieldStrategy extends AbstractFieldStrategy implements Joinable, Embeddable {
   private static final Object NULL = new Object();
   private static final Localizer _loc = Localizer.forPackage(HandlerFieldStrategy.class);
   private Column[] _cols = null;
   private ColumnIO _io = null;
   private Object[] _args = null;
   private boolean _load = false;
   private boolean _lob = false;

   public void map(boolean adapt) {
      if (this.field.getHandler() == null) {
         throw new MetaDataException(_loc.get("no-handler", (Object)this.field));
      } else {
         this.assertNotMappedBy();
         this.field.mapJoin(adapt, false);
         this.field.getKeyMapping().getValueInfo().assertNoSchemaComponents(this.field.getKey(), !adapt);
         this.field.getElementMapping().getValueInfo().assertNoSchemaComponents(this.field.getElement(), !adapt);
         this._io = new ColumnIO();
         this._cols = HandlerStrategies.map(this.field, this.field.getName(), this._io, adapt);
         int i;
         if (this.field.getValueStrategy() == 3) {
            boolean marked = false;

            for(i = 0; !marked && i < this._cols.length; ++i) {
               if (this._cols[i].isAutoAssigned()) {
                  marked = true;
               }
            }

            if (!marked) {
               for(i = 0; i < this._cols.length; ++i) {
                  this._cols[i].setAutoAssigned(true);
               }
            }
         }

         this.field.mapPrimaryKey(adapt);
         PrimaryKey pk = this.field.getTable().getPrimaryKey();
         if (this.field.isPrimaryKey() && pk != null && (adapt || pk.isLogical())) {
            for(i = 0; i < this._cols.length; ++i) {
               pk.addColumn(this._cols[i]);
            }
         }

         if (!this.field.getHandler().objectValueRequiresLoad(this.field)) {
            for(i = 0; i < this._cols.length; ++i) {
               this.field.getDefiningMapping().setJoinable(this._cols[i], this);
            }
         }

      }
   }

   public void initialize() {
      this._load = this.field.getHandler().objectValueRequiresLoad(this.field);
      if (this._load) {
         this.field.setUsesIntermediate(true);
      }

      for(int i = 0; !this._lob && i < this._cols.length; ++i) {
         this._lob = this._cols[i].isLob();
      }

      Object args = this.field.getHandler().getResultArgument(this.field);
      if (args == null) {
         this._args = null;
      } else if (this._cols.length == 1) {
         this._args = new Object[]{args};
      } else {
         this._args = (Object[])((Object[])args);
      }

   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = this.field.getRow(sm, store, rm, 1);
      if (row != null) {
         HandlerStrategies.set(this.field, sm.fetch(this.field.getIndex()), store, row, this._cols, this._io, this.field.getNullValue() == 0);
      }

   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = this.field.getRow(sm, store, rm, 0);
      if (row != null) {
         HandlerStrategies.set(this.field, sm.fetch(this.field.getIndex()), store, row, this._cols, this._io, this.field.getNullValue() == 0);
      }

   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.field.deleteRow(sm, store, rm);
   }

   public int supportsSelect(Select sel, int type, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) {
      return (type != 3 || !sel.isSelected(this.field.getTable())) && (!this._load || type != 4) ? 0 : 1;
   }

   public int select(Select sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      if (this._cols.length == 0) {
         return -1;
      } else if (sm != null && sm.getIntermediate(this.field.getIndex()) != null) {
         return -1;
      } else if (!this._lob || this.field.isPrimaryKey() || !sel.isDistinct() && eagerMode != 0) {
         sel.select(this._cols, this.field.join(sel));
         return 1;
      } else {
         return -1;
      }
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
      if (this._cols.length != 0 && res.containsAll(this._cols)) {
         Object val = HandlerStrategies.loadDataStore(this.field, res, (Joins)null, this._cols);
         if (!this._load) {
            sm.store(this.field.getIndex(), this.field.getHandler().toObjectValue(this.field, val));
         } else {
            if (val == null) {
               val = NULL;
            }

            sm.setIntermediate(this.field.getIndex(), val);
         }

      }
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
      if (this._cols.length == 0) {
         if (this._load) {
            sm.store(this.field.getIndex(), this.field.getHandler().toObjectValue(this.field, (Object)null, sm, store, fetch));
         } else {
            sm.store(this.field.getIndex(), this.field.getHandler().toObjectValue(this.field, (Object)null));
         }

      } else {
         if (this._load) {
            Object ds = sm.getIntermediate(this.field.getIndex());
            if (ds != null) {
               if (ds == NULL) {
                  ds = null;
               }

               sm.store(this.field.getIndex(), this.field.getHandler().toObjectValue(this.field, ds, sm, store, fetch));
               return;
            }
         }

         Select sel = store.getSQLFactory().newSelect();
         sel.select(this._cols);
         this.field.wherePrimaryKey(sel, sm, store);
         Result res = sel.execute(store, fetch);
         Object val = null;

         try {
            if (res.next()) {
               val = HandlerStrategies.loadDataStore(this.field, res, (Joins)null, this._cols);
            }
         } finally {
            res.close();
         }

         this.loadEmbedded(sm, store, fetch, val);
      }
   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      return HandlerStrategies.toDataStoreValue(this.field, val, this._cols, store);
   }

   public void appendIsNull(SQLBuffer sql, Select sel, Joins joins) {
      joins = this.join(joins, false);

      for(int i = 0; i < this._cols.length; ++i) {
         if (i > 0) {
            sql.append(" AND ");
         }

         sql.append(sel.getColumnAlias(this._cols[i], joins)).append(" IS ").appendValue((Object)null, this._cols[i]);
      }

   }

   public void appendIsNotNull(SQLBuffer sql, Select sel, Joins joins) {
      joins = this.join(joins, false);
      if (this._cols.length > 1) {
         sql.append("(");
      }

      for(int i = 0; i < this._cols.length; ++i) {
         if (i > 0) {
            sql.append(" OR ");
         }

         sql.append(sel.getColumnAlias(this._cols[i], joins)).append(" IS NOT ").appendValue((Object)null, this._cols[i]);
      }

      if (this._cols.length > 1) {
         sql.append(")");
      }

   }

   public Joins join(Joins joins, boolean forceOuter) {
      return this.field.join(joins, forceOuter, false);
   }

   public Joins joinRelation(Joins joins, boolean forceOuter, boolean traverse) {
      if (traverse) {
         HandlerStrategies.assertJoinable(this.field);
      }

      return joins;
   }

   public Object loadProjection(JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return HandlerStrategies.loadObject(this.field, (OpenJPAStateManager)null, store, fetch, res, joins, this._cols, this._load);
   }

   public boolean isVersionable() {
      return !this._lob && !this.field.isJoinOuter() && this.field.getHandler().isVersionable(this.field);
   }

   public void where(OpenJPAStateManager sm, JDBCStore store, RowManager rm, Object prevValue) throws SQLException {
      Row row = this.field.getRow(sm, store, rm, 0);
      if (row != null) {
         HandlerStrategies.where(this.field, prevValue, store, row, this._cols);
      }

   }

   public int getFieldIndex() {
      return this.field.getIndex();
   }

   public Object getPrimaryKeyValue(Result res, Column[] cols, ForeignKey fk, JDBCStore store, Joins joins) throws SQLException {
      Object val = null;
      Column col;
      if (cols.length == 1) {
         col = cols[0];
         if (fk != null) {
            col = fk.getColumn(col);
         }

         val = res.getObject(col, this.field.getHandler().getResultArgument(this.field), joins);
      } else if (cols.length > 1) {
         Object[] vals = new Object[cols.length];
         Object[] args = (Object[])((Object[])this.field.getHandler().getResultArgument(this.field));

         for(int i = 0; i < vals.length; ++i) {
            col = cols[i];
            if (fk != null) {
               col = fk.getColumn(col);
            }

            vals[i] = res.getObject(col, args == null ? null : args[i], joins);
         }

         val = vals;
      }

      return this.field.getHandler().toObjectValue(this.field, val);
   }

   public Column[] getColumns() {
      return this._cols;
   }

   public Object[] getResultArguments() {
      return this._args;
   }

   public Object getJoinValue(Object fieldVal, Column col, JDBCStore store) {
      Object val = HandlerStrategies.toDataStoreValue(this.field, fieldVal, this._cols, store);
      if (val != null && this._cols.length >= 2) {
         for(int i = 0; i < this._cols.length; ++i) {
            if (this._cols[i] == col) {
               return ((Object[])((Object[])val))[i];
            }
         }

         throw new InternalException();
      } else {
         return val;
      }
   }

   public Object getJoinValue(OpenJPAStateManager sm, Column col, JDBCStore store) {
      return this.getJoinValue(sm.fetch(this.field.getIndex()), col, store);
   }

   public void setAutoAssignedValue(OpenJPAStateManager sm, JDBCStore store, Column col, Object autoInc) {
      Object data;
      if (this._cols.length == 1) {
         data = JavaTypes.convert(autoInc, col.getJavaType());
      } else {
         data = this.field.getHandler().toDataStoreValue(this.field, sm.fetch(this.field.getIndex()), store);
         if (data == null) {
            data = new Object[this._cols.length];
         }

         for(int i = 0; i < this._cols.length; ++i) {
            if (this._cols[i] == col) {
               ((Object[])((Object[])data))[i] = JavaTypes.convert(autoInc, col.getJavaType());
               break;
            }
         }
      }

      Object val = this.field.getHandler().toObjectValue(this.field, data);
      sm.store(this.field.getIndex(), val);
   }

   public ColumnIO getColumnIO() {
      return this._io;
   }

   public Object toEmbeddedDataStoreValue(Object val, JDBCStore store) {
      val = this.field.getHandler().toDataStoreValue(this.field, val, store);
      return val == null && this._cols.length > 1 ? new Object[this._cols.length] : val;
   }

   public Object toEmbeddedObjectValue(Object val) {
      return !this._load ? this.field.getHandler().toObjectValue(this.field, val) : UNSUPPORTED;
   }

   public void loadEmbedded(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Object val) throws SQLException {
      if (val == null && this._cols.length > 1) {
         val = new Object[this._cols.length];
      }

      if (this._load) {
         sm.store(this.field.getIndex(), this.field.getHandler().toObjectValue(this.field, val, sm, store, fetch));
      } else {
         sm.store(this.field.getIndex(), this.field.getHandler().toObjectValue(this.field, val));
      }

   }
}
