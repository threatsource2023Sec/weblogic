package org.python.modules.posix;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import jnr.constants.platform.Errno;
import jnr.posix.POSIXHandler;
import org.python.core.Options;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.imp;

public class PythonPOSIXHandler implements POSIXHandler {
   public void error(Errno error, String extraData) {
      throw Py.OSError(error, Py.newStringOrUnicode(extraData));
   }

   public void error(Errno error, String methodName, String extraData) {
      throw Py.OSError(error, Py.newStringOrUnicode(extraData));
   }

   public void unimplementedError(String methodName) {
      if (!methodName.startsWith("stat.")) {
         throw Py.NotImplementedError(methodName);
      }
   }

   public void warn(POSIXHandler.WARNING_ID id, String message, Object... data) {
   }

   public boolean isVerbose() {
      return Options.verbose >= 3;
   }

   public File getCurrentWorkingDirectory() {
      return new File(Py.getSystemState().getCurrentWorkingDir());
   }

   public String[] getEnv() {
      PyObject items = imp.load("os").__getattr__("environ").invoke("items");
      String[] env = new String[items.__len__()];
      int i = 0;

      PyObject item;
      for(Iterator var4 = items.asIterable().iterator(); var4.hasNext(); env[i++] = String.format("%s=%s", item.__getitem__(0), item.__getitem__(1))) {
         item = (PyObject)var4.next();
      }

      return env;
   }

   public InputStream getInputStream() {
      return System.in;
   }

   public PrintStream getOutputStream() {
      return System.out;
   }

   public int getPID() {
      return 0;
   }

   public PrintStream getErrorStream() {
      return System.err;
   }
}
