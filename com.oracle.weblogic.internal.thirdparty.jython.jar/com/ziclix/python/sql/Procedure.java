package com.ziclix.python.sql;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.BitSet;
import org.python.core.Py;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;

public class Procedure {
   protected static final int NAME = 3;
   protected static final int COLUMN_TYPE = 4;
   protected static final int DATA_TYPE = 5;
   protected static final int DATA_TYPE_NAME = 6;
   protected static final int PRECISION = 7;
   protected static final int LENGTH = 8;
   protected static final int SCALE = 9;
   protected static final int NULLABLE = 11;
   protected PyCursor cursor;
   protected PyObject columns;
   protected PyObject procedureCatalog;
   protected PyObject procedureSchema;
   protected PyObject procedureName;
   protected BitSet inputSet;

   public Procedure(PyCursor cursor, PyObject name) throws SQLException {
      this.cursor = cursor;
      this.inputSet = new BitSet();
      if (name instanceof PyString) {
         this.procedureCatalog = this.getDefault();
         this.procedureSchema = this.getDefault();
         this.procedureName = name;
      } else if (PyCursor.isSeq(name) && name.__len__() == 3) {
         this.procedureCatalog = name.__getitem__(0);
         this.procedureSchema = name.__getitem__(1);
         this.procedureName = name.__getitem__(2);
      }

      this.fetchColumns();
   }

   public CallableStatement prepareCall() throws SQLException {
      return this.prepareCall(Py.None, Py.None);
   }

   public CallableStatement prepareCall(PyObject rsType, PyObject rsConcur) throws SQLException {
      CallableStatement statement = null;
      boolean normal = rsType == Py.None && rsConcur == Py.None;

      try {
         String sqlString = this.toSql();
         if (normal) {
            statement = this.cursor.connection.connection.prepareCall(sqlString);
         } else {
            int t = rsType.asInt();
            int c = rsConcur.asInt();
            statement = this.cursor.connection.connection.prepareCall(sqlString, t, c);
         }

         this.registerOutParameters(statement);
         return statement;
      } catch (SQLException var9) {
         if (statement != null) {
            try {
               statement.close();
            } catch (Exception var8) {
            }
         }

         throw var9;
      }
   }

   public void normalizeInput(PyObject params, PyObject bindings) throws SQLException {
      if (this.columns != Py.None) {
         int i = 0;
         int len = this.columns.__len__();
         int binding = 0;

         while(i < len) {
            PyObject column = this.columns.__getitem__(i);
            int colType = column.__getitem__(4).asInt();
            switch (colType) {
               case 1:
               case 2:
                  PyInteger key = Py.newInteger(binding++);
                  if (bindings.__finditem__((PyObject)key) == null) {
                     int dataType = column.__getitem__(5).asInt();
                     bindings.__setitem__((PyObject)key, Py.newInteger(dataType));
                  }

                  this.inputSet.set(i + 1);
               default:
                  ++i;
            }
         }

      }
   }

   public boolean isInput(int index) throws SQLException {
      return this.inputSet.get(index);
   }

   public String toSql() throws SQLException {
      int colParam = 0;
      int colReturn = 0;
      if (this.columns != Py.None) {
         int i = 0;

         for(int len = this.columns.__len__(); i < len; ++i) {
            PyObject column = this.columns.__getitem__(i);
            int colType = column.__getitem__(4).asInt();
            switch (colType) {
               case 0:
                  throw zxJDBC.makeException(zxJDBC.NotSupportedError, "procedureColumnUnknown");
               case 1:
               case 2:
               case 4:
                  ++colParam;
                  break;
               case 3:
                  throw zxJDBC.makeException(zxJDBC.NotSupportedError, "procedureColumnResult");
               case 5:
                  ++colReturn;
                  break;
               default:
                  throw zxJDBC.makeException(zxJDBC.DataError, "unknown column type [" + colType + "]");
            }
         }
      }

      StringBuffer sql = new StringBuffer("{");
      if (colReturn > 0) {
         PyList list;
         for(list = new PyList(); colReturn > 0; --colReturn) {
            list.append(Py.newString("?"));
         }

         sql.append(Py.newString(",").join(list)).append(" = ");
      }

      String name = this.getProcedureName();
      sql.append("call ").append(name).append("(");
      if (colParam > 0) {
         PyList list;
         for(list = new PyList(); colParam > 0; --colParam) {
            list.append(Py.newString("?"));
         }

         sql.append(Py.newString(",").join(list));
      }

      return sql.append(")}").toString();
   }

   protected void registerOutParameters(CallableStatement statement) throws SQLException {
      if (this.columns != Py.None) {
         int i = 0;
         int len = this.columns.__len__();

         while(i < len) {
            PyObject column = this.columns.__getitem__(i);
            int colType = column.__getitem__(4).asInt();
            int dataType = column.__getitem__(5).asInt();
            String dataTypeName = column.__getitem__(6).toString();
            switch (colType) {
               case 2:
               case 4:
               case 5:
                  this.cursor.datahandler.registerOut(statement, i + 1, colType, dataType, dataTypeName);
               case 3:
               default:
                  ++i;
            }
         }

      }
   }

   protected void fetchColumns() throws SQLException {
      PyExtendedCursor pec = (PyExtendedCursor)this.cursor.connection.cursor();

      try {
         pec.datahandler = this.cursor.datahandler;
         pec.procedurecolumns(this.procedureCatalog, this.procedureSchema, this.procedureName, Py.None);
         this.columns = pec.fetchall();
      } finally {
         pec.close();
      }

   }

   protected PyObject getDefault() {
      return Py.EmptyString;
   }

   protected String getProcedureName() {
      StringBuffer proc = new StringBuffer();
      if (this.procedureCatalog.__nonzero__()) {
         proc.append(this.procedureCatalog.toString()).append(".");
      }

      return proc.append(this.procedureName.toString()).toString();
   }
}
