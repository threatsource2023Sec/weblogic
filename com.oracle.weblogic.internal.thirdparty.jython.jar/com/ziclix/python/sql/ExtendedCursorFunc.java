package com.ziclix.python.sql;

import org.python.core.Py;
import org.python.core.PyBuiltinMethodSet;
import org.python.core.PyObject;

class ExtendedCursorFunc extends PyBuiltinMethodSet {
   ExtendedCursorFunc(String name, int index, int argcount, String doc) {
      this(name, index, argcount, argcount, doc);
   }

   ExtendedCursorFunc(String name, int index, int minargs, int maxargs, String doc) {
      super(name, index, minargs, maxargs, doc, PyExtendedCursor.class);
   }

   public PyObject __call__() {
      PyExtendedCursor cursor = (PyExtendedCursor)this.__self__;
      switch (this.index) {
         case 107:
            cursor.typeinfo(Py.None);
            return Py.None;
         case 108:
            cursor.tabletypeinfo();
            return Py.None;
         default:
            throw this.info.unexpectedCall(0, false);
      }
   }

   public PyObject __call__(PyObject arga) {
      PyExtendedCursor cursor = (PyExtendedCursor)this.__self__;
      switch (this.index) {
         case 107:
            cursor.typeinfo(arga);
            return Py.None;
         default:
            throw this.info.unexpectedCall(1, false);
      }
   }

   public PyObject __call__(PyObject arga, PyObject argb, PyObject argc) {
      PyExtendedCursor cursor = (PyExtendedCursor)this.__self__;
      switch (this.index) {
         case 102:
            cursor.primarykeys(arga, argb, argc);
            return Py.None;
         case 103:
         case 105:
         case 106:
         case 107:
         case 108:
         default:
            throw this.info.unexpectedCall(3, false);
         case 104:
            cursor.procedures(arga, argb, argc);
            return Py.None;
         case 109:
            cursor.bestrow(arga, argb, argc);
            return Py.None;
         case 110:
            cursor.versioncolumns(arga, argb, argc);
            return Py.None;
      }
   }

   public PyObject fancyCall(PyObject[] args) {
      PyExtendedCursor cursor = (PyExtendedCursor)this.__self__;
      switch (this.index) {
         case 103:
            cursor.foreignkeys(args[0], args[1], args[2], args[3], args[4], args[5]);
            return Py.None;
         case 106:
            cursor.statistics(args[0], args[1], args[2], args[3], args[4]);
            return Py.None;
         default:
            throw this.info.unexpectedCall(args.length, true);
      }
   }

   public PyObject __call__(PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4) {
      PyExtendedCursor cursor = (PyExtendedCursor)this.__self__;
      switch (this.index) {
         case 100:
            cursor.tables(arg1, arg2, arg3, arg4);
            return Py.None;
         case 101:
            cursor.columns(arg1, arg2, arg3, arg4);
            return Py.None;
         case 105:
            cursor.procedurecolumns(arg1, arg2, arg3, arg4);
            return Py.None;
         default:
            throw this.info.unexpectedCall(4, false);
      }
   }
}
