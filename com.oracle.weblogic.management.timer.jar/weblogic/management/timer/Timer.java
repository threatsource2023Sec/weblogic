package weblogic.management.timer;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.timer.TimerMBean;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

public final class Timer extends javax.management.timer.Timer implements TimerMBean, NotificationBroadcaster, Serializable {
   private Object listenerLock = new Object();
   private Map allNotifications = new HashMap(11);
   private TimerListener[] allListeners = new TimerListener[0];
   private int notificationCount = 0;
   private int listenerCount = 0;
   private boolean sendPastNotifications = false;
   TimerManager timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("TimerMBean");
   private int Id = 0;
   private volatile boolean isActive = true;
   private Set pastNotifications = new HashSet(7);

   public int getNbNotifications() {
      return this.notificationCount;
   }

   public Vector getAllNotificationIDs() {
      Vector v = new Vector();
      synchronized(this.allNotifications) {
         Iterator it = this.allNotifications.keySet().iterator();

         while(it.hasNext()) {
            v.addElement(it.next());
         }

         return v;
      }
   }

   public Vector getNotificationIDs(String type) {
      Vector v = new Vector();
      synchronized(this.allNotifications) {
         Iterator it = this.allNotifications.keySet().iterator();

         while(it.hasNext()) {
            Integer key = (Integer)it.next();
            TimerNotification tn = (TimerNotification)this.allNotifications.get(key);
            if (tn != null && type.equals(tn.getType())) {
               v.addElement(new Integer(tn.getTriggerID()));
            }
         }

         return v;
      }
   }

   public String getNotificationType(Integer id) {
      TimerNotification nt = this.getNotification(id);
      return nt != null ? nt.getType() : null;
   }

   public String getNotificationMessage(Integer id) {
      TimerNotification nt = this.getNotification(id);
      return nt != null ? nt.getMessage() : null;
   }

   public Object getNotificationUserData(Integer id) {
      TimerNotification nt = this.getNotification(id);
      return nt != null ? nt.getUserData() : null;
   }

   public Date getDate(Integer id) {
      TimerNotification nt = this.getNotification(id);
      return nt != null ? nt.getDate() : null;
   }

   public Long getPeriod(Integer id) {
      TimerNotification nt = this.getNotification(id);
      return nt != null ? new Long(nt.getTimeStamp()) : null;
   }

   public Long getNbOccurences(Integer id) {
      TimerNotification nt = this.getNotification(id);
      return nt != null ? new Long(nt.getNbOccurences()) : null;
   }

   public Boolean getFixedRate(Integer id) {
      TimerNotification nt = this.getNotification(id);
      return nt != null ? new Boolean(nt.isFixedRate()) : null;
   }

   public boolean getSendPastNotifications() {
      return this.sendPastNotifications;
   }

   public boolean isActive() {
      return this.isActive;
   }

   public synchronized boolean isEmpty() {
      return this.notificationCount == 0;
   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      return new MBeanNotificationInfo[0];
   }

   public void setSendPastNotifications(boolean value) {
      this.sendPastNotifications = value;
   }

   public void start() {
      this.isActive = true;
      this.firePastNotifications();
   }

   public void stop() {
      this.isActive = false;
   }

   public Integer addNotification(String type, String message, Object userData, Date date, long period, long nbOccurences) throws IllegalArgumentException {
      return this.addNotification(type, message, userData, date, period, nbOccurences, false);
   }

   public Integer addNotification(String type, String message, Object userData, Date date, long period, long nbOccurences, boolean fixedRate) throws IllegalArgumentException {
      if (date == null) {
         throw new IllegalArgumentException("Null notification date.");
      } else if (period < 0L) {
         throw new IllegalArgumentException("Negative period value: " + period);
      } else if (nbOccurences < 0L) {
         throw new IllegalArgumentException("Negative occurances: " + nbOccurences);
      } else {
         TimerNotification tn = new TimerNotification(type, message, userData, date, period, nbOccurences, fixedRate, this);
         weblogic.timers.Timer tracker = null;
         if (!fixedRate) {
            tracker = this.timerManager.schedule(tn, date, period);
         } else {
            tracker = this.timerManager.scheduleAtFixedRate(tn, date, period);
         }

         tn.setTracker(tracker);
         Integer nextId = this.getNextId();
         synchronized(this.allNotifications) {
            this.allNotifications.put(nextId, tn);
            ++this.notificationCount;
            return nextId;
         }
      }
   }

   public Integer addNotification(String type, String message, Object userData, Date date, long period) throws IllegalArgumentException {
      return this.addNotification(type, message, userData, date, period, 0L);
   }

   public Integer addNotification(String type, String message, Object userData, Date date) throws IllegalArgumentException {
      return this.addNotification(type, message, userData, date, 0L, 0L);
   }

   public void removeNotification(Integer id) throws InstanceNotFoundException {
      synchronized(this.allNotifications) {
         TimerNotification tn = (TimerNotification)this.allNotifications.remove(id);
         if (tn != null) {
            tn.getTracker().cancel();
            --this.notificationCount;
         } else {
            throw new InstanceNotFoundException("Notification with id=" + id + " could not be found");
         }
      }
   }

   public void removeNotifications(String type) throws InstanceNotFoundException {
      Vector toRemove = new Vector();
      synchronized(this.allNotifications) {
         Iterator it = this.allNotifications.keySet().iterator();

         while(it.hasNext()) {
            Integer key = (Integer)it.next();
            TimerNotification nt = (TimerNotification)this.allNotifications.get(key);
            if (type.equals(nt.getType())) {
               toRemove.add(key);
               nt.getTracker().cancel();
            }
         }

         if (toRemove.size() == 0) {
            throw new InstanceNotFoundException("No notification of type=" + type + " found");
         } else {
            for(int i = 0; i < toRemove.size(); ++i) {
               this.allNotifications.remove(toRemove.get(i));
               --this.notificationCount;
            }

         }
      }
   }

   public void removeAllNotifications() {
      synchronized(this.allNotifications) {
         Iterator it = this.allNotifications.keySet().iterator();

         while(it.hasNext()) {
            TimerNotification tn = (TimerNotification)this.allNotifications.get((Integer)it.next());
            tn.getTracker().cancel();
         }

         this.allNotifications.clear();
         this.notificationCount = 0;
      }
   }

   public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
      synchronized(this.listenerLock) {
         if (this.listenerCount == this.allListeners.length) {
            int newSize = this.listenerCount == 0 ? 1 : this.listenerCount * 2;
            TimerListener[] newListeners = new TimerListener[newSize];
            System.arraycopy(this.allListeners, 0, newListeners, 0, this.listenerCount);
            this.allListeners = newListeners;
         }

         TimerListener tl = new TimerListener(listener, filter, handback);
         this.allListeners[this.listenerCount] = tl;
         ++this.listenerCount;
      }
   }

   public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
      synchronized(this.listenerLock) {
         for(int i = 0; i < this.listenerCount; ++i) {
            if (this.allListeners[i].getListener().equals(listener)) {
               --this.listenerCount;
               System.arraycopy(this.allListeners, i + 1, this.allListeners, i, this.listenerCount - i);
               this.allListeners[this.listenerCount] = null;
               break;
            }
         }

      }
   }

   public void deliverNotifications(Notification tn) {
      for(int i = 0; i < this.listenerCount; ++i) {
         this.allListeners[i].deliverNotification(tn);
      }

   }

   private synchronized Integer getNextId() {
      return new Integer(this.Id++);
   }

   private TimerNotification getNotification(Integer id) {
      synchronized(this.allNotifications) {
         return (TimerNotification)this.allNotifications.get(id);
      }
   }

   void addPastNotification(TimerNotification tn) {
      this.pastNotifications.add(tn);
   }

   private void firePastNotifications() {
      Iterator it = this.pastNotifications.iterator();

      while(it.hasNext()) {
         TimerNotification tn = (TimerNotification)it.next();

         for(int tnCount = tn.getPastNotificationCount(); tnCount > 0; --tnCount) {
            this.deliverNotifications(tn.getNotificationObject());
         }

         tn.unsetPastNotificationCount();
      }

   }
}
