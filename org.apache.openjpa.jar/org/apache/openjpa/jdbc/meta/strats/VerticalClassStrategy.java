package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.DelegatingJoinable;
import org.apache.openjpa.jdbc.meta.Joinable;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class VerticalClassStrategy extends AbstractClassStrategy {
   public static final String ALIAS = "vertical";
   private static final Localizer _loc = Localizer.forPackage(VerticalClassStrategy.class);
   private Boolean _fkOid = null;

   public String getAlias() {
      return "vertical";
   }

   public void map(boolean adapt) {
      ClassMapping sup = this.cls.getMappedPCSuperclassMapping();
      if (sup == null) {
         throw new MetaDataException(_loc.get("not-sub", (Object)this.cls));
      } else {
         ClassMappingInfo info = this.cls.getMappingInfo();
         info.assertNoIndex(this.cls, false);
         info.assertNoUnique(this.cls, false);
         Table table = info.getTable(this.cls, adapt);
         ForeignKey fk = info.getSuperclassJoin(this.cls, table, adapt);
         Column[] pkCols = fk.getColumns();
         this.cls.setTable(table);
         this.cls.setJoinForeignKey(fk);
         this.cls.setPrimaryKeyColumns(pkCols);
         this.cls.setColumnIO(info.getColumnIO());
         PrimaryKey pk = table.getPrimaryKey();
         if (pk == null) {
            String pkname = null;
            if (adapt) {
               pkname = this.cls.getMappingRepository().getMappingDefaults().getPrimaryKeyName(this.cls, table);
            }

            pk = table.addPrimaryKey(pkname);
            pk.setLogical(!adapt);
            pk.setColumns(pkCols);
         }

         for(int i = 0; i < pkCols.length; ++i) {
            if (this.cls.getJoinable(pkCols[i]) == null) {
               Joinable join = sup.assertJoinable(fk.getPrimaryKeyColumn(pkCols[i]));
               Joinable join = new DelegatingJoinable(join, fk);
               Column[] cols = join.getColumns();

               for(int j = 0; j < cols.length; ++j) {
                  this.cls.setJoinable(cols[j], join);
               }
            }
         }

      }
   }

   public void initialize() {
      ClassMapping sup = this.cls.getMappedPCSuperclassMapping();
      this._fkOid = sup.isForeignKeyObjectId(this.cls.getJoinForeignKey());
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = rm.getRow(this.cls.getTable(), 1, sm, true);
      row.setPrimaryKey(this.cls.getColumnIO(), sm);
      row.setForeignKey(this.cls.getJoinForeignKey(), this.cls.getColumnIO(), sm);
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = rm.getRow(this.cls.getTable(), 0, sm, false);
      if (row != null) {
         row.wherePrimaryKey(sm);
         row.whereForeignKey(this.cls.getJoinForeignKey(), sm);
      }

   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = rm.getRow(this.cls.getTable(), 2, sm, true);
      row.wherePrimaryKey(sm);
      row.whereForeignKey(this.cls.getJoinForeignKey(), sm);
   }

   public boolean isPrimaryKeyObjectId(boolean hasAll) {
      return Boolean.TRUE.equals(this._fkOid) || !hasAll && this._fkOid == null;
   }

   public Joins joinSuperclass(Joins joins, boolean toThis) {
      return toThis ? joins.outerJoin(this.cls.getJoinForeignKey(), true, false) : joins.join(this.cls.getJoinForeignKey(), false, false);
   }

   public boolean supportsEagerSelect(Select sel, OpenJPAStateManager sm, JDBCStore store, ClassMapping base, JDBCFetchConfiguration fetch) {
      return store.getDBDictionary().canOuterJoin(sel.getJoinSyntax(), this.cls.getJoinForeignKey());
   }
}
