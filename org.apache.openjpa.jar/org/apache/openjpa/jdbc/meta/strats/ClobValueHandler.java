package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;

public class ClobValueHandler extends AbstractValueHandler {
   private static final ClobValueHandler _instance = new ClobValueHandler();

   public static ClobValueHandler getInstance() {
      return _instance;
   }

   public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
      Column col = new Column();
      col.setName(name);
      col.setJavaType(9);
      col.setSize(-1);
      return new Column[]{col};
   }
}
