package com.ziclix.python.sql;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import org.python.core.ClassDictInit;
import org.python.core.Options;
import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.Untraversable;

@Untraversable
public class zxJDBC extends PyObject implements ClassDictInit {
   public static PyObject Error;
   public static PyObject Warning;
   public static PyObject InterfaceError;
   public static PyObject DatabaseError;
   public static PyObject InternalError;
   public static PyObject OperationalError;
   public static PyObject ProgrammingError;
   public static PyObject IntegrityError;
   public static PyObject DataError;
   public static PyObject NotSupportedError;
   private static ResourceBundle resourceBundle;
   public static DateFactory datefactory;

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"apilevel", new PyString("2.0"));
      dict.__setitem__((String)"threadsafety", new PyInteger(1));
      dict.__setitem__((String)"paramstyle", new PyString("qmark"));
      dict.__setitem__((String)"Date", new zxJDBCFunc("Date", 1, 3, 3, "construct a Date from year, month, day"));
      dict.__setitem__((String)"Time", new zxJDBCFunc("Time", 2, 3, 3, "construct a Date from hour, minute, second"));
      dict.__setitem__((String)"Timestamp", new zxJDBCFunc("Timestamp", 3, 6, 6, "construct a Timestamp from year, month, day, hour, minute, second"));
      dict.__setitem__((String)"DateFromTicks", new zxJDBCFunc("DateFromTicks", 4, 1, 1, "construct a Date from seconds since the epoch"));
      dict.__setitem__((String)"TimeFromTicks", new zxJDBCFunc("TimeFromTicks", 5, 1, 1, "construct a Time from seconds since the epoch"));
      dict.__setitem__((String)"TimestampFromTicks", new zxJDBCFunc("TimestampFromTicks", 6, 1, 1, "construct a Timestamp from seconds since the epoch"));
      dict.__setitem__((String)"Binary", new zxJDBCFunc("Binary", 7, 1, 1, "construct an object capable of holding binary data"));
      _addSqlTypes(dict);
      _addConnectors(dict);
      _buildExceptions(dict);
      dict.__setitem__((String)"initModule", (PyObject)null);
      dict.__setitem__((String)"toString", (PyObject)null);
      dict.__setitem__((String)"getPyClass", (PyObject)null);
      dict.__setitem__((String)"classDictInit", (PyObject)null);
      dict.__setitem__((String)"_addSqlTypes", (PyObject)null);
      dict.__setitem__((String)"_addConnectors", (PyObject)null);
      dict.__setitem__((String)"_buildExceptions", (PyObject)null);
      dict.__setitem__((String)"buildClass", (PyObject)null);
      dict.__setitem__((String)"createExceptionMessage", (PyObject)null);
      dict.__setitem__((String)"resourceBundle", (PyObject)null);
      dict.__setitem__((String)"getString", (PyObject)null);
      dict.__setitem__((String)"makeException", (PyObject)null);
   }

   protected static void _addSqlTypes(PyObject dict) throws PyException {
      PyDictionary sqltype = new PyDictionary();
      dict.__setitem__((String)"sqltype", sqltype);

      try {
         Class c = Class.forName("java.sql.Types");
         Field[] fields = c.getFields();
         Field[] var4 = fields;
         int var5 = fields.length;

         int var6;
         Field f;
         PyString name;
         for(var6 = 0; var6 < var5; ++var6) {
            f = var4[var6];
            name = Py.newString(f.getName());
            PyObject value = new DBApiType(f.getInt(c));
            dict.__setitem__((PyObject)name, value);
            sqltype.__setitem__(value, name);
         }

         c = Class.forName("java.sql.ResultSet");
         fields = c.getFields();
         var4 = fields;
         var5 = fields.length;

         for(var6 = 0; var6 < var5; ++var6) {
            f = var4[var6];
            name = Py.newString(f.getName());
            PyObject value = Py.newInteger(f.getInt(c));
            dict.__setitem__((PyObject)name, value);
         }
      } catch (Throwable var10) {
         throw makeException(var10);
      }

      dict.__setitem__("ROWID", dict.__getitem__(Py.newString("OTHER")));
      dict.__setitem__("NUMBER", dict.__getitem__(Py.newString("NUMERIC")));
      dict.__setitem__("STRING", dict.__getitem__(Py.newString("VARCHAR")));
      dict.__setitem__("DATETIME", dict.__getitem__(Py.newString("TIMESTAMP")));
   }

   protected static void _addConnectors(PyObject dict) throws PyException {
      PyObject connector = Py.None;
      Properties props = new Properties();
      props.put("connect", "com.ziclix.python.sql.connect.Connect");
      props.put("lookup", "com.ziclix.python.sql.connect.Lookup");
      props.put("connectx", "com.ziclix.python.sql.connect.Connectx");
      Enumeration names = props.propertyNames();

      while(names.hasMoreElements()) {
         String name = ((String)names.nextElement()).trim();
         String className = props.getProperty(name).trim();

         try {
            connector = (PyObject)Class.forName(className).newInstance();
            dict.__setitem__(name, connector);
            Py.writeComment("zxJDBC", "loaded connector [" + className + "] as [" + name + "]");
         } catch (Throwable var7) {
            Py.writeComment("zxJDBC", "failed to load connector [" + name + "] using class [" + className + "]");
         }
      }

   }

   protected static void _buildExceptions(PyObject dict) {
      Error = buildClass("Error", Py.StandardError);
      Warning = buildClass("Warning", Py.StandardError);
      InterfaceError = buildClass("InterfaceError", Error);
      DatabaseError = buildClass("DatabaseError", Error);
      InternalError = buildClass("InternalError", DatabaseError);
      OperationalError = buildClass("OperationalError", DatabaseError);
      ProgrammingError = buildClass("ProgrammingError", DatabaseError);
      IntegrityError = buildClass("IntegrityError", DatabaseError);
      DataError = buildClass("DataError", DatabaseError);
      NotSupportedError = buildClass("NotSupportedError", DatabaseError);
   }

   public static String getString(String key) {
      int i = 0;
      List lines = null;
      String resource = null;

      while(true) {
         try {
            resource = resourceBundle.getString(key + "." + i++);
            if (lines == null) {
               lines = new ArrayList();
            }

            lines.add(resource);
         } catch (MissingResourceException var7) {
            if (lines != null && lines.size() != 0) {
               String sep = System.getProperty("line.separator");
               StringBuffer sb = new StringBuffer();

               for(i = 0; i < lines.size() - 1; ++i) {
                  sb.append((String)lines.get(i)).append(sep);
               }

               sb.append((String)lines.get(lines.size() - 1));
               resource = sb.toString();
            } else {
               try {
                  resource = resourceBundle.getString(key);
               } catch (MissingResourceException var6) {
                  return key;
               }
            }

            return resource;
         }
      }
   }

   public static String getString(String key, Object[] values) {
      String format = getString(key);
      return MessageFormat.format(format, values);
   }

   public static PyException makeException(String msg) {
      return makeException(Error, msg);
   }

   public static PyException makeException(PyObject type, String msg) {
      return Py.makeException(type, msg == null ? Py.EmptyString : Py.newString(msg));
   }

   public static PyException makeException(Throwable throwable) {
      PyObject type = Error;
      if (throwable instanceof SQLException) {
         String state = ((SQLException)throwable).getSQLState();
         if (state != null && state.length() == 5) {
            if (state.startsWith("23")) {
               type = IntegrityError;
            } else if (state.equals("40002")) {
               type = IntegrityError;
            }
         }
      }

      return makeException(type, throwable);
   }

   public static PyException makeException(PyObject type, Throwable t) {
      return makeException(type, t, -1);
   }

   public static PyException makeException(PyObject type, Throwable t, int rowIndex) {
      if (Options.showJavaExceptions) {
         CharArrayWriter buf = new CharArrayWriter();
         PrintWriter writer = new PrintWriter(buf);
         writer.println("Java Traceback:");
         if (t instanceof PyException) {
            ((PyException)t).super__printStackTrace(writer);
         } else {
            t.printStackTrace(writer);
         }

         Py.stderr.print(buf.toString());
      }

      if (t instanceof PyException) {
         return (PyException)t;
      } else if (t instanceof SQLException) {
         SQLException sqlException = (SQLException)t;
         StringBuffer buffer = new StringBuffer();

         do {
            buffer.append(sqlException.getMessage());
            buffer.append(" [SQLCode: " + sqlException.getErrorCode() + "]");
            if (sqlException.getSQLState() != null) {
               buffer.append(", [SQLState: " + sqlException.getSQLState() + "]");
            }

            if (rowIndex >= 0) {
               buffer.append(", [Row number: " + rowIndex + "]");
            }

            sqlException = sqlException.getNextException();
            if (sqlException != null) {
               buffer.append(System.getProperty("line.separator"));
            }
         } while(sqlException != null);

         return makeException(type, buffer.toString());
      } else {
         return makeException(type, t.getMessage());
      }
   }

   protected static PyObject buildClass(String classname, PyObject superclass) {
      PyObject dict = new PyStringMap();
      dict.__setitem__((String)"__doc__", Py.newString(getString(classname)));
      dict.__setitem__((String)"__module__", Py.newString("zxJDBC"));
      return Py.makeClass(classname, (PyObject)superclass, (PyObject)dict);
   }

   static {
      Error = Py.None;
      Warning = Py.None;
      InterfaceError = Py.None;
      DatabaseError = Py.None;
      InternalError = Py.None;
      OperationalError = Py.None;
      ProgrammingError = Py.None;
      IntegrityError = Py.None;
      DataError = Py.None;
      NotSupportedError = Py.None;
      resourceBundle = null;
      datefactory = new JavaDateFactory();

      try {
         resourceBundle = ResourceBundle.getBundle("com.ziclix.python.sql.resource.zxJDBCMessages");
      } catch (MissingResourceException var1) {
         throw new RuntimeException("missing zxjdbc resource bundle");
      }
   }
}
