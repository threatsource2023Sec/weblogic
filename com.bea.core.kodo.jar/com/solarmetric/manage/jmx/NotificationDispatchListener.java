package com.solarmetric.manage.jmx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Localizer;

public class NotificationDispatchListener implements NotificationListener, Closeable {
   private static final Localizer s_loc = Localizer.forPackage(NotificationDispatchListener.class);
   private MBeanServer _server;
   private ObjectInstance _instance;
   private HashMap _ActiveTypeToListeners = new HashMap();
   private HashMap _InactiveTypeToListeners = new HashMap();
   private boolean _registered = false;
   private int _activeListeners = 0;
   private Log _log;

   public NotificationDispatchListener(MBeanServer server, ObjectInstance instance, Log log) {
      this._server = server;
      this._instance = instance;
      this._log = log;
   }

   public void addListener(MBeanNotificationInfo notifInfo, NotificationListener listener) {
      String[] types = notifInfo.getNotifTypes();

      for(int i = 0; i < types.length; ++i) {
         ArrayList listeners = (ArrayList)this._ActiveTypeToListeners.get(types[i]);
         if (listeners == null) {
            listeners = (ArrayList)this._InactiveTypeToListeners.get(types[i]);
            if (listeners == null) {
               listeners = new ArrayList();
               this._InactiveTypeToListeners.put(types[i], listeners);
            }
         } else {
            ++this._activeListeners;
         }

         listeners.add(listener);
      }

      if (this._activeListeners > 0) {
         this.registerDispatcher();
      }

   }

   public void registerDispatcher() {
      if (!this._registered) {
         try {
            this._server.addNotificationListener(this._instance.getObjectName(), this, (NotificationFilter)null, (Object)null);
            this._registered = true;
         } catch (Exception var2) {
            this._log.warn(s_loc.get("cant-register"), var2);
         }

      }
   }

   public void deregisterDispatcher() {
      if (this._registered) {
         try {
            this._server.removeNotificationListener(this._instance.getObjectName(), this);
            this._registered = false;
         } catch (Exception var2) {
            this._log.warn(s_loc.get("cant-close"));
            if (this._log.isTraceEnabled()) {
               this._log.trace(s_loc.get("cant-close"), var2);
            }
         }

      }
   }

   public void activateNotificationTypes(MBeanNotificationInfo notifInfo, String[] types) {
      for(int i = 0; i < types.length; ++i) {
         ArrayList listeners = (ArrayList)this._ActiveTypeToListeners.get(types[i]);
         if (listeners == null) {
            listeners = (ArrayList)this._InactiveTypeToListeners.remove(types[i]);
            if (listeners == null) {
               listeners = new ArrayList();
            }

            this._ActiveTypeToListeners.put(types[i], listeners);
            this._activeListeners += listeners.size();
         }
      }

      if (this._activeListeners > 0) {
         this.registerDispatcher();
      }

   }

   public void deactivateNotificationTypes(MBeanNotificationInfo notifInfo, String[] types) {
      for(int i = 0; i < types.length; ++i) {
         ArrayList listeners = (ArrayList)this._ActiveTypeToListeners.remove(types[i]);
         if (listeners != null) {
            this._InactiveTypeToListeners.put(types[i], listeners);
            this._activeListeners -= listeners.size();
         }
      }

      if (this._activeListeners <= 0) {
         this.deregisterDispatcher();
      }

   }

   public void handleNotification(Notification notification, Object handback) {
      ArrayList listeners = (ArrayList)this._ActiveTypeToListeners.get(notification.getType());
      if (listeners != null) {
         Iterator i = (new HashSet(listeners)).iterator();

         while(i.hasNext()) {
            NotificationListener listener = (NotificationListener)i.next();
            listener.handleNotification(notification, handback);
         }
      }

   }

   public void close() {
      this.deregisterDispatcher();
   }
}
