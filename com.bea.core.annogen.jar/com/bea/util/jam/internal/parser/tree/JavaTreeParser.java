package com.bea.util.jam.internal.parser.tree;

import java.util.HashMap;

public class JavaTreeParser extends JavaTreeConsts {
   protected static final int INFO = 1;
   protected static final int WARN = 2;
   protected static final int ERROR = 3;
   protected static final int FATAL = 4;

   public HashMap parse(String file, boolean verbose) {
      if (verbose) {
         System.out.println("WARNING: not suitable JavaTreeParser, ignoring to parse the file [" + file + "]");
      }

      return new HashMap();
   }

   private static void logInner(boolean verbose, String msg) {
      if (verbose) {
         System.out.println(msg);
      }
   }

   protected static void log(boolean verbose, String msg) {
      log(verbose, 1, msg);
   }

   protected static void log(boolean verbose, int level, String msg) {
      if (verbose) {
         msg = msg == null ? "" : msg;
         switch (level) {
            case 2:
               msg = "[WARNING] " + msg;
               break;
            case 3:
               msg = "[ERROR] " + msg;
               break;
            case 4:
               msg = "[FATAL] " + msg;
               break;
            default:
               msg = "[INFO] " + msg;
         }

         logInner(verbose, msg);
      }
   }
}
