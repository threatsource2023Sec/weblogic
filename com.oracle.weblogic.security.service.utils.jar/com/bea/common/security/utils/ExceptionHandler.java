package com.bea.common.security.utils;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

public class ExceptionHandler {
   public static void handleLoginException(LoggerSpi logger, String message, Exception e) throws LoginException {
      if (logger != null && logger.isDebugEnabled()) {
         logger.debug(message);
      }

      if (e != null) {
         throw new LoginException(SecurityLogger.getUserLoginFailureGeneral() + " " + e);
      } else {
         throw new LoginException(SecurityLogger.getUserLoginFailureGeneral());
      }
   }

   public static void handleFailedLoginException(LoggerSpi logger, String message, Exception e) throws LoginException {
      if (logger != null && logger.isDebugEnabled()) {
         logger.debug(message);
      }

      if (e != null) {
         throw new FailedLoginException(SecurityLogger.getUserLoginFailureGeneral() + " " + e);
      } else {
         throw new FailedLoginException(SecurityLogger.getUserLoginFailureGeneral());
      }
   }

   public static void throwFailedLoginException(LoggerSpi logger, String message, boolean propagateCauseForLoginException, Exception exception, Throwable cause) throws LoginException {
      if (logger != null && logger.isDebugEnabled()) {
         logger.debug(message);
      }

      FailedLoginException e = new FailedLoginException(SecurityLogger.getUserLoginFailureGeneral() + " " + exception);
      if (propagateCauseForLoginException && cause != null) {
         e.initCause(cause);
      }

      throw e;
   }
}
