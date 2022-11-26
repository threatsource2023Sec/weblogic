package com.bea.common.engine.internal;

import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;

public class NoopLoggerServiceImpl implements LoggerService {
   NoopLoggerSpiImpl logger = new NoopLoggerSpiImpl();

   public LoggerSpi getLogger(String name) {
      return this.logger;
   }
}
