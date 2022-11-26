package com.ziclix.python.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;
import org.python.core.ClassDictInit;
import org.python.core.ContextManager;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;

public class PyConnection extends PyObject implements ClassDictInit, ContextManager, Traverseproc {
   protected boolean closed = false;
   protected boolean supportsTransactions;
   protected boolean supportsMultipleResultSets;
   protected Connection connection;
   private Set cursors = Collections.newSetFromMap(new WeakHashMap());
   private Set statements;
   protected static PyList __members__;
   protected static PyList __methods__;

   public PyConnection(Connection connection) throws SQLException {
      this.cursors = Collections.synchronizedSet(this.cursors);
      this.connection = connection;
      this.statements = Collections.newSetFromMap(new WeakHashMap());
      this.statements = Collections.synchronizedSet(this.statements);
      this.supportsTransactions = this.connection.getMetaData().supportsTransactions();
      this.supportsMultipleResultSets = this.connection.getMetaData().supportsMultipleResultSets();
      if (this.supportsTransactions) {
         this.connection.setAutoCommit(false);
      }

   }

   public String toString() {
      try {
         return String.format("<PyConnection object at %s user='%s', url='%s'>", Py.idstr(this), this.connection.getMetaData().getUserName(), this.connection.getMetaData().getURL());
      } catch (SQLException var2) {
         return String.format("<PyConnection object at %s", Py.idstr(this));
      }
   }

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"autocommit", new PyInteger(0));
      dict.__setitem__((String)"close", new ConnectionFunc("close", 0, 0, 0, zxJDBC.getString("close")));
      dict.__setitem__((String)"commit", new ConnectionFunc("commit", 1, 0, 0, zxJDBC.getString("commit")));
      dict.__setitem__((String)"cursor", new ConnectionFunc("cursor", 2, 0, 4, zxJDBC.getString("cursor")));
      dict.__setitem__((String)"rollback", new ConnectionFunc("rollback", 3, 0, 0, zxJDBC.getString("rollback")));
      dict.__setitem__((String)"nativesql", new ConnectionFunc("nativesql", 4, 1, 1, zxJDBC.getString("nativesql")));
      dict.__setitem__((String)"__enter__", new ConnectionFunc("__enter__", 5, 0, 0, "__enter__"));
      dict.__setitem__((String)"__exit__", new ConnectionFunc("__exit__", 6, 3, 3, "__exit__"));
      dict.__setitem__((String)"initModule", (PyObject)null);
      dict.__setitem__((String)"toString", (PyObject)null);
      dict.__setitem__((String)"setConnection", (PyObject)null);
      dict.__setitem__((String)"getPyClass", (PyObject)null);
      dict.__setitem__((String)"connection", (PyObject)null);
      dict.__setitem__((String)"classDictInit", (PyObject)null);
      dict.__setitem__((String)"cursors", (PyObject)null);
   }

   public void __setattr__(String name, PyObject value) {
      if ("autocommit".equals(name)) {
         try {
            if (this.supportsTransactions) {
               this.connection.setAutoCommit(value.__nonzero__());
            }

         } catch (SQLException var4) {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var4);
         }
      } else {
         super.__setattr__(name, value);
      }
   }

   public PyObject __findattr_ex__(String name) {
      if ("autocommit".equals(name)) {
         try {
            return this.connection.getAutoCommit() ? Py.One : Py.Zero;
         } catch (SQLException var3) {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var3);
         }
      } else if ("dbname".equals(name)) {
         try {
            return Py.newString(this.connection.getMetaData().getDatabaseProductName());
         } catch (SQLException var4) {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var4);
         }
      } else if ("dbversion".equals(name)) {
         try {
            return Py.newString(this.connection.getMetaData().getDatabaseProductVersion());
         } catch (SQLException var5) {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var5);
         }
      } else if ("drivername".equals(name)) {
         try {
            return Py.newString(this.connection.getMetaData().getDriverName());
         } catch (SQLException var6) {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var6);
         }
      } else if ("driverversion".equals(name)) {
         try {
            return Py.newString(this.connection.getMetaData().getDriverVersion());
         } catch (SQLException var7) {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var7);
         }
      } else if ("url".equals(name)) {
         try {
            return Py.newString(this.connection.getMetaData().getURL());
         } catch (SQLException var8) {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var8);
         }
      } else if ("__connection__".equals(name)) {
         return Py.java2py(this.connection);
      } else if ("__cursors__".equals(name)) {
         return Py.java2py(Collections.unmodifiableSet(this.cursors));
      } else if ("__statements__".equals(name)) {
         return Py.java2py(Collections.unmodifiableSet(this.statements));
      } else if ("__methods__".equals(name)) {
         return __methods__;
      } else if ("__members__".equals(name)) {
         return __members__;
      } else {
         return (PyObject)("closed".equals(name) ? Py.newBoolean(this.closed) : super.__findattr_ex__(name));
      }
   }

   public void close() {
      if (this.closed) {
         throw zxJDBC.makeException(zxJDBC.ProgrammingError, "connection is closed");
      } else {
         this.closed = true;
         Iterator var2;
         synchronized(this.cursors) {
            var2 = this.cursors.iterator();

            while(true) {
               if (!var2.hasNext()) {
                  this.cursors.clear();
                  break;
               }

               PyCursor cursor = (PyCursor)var2.next();
               cursor.close();
            }
         }

         synchronized(this.statements) {
            var2 = this.statements.iterator();

            while(true) {
               if (!var2.hasNext()) {
                  this.statements.clear();
                  break;
               }

               PyStatement statement = (PyStatement)var2.next();
               statement.close();
            }
         }

         try {
            this.connection.close();
         } catch (SQLException var6) {
            throw zxJDBC.makeException((Throwable)var6);
         }
      }
   }

   public void commit() {
      if (this.closed) {
         throw zxJDBC.makeException(zxJDBC.ProgrammingError, "connection is closed");
      } else if (this.supportsTransactions) {
         try {
            this.connection.commit();
         } catch (SQLException var2) {
            throw zxJDBC.makeException((Throwable)var2);
         }
      }
   }

   public void rollback() {
      if (this.closed) {
         throw zxJDBC.makeException(zxJDBC.ProgrammingError, "connection is closed");
      } else if (this.supportsTransactions) {
         try {
            this.connection.rollback();
         } catch (SQLException var2) {
            throw zxJDBC.makeException((Throwable)var2);
         }
      }
   }

   public PyObject nativesql(PyObject nativeSQL) {
      if (this.closed) {
         throw zxJDBC.makeException(zxJDBC.ProgrammingError, "connection is closed");
      } else if (nativeSQL == Py.None) {
         return Py.None;
      } else {
         try {
            return (PyObject)(nativeSQL instanceof PyUnicode ? Py.newUnicode(this.connection.nativeSQL(nativeSQL.toString())) : Py.newString(this.connection.nativeSQL(nativeSQL.__str__().toString())));
         } catch (SQLException var3) {
            throw zxJDBC.makeException((Throwable)var3);
         }
      }
   }

   public PyCursor cursor() {
      return this.cursor(false);
   }

   public PyCursor cursor(boolean dynamicFetch) {
      return this.cursor(dynamicFetch, Py.None, Py.None);
   }

   public PyCursor cursor(boolean dynamicFetch, PyObject rsType, PyObject rsConcur) {
      if (this.closed) {
         throw zxJDBC.makeException(zxJDBC.ProgrammingError, "connection is closed");
      } else {
         PyCursor cursor = new PyExtendedCursor(this, dynamicFetch, rsType, rsConcur);
         this.cursors.add(cursor);
         return cursor;
      }
   }

   void remove(PyCursor cursor) {
      if (!this.closed) {
         this.cursors.remove(cursor);
      }
   }

   void add(PyStatement statement) {
      if (!this.closed) {
         this.statements.add(statement);
      }
   }

   boolean contains(PyStatement statement) {
      return this.closed ? false : this.statements.contains(statement);
   }

   public PyObject __enter__(ThreadState ts) {
      return this;
   }

   public PyObject __enter__() {
      return this;
   }

   public boolean __exit__(ThreadState ts, PyException exception) {
      if (exception == null) {
         this.commit();
      } else {
         this.rollback();
      }

      return false;
   }

   public boolean __exit__(PyObject type, PyObject value, PyObject traceback) {
      if (type != null && type != Py.None) {
         this.rollback();
      } else {
         this.commit();
      }

      return false;
   }

   public int traverse(Visitproc visit, Object arg) {
      Iterator var3 = this.cursors.iterator();

      PyObject ob;
      int retVal;
      while(var3.hasNext()) {
         ob = (PyObject)var3.next();
         if (ob != null) {
            retVal = visit.visit(ob, arg);
            if (retVal != 0) {
               return retVal;
            }
         }
      }

      var3 = this.statements.iterator();

      while(var3.hasNext()) {
         ob = (PyObject)var3.next();
         if (ob != null) {
            retVal = visit.visit(ob, arg);
            if (retVal != 0) {
               return retVal;
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (ob == null) {
         return false;
      } else if (this.cursors != null && this.cursors.contains(ob)) {
         return true;
      } else {
         return this.statements != null && this.statements.contains(ob);
      }
   }

   static {
      PyObject[] m = new PyObject[]{new PyString("close"), new PyString("commit"), new PyString("cursor"), new PyString("rollback"), new PyString("nativesql")};
      __methods__ = new PyList(m);
      m = new PyObject[]{new PyString("autocommit"), new PyString("dbname"), new PyString("dbversion"), new PyString("drivername"), new PyString("driverversion"), new PyString("url"), new PyString("__connection__"), new PyString("__cursors__"), new PyString("__statements__"), new PyString("closed")};
      __members__ = new PyList(m);
   }
}
