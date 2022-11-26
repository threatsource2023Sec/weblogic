package com.ziclix.python.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.python.core.Py;
import org.python.core.PyList;
import org.python.core.PyObject;

public abstract class FilterDataHandler extends DataHandler {
   private DataHandler delegate;

   public FilterDataHandler(DataHandler delegate) {
      this.delegate = delegate;
   }

   public PyObject getRowId(Statement stmt) throws SQLException {
      return this.delegate.getRowId(stmt);
   }

   public void preExecute(Statement stmt) throws SQLException {
      this.delegate.preExecute(stmt);
   }

   public void postExecute(Statement stmt) throws SQLException {
      this.delegate.postExecute(stmt);
   }

   public void setJDBCObject(PreparedStatement stmt, int index, PyObject object) throws SQLException {
      this.delegate.setJDBCObject(stmt, index, object);
   }

   public void setJDBCObject(PreparedStatement stmt, int index, PyObject object, int type) throws SQLException {
      this.delegate.setJDBCObject(stmt, index, object, type);
   }

   public PyObject getPyObject(ResultSet set, int col, int type) throws SQLException {
      return this.delegate.getPyObject(set, col, type);
   }

   public PyObject __chain__() {
      PyList list = new PyList();
      DataHandler handler = this;

      while(handler != null) {
         list.append(Py.java2py(handler));
         if (handler instanceof FilterDataHandler) {
            handler = ((FilterDataHandler)handler).delegate;
         } else {
            handler = null;
         }
      }

      return list;
   }
}
