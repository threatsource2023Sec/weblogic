package org.apache.openjpa.jdbc.meta.strats;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.openjpa.jdbc.schema.Column;

public class TimestampVersionStrategy extends ColumnVersionStrategy {
   public static final String ALIAS = "timestamp";

   public String getAlias() {
      return "timestamp";
   }

   protected int getJavaType() {
      return 1011;
   }

   protected Object nextVersion(Object version) {
      return new Timestamp(System.currentTimeMillis());
   }

   public Map getBulkUpdateValues() {
      Column[] cols = this.vers.getColumns();
      Map map = new HashMap(cols.length);
      Date d = new Date();

      for(int i = 0; i < cols.length; ++i) {
         map.put(cols[i], d);
      }

      return map;
   }
}
