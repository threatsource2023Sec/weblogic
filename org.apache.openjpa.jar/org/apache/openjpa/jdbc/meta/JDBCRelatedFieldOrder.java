package org.apache.openjpa.jdbc.meta;

import java.util.Comparator;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

class JDBCRelatedFieldOrder implements JDBCOrder {
   private static final Localizer _loc = Localizer.forPackage(JDBCRelatedFieldOrder.class);
   private final FieldMapping _fm;
   private final boolean _asc;

   public JDBCRelatedFieldOrder(FieldMapping owner, FieldMapping rel, boolean asc) {
      if (!rel.isInDefaultFetchGroup() && !rel.isPrimaryKey()) {
         throw new MetaDataException(_loc.get("nondfg-field-orderable", owner, rel.getName()));
      } else {
         this._fm = rel;
         this._asc = asc;
      }
   }

   public String getName() {
      return this._fm.getName();
   }

   public boolean isAscending() {
      return this._asc;
   }

   public Comparator getComparator() {
      return null;
   }

   public boolean isInRelation() {
      return true;
   }

   public void order(Select sel, ClassMapping elem, Joins joins) {
      FieldMapping fm = elem.getFieldMapping(this._fm.getIndex());
      sel.orderBy(fm.getColumns(), this._asc, joins, false);
   }
}
