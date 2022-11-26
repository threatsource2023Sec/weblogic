package weblogic.rmi.extensions;

import java.rmi.Remote;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.extensions.server.DisconnectMonitorProvider;
import weblogic.rmi.extensions.server.HeartbeatHelper;

public abstract class AbstractDisconnectMonitorDelegate implements DisconnectMonitorProvider {
   private static final DebugLogger debugDGC = DebugLogger.getDebugLogger("DebugDGC");
   private static final boolean DEBUG = getDebug();
   private final HashMap listenerSet = new HashMap();
   private final HashMap remote2Helper = new HashMap();
   private static ThreadGroup hbmThreadGroup = null;
   private static boolean hasThreadGroupAccess = true;

   private static boolean getDebug() {
      try {
         return Boolean.getBoolean("weblogic.debug.client.dgc");
      } catch (Exception var1) {
         return false;
      }
   }

   private static void debug(String msg) {
      debugDGC.debug("<AbstractDisconnectMonitorDelegate>: " + msg);
   }

   public AbstractDisconnectMonitorDelegate() {
      DisconnectMonitorListImpl.getDisconnectMonitorList().addDisconnectMonitor(this);
   }

   protected abstract HeartbeatHelper getHeartbeatHelper(Object var1);

   public boolean addDisconnectListener(Remote r, DisconnectListener listener) {
      HeartbeatHelper helper = this.getHeartbeatHelper(r);
      if (helper == null) {
         return false;
      } else {
         if (DEBUG) {
            debug("addDisconnectMonitorListener...");
         }

         synchronized(this.listenerSet) {
            this.remote2Helper.put(listener, helper);
            HelperEntry entry = (HelperEntry)this.listenerSet.get(helper);
            if (entry == null) {
               entry = new HelperEntry(this.startPinger(helper));
               this.listenerSet.put(helper, entry);
            }

            entry.listeners.add(listener);
            return true;
         }
      }
   }

   private Timer startPinger(HeartbeatHelper helper) {
      Timer timer = new Timer(this, helper);
      Thread timerThread = createThread(timer, "Request Timer Thread");
      timerThread.setDaemon(true);
      Pinger pinger = new Pinger(this, helper, timer);
      Thread pingerThread = createThread(pinger, "Heartbeat Request Thread");
      pingerThread.setDaemon(true);
      pingerThread.start();
      timerThread.start();
      if (DEBUG) {
         debug("timer/pinger started");
      }

      return timer;
   }

   public boolean removeDisconnectListener(Remote r, DisconnectListener listener) {
      synchronized(this.listenerSet) {
         HeartbeatHelper helper = (HeartbeatHelper)this.remote2Helper.remove(listener);
         if (helper == null) {
            return false;
         } else {
            HelperEntry entry = (HelperEntry)this.listenerSet.get(helper);
            if (entry != null) {
               entry.listeners.remove(listener);
            }

            return true;
         }
      }
   }

   private void deliverDisconnectEvent(Throwable reason, Object key) {
      HelperEntry entry = null;
      if (DEBUG) {
         debug("deliver [" + reason + "] to listeners");
      }

      synchronized(this.listenerSet) {
         entry = (HelperEntry)this.listenerSet.remove(key);
         if (entry == null) {
            return;
         }
      }

      Iterator i = ((HashSet)((HashSet)entry.listeners.clone())).iterator();

      while(i.hasNext()) {
         DisconnectListener l = (DisconnectListener)i.next();
         l.onDisconnect(new DisconnectEventImpl(reason));
      }

   }

   private static Thread createThread(Runnable runnable, String name) {
      if (!KernelStatus.isApplet()) {
         return new Thread(runnable, name);
      } else {
         initializeHBMThreadGroup();
         return hbmThreadGroup != null ? new Thread(hbmThreadGroup, runnable, name) : new Thread(runnable, name);
      }
   }

   private static void initializeHBMThreadGroup() {
      if (hasThreadGroupAccess && hbmThreadGroup == null) {
         Class var0 = AbstractDisconnectMonitorDelegate.class;
         synchronized(AbstractDisconnectMonitorDelegate.class) {
            if (hbmThreadGroup == null) {
               try {
                  ThreadGroup tg;
                  for(tg = Thread.currentThread().getThreadGroup(); !tg.getName().equals("main") || !tg.getParent().getName().equals("system"); tg = tg.getParent()) {
                  }

                  hbmThreadGroup = new ThreadGroup(tg, "HeartbeatMonitor ThreadGroup") {
                     public String toString() {
                        return "HBMThreadGroup(name=" + this.getName() + ", parent=" + this.getParent() + ')';
                     }
                  };
               } catch (SecurityException var4) {
                  debug(" +++ <Warning> Don't have permissions to access ThreadGroup.  We strongly recommend to use signed applet.");
                  debug(" +++ <Warning> Proceed further without creating ThreadGroup.");
                  hasThreadGroupAccess = false;
                  return;
               }

               return;
            }
         }
      }

   }

   protected static class Pinger implements Runnable {
      protected AbstractDisconnectMonitorDelegate delegate;
      protected HeartbeatHelper helper;
      protected Timer timer;

      protected Pinger(AbstractDisconnectMonitorDelegate delegate, HeartbeatHelper helper, Timer timer) {
         this.delegate = delegate;
         this.helper = helper;
         this.timer = timer;
      }

      public void run() {
         while(true) {
            boolean var14 = false;

            label157: {
               try {
                  var14 = true;
                  if (AbstractDisconnectMonitorDelegate.DEBUG) {
                     AbstractDisconnectMonitorDelegate.debug("pinger called");
                  }

                  this.helper.ping();
                  if (AbstractDisconnectMonitorDelegate.DEBUG) {
                     AbstractDisconnectMonitorDelegate.debug("pinger renews lease");
                  }

                  this.timer.renewLease();
                  var14 = false;
                  break label157;
               } catch (Exception var18) {
                  if (AbstractDisconnectMonitorDelegate.DEBUG) {
                     AbstractDisconnectMonitorDelegate.debugDGC.debug("pinger caught:", var18);
                  }

                  this.delegate.deliverDisconnectEvent(var18, this.helper);
                  var14 = false;
               } finally {
                  if (var14) {
                     synchronized(this.delegate.listenerSet) {
                        HelperEntry entry = (HelperEntry)this.delegate.listenerSet.get(this.helper);
                        if (entry == null || entry.listeners.isEmpty()) {
                           this.timer.cancel();
                           this.delegate.listenerSet.remove(this.helper);
                           return;
                        }

                     }
                  }
               }

               synchronized(this.delegate.listenerSet) {
                  HelperEntry entry = (HelperEntry)this.delegate.listenerSet.get(this.helper);
                  if (entry != null && !entry.listeners.isEmpty()) {
                     return;
                  }

                  this.timer.cancel();
                  this.delegate.listenerSet.remove(this.helper);
                  return;
               }
            }

            synchronized(this.delegate.listenerSet) {
               HelperEntry entry = (HelperEntry)this.delegate.listenerSet.get(this.helper);
               if (entry == null || entry.listeners.isEmpty()) {
                  this.timer.cancel();
                  this.delegate.listenerSet.remove(this.helper);
                  return;
               }
            }
         }
      }
   }

   protected static class Timer implements Runnable {
      protected AbstractDisconnectMonitorDelegate delegate;
      protected Object key;
      protected int lease = 1;
      protected volatile boolean cancel = false;

      protected Timer(AbstractDisconnectMonitorDelegate delegate, Object key) {
         this.delegate = delegate;
         this.key = key;
      }

      protected void renewLease() {
         ++this.lease;
      }

      protected synchronized void cancel() {
         this.cancel = true;
         this.notify();
      }

      public void run() {
         long t1 = System.currentTimeMillis();
         int oldLease = this.lease;

         while(true) {
            while(true) {
               try {
                  if (AbstractDisconnectMonitorDelegate.DEBUG) {
                     AbstractDisconnectMonitorDelegate.debug("timer waits...");
                  }

                  try {
                     synchronized(this) {
                        this.wait(60000L);
                     }
                  } catch (InterruptedException var7) {
                  }

                  if (this.cancel) {
                     if (AbstractDisconnectMonitorDelegate.DEBUG) {
                        AbstractDisconnectMonitorDelegate.debug("timer canceled");
                     }

                     return;
                  }

                  if (oldLease < this.lease) {
                     oldLease = this.lease;
                     t1 = System.currentTimeMillis();
                  } else {
                     long delta = System.currentTimeMillis() - t1;
                     if (AbstractDisconnectMonitorDelegate.DEBUG) {
                        AbstractDisconnectMonitorDelegate.debug("timer fires after " + delta + "ms, oldLease=" + oldLease + ", lease=" + this.lease);
                     }

                     if (delta >= 240000L) {
                        this.delegate.deliverDisconnectEvent(new DisconnectMonitorException("DisconnectMonitor timed out after " + delta / 1000L + "s."), this.key);
                        return;
                     }
                  }
               } catch (Throwable var8) {
                  if (AbstractDisconnectMonitorDelegate.DEBUG) {
                     AbstractDisconnectMonitorDelegate.debugDGC.debug(var8.getMessage(), var8);
                  }

                  this.delegate.deliverDisconnectEvent(var8, this.key);
                  return;
               }
            }
         }
      }
   }

   private static class HelperEntry {
      private Timer timer;
      private HashSet listeners;

      private HelperEntry(Timer timer) {
         this.listeners = new HashSet();
         this.timer = timer;
      }

      // $FF: synthetic method
      HelperEntry(Timer x0, Object x1) {
         this(x0);
      }
   }
}
