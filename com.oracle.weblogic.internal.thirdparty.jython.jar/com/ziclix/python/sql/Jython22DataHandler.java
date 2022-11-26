package com.ziclix.python.sql;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import org.python.core.Py;
import org.python.core.PyFile;
import org.python.core.PyList;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.util.StringUtil;

public class Jython22DataHandler extends DataHandler {
   public String getMetaDataName(PyObject name) {
      return name == Py.None ? null : name.__str__().toString();
   }

   public Procedure getProcedure(PyCursor cursor, PyObject name) throws SQLException {
      return new Procedure(cursor, name);
   }

   public PyObject getRowId(Statement stmt) throws SQLException {
      return Py.None;
   }

   public void preExecute(Statement stmt) throws SQLException {
   }

   public void postExecute(Statement stmt) throws SQLException {
   }

   public void setJDBCObject(PreparedStatement stmt, int index, PyObject object) throws SQLException {
      try {
         stmt.setObject(index, object.__tojava__(Object.class));
      } catch (Exception var7) {
         SQLException cause = null;
         SQLException ex = new SQLException("error setting index [" + index + "]");
         if (var7 instanceof SQLException) {
            cause = (SQLException)var7;
         } else {
            cause = new SQLException(var7.getMessage());
         }

         ex.setNextException(cause);
         throw ex;
      }
   }

   public void setJDBCObject(PreparedStatement stmt, int index, PyObject object, int type) throws SQLException {
      try {
         if (!checkNull(stmt, index, (PyObject)object, type)) {
            switch (type) {
               case -7:
                  stmt.setBoolean(index, ((PyObject)object).__nonzero__());
                  break;
               case -1:
                  if (object instanceof PyFile) {
                     object = ((PyFile)object).read();
                  }

                  String varchar = (String)((PyObject)object).__tojava__(String.class);
                  Reader reader = new BufferedReader(new StringReader(varchar));
                  stmt.setCharacterStream(index, reader, varchar.length());
                  break;
               case 91:
                  Date date = (Date)((PyObject)object).__tojava__(Date.class);
                  stmt.setDate(index, date);
                  break;
               case 92:
                  Time time = (Time)((PyObject)object).__tojava__(Time.class);
                  stmt.setTime(index, time);
                  break;
               case 93:
                  Timestamp timestamp = (Timestamp)((PyObject)object).__tojava__(Timestamp.class);
                  stmt.setTimestamp(index, timestamp);
                  break;
               default:
                  if (object instanceof PyFile) {
                     object = ((PyFile)object).read();
                  }

                  stmt.setObject(index, ((PyObject)object).__tojava__(Object.class), type);
            }

         }
      } catch (Exception var10) {
         SQLException cause = null;
         SQLException ex = new SQLException("error setting index [" + index + "], type [" + type + "]");
         if (var10 instanceof SQLException) {
            cause = (SQLException)var10;
         } else {
            cause = new SQLException(var10.getMessage());
         }

         ex.setNextException(cause);
         throw ex;
      }
   }

   public PyObject getPyObject(ResultSet set, int col, int type) throws SQLException {
      PyObject obj = Py.None;
      switch (type) {
         case -7:
            obj = set.getBoolean(col) ? Py.One : Py.Zero;
            break;
         case -6:
         case 4:
         case 5:
            obj = Py.newInteger(set.getInt(col));
            break;
         case -5:
            obj = new PyLong(set.getLong(col));
            break;
         case -4:
         case -3:
         case -2:
            obj = Py.java2py(set.getBytes(col));
            break;
         case -1:
            InputStream longvarchar = set.getAsciiStream(col);
            if (longvarchar == null) {
               obj = Py.None;
            } else {
               try {
                  longvarchar = new BufferedInputStream((InputStream)longvarchar);
                  byte[] bytes = read((InputStream)longvarchar);
                  if (bytes != null) {
                     obj = Py.newString(StringUtil.fromBytes(bytes));
                  }
               } finally {
                  try {
                     ((InputStream)longvarchar).close();
                  } catch (Throwable var14) {
                  }

               }
            }
            break;
         case 0:
            obj = Py.None;
            break;
         case 1:
         case 12:
            String string = set.getString(col);
            obj = string == null ? Py.None : Py.newString(string);
            break;
         case 2:
         case 3:
            BigDecimal bd = null;

            try {
               bd = set.getBigDecimal(col, set.getMetaData().getPrecision(col));
            } catch (Throwable var15) {
               bd = set.getBigDecimal(col, 10);
            }

            obj = bd == null ? Py.None : Py.newFloat(bd.doubleValue());
            break;
         case 6:
         case 7:
            obj = Py.newFloat(set.getFloat(col));
            break;
         case 8:
            obj = Py.newFloat(set.getDouble(col));
            break;
         case 91:
            obj = Py.java2py(set.getDate(col));
            break;
         case 92:
            obj = Py.java2py(set.getTime(col));
            break;
         case 93:
            obj = Py.java2py(set.getTimestamp(col));
            break;
         case 1111:
            obj = Py.java2py(set.getObject(col));
            break;
         default:
            throw this.createUnsupportedTypeSQLException(new Integer(type), col);
      }

      return (PyObject)(!set.wasNull() && obj != null ? obj : Py.None);
   }

   public PyObject getPyObject(CallableStatement stmt, int col, int type) throws SQLException {
      PyObject obj = Py.None;
      Object obj;
      switch (type) {
         case -7:
            obj = stmt.getBoolean(col) ? Py.One : Py.Zero;
            break;
         case -6:
         case 4:
         case 5:
            obj = Py.newInteger(stmt.getInt(col));
            break;
         case -5:
            obj = new PyLong(stmt.getLong(col));
            break;
         case -4:
         case -3:
         case -2:
            obj = Py.java2py(stmt.getBytes(col));
            break;
         case -1:
         case 1:
         case 12:
            String string = stmt.getString(col);
            obj = string == null ? Py.None : Py.newString(string);
            break;
         case 0:
            obj = Py.None;
            break;
         case 2:
         case 3:
            BigDecimal bd = stmt.getBigDecimal(col, 10);
            obj = bd == null ? Py.None : Py.newFloat(bd.doubleValue());
            break;
         case 6:
         case 7:
            obj = Py.newFloat(stmt.getFloat(col));
            break;
         case 8:
            obj = Py.newFloat(stmt.getDouble(col));
            break;
         case 91:
            obj = Py.java2py(stmt.getDate(col));
            break;
         case 92:
            obj = Py.java2py(stmt.getTime(col));
            break;
         case 93:
            obj = Py.java2py(stmt.getTimestamp(col));
            break;
         case 1111:
            obj = Py.java2py(stmt.getObject(col));
            break;
         default:
            throw this.createUnsupportedTypeSQLException(new Integer(type), col);
      }

      return (PyObject)(!stmt.wasNull() && obj != null ? obj : Py.None);
   }

   public void registerOut(CallableStatement statement, int index, int colType, int dataType, String dataTypeName) throws SQLException {
      try {
         statement.registerOutParameter(index, dataType);
      } catch (Throwable var9) {
         SQLException cause = null;
         SQLException ex = new SQLException("error setting index [" + index + "], coltype [" + colType + "], datatype [" + dataType + "], datatypename [" + dataTypeName + "]");
         if (var9 instanceof SQLException) {
            cause = (SQLException)var9;
         } else {
            cause = new SQLException(var9.getMessage());
         }

         ex.setNextException(cause);
         throw ex;
      }
   }

   public PyObject __chain__() {
      return new PyList(Py.javas2pys(this));
   }
}
