package org.apache.xmlbeans.impl.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.xmlbeans.SystemProperties;

public class XBeanDebug {
   public static final int TRACE_SCHEMA_LOADING = 1;
   public static final String traceProp = "org.apache.xmlbeans.impl.debug";
   public static final String defaultProp = "";
   private static int _enabled = initializeBitsFromProperty();
   private static int _indent = 0;
   private static String _indentspace = "                                                                                ";
   static PrintStream _err;

   private static int initializeBitsFromProperty() {
      int bits = 0;
      String prop = SystemProperties.getProperty("org.apache.xmlbeans.impl.debug", "");
      if (prop.indexOf("TRACE_SCHEMA_LOADING") >= 0) {
         bits |= 1;
      }

      return bits;
   }

   public static void enable(int bits) {
      _enabled |= bits;
   }

   public static void disable(int bits) {
      _enabled &= ~bits;
   }

   public static void trace(int bits, String message, int indent) {
      if (test(bits)) {
         Class var3 = XBeanDebug.class;
         synchronized(XBeanDebug.class) {
            if (indent < 0) {
               _indent += indent;
            }

            String spaces = _indent < 0 ? "" : (_indent > _indentspace.length() ? _indentspace : _indentspace.substring(0, _indent));
            String logmessage = Thread.currentThread().getName() + ": " + spaces + message + "\n";
            System.err.print(logmessage);
            if (indent > 0) {
               _indent += indent;
            }
         }
      }

   }

   public static boolean test(int bits) {
      return (_enabled & bits) != 0;
   }

   public static String log(String message) {
      log(message, (Throwable)null);
      return message;
   }

   public static String logStackTrace(String message) {
      log(message, new Throwable());
      return message;
   }

   private static synchronized String log(String message, Throwable stackTrace) {
      if (_err == null) {
         try {
            File diagnosticFile = File.createTempFile("xmlbeandebug", ".log");
            _err = new PrintStream(new FileOutputStream(diagnosticFile));
            System.err.println("Diagnostic XML Bean debug log file created: " + diagnosticFile);
         } catch (IOException var3) {
            _err = System.err;
         }
      }

      _err.println(message);
      if (stackTrace != null) {
         stackTrace.printStackTrace(_err);
      }

      return message;
   }

   public static Throwable logException(Throwable t) {
      log(t.getMessage(), t);
      return t;
   }
}
