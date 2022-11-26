package com.ziclix.python.sql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyUnicode;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.core.codecs;

public class PyStatement extends PyObject implements Traverseproc {
   public static final int STATEMENT_STATIC = 2;
   public static final int STATEMENT_PREPARED = 4;
   public static final int STATEMENT_CALLABLE = 8;
   private int style;
   private Object sql;
   private boolean closed;
   Statement statement;
   protected static PyList __methods__;
   protected static PyList __members__;

   public PyStatement(Statement statement, Object sql, int style) {
      this.statement = statement;
      this.sql = sql;
      this.style = style;
      this.closed = false;
   }

   public PyStatement(Statement statement, Procedure procedure) {
      this(statement, procedure, 8);
   }

   public PyUnicode __unicode__() {
      if (this.sql instanceof String) {
         return Py.newUnicode((String)this.sql);
      } else if (this.sql instanceof Procedure) {
         try {
            return Py.newUnicode(((Procedure)this.sql).toSql());
         } catch (SQLException var2) {
            throw zxJDBC.makeException((Throwable)var2);
         }
      } else {
         return super.__unicode__();
      }
   }

   public PyString __str__() {
      return Py.newString(this.__unicode__().encode(codecs.getDefaultEncoding(), "replace"));
   }

   public String toString() {
      return String.format("<PyStatement object at %s for [%s]", Py.idstr(this), this.__unicode__());
   }

   public PyObject __findattr_ex__(String name) {
      if ("style".equals(name)) {
         return Py.newInteger(this.style);
      } else if ("closed".equals(name)) {
         return Py.newBoolean(this.closed);
      } else if ("__statement__".equals(name)) {
         return Py.java2py(this.statement);
      } else if ("__methods__".equals(name)) {
         return __methods__;
      } else {
         return (PyObject)("__members__".equals(name) ? __members__ : super.__findattr_ex__(name));
      }
   }

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"classDictInit", (PyObject)null);
      dict.__setitem__((String)"statement", (PyObject)null);
      dict.__setitem__((String)"execute", (PyObject)null);
      dict.__setitem__((String)"prepare", (PyObject)null);
      dict.__setitem__((String)"STATEMENT_STATIC", (PyObject)null);
      dict.__setitem__((String)"STATEMENT_PREPARED", (PyObject)null);
      dict.__setitem__((String)"STATEMENT_CALLABLE", (PyObject)null);
   }

   public void __del__() {
      this.close();
   }

   public void execute(PyCursor cursor, PyObject params, PyObject bindings) throws SQLException {
      if (this.closed) {
         throw zxJDBC.makeException(zxJDBC.ProgrammingError, "statement is closed");
      } else {
         this.prepare(cursor, params, bindings);
         Fetch fetch = cursor.fetch;
         switch (this.style) {
            case 2:
               if (this.statement.execute((String)this.sql)) {
                  fetch.add(this.statement.getResultSet());
               }
               break;
            case 4:
               PreparedStatement preparedStatement = (PreparedStatement)this.statement;
               if (preparedStatement.execute()) {
                  fetch.add(preparedStatement.getResultSet());
               }
               break;
            case 8:
               CallableStatement callableStatement = (CallableStatement)this.statement;
               if (callableStatement.execute()) {
                  fetch.add(callableStatement.getResultSet());
               }

               fetch.add(callableStatement, (Procedure)this.sql, params);
               break;
            default:
               throw zxJDBC.makeException(zxJDBC.ProgrammingError, zxJDBC.getString("invalidStyle"));
         }

      }
   }

   private void prepare(PyCursor cursor, PyObject params, PyObject bindings) throws SQLException {
      if (params != Py.None && this.style != 2) {
         DataHandler datahandler = cursor.datahandler;
         int columns = false;
         int column = false;
         int index = params.__len__();
         PreparedStatement preparedStatement = (PreparedStatement)this.statement;
         Procedure procedure = this.style == 8 ? (Procedure)this.sql : null;
         int columns;
         if (this.style != 8) {
            columns = params.__len__();
            preparedStatement.clearParameters();
         } else {
            columns = procedure.columns == Py.None ? 0 : procedure.columns.__len__();
         }

         while(true) {
            while(true) {
               int column;
               do {
                  if (columns-- <= 0) {
                     return;
                  }

                  column = columns + 1;
               } while(procedure != null && !procedure.isInput(column));

               --index;
               PyObject param = params.__getitem__(index);
               if (bindings != Py.None) {
                  PyObject binding = bindings.__finditem__((PyObject)Py.newInteger(index));
                  if (binding != null) {
                     try {
                        int bindingValue = binding.asInt();
                        datahandler.setJDBCObject(preparedStatement, column, param, bindingValue);
                        continue;
                     } catch (PyException var13) {
                        throw zxJDBC.makeException(zxJDBC.ProgrammingError, zxJDBC.getString("bindingValue"));
                     }
                  }
               }

               datahandler.setJDBCObject(preparedStatement, column, param);
            }
         }
      }
   }

   public void close() {
      try {
         this.statement.close();
      } catch (SQLException var5) {
         throw zxJDBC.makeException((Throwable)var5);
      } finally {
         this.closed = true;
      }

   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.sql != null) {
         int retVal;
         if (this.sql instanceof PyObject) {
            retVal = visit.visit((PyObject)this.sql, arg);
            if (retVal != 0) {
               return retVal;
            }
         } else if (this.sql instanceof Traverseproc) {
            retVal = ((Traverseproc)this.sql).traverse(visit, arg);
            if (retVal != 0) {
               return retVal;
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (this.sql != null && ob != null) {
         if (this.sql instanceof PyObject) {
            return this.sql == ob;
         } else {
            return this.sql instanceof Traverseproc ? ((Traverseproc)this.sql).refersDirectlyTo(ob) : false;
         }
      } else {
         return false;
      }
   }

   static {
      PyObject[] m = new PyObject[]{new PyString("close")};
      __methods__ = new PyList(m);
      m = new PyObject[]{new PyString("style"), new PyString("closed"), new PyString("__statement__")};
      __members__ = new PyList(m);
   }
}
