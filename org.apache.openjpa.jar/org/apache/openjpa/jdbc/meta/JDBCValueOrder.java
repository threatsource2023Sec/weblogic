package org.apache.openjpa.jdbc.meta;

import java.util.Comparator;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Select;

class JDBCValueOrder implements JDBCOrder {
   private final FieldMapping _fm;
   private final boolean _asc;

   public JDBCValueOrder(FieldMapping fm, boolean asc) {
      this._fm = fm;
      this._asc = asc;
   }

   public String getName() {
      return "#element";
   }

   public boolean isAscending() {
      return this._asc;
   }

   public Comparator getComparator() {
      return null;
   }

   public boolean isInRelation() {
      return this._fm.getElement().getTypeMetaData() != null;
   }

   public void order(Select sel, ClassMapping elem, Joins joins) {
      if (elem != null) {
         sel.orderBy(elem.getPrimaryKeyColumns(), this._asc, joins, false);
      } else {
         sel.orderBy(this._fm.getElementMapping().getColumns(), this._asc, joins, false);
      }

   }
}
