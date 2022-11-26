package com.ziclix.python.sql.util;

import com.ziclix.python.sql.zxJDBC;
import org.python.core.Py;
import org.python.core.PyBuiltinMethodSet;
import org.python.core.PyObject;

class BCPFunc extends PyBuiltinMethodSet {
   BCPFunc(String name, int index, int argcount, String doc) {
      this(name, index, argcount, argcount, doc);
   }

   BCPFunc(String name, int index, int minargs, int maxargs, String doc) {
      super(name, index, minargs, maxargs, doc, BCP.class);
   }

   public PyObject __call__(PyObject arg) {
      BCP bcp = (BCP)this.__self__;
      switch (this.index) {
         case 0:
            String table = (String)arg.__tojava__(String.class);
            if (table == null) {
               throw Py.ValueError(zxJDBC.getString("invalidTableName"));
            }

            PyObject count = bcp.bcp(table, (String)null, Py.None, Py.None, Py.None, (String)null, Py.None);
            return count;
         default:
            throw this.info.unexpectedCall(1, false);
      }
   }

   public PyObject __call__(PyObject arga, PyObject argb) {
      BCP bcp = (BCP)this.__self__;
      switch (this.index) {
         case 0:
            String table = (String)arga.__tojava__(String.class);
            if (table == null) {
               throw Py.ValueError(zxJDBC.getString("invalidTableName"));
            }

            String where = (String)argb.__tojava__(String.class);
            PyObject count = bcp.bcp(table, where, Py.None, Py.None, Py.None, (String)null, Py.None);
            return count;
         default:
            throw this.info.unexpectedCall(2, false);
      }
   }

   public PyObject __call__(PyObject arga, PyObject argb, PyObject argc) {
      BCP bcp = (BCP)this.__self__;
      switch (this.index) {
         case 0:
            String table = (String)arga.__tojava__(String.class);
            if (table == null) {
               throw Py.ValueError(zxJDBC.getString("invalidTableName"));
            }

            String where = (String)argb.__tojava__(String.class);
            PyObject count = bcp.bcp(table, where, argc, Py.None, Py.None, (String)null, Py.None);
            return count;
         default:
            throw this.info.unexpectedCall(3, false);
      }
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      BCP bcp = (BCP)this.__self__;
      switch (this.index) {
         case 0:
            String where = null;
            PyObject params = Py.None;
            PyArgParser parser = new PyArgParser(args, keywords);
            String table = (String)parser.arg(0, Py.None).__tojava__(String.class);
            if (table == null) {
               throw Py.ValueError(zxJDBC.getString("invalidTableName"));
            }

            if (parser.numArg() >= 2) {
               where = (String)parser.arg(1, Py.None).__tojava__(String.class);
            }

            if (where == null) {
               where = (String)parser.kw("where", Py.None).__tojava__(String.class);
            }

            if (parser.numArg() >= 3) {
               params = parser.arg(2, Py.None);
            }

            if (params == Py.None) {
               params = parser.kw("params", Py.None);
            }

            String toTable = (String)parser.kw("toTable", Py.None).__tojava__(String.class);
            PyObject include = parser.kw("include", Py.None);
            PyObject exclude = parser.kw("exclude", Py.None);
            PyObject bindings = parser.kw("bindings", Py.None);
            PyObject count = bcp.bcp(table, where, params, include, exclude, toTable, bindings);
            return count;
         default:
            throw this.info.unexpectedCall(3, false);
      }
   }
}
