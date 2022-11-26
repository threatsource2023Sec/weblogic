package org.apache.openjpa.lib.log;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log4JLogFactory extends LogFactoryAdapter {
   protected Log newLogAdapter(String channel) {
      return new LogAdapter(LogManager.getLogger(channel));
   }

   public static class LogAdapter implements Log {
      private Logger _log;

      private LogAdapter(Logger wrapee) {
         this._log = wrapee;
      }

      public Logger getDelegate() {
         return this._log;
      }

      public boolean isTraceEnabled() {
         return this._log.isEnabledFor(Level.DEBUG);
      }

      public boolean isInfoEnabled() {
         return this._log.isEnabledFor(Level.INFO);
      }

      public boolean isWarnEnabled() {
         return this._log.isEnabledFor(Level.WARN);
      }

      public boolean isErrorEnabled() {
         return this._log.isEnabledFor(Level.ERROR);
      }

      public boolean isFatalEnabled() {
         return this._log.isEnabledFor(Level.FATAL);
      }

      public void trace(Object o) {
         this._log.debug(o);
      }

      public void trace(Object o, Throwable t) {
         this._log.debug(o, t);
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
      LogAdapter(Logger x0, Object x1) {
         this(x0);
      }
   }
}
