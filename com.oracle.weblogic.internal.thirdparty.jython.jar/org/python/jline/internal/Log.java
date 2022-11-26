package org.python.jline.internal;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class Log {
   public static final boolean TRACE = Configuration.getBoolean(Log.class.getName() + ".trace");
   public static final boolean DEBUG;
   private static PrintStream output;
   private static boolean useJul;

   public static PrintStream getOutput() {
      return output;
   }

   public static void setOutput(PrintStream out) {
      output = (PrintStream)Preconditions.checkNotNull(out);
   }

   @TestAccessible
   static void render(PrintStream out, Object message) {
      if (message.getClass().isArray()) {
         Object[] array = (Object[])((Object[])message);
         out.print("[");

         for(int i = 0; i < array.length; ++i) {
            out.print(array[i]);
            if (i + 1 < array.length) {
               out.print(",");
            }
         }

         out.print("]");
      } else {
         out.print(message);
      }

   }

   @TestAccessible
   static void log(Level level, Object... messages) {
      if (useJul) {
         logWithJul(level, messages);
      } else {
         synchronized(output) {
            output.format("[%s] ", level);

            for(int i = 0; i < messages.length; ++i) {
               if (i + 1 == messages.length && messages[i] instanceof Throwable) {
                  output.println();
                  ((Throwable)messages[i]).printStackTrace(output);
               } else {
                  render(output, messages[i]);
               }
            }

            output.println();
            output.flush();
         }
      }
   }

   static void logWithJul(Level level, Object... messages) {
      Logger logger = Logger.getLogger("jline");
      Throwable cause = null;
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(baos);

      for(int i = 0; i < messages.length; ++i) {
         if (i + 1 == messages.length && messages[i] instanceof Throwable) {
            cause = (Throwable)messages[i];
         } else {
            render(ps, messages[i]);
         }
      }

      ps.close();
      LogRecord r = new LogRecord(toJulLevel(level), baos.toString());
      r.setThrown(cause);
      logger.log(r);
   }

   private static java.util.logging.Level toJulLevel(Level level) {
      switch (level) {
         case TRACE:
            return java.util.logging.Level.FINEST;
         case DEBUG:
            return java.util.logging.Level.FINE;
         case INFO:
            return java.util.logging.Level.INFO;
         case WARN:
            return java.util.logging.Level.WARNING;
         case ERROR:
            return java.util.logging.Level.SEVERE;
         default:
            throw new IllegalArgumentException();
      }
   }

   public static void trace(Object... messages) {
      if (TRACE) {
         log(Log.Level.TRACE, messages);
      }

   }

   public static void debug(Object... messages) {
      if (TRACE || DEBUG) {
         log(Log.Level.DEBUG, messages);
      }

   }

   public static void info(Object... messages) {
      log(Log.Level.INFO, messages);
   }

   public static void warn(Object... messages) {
      log(Log.Level.WARN, messages);
   }

   public static void error(Object... messages) {
      log(Log.Level.ERROR, messages);
   }

   static {
      DEBUG = TRACE || Configuration.getBoolean(Log.class.getName() + ".debug");
      output = System.err;
      useJul = Configuration.getBoolean("org.python.jline.log.jul");
   }

   public static enum Level {
      TRACE,
      DEBUG,
      INFO,
      WARN,
      ERROR;
   }
}
