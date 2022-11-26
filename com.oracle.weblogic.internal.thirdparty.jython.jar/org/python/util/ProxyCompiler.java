package org.python.util;

import java.util.Properties;
import org.python.core.Options;
import org.python.core.PySystemState;

public class ProxyCompiler {
   public static void compile(String filename, String destDir) {
      Properties props = new Properties(System.getProperties());
      props.setProperty("python.cachedir.skip", "true");
      PySystemState.initialize(props, (Properties)null);
      PythonInterpreter interp = new PythonInterpreter();
      String origProxyDir = Options.proxyDebugDirectory;

      try {
         Options.proxyDebugDirectory = destDir;
         interp.execfile(filename);
      } finally {
         Options.proxyDebugDirectory = origProxyDir;
      }

   }
}
