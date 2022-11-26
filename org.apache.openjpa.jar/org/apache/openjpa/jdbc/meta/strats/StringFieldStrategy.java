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
import org.apache.openjpa.util.MetaDataException;

public class StringFieldStrategy extends AbstractFieldStrategy implements Joinable, Embeddable {
   private static final Localizer _loc = Localizer.forPackage(StringFieldStrategy.class);

   public void map(boolean adapt) {
      if (this.field.getTypeCode() != 9) {
         throw new MetaDataException(_loc.get("not-string", (Object)this.field));
      } else {
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
      }
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      String str = (String)this.toDataStoreValue(sm.fetchString(this.field.getIndex()), store);
      if (this.field.getColumnIO().isInsertable(0, str == null)) {
         Row row = this.field.getRow(sm, store, rm, 1);
         if (row != null) {
            row.setString(this.field.getColumns()[0], str);
         }
      }

   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      String str = (String)this.toDataStoreValue(sm.fetchString(this.field.getIndex()), store);
      if (this.field.getColumnIO().isUpdatable(0, str == null)) {
         Row row = this.field.getRow(sm, store, rm, 0);
         if (row != null) {
            row.setString(this.field.getColumns()[0], str);
         }
      }

   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.field.deleteRow(sm, store, rm);
   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      if (val != null) {
         return val;
      } else if (this.field.getNullValue() != 1) {
         return null;
      } else {
         return this.field.getColumns()[0].getDefaultString() != null ? null : "";
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
         sm.storeString(this.field.getIndex(), res.getString(col));
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
      return res.getString(this.field.getColumns()[0], joins);
   }

   public boolean isVersionable() {
      return true;
   }

   public void where(OpenJPAStateManager sm, JDBCStore store, RowManager rm, Object prevValue) throws SQLException {
      Row row = this.field.getRow(sm, store, rm, 0);
      if (row != null) {
         Column col = this.field.getColumns()[0];
         if (prevValue == null) {
            row.whereNull(col);
         } else {
            row.whereString(col, prevValue.toString());
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

      return res.getString(col, joins);
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
      String str = autoInc == null ? null : autoInc.toString();
      sm.storeString(this.field.getIndex(), str);
   }

   public ColumnIO getColumnIO() {
      return this.field.getColumnIO();
   }

   public Object[] getResultArguments() {
      return null;
   }

   public Object toEmbeddedDataStoreValue(Object val, JDBCStore store) {
      return this.toDataStoreValue(val, store);
   }

   public Object toEmbeddedObjectValue(Object val) {
      return val;
   }

   public void loadEmbedded(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Object val) throws SQLException {
      sm.storeString(this.field.getIndex(), (String)val);
   }
}
