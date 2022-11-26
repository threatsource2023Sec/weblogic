package weblogic.management.timer;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Date;
import javax.management.Notification;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

class TimerNotification implements weblogic.timers.TimerListener {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final long period;
   private final long nbOccurences;
   private long executeCount;
   private final Timer timer;
   private boolean removed;
   private boolean fixedRate;
   private final Notification notificationEvent;
   private final AuthenticatedSubject subject;
   private ClassLoader initClassLoader;
   private long time;
   private volatile int pastNotificationCount;
   private weblogic.timers.Timer tracker;
   private int instance;

   public TimerNotification(String type, String message, Object userData, Date date, long period, long nbOccurences, Timer timer) {
      this(type, message, userData, date, period, nbOccurences, false, timer);
   }

   public TimerNotification(String type, String message, Object userData, Date date, long period, long nbOccurences, boolean fixedRate, Timer timer) {
      this.executeCount = 0L;
      this.removed = false;
      this.fixedRate = false;
      this.pastNotificationCount = 0;
      this.tracker = null;
      this.instance = -1;
      this.time = date.getTime();
      this.period = period;
      this.nbOccurences = nbOccurences;
      this.fixedRate = fixedRate;
      this.timer = timer;
      this.notificationEvent = new Notification(type, timer, 0L, this.time, message);
      this.notificationEvent.setUserData(userData);
      this.subject = SecurityServiceManager.getCurrentSubject(kernelId);
      this.initClassLoader = Thread.currentThread().getContextClassLoader();
   }

   public Date getDate() {
      return new Date(this.time);
   }

   public long getPeriod() {
      return this.period;
   }

   public long getNbOccurences() {
      return this.nbOccurences;
   }

   public int getTriggerID() {
      return this.instance;
   }

   public void setTriggerID(int id) {
      this.instance = id;
   }

   public boolean isRemoved() {
      return this.removed;
   }

   public boolean isFixedRate() {
      return this.fixedRate;
   }

   public void setRemoved(boolean removed) {
      this.removed = removed;
   }

   public String getType() {
      return this.notificationEvent.getType();
   }

   public String getMessage() {
      return this.notificationEvent.getMessage();
   }

   public Object getUserData() {
      return this.notificationEvent.getUserData();
   }

   public long getTimeStamp() {
      return this.time;
   }

   public void timerExpired(weblogic.timers.Timer time) {
      ++this.executeCount;
      if (this.executeCount == this.nbOccurences) {
         time.cancel();
      }

      if (!this.timer.isActive()) {
         if (this.timer.getSendPastNotifications()) {
            ++this.pastNotificationCount;
            this.timer.addPastNotification(this);
         }

      } else {
         if (this.initClassLoader != null) {
            Thread.currentThread().setContextClassLoader(this.initClassLoader);
         }

         SecurityServiceManager.runAsForUserCode(kernelId, this.subject, new PrivilegedAction() {
            public Object run() {
               TimerNotification.this.timer.deliverNotifications(TimerNotification.this.notificationEvent);
               return null;
            }
         });
      }
   }

   void setTracker(weblogic.timers.Timer tracker) {
      this.tracker = tracker;
   }

   weblogic.timers.Timer getTracker() {
      return this.tracker;
   }

   Notification getNotificationObject() {
      return this.notificationEvent;
   }

   int getPastNotificationCount() {
      return this.pastNotificationCount;
   }

   void unsetPastNotificationCount() {
      this.pastNotificationCount = 0;
   }
}
