package weblogic.security.notshared;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.logging.Logger;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.WLLevel;
import weblogic.security.SecurityEnvironment;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.shared.LoggerAdapter;

public class LoggerAdapterImpl implements LoggerAdapter {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String WLS_PACKAGE = "weblogic.security.providers.";
   private static final int WLS_PACKAGE_OFFSET = "weblogic.security.providers.".length();
   private static final String WLES_PACKAGE = "com.bea.security.providers.";
   private static final int WLES_PACKAGE_OFFSET = "com.bea.security.providers.".length();
   private HashMap loggers = null;
   private boolean appletEnvironment = false;

   public LoggerAdapterImpl() {
      this.convertLegacyDebugSystemProperties();
      this.loggers = new HashMap();
   }

   private void run(PrivilegedAction action) {
      SecurityServiceManager.runAs(kernelId, kernelId, action);
   }

   public boolean isDebugEnabled(Object theLoggerHolder) {
      DebugLogger debugLogger = this.getDebugLogger(theLoggerHolder);
      if (debugLogger == null) {
         Logger logger = this.getLogger(theLoggerHolder);
         return logger == null ? false : logger.isLoggable(WLLevel.DEBUG);
      } else {
         return debugLogger.isDebugEnabled();
      }
   }

   private void convertLegacyDebugSystemProperties() {
      this.mapOldSecurityDebugProperty("ssl.debug", "DebugSecuritySSL");
      this.mapOldSecurityDebugProperty("weblogic.security.SSL.verbose", "DebugSecuritySSL");
      this.mapOldSecurityDebugProperty("weblogic.security.ssl.verbose", "DebugSecuritySSL");
      this.mapOldSecurityDebugProperty("ssl.debugEaten", "DebugSecuritySSLEaten");
      this.mapOldSecurityDebugProperty("weblogic.security.SSL.debugEaten", "DebugSecuritySSLEaten");
      this.mapOldSecurityDebugProperty("weblogic.security.ssl.debugEaten", "DebugSecuritySSLEaten");
      this.mapOldSecurityDebugProperty("weblogic.debug.CertRevocCheck", "DebugCertRevocCheck");
      this.mapOldSecurityDebugProperty("weblogic.security.realm.debug", "DebugSecurityRealm");
   }

   private void mapOldSecurityDebugProperty(String oldName, String newAttributeName) {
      try {
         String oldSetting = System.getProperty(oldName);
         if (oldSetting == null) {
            return;
         }

         System.setProperty("weblogic.debug." + newAttributeName, oldSetting);
      } catch (SecurityException var4) {
         this.appletEnvironment = true;
      }

   }

   public synchronized Object getLogger(String name) {
      LoggerHolder theHolder = (LoggerHolder)this.loggers.get(name);
      if (theHolder != null) {
         return theHolder;
      } else {
         Logger logger = null;
         DebugLogger debugLogger = null;
         logger = this.getServerLogger();
         if (name != null) {
            if (name.startsWith("Security")) {
               debugLogger = this.getDebugLogger("Debug" + name);
            } else if (name.startsWith("Debug")) {
               debugLogger = this.getDebugLogger(name);
            } else {
               String trimmed;
               if (name.startsWith("weblogic.security.providers.")) {
                  trimmed = name.substring(WLS_PACKAGE_OFFSET);
                  if (trimmed != null) {
                     if (trimmed.startsWith("authentication")) {
                        debugLogger = this.getDebugLogger("DebugSecurityAtn");
                     } else if (trimmed.startsWith("authorization")) {
                        debugLogger = this.getDebugLogger("DebugSecurityAtz");
                     } else if (trimmed.startsWith("audit")) {
                        debugLogger = this.getDebugLogger("DebugSecurityAuditor");
                     } else if (trimmed.startsWith("credentials")) {
                        debugLogger = this.getDebugLogger("DebugSecurityCredMap");
                     } else if (trimmed.startsWith("pk")) {
                        debugLogger = this.getDebugLogger("DebugSecurityKeyStore");
                     } else if (trimmed.startsWith("profiles")) {
                        debugLogger = this.getDebugLogger("DebugSecurityProfiler");
                     } else if (trimmed.startsWith("saml")) {
                        debugLogger = this.getDebugLogger("DebugSecurityAtn");
                     }
                  }
               } else if (name.startsWith("com.bea.security.providers.")) {
                  trimmed = name.substring(WLES_PACKAGE_OFFSET);
                  if (trimmed != null) {
                     if (trimmed.startsWith("authentication")) {
                        debugLogger = this.getDebugLogger("DebugSecurityAtn");
                     } else if (trimmed.startsWith("authorization")) {
                        debugLogger = this.getDebugLogger("DebugSecurityAtz");
                     } else if (trimmed.startsWith("audit")) {
                        debugLogger = this.getDebugLogger("DebugSecurityAuditor");
                     } else if (trimmed.startsWith("credentials")) {
                        debugLogger = this.getDebugLogger("DebugSecurityCredMap");
                     } else if (trimmed.startsWith("pk")) {
                        debugLogger = this.getDebugLogger("DebugSecurityKeyStore");
                     } else if (trimmed.startsWith("profiles")) {
                        debugLogger = this.getDebugLogger("DebugSecurityProfiler");
                     } else if (trimmed.startsWith("saml")) {
                        debugLogger = this.getDebugLogger("DebugSecurityAtn");
                     }
                  }
               } else if (name.startsWith("weblogic.entitlement")) {
                  debugLogger = this.getDebugLogger("DebugSecurityEEngine");
               } else {
                  debugLogger = this.getDebugLogger(name);
               }
            }
         }

         if (logger != null || debugLogger != null) {
            theHolder = new LoggerHolder(logger, debugLogger);
            this.loggers.put(name, theHolder);
         }

         return theHolder;
      }
   }

   private Logger getServerLogger() {
      if (this.appletEnvironment) {
         return null;
      } else {
         try {
            return SecurityEnvironment.getSecurityEnvironment().getServerLogger();
         } catch (SecurityException var2) {
            return null;
         }
      }
   }

   private DebugLogger getDebugLogger(String name) {
      if (this.appletEnvironment) {
         return null;
      } else {
         try {
            return DebugLogger.getDebugLogger(name);
         } catch (SecurityException var3) {
            return null;
         }
      }
   }

   public void debug(Object theLoggerHolder, final Object msg) {
      final DebugLogger debugLogger = this.getDebugLogger(theLoggerHolder);
      if (debugLogger == null) {
         final Logger logger = this.getLogger(theLoggerHolder);
         if (logger != null) {
            this.run(new PrivilegedAction() {
               public Object run() {
                  logger.log(WLLevel.DEBUG, msg.toString());
                  return null;
               }
            });
         }

      } else {
         this.run(new PrivilegedAction() {
            public Object run() {
               debugLogger.debug(msg.toString());
               return null;
            }
         });
      }
   }

   public void debug(Object theLoggerHolder, final Object msg, final Throwable th) {
      final DebugLogger debugLogger = this.getDebugLogger(theLoggerHolder);
      if (debugLogger == null) {
         final Logger logger = this.getLogger(theLoggerHolder);
         if (logger != null) {
            this.run(new PrivilegedAction() {
               public Object run() {
                  logger.log(WLLevel.DEBUG, msg.toString(), th);
                  return null;
               }
            });
         }

      } else {
         this.run(new PrivilegedAction() {
            public Object run() {
               debugLogger.debug(msg.toString(), th);
               return null;
            }
         });
      }
   }

   public void info(Object theLoggerHolder, final Object msg) {
      final Logger logger = this.getLogger(theLoggerHolder);
      if (logger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               logger.log(WLLevel.INFO, msg.toString());
               return null;
            }
         });
      }

   }

   public void info(Object theLoggerHolder, final Object msg, final Throwable th) {
      final Logger logger = this.getLogger(theLoggerHolder);
      if (logger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               logger.log(WLLevel.INFO, msg.toString(), th);
               return null;
            }
         });
      }

   }

   public void warn(Object theLoggerHolder, final Object msg) {
      final Logger logger = this.getLogger(theLoggerHolder);
      if (logger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               logger.log(WLLevel.WARNING, msg.toString());
               return null;
            }
         });
      }

   }

   public void warn(Object theLoggerHolder, final Object msg, final Throwable th) {
      final Logger logger = this.getLogger(theLoggerHolder);
      if (logger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               logger.log(WLLevel.WARNING, msg.toString(), th);
               return null;
            }
         });
      }

   }

   public void error(Object theLoggerHolder, final Object msg) {
      final Logger logger = this.getLogger(theLoggerHolder);
      if (logger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               logger.log(WLLevel.ERROR, msg.toString());
               return null;
            }
         });
      }

   }

   public void error(Object theLoggerHolder, final Object msg, final Throwable th) {
      final Logger logger = this.getLogger(theLoggerHolder);
      if (logger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               logger.log(WLLevel.ERROR, msg.toString(), th);
               return null;
            }
         });
      }

   }

   public void severe(Object theLoggerHolder, final Object msg) {
      final Logger logger = this.getLogger(theLoggerHolder);
      if (logger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               logger.log(WLLevel.CRITICAL, msg.toString());
               return null;
            }
         });
      }

   }

   public void severe(Object theLoggerHolder, final Object msg, final Throwable th) {
      final Logger logger = this.getLogger(theLoggerHolder);
      if (logger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               logger.log(WLLevel.CRITICAL, msg.toString(), th);
               return null;
            }
         });
      }

   }

   private final DebugLogger getDebugLogger(Object theLoggerHolder) {
      return theLoggerHolder == null ? null : ((LoggerHolder)((LoggerHolder)theLoggerHolder)).getDebugLogger();
   }

   private final Logger getLogger(Object theLoggerHolder) {
      return theLoggerHolder == null ? null : ((LoggerHolder)((LoggerHolder)theLoggerHolder)).getLogger();
   }

   private class LoggerHolder {
      private Logger logger = null;
      private DebugLogger debugLogger = null;

      private LoggerHolder() {
      }

      public LoggerHolder(Logger theLogger, DebugLogger theDebugLogger) {
         this.logger = theLogger;
         this.debugLogger = theDebugLogger;
      }

      public final Logger getLogger() {
         return this.logger;
      }

      public final DebugLogger getDebugLogger() {
         return this.debugLogger;
      }
   }
}
