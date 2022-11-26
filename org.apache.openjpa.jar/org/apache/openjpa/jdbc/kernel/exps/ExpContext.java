package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;

public class ExpContext {
   public JDBCStore store;
   public Object[] params;
   public JDBCFetchConfiguration fetch;

   public ExpContext() {
   }

   public ExpContext(JDBCStore store, Object[] params, JDBCFetchConfiguration fetch) {
      this.store = store;
      this.params = params;
      this.fetch = fetch;
   }
}
