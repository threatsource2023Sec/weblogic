package org.python.core;

@Untraversable
class ImportFunction extends PyBuiltinFunction {
   ImportFunction() {
      super("__import__", "__import__(name, globals={}, locals={}, fromlist=[], level=-1) -> module\n\nImport a module.  The globals are only used to determine the context;\nthey are not modified.  The locals are currently unused.  The fromlist\nshould be a list of names to emulate ``from name import ...'', or an\nempty list to emulate ``import name''.\nWhen importing a module from a package, note that __import__('A.B', ...)\nreturns package A when fromlist is empty, but its submodule B when\nfromlist is not empty.  Level is used to determine whether to perform \nabsolute or relative imports.  -1 is the original strategy of attempting\nboth absolute and relative imports, 0 is absolute, a positive number\nis the number of parent directories to search relative to the current module.");
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("__import__", args, keywords, new String[]{"name", "globals", "locals", "fromlist", "level"}, 1);
      String module = ap.getString(0);
      PyObject globals = ap.getPyObject(1, (PyObject)null);
      PyObject fromlist = ap.getPyObject(3, Py.EmptyTuple);
      int level = ap.getInt(4, -1);
      return imp.importName(module.intern(), fromlist == Py.None || fromlist.__len__() == 0, globals, fromlist, level);
   }
}
