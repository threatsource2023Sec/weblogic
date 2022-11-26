package com.ziclix.python.sql.handler;

import com.ziclix.python.sql.DataHandler;
import com.ziclix.python.sql.FilterDataHandler;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.Generic;

public abstract class RowIdHandler extends FilterDataHandler {
   private static Map ROWIDS = Generic.map();
   private static Object CHECKED = new Object();

   public RowIdHandler(DataHandler handler) {
      super(handler);
   }

   protected abstract String getRowIdMethodName();

   public PyObject getRowId(Statement stmt) throws SQLException {
      Class c = stmt.getClass();
      Object o = ROWIDS.get(c);
      if (o == null) {
         synchronized(ROWIDS) {
            try {
               o = c.getMethod(this.getRowIdMethodName(), (Class[])null);
               ROWIDS.put(c, o);
            } catch (Throwable var7) {
               ROWIDS.put(c, CHECKED);
            }
         }
      }

      if (o != null && o != CHECKED) {
         try {
            return Py.java2py(((Method)o).invoke(stmt, (Object[])null));
         } catch (Throwable var9) {
         }
      }

      return super.getRowId(stmt);
   }
}
