package org.python.netty.handler.logging;

import org.python.netty.util.internal.logging.InternalLogLevel;

public enum LogLevel {
   TRACE(InternalLogLevel.TRACE),
   DEBUG(InternalLogLevel.DEBUG),
   INFO(InternalLogLevel.INFO),
   WARN(InternalLogLevel.WARN),
   ERROR(InternalLogLevel.ERROR);

   private final InternalLogLevel internalLevel;

   private LogLevel(InternalLogLevel internalLevel) {
      this.internalLevel = internalLevel;
   }

   public InternalLogLevel toInternalLevel() {
      return this.internalLevel;
   }
}
