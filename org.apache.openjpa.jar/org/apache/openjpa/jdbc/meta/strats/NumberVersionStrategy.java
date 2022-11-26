package org.apache.openjpa.jdbc.meta.strats;

import java.util.HashMap;
import java.util.Map;
import org.apache.openjpa.jdbc.schema.Column;
import serp.util.Numbers;

public class NumberVersionStrategy extends ColumnVersionStrategy {
   public static final String ALIAS = "version-number";
   private Number _initial = Numbers.valueOf(1);

   public void setInitialValue(int initial) {
      this._initial = Numbers.valueOf(initial);
   }

   public int getInitialValue() {
      return this._initial.intValue();
   }

   public String getAlias() {
      return "version-number";
   }

   protected int getJavaType() {
      return 5;
   }

   protected Object nextVersion(Object version) {
      return version == null ? this._initial : Numbers.valueOf(((Number)version).intValue() + 1);
   }

   public Map getBulkUpdateValues() {
      Column[] cols = this.vers.getColumns();
      Map map = new HashMap(cols.length);

      for(int i = 0; i < cols.length; ++i) {
         map.put(cols[i], cols[i].getName() + " + 1");
      }

      return map;
   }
}
