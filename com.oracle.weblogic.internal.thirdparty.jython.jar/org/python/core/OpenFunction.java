package org.python.core;

import java.io.InputStream;
import java.io.OutputStream;

@Untraversable
class OpenFunction extends PyBuiltinFunction {
   private static final String warning = "Passing an Input/OutputStream to open is deprecated, use org.python.core.util.FileUtil.wrap(stream[, bufsize]) instead.";

   OpenFunction() {
      super("open", "Open a file using the file() type, returns a file object.  This is the\npreferred way to open a file.");
   }

   public PyObject __call__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("file", args, kwds, new String[]{"name", "mode", "buffering"}, 1);
      PyObject obj = ap.getPyObject(0);
      if (obj.getJavaProxy() != null) {
         int bufsize = ap.getInt(2, -1);
         Object javaProxy = obj.getJavaProxy();
         if (javaProxy instanceof InputStream) {
            Py.warning(Py.DeprecationWarning, "Passing an Input/OutputStream to open is deprecated, use org.python.core.util.FileUtil.wrap(stream[, bufsize]) instead.");
            return new PyFile((InputStream)javaProxy, bufsize);
         }

         if (javaProxy instanceof OutputStream) {
            Py.warning(Py.DeprecationWarning, "Passing an Input/OutputStream to open is deprecated, use org.python.core.util.FileUtil.wrap(stream[, bufsize]) instead.");
            return new PyFile((OutputStream)javaProxy, bufsize);
         }
      }

      return PyFile.TYPE.__call__(args, kwds);
   }
}
