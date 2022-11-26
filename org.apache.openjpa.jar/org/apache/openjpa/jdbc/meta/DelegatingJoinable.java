package org.apache.openjpa.jdbc.meta;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class DelegatingJoinable implements Joinable {
   private static final Localizer _loc = Localizer.forPackage(DelegatingJoinable.class);
   private final Joinable _join;
   private final ForeignKey _fk;
   private final Column[] _cols;

   public DelegatingJoinable(Joinable join, ForeignKey fk) {
      this._join = join;
      this._fk = fk;
      Column[] pks = join.getColumns();
      this._cols = new Column[pks.length];

      for(int i = 0; i < pks.length; ++i) {
         this._cols[i] = fk.getColumn(pks[i]);
         if (this._cols[i] == null) {
            throw new MetaDataException(_loc.get("incomplete-join", (Object)pks[i].getFullName()));
         }
      }

   }

   public DelegatingJoinable(Joinable join, Column[] cols) {
      this._join = join;
      this._fk = null;
      this._cols = cols;
      if (cols.length != join.getColumns().length) {
         throw new MetaDataException(_loc.get("bad-remap", (Object)join.getColumns()[0].getFullName()));
      }
   }

   public int getFieldIndex() {
      return this._join.getFieldIndex();
   }

   public Object getPrimaryKeyValue(Result res, Column[] cols, ForeignKey fk, JDBCStore store, Joins joins) throws SQLException {
      return this._join.getPrimaryKeyValue(res, cols, fk, store, joins);
   }

   public Column[] getColumns() {
      return this._cols;
   }

   public Object getJoinValue(Object val, Column col, JDBCStore store) {
      return this._join.getJoinValue(val, this.translate(col), store);
   }

   public Object getJoinValue(OpenJPAStateManager sm, Column col, JDBCStore store) {
      return this._join.getJoinValue(sm, this.translate(col), store);
   }

   public void setAutoAssignedValue(OpenJPAStateManager sm, JDBCStore store, Column col, Object autogen) {
      this._join.setAutoAssignedValue(sm, store, this.translate(col), autogen);
   }

   private Column translate(Column col) {
      if (this._fk != null) {
         return this._fk.getPrimaryKeyColumn(col);
      } else {
         Column[] cols = this._join.getColumns();

         for(int i = 0; i < cols.length; ++i) {
            if (col == cols[i]) {
               return this._cols[i];
            }
         }

         return null;
      }
   }
}
