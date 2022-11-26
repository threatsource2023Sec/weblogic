package org.python.core;

import java.util.Iterator;

@Untraversable
class AnyFunction extends PyBuiltinFunctionNarrow {
   AnyFunction() {
      super("any", 1, 1, "any(iterable) -> bool\n\nReturn True if bool(x) is True for any x in the iterable.");
   }

   public PyObject __call__(PyObject arg) {
      PyObject iter = arg.__iter__();
      if (iter == null) {
         throw Py.TypeError("'" + arg.getType().fastGetName() + "' object is not iterable");
      } else {
         Iterator var3 = iter.asIterable().iterator();

         PyObject item;
         do {
            if (!var3.hasNext()) {
               return Py.False;
            }

            item = (PyObject)var3.next();
         } while(!item.__nonzero__());

         return Py.True;
      }
   }
}
