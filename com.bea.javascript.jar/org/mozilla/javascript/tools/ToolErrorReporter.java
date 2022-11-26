package org.mozilla.javascript.tools;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

public class ToolErrorReporter implements ErrorReporter {
   private final String messagePrefix;
   private boolean hasReportedErrorFlag;
   private boolean reportWarnings;
   private PrintStream err;

   public ToolErrorReporter(boolean var1) {
      this(var1, System.err);
   }

   public ToolErrorReporter(boolean var1, PrintStream var2) {
      this.messagePrefix = "js: ";
      this.reportWarnings = var1;
      this.err = var2;
   }

   private String buildIndicator(int var1) {
      StringBuffer var2 = new StringBuffer();

      for(int var3 = 0; var3 < var1 - 1; ++var3) {
         var2.append(".");
      }

      var2.append("^");
      return var2.toString();
   }

   public void error(String var1, String var2, int var3, String var4, int var5) {
      this.hasReportedErrorFlag = true;
      var1 = this.formatMessage(var1, var2, var3);
      this.err.println("js: " + var1);
      if (var4 != null) {
         this.err.println("js: " + var4);
         this.err.println("js: " + this.buildIndicator(var5));
      }

   }

   private String formatMessage(String var1, String var2, int var3) {
      Object[] var4;
      if (var3 > 0) {
         if (var2 != null) {
            var4 = new Object[]{var2, new Integer(var3), var1};
            return getMessage("msg.format3", var4);
         } else {
            var4 = new Object[]{new Integer(var3), var1};
            return getMessage("msg.format2", var4);
         }
      } else {
         var4 = new Object[]{var1};
         return getMessage("msg.format1", var4);
      }
   }

   public static String getMessage(String var0) {
      return getMessage(var0, (Object[])null);
   }

   public static String getMessage(String var0, Object var1, Object var2) {
      Object[] var3 = new Object[]{var1, var2};
      return getMessage(var0, var3);
   }

   public static String getMessage(String var0, String var1) {
      Object[] var2 = new Object[]{var1};
      return getMessage(var0, var2);
   }

   public static String getMessage(String var0, Object[] var1) {
      Context var2 = Context.getCurrentContext();
      Locale var3 = var2 == null ? Locale.getDefault() : var2.getLocale();
      ResourceBundle var4 = ResourceBundle.getBundle("org.mozilla.javascript.tools.resources.Messages", var3);

      String var5;
      try {
         var5 = var4.getString(var0);
      } catch (MissingResourceException var7) {
         throw new RuntimeException("no message resource found for message property " + var0);
      }

      if (var1 == null) {
         return var5;
      } else {
         MessageFormat var6 = new MessageFormat(var5);
         return var6.format(var1);
      }
   }

   public boolean hasReportedError() {
      return this.hasReportedErrorFlag;
   }

   public boolean isReportingWarnings() {
      return this.reportWarnings;
   }

   public EvaluatorException runtimeError(String var1, String var2, int var3, String var4, int var5) {
      this.error(var1, var2, var3, var4, var5);
      return new EvaluatorException(var1);
   }

   public void setIsReportingWarnings(boolean var1) {
      this.reportWarnings = var1;
   }

   public void warning(String var1, String var2, int var3, String var4, int var5) {
      if (this.reportWarnings) {
         Object[] var6 = new Object[]{this.formatMessage(var1, var2, var3)};
         var1 = getMessage("msg.warning", var6);
         this.err.println("js: " + var1);
         if (var4 != null) {
            this.err.println("js: " + var4);
            this.err.println("js: " + this.buildIndicator(var5));
         }

      }
   }
}
