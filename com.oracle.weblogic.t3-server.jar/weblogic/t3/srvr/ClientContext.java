package weblogic.t3.srvr;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.security.AccessController;
import java.util.Hashtable;
import weblogic.common.T3Exception;
import weblogic.common.internal.T3ClientParams;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.Kernel;
import weblogic.kernel.T3SrvrLogger;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.PeerGoneEvent;
import weblogic.rjvm.PeerGoneListener;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMManager;
import weblogic.rjvm.RemoteInvokable;
import weblogic.rjvm.RemoteRequest;
import weblogic.rjvm.RequestStream;
import weblogic.rmi.internal.OIDManager;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.time.common.internal.TimeEventGenerator;
import weblogic.utils.AssertionError;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public final class ClientContext extends ExecutionContext implements RemoteClientContext, RemoteInvokable, Runnable, Scavengable, PeerGoneListener {
   private AuthenticatedUser user;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger debugConnection = DebugLogger.getDebugLogger("DebugConnection");
   private static Hashtable cliConTable = new Hashtable();
   private static final String NAME_SEPARATOR = ".";
   private static int nextIncarnation = 0;
   private static Hashtable ccNameTable = new Hashtable();
   private static Object tableLock = new Object();
   private RJVM rjvm = null;
   private String ccName;
   private byte QOS;
   private int idleCallbackID;
   private ClientMsg cm;
   private int ccID = -1;
   private long disconnectNoticed;
   private boolean hardDisconnectOccurred;
   private long lastWorkPerformed;
   private int workQueueDepth;
   private Object workQueueDepthLock = new Object();
   private boolean dead;
   private static int defHDTM = 0;
   private static int defSDTM = 0;
   private static int defISDTM = -1;

   private static void addEntry(String key, ClientContext cliCon) {
      cliConTable.put(key, cliCon);
   }

   private static void removeEntry(String key) {
      cliConTable.remove(key);
   }

   private static ClientContext findContext(String key) {
      return (ClientContext)cliConTable.get(key);
   }

   private static String makeWSIDSuffix() {
      String suffix = nextIncarnation + "." + TimeEventGenerator.getLaunch().getTime();
      ++nextIncarnation;
      return suffix;
   }

   public static ClientContext getClientContext(RJVM rjvm, String workspace, UserInfo t3u, int idleCallbackID, byte QOS) throws T3Exception {
      synchronized(tableLock) {
         ClientContext cc;
         if (workspace != null && !workspace.equals("")) {
            if (isWSID(workspace)) {
               cc = findContext(workspace);
               if (cc == null) {
                  throw new T3Exception("Attempt to connect to workspace: '" + workspace + "' which doesn't exist");
               }
            } else {
               String qualifiedName = t3u.getName() + "." + workspace;
               cc = (ClientContext)ccNameTable.get(qualifiedName);
               if (cc == null) {
                  cc = new ClientContext(qualifiedName, workspace, QOS);
                  ccNameTable.put(qualifiedName, cc);
               }
            }
         } else {
            cc = new ClientContext((String)null, (String)null, QOS);
         }

         cc.bind(rjvm, idleCallbackID);
         return cc;
      }
   }

   public int hashCode() {
      return this.ccID;
   }

   public boolean equals(Object o) {
      return o == this;
   }

   public AuthenticatedUser getUser() {
      return this.user;
   }

   public AuthenticatedSubject getSubject() {
      return SecurityServiceManager.getSealedSubjectFromWire(kernelId, this.getUser());
   }

   RJVM getRJVM() {
      return this.rjvm;
   }

   private ClientContext(String ccName, String wsName, byte QOS) throws T3Exception {
      super(makeWSIDSuffix());
      this.ccName = ccName;
      this.QOS = QOS;
      this.user = this.getCurrentSubject();
      this.cm = new ClientMsg();
      this.cm.wsName = wsName;
      this.cm.serverName = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
      this.disconnectNoticed = 0L;
      this.hardDisconnectOccurred = false;
      this.lastWorkPerformed = TimeEventGenerator.getCurrentMins() + 1L;
      this.workQueueDepth = 0;
      this.dead = false;
      addEntry(this.getID(), this);
      Scavenger.addScavengable(this.getID(), this);
      this.ccID = OIDManager.getInstance().getNextObjectID();
      RJVMManager.getLocalRJVM().getFinder().put(this.ccID, this);
   }

   private boolean isBound() {
      return this.rjvm != null;
   }

   private void bind(RJVM rjvm, int idleCallbackID) throws T3Exception {
      if (this.isBound()) {
         throw new T3Exception("Attempt to bind to ClientContext: '" + this + "' that is already bound");
      } else if (this.dead) {
         throw new T3Exception("Attempt to bind to ClientContext: '" + this + "' that is dead");
      } else {
         this.rjvm = rjvm;
         this.idleCallbackID = idleCallbackID;
         this.disconnectNoticed = 0L;
         this.hardDisconnectOccurred = false;
         rjvm.addPeerGoneListener(this);
      }
   }

   private void unbind() {
      if (!this.isBound()) {
         T3SrvrLogger.logAttemptUnbindUnboundClientContext(this.toString());
      } else {
         this.rjvm.removePeerGoneListener(this);
         this.rjvm = null;
         if (this.disconnectNoticed == 0L) {
            this.disconnectNoticed = TimeEventGenerator.getCurrentMins();
         }

      }
   }

   public String toString() {
      return "ClientContext - id: '" + this.getID() + "', bound: '" + this.isBound() + "', dead: '" + this.dead + "'";
   }

   void incWorkQueueDepth() {
      synchronized(this.workQueueDepthLock) {
         ++this.workQueueDepth;
      }

      this.lastWorkPerformed = TimeEventGenerator.getCurrentMins();
   }

   void decWorkQueueDepth() {
      synchronized(this.workQueueDepthLock) {
         --this.workQueueDepth;
      }
   }

   private void enqueueWork(final Runnable req) {
      if (this.dead) {
         throw new AssertionError("Connection: '" + this + "' attempted to enqueue work: '" + req + "' when it is dead.");
      } else {
         this.incWorkQueueDepth();
         if (SubjectUtils.doesUserHaveAnyAdminRoles(this.getSubject())) {
            WorkManagerFactory.getInstance().getSystem().schedule(req);
         } else {
            WorkManagerFactory.getInstance().getDefault().schedule(new WorkAdapter() {
               public void run() {
                  try {
                     req.run();
                  } catch (Exception var2) {
                     throw new RuntimeException(var2);
                  }
               }
            });
         }

      }
   }

   private boolean checkWorkQueueDepth(int n) {
      if (this.workQueueDepth == n) {
         return true;
      } else {
         if (this.workQueueDepth > n) {
            if (Kernel.DEBUG && debugConnection.isDebugEnabled()) {
               T3SrvrLogger.logCCHasPendingExecuteRequests(this.toString(), this.workQueueDepth - n);
            }
         } else if (this.workQueueDepth < 0) {
            if (Kernel.DEBUG && debugConnection.isDebugEnabled()) {
               T3SrvrLogger.logCCHasNegativeWorkQueueDepth(this.toString(), this.workQueueDepth);
            }

            this.workQueueDepth = 0;
         }

         return false;
      }
   }

   public void invoke(RemoteRequest req) throws RemoteException {
      String clss;
      Object o;
      try {
         clss = req.readAbbrevString();
         o = req.readObjectWL();
      } catch (IOException var5) {
         throw new UnmarshalException("", var5);
      } catch (ClassNotFoundException var6) {
         throw new UnmarshalException("", var6);
      }

      if ("XZZdisconnectZZX".equals(clss)) {
         this.requestKill();
      } else {
         this.enqueueWork(new ClientRequest(clss, o, this, req));
      }

   }

   public void setHardDisconnectTimeoutMins(int minutes) {
      this.cm.hardDisconnectTimeoutMins = minutes;
   }

   public void setSoftDisconnectTimeoutMins(int minutes) {
      this.cm.softDisconnectTimeoutMins = minutes;
   }

   public void setIdleDisconnectTimeoutMins(int minutes) {
      this.cm.idleSoftDisconnectTimeoutMins = minutes;
   }

   public void setVerbose(boolean verbose) {
      this.cm.verbose = verbose;
   }

   public T3ClientParams getParams() {
      T3ClientParams res = new T3ClientParams();
      res.verbose = Kernel.DEBUG && debugConnection.isDebugEnabled();
      res.QOS = this.QOS;
      res.hardDisconnectTimeoutMins = this.cm.hardDisconnectTimeoutMins;
      res.softDisconnectTimeoutMins = this.cm.softDisconnectTimeoutMins;
      res.idleSoftDisconnectTimeoutMins = this.cm.idleSoftDisconnectTimeoutMins;
      res.serverName = this.cm.serverName;
      res.wsName = this.cm.wsName;
      res.wsID = this.getID();
      res.ccID = this.ccID;
      res.rcc = this;
      res.user = this.getCurrentSubject();
      return res;
   }

   private AuthenticatedUser getCurrentSubject() {
      return SecurityServiceManager.getCurrentSubject(kernelId);
   }

   private void sendUnsolicitedData(int callbackID, Object o) throws IOException {
      if (this.isBound()) {
         try {
            RequestStream req = this.rjvm.getRequestStream((ServerChannel)null);
            req.writeObjectWL(o);
            req.sendOneWay(callbackID, this.QOS);
         } catch (IOException var4) {
            T3SrvrLogger.logFailedSendingUnsolicitedMessage(o.toString(), var4);
            throw var4;
         }
      }
   }

   public void peerGone(PeerGoneEvent pge) {
      if (!this.dead) {
         if (Kernel.DEBUG && debugConnection.isDebugEnabled()) {
            T3SrvrLogger.logConnectionUnexpectedlyLostHardDisconnect(this.toString(), pge.getReason());
         }

         this.hardDisconnectOccurred = true;
         this.requestKill();
      }
   }

   public void scavenge(int passID) throws IOException {
      synchronized(tableLock) {
         if (this.dead) {
            return;
         }

         if (!this.isBound()) {
            this.dieIfTimedOut(0);
            return;
         }
      }

      if (this.checkIdleDisconnectTimeout() && this.checkWorkQueueDepth(0)) {
         if (Kernel.DEBUG && debugConnection.isDebugEnabled()) {
            T3SrvrLogger.logTimingOutClientContextOnIdle(this.toString());
         }

         this.cm.cmd = 8;
         this.cm.reason = "Removing client because of idle disconnect timeout";
         this.sendUnsolicitedData(this.idleCallbackID, this.cm);
         this.requestKill();
      }

   }

   private void requestKill() {
      synchronized(tableLock) {
         if (!this.isBound()) {
            if (Kernel.DEBUG && debugConnection.isDebugEnabled()) {
               T3SrvrLogger.logIgnoringCCDeathRequest(this.toString());
            }

         } else {
            this.unbind();
            if (Kernel.DEBUG && debugConnection.isDebugEnabled()) {
               T3SrvrLogger.logSchedulingClientContextDeath(this.toString());
            }

            this.enqueueWork(this);
         }
      }
   }

   public synchronized void run() {
      synchronized(tableLock) {
         if (!this.dead) {
            if (!this.isBound()) {
               this.dieIfTimedOut(1);
            }

            this.decWorkQueueDepth();
         }
      }
   }

   private void dieIfTimedOut(int depth) {
      if ((this.hardDisconnectOccurred && this.checkHardDisconnectTimeout() || !this.hardDisconnectOccurred && this.checkSoftDisconnectTimeout()) && this.checkWorkQueueDepth(depth)) {
         if (this.hardDisconnectOccurred) {
            if (Kernel.DEBUG && debugConnection.isDebugEnabled()) {
               T3SrvrLogger.logRemovingClientContextHardDisconnect(this.toString());
            }
         } else if (Kernel.DEBUG && debugConnection.isDebugEnabled()) {
            T3SrvrLogger.logRemovingClientContextSoftDisconnect(this.toString());
         }

         this.dead = true;
         if (this.ccName != null) {
            ccNameTable.remove(this.ccName);
         }

         removeEntry(this.getID());
         Scavenger.removeScavengable(this.getID());
         RJVMManager.getLocalRJVM().getFinder().remove(this.ccID);
         this.clear();
      }

   }

   private boolean checkSoftDisconnectTimeout() {
      int timeoutMax = this.cm.softDisconnectTimeoutMins;
      if (timeoutMax == -2) {
         timeoutMax = defSDTM;
      }

      if (timeoutMax == -1) {
         return false;
      } else {
         int timeSoFar = TimeEventGenerator.deltaMins(this.disconnectNoticed);
         if (timeSoFar >= timeoutMax) {
            return true;
         } else {
            if (Kernel.DEBUG && debugConnection.isDebugEnabled()) {
               T3SrvrLogger.logSoftDisconnectPendingMins(timeoutMax);
            }

            return false;
         }
      }
   }

   private boolean checkHardDisconnectTimeout() {
      int timeoutMax = this.cm.hardDisconnectTimeoutMins;
      if (timeoutMax == -2) {
         timeoutMax = defHDTM;
      }

      if (timeoutMax == -1) {
         return false;
      } else {
         int timeSoFar = TimeEventGenerator.deltaMins(this.disconnectNoticed);
         if (timeSoFar >= timeoutMax) {
            return true;
         } else {
            if (Kernel.DEBUG && debugConnection.isDebugEnabled()) {
               T3SrvrLogger.logHardDisconnectPendingMins(timeoutMax);
            }

            return false;
         }
      }
   }

   private boolean checkIdleDisconnectTimeout() {
      int timeoutMax = this.cm.idleSoftDisconnectTimeoutMins;
      if (timeoutMax == -2) {
         timeoutMax = defISDTM;
      }

      if (timeoutMax == -1) {
         return false;
      } else {
         int timeSoFar = TimeEventGenerator.deltaMins(this.lastWorkPerformed);
         if (timeSoFar >= timeoutMax) {
            return true;
         } else {
            if (Kernel.DEBUG && debugConnection.isDebugEnabled()) {
               T3SrvrLogger.logIdleDisconnectPendingMins(timeoutMax);
            }

            return false;
         }
      }
   }
}
