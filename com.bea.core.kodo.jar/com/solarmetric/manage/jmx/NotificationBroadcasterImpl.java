package com.solarmetric.manage.jmx;

import com.solarmetric.manage.ManagementLog;
import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class NotificationBroadcasterImpl implements NotificationBroadcaster, Serializable {
   private static Localizer s_loc = Localizer.forPackage(NotificationBroadcasterImpl.class);
   private ArrayList _listeners = new ArrayList();
   private NotificationThread _notifThread = null;
   private Configuration _conf;
   private boolean _timed;

   public NotificationBroadcasterImpl(Configuration conf, boolean timed) {
      this._conf = conf;
      this._timed = timed;
   }

   public boolean hasListeners() {
      synchronized(this._listeners) {
         return this._listeners.size() > 0;
      }
   }

   public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) {
      ListenerInfo info = new ListenerInfo(listener, filter, handback);
      synchronized(this._listeners) {
         this._listeners.add(info);
      }

      synchronized(this) {
         if (this._timed && this._notifThread == null) {
            this._notifThread = new NotificationThread();
            this._notifThread.setDaemon(true);
            this._notifThread.start();
         }

      }
   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      return null;
   }

   public void removeNotificationListener(NotificationListener listener) {
      synchronized(this._listeners) {
         Iterator i = this._listeners.iterator();

         while(i.hasNext()) {
            ListenerInfo info = (ListenerInfo)i.next();
            if (info.listener.equals(listener)) {
               i.remove();
            }
         }
      }

      synchronized(this) {
         if (this._notifThread != null && this._listeners.size() == 0) {
            this._notifThread.deactivate();
            this._notifThread = null;
         }

      }
   }

   public void sendNotification(Notification n) {
      synchronized(this) {
         if (this._notifThread != null) {
            this._notifThread.insert(n);
         } else if (!this._timed && this._listeners.size() > 0) {
            this.sendNotificationNow(n);
         }

      }
   }

   public void sendNotificationNow(Notification n) {
      LinkedList badListeners = new LinkedList();
      synchronized(this._listeners) {
         Iterator i = this._listeners.iterator();

         while(i.hasNext()) {
            ListenerInfo info = (ListenerInfo)i.next();

            try {
               if (info.filter == null) {
                  info.listener.handleNotification(n, info.handback);
               } else if (info.filter.isNotificationEnabled(n)) {
                  info.listener.handleNotification(n, info.handback);
               }
            } catch (UndeclaredThrowableException var8) {
               badListeners.add(info);
            }
         }
      }

      Iterator i = badListeners.iterator();

      while(i.hasNext()) {
         ListenerInfo info = (ListenerInfo)i.next();
         this.removeNotificationListener(info.listener);
      }

   }

   class NotificationThread extends Thread {
      private LinkedList queue = new LinkedList();
      private boolean active = true;

      void deactivate() {
         this.active = false;
      }

      void insert(Notification notif) {
         synchronized(this) {
            this.queue.addLast(notif);
         }
      }

      public void run() {
         while(this.active) {
            while(this.queue.size() > 0) {
               try {
                  Notification notif;
                  synchronized(this) {
                     notif = (Notification)this.queue.removeFirst();
                  }

                  NotificationBroadcasterImpl.this.sendNotificationNow(notif);
               } catch (Exception var6) {
                  Log l = ManagementLog.get(NotificationBroadcasterImpl.this._conf);
                  if (l.isErrorEnabled()) {
                     l.error(NotificationBroadcasterImpl.s_loc.get("exception-sending"), var6);
                  }
               }
            }

            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var4) {
            }
         }

      }
   }

   static class ListenerInfo implements Serializable {
      public NotificationListener listener;
      public NotificationFilter filter;
      public Object handback;

      public ListenerInfo(NotificationListener listener, NotificationFilter filter, Object handback) {
         this.listener = listener;
         this.filter = filter;
         this.handback = handback;
      }
   }
}
