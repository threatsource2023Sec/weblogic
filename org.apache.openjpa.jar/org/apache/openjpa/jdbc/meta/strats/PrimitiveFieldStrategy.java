package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.Embeddable;
import org.apache.openjpa.jdbc.meta.Joinable;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
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

public class PrimitiveFieldStrategy extends AbstractFieldStrategy implements Joinable, Embeddable {
   private static final Object NULL = new Object();
   private static final Localizer _loc = Localizer.forPackage(PrimitiveFieldStrategy.class);
   private boolean _stateImage = false;

   public void map(boolean adapt) {
      if (!this.field.isSerialized() && this.field.getType().isPrimitive()) {
         this.assertNotMappedBy();
         this.field.mapJoin(adapt, false);
         this.field.getKeyMapping().getValueInfo().assertNoSchemaComponents(this.field.getKey(), !adapt);
         this.field.getElementMapping().getValueInfo().assertNoSchemaComponents(this.field.getElement(), !adapt);
         ValueMappingInfo vinfo = this.field.getValueInfo();
         vinfo.assertNoJoin(this.field, true);
         vinfo.assertNoForeignKey(this.field, !adapt);
         Column tmpCol = new Column();
         tmpCol.setName(this.field.getName());
         tmpCol.setJavaType(this.field.getTypeCode());
         Column[] cols = vinfo.getColumns(this.field, this.field.getName(), new Column[]{tmpCol}, this.field.getTable(), adapt);
         if (this.field.getValueStrategy() == 3) {
            cols[0].setAutoAssigned(true);
         }

         this.field.setColumns(cols);
         this.field.setColumnIO(vinfo.getColumnIO());
         this.field.mapConstraints(this.field.getName(), adapt);
         this.field.mapPrimaryKey(adapt);
         PrimaryKey pk = this.field.getTable().getPrimaryKey();
         if (this.field.isPrimaryKey() && pk != null && (adapt || pk.isLogical())) {
            pk.addColumn(cols[0]);
         }

         this.field.getDefiningMapping().setJoinable(this.field.getColumns()[0], this);
      } else {
         throw new MetaDataException(_loc.get("not-primitive", (Object)this.field));
      }
   }

   public void initialize() {
      this._stateImage = this.field.getDefiningMapping().getVersion().getStrategy().getAlias().equals("state-comparison");
      if (this._stateImage) {
         this.field.setUsesImplData((Boolean)null);
      }

   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      if (this.field.getColumnIO().isInsertable(0, false)) {
         Row row = this.field.getRow(sm, store, rm, 1);
         if (row != null) {
            this.update(sm, row);
         }

      }
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      if (this.field.getColumnIO().isUpdatable(0, false)) {
         Row row = this.field.getRow(sm, store, rm, 0);
         if (row != null) {
            this.update(sm, row);
         }

      }
   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.field.deleteRow(sm, store, rm);
   }

   private void update(OpenJPAStateManager sm, Row row) throws SQLException {
      Column col = this.field.getColumns()[0];
      switch (this.field.getTypeCode()) {
         case 0:
            row.setBoolean(col, sm.fetchBoolean(this.field.getIndex()));
            break;
         case 1:
            row.setByte(col, sm.fetchByte(this.field.getIndex()));
            break;
         case 2:
            row.setChar(col, sm.fetchChar(this.field.getIndex()));
            break;
         case 3:
            row.setDouble(col, sm.fetchDouble(this.field.getIndex()));
            break;
         case 4:
            row.setFloat(col, sm.fetchFloat(this.field.getIndex()));
            break;
         case 5:
            row.setInt(col, sm.fetchInt(this.field.getIndex()));
            break;
         case 6:
            row.setLong(col, sm.fetchLong(this.field.getIndex()));
            break;
         case 7:
            row.setShort(col, sm.fetchShort(this.field.getIndex()));
            break;
         default:
            throw new InternalException();
      }

   }

   public int supportsSelect(Select sel, int type, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) {
      return type == 3 && sel.isSelected(this.field.getTable()) ? 1 : 0;
   }

   public int select(Select sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      sel.select(this.field.getColumns()[0], this.field.join(sel));
      return 1;
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
      Column col = this.field.getColumns()[0];
      if (res.contains(col)) {
         int idx = this.field.getIndex();
         boolean checkNull = this._stateImage && !this.field.isJoinOuter();
         switch (this.field.getTypeCode()) {
            case 0:
               sm.storeBoolean(idx, res.getBoolean(col));
               break;
            case 1:
               sm.storeByte(idx, res.getByte(col));
               break;
            case 2:
               sm.storeChar(idx, res.getChar(col));
               break;
            case 3:
               sm.storeDouble(idx, res.getDouble(col));
               checkNull = false;
               break;
            case 4:
               sm.storeFloat(idx, res.getFloat(col));
               checkNull = false;
               break;
            case 5:
               sm.storeInt(idx, res.getInt(col));
               break;
            case 6:
               sm.storeLong(idx, res.getLong(col));
               break;
            case 7:
               sm.storeShort(idx, res.getShort(col));
               break;
            default:
               throw new InternalException();
         }

         if (checkNull && res.wasNull()) {
            sm.setImplData(this.field.getIndex(), NULL);
         }

      }
   }

   public void appendIsNull(SQLBuffer sql, Select sel, Joins joins) {
      joins = this.join(joins, false);
      sql.append(sel.getColumnAlias(this.field.getColumns()[0], joins)).append(" IS ").appendValue((Object)null, this.field.getColumns()[0]);
   }

   public void appendIsNotNull(SQLBuffer sql, Select sel, Joins joins) {
      joins = this.join(joins, false);
      sql.append(sel.getColumnAlias(this.field.getColumns()[0], joins)).append(" IS NOT ").appendValue((Object)null, this.field.getColumns()[0]);
   }

   public Joins join(Joins joins, boolean forceOuter) {
      return this.field.join(joins, forceOuter, false);
   }

   public Object loadProjection(JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return res.getObject(this.field.getColumns()[0], (Object)null, joins);
   }

   public boolean isVersionable() {
      if (this.field.isJoinOuter()) {
         return false;
      } else {
         switch (this.field.getTypeCode()) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 6:
            case 7:
               return true;
            case 3:
            case 4:
            default:
               return false;
         }
      }
   }

   public void where(OpenJPAStateManager sm, JDBCStore store, RowManager rm, Object prevValue) throws SQLException {
      Row row = this.field.getRow(sm, store, rm, 0);
      if (row != null) {
         Column col = this.field.getColumns()[0];
         if (sm.setImplData(this.field.getIndex(), (Object)null) == NULL) {
            row.whereNull(col);
         } else {
            row.whereObject(col, prevValue);
         }

      }
   }

   public int getFieldIndex() {
      return this.field.getIndex();
   }

   public Object getPrimaryKeyValue(Result res, Column[] cols, ForeignKey fk, JDBCStore store, Joins joins) throws SQLException {
      Column col = cols[0];
      if (fk != null) {
         col = fk.getColumn(col);
      }

      return JavaTypes.convert(res.getObject(col, (Object)null, joins), this.field.getTypeCode());
   }

   public Column[] getColumns() {
      return this.field.getColumns();
   }

   public Object getJoinValue(Object fieldVal, Column col, JDBCStore store) {
      return fieldVal;
   }

   public Object getJoinValue(OpenJPAStateManager sm, Column col, JDBCStore store) {
      return sm.fetch(this.field.getIndex());
   }

   public void setAutoAssignedValue(OpenJPAStateManager sm, JDBCStore store, Column col, Object autoInc) {
      int idx = this.field.getIndex();
      switch (this.field.getTypeCode()) {
         case 0:
            if (autoInc == null) {
               sm.storeBoolean(idx, false);
            } else if (autoInc instanceof Boolean) {
               sm.storeBoolean(idx, (Boolean)autoInc);
            } else {
               sm.storeBoolean(idx, ((Number)autoInc).intValue() != 0);
            }
            break;
         case 1:
            if (autoInc == null) {
               sm.storeByte(idx, (byte)0);
            } else {
               sm.storeByte(idx, ((Number)autoInc).byteValue());
            }
            break;
         case 2:
            if (autoInc == null) {
               sm.storeChar(idx, '\u0000');
            } else if (autoInc instanceof Character) {
               sm.storeChar(idx, (Character)autoInc);
            } else if (autoInc instanceof String) {
               sm.storeChar(idx, ((String)autoInc).charAt(0));
            } else {
               sm.storeChar(idx, (char)((Number)autoInc).intValue());
            }
            break;
         case 3:
            if (autoInc == null) {
               sm.storeDouble(idx, 0.0);
            } else {
               sm.storeDouble(idx, ((Number)autoInc).doubleValue());
            }
            break;
         case 4:
            if (autoInc == null) {
               sm.storeFloat(idx, 0.0F);
            } else {
               sm.storeFloat(idx, ((Number)autoInc).floatValue());
            }
            break;
         case 5:
            if (autoInc == null) {
               sm.storeInt(idx, 0);
            } else {
               sm.storeInt(idx, ((Number)autoInc).intValue());
            }
            break;
         case 6:
            if (autoInc == null) {
               sm.storeLong(idx, 0L);
            } else {
               sm.storeLong(idx, ((Number)autoInc).longValue());
            }
            break;
         case 7:
            if (autoInc == null) {
               sm.storeShort(idx, (short)0);
            } else {
               sm.storeShort(idx, ((Number)autoInc).shortValue());
            }
            break;
         default:
            throw new InternalException();
      }

   }

   public ColumnIO getColumnIO() {
      return this.field.getColumnIO();
   }

   public Object[] getResultArguments() {
      return null;
   }

   public Object toEmbeddedObjectValue(Object val) {
      return val;
   }

   public Object toEmbeddedDataStoreValue(Object val, JDBCStore store) {
      return this.toDataStoreValue(val, store);
   }

   public void loadEmbedded(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Object val) throws SQLException {
      sm.store(this.field.getIndex(), val);
   }
}
