package com.ziclix.python.sql.handler;

import com.ziclix.python.sql.DataHandler;
import com.ziclix.python.sql.FilterDataHandler;
import com.ziclix.python.sql.zxJDBC;
import java.sql.SQLException;
import java.sql.Statement;
import org.python.core.Py;

public class UpdateCountDataHandler extends FilterDataHandler {
   private static boolean once = false;
   public int updateCount;

   public UpdateCountDataHandler(DataHandler datahandler) {
      super(datahandler);
      if (!once) {
         Py.writeError("UpdateCountDataHandler", zxJDBC.getString("updateCountDeprecation"));
         once = true;
      }

      this.updateCount = -1;
   }

   public void preExecute(Statement stmt) throws SQLException {
      super.preExecute(stmt);
      this.updateCount = -1;
   }

   public void postExecute(Statement stmt) throws SQLException {
      super.postExecute(stmt);
      this.updateCount = stmt.getUpdateCount();
   }
}
