package kodo.jdbc.sql;

import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.SybaseDictionary;

public class KodoSybaseDictionary extends SybaseDictionary {
   public String getPlaceholderValueString(Column col) {
      switch (col.getType()) {
         case 2005:
            return "NULL";
         default:
            return super.getPlaceholderValueString(col);
      }
   }
}
