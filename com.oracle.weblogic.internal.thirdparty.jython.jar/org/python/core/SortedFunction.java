package org.python.core;

@Untraversable
class SortedFunction extends PyBuiltinFunction {
   SortedFunction() {
      super("sorted", "sorted(iterable, cmp=None, key=None, reverse=False) --> new sorted list");
   }

   public PyObject __call__(PyObject[] args, String[] kwds) {
      if (args.length == 0) {
         throw Py.TypeError("sorted() takes at least 1 argument (0 given)");
      } else if (args.length > 4) {
         throw Py.TypeError(String.format("sorted() takes at most 4 arguments (%s given)", args.length));
      } else {
         PyObject iter = args[0].__iter__();
         if (iter == null) {
            throw Py.TypeError(String.format("'%s' object is not iterable", args[0].getType().fastGetName()));
         } else {
            PyList seq = new PyList(args[0]);
            PyObject[] newargs = new PyObject[args.length - 1];
            System.arraycopy(args, 1, newargs, 0, args.length - 1);
            ArgParser ap = new ArgParser("sorted", newargs, kwds, new String[]{"cmp", "key", "reverse"}, 0);
            PyObject cmp = ap.getPyObject(0, Py.None);
            PyObject key = ap.getPyObject(1, Py.None);
            PyObject reverse = ap.getPyObject(2, Py.None);
            seq.sort(cmp, key, reverse);
            return seq;
         }
      }
   }
}
