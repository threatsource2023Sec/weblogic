package com.bea.logging;

import weblogic.i18n.logging.MessageLoggerRegistry;

public class LoggingLifecycle {
   public LoggingLifecycle() {
      LogFileConfigImpl.setUseDefaultLogFile(true);
      new LogFileConfigImpl();
      MessageLoggerRegistry.registerMessageLogger("", LoggingService.getInstance());
      LoggingService.getInstance().stopUsingPrimordialLogger();
   }
}
