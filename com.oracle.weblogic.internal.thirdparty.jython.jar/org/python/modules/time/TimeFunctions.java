package org.python.modules.time;

import org.python.core.Py;
import org.python.core.PyBuiltinFunctionSet;
import org.python.core.PyObject;
import org.python.core.Untraversable;

@Untraversable
class TimeFunctions extends PyBuiltinFunctionSet {
   public static final PyObject module = Py.newString("time");

   public TimeFunctions(String name, int index, int argcount) {
      super(name, index, argcount);
   }

   public PyObject getModule() {
      return module;
   }

   public PyObject __call__() {
      switch (this.index) {
         case 0:
            return Py.newFloat(Time.time());
         case 1:
            return Py.newFloat(Time.clock());
         default:
            throw this.info.unexpectedCall(0, false);
      }
   }
}
