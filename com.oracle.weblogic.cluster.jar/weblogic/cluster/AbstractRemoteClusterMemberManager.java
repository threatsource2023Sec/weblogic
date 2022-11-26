package weblogic.cluster;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.jndi.Environment;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.ManagementService;
import weblogic.rmi.extensions.server.RemoteDomainSecurityHelper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.StopTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

public abstract class AbstractRemoteClusterMemberManager implements NakedTimerListener, StopTimerListener, RemoteClusterMemberManager {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final ClusterMBean cluster;
   private final Environment env;
   private final ArrayList listeners;
   private int leaseRenewInterval;
   private long version;
   private boolean loggedMessageOnce;
   private RemoteClusterHealthChecker healthChecker;
   private TimerManager timerFactory;
   private ArrayList currentCandidates;
   private String remoteClusterURL;
   protected boolean crossDomainSecurityNeeded;
   private boolean isStarted = false;

   protected AbstractRemoteClusterMemberManager() {
      this.cluster = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      this.env = new Environment();
      this.listeners = new ArrayList();
      if (this.cluster != null) {
         this.leaseRenewInterval = this.cluster.getHealthCheckIntervalMillis();
         this.timerFactory = TimerManagerFactory.getTimerManagerFactory().getTimerManager("RemoteClusterMemberManager");
      } else {
         this.timerFactory = null;
         this.leaseRenewInterval = 0;
      }

   }

   public void start() {
      if (!this.isStarted) {
         this.isStarted = true;
         this.checkClusterMembership();
         if (this.timerFactory != null) {
            this.timerFactory.schedule(this, (long)this.leaseRenewInterval, (long)this.leaseRenewInterval);
         }

      }
   }

   public synchronized void addRemoteClusterMemberListener(RemoteClusterMembersChangeListener listener) {
      this.listeners.add(listener);
      if (this.currentCandidates != null) {
         ArrayList list = new ArrayList(1);
         list.add(listener);
         this.fireMembershipChangeEvent(list, this.currentCandidates);
      }

   }

   public synchronized void removeRemoteClusterMemberListener(RemoteClusterMembersChangeListener listener) {
      this.listeners.remove(listener);
   }

   public void stop() {
      if (this.timerFactory != null) {
         this.timerFactory.stop();
         this.timerFactory = null;
      }

      this.leaseRenewInterval = 0;
      this.isStarted = false;
   }

   public void timerStopped(Timer t) {
   }

   public void timerExpired(Timer t) {
      this.checkClusterMembership();
   }

   private void checkClusterMembership() {
      ArrayList response = null;
      RemoteClusterHealthChecker checker = this.getHealthChecker();
      ArrayList listenersClone = null;

      try {
         if (checker == null) {
            return;
         }

         synchronized(this) {
            listenersClone = (ArrayList)this.listeners.clone();
         }

         response = checker.checkClusterMembership(this.version);
         if (response != null) {
            this.currentCandidates = response;
            this.version = 0L;

            for(int i = 0; i < response.size(); ++i) {
               ClusterMemberInfo info = (ClusterMemberInfo)response.get(i);
               this.version += (long)info.identity().hashCode();
            }

            this.fireMembershipChangeEvent(listenersClone, response);
         }
      } catch (RemoteException var7) {
         this.currentCandidates = null;
         this.healthChecker = null;
         this.version = 0L;
         this.fireMembershipChangeEvent(listenersClone, new ArrayList());
      }

   }

   private void fireMembershipChangeEvent(ArrayList listeners, ArrayList change) {
      for(int i = 0; i < listeners.size(); ++i) {
         RemoteClusterMembersChangeListener changeListener = (RemoteClusterMembersChangeListener)listeners.get(i);
         changeListener.remoteClusterMembersChanged(change);
      }

   }

   public void setRemoteClusterURL(String url) {
      this.remoteClusterURL = url;
   }

   public void setCrossDomainSecurityNeeded(boolean isCrossDomain) {
      this.crossDomainSecurityNeeded = isCrossDomain;
   }

   private RemoteClusterHealthChecker getHealthChecker() {
      if (this.healthChecker != null) {
         return this.healthChecker;
      } else {
         if (this.remoteClusterURL == null) {
            this.remoteClusterURL = this.cluster.getRemoteClusterAddress();
         }

         if (this.remoteClusterURL == null) {
            return null;
         } else if (!ServerHelper.isURLValid(this.remoteClusterURL)) {
            if (!this.loggedMessageOnce) {
               ClusterLogger.logIncorrectRemoteClusterAddress(this.remoteClusterURL);
               this.loggedMessageOnce = true;
            }

            return null;
         } else {
            try {
               AuthenticatedSubject subject = RemoteDomainSecurityHelper.getSubject(this.remoteClusterURL);
               if (subject == null) {
                  subject = SubjectUtils.getAnonymousSubject();
                  this.healthChecker = this.lookup(subject, this.remoteClusterURL);
               } else {
                  this.healthChecker = this.lookup(subject, this.remoteClusterURL);
                  if (this.healthChecker != null && this.crossDomainSecurityNeeded) {
                     this.healthChecker = new SecureClusterHealthChecker(this.healthChecker, subject);
                  }
               }

               return this.healthChecker;
            } catch (IOException var2) {
               return null;
            }
         }
      }
   }

   private RemoteClusterHealthChecker lookup(AuthenticatedSubject subject, final String url) throws RemoteException {
      try {
         Object obj = SecurityManager.runAs(kernelId, subject, new PrivilegedExceptionAction() {
            public Object run() {
               Context ctx = null;

               Object var3;
               try {
                  AbstractRemoteClusterMemberManager.this.env.setProviderUrl(url);
                  AbstractRemoteClusterMemberManager.this.env.setConnectionTimeout((long)(AbstractRemoteClusterMemberManager.this.cluster.getHealthCheckIntervalMillis() * AbstractRemoteClusterMemberManager.this.cluster.getIdlePeriodsUntilTimeout()));
                  ctx = AbstractRemoteClusterMemberManager.this.env.getInitialContext();
                  RemoteClusterHealthChecker var2 = (RemoteClusterHealthChecker)ctx.lookup("weblogic/cluster/RemoteClusterHealthChecker");
                  return var2;
               } catch (NamingException var13) {
                  var3 = null;
               } finally {
                  try {
                     if (ctx != null) {
                        ctx.close();
                     }
                  } catch (NamingException var12) {
                  }

               }

               return var3;
            }
         });
         if (obj != null) {
            if (obj instanceof RemoteException) {
               throw (RemoteException)obj;
            } else {
               return (RemoteClusterHealthChecker)obj;
            }
         } else {
            return null;
         }
      } catch (PrivilegedActionException var4) {
         throw new AssertionError(var4);
      }
   }

   private static class SecureClusterHealthChecker implements RemoteClusterHealthChecker {
      private final RemoteClusterHealthChecker delegate;
      private final AuthenticatedSubject subject;

      private SecureClusterHealthChecker(RemoteClusterHealthChecker delegate, AuthenticatedSubject subject) {
         this.delegate = delegate;
         this.subject = subject;
      }

      public ArrayList checkClusterMembership(final long version) throws RemoteException {
         if (this.subject == null) {
            return this.delegate.checkClusterMembership(version);
         } else {
            try {
               Object obj = SecurityManager.runAs(AbstractRemoteClusterMemberManager.kernelId, this.subject, new PrivilegedExceptionAction() {
                  public Object run() {
                     try {
                        return SecureClusterHealthChecker.this.delegate.checkClusterMembership(version);
                     } catch (RemoteException var2) {
                        return var2;
                     }
                  }
               });
               if (obj instanceof RemoteException) {
                  throw (RemoteException)obj;
               } else {
                  return (ArrayList)obj;
               }
            } catch (PrivilegedActionException var4) {
               throw new AssertionError(var4);
            }
         }
      }

      // $FF: synthetic method
      SecureClusterHealthChecker(RemoteClusterHealthChecker x0, AuthenticatedSubject x1, Object x2) {
         this(x0, x1);
      }
   }
}
