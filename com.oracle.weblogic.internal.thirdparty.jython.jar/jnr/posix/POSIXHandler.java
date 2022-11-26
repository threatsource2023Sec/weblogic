package jnr.posix;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import jnr.constants.platform.Errno;

public interface POSIXHandler {
   void error(Errno var1, String var2);

   void error(Errno var1, String var2, String var3);

   void unimplementedError(String var1);

   void warn(WARNING_ID var1, String var2, Object... var3);

   boolean isVerbose();

   File getCurrentWorkingDirectory();

   String[] getEnv();

   InputStream getInputStream();

   PrintStream getOutputStream();

   int getPID();

   PrintStream getErrorStream();

   public static enum WARNING_ID {
      DUMMY_VALUE_USED("DUMMY_VALUE_USED");

      private String messageID;

      private WARNING_ID(String messageID) {
         this.messageID = messageID;
      }
   }
}
