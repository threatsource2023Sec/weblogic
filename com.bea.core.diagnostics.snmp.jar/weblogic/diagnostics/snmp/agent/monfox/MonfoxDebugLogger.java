package weblogic.diagnostics.snmp.agent.monfox;

import monfox.log.Logger;
import weblogic.diagnostics.debug.DebugLogger;

public class MonfoxDebugLogger extends Logger {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPToolkit");
   private static final MonfoxDebugLogger SINGLETON = new MonfoxDebugLogger();

   private MonfoxDebugLogger() {
   }

   public boolean isErrorEnabled() {
      return DEBUG.isDebugEnabled();
   }

   public boolean isWarnEnabled() {
      return DEBUG.isDebugEnabled();
   }

   public boolean isInfoEnabled() {
      return DEBUG.isDebugEnabled();
   }

   public boolean isConfigEnabled() {
      return DEBUG.isDebugEnabled();
   }

   public boolean isCommsEnabled() {
      return DEBUG.isDebugEnabled();
   }

   public boolean isDebugEnabled() {
      return DEBUG.isDebugEnabled();
   }

   public boolean isDetailedEnabled() {
      return DEBUG.isDebugEnabled();
   }

   public boolean isEnabled() {
      return DEBUG.isDebugEnabled();
   }

   public void info(Object obj) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString());
      }

   }

   public void info(Object obj, Throwable throwable) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString(), throwable);
      }

   }

   public void debug(Object obj) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString());
      }

   }

   public void debug(Object obj, Throwable throwable) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString(), throwable);
      }

   }

   public void warn(Object obj) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString());
      }

   }

   public void warn(Object obj, Throwable throwable) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString(), throwable);
      }

   }

   public void error(Object obj) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString());
      }

   }

   public void error(Object obj, Throwable throwable) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString(), throwable);
      }

   }

   public void config(Object obj) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString());
      }

   }

   public void config(Object obj, Throwable throwable) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString(), throwable);
      }

   }

   public void comms(Object obj) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString());
      }

   }

   public void comms(Object obj, Throwable throwable) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString(), throwable);
      }

   }

   public void detailed(Object obj) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString());
      }

   }

   public void detailed(Object obj, Throwable throwable) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(obj.toString(), throwable);
      }

   }

   static class ProviderImpl implements Logger.Provider {
      public Logger getInstance(String s) {
         return MonfoxDebugLogger.SINGLETON;
      }

      public void enableAll() {
      }

      public void disableAll() {
      }
   }
}
