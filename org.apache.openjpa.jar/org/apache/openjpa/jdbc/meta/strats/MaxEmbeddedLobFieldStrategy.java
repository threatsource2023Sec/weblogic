package org.apache.openjpa.jdbc.meta.strats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;

abstract class MaxEmbeddedLobFieldStrategy extends AbstractFieldStrategy {
   protected abstract int getExpectedJavaType();

   protected abstract void update(OpenJPAStateManager var1, Row var2) throws SQLException;

   protected abstract Boolean isCustom(OpenJPAStateManager var1, JDBCStore var2);

   protected abstract void putData(OpenJPAStateManager var1, ResultSet var2, DBDictionary var3) throws SQLException;

   public void map(boolean adapt) {
      this.assertNotMappedBy();
      this.field.mapJoin(adapt, false);
      this.field.getKeyMapping().getValueInfo().assertNoSchemaComponents(this.field.getKey(), !adapt);
      this.field.getElementMapping().getValueInfo().assertNoSchemaComponents(this.field.getElement(), !adapt);
      ValueMappingInfo vinfo = this.field.getValueInfo();
      vinfo.assertNoJoin(this.field, true);
      vinfo.assertNoForeignKey(this.field, !adapt);
      Column tmpCol = new Column();
      tmpCol.setName(this.field.getName());
      tmpCol.setJavaType(this.getExpectedJavaType());
      tmpCol.setSize(-1);
      Column[] cols = vinfo.getColumns(this.field, this.field.getName(), new Column[]{tmpCol}, this.field.getTable(), adapt);
      this.field.setColumns(cols);
      this.field.setColumnIO(vinfo.getColumnIO());
      this.field.mapConstraints(this.field.getName(), adapt);
      this.field.mapPrimaryKey(adapt);
   }

   public Boolean isCustomInsert(OpenJPAStateManager sm, JDBCStore store) {
      return !this.field.getColumnIO().isInsertable(0, false) ? Boolean.FALSE : this.isCustom(sm, store);
   }

   public Boolean isCustomUpdate(OpenJPAStateManager sm, JDBCStore store) {
      return !this.field.getColumnIO().isUpdatable(0, false) ? Boolean.FALSE : this.isCustom(sm, store);
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

   public void customInsert(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      this.customUpdate(sm, store);
   }

   public void customUpdate(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      Column col = this.field.getColumns()[0];
      Select sel = store.getSQLFactory().newSelect();
      sel.select(col);
      this.field.wherePrimaryKey(sel, sm, store);
      SQLBuffer sql = sel.toSelect(true, store.getFetchConfiguration());
      Connection conn = store.getConnection();
      PreparedStatement stmnt = null;
      ResultSet rs = null;

      try {
         stmnt = sql.prepareStatement(conn, 1004, 1008);
         rs = stmnt.executeQuery();
         rs.next();
         this.putData(sm, rs, store.getDBDictionary());
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException var21) {
            }
         }

         if (stmnt != null) {
            try {
               stmnt.close();
            } catch (SQLException var20) {
            }
         }

         try {
            conn.close();
         } catch (SQLException var19) {
         }

      }

   }

   public int supportsSelect(Select sel, int type, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) {
      return type == 3 && sel.isSelected(this.field.getTable()) ? 1 : 0;
   }

   public int select(Select sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      if (!sel.isDistinct() && eagerMode != 0) {
         sel.select(this.field.getColumns()[0], this.field.join(sel));
         return 1;
      } else {
         return -1;
      }
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
      Column col = this.field.getColumns()[0];
      if (res.contains(col)) {
         sm.store(this.field.getIndex(), this.load((Column)col, (Result)res, (Joins)null));
      }

   }

   public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
      Column col = this.field.getColumns()[0];
      Select sel = store.getSQLFactory().newSelect();
      sel.select(col);
      this.field.wherePrimaryKey(sel, sm, store);
      Result res = sel.execute(store, fetch);
      Object val = null;

      try {
         if (res.next()) {
            val = this.load((Column)col, (Result)res, (Joins)null);
         }
      } finally {
         res.close();
      }

      sm.store(this.field.getIndex(), val);
   }

   protected Object load(Column col, Result res, Joins joins) throws SQLException {
      return res.getObject(col, (Object)null, joins);
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
      return this.load(this.field.getColumns()[0], res, joins);
   }

   public boolean isVersionable() {
      return false;
   }

   public void where(OpenJPAStateManager sm, JDBCStore store, RowManager rm, Object prevValue) throws SQLException {
   }
}
