package org.apache.openjpa.lib.log;

public class CommonsLogFactory extends LogFactoryAdapter {
   private org.apache.commons.logging.LogFactory _factory = org.apache.commons.logging.LogFactory.getFactory();

   protected Log newLogAdapter(String channel) {
      return new LogAdapter(this._factory.getInstance(channel));
   }

   public static class LogAdapter implements Log {
      private org.apache.commons.logging.Log _log;

      private LogAdapter(org.apache.commons.logging.Log wrapee) {
         this._log = wrapee;
      }

      public org.apache.commons.logging.Log getDelegate() {
         return this._log;
      }

      public boolean isErrorEnabled() {
         return this._log.isErrorEnabled();
      }

      public boolean isFatalEnabled() {
         return this._log.isFatalEnabled();
      }

      public boolean isInfoEnabled() {
         return this._log.isInfoEnabled();
      }

      public boolean isTraceEnabled() {
         return this._log.isTraceEnabled();
      }

      public boolean isWarnEnabled() {
         return this._log.isWarnEnabled();
      }

      public void trace(Object o) {
         this._log.trace(o);
      }

      public void trace(Object o, Throwable t) {
         this._log.trace(o, t);
      }

      public void info(Object o) {
         this._log.info(o);
      }

      public void info(Object o, Throwable t) {
         this._log.info(o, t);
      }

      public void warn(Object o) {
         this._log.warn(o);
      }

      public void warn(Object o, Throwable t) {
         this._log.warn(o, t);
      }

      public void error(Object o) {
         this._log.error(o);
      }

      public void error(Object o, Throwable t) {
         this._log.error(o, t);
      }

      public void fatal(Object o) {
         this._log.fatal(o);
      }

      public void fatal(Object o, Throwable t) {
         this._log.fatal(o, t);
      }

      // $FF: synthetic method
      LogAdapter(org.apache.commons.logging.Log x0, Object x1) {
         this(x0);
      }
   }
}
