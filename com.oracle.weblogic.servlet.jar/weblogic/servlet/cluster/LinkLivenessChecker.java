package weblogic.servlet.cluster;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Set;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.jndi.Environment;
import weblogic.management.configuration.ClusterMBean;
import weblogic.rmi.extensions.server.RemoteDomainSecurityHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.servlet.cluster.wan.BatchedSessionState;
import weblogic.servlet.cluster.wan.PersistenceServiceInternal;
import weblogic.servlet.cluster.wan.ServiceUnavailableException;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

public class LinkLivenessChecker implements TimerListener {
   private static final long SUSPEND_TIMEOUT = 5000L;
   private final String remoteClusterURL;
   private PersistenceServiceInternal service;
   private boolean timerStopped;
   private final TimerManager timerManager;
   private final ClusterMBean clusterMBean;
   private boolean requiresValidSubject;

   LinkLivenessChecker(String url, ClusterMBean clusterMBean) {
      this(url, clusterMBean, false);
   }

   LinkLivenessChecker(String url, ClusterMBean clusterMBean, boolean requiresValidSubject) {
      this.timerStopped = true;
      this.remoteClusterURL = url;
      this.clusterMBean = clusterMBean;
      this.requiresValidSubject = requiresValidSubject;
      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("commLinkManager");
   }

   PersistenceServiceInternal getRemotePersistenceService() {
      return this.service;
   }

   void stop() {
      synchronized(this.timerManager) {
         if (!this.timerStopped) {
            try {
               this.timerManager.waitForSuspend(5000L);
            } catch (InterruptedException var4) {
            }

            this.timerStopped = true;
         }
      }
   }

   void resume() {
      synchronized(this.timerManager) {
         if (this.timerStopped) {
            this.timerManager.resume();
            this.timerManager.schedule(this, (long)this.clusterMBean.getInterClusterCommLinkHealthCheckInterval());
            this.timerStopped = false;
         }

      }
   }

   private void lookupRemotePersistenceService() {
      int interval;
      try {
         this.service = LinkLivenessChecker.SecurePersistenceServiceImpl.getInstance(this.remoteClusterURL, this.requiresValidSubject);
         if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            String msg = this.service != null ? "Successfully looked up" : "Failed to look up";
            WANReplicationDetailsDebugLogger.debug(msg + " Persistence Service from the remote cluster " + this.service);
         }

         if (this.service == null) {
            int interval = this.clusterMBean.getInterClusterCommLinkHealthCheckInterval();
            this.timerManager.schedule(this, (long)interval);
         }
      } catch (NamingException var3) {
         if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            WANReplicationDetailsDebugLogger.debug("Failed to get initial context " + this.remoteClusterURL);
         }

         interval = this.clusterMBean.getInterClusterCommLinkHealthCheckInterval();
         this.timerManager.schedule(this, (long)interval);
      } catch (IOException var4) {
         if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            WANReplicationDetailsDebugLogger.debug("Failed to get initial context " + this.remoteClusterURL);
         }

         interval = this.clusterMBean.getInterClusterCommLinkHealthCheckInterval();
         this.timerManager.schedule(this, (long)interval);
      }

   }

   public void timerExpired(Timer timer) {
      this.lookupRemotePersistenceService();
   }

   private static class SecurePersistenceServiceImpl implements PersistenceServiceInternal {
      private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      private final PersistenceServiceInternal delegate;
      private final String remoteURL;
      private final AuthenticatedSubject subject;

      static PersistenceServiceInternal getInstance(final String remoteClusterURL, boolean requiresValidSubject) throws NamingException, IOException {
         AuthenticatedSubject subject = RemoteDomainSecurityHelper.getSubject(remoteClusterURL);
         if (WANReplicationDetailsDebugLogger.isDebugEnabled()) {
            WANReplicationDetailsDebugLogger.debug("SecurePersistenceServiceImpl.getInstance  subject for " + remoteClusterURL + " is " + subject);
         }

         if (subject == null) {
            return requiresValidSubject ? null : lookup(remoteClusterURL);
         } else {
            PrivilegedExceptionAction action = new PrivilegedExceptionAction() {
               public Object run() {
                  try {
                     return LinkLivenessChecker.SecurePersistenceServiceImpl.lookup(remoteClusterURL);
                  } catch (NamingException var2) {
                     return var2;
                  }
               }
            };

            try {
               Object result = WebServerRegistry.getInstance().getSecurityProvider().runAs(subject, action, KERNEL_ID);
               if (result instanceof NamingException) {
                  throw (NamingException)result;
               } else {
                  return new SecurePersistenceServiceImpl((PersistenceServiceInternal)result, remoteClusterURL, subject);
               }
            } catch (PrivilegedActionException var5) {
               throw new AssertionError(var5);
            }
         }
      }

      private static PersistenceServiceInternal lookup(String remoteClusterURL) throws NamingException {
         Environment env = new Environment();
         env.setProviderUrl(remoteClusterURL);
         Context ctx = env.getInitialContext();
         return (PersistenceServiceInternal)ctx.lookup("weblogic/servlet/wan/persistenceservice");
      }

      private SecurePersistenceServiceImpl(PersistenceServiceInternal delegate, String remoteClusterURL, AuthenticatedSubject subject) {
         this.delegate = delegate;
         this.remoteURL = remoteClusterURL;
         this.subject = subject;
      }

      public void persistState(final BatchedSessionState state) throws ServiceUnavailableException, RemoteException {
         if (this.subject == null) {
            this.delegate.persistState(state);
         } else {
            execute(new PrivilegedExceptionAction() {
               public Object run() {
                  try {
                     SecurePersistenceServiceImpl.this.delegate.persistState(state);
                     return null;
                  } catch (RemoteException var2) {
                     return var2;
                  }
               }
            }, this.subject);
         }

      }

      public void invalidateSessions(final Set set) throws RemoteException {
         if (this.subject == null) {
            this.delegate.invalidateSessions(set);
         } else {
            execute(new PrivilegedExceptionAction() {
               public Object run() {
                  try {
                     SecurePersistenceServiceImpl.this.delegate.invalidateSessions(set);
                     return null;
                  } catch (RemoteException var2) {
                     return var2;
                  }
               }
            }, this.subject);
         }

      }

      private static void execute(PrivilegedExceptionAction action, AuthenticatedSubject subject) throws RemoteException {
         try {
            RemoteException re = (RemoteException)WebServerRegistry.getInstance().getSecurityProvider().runAs(subject, action, KERNEL_ID);
            if (re != null) {
               throw re;
            }
         } catch (PrivilegedActionException var3) {
            throw new AssertionError(var3);
         }
      }

      private static AuthenticatedSubject getSubject(String remoteURL) {
         try {
            return RemoteDomainSecurityHelper.getSubject(remoteURL);
         } catch (IOException var2) {
            return null;
         }
      }
   }
}
