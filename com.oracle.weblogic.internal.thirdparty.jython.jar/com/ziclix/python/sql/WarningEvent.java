package com.ziclix.python.sql;

import java.sql.SQLWarning;
import java.util.EventObject;

public class WarningEvent extends EventObject {
   private SQLWarning warning;

   public WarningEvent(Object source, SQLWarning warning) {
      super(source);
      this.warning = warning;
   }

   public SQLWarning getWarning() {
      return this.warning;
   }
}
