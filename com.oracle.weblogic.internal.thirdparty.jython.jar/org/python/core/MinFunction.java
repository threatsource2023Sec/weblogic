package org.python.core;

import java.util.Iterator;

@Untraversable
class MinFunction extends PyBuiltinFunction {
   MinFunction() {
      super("min", "min(iterable[, key=func]) -> value\nmin(a, b, c, ...[, key=func]) -> value\n\nWith a single iterable argument, return its smallest item.\nWith two or more arguments, return the smallest argument.'");
   }

   public PyObject __call__(PyObject[] args, String[] kwds) {
      int argslen = args.length;
      PyObject key = null;
      if (args.length - kwds.length == 0) {
         throw Py.TypeError("min() expected 1 arguments, got 0");
      } else {
         if (kwds.length > 0) {
            if (!kwds[0].equals("key")) {
               throw Py.TypeError("min() got an unexpected keyword argument");
            }

            key = args[argslen - 1];
            PyObject[] newargs = new PyObject[argslen - 1];
            System.arraycopy(args, 0, newargs, 0, argslen - 1);
            args = newargs;
         }

         return args.length > 1 ? min(new PyTuple(args), key) : min(args[0], key);
      }
   }

   private static PyObject min(PyObject o, PyObject key) {
      PyObject min = null;
      PyObject minKey = null;
      Iterator var4 = o.asIterable().iterator();

      while(true) {
         PyObject item;
         PyObject itemKey;
         do {
            if (!var4.hasNext()) {
               if (min == null) {
                  throw Py.ValueError("min of empty sequence");
               }

               return min;
            }

            item = (PyObject)var4.next();
            if (key == null) {
               itemKey = item;
            } else {
               itemKey = key.__call__(item);
            }
         } while(minKey != null && !itemKey._lt(minKey).__nonzero__());

         minKey = itemKey;
         min = item;
      }
   }
}
