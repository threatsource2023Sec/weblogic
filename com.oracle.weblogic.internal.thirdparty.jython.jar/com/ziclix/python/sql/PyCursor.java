package com.ziclix.python.sql;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.List;
import org.python.core.ClassDictInit;
import org.python.core.ContextManager;
import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;

public class PyCursor extends PyObject implements ClassDictInit, WarningListener, ContextManager, Traverseproc {
   protected Fetch fetch;
   private boolean closed;
   protected int arraysize;
   protected int softspace;
   protected PyObject rsType;
   protected PyObject rsConcur;
   protected PyObject warnings;
   protected PyObject lastrowid;
   protected PyObject updatecount;
   protected boolean dynamicFetch;
   protected PyConnection connection;
   protected DataHandler datahandler;
   protected PyStatement statement;
   private static final DataHandler DATAHANDLER = DataHandler.getSystemDataHandler();
   protected static PyList __methods__;
   protected static PyList __members__;

   PyCursor(PyConnection connection) {
      this(connection, false);
   }

   PyCursor(PyConnection connection, boolean dynamicFetch) {
      this.arraysize = 1;
      this.softspace = 0;
      this.closed = false;
      this.rsType = Py.None;
      this.rsConcur = Py.None;
      this.connection = connection;
      this.datahandler = DATAHANDLER;
      this.dynamicFetch = dynamicFetch;
      this.clear();
   }

   PyCursor(PyConnection connection, boolean dynamicFetch, PyObject rsType, PyObject rsConcur) {
      this(connection, dynamicFetch);
      this.rsType = rsType;
      this.rsConcur = rsConcur;
   }

   public String toString() {
      return String.format("<PyCursor object at %s>", Py.idstr(this));
   }

   public void __setattr__(String name, PyObject value) {
      if ("arraysize".equals(name)) {
         this.arraysize = value.asInt();
      } else if ("softspace".equals(name)) {
         this.softspace = value.asInt();
      } else if ("datahandler".equals(name)) {
         this.datahandler = (DataHandler)value.__tojava__(DataHandler.class);
      } else {
         super.__setattr__(name, value);
      }

   }

   public PyObject __findattr_ex__(String name) {
      if ("arraysize".equals(name)) {
         return Py.newInteger(this.arraysize);
      } else if ("softspace".equals(name)) {
         return Py.newInteger(this.softspace);
      } else if ("__methods__".equals(name)) {
         return __methods__;
      } else if ("__members__".equals(name)) {
         return __members__;
      } else if ("description".equals(name)) {
         return this.fetch.description;
      } else if ("rowcount".equals(name)) {
         return Py.newInteger(this.fetch.rowcount);
      } else if ("rownumber".equals(name)) {
         int rn = this.fetch.rownumber;
         return (PyObject)(rn < 0 ? Py.None : Py.newInteger(rn));
      } else if ("warnings".equals(name)) {
         return this.warnings;
      } else if ("lastrowid".equals(name)) {
         return this.lastrowid;
      } else if ("updatecount".equals(name)) {
         return this.updatecount;
      } else if ("datahandler".equals(name)) {
         return Py.java2py(this.datahandler);
      } else if ("dynamic".equals(name)) {
         return this.dynamicFetch ? Py.One : Py.Zero;
      } else if ("connection".equals(name)) {
         return this.connection;
      } else if ("closed".equals(name)) {
         return Py.newBoolean(this.closed);
      } else {
         if ("callproc".equals(name)) {
            try {
               if (!this.getMetaData().supportsStoredProcedures()) {
                  return null;
               }
            } catch (Throwable var3) {
            }
         }

         return super.__findattr_ex__(name);
      }
   }

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"fetchmany", new CursorFunc("fetchmany", 0, 0, 1, "fetch specified number of rows"));
      dict.__setitem__((String)"close", new CursorFunc("close", 1, 0, "close the cursor"));
      dict.__setitem__((String)"fetchall", new CursorFunc("fetchall", 2, 0, "fetch all results"));
      dict.__setitem__((String)"fetchone", new CursorFunc("fetchone", 3, 0, "fetch the next result"));
      dict.__setitem__((String)"nextset", new CursorFunc("nextset", 4, 0, "return next set or None"));
      dict.__setitem__((String)"execute", new CursorFunc("execute", 5, 1, 4, "execute the sql expression"));
      dict.__setitem__((String)"setinputsizes", new CursorFunc("setinputsizes", 6, 1, "not implemented"));
      dict.__setitem__((String)"setoutputsize", new CursorFunc("setoutputsize", 7, 1, 2, "not implemented"));
      dict.__setitem__((String)"callproc", new CursorFunc("callproc", 8, 1, 4, "executes a stored procedure"));
      dict.__setitem__((String)"executemany", new CursorFunc("executemany", 9, 1, 3, "execute sql with the parameter list"));
      dict.__setitem__((String)"scroll", new CursorFunc("scroll", 10, 1, 2, "scroll the cursor in the result set to a new position according to mode"));
      dict.__setitem__((String)"write", new CursorFunc("write", 11, 1, "execute the sql written to this file-like object"));
      dict.__setitem__((String)"prepare", new CursorFunc("prepare", 12, 1, "prepare the sql statement for later execution"));
      dict.__setitem__((String)"__enter__", new CursorFunc("__enter__", 13, 0, 0, "__enter__"));
      dict.__setitem__((String)"__exit__", new CursorFunc("__exit__", 14, 3, 3, "__exit__"));
      dict.__setitem__((String)"classDictInit", (PyObject)null);
      dict.__setitem__((String)"toString", (PyObject)null);
      dict.__setitem__((String)"getDataHandler", (PyObject)null);
      dict.__setitem__((String)"warning", (PyObject)null);
      dict.__setitem__((String)"fetch", (PyObject)null);
      dict.__setitem__((String)"statement", (PyObject)null);
      dict.__setitem__((String)"dynamicFetch", (PyObject)null);
      dict.__setitem__((String)"getPyClass", (PyObject)null);
      dict.__setitem__((String)"rsConcur", (PyObject)null);
      dict.__setitem__((String)"rsType", (PyObject)null);
   }

   public void __del__() {
      this.close();
   }

   public void close() {
      try {
         this.clear();
         this.connection.remove(this);
      } finally {
         this.closed = true;
      }

   }

   public PyObject __iter__() {
      return this;
   }

   public PyObject next() {
      PyObject row = this.__iternext__();
      if (row == null) {
         throw Py.StopIteration("");
      } else {
         return row;
      }
   }

   public PyObject __iternext__() {
      PyObject row = this.fetchone();
      return row.__nonzero__() ? row : null;
   }

   protected DatabaseMetaData getMetaData() throws SQLException {
      return this.connection.connection.getMetaData();
   }

   public DataHandler getDataHandler() {
      return this.datahandler;
   }

   private PyStatement prepareStatement(PyObject sql, PyObject maxRows, boolean prepared) {
      PyStatement stmt = null;
      if (sql == Py.None) {
         return null;
      } else {
         try {
            if (sql instanceof PyStatement) {
               stmt = (PyStatement)sql;
            } else {
               Statement sqlStatement = null;
               String sqlString = sql instanceof PyUnicode ? sql.toString() : sql.__str__().toString();
               if (sqlString.trim().length() == 0) {
                  return null;
               }

               boolean normal = this.rsType == Py.None && this.rsConcur == Py.None;
               int style;
               if (normal) {
                  if (prepared) {
                     sqlStatement = this.connection.connection.prepareStatement(sqlString);
                  } else {
                     sqlStatement = this.connection.connection.createStatement();
                  }
               } else {
                  style = this.rsType.asInt();
                  int c = this.rsConcur.asInt();
                  if (prepared) {
                     sqlStatement = this.connection.connection.prepareStatement(sqlString, style, c);
                  } else {
                     sqlStatement = this.connection.connection.createStatement(style, c);
                  }
               }

               style = prepared ? 4 : 2;
               stmt = new PyStatement((Statement)sqlStatement, sqlString, style);
            }

            if (maxRows != Py.None) {
               stmt.statement.setMaxRows(maxRows.asInt());
            }

            return stmt;
         } catch (AbstractMethodError var10) {
            throw zxJDBC.makeException(zxJDBC.NotSupportedError, zxJDBC.getString("nodynamiccursors"));
         } catch (PyException var11) {
            throw var11;
         } catch (Throwable var12) {
            throw zxJDBC.makeException(var12);
         }
      }
   }

   public void callproc(PyObject name, PyObject params, PyObject bindings, PyObject maxRows) {
      this.clear();

      try {
         if (this.getMetaData().supportsStoredProcedures()) {
            if (isSeqSeq(params)) {
               throw zxJDBC.makeException(zxJDBC.NotSupportedError, "sequence of sequences is not supported");
            } else {
               Procedure procedure = this.datahandler.getProcedure(this, name);
               Statement stmt = procedure.prepareCall(this.rsType, this.rsConcur);
               if (maxRows != Py.None) {
                  stmt.setMaxRows(maxRows.asInt());
               }

               PyDictionary callableBindings = new PyDictionary();
               procedure.normalizeInput(params, callableBindings);
               if (bindings instanceof PyDictionary) {
                  callableBindings.update(bindings);
               }

               this.statement = new PyStatement(stmt, procedure);
               this.execute(params, callableBindings);
            }
         } else {
            throw zxJDBC.makeException(zxJDBC.NotSupportedError, zxJDBC.getString("noStoredProc"));
         }
      } catch (Throwable var8) {
         if (this.statement != null) {
            this.statement.close();
         }

         throw zxJDBC.makeException(var8);
      }
   }

   public void executemany(PyObject sql, PyObject params, PyObject bindings, PyObject maxRows) {
      if (!isSeq(params) || params.__len__() != 0) {
         this.execute(sql, params, bindings, maxRows);
      }
   }

   public void execute(PyObject sql, PyObject params, PyObject bindings, PyObject maxRows) {
      int rowIndex = -1;
      this.clear();
      boolean hasParams = hasParams(params);
      PyStatement stmt = this.prepareStatement(sql, maxRows, hasParams);
      if (stmt != null) {
         this.statement = stmt;

         try {
            synchronized(this.statement) {
               if (hasParams) {
                  if (isSeqSeq(params)) {
                     rowIndex = 0;
                     int i = 0;

                     for(int len = params.__len__(); i < len; ++i) {
                        PyObject param = params.__getitem__(i);
                        this.execute(param, bindings);
                        ++rowIndex;
                     }
                  } else {
                     this.execute(params, bindings);
                  }
               } else {
                  this.execute(Py.None, Py.None);
               }

            }
         } catch (Throwable var14) {
            if (this.statement != null && !(sql instanceof PyStatement)) {
               this.statement.close();
            }

            throw zxJDBC.makeException(zxJDBC.Error, var14, rowIndex);
         }
      }
   }

   protected void execute(PyObject params, PyObject bindings) {
      try {
         Statement stmt = this.statement.statement;
         this.datahandler.preExecute(stmt);
         this.statement.execute(this, params, bindings);
         this.updateAttributes(stmt.getUpdateCount());
         this.warning(new WarningEvent(this, stmt.getWarnings()));
         this.datahandler.postExecute(stmt);
      } catch (PyException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw zxJDBC.makeException(var5);
      }
   }

   private void updateAttributes(int updateCount) throws SQLException {
      this.lastrowid = this.datahandler.getRowId(this.statement.statement);
      this.updatecount = (PyObject)(updateCount < 0 ? Py.None : Py.newInteger(updateCount));
   }

   public PyObject fetchone() {
      this.ensureOpen();
      return this.fetch.fetchone();
   }

   public PyObject fetchall() {
      this.ensureOpen();
      return this.fetch.fetchall();
   }

   public PyObject fetchmany(int size) {
      this.ensureOpen();
      return this.fetch.fetchmany(size);
   }

   public PyObject nextset() {
      this.ensureOpen();
      PyObject nextset = this.fetch.nextset();
      if (!((PyObject)nextset).__nonzero__() && this.connection.supportsMultipleResultSets && !this.dynamicFetch) {
         Statement stmt = this.statement.statement;

         try {
            int updateCount = true;
            boolean hasMoreResults;
            int updateCount;
            if ((hasMoreResults = stmt.getMoreResults()) || (updateCount = stmt.getUpdateCount()) != -1) {
               this.updateAttributes(!hasMoreResults ? updateCount : stmt.getUpdateCount());
               this.fetch.add(stmt.getResultSet());
               nextset = Py.One;
            }
         } catch (SQLException var5) {
            throw zxJDBC.makeException((Throwable)var5);
         }
      }

      return (PyObject)nextset;
   }

   public PyStatement prepare(PyObject sql) {
      PyStatement s = this.prepareStatement(sql, Py.None, true);
      this.connection.add(s);
      return s;
   }

   public void scroll(int value, String mode) {
      this.ensureOpen();
      this.fetch.scroll(value, mode);
   }

   public void warning(WarningEvent event) {
      if (this.warnings == Py.None) {
         this.warnings = new PyList();
      }

      for(SQLWarning warning = event.getWarning(); warning != null; warning = warning.getNextWarning()) {
         PyObject[] warn = Py.javas2pys(warning.getMessage(), warning.getSQLState(), warning.getErrorCode());
         ((PyList)this.warnings).append(new PyTuple(warn));
      }

   }

   protected void clear() {
      this.ensureOpen();
      this.warnings = Py.None;
      this.lastrowid = Py.None;
      this.updatecount = Py.newInteger(-1);

      try {
         this.fetch.close();
      } catch (Throwable var11) {
      } finally {
         this.fetch = Fetch.newFetch(this.datahandler, this.dynamicFetch);
         this.fetch.addWarningListener(this);
      }

      if (this.statement != null) {
         try {
            if (!this.connection.contains(this.statement)) {
               this.statement.close();
            }
         } finally {
            this.statement = null;
         }
      }

   }

   public static boolean isSeq(PyObject object) {
      if (object != null && object != Py.None) {
         if (object.__tojava__(List.class) != Py.NoConversion) {
            return true;
         } else {
            return object instanceof PyList || object instanceof PyTuple;
         }
      } else {
         return false;
      }
   }

   public static boolean hasParams(PyObject params) {
      if (Py.None == params) {
         return false;
      } else {
         boolean isSeq = isSeq(params);
         if (!isSeq) {
            throw zxJDBC.makeException(zxJDBC.ProgrammingError, zxJDBC.getString("optionalSecond"));
         } else {
            return params.__len__() > 0;
         }
      }
   }

   public static boolean isSeqSeq(PyObject object) {
      if (isSeq(object) && object.__len__() > 0) {
         for(int i = 0; i < object.__len__(); ++i) {
            if (!isSeq(object.__finditem__(i))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void ensureOpen() {
      if (this.closed) {
         throw zxJDBC.makeException(zxJDBC.ProgrammingError, "cursor is closed");
      }
   }

   public PyObject __enter__(ThreadState ts) {
      return this;
   }

   public PyObject __enter__() {
      return this;
   }

   public boolean __exit__(ThreadState ts, PyException exception) {
      this.close();
      return false;
   }

   public boolean __exit__(PyObject type, PyObject value, PyObject traceback) {
      this.close();
      return false;
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.fetch != null) {
         retVal = this.fetch.traverse(visit, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.rsType != null) {
         retVal = visit.visit(this.rsType, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.rsConcur != null) {
         retVal = visit.visit(this.rsConcur, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.warnings != null) {
         retVal = visit.visit(this.warnings, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.lastrowid != null) {
         retVal = visit.visit(this.lastrowid, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.updatecount != null) {
         retVal = visit.visit(this.updatecount, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.connection != null) {
         retVal = visit.visit(this.connection, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.statement != null ? visit.visit(this.statement, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (ob == null) {
         return false;
      } else {
         return ob != this.rsType && ob != this.rsConcur && ob != this.warnings && ob != this.lastrowid && ob != this.updatecount && ob != this.connection && ob != this.statement ? this.fetch.refersDirectlyTo(ob) : true;
      }
   }

   static {
      PyObject[] m = new PyObject[]{new PyString("close"), new PyString("execute"), new PyString("executemany"), new PyString("fetchone"), new PyString("fetchall"), new PyString("fetchmany"), new PyString("callproc"), new PyString("next"), new PyString("write")};
      __methods__ = new PyList(m);
      m = new PyObject[]{new PyString("arraysize"), new PyString("rowcount"), new PyString("rownumber"), new PyString("description"), new PyString("datahandler"), new PyString("warnings"), new PyString("lastrowid"), new PyString("updatecount"), new PyString("softspace"), new PyString("closed"), new PyString("connection")};
      __members__ = new PyList(m);
   }
}
