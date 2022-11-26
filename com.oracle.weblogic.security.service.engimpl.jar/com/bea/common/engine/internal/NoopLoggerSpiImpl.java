package com.bea.common.engine.internal;

import com.bea.common.logger.spi.LoggerSpi;

public class NoopLoggerSpiImpl implements LoggerSpi {
   public boolean isDebugEnabled() {
      return false;
   }

   public void debug(Object msg) {
   }

   public void debug(Object msg, Throwable th) {
   }

   public void info(Object msg) {
   }

   public void info(Object msg, Throwable th) {
   }

   public void warn(Object msg) {
   }

   public void warn(Object msg, Throwable th) {
   }

   public void error(Object msg) {
   }

   public void error(Object msg, Throwable th) {
   }

   public void severe(Object msg) {
   }

   public void severe(Object msg, Throwable th) {
   }
}
