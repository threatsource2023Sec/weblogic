package jnr.posix.util;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jnr.constants.platform.Errno;
import jnr.posix.POSIXHandler;

public class DefaultPOSIXHandler implements POSIXHandler {
   public void error(Errno error, String extraData) {
      throw new RuntimeException("native error " + error.description() + " " + extraData);
   }

   public void error(Errno error, String methodName, String extraData) {
      throw new RuntimeException("native error calling " + methodName + ": " + error.description() + " " + extraData);
   }

   public void unimplementedError(String methodName) {
      throw new IllegalStateException(methodName + " is not implemented in jnr-posix");
   }

   public void warn(POSIXHandler.WARNING_ID id, String message, Object... data) {
      String msg;
      try {
         msg = String.format(message, data);
      } catch (IllegalFormatException var6) {
         msg = message + " " + Arrays.toString(data);
      }

      Logger.getLogger("jnr-posix").log(Level.WARNING, msg);
   }

   public boolean isVerbose() {
      return false;
   }

   public File getCurrentWorkingDirectory() {
      return new File(".");
   }

   public String[] getEnv() {
      String[] envp = new String[System.getenv().size()];
      int i = 0;

      Map.Entry pair;
      for(Iterator var3 = System.getenv().entrySet().iterator(); var3.hasNext(); envp[i++] = (String)pair.getKey() + "=" + (String)pair.getValue()) {
         pair = (Map.Entry)var3.next();
      }

      return envp;
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
