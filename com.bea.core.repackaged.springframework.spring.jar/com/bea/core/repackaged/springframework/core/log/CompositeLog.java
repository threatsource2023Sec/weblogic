package com.bea.core.repackaged.springframework.core.log;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.impl.NoOpLog;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

final class CompositeLog implements Log {
   private static final Log NO_OP_LOG = new NoOpLog();
   private final Log fatalLogger;
   private final Log errorLogger;
   private final Log warnLogger;
   private final Log infoLogger;
   private final Log debugLogger;
   private final Log traceLogger;

   public CompositeLog(List loggers) {
      this.fatalLogger = initLogger(loggers, Log::isFatalEnabled);
      this.errorLogger = initLogger(loggers, Log::isErrorEnabled);
      this.warnLogger = initLogger(loggers, Log::isWarnEnabled);
      this.infoLogger = initLogger(loggers, Log::isInfoEnabled);
      this.debugLogger = initLogger(loggers, Log::isDebugEnabled);
      this.traceLogger = initLogger(loggers, Log::isTraceEnabled);
   }

   private static Log initLogger(List loggers, Predicate predicate) {
      Iterator var2 = loggers.iterator();

      Log logger;
      do {
         if (!var2.hasNext()) {
            return NO_OP_LOG;
         }

         logger = (Log)var2.next();
      } while(!predicate.test(logger));

      return logger;
   }

   public boolean isFatalEnabled() {
      return this.fatalLogger != NO_OP_LOG;
   }

   public boolean isErrorEnabled() {
      return this.errorLogger != NO_OP_LOG;
   }

   public boolean isWarnEnabled() {
      return this.warnLogger != NO_OP_LOG;
   }

   public boolean isInfoEnabled() {
      return this.infoLogger != NO_OP_LOG;
   }

   public boolean isDebugEnabled() {
      return this.debugLogger != NO_OP_LOG;
   }

   public boolean isTraceEnabled() {
      return this.traceLogger != NO_OP_LOG;
   }

   public void fatal(Object message) {
      this.fatalLogger.fatal(message);
   }

   public void fatal(Object message, Throwable ex) {
      this.fatalLogger.fatal(message, ex);
   }

   public void error(Object message) {
      this.errorLogger.error(message);
   }

   public void error(Object message, Throwable ex) {
      this.errorLogger.error(message, ex);
   }

   public void warn(Object message) {
      this.warnLogger.warn(message);
   }

   public void warn(Object message, Throwable ex) {
      this.warnLogger.warn(message, ex);
   }

   public void info(Object message) {
      this.infoLogger.info(message);
   }

   public void info(Object message, Throwable ex) {
      this.infoLogger.info(message, ex);
   }

   public void debug(Object message) {
      this.debugLogger.debug(message);
   }

   public void debug(Object message, Throwable ex) {
      this.debugLogger.debug(message, ex);
   }

   public void trace(Object message) {
      this.traceLogger.trace(message);
   }

   public void trace(Object message, Throwable ex) {
      this.traceLogger.trace(message, ex);
   }
}
