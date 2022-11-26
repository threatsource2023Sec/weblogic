package org.apache.openjpa.lib.log;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class AbstractLog implements Log {
   protected abstract boolean isEnabled(short var1);

   protected abstract void log(short var1, String var2, Throwable var3);

   public boolean isTraceEnabled() {
      return this.isEnabled((short)1);
   }

   public boolean isInfoEnabled() {
      return this.isEnabled((short)3);
   }

   public boolean isWarnEnabled() {
      return this.isEnabled((short)4);
   }

   public boolean isErrorEnabled() {
      return this.isEnabled((short)5);
   }

   public boolean isFatalEnabled() {
      return this.isEnabled((short)6);
   }

   public void trace(Object message) {
      this.trace(message, throwableParam(message, (Throwable)null));
   }

   public void trace(Object message, Throwable t) {
      if (this.isTraceEnabled()) {
         this.log((short)1, toString(message), throwableParam(message, t));
      }

   }

   public void info(Object message) {
      this.info(message, throwableParam(message, (Throwable)null));
   }

   public void info(Object message, Throwable t) {
      if (this.isInfoEnabled()) {
         this.log((short)3, toString(message), throwableParam(message, t));
      }

   }

   public void warn(Object message) {
      this.warn(message, throwableParam(message, (Throwable)null));
   }

   public void warn(Object message, Throwable t) {
      if (this.isWarnEnabled()) {
         this.log((short)4, toString(message), throwableParam(message, t));
      }

   }

   public void error(Object message) {
      this.error(message, throwableParam(message, (Throwable)null));
   }

   public void error(Object message, Throwable t) {
      if (this.isErrorEnabled()) {
         this.log((short)5, toString(message), throwableParam(message, t));
      }

   }

   public void fatal(Object message) {
      this.fatal(message, throwableParam(message, (Throwable)null));
   }

   public void fatal(Object message, Throwable t) {
      if (this.isFatalEnabled()) {
         this.log((short)6, toString(message), throwableParam(message, t));
      }

   }

   protected static String getStackTrace(Throwable t) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw, true);
      t.printStackTrace(pw);
      pw.close();
      return sw.toString();
   }

   private static Throwable throwableParam(Object message, Throwable t) {
      if (t != null) {
         return t;
      } else {
         return message instanceof Throwable ? (Throwable)message : null;
      }
   }

   private static String toString(Object o) {
      return o == null ? "null" : o.toString();
   }
}
