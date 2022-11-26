package org.python.core;

import java.util.Iterator;

@Untraversable
class MaxFunction extends PyBuiltinFunction {
   MaxFunction() {
      super("max", "max(iterable[, key=func]) -> value\nmax(a, b, c, ...[, key=func]) -> value\n\nWith a single iterable argument, return its largest item.\nWith two or more arguments, return the largest argument.");
   }

   public PyObject __call__(PyObject[] args, String[] kwds) {
      int argslen = args.length;
      PyObject key = null;
      if (args.length - kwds.length == 0) {
         throw Py.TypeError("max() expected 1 arguments, got 0");
      } else {
         if (kwds.length > 0) {
            if (!kwds[0].equals("key")) {
               throw Py.TypeError("max() got an unexpected keyword argument");
            }

            key = args[argslen - 1];
            PyObject[] newargs = new PyObject[argslen - 1];
            System.arraycopy(args, 0, newargs, 0, argslen - 1);
            args = newargs;
         }

         return args.length > 1 ? max(new PyTuple(args), key) : max(args[0], key);
      }
   }

   private static PyObject max(PyObject o, PyObject key) {
      PyObject max = null;
      PyObject maxKey = null;
      Iterator var4 = o.asIterable().iterator();

      while(true) {
         PyObject item;
         PyObject itemKey;
         do {
            if (!var4.hasNext()) {
               if (max == null) {
                  throw Py.ValueError("max of empty sequence");
               }

               return max;
            }

            item = (PyObject)var4.next();
            if (key == null) {
               itemKey = item;
            } else {
               itemKey = key.__call__(item);
            }
         } while(maxKey != null && !itemKey._gt(maxKey).__nonzero__());

         maxKey = itemKey;
         max = item;
      }
   }
}
