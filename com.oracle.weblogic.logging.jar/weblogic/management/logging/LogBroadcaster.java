package weblogic.management.logging;

import java.security.AccessController;
import java.util.Iterator;
import java.util.List;
import javax.management.MBeanException;
import weblogic.logging.LogEntry;
import weblogic.management.ManagementException;
import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.runtime.LogBroadcasterRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.TxHelperService;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.collections.CopyOnWriteArrayList;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class LogBroadcaster extends RuntimeMBeanDelegate implements LogBroadcasterRuntimeMBean {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   static final String BASE_TYPE = "weblogic.log.";
   private static final long serialVersionUID = 7795992271907801150L;
   private static final boolean DEBUG = false;
   private long messagesLogged;
   private List generators;
   private WorkManager workManager;

   public static LogBroadcaster getLogBroadcaster() throws ManagementException {
      return LogBroadcaster.SingletonFactory.getSingleton();
   }

   private LogBroadcaster() throws ManagementException {
      super("TheLogBroadcaster");
      this.generators = new CopyOnWriteArrayList();
      this.workManager = WorkManagerFactory.getInstance().findOrCreate("weblogic.logging.LogBroadcaster", 1, -1);
   }

   void addNotificationGenerator(NotificationGenerator ng) {
      this.generators.add(ng);
   }

   public long getMessagesLogged() {
      return this.messagesLogged;
   }

   public void translateLogEntry(final LogEntry logEntry) {
      if (logEntry.getSeverity() < 128) {
         if (this.isTranslationNeeded()) {
            String userId = logEntry.getUserId();
            if (userId == null) {
               userId = "";
            }

            TxHelperService txHelper = (TxHelperService)LocatorUtilities.getService(TxHelperService.class);
            String txId = txHelper.getTransactionId();
            if (txId == null) {
               txId = "";
            }

            final String type = "weblogic.log." + logEntry.getSubsystem() + "." + logEntry.getId();
            ++this.messagesLogged;
            final Iterator i = this.generators.iterator();
            this.workManager.schedule(new Runnable() {
               public void run() {
                  while(i.hasNext()) {
                     NotificationGenerator ng = (NotificationGenerator)i.next();
                     if (ng.isSubscribed()) {
                        WebLogicLogNotification notification = new WebLogicLogNotification(type, LogBroadcaster.this.messagesLogged, ng.getObjectName(), logEntry);

                        try {
                           ng.sendNotification(notification);
                           ng.incrementSequenceNumber();
                        } catch (MBeanException var4) {
                        }
                     }
                  }

               }
            });
         }
      }
   }

   private boolean isTranslationNeeded() {
      boolean result = false;
      Iterator i = this.generators.iterator();

      while(i.hasNext()) {
         NotificationGenerator ng = (NotificationGenerator)i.next();
         if (ng.isSubscribed()) {
            result = true;
         }
      }

      return result;
   }

   // $FF: synthetic method
   LogBroadcaster(Object x0) throws ManagementException {
      this();
   }

   private static final class SingletonFactory {
      private static LogBroadcaster singleton = null;

      private static LogBroadcaster getSingleton() throws ManagementException {
         if (singleton == null) {
            singleton = new LogBroadcaster();
         }

         return singleton;
      }
   }
}
