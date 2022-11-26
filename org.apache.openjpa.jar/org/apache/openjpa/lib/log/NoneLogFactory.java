package org.apache.openjpa.lib.log;

public class NoneLogFactory implements LogFactory {
   public final Log getLog(String channel) {
      return NoneLogFactory.NoneLog.getInstance();
   }

   public static class NoneLog implements Log {
      private static final NoneLog s_log = new NoneLog();

      public static NoneLog getInstance() {
         return s_log;
      }

      public final boolean isErrorEnabled() {
         return false;
      }

      public final boolean isFatalEnabled() {
         return false;
      }

      public final boolean isInfoEnabled() {
         return false;
      }

      public final boolean isTraceEnabled() {
         return false;
      }

      public final boolean isWarnEnabled() {
         return false;
      }

      public final void trace(Object o) {
      }

      public final void trace(Object o, Throwable t) {
      }

      public final void info(Object o) {
      }

      public final void info(Object o, Throwable t) {
      }

      public final void warn(Object o) {
      }

      public final void warn(Object o, Throwable t) {
      }

      public final void error(Object o) {
      }

      public final void error(Object o, Throwable t) {
      }

      public final void fatal(Object o) {
      }

      public final void fatal(Object o, Throwable t) {
      }
   }
}
