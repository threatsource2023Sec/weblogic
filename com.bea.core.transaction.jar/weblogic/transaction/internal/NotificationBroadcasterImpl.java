package weblogic.transaction.internal;

import java.rmi.Remote;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.DisconnectMonitor;
import weblogic.rmi.extensions.DisconnectMonitorUnavailableException;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;

public class NotificationBroadcasterImpl extends NotificationBroadcasterManager {
   private SubCoordinator sc;
   protected ArrayList listeners = new ArrayList();
   private AtomicLong sequenceNumber = new AtomicLong();
   protected Timer xaTimer;
   protected Timer nonxaTimer;
   private XANotificationTimerListener xaAsyncNotifier = new XANotificationTimerListener();
   private NonXANotificationTimerListener nonxaAsyncNotifier = new NonXANotificationTimerListener();
   private int resourceNotificationFrequencySeconds = Integer.getInteger("weblogic.transaction.resourceNotificationFrequencySeconds", 10);

   public void setSubCoordinator(SubCoordinator sc) {
      this.sc = sc;
   }

   private List getListeners() {
      return (List)this.listeners.clone();
   }

   public synchronized void addNotificationListener(NotificationListener listener, Object handback) throws IllegalArgumentException {
      if (PlatformHelper.getPlatformHelper().isColocated(listener)) {
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("NotificationBroadcasterImpl.addNotificationListener() ignoring registration of local listener=" + listener);
         }

      } else {
         RegisteredListener entry = new RegisteredListener(listener, handback);
         if (listener instanceof Remote) {
            DisconnectMonitor dm = (DisconnectMonitor)PlatformHelper.getPlatformHelper().getDisconnectMonitor();

            try {
               dm.addDisconnectListener(listener, entry);
            } catch (DisconnectMonitorUnavailableException var6) {
            } catch (Exception var7) {
               throw new RuntimeException("Error while registering disconnect monitor", var7);
            }
         }

         this.listeners.add(entry);
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("NotificationBroadcasterImpl.addNotificationListener() added listener=" + listener);
         }

      }
   }

   public synchronized void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("NotificationBroadcasterImpl.removeNotificationListener() listener=" + listener);
      }

      Iterator it = this.listeners.iterator();

      while(it.hasNext()) {
         RegisteredListener rl = (RegisteredListener)it.next();
         if (rl.getNotificationListener() == listener) {
            it.remove();
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("NotificationBroadcasterImpl.removeNotificationListener() removed listener=" + listener);
            }
         }
      }

   }

   public synchronized void xaResourceRegistrationChange(String[] oldValues, String[] newValues) {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("NotificationBroadcasterImpl.xaResourceRegistrationChange() oldValues: " + Arrays.toString(oldValues) + ", newValues: " + Arrays.toString(newValues));
      }

      this.xaAsyncNotifier.setNotifyRequested(true);
   }

   public synchronized void nonXAResourceRegistrationChange(String[] oldValues, String[] newValues) {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("NotificationBroadcasterImpl.nonXAResourceRegistrationChange() oldValues: " + Arrays.toString(oldValues) + ", newValues: " + Arrays.toString(newValues));
      }

      this.nonxaAsyncNotifier.setNotifyRequested(true);
   }

   protected void notifyListeners(Notification notification) {
      List notificationListeners = this.getListeners();
      Iterator it = notificationListeners.iterator();

      while(it.hasNext()) {
         RegisteredListener rl = (RegisteredListener)it.next();
         NotificationListener l = rl.getNotificationListener();
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("NotificationBroadcasterImpl.notifyListeners() sending notification " + notification + " listener=" + l + ", handback=" + rl.getHandback());
         }

         try {
            PlatformHelper.getPlatformHelper().runAction(new StartNotificationAction(l, notification, rl.getHandback()), (String)null, "broadcast");
         } catch (RuntimeException var7) {
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug((String)"NotificationBroadcasterImpl.notifyListeners() runtime exception", (Throwable)var7);
            }

            throw var7;
         } catch (Throwable var8) {
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("NotificationBroadcasterImpl.notifyListeners() exception", var8);
            }
         }
      }

   }

   public void startTimer() {
      if (this.resourceNotificationFrequencySeconds > 0) {
         if (this.xaTimer == null) {
            this.xaTimer = (Timer)PlatformHelper.getPlatformHelper().defaultTimerManagerSchedule(this.xaAsyncNotifier, (long)(this.resourceNotificationFrequencySeconds * 1000), (long)(this.resourceNotificationFrequencySeconds * 1000));
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("NotificationBroadcasterImpl.startTimers() started XANotificationTimer: " + this.xaTimer);
            }
         }

         if (this.nonxaTimer == null) {
            this.nonxaTimer = (Timer)PlatformHelper.getPlatformHelper().defaultTimerManagerSchedule(this.nonxaAsyncNotifier, (long)(this.resourceNotificationFrequencySeconds * 1000), (long)(this.resourceNotificationFrequencySeconds * 1000));
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("NotificationBroadcasterImpl.startTimers() started NonXANotificationTimer: " + this.nonxaTimer);
            }
         }
      }

   }

   public void stopTimer() {
      boolean canceled;
      if (this.xaTimer != null) {
         canceled = this.xaTimer.cancel();
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("NotificationBroadcasterImpl.stopTimers() XANotificationTimer cancel() returned " + canceled);
         }

         this.xaTimer = null;
      }

      if (this.nonxaTimer != null) {
         canceled = this.nonxaTimer.cancel();
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("NotificationBroadcasterImpl.stopTimers() NonXANotificationTimer cancel() returned " + canceled);
         }

         this.nonxaTimer = null;
      }

   }

   abstract class BaseNotificationTimerListener implements NakedTimerListener {
      static final int TIMER_FREQ_SEC = 5;
      boolean notifyRequested;
      boolean notifyScheduled;

      synchronized void setNotifyRequested(boolean b) {
         this.notifyRequested = b;
      }

      synchronized boolean isNotifyRequested() {
         return this.notifyRequested;
      }

      abstract Notification createNotification();

      public void timerExpired(Timer timer) {
         synchronized(this) {
            if (!this.notifyRequested || this.notifyScheduled) {
               return;
            }

            this.notifyScheduled = true;
            this.notifyRequested = false;
         }

         boolean var15 = false;

         label134: {
            try {
               var15 = true;
               Notification notification = this.createNotification();
               NotificationBroadcasterImpl.this.notifyListeners(notification);
               var15 = false;
               break label134;
            } catch (Throwable var19) {
               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("NotificationBroadcasterImpl.AsyncNotifier.run() error: ", var19);
               }

               this.notifyRequested = true;
               var15 = false;
            } finally {
               if (var15) {
                  synchronized(this) {
                     this.notifyScheduled = false;
                  }
               }
            }

            synchronized(this) {
               this.notifyScheduled = false;
               return;
            }
         }

         synchronized(this) {
            this.notifyScheduled = false;
         }

      }
   }

   class NonXANotificationTimerListener extends BaseNotificationTimerListener {
      NonXANotificationTimerListener() {
         super();
      }

      Notification createNotification() {
         List resourceNames = new ArrayList();
         List allResources = ResourceDescriptor.getAllResources();
         Iterator it = allResources.iterator();

         while(it.hasNext()) {
            ResourceDescriptor rd = (ResourceDescriptor)it.next();
            if (rd instanceof NonXAResourceDescriptor && rd.isRegistered()) {
               resourceNames.add(rd.getName());
            }
         }

         String[] registeredNonXAResources = (String[])resourceNames.toArray(new String[resourceNames.size()]);
         Notification notification = new PropertyChangeNotification(NotificationBroadcasterImpl.this.sc, NotificationBroadcasterImpl.this.sequenceNumber.getAndIncrement(), System.currentTimeMillis(), "Registered NonXAResource change notification", "NonXAResources", (Object)null, registeredNonXAResources);
         return notification;
      }
   }

   class XANotificationTimerListener extends BaseNotificationTimerListener {
      XANotificationTimerListener() {
         super();
      }

      Notification createNotification() {
         List resourceNames = new ArrayList();
         List allResources = ResourceDescriptor.getAllResources();
         Iterator it = allResources.iterator();

         while(it.hasNext()) {
            ResourceDescriptor rd = (ResourceDescriptor)it.next();
            if (rd instanceof XAResourceDescriptor && rd.isRegistered()) {
               resourceNames.add(rd.getName());
            }
         }

         String[] registeredXAResources = (String[])resourceNames.toArray(new String[resourceNames.size()]);
         Notification notification = new PropertyChangeNotification(NotificationBroadcasterImpl.this.sc, NotificationBroadcasterImpl.this.sequenceNumber.getAndIncrement(), System.currentTimeMillis(), "Registered XAResource change notification", "XAResources", (Object)null, registeredXAResources);
         return notification;
      }
   }

   private class StartNotificationAction implements PrivilegedExceptionAction {
      private NotificationListener listener;
      private Notification notification;
      private Object handback;

      StartNotificationAction(NotificationListener aListener, Notification aNotification, Object aHandback) {
         this.listener = aListener;
         this.notification = aNotification;
         this.handback = aHandback;
      }

      public Object run() throws Exception {
         this.listener.handleNotification(this.notification, this.handback);
         return null;
      }
   }

   class RegisteredListener implements DisconnectListener {
      NotificationListener notificationListener;
      Object handback;

      RegisteredListener(NotificationListener listener, Object handback) {
         this.notificationListener = listener;
         this.handback = handback;
      }

      NotificationListener getNotificationListener() {
         return this.notificationListener;
      }

      Object getHandback() {
         return this.handback;
      }

      public void onDisconnect(DisconnectEvent event) {
         try {
            NotificationBroadcasterImpl.this.removeNotificationListener(this.notificationListener);
         } catch (ListenerNotFoundException var3) {
         }

      }
   }
}
