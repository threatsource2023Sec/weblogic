package weblogic.rmi.internal.dgc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ProtocolStack;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.rmi.RMILogger;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.BasicRemoteRef;
import weblogic.rmi.internal.ClientRuntimeDescriptor;
import weblogic.rmi.internal.InitialReferenceConstants;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.HostID;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.NestedException;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public final class DGCClientImpl implements NakedTimerListener, InitialReferenceConstants {
   private static DGCClientImpl theDGCClientImpl = null;
   private static Method renewMethod = null;
   private static final Class[] params = new Class[]{int[].class};
   private Map remoteHostIDMap = new HashMap();
   private static final RuntimeMethodDescriptor renewMD;
   private int periodLengthMillis = this.getPeriodLengthMillis();
   private static final DebugLogger debugDgcEnrollment;
   private static final boolean forceGC;

   public static void initialize() {
      getDGCClientImpl();
   }

   public static DGCClientImpl getDGCClientImpl() {
      if (theDGCClientImpl == null) {
         Class var0 = DGCClientImpl.class;
         synchronized(DGCClientImpl.class) {
            if (theDGCClientImpl == null) {
               theDGCClientImpl = new DGCClientImpl();
            }
         }
      }

      return theDGCClientImpl;
   }

   private DGCClientImpl() {
      TimerManagerFactory.getTimerManagerFactory().getTimerManager("DGCClientImpl", "weblogic.kernel.System").schedule(this, (long)this.periodLengthMillis, (long)this.periodLengthMillis);
   }

   private int getPeriodLengthMillis() {
      int period = RMIEnvironment.getEnvironment().getHeartbeatPeriodLength();
      if (period == 0) {
         period = 60000;
      }

      return this.getTimePeriodToSendGCNotice(period);
   }

   private int getTimePeriodToSendGCNotice(int period) {
      if (RJVMEnvironment.getEnvironment().getRjvmIdleTimeout() > 0) {
         period *= RMIEnvironment.getEnvironment().getDGCIdlePeriodsUntilTimeout();
      } else {
         period -= 100;
      }

      return period;
   }

   public void timerExpired(Timer t) {
      try {
         long before = 0L;
         if (KernelStatus.DEBUG && debugDgcEnrollment.isDebugEnabled()) {
            before = System.currentTimeMillis();
         }

         if (forceGC) {
            System.gc();
         }

         this.mark();
         if (KernelStatus.DEBUG && debugDgcEnrollment.isDebugEnabled()) {
            long after = System.currentTimeMillis();
            RMILogger.logDebug("Marked in: " + (after - before) + " ms");
         }
      } catch (Exception var6) {
         RMILogger.logNotMarked(var6);
      }

   }

   private void mark() {
      DGCClientHelper.mark(this.remoteHostIDMap);
      Vector heartBeats = DGCClientHelper.getHeartBeats();
      if (heartBeats.size() != 0) {
         Enumeration e = heartBeats.elements();

         while(true) {
            Vector ds;
            do {
               if (!e.hasMoreElements()) {
                  return;
               }

               ds = (Vector)e.nextElement();
            } while(ds.isEmpty());

            DGCReferenceCounter counter = (DGCReferenceCounter)ds.elementAt(0);
            HostID hostID = counter.getHostID();
            BasicRemoteRef ref;
            if (this.remoteHostIDMap.get(hostID) == null) {
               ref = new BasicRemoteRef(2, hostID);
               this.remoteHostIDMap.put(hostID, ref);
            } else {
               ref = (BasicRemoteRef)this.remoteHostIDMap.get(hostID);
            }

            HeartBeat hb = new HeartBeat(ds, KernelStatus.DEBUG && debugDgcEnrollment.isDebugEnabled(), renewMethod, renewMD, ref);
            WorkManagerFactory.getInstance().getSystem().schedule(hb);
         }
      }
   }

   static {
      try {
         renewMethod = DGCServer.class.getMethod("renewLease", params);
      } catch (NoSuchMethodException var1) {
         throw new AssertionError("Impossible to throw this exception", var1);
      }

      renewMD = new MethodDescriptor(renewMethod, DGCServer.class, true, false, false, false, 0, 2, false, "");
      debugDgcEnrollment = DebugLogger.getDebugLogger("DebugDGCEnrollment");
      forceGC = KernelStatus.DEBUG && DebugLogger.getDebugLogger("ForceGCEachDGCPeriod").isDebugEnabled();
   }

   static class InvokeReference implements PrivilegedExceptionAction {
      private RemoteReference ref;
      private RuntimeMethodDescriptor md;
      private Object[] args;
      private Method m;
      private String partitionName;

      public InvokeReference(RemoteReference ref, RuntimeMethodDescriptor md, Object[] args, Method m, String partitionName) {
         this.ref = ref;
         this.md = md;
         this.args = args;
         this.m = m;
         this.partitionName = partitionName;
      }

      public Object run() throws Exception {
         try {
            this.ref.invoke(new DGCStub(this.partitionName), this.md, this.args, this.m);
            return null;
         } catch (Throwable var2) {
            throw new NestedException(var2);
         }
      }

      private class DGCStub implements Remote, StubInfoIntf {
         private String partitionName;

         DGCStub(String partitionName) {
            this.partitionName = partitionName;
         }

         public StubInfo getStubInfo() {
            RemoteReference dummyRef = (RemoteReference)Proxy.newProxyInstance(DGCStub.class.getClassLoader(), new Class[]{RemoteReference.class}, new InvocationHandler() {
               public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                  return method.getName().contains("getHostID") ? LocalServerIdentity.getIdentity() : null;
               }
            });
            return new StubInfo(dummyRef, (ClientRuntimeDescriptor)null, (String)null, (String)null, (String)null, this.partitionName);
         }
      }
   }

   static class HeartBeat extends WorkAdapter {
      final Vector acks;
      final Method renewMethod;
      final RuntimeMethodDescriptor renewMD;
      final boolean debug;
      final BasicRemoteRef ref;
      private final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      HeartBeat(Vector acks, boolean debug, Method m, RuntimeMethodDescriptor md, BasicRemoteRef ref) {
         this.acks = acks;
         this.debug = debug;
         this.ref = ref;
         this.renewMethod = m;
         this.renewMD = md;
      }

      public void run() {
         HostID hostID = this.ref.getHostID();
         if (this.debug) {
            RMILogger.logDebug("Renewing lease for: " + this.acks.size() + " objects hosted by: " + hostID);
         }

         if (this.ref.getEndPoint() != null && !this.ref.getEndPoint().isDead()) {
            int[] refs = new int[this.acks.size()];
            int i = 0;

            for(int end = this.acks.size(); i < end; ++i) {
               DGCReferenceCounter counter = (DGCReferenceCounter)this.acks.elementAt(i);
               refs[i] = counter.getOID();
               if (this.debug) {
                  RMILogger.logDebug("Renewing lease for: " + counter);
               }

               if (counter.leaseRenewed()) {
                  counter.renewLease(false);
               }
            }

            Object[] params = new Object[]{refs};
            String partitionName = ((DGCReferenceCounter)this.acks.elementAt(0)).getPartitionName();

            try {
               Channel channel = this.ref.getChannel();
               String protocolName = channel != null ? channel.getProtocolPrefix() : null;
               Protocol protocol = protocolName != null ? ProtocolManager.getProtocolByName(protocolName) : ProtocolManager.getDefaultProtocol();
               boolean secure = protocol.isSecure();
               ProtocolStack.push(protocol);
               AuthenticatedSubject subject = SubjectUtils.getAnonymousSubject();
               if (secure) {
                  if (KernelStatus.isServer()) {
                     subject = this.kernelId;
                  } else {
                     subject = RMIEnvironment.getEnvironment().getCurrentSubjectForWire(this.kernelId);
                  }
               }

               SecurityManager.runAs(this.kernelId, subject, new InvokeReference(this.ref, this.renewMD, params, this.renewMethod, partitionName));
            } catch (Throwable var13) {
               if (this.debug) {
                  RMILogger.logFailedRenew(refs.length, hostID.toString());
               }
            } finally {
               ProtocolStack.pop();
            }

            if (this.debug) {
               RMILogger.logDebug("Renewed lease for: " + refs.length + " objects hosted by: " + hostID);
            }

         } else {
            RMILogger.logNoConnection(hostID.toString());
         }
      }
   }
}
