package javolution.realtime;

import javolution.util.StandardLog;

public abstract class LogContext extends Context {
   public static final StandardLog STANDARD = new StandardLog();
   public static final LogContext NULL = new NullLog();
   public static final LogContext SYSTEM = new SystemLog();
   public static final LogContext SYSTEM_ERR = new SystemErrLog();
   private static volatile LogContext Default;

   public static LogContext current() {
      for(Context var0 = Context.current(); var0 != null; var0 = var0.getOuter()) {
         if (var0 instanceof LogContext) {
            return (LogContext)var0;
         }
      }

      return Default;
   }

   public static LogContext getDefault() {
      return Default;
   }

   public static void setDefault(LogContext var0) {
      Default = var0;
   }

   public static void info(CharSequence var0) {
      LogContext var1 = current();
      var1.logInfo(var0);
   }

   public static void warning(CharSequence var0) {
      LogContext var1 = current();
      var1.logWarning(var0);
   }

   public static void error(Throwable var0) {
      LogContext var1 = current();
      var1.logError(var0, (CharSequence)null);
   }

   public static void error(Throwable var0, CharSequence var1) {
      LogContext var2 = current();
      var2.logError(var0, var1);
   }

   public abstract boolean isInfoLogged();

   public abstract void logInfo(CharSequence var1);

   public abstract boolean isWarningLogged();

   public abstract void logWarning(CharSequence var1);

   public abstract boolean isErrorLogged();

   public abstract void logError(Throwable var1, CharSequence var2);

   protected void enterAction() {
   }

   protected void exitAction() {
   }

   static {
      Default = STANDARD;
   }

   private static class SystemErrLog extends LogContext {
      private SystemErrLog() {
      }

      public boolean isInfoLogged() {
         return false;
      }

      public boolean isWarningLogged() {
         return true;
      }

      public boolean isErrorLogged() {
         return true;
      }

      public void logInfo(CharSequence var1) {
      }

      public void logWarning(CharSequence var1) {
         System.err.print("[warning] ");
         System.err.println(var1);
      }

      public void logError(Throwable var1, CharSequence var2) {
         System.err.print("[error] ");
         System.err.print(var1.getClass().getName());
         if (var2 != null) {
            System.err.print(" - ");
            System.err.println(var2);
         } else {
            String var3 = var1.getMessage();
            if (var3 != null) {
               System.err.print(" - ");
               System.err.println(var3);
            } else {
               System.err.println();
            }
         }

         var1.printStackTrace();
      }

      SystemErrLog(Object var1) {
         this();
      }
   }

   private static class SystemLog extends LogContext {
      private SystemLog() {
      }

      public boolean isInfoLogged() {
         return true;
      }

      public boolean isWarningLogged() {
         return true;
      }

      public boolean isErrorLogged() {
         return true;
      }

      public void logInfo(CharSequence var1) {
         System.out.print("[info] ");
         System.out.println(var1);
      }

      public void logWarning(CharSequence var1) {
         System.err.print("[warning] ");
         System.err.println(var1);
      }

      public void logError(Throwable var1, CharSequence var2) {
         System.err.print("[error] ");
         System.err.print(var1.getClass().getName());
         if (var2 != null) {
            System.err.print(" - ");
            System.err.println(var2);
         } else {
            String var3 = var1.getMessage();
            if (var3 != null) {
               System.err.print(" - ");
               System.err.println(var3);
            } else {
               System.err.println();
            }
         }

         var1.printStackTrace();
      }

      SystemLog(Object var1) {
         this();
      }
   }

   private static class NullLog extends LogContext {
      private NullLog() {
      }

      public boolean isInfoLogged() {
         return false;
      }

      public boolean isWarningLogged() {
         return false;
      }

      public boolean isErrorLogged() {
         return false;
      }

      public void logInfo(CharSequence var1) {
      }

      public void logWarning(CharSequence var1) {
      }

      public void logError(Throwable var1, CharSequence var2) {
      }

      NullLog(Object var1) {
         this();
      }
   }
}
