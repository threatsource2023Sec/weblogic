package org.apache.openjpa.jdbc.sql;

import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.jdbc.schema.Column;

public class FirebirdDictionary extends InterbaseDictionary {
   public FirebirdDictionary() {
      this.platform = "Firebird";
      this.supportsLockingWithMultipleTables = false;
      this.forUpdateClause = "FOR UPDATE WITH LOCK";
   }

   public String getPlaceholderValueString(Column col) {
      return super.getPlaceholderValueString(col) + " AS " + this.getTypeName(col);
   }

   public void substring(SQLBuffer buf, FilterValue str, FilterValue start, FilterValue end) {
      buf.append("SUBSTRING(");
      str.appendTo(buf);
      buf.append(" FROM ");
      start.appendTo(buf);
      buf.append(" + 1");
      if (end != null) {
         buf.append(" FOR ");
         end.appendTo(buf);
         buf.append(" - (");
         start.appendTo(buf);
         buf.append(")");
      }

      buf.append(")");
   }
}
