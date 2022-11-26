package com.ziclix.python.sql;

import com.ziclix.python.sql.util.PyArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinMethodSet;
import org.python.core.PyObject;

class ConnectionFunc extends PyBuiltinMethodSet {
   ConnectionFunc(String name, int index, int minargs, int maxargs, String doc) {
      super(name, index, minargs, maxargs, doc, PyConnection.class);
   }

   public PyObject __call__() {
      PyConnection c = (PyConnection)this.__self__;
      switch (this.index) {
         case 0:
            c.close();
            return Py.None;
         case 1:
            c.commit();
            return Py.None;
         case 2:
            return c.cursor();
         case 3:
            c.rollback();
            return Py.None;
         case 4:
         default:
            throw this.info.unexpectedCall(0, false);
         case 5:
            return c.__enter__();
      }
   }

   public PyObject __call__(PyObject arg) {
      PyConnection c = (PyConnection)this.__self__;
      switch (this.index) {
         case 2:
            return c.cursor(arg.__nonzero__());
         case 4:
            return c.nativesql(arg);
         default:
            throw this.info.unexpectedCall(1, false);
      }
   }

   public PyObject __call__(PyObject arg1, PyObject arg2, PyObject arg3) {
      PyConnection c = (PyConnection)this.__self__;
      switch (this.index) {
         case 2:
            return c.cursor(arg1.__nonzero__(), arg2, arg3);
         case 6:
            return Py.newBoolean(c.__exit__(arg1, arg2, arg3));
         default:
            throw this.info.unexpectedCall(3, false);
      }
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      PyConnection c = (PyConnection)this.__self__;
      PyArgParser parser = new PyArgParser(args, keywords);
      switch (this.index) {
         case 2:
            PyObject dynamic = parser.kw("dynamic", Py.None);
            PyObject rstype = parser.kw("rstype", Py.None);
            PyObject rsconcur = parser.kw("rsconcur", Py.None);
            dynamic = parser.numArg() >= 1 ? parser.arg(0) : dynamic;
            rstype = parser.numArg() >= 2 ? parser.arg(1) : rstype;
            rsconcur = parser.numArg() >= 3 ? parser.arg(2) : rsconcur;
            return c.cursor(dynamic.__nonzero__(), rstype, rsconcur);
         default:
            throw this.info.unexpectedCall(args.length, true);
      }
   }
}
