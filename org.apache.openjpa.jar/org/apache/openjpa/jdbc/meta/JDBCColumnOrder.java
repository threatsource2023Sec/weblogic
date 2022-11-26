package org.apache.openjpa.jdbc.meta;

import java.util.Comparator;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Select;

class JDBCColumnOrder implements JDBCOrder {
   private Column _col = null;
   private ColumnIO _io = null;

   public Column getColumn() {
      return this._col;
   }

   public void setColumn(Column col) {
      this._col = col;
   }

   public ColumnIO getColumnIO() {
      return this._io == null ? ColumnIO.UNRESTRICTED : this._io;
   }

   public void setColumnIO(ColumnIO io) {
      this._io = io;
   }

   public String getName() {
      return this._col == null ? "" : this._col.getName();
   }

   public boolean isAscending() {
      return true;
   }

   public Comparator getComparator() {
      return null;
   }

   public boolean isInRelation() {
      return false;
   }

   public void order(Select sel, ClassMapping elem, Joins joins) {
      if (this._col != null) {
         sel.orderBy(this._col, true, joins, true);
      }

   }
}
