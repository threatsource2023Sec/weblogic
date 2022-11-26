package com.bea.common.security.internal.utils.database;

import com.bea.common.security.internal.service.ServiceLogger;

public class ASIDBConnUnavailableException extends Exception {
   public ASIDBConnUnavailableException() {
      super(ServiceLogger.getDBConnectionNotAvailable());
   }
}
