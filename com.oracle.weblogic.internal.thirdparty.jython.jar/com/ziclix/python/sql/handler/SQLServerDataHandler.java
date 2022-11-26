package com.ziclix.python.sql.handler;

import com.ziclix.python.sql.DataHandler;
import com.ziclix.python.sql.FilterDataHandler;
import com.ziclix.python.sql.Procedure;
import com.ziclix.python.sql.PyCursor;
import com.ziclix.python.sql.procedure.SQLServerProcedure;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.python.core.Py;
import org.python.core.PyObject;

public class SQLServerDataHandler extends FilterDataHandler {
   public static final int UNICODE_VARCHAR = -9;

   public SQLServerDataHandler(DataHandler datahandler) {
      super(datahandler);
   }

   public Procedure getProcedure(PyCursor cursor, PyObject name) throws SQLException {
      return new SQLServerProcedure(cursor, name);
   }

   public PyObject getPyObject(ResultSet set, int col, int type) throws SQLException {
      PyObject obj = Py.None;
      switch (type) {
         case -9:
            obj = super.getPyObject(set, col, 12);
            break;
         default:
            obj = super.getPyObject(set, col, type);
      }

      return !set.wasNull() && obj != null ? obj : Py.None;
   }
}
