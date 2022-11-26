package com.bea.common.logger.service;

import com.bea.common.logger.spi.LoggerSpi;

public interface LoggerService {
   String SERVICE_NAME = LoggerService.class.getName();

   LoggerSpi getLogger(String var1) throws LoggerInitializationException, LoggerNotFoundException;
}
