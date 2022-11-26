package org.python.modules;

import java.io.File;
import jnr.constants.platform.Errno;
import org.python.core.Py;
import org.python.core.PyList;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.core.imp;

public class _py_compile {
   public static PyList __all__ = new PyList(new PyString[]{new PyString("compile")});

   public static boolean compile(PyString fileName, PyString compiledName, PyString displayName) {
      PySystemState sys = Py.getSystemState();
      String file = sys.getPath(Py.fileSystemDecode(fileName));
      File f = new File(file);
      if (!f.exists()) {
         throw Py.IOError(Errno.ENOENT, (String)file);
      } else {
         String c = compiledName == null ? null : sys.getPath(Py.fileSystemDecode(compiledName));
         String d = displayName == null ? null : Py.fileSystemDecode(displayName);
         byte[] bytes = imp.compileSource(getModuleName(f), f, d, c);
         imp.cacheCompiledSource(file, c, bytes);
         return bytes.length > 0;
      }
   }

   public static final String getModuleName(File f) {
      String name = f.getName();
      int dot = name.lastIndexOf(46);
      if (dot != -1) {
         name = name.substring(0, dot);
      }

      File dir = f.getParentFile();
      if (name.equals("__init__")) {
         name = dir.getName();
         dir = dir.getParentFile();
      }

      while(dir != null && (new File(dir, "__init__.py")).exists()) {
         name = dir.getName() + "." + name;
         dir = dir.getParentFile();
      }

      return name;
   }
}
