package com.ziclix.python.sql;

import com.ziclix.python.sql.util.PyArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinMethodSet;
import org.python.core.PyObject;

class CursorFunc extends PyBuiltinMethodSet {
   CursorFunc(String name, int index, int argcount, String doc) {
      this(name, index, argcount, argcount, doc);
   }

   CursorFunc(String name, int index, int minargs, int maxargs, String doc) {
      super(name, index, minargs, maxargs, doc, PyCursor.class);
   }

   public PyObject __call__() {
      PyCursor cursor = (PyCursor)this.__self__;
      switch (this.index) {
         case 0:
            return cursor.fetchmany(cursor.arraysize);
         case 1:
            cursor.close();
            return Py.None;
         case 2:
            return cursor.fetchall();
         case 3:
            return cursor.fetchone();
         case 4:
            return cursor.nextset();
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         default:
            throw this.info.unexpectedCall(0, false);
         case 13:
            return cursor.__enter__();
      }
   }

   public PyObject __call__(PyObject arg) {
      PyCursor cursor = (PyCursor)this.__self__;
      switch (this.index) {
         case 0:
            return cursor.fetchmany(arg.asInt());
         case 1:
         case 2:
         case 3:
         case 4:
         default:
            throw this.info.unexpectedCall(1, false);
         case 5:
            cursor.execute(arg, Py.None, Py.None, Py.None);
            return Py.None;
         case 6:
         case 7:
            return Py.None;
         case 8:
            cursor.callproc(arg, Py.None, Py.None, Py.None);
            return Py.None;
         case 9:
            cursor.executemany(arg, Py.None, Py.None, Py.None);
            return Py.None;
         case 10:
            cursor.scroll(arg.asInt(), "relative");
            return Py.None;
         case 11:
            cursor.execute(arg, Py.None, Py.None, Py.None);
            return Py.None;
         case 12:
            return cursor.prepare(arg);
      }
   }

   public PyObject __call__(PyObject arga, PyObject argb) {
      PyCursor cursor = (PyCursor)this.__self__;
      switch (this.index) {
         case 5:
            cursor.execute(arga, argb, Py.None, Py.None);
            return Py.None;
         case 6:
         default:
            throw this.info.unexpectedCall(2, false);
         case 7:
            return Py.None;
         case 8:
            cursor.callproc(arga, argb, Py.None, Py.None);
            return Py.None;
         case 9:
            cursor.executemany(arga, argb, Py.None, Py.None);
            return Py.None;
         case 10:
            cursor.scroll(arga.asInt(), argb.toString());
            return Py.None;
      }
   }

   public PyObject __call__(PyObject arga, PyObject argb, PyObject argc) {
      PyCursor cursor = (PyCursor)this.__self__;
      switch (this.index) {
         case 5:
            cursor.execute(arga, argb, argc, Py.None);
            return Py.None;
         case 6:
         case 7:
         case 10:
         case 11:
         case 12:
         case 13:
         default:
            throw this.info.unexpectedCall(3, false);
         case 8:
            cursor.callproc(arga, argb, argc, Py.None);
            return Py.None;
         case 9:
            cursor.executemany(arga, argb, argc, Py.None);
            return Py.None;
         case 14:
            return Py.newBoolean(cursor.__exit__(arga, argc, argc));
      }
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      PyCursor cursor = (PyCursor)this.__self__;
      PyArgParser parser = new PyArgParser(args, keywords);
      PyObject sql = parser.arg(0);
      PyObject params = parser.kw("params", Py.None);
      PyObject bindings = parser.kw("bindings", Py.None);
      PyObject maxrows = parser.kw("maxrows", Py.None);
      params = parser.numArg() >= 2 ? parser.arg(1) : params;
      bindings = parser.numArg() >= 3 ? parser.arg(2) : bindings;
      maxrows = parser.numArg() >= 4 ? parser.arg(3) : maxrows;
      switch (this.index) {
         case 5:
            cursor.execute(sql, params, bindings, maxrows);
            return Py.None;
         case 6:
         case 7:
         default:
            throw this.info.unexpectedCall(args.length, true);
         case 8:
            cursor.callproc(sql, params, bindings, maxrows);
            return Py.None;
         case 9:
            cursor.executemany(sql, params, bindings, maxrows);
            return Py.None;
      }
   }
}
