package com.ziclix.python.sql;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import org.python.core.Py;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;

public class PyExtendedCursor extends PyCursor {
   protected static PyList __members__;
   protected static PyList __methods__;

   PyExtendedCursor(PyConnection connection) {
      super(connection);
   }

   PyExtendedCursor(PyConnection connection, boolean dynamicFetch) {
      super(connection, dynamicFetch);
   }

   PyExtendedCursor(PyConnection connection, boolean dynamicFetch, PyObject rsType, PyObject rsConcur) {
      super(connection, dynamicFetch, rsType, rsConcur);
   }

   public String toString() {
      return "<PyExtendedCursor object instance at " + Py.id(this) + ">";
   }

   public static void classDictInit(PyObject dict) {
      PyCursor.classDictInit(dict);
      dict.__setitem__((String)"tables", new ExtendedCursorFunc("tables", 100, 4, 4, "query for table information"));
      dict.__setitem__((String)"columns", new ExtendedCursorFunc("columns", 101, 4, 4, "query for column information"));
      dict.__setitem__((String)"primarykeys", new ExtendedCursorFunc("primarykeys", 102, 3, 3, "query for primary keys"));
      dict.__setitem__((String)"foreignkeys", new ExtendedCursorFunc("foreignkeys", 103, 6, 6, "query for foreign keys"));
      dict.__setitem__((String)"procedures", new ExtendedCursorFunc("procedures", 104, 3, 3, "query for procedures"));
      dict.__setitem__((String)"procedurecolumns", new ExtendedCursorFunc("procedurecolumns", 105, 4, 4, "query for procedures columns"));
      dict.__setitem__((String)"statistics", new ExtendedCursorFunc("statistics", 106, 5, 5, "description of a table's indices and statistics"));
      dict.__setitem__((String)"gettypeinfo", new ExtendedCursorFunc("gettypeinfo", 107, 0, 1, "query for sql type info"));
      dict.__setitem__((String)"gettabletypeinfo", new ExtendedCursorFunc("gettabletypeinfo", 108, 0, 1, "query for table types"));
      dict.__setitem__((String)"bestrow", new ExtendedCursorFunc("bestrow", 109, 3, 3, "optimal set of columns that uniquely identifies a row"));
      dict.__setitem__((String)"versioncolumns", new ExtendedCursorFunc("versioncolumns", 110, 3, 3, "columns that are automatically updated when any value in a row is updated"));
      dict.__setitem__((String)"classDictInit", (PyObject)null);
      dict.__setitem__((String)"toString", (PyObject)null);
   }

   public PyObject __findattr_ex__(String name) {
      if ("__methods__".equals(name)) {
         return __methods__;
      } else {
         return (PyObject)("__members__".equals(name) ? __members__ : super.__findattr_ex__(name));
      }
   }

   protected void tables(PyObject qualifier, PyObject owner, PyObject table, PyObject type) {
      this.clear();
      String q = this.getMetaDataName(qualifier);
      String o = this.getMetaDataName(owner);
      String t = this.getMetaDataName(table);
      String[] y = null;
      if (type != Py.None) {
         String typeName = null;
         if (isSeq(type)) {
            int len = type.__len__();
            y = new String[len];

            for(int i = 0; i < len; ++i) {
               typeName = this.getMetaDataName(type.__getitem__(i));
               y[i] = typeName == null ? null : typeName.toUpperCase();
            }
         } else {
            typeName = this.getMetaDataName(type.__getitem__(type));
            y = new String[]{typeName == null ? null : typeName.toUpperCase()};
         }
      }

      try {
         this.fetch.add(this.getMetaData().getTables(q, o, t, y));
      } catch (SQLException var12) {
         throw zxJDBC.makeException((Throwable)var12);
      }
   }

   protected void columns(PyObject qualifier, PyObject owner, PyObject table, PyObject column) {
      this.clear();
      String q = this.getMetaDataName(qualifier);
      String o = this.getMetaDataName(owner);
      String t = this.getMetaDataName(table);
      String c = this.getMetaDataName(column);

      try {
         this.fetch.add(this.getMetaData().getColumns(q, o, t, c));
      } catch (SQLException var10) {
         throw zxJDBC.makeException((Throwable)var10);
      }
   }

   protected void procedures(PyObject qualifier, PyObject owner, PyObject procedure) {
      this.clear();
      String q = this.getMetaDataName(qualifier);
      String o = this.getMetaDataName(owner);
      String p = this.getMetaDataName(procedure);

      try {
         this.fetch.add(this.getMetaData().getProcedures(q, o, p));
      } catch (SQLException var8) {
         throw zxJDBC.makeException((Throwable)var8);
      }
   }

   protected void procedurecolumns(PyObject qualifier, PyObject owner, PyObject procedure, PyObject column) {
      this.clear();
      String q = this.getMetaDataName(qualifier);
      String o = this.getMetaDataName(owner);
      String p = this.getMetaDataName(procedure);
      String c = this.getMetaDataName(column);

      try {
         this.fetch.add(this.getMetaData().getProcedureColumns(q, o, p, c));
      } catch (SQLException var10) {
         throw zxJDBC.makeException((Throwable)var10);
      }
   }

   protected void primarykeys(PyObject qualifier, PyObject owner, PyObject table) {
      this.clear();
      String q = this.getMetaDataName(qualifier);
      String o = this.getMetaDataName(owner);
      String t = this.getMetaDataName(table);

      try {
         this.fetch.add(this.getMetaData().getPrimaryKeys(q, o, t));
      } catch (SQLException var8) {
         throw zxJDBC.makeException((Throwable)var8);
      }
   }

   protected void foreignkeys(PyObject primaryQualifier, PyObject primaryOwner, PyObject primaryTable, PyObject foreignQualifier, PyObject foreignOwner, PyObject foreignTable) {
      this.clear();
      String pq = this.getMetaDataName(primaryQualifier);
      String po = this.getMetaDataName(primaryOwner);
      String pt = this.getMetaDataName(primaryTable);
      String fq = this.getMetaDataName(foreignQualifier);
      String fo = this.getMetaDataName(foreignOwner);
      String ft = this.getMetaDataName(foreignTable);

      try {
         this.fetch.add(this.getMetaData().getCrossReference(pq, po, pt, fq, fo, ft));
      } catch (SQLException var14) {
         throw zxJDBC.makeException((Throwable)var14);
      }
   }

   protected void statistics(PyObject qualifier, PyObject owner, PyObject table, PyObject unique, PyObject accuracy) {
      this.clear();
      Set skipCols = new HashSet();
      skipCols.add(12);
      String q = this.getMetaDataName(qualifier);
      String o = this.getMetaDataName(owner);
      String t = this.getMetaDataName(table);
      boolean u = unique.__nonzero__();
      boolean a = accuracy.__nonzero__();

      try {
         this.fetch.add(this.getMetaData().getIndexInfo(q, o, t, u, a), skipCols);
      } catch (SQLException var13) {
         throw zxJDBC.makeException((Throwable)var13);
      }
   }

   protected void typeinfo(PyObject type) {
      this.clear();
      Set skipCols = new HashSet();
      skipCols.add(16);
      skipCols.add(17);

      try {
         this.fetch.add(this.getMetaData().getTypeInfo(), skipCols);
      } catch (SQLException var4) {
         throw zxJDBC.makeException((Throwable)var4);
      }
   }

   protected void tabletypeinfo() {
      this.clear();

      try {
         this.fetch.add(this.getMetaData().getTableTypes());
      } catch (SQLException var2) {
         throw zxJDBC.makeException((Throwable)var2);
      }
   }

   protected void bestrow(PyObject qualifier, PyObject owner, PyObject table) {
      this.clear();
      String c = this.getMetaDataName(qualifier);
      String s = this.getMetaDataName(owner);
      String t = this.getMetaDataName(table);
      int p = 2;
      boolean n = true;

      try {
         this.fetch.add(this.getMetaData().getBestRowIdentifier(c, s, t, p, n));
      } catch (SQLException var10) {
         throw zxJDBC.makeException((Throwable)var10);
      }
   }

   protected void versioncolumns(PyObject qualifier, PyObject owner, PyObject table) {
      this.clear();
      String q = this.getMetaDataName(qualifier);
      String o = this.getMetaDataName(owner);
      String t = this.getMetaDataName(table);

      try {
         this.fetch.add(this.getMetaData().getVersionColumns(q, o, t));
      } catch (SQLException var8) {
         throw zxJDBC.makeException((Throwable)var8);
      }
   }

   protected String getMetaDataName(PyObject name) {
      if (name == Py.None) {
         return null;
      } else {
         String string = name.__str__().toString();

         try {
            if (this.getMetaData().storesLowerCaseIdentifiers()) {
               return string.toLowerCase();
            }

            if (this.getMetaData().storesUpperCaseIdentifiers()) {
               return string.toUpperCase();
            }
         } catch (SQLException var4) {
         }

         return this.datahandler.getMetaDataName(name);
      }
   }

   static {
      PyObject[] m = new PyObject[]{new PyString("tables"), new PyString("columns"), new PyString("primarykeys"), new PyString("foreignkeys"), new PyString("procedures"), new PyString("procedurecolumns"), new PyString("statistics"), new PyString("bestrow"), new PyString("versioncolumns")};
      __methods__ = new PyList(m);
      __methods__.extend(PyCursor.__methods__);
      m = new PyObject[0];
      __members__ = new PyList(m);
      __members__.extend(PyCursor.__members__);
   }
}
