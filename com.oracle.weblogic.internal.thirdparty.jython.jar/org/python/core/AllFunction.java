package org.python.core;

import java.util.Iterator;

@Untraversable
class AllFunction extends PyBuiltinFunctionNarrow {
   AllFunction() {
      super("all", 1, 1, "all(iterable) -> bool\n\nReturn True if bool(x) is True for all values x in the iterable.");
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
               return Py.True;
            }

            item = (PyObject)var3.next();
         } while(item.__nonzero__());

         return Py.False;
      }
   }
}
