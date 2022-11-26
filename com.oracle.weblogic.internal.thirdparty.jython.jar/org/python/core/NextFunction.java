package org.python.core;

@Untraversable
class NextFunction extends PyBuiltinFunction {
   NextFunction() {
      super("next", "next(iterator[, default])\n\nReturn the next item from the iterator. If default is given and the iterator\nis exhausted, it is returned instead of raising StopIteration.");
   }

   public PyObject __call__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("next", args, kwds, new String[]{"iterator", "default"}, 1);
      ap.noKeywords();
      PyObject it = ap.getPyObject(0);
      PyObject def = ap.getPyObject(1, (PyObject)null);
      PyObject next;
      if ((next = it.__findattr__("next")) == null) {
         throw Py.TypeError(String.format("'%.200s' object is not an iterator", it.getType().fastGetName()));
      } else {
         try {
            return next.__call__();
         } catch (PyException var8) {
            if (var8.match(Py.StopIteration) && def != null) {
               return def;
            } else {
               throw var8;
            }
         }
      }
   }
}
