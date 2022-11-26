package org.apache.openjpa.jdbc.meta.strats;

import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.PostgresDictionary;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class LobFieldStrategy extends AbstractFieldStrategy {
   private int fieldType;
   private boolean isBlob;

   public void map(boolean adapt) {
      this.assertNotMappedBy();
      this.field.mapJoin(adapt, false);
      this.field.getKeyMapping().getValueInfo().assertNoSchemaComponents(this.field.getKey(), !adapt);
      this.field.getElementMapping().getValueInfo().assertNoSchemaComponents(this.field.getElement(), !adapt);
      this.field.setStream(true);
      ValueMappingInfo vinfo = this.field.getValueInfo();
      vinfo.assertNoJoin(this.field, true);
      vinfo.assertNoForeignKey(this.field, !adapt);
      Column tmpCol = new Column();
      tmpCol.setName(this.field.getName());
      tmpCol.setType(this.fieldType);
      tmpCol.setJavaType(this.field.getTypeCode());
      tmpCol.setSize(-1);
      Column[] cols = vinfo.getColumns(this.field, this.field.getName(), new Column[]{tmpCol}, this.field.getTable(), adapt);
      this.field.setColumns(cols);
      this.field.setColumnIO(vinfo.getColumnIO());
      this.field.mapConstraints(this.field.getName(), adapt);
      this.field.mapPrimaryKey(adapt);
   }

   public Boolean isCustomInsert(OpenJPAStateManager sm, JDBCStore store) {
      return null;
   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Select sel = this.createSelect(sm, store);
      store.getDBDictionary().deleteStream(store, sel);
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Object ob = this.toDataStoreValue(sm.fetchObjectField(this.field.getIndex()), store);
      Row row = this.field.getRow(sm, store, rm, 1);
      if (this.field.getColumnIO().isInsertable(0, ob == null)) {
         Select sel = this.createSelect(sm, store);
         if (this.isBlob) {
            store.getDBDictionary().insertBlobForStreamingLoad(row, this.field.getColumns()[0], store, ob, sel);
         } else {
            store.getDBDictionary().insertClobForStreamingLoad(row, this.field.getColumns()[0], ob);
         }
      }

   }

   public void customInsert(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      Object ob = this.toDataStoreValue(sm.fetchObjectField(this.field.getIndex()), store);
      if (this.field.getColumnIO().isInsertable(0, ob == null) && ob != null) {
         Select sel = this.createSelect(sm, store);
         if (this.isBlob) {
            store.getDBDictionary().updateBlob(sel, store, (InputStream)ob);
         } else {
            store.getDBDictionary().updateClob(sel, store, (Reader)ob);
         }
      }

   }

   public Boolean isCustomUpdate(OpenJPAStateManager sm, JDBCStore store) {
      return null;
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Object ob = this.toDataStoreValue(sm.fetchObjectField(this.field.getIndex()), store);
      if (this.field.getColumnIO().isUpdatable(0, ob == null)) {
         Row row = this.field.getRow(sm, store, rm, 0);
         Select sel = this.createSelect(sm, store);
         if (this.isBlob) {
            store.getDBDictionary().insertBlobForStreamingLoad(row, this.field.getColumns()[0], store, ob, sel);
         } else {
            store.getDBDictionary().insertClobForStreamingLoad(row, this.field.getColumns()[0], sel);
         }
      }

   }

   public void customUpdate(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      Object ob = this.toDataStoreValue(sm.fetchObjectField(this.field.getIndex()), store);
      if (this.field.getColumnIO().isUpdatable(0, ob == null) && ob != null) {
         Select sel = this.createSelect(sm, store);
         if (this.isBlob) {
            store.getDBDictionary().updateBlob(sel, store, (InputStream)ob);
         } else {
            store.getDBDictionary().updateClob(sel, store, (Reader)ob);
         }
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
         if (this.isBlob) {
            sm.storeObject(this.field.getIndex(), res.getLOBStream(store, col));
         } else {
            sm.storeObject(this.field.getIndex(), res.getCharacterStream(col));
         }
      }

   }

   protected void assertNotMappedBy() {
      if (this.field != null && this.field.getMappedBy() != null) {
         throw new UnsupportedOperationException();
      }
   }

   public void setFieldMapping(FieldMapping owner) {
      this.field = owner;
      if (owner.getElementMapping().getMappingRepository().getDBDictionary() instanceof PostgresDictionary) {
         this.fieldType = 4;
         this.isBlob = true;
         this.field.setTypeCode(5);
      } else if (owner.getType().isAssignableFrom(InputStream.class)) {
         this.isBlob = true;
         this.fieldType = 2004;
      } else if (owner.getType().isAssignableFrom(Reader.class)) {
         this.isBlob = false;
         this.fieldType = 2005;
      }

   }

   private Select createSelect(OpenJPAStateManager sm, JDBCStore store) {
      Select sel = store.getSQLFactory().newSelect();
      sel.select(this.field.getColumns()[0]);
      sel.selectPrimaryKey(this.field.getDefiningMapping());
      sel.wherePrimaryKey(sm.getObjectId(), this.field.getDefiningMapping(), store);
      sel.setLob(true);
      return sel;
   }
}
