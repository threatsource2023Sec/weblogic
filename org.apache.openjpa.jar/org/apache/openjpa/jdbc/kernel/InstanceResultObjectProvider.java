package org.apache.openjpa.jdbc.kernel;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SelectExecutor;

public class InstanceResultObjectProvider extends SelectResultObjectProvider {
   private final ClassMapping _mapping;

   public InstanceResultObjectProvider(SelectExecutor sel, ClassMapping mapping, JDBCStore store, JDBCFetchConfiguration fetch) {
      super(sel, store, fetch);
      this._mapping = mapping;
   }

   public Object getResultObject() throws SQLException {
      Result res = this.getResult();
      ClassMapping mapping = res.getBaseMapping();
      if (mapping == null) {
         mapping = this._mapping;
      }

      return res.load(mapping, this.getStore(), this.getFetchConfiguration());
   }
}
