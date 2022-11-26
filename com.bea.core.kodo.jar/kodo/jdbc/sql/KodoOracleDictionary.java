package kodo.jdbc.sql;

import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.OracleDictionary;

public class KodoOracleDictionary extends OracleDictionary {
   public String getPlaceholderValueString(Column col) {
      switch (col.getType()) {
         case 2005:
            return "NULL";
         default:
            return super.getPlaceholderValueString(col);
      }
   }
}
