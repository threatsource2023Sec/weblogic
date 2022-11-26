package weblogic.nodemanager.server;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;
import java.util.logging.ErrorManager;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import weblogic.nodemanager.plugin.NMEnvironment;

public class NMEnvironmentImpl implements NMEnvironment {
   private final Logger nmLogger;

   public NMEnvironmentImpl(String componentType) {
      this.nmLogger = new LoggerWrapper(componentType, NMServer.nmLog);
   }

   public Logger getNMLogger() {
      return this.nmLogger;
   }

   class HandlerWrapper extends java.util.logging.Handler {
      private final java.util.logging.Handler delegate;

      public HandlerWrapper(java.util.logging.Handler delegate) {
         this.delegate = delegate;
      }

      public void publish(LogRecord record) {
         this.delegate.publish(record);
      }

      public void flush() {
         this.delegate.flush();
      }

      public void close() throws SecurityException {
         throw new UnsupportedOperationException();
      }

      public void setFormatter(Formatter newFormatter) throws SecurityException {
         throw new UnsupportedOperationException();
      }

      public Formatter getFormatter() {
         return this.delegate.getFormatter();
      }

      public void setEncoding(String encoding) throws SecurityException, UnsupportedEncodingException {
         throw new UnsupportedOperationException();
      }

      public String getEncoding() {
         return this.delegate.getEncoding();
      }

      public void setFilter(Filter newFilter) throws SecurityException {
         throw new UnsupportedOperationException();
      }

      public Filter getFilter() {
         return this.delegate.getFilter();
      }

      public void setErrorManager(ErrorManager em) {
         throw new UnsupportedOperationException();
      }

      public ErrorManager getErrorManager() {
         return this.delegate.getErrorManager();
      }

      public void reportError(String msg, Exception ex, int code) {
         throw new UnsupportedOperationException();
      }

      public void setLevel(Level newLevel) throws SecurityException {
         throw new UnsupportedOperationException();
      }

      public Level getLevel() {
         return this.delegate.getLevel();
      }

      public boolean isLoggable(LogRecord record) {
         return this.delegate.isLoggable(record);
      }
   }

   class LoggerWrapper extends Logger {
      private final Logger delegate;

      protected LoggerWrapper(String name, Logger delegate) {
         super(name, (String)null);
         this.delegate = delegate;
      }

      public ResourceBundle getResourceBundle() {
         throw new UnsupportedOperationException();
      }

      public String getResourceBundleName() {
         return null;
      }

      public void setFilter(Filter newFilter) throws SecurityException {
         throw new UnsupportedOperationException();
      }

      public Filter getFilter() {
         throw new UnsupportedOperationException();
      }

      public void log(LogRecord record) {
         this.delegate.log(record);
      }

      public void log(Level level, String msg) {
         this.delegate.log(level, msg);
      }

      public void log(Level level, String msg, Object param1) {
         this.delegate.log(level, msg, param1);
      }

      public void log(Level level, String msg, Object[] params) {
         this.delegate.log(level, msg, params);
      }

      public void log(Level level, String msg, Throwable thrown) {
         this.delegate.log(level, msg, thrown);
      }

      public void logp(Level level, String sourceClass, String sourceMethod, String msg) {
         this.delegate.logp(level, sourceClass, sourceMethod, msg);
      }

      public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object param1) {
         this.delegate.logp(level, sourceClass, sourceMethod, msg, param1);
      }

      public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object[] params) {
         this.delegate.logp(level, sourceClass, sourceMethod, msg, params);
      }

      public void logp(Level level, String sourceClass, String sourceMethod, String msg, Throwable thrown) {
         this.delegate.logp(level, sourceClass, sourceMethod, msg, thrown);
      }

      public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg) {
         this.delegate.logrb(level, sourceClass, sourceMethod, bundleName, msg);
      }

      public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object param1) {
         this.delegate.logrb(level, sourceClass, sourceMethod, bundleName, msg, param1);
      }

      public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object[] params) {
         this.delegate.logrb(level, sourceClass, sourceMethod, bundleName, msg, params);
      }

      public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Throwable thrown) {
         this.delegate.logrb(level, sourceClass, sourceMethod, bundleName, msg, thrown);
      }

      public void entering(String sourceClass, String sourceMethod) {
         this.delegate.entering(sourceClass, sourceMethod);
      }

      public void entering(String sourceClass, String sourceMethod, Object param1) {
         this.delegate.entering(sourceClass, sourceMethod, param1);
      }

      public void entering(String sourceClass, String sourceMethod, Object[] params) {
         this.delegate.entering(sourceClass, sourceMethod, params);
      }

      public void exiting(String sourceClass, String sourceMethod) {
         this.delegate.exiting(sourceClass, sourceMethod);
      }

      public void exiting(String sourceClass, String sourceMethod, Object result) {
         this.delegate.exiting(sourceClass, sourceMethod, result);
      }

      public void throwing(String sourceClass, String sourceMethod, Throwable thrown) {
         this.delegate.throwing(sourceClass, sourceMethod, thrown);
      }

      public void severe(String msg) {
         this.delegate.severe(msg);
      }

      public void warning(String msg) {
         this.delegate.warning(msg);
      }

      public void info(String msg) {
         this.delegate.info(msg);
      }

      public void config(String msg) {
         this.delegate.config(msg);
      }

      public void fine(String msg) {
         this.delegate.fine(msg);
      }

      public void finer(String msg) {
         this.delegate.finer(msg);
      }

      public void finest(String msg) {
         this.delegate.finest(msg);
      }

      public void setLevel(Level newLevel) throws SecurityException {
         throw new UnsupportedOperationException();
      }

      public Level getLevel() {
         return this.delegate.getLevel();
      }

      public boolean isLoggable(Level level) {
         return this.delegate.isLoggable(level);
      }

      public String getName() {
         return this.delegate.getName();
      }

      public void addHandler(java.util.logging.Handler handler) throws SecurityException {
         throw new UnsupportedOperationException();
      }

      public void removeHandler(java.util.logging.Handler handler) throws SecurityException {
         throw new UnsupportedOperationException();
      }

      public java.util.logging.Handler[] getHandlers() {
         java.util.logging.Handler[] handlers = this.delegate.getHandlers();
         java.util.logging.Handler[] wrappers = new java.util.logging.Handler[handlers.length];

         for(int i = 0; i < handlers.length; ++i) {
            wrappers[i] = NMEnvironmentImpl.this.new HandlerWrapper(handlers[i]);
         }

         return wrappers;
      }

      public void setUseParentHandlers(boolean useParentHandlers) {
         throw new UnsupportedOperationException();
      }

      public boolean getUseParentHandlers() {
         return this.delegate.getUseParentHandlers();
      }

      public Logger getParent() {
         return null;
      }

      public void setParent(Logger parent) {
         throw new UnsupportedOperationException();
      }
   }
}
