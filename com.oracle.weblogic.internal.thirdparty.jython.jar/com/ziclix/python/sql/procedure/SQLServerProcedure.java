package com.ziclix.python.sql.procedure;

import com.ziclix.python.sql.Procedure;
import com.ziclix.python.sql.PyCursor;
import java.sql.SQLException;
import org.python.core.Py;
import org.python.core.PyObject;

public class SQLServerProcedure extends Procedure {
   public SQLServerProcedure(PyCursor cursor, PyObject name) throws SQLException {
      super(cursor, name);
   }

   protected PyObject getDefault() {
      return Py.None;
   }

   protected String getProcedureName() {
      StringBuffer proc = new StringBuffer();
      if (this.procedureSchema.__nonzero__()) {
         proc.append(this.procedureSchema.toString()).append(".");
      }

      return proc.append(this.procedureName.toString()).toString();
   }
}
