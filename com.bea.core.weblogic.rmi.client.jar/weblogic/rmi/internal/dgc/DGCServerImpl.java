package weblogic.rmi.internal.dgc;

import java.lang.ref.ReferenceQueue;
import java.rmi.NoSuchObjectException;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.RMILogger;
import weblogic.rmi.extensions.server.Collectable;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.PhantomRef;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;

public final class DGCServerImpl implements DGCServer, NakedTimerListener {
   private static DGCServerImpl theDGCServerImpl = null;
   private static OIDManager oidMngr = null;
   private static final ReferenceQueue refQueue = new ReferenceQueue();
   private static final ConcurrentHashMap map = new ConcurrentHashMap();
   private static final Object dummy = new Object();
   private static final boolean DEBUG = false;
   private final Timer timer;
   private final int idleTimeout;
   private static final DebugLogger debugLogStatistics = DebugLogger.getDebugLogger("LogDGCStatistics");
   private static final DebugLogger debugDgcEnrollment = DebugLogger.getDebugLogger("DebugDGCEnrollment");

   public static final DGCServerImpl getDGCServerImpl() {
      if (theDGCServerImpl == null) {
         Class var0 = DGCServerImpl.class;
         synchronized(DGCServerImpl.class) {
            if (theDGCServerImpl == null) {
               theDGCServerImpl = new DGCServerImpl();
            }
         }

         oidMngr = OIDManager.getInstance();
      }

      return theDGCServerImpl;
   }

   private DGCServerImpl() {
      theDGCServerImpl = this;
      this.idleTimeout = this.getIdleTimeout();
      this.timer = TimerManagerFactory.getTimerManagerFactory().getTimerManager("DGCServer", "weblogic.kernel.System").schedule(this, (long)this.idleTimeout, (long)this.idleTimeout);
   }

   private int getIdleTimeout() {
      int period = RMIEnvironment.getEnvironment().getHeartbeatPeriodLength();
      if (period == 0) {
         period = 60000;
      }

      return period * RMIEnvironment.getEnvironment().getDGCIdlePeriodsUntilTimeout();
   }

   public void timerExpired(Timer t) {
      int before = 0;
      long tb = 0L;
      if (KernelStatus.DEBUG && debugLogStatistics.isDebugEnabled()) {
         tb = System.currentTimeMillis();
      }

      this.mark();
      long ta;
      if (KernelStatus.DEBUG && debugLogStatistics.isDebugEnabled()) {
         ta = System.currentTimeMillis();
         RMILogger.logMarked(ta - tb);
         before = oidMngr.size();
         tb = System.currentTimeMillis();
      }

      try {
         oidMngr.sweep();
      } catch (Exception var8) {
         RMILogger.logSweepException(var8);
      }

      if (KernelStatus.DEBUG && debugLogStatistics.isDebugEnabled()) {
         ta = System.currentTimeMillis();
         int after = oidMngr.size();
         RMILogger.logSweepFreed((long)(before - after), ta - tb, (long)after);
      }

   }

   public void enroll(int[] oids) {
      int i = 0;

      for(int end = oids.length; i < end; ++i) {
         try {
            ((Collectable)oidMngr.getServerReference(oids[i])).incrementRefCount();
         } catch (NoSuchObjectException var5) {
            if (KernelStatus.DEBUG && debugDgcEnrollment.isDebugEnabled()) {
               RMILogger.logEnrollLostRef(oids[i]);
            }
         }
      }

   }

   public void unenroll(int[] oids) {
      int i = 0;

      for(int end = oids.length; i < end; ++i) {
         try {
            ((Collectable)oidMngr.getServerReference(oids[i])).decrementRefCount();
         } catch (NoSuchObjectException var5) {
            if (KernelStatus.DEBUG && debugDgcEnrollment.isDebugEnabled()) {
               RMILogger.logUnenrollLostRef(oids[i]);
            }
         }
      }

   }

   public void renewLease(int[] oids) {
      int i = 0;

      for(int end = oids.length; i < end; ++i) {
         try {
            ((Collectable)oidMngr.getServerReference(oids[i])).renewLease();
         } catch (NoSuchObjectException var5) {
            if (KernelStatus.DEBUG && debugDgcEnrollment.isDebugEnabled()) {
               RMILogger.logRenewLease(oids[i]);
            }
         }
      }

   }

   public static final ReferenceQueue getReferenceQueue() {
      return refQueue;
   }

   public static final void addPhantomRef(PhantomRef ph) {
      map.put(ph, dummy);
   }

   private void mark() {
      for(PhantomRef ref = (PhantomRef)refQueue.poll(); ref != null; ref = (PhantomRef)refQueue.poll()) {
         map.remove(ref);
         int oid = ref.getOID();

         try {
            ((Collectable)oidMngr.getServerReference(oid)).decrementRefCount();
         } catch (NoSuchObjectException var7) {
            if (KernelStatus.DEBUG && debugDgcEnrollment.isDebugEnabled()) {
               RMILogger.logUnenrollLostRef(oid);
            }
         } finally {
            ref.clear();
         }
      }

   }
}
