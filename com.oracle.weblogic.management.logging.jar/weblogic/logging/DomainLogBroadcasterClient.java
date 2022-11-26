package weblogic.logging;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jndi.Environment;
import weblogic.management.configuration.LogMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.logging.DomainLogHandler;
import weblogic.management.logging.DomainLogHandlerImpl;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Service
public class DomainLogBroadcasterClient implements DomainLogBroadcasterClientService {
   private static final int MAX_BUFFER_SIZE = 100;
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDomainLogHandler");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private static final int MIN_RETRY_COUNT = 1;
   private static final int MAX_RETRY_COUNT = 10;
   private static final long RETRY_SLEEP_INTERVAL = 5000L;
   private ArrayList logBuffer;
   private LogMBean logConfig;
   private DomainLogHandler domainLogHandler = null;
   private long messagesBroadcastedToDomain = 0L;
   boolean closed = false;
   private WorkManager workManager;
   private Object logBufferLock = new Object();

   /** @deprecated */
   @Deprecated
   public static DomainLogBroadcasterClient getInstance() {
      return DomainLogBroadcasterClient.DomainLogBroadcasterClientInitializer.singleton;
   }

   private DomainLogBroadcasterClient() {
      ServerMBean localServer = ManagementService.getRuntimeAccess(KERNEL_ID).getServer();
      this.logConfig = localServer.getLog();
      this.logBuffer = new ArrayList(100);
      this.workManager = WorkManagerFactory.getInstance().findOrCreate("weblogic.logging.DomainLogBroadcasterClient", 1, -1);
   }

   public void initDomainLogHandler(final boolean pingAdmin) {
      this.workManager.schedule(new Runnable() {
         public void run() {
            DomainLogBroadcasterClient.this.initDomainLogHandlerAsync(pingAdmin);
         }
      });
   }

   public DomainLogHandler getDomainLogHandler() {
      return this.domainLogHandler;
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private synchronized void initDomainLogHandlerAsync(boolean pingAdmin) {
      int retryCount = 1;
      if (pingAdmin) {
         retryCount = 10;
      }

      if (this.domainLogHandler != null && pingAdmin) {
         try {
            this.domainLogHandler.ping();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Ping worked, no need to lookup again");
            }

            return;
         } catch (RemoteException var21) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Remote reference is bad");
            }

            this.domainLogHandler = null;
         }
      }

      Exception ctxLookupException = null;
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Entering loop with retryCount=" + retryCount);
      }

      for(int i = 0; i < retryCount; ++i) {
         Context ctx = null;

         try {
            try {
               if (ManagementService.getRuntimeAccess(KERNEL_ID).isAdminServer()) {
                  this.domainLogHandler = DomainLogHandlerImpl.getInstance();
               } else {
                  Environment env = new Environment();
                  env.setProviderUrl(getURLManagerService().findAdministrationURL(ManagementService.getRuntimeAccess(KERNEL_ID).getAdminServerName()));
                  ctx = env.getInitialContext();
                  this.domainLogHandler = (DomainLogHandler)ctx.lookup("weblogic.logging.DomainLogHandler");
               }

               LogMgmtLogger.logDomainLogHandlerInitialized();
               break;
            } catch (Exception var19) {
               ctxLookupException = var19;
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Got exception while ctx lookup, retryCount=" + i, var19);
               }
            }

            try {
               Thread.currentThread();
               Thread.sleep(5000L);
            } catch (InterruptedException var18) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Got exception while sleeping", var18);
               }
            }
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var17) {
               }
            }

         }
      }

      if (this.domainLogHandler == null) {
         LogMgmtLogger.logCannotGetDomainLogHandler(ctxLookupException);
      } else {
         this.flush();
      }

   }

   public void broadcast(LogEntry logEntry) {
      ArrayList flushBuffer;
      synchronized(this.logBufferLock) {
         if (this.closed) {
            return;
         }

         this.logBuffer.add(logEntry);
         int limit = this.logConfig.getDomainLogBroadcasterBufferSize();
         if (this.domainLogHandler == null) {
            limit = 100;
         }

         if (this.logBuffer.size() >= limit) {
            if (this.domainLogHandler != null) {
               flushBuffer = this.logBuffer;
               this.logBuffer = new ArrayList(this.logConfig.getDomainLogBroadcasterBufferSize());
            } else {
               if (this.logBuffer.size() > limit) {
                  this.logBuffer.remove(0);
               }

               flushBuffer = null;
            }
         } else {
            flushBuffer = null;
         }
      }

      if (flushBuffer != null) {
         this.scheduleLogBroadcast(flushBuffer);
      }

   }

   public void flush() {
      ArrayList flushBuffer;
      synchronized(this.logBufferLock) {
         int size = this.logBuffer.size();
         if (size == 0) {
            return;
         }

         flushBuffer = this.logBuffer;
         this.logBuffer = new ArrayList(this.logConfig.getDomainLogBroadcasterBufferSize());
      }

      this.scheduleLogBroadcast(flushBuffer);
   }

   private void scheduleLogBroadcast(final ArrayList flushBuffer) {
      this.workManager.schedule(new Runnable() {
         public void run() {
            DomainLogHandler localDomainLogHandler = DomainLogBroadcasterClient.this.domainLogHandler;
            LogEntry[] entries = new LogEntry[flushBuffer.size()];
            flushBuffer.toArray(entries);

            try {
               if (localDomainLogHandler != null) {
                  localDomainLogHandler.publishLogEntries(entries);
               }
            } catch (RemoteException var8) {
               try {
                  if (localDomainLogHandler != null) {
                     localDomainLogHandler.publishLogEntries(entries);
                  }
               } catch (RemoteException var7) {
                  DomainLogBroadcasterClient.this.domainLogHandler = null;
                  LogMgmtLogger.logCannotGetDomainLogHandler(var8);
                  if (DomainLogBroadcasterClient.DEBUG.isDebugEnabled()) {
                     StringBuilder sb = new StringBuilder();

                     for(int j = 0; j < entries.length; ++j) {
                        sb.append('[');
                        sb.append(entries[j].getLogMessage());
                        sb.append(']');
                     }

                     DomainLogBroadcasterClient.DEBUG.debug("Failed to send messages to the domain: " + sb.toString(), var8);
                  }
               }
            }

         }
      });
   }

   public void close() {
      synchronized(this.logBufferLock) {
         this.closed = true;
         this.logBuffer.clear();
      }
   }

   public void sendALAlertTrap(final String trapType, final String severity, final String domainName, final String serverName, final String alertId, final String ruleId, final String ruleName, final String ruleCondition, final String timeStamp, final String annotation, final String serviceName, final String servicePath) throws Exception {
      try {
         final DomainLogHandler localDomainLogHandler = this.domainLogHandler;
         if (localDomainLogHandler != null) {
            this.workManager.schedule(new Runnable() {
               public void run() {
                  try {
                     localDomainLogHandler.sendALAlertTrap(trapType, severity, domainName, serverName, alertId, ruleId, ruleName, ruleCondition, timeStamp, annotation, serviceName, servicePath);
                  } catch (RemoteException var2) {
                     if (DomainLogBroadcasterClient.DEBUG.isDebugEnabled()) {
                        DomainLogBroadcasterClient.DEBUG.debug("Failed to send AquaLogic Alert trap to the domain", var2);
                     }
                  }

               }
            });
         } else {
            Loggable l = LogMgmtLogger.logDomainLogHandlerNotAvailableForTrapLoggable();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug(l.getMessageBody());
            }

            throw new Exception(l.getMessageBody());
         }
      } catch (RemoteException var15) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Failed to send AquaLogic Alert trap to the domain", var15);
         }

         throw new Exception(var15.getMessage());
      }
   }

   public void sendTrap(final String trapName, final List varBindings) throws RemoteException {
      final DomainLogHandler localDomainLogHandler = this.domainLogHandler;
      if (localDomainLogHandler != null) {
         this.workManager.schedule(new Runnable() {
            public void run() {
               try {
                  if (DomainLogBroadcasterClient.DEBUG.isDebugEnabled()) {
                     DomainLogBroadcasterClient.DEBUG.debug("Sending trap " + trapName);
                  }

                  localDomainLogHandler.sendTrap(trapName, varBindings);
               } catch (RemoteException var2) {
                  if (DomainLogBroadcasterClient.DEBUG.isDebugEnabled()) {
                     DomainLogBroadcasterClient.DEBUG.debug("Failed to send trap to the domain", var2);
                  }
               }

            }
         });
      } else {
         Loggable l = LogMgmtLogger.logDomainLogHandlerNotAvailableForTrapLoggable();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug(l.getMessageBody());
         }

         throw new RemoteException(l.getMessageBody());
      }
   }

   private static final class DomainLogBroadcasterClientInitializer {
      private static final DomainLogBroadcasterClient singleton = (DomainLogBroadcasterClient)LocatorUtilities.getService(DomainLogBroadcasterClient.class);
   }
}
