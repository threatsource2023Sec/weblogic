package com.ziclix.python.sql;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
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
import org.python.core.PyObject;

public class DataHandler {
   private static final int INITIAL_SIZE = 4096;
   private static final String[] SYSTEM_DATAHANDLERS = new String[]{"com.ziclix.python.sql.JDBC20DataHandler"};

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
         Object o = object.__tojava__(Object.class);
         if (o instanceof BigInteger) {
            stmt.setObject(index, o, -5);
         } else {
            stmt.setObject(index, o);
         }

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
      Object obj;
      switch (type) {
         case -16:
         case -1:
            Reader reader = set.getCharacterStream(col);
            obj = reader == null ? Py.None : Py.newUnicode(read(reader));
            break;
         case -15:
         case -9:
         case 1:
         case 12:
            String string = set.getString(col);
            obj = string == null ? Py.None : Py.newUnicode(string);
            break;
         case -8:
            throw this.createUnsupportedTypeSQLException("STRUCT", col);
         case -7:
         case 16:
            obj = set.getBoolean(col) ? Py.True : Py.False;
            break;
         case -6:
         case 4:
         case 5:
            obj = Py.newInteger(set.getInt(col));
            break;
         case -5:
            obj = Py.newLong(set.getLong(col));
            break;
         case -4:
         case -3:
         case -2:
            obj = Py.java2py(set.getBytes(col));
            break;
         case 0:
            obj = Py.None;
            break;
         case 2:
         case 3:
            BigDecimal bd = set.getBigDecimal(col);
            obj = bd == null ? Py.None : Py.newFloat(bd.doubleValue());
            break;
         case 6:
         case 7:
            obj = Py.newFloat(set.getFloat(col));
            break;
         case 8:
            obj = Py.newFloat(set.getDouble(col));
            break;
         case 70:
            throw this.createUnsupportedTypeSQLException("DATALINK", col);
         case 91:
            Object date = set.getObject(col);
            obj = date instanceof Date ? Py.newDate((Date)date) : Py.java2py(date);
            break;
         case 92:
            obj = Py.newTime(set.getTime(col));
            break;
         case 93:
            obj = Py.newDatetime(set.getTimestamp(col));
            break;
         case 1111:
         case 2000:
            obj = Py.java2py(set.getObject(col));
            break;
         case 2001:
            throw this.createUnsupportedTypeSQLException("DISTINCT", col);
         case 2002:
            throw this.createUnsupportedTypeSQLException("STRUCT", col);
         case 2003:
            throw this.createUnsupportedTypeSQLException("ARRAY", col);
         case 2004:
            Blob blob = set.getBlob(col);
            obj = blob == null ? Py.None : Py.java2py(read(blob.getBinaryStream()));
            break;
         case 2005:
         case 2009:
         case 2011:
            Clob clob = set.getClob(col);
            obj = clob == null ? Py.None : Py.java2py(read(clob.getCharacterStream()));
            break;
         case 2006:
            throw this.createUnsupportedTypeSQLException("REF", col);
         default:
            throw this.createUnsupportedTypeSQLException(new Integer(type), col);
      }

      return (PyObject)(!set.wasNull() && obj != null ? obj : Py.None);
   }

   protected final SQLException createUnsupportedTypeSQLException(Object type, int col) {
      Object[] vals = new Object[]{type, new Integer(col)};
      String msg = zxJDBC.getString("unsupportedTypeForColumn", vals);
      return new SQLException(msg);
   }

   public PyObject getPyObject(CallableStatement stmt, int col, int type) throws SQLException {
      PyObject obj = Py.None;
      switch (type) {
         case -7:
            obj = stmt.getBoolean(col) ? Py.True : Py.False;
            break;
         case -6:
         case 4:
         case 5:
            obj = Py.newInteger(stmt.getInt(col));
            break;
         case -5:
            obj = Py.newLong(stmt.getLong(col));
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
            obj = string == null ? Py.None : Py.newUnicode(string);
            break;
         case 0:
            obj = Py.None;
            break;
         case 2:
         case 3:
            BigDecimal bd = stmt.getBigDecimal(col);
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
            obj = Py.newDate(stmt.getDate(col));
            break;
         case 92:
            obj = Py.newTime(stmt.getTime(col));
            break;
         case 93:
            obj = Py.newDatetime(stmt.getTimestamp(col));
            break;
         case 1111:
            obj = Py.java2py(stmt.getObject(col));
            break;
         default:
            this.createUnsupportedTypeSQLException(type, col);
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

   public static final boolean checkNull(PreparedStatement stmt, int index, PyObject object, int type) throws SQLException {
      if (object != null && Py.None != object) {
         return false;
      } else {
         stmt.setNull(index, type);
         return true;
      }
   }

   public static final byte[] read(InputStream stream) {
      int size = false;
      byte[] buffer = new byte[4096];
      ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);

      try {
         int size;
         try {
            while((size = stream.read(buffer)) != -1) {
               baos.write(buffer, 0, size);
            }
         } catch (IOException var12) {
            throw zxJDBC.makeException((Throwable)var12);
         }
      } finally {
         try {
            stream.close();
         } catch (IOException var11) {
            throw zxJDBC.makeException((Throwable)var11);
         }
      }

      return baos.toByteArray();
   }

   public static String read(Reader reader) {
      int size = false;
      char[] buffer = new char[4096];
      StringBuilder builder = new StringBuilder(4096);

      try {
         int size;
         try {
            while((size = reader.read(buffer)) != -1) {
               builder.append(buffer, 0, size);
            }
         } catch (IOException var12) {
            throw zxJDBC.makeException((Throwable)var12);
         }
      } finally {
         try {
            reader.close();
         } catch (IOException var11) {
            throw zxJDBC.makeException((Throwable)var11);
         }
      }

      return builder.toString();
   }

   public static final DataHandler getSystemDataHandler() {
      DataHandler dh = new DataHandler();
      String[] var1 = SYSTEM_DATAHANDLERS;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String element = var1[var3];

         try {
            Class c = Class.forName(element);
            Constructor cons = c.getConstructor(DataHandler.class);
            dh = (DataHandler)cons.newInstance(dh);
         } catch (Throwable var7) {
         }
      }

      return dh;
   }

   public PyObject __chain__() {
      return new PyList(Py.javas2pys(this));
   }

   public String toString() {
      return this.getClass().getName();
   }
}
