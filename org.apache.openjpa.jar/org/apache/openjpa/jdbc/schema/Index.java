package org.apache.openjpa.jdbc.schema;

import org.apache.commons.lang.StringUtils;

public class Index extends LocalConstraint {
   private boolean _unique = false;

   public Index() {
   }

   public Index(String name, Table table) {
      super(name, table);
   }

   public boolean isUnique() {
      return this._unique;
   }

   public void setUnique(boolean unique) {
      this._unique = unique;
   }

   public boolean isLogical() {
      return false;
   }

   public boolean equalsIndex(Index idx) {
      if (idx == this) {
         return true;
      } else if (idx == null) {
         return false;
      } else if (this.isUnique() != idx.isUnique()) {
         return false;
      } else {
         return !StringUtils.equalsIgnoreCase(this.getFullName(), idx.getFullName()) ? false : this.equalsLocalConstraint(idx);
      }
   }
}
