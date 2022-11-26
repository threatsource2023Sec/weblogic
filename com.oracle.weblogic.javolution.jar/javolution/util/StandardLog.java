package javolution.util;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javolution.lang.Text;
import javolution.realtime.LogContext;

public class StandardLog extends LogContext {
   private Logger _logger;

   public StandardLog() {
      this(Logger.getLogger(""));
   }

   public StandardLog(Logger var1) {
      this._logger = var1;
   }

   public final Logger getLogger() {
      return this._logger;
   }

   public static boolean isLoggable(Level var0) {
      LogContext var1 = LogContext.current();
      if (var1 instanceof StandardLog) {
         return ((StandardLog)var1)._logger.isLoggable(var0);
      } else if (var0.intValue() >= Level.WARNING.intValue()) {
         return var1.isWarningLogged();
      } else {
         return var0.intValue() >= Level.INFO.intValue() ? var1.isInfoLogged() : false;
      }
   }

   public static void log(LogRecord var0) {
      LogContext var1 = LogContext.current();
      if (var1 instanceof StandardLog) {
         ((StandardLog)var1)._logger.log(var0);
      } else {
         Throwable var2 = var0.getThrown();
         if (var2 != null) {
            var1.logError(var2, toCharSeq(var0.getMessage()));
         } else if (var0.getLevel().intValue() > Level.WARNING.intValue()) {
            var1.logWarning(toCharSeq(var0.getMessage()));
         } else if (var0.getLevel().intValue() > Level.INFO.intValue()) {
            var1.logInfo(toCharSeq(var0.getMessage()));
         }
      }

   }

   public static void severe(String var0) {
      LogContext var1 = LogContext.current();
      if (var1 instanceof StandardLog) {
         ((StandardLog)var1)._logger.severe(var0);
      } else {
         var1.logWarning(toCharSeq(var0));
      }

   }

   public static void warning(String var0) {
      LogContext var1 = LogContext.current();
      var1.logWarning(toCharSeq(var0));
   }

   public static void info(String var0) {
      LogContext var1 = LogContext.current();
      var1.logInfo(toCharSeq(var0));
   }

   public static void config(String var0) {
      LogContext var1 = LogContext.current();
      if (var1 instanceof StandardLog) {
         ((StandardLog)var1)._logger.config(var0);
      }

   }

   public static void fine(String var0) {
      LogContext var1 = LogContext.current();
      if (var1 instanceof StandardLog) {
         ((StandardLog)var1)._logger.fine(var0);
      }

   }

   public static void finer(String var0) {
      LogContext var1 = LogContext.current();
      if (var1 instanceof StandardLog) {
         ((StandardLog)var1)._logger.finer(var0);
      }

   }

   public static void finest(String var0) {
      LogContext var1 = LogContext.current();
      if (var1 instanceof StandardLog) {
         ((StandardLog)var1)._logger.finest(var0);
      }

   }

   public static void throwing(String var0, String var1, Throwable var2) {
      LogContext var3 = LogContext.current();
      if (var3 instanceof StandardLog) {
         ((StandardLog)var3)._logger.throwing(var0, var1, var2);
      } else {
         var3.logError(var2, (CharSequence)null);
      }

   }

   public static void entering(String var0, String var1) {
      LogContext var2 = LogContext.current();
      if (var2 instanceof StandardLog) {
         ((StandardLog)var2)._logger.entering(var0, var1);
      }

   }

   public static void exiting(String var0, String var1) {
      LogContext var2 = LogContext.current();
      if (var2 instanceof StandardLog) {
         ((StandardLog)var2)._logger.exiting(var0, var1);
      }

   }

   public boolean isInfoLogged() {
      return this._logger.isLoggable(Level.INFO);
   }

   public boolean isWarningLogged() {
      return this._logger.isLoggable(Level.WARNING);
   }

   public boolean isErrorLogged() {
      return this._logger.isLoggable(Level.SEVERE);
   }

   public void logInfo(CharSequence var1) {
      this._logger.info(var1.toString());
   }

   public void logWarning(CharSequence var1) {
      this._logger.warning(var1.toString());
   }

   public void logError(Throwable var1, CharSequence var2) {
      String var3 = var2 == null ? var1.getMessage() : var2.toString();
      var3 = var3 == null ? var1.getClass().getName() : var1.getClass().getName() + " - " + var3;
      this._logger.severe(var3);
   }

   private static CharSequence toCharSeq(Object var0) {
      return (CharSequence)(var0 instanceof CharSequence ? (CharSequence)var0 : Text.valueOf((Object)((String)var0)));
   }
}
