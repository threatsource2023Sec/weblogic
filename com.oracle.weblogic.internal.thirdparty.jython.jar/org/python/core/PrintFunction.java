package org.python.core;

@Untraversable
class PrintFunction extends PyBuiltinFunction {
   PrintFunction() {
      super("print", "print(value, ..., sep=' ', end='\\n', file=sys.stdout)\n\nPrints the values to a stream, or to sys.stdout by default.\nOptional keyword arguments:\nfile: a file-like object (stream); defaults to the current sys.stdout.\nsep:  string inserted between values, default a space.\nend:  string appended after the last value, default a newline.\n");
   }

   public PyObject __call__(PyObject[] args, String[] kwds) {
      int kwlen = kwds.length;
      int argslen = args.length;
      boolean useUnicode = false;
      PyObject[] values = new PyObject[argslen - kwlen];
      System.arraycopy(args, 0, values, 0, argslen - kwlen);
      PyObject[] keyValues = new PyObject[kwlen];
      System.arraycopy(args, argslen - kwlen, keyValues, 0, kwlen);
      ArgParser ap = new ArgParser("print", keyValues, kwds, new String[]{"sep", "end", "file"});
      PyObject[] var9 = keyValues;
      int var10 = keyValues.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         PyObject keyValue = var9[var11];
         if (keyValue instanceof PyUnicode) {
            useUnicode = true;
         }
      }

      String sep = ap.getString(0, (String)null);
      String end = ap.getString(1, (String)null);
      PyObject file = ap.getPyObject(2, (PyObject)null);
      return print(values, sep, end, file, useUnicode);
   }

   private static PyObject print(PyObject[] values, String sep, String end, PyObject file, boolean useUnicode) {
      Object out;
      if (file != null && file != Py.None) {
         out = new FixedFileWrapper(file);
      } else {
         out = Py.stdout;
      }

      if (values.length == 0) {
         ((StdoutWrapper)out).println(useUnicode);
      } else {
         if (!useUnicode) {
            PyObject[] var6 = values;
            int var7 = values.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               PyObject value = var6[var8];
               if (value instanceof PyUnicode) {
                  useUnicode = true;
                  break;
               }
            }
         }

         Object sepObject;
         if (sep == null) {
            sepObject = useUnicode ? Py.UnicodeSpace : Py.Space;
         } else {
            sepObject = useUnicode ? Py.newUnicode(sep) : Py.newString(sep);
         }

         Object endObject;
         if (end == null) {
            endObject = useUnicode ? Py.UnicodeNewline : Py.Newline;
         } else {
            endObject = useUnicode ? Py.newUnicode(end) : Py.newString(end);
         }

         ((StdoutWrapper)out).print(values, (PyObject)sepObject, (PyObject)endObject);
      }

      return Py.None;
   }
}
