package org.python.util;

import java.util.Properties;
import org.python.core.PySystemState;
import org.python.core.imp;

public class JarRunner {
   public static void run(String[] args) {
      String runner = "__run__";
      String[] argv = new String[args.length + 1];
      argv[0] = "__run__";
      System.arraycopy(args, 0, argv, 1, args.length);
      PySystemState.initialize(PySystemState.getBaseProperties(), (Properties)null, argv);
      imp.load("__run__");
   }

   public static void main(String[] args) {
      run(args);
   }
}
