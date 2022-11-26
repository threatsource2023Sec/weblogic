package weblogic.security.service;

import com.bea.common.logger.spi.LoggableMessageSpi;
import com.bea.common.logger.spi.LoggerSpi;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Logger;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.WLLevel;
import weblogic.logging.WLLogRecord;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class CommonSecurityLoggerSpiImpl implements LoggerSpi {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String PREFIX_DELIM = "-";
   private static final String SECURITYSUBSYSTEM = "Security";
   private Logger logger = null;
   private DebugLogger debugLogger = null;

   private CommonSecurityLoggerSpiImpl() {
   }

   private void run(PrivilegedAction action) {
      SecurityServiceManager.runAs(kernelId, kernelId, action);
   }

   public CommonSecurityLoggerSpiImpl(Logger logger, DebugLogger debugLogger) {
      this.logger = logger;
      this.debugLogger = debugLogger;
   }

   public boolean isDebugEnabled() {
      return this.debugLogger != null ? this.debugLogger.isDebugEnabled() : false;
   }

   public void debug(final Object msg) {
      if (msg != null) {
         if (this.debugLogger != null) {
            this.run(new PrivilegedAction() {
               public Object run() {
                  CommonSecurityLoggerSpiImpl.this.debugLogger.debug(msg.toString());
                  return null;
               }
            });
         }

      }
   }

   public void debug(final Object msg, final Throwable th) {
      if (this.debugLogger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               if (msg == null) {
                  CommonSecurityLoggerSpiImpl.this.debugLogger.debug("", th);
                  return null;
               } else {
                  CommonSecurityLoggerSpiImpl.this.debugLogger.debug(msg.toString(), th);
                  return null;
               }
            }
         });
      }

   }

   public void info(final Object msg) {
      if (this.debugLogger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               CommonSecurityLoggerSpiImpl.this.logMessage(msg, WLLevel.INFO, (Throwable)null);
               return null;
            }
         });
      }

   }

   public void info(final Object msg, final Throwable th) {
      if (this.debugLogger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               CommonSecurityLoggerSpiImpl.this.logMessage(msg, WLLevel.INFO, th);
               return null;
            }
         });
      }

   }

   public void warn(final Object msg) {
      if (this.debugLogger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               CommonSecurityLoggerSpiImpl.this.logMessage(msg, WLLevel.WARNING, (Throwable)null);
               return null;
            }
         });
      }

   }

   public void warn(final Object msg, final Throwable th) {
      if (this.debugLogger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               CommonSecurityLoggerSpiImpl.this.logMessage(msg, WLLevel.WARNING, th);
               return null;
            }
         });
      }

   }

   public void error(final Object msg) {
      if (this.debugLogger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               CommonSecurityLoggerSpiImpl.this.logMessage(msg, WLLevel.ERROR, (Throwable)null);
               return null;
            }
         });
      }

   }

   public void error(final Object msg, final Throwable th) {
      if (this.debugLogger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               CommonSecurityLoggerSpiImpl.this.logMessage(msg, WLLevel.ERROR, th);
               return null;
            }
         });
      }

   }

   public void severe(final Object msg) {
      if (this.debugLogger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               CommonSecurityLoggerSpiImpl.this.logMessage(msg, WLLevel.CRITICAL, (Throwable)null);
               return null;
            }
         });
      }

   }

   public void severe(final Object msg, final Throwable th) {
      if (this.debugLogger != null) {
         this.run(new PrivilegedAction() {
            public Object run() {
               CommonSecurityLoggerSpiImpl.this.logMessage(msg, WLLevel.CRITICAL, th);
               return null;
            }
         });
      }

   }

   private void logMessage(Object msg, WLLevel level, Throwable th) {
      if (this.logger != null && msg != null) {
         String messageText = null;
         String subsystem = null;
         String messageId = null;
         LoggableMessageSpi logRecord;
         if (msg instanceof LoggableMessageSpi) {
            logRecord = (LoggableMessageSpi)msg;
            subsystem = logRecord.getSubsystem();
            messageId = logRecord.getPrefix() + "-" + logRecord.getMessageId();
            messageText = logRecord.getFormattedMessageBody();
         } else {
            subsystem = "Security";
            messageText = msg.toString();
         }

         logRecord = null;
         WLLogRecord logRecord;
         if (th == null) {
            logRecord = new WLLogRecord(level, messageText);
         } else {
            logRecord = new WLLogRecord(level, messageText, th);
         }

         logRecord.setLoggerName(subsystem);
         if (messageId != null) {
            logRecord.setId(messageId);
         }

         this.logger.log(logRecord);
      }

   }
}
